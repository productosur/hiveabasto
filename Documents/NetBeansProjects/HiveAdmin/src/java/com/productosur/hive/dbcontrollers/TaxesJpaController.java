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
public class TaxesJpaController implements Serializable {

    public TaxesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Taxes taxes) {
        if (taxes.getProductsCollection() == null) {
            taxes.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : taxes.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            taxes.setProductsCollection(attachedProductsCollection);
            em.persist(taxes);
            for (Products productsCollectionProducts : taxes.getProductsCollection()) {
                Taxes oldTaxIdOfProductsCollectionProducts = productsCollectionProducts.getTaxId();
                productsCollectionProducts.setTaxId(taxes);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldTaxIdOfProductsCollectionProducts != null) {
                    oldTaxIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldTaxIdOfProductsCollectionProducts = em.merge(oldTaxIdOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Taxes taxes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taxes persistentTaxes = em.find(Taxes.class, taxes.getId());
            Collection<Products> productsCollectionOld = persistentTaxes.getProductsCollection();
            Collection<Products> productsCollectionNew = taxes.getProductsCollection();
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            taxes.setProductsCollection(productsCollectionNew);
            taxes = em.merge(taxes);
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    productsCollectionOldProducts.setTaxId(null);
                    productsCollectionOldProducts = em.merge(productsCollectionOldProducts);
                }
            }
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Taxes oldTaxIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getTaxId();
                    productsCollectionNewProducts.setTaxId(taxes);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldTaxIdOfProductsCollectionNewProducts != null && !oldTaxIdOfProductsCollectionNewProducts.equals(taxes)) {
                        oldTaxIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldTaxIdOfProductsCollectionNewProducts = em.merge(oldTaxIdOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = taxes.getId();
                if (findTaxes(id) == null) {
                    throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taxes taxes;
            try {
                taxes = em.getReference(Taxes.class, id);
                taxes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taxes with id " + id + " no longer exists.", enfe);
            }
            Collection<Products> productsCollection = taxes.getProductsCollection();
            for (Products productsCollectionProducts : productsCollection) {
                productsCollectionProducts.setTaxId(null);
                productsCollectionProducts = em.merge(productsCollectionProducts);
            }
            em.remove(taxes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Taxes> findTaxesEntities() {
        return findTaxesEntities(true, -1, -1);
    }

    public List<Taxes> findTaxesEntities(int maxResults, int firstResult) {
        return findTaxesEntities(false, maxResults, firstResult);
    }

    private List<Taxes> findTaxesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taxes.class));
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

    public Taxes findTaxes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Taxes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaxesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taxes> rt = cq.from(Taxes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Taxes findDefaultTax() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("Select o from Taxes o where o.defaultValue=1");
           
            return (Taxes)q.getSingleResult();
        } finally {
            em.close();
        }
    }

    
}
