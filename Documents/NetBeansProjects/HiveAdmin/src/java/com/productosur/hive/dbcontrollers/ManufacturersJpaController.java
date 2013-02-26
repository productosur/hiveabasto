/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.IllegalOrphanException;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Manufacturers;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Products;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.Purchases;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class ManufacturersJpaController implements Serializable {

    public ManufacturersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Manufacturers manufacturers) {
        if (manufacturers.getProductsCollection() == null) {
            manufacturers.setProductsCollection(new ArrayList<Products>());
        }
        if (manufacturers.getPurchasesCollection() == null) {
            manufacturers.setPurchasesCollection(new ArrayList<Purchases>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : manufacturers.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            manufacturers.setProductsCollection(attachedProductsCollection);
            Collection<Purchases> attachedPurchasesCollection = new ArrayList<Purchases>();
            for (Purchases purchasesCollectionPurchasesToAttach : manufacturers.getPurchasesCollection()) {
                purchasesCollectionPurchasesToAttach = em.getReference(purchasesCollectionPurchasesToAttach.getClass(), purchasesCollectionPurchasesToAttach.getId());
                attachedPurchasesCollection.add(purchasesCollectionPurchasesToAttach);
            }
            manufacturers.setPurchasesCollection(attachedPurchasesCollection);
            em.persist(manufacturers);
            for (Products productsCollectionProducts : manufacturers.getProductsCollection()) {
                Manufacturers oldManufacturerIdOfProductsCollectionProducts = productsCollectionProducts.getManufacturerId();
                productsCollectionProducts.setManufacturerId(manufacturers);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldManufacturerIdOfProductsCollectionProducts != null) {
                    oldManufacturerIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldManufacturerIdOfProductsCollectionProducts = em.merge(oldManufacturerIdOfProductsCollectionProducts);
                }
            }
            for (Purchases purchasesCollectionPurchases : manufacturers.getPurchasesCollection()) {
                Manufacturers oldManufacturersIdOfPurchasesCollectionPurchases = purchasesCollectionPurchases.getManufacturersId();
                purchasesCollectionPurchases.setManufacturersId(manufacturers);
                purchasesCollectionPurchases = em.merge(purchasesCollectionPurchases);
                if (oldManufacturersIdOfPurchasesCollectionPurchases != null) {
                    oldManufacturersIdOfPurchasesCollectionPurchases.getPurchasesCollection().remove(purchasesCollectionPurchases);
                    oldManufacturersIdOfPurchasesCollectionPurchases = em.merge(oldManufacturersIdOfPurchasesCollectionPurchases);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Manufacturers manufacturers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Manufacturers persistentManufacturers = em.find(Manufacturers.class, manufacturers.getId());
            Collection<Products> productsCollectionOld = persistentManufacturers.getProductsCollection();
            Collection<Products> productsCollectionNew = manufacturers.getProductsCollection();
            Collection<Purchases> purchasesCollectionOld = persistentManufacturers.getPurchasesCollection();
            Collection<Purchases> purchasesCollectionNew = manufacturers.getPurchasesCollection();
            List<String> illegalOrphanMessages = null;
            for (Purchases purchasesCollectionOldPurchases : purchasesCollectionOld) {
                if (!purchasesCollectionNew.contains(purchasesCollectionOldPurchases)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Purchases " + purchasesCollectionOldPurchases + " since its manufacturersId field is not nullable.");
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
            manufacturers.setProductsCollection(productsCollectionNew);
            Collection<Purchases> attachedPurchasesCollectionNew = new ArrayList<Purchases>();
            for (Purchases purchasesCollectionNewPurchasesToAttach : purchasesCollectionNew) {
                purchasesCollectionNewPurchasesToAttach = em.getReference(purchasesCollectionNewPurchasesToAttach.getClass(), purchasesCollectionNewPurchasesToAttach.getId());
                attachedPurchasesCollectionNew.add(purchasesCollectionNewPurchasesToAttach);
            }
            purchasesCollectionNew = attachedPurchasesCollectionNew;
            manufacturers.setPurchasesCollection(purchasesCollectionNew);
            manufacturers = em.merge(manufacturers);
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    productsCollectionOldProducts.setManufacturerId(null);
                    productsCollectionOldProducts = em.merge(productsCollectionOldProducts);
                }
            }
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Manufacturers oldManufacturerIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getManufacturerId();
                    productsCollectionNewProducts.setManufacturerId(manufacturers);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldManufacturerIdOfProductsCollectionNewProducts != null && !oldManufacturerIdOfProductsCollectionNewProducts.equals(manufacturers)) {
                        oldManufacturerIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldManufacturerIdOfProductsCollectionNewProducts = em.merge(oldManufacturerIdOfProductsCollectionNewProducts);
                    }
                }
            }
            for (Purchases purchasesCollectionNewPurchases : purchasesCollectionNew) {
                if (!purchasesCollectionOld.contains(purchasesCollectionNewPurchases)) {
                    Manufacturers oldManufacturersIdOfPurchasesCollectionNewPurchases = purchasesCollectionNewPurchases.getManufacturersId();
                    purchasesCollectionNewPurchases.setManufacturersId(manufacturers);
                    purchasesCollectionNewPurchases = em.merge(purchasesCollectionNewPurchases);
                    if (oldManufacturersIdOfPurchasesCollectionNewPurchases != null && !oldManufacturersIdOfPurchasesCollectionNewPurchases.equals(manufacturers)) {
                        oldManufacturersIdOfPurchasesCollectionNewPurchases.getPurchasesCollection().remove(purchasesCollectionNewPurchases);
                        oldManufacturersIdOfPurchasesCollectionNewPurchases = em.merge(oldManufacturersIdOfPurchasesCollectionNewPurchases);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = manufacturers.getId();
                if (findManufacturers(id) == null) {
                    throw new NonexistentEntityException("The manufacturers with id " + id + " no longer exists.");
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
            Manufacturers manufacturers;
            try {
                manufacturers = em.getReference(Manufacturers.class, id);
                manufacturers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The manufacturers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Purchases> purchasesCollectionOrphanCheck = manufacturers.getPurchasesCollection();
            for (Purchases purchasesCollectionOrphanCheckPurchases : purchasesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Manufacturers (" + manufacturers + ") cannot be destroyed since the Purchases " + purchasesCollectionOrphanCheckPurchases + " in its purchasesCollection field has a non-nullable manufacturersId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> productsCollection = manufacturers.getProductsCollection();
            for (Products productsCollectionProducts : productsCollection) {
                productsCollectionProducts.setManufacturerId(null);
                productsCollectionProducts = em.merge(productsCollectionProducts);
            }
            em.remove(manufacturers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Manufacturers> findManufacturersEntities() {
        return findManufacturersEntities(true, -1, -1);
    }

    public List<Manufacturers> findManufacturersEntities(int maxResults, int firstResult) {
        return findManufacturersEntities(false, maxResults, firstResult);
    }

    private List<Manufacturers> findManufacturersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Manufacturers.class));
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

    public Manufacturers findManufacturers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Manufacturers.class, id);
        } finally {
            em.close();
        }
    }

    public int getManufacturersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Manufacturers> rt = cq.from(Manufacturers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
