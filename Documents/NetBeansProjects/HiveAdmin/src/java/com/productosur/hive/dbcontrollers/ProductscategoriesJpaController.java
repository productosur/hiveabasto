/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class ProductscategoriesJpaController implements Serializable {

    public ProductscategoriesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productscategories productscategories) {
        if (productscategories.getProductsCollection() == null) {
            productscategories.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : productscategories.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            productscategories.setProductsCollection(attachedProductsCollection);
            em.persist(productscategories);
            for (Products productsCollectionProducts : productscategories.getProductsCollection()) {
                Productscategories oldProductscategoryIdOfProductsCollectionProducts = productsCollectionProducts.getProductscategoryId();
                productsCollectionProducts.setProductscategoryId(productscategories);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldProductscategoryIdOfProductsCollectionProducts != null) {
                    oldProductscategoryIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldProductscategoryIdOfProductsCollectionProducts = em.merge(oldProductscategoryIdOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productscategories productscategories) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productscategories persistentProductscategories = em.find(Productscategories.class, productscategories.getId());
            Collection<Products> productsCollectionOld = persistentProductscategories.getProductsCollection();
            Collection<Products> productsCollectionNew = productscategories.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its productscategoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            productscategories.setProductsCollection(productsCollectionNew);
            productscategories = em.merge(productscategories);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Productscategories oldProductscategoryIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getProductscategoryId();
                    productsCollectionNewProducts.setProductscategoryId(productscategories);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldProductscategoryIdOfProductsCollectionNewProducts != null && !oldProductscategoryIdOfProductsCollectionNewProducts.equals(productscategories)) {
                        oldProductscategoryIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldProductscategoryIdOfProductsCollectionNewProducts = em.merge(oldProductscategoryIdOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productscategories.getId();
                if (findProductscategories(id) == null) {
                    throw new NonexistentEntityException("The productscategories with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productscategories productscategories;
            try {
                productscategories = em.getReference(Productscategories.class, id);
                productscategories.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productscategories with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = productscategories.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productscategories (" + productscategories + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable productscategoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productscategories);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productscategories> findProductscategoriesEntities() {
        return findProductscategoriesEntities(true, -1, -1);
    }

    public List<Productscategories> findProductscategoriesEntities(int maxResults, int firstResult) {
        return findProductscategoriesEntities(false, maxResults, firstResult);
    }

    private List<Productscategories> findProductscategoriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productscategories.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Productscategories findProductscategories(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productscategories.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductscategoriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productscategories> rt = cq.from(Productscategories.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
