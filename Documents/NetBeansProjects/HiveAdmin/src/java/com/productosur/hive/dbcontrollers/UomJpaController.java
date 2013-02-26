/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class UomJpaController implements Serializable {

    public UomJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Uom uom) {
        if (uom.getUomCovertionFactorsCollection() == null) {
            uom.setUomCovertionFactorsCollection(new ArrayList<UomCovertionFactors>());
        }
        if (uom.getUomCovertionFactorsCollection1() == null) {
            uom.setUomCovertionFactorsCollection1(new ArrayList<UomCovertionFactors>());
        }
        if (uom.getProductsCollection() == null) {
            uom.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UomCovertionFactors> attachedUomCovertionFactorsCollection = new ArrayList<UomCovertionFactors>();
            for (UomCovertionFactors uomCovertionFactorsCollectionUomCovertionFactorsToAttach : uom.getUomCovertionFactorsCollection()) {
                uomCovertionFactorsCollectionUomCovertionFactorsToAttach = em.getReference(uomCovertionFactorsCollectionUomCovertionFactorsToAttach.getClass(), uomCovertionFactorsCollectionUomCovertionFactorsToAttach.getId());
                attachedUomCovertionFactorsCollection.add(uomCovertionFactorsCollectionUomCovertionFactorsToAttach);
            }
            uom.setUomCovertionFactorsCollection(attachedUomCovertionFactorsCollection);
            Collection<UomCovertionFactors> attachedUomCovertionFactorsCollection1 = new ArrayList<UomCovertionFactors>();
            for (UomCovertionFactors uomCovertionFactorsCollection1UomCovertionFactorsToAttach : uom.getUomCovertionFactorsCollection1()) {
                uomCovertionFactorsCollection1UomCovertionFactorsToAttach = em.getReference(uomCovertionFactorsCollection1UomCovertionFactorsToAttach.getClass(), uomCovertionFactorsCollection1UomCovertionFactorsToAttach.getId());
                attachedUomCovertionFactorsCollection1.add(uomCovertionFactorsCollection1UomCovertionFactorsToAttach);
            }
            uom.setUomCovertionFactorsCollection1(attachedUomCovertionFactorsCollection1);
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : uom.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            uom.setProductsCollection(attachedProductsCollection);
            em.persist(uom);
            for (UomCovertionFactors uomCovertionFactorsCollectionUomCovertionFactors : uom.getUomCovertionFactorsCollection()) {
                Uom oldUomToIdOfUomCovertionFactorsCollectionUomCovertionFactors = uomCovertionFactorsCollectionUomCovertionFactors.getUomToId();
                uomCovertionFactorsCollectionUomCovertionFactors.setUomToId(uom);
                uomCovertionFactorsCollectionUomCovertionFactors = em.merge(uomCovertionFactorsCollectionUomCovertionFactors);
                if (oldUomToIdOfUomCovertionFactorsCollectionUomCovertionFactors != null) {
                    oldUomToIdOfUomCovertionFactorsCollectionUomCovertionFactors.getUomCovertionFactorsCollection().remove(uomCovertionFactorsCollectionUomCovertionFactors);
                    oldUomToIdOfUomCovertionFactorsCollectionUomCovertionFactors = em.merge(oldUomToIdOfUomCovertionFactorsCollectionUomCovertionFactors);
                }
            }
            for (UomCovertionFactors uomCovertionFactorsCollection1UomCovertionFactors : uom.getUomCovertionFactorsCollection1()) {
                Uom oldUomFromIdOfUomCovertionFactorsCollection1UomCovertionFactors = uomCovertionFactorsCollection1UomCovertionFactors.getUomFromId();
                uomCovertionFactorsCollection1UomCovertionFactors.setUomFromId(uom);
                uomCovertionFactorsCollection1UomCovertionFactors = em.merge(uomCovertionFactorsCollection1UomCovertionFactors);
                if (oldUomFromIdOfUomCovertionFactorsCollection1UomCovertionFactors != null) {
                    oldUomFromIdOfUomCovertionFactorsCollection1UomCovertionFactors.getUomCovertionFactorsCollection1().remove(uomCovertionFactorsCollection1UomCovertionFactors);
                    oldUomFromIdOfUomCovertionFactorsCollection1UomCovertionFactors = em.merge(oldUomFromIdOfUomCovertionFactorsCollection1UomCovertionFactors);
                }
            }
            for (Products productsCollectionProducts : uom.getProductsCollection()) {
                Uom oldUomIdOfProductsCollectionProducts = productsCollectionProducts.getUomId();
                productsCollectionProducts.setUomId(uom);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldUomIdOfProductsCollectionProducts != null) {
                    oldUomIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldUomIdOfProductsCollectionProducts = em.merge(oldUomIdOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Uom uom) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Uom persistentUom = em.find(Uom.class, uom.getId());
            Collection<UomCovertionFactors> uomCovertionFactorsCollectionOld = persistentUom.getUomCovertionFactorsCollection();
            Collection<UomCovertionFactors> uomCovertionFactorsCollectionNew = uom.getUomCovertionFactorsCollection();
            Collection<UomCovertionFactors> uomCovertionFactorsCollection1Old = persistentUom.getUomCovertionFactorsCollection1();
            Collection<UomCovertionFactors> uomCovertionFactorsCollection1New = uom.getUomCovertionFactorsCollection1();
            Collection<Products> productsCollectionOld = persistentUom.getProductsCollection();
            Collection<Products> productsCollectionNew = uom.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (UomCovertionFactors uomCovertionFactorsCollectionOldUomCovertionFactors : uomCovertionFactorsCollectionOld) {
                if (!uomCovertionFactorsCollectionNew.contains(uomCovertionFactorsCollectionOldUomCovertionFactors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UomCovertionFactors " + uomCovertionFactorsCollectionOldUomCovertionFactors + " since its uomToId field is not nullable.");
                }
            }
            for (UomCovertionFactors uomCovertionFactorsCollection1OldUomCovertionFactors : uomCovertionFactorsCollection1Old) {
                if (!uomCovertionFactorsCollection1New.contains(uomCovertionFactorsCollection1OldUomCovertionFactors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UomCovertionFactors " + uomCovertionFactorsCollection1OldUomCovertionFactors + " since its uomFromId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UomCovertionFactors> attachedUomCovertionFactorsCollectionNew = new ArrayList<UomCovertionFactors>();
            for (UomCovertionFactors uomCovertionFactorsCollectionNewUomCovertionFactorsToAttach : uomCovertionFactorsCollectionNew) {
                uomCovertionFactorsCollectionNewUomCovertionFactorsToAttach = em.getReference(uomCovertionFactorsCollectionNewUomCovertionFactorsToAttach.getClass(), uomCovertionFactorsCollectionNewUomCovertionFactorsToAttach.getId());
                attachedUomCovertionFactorsCollectionNew.add(uomCovertionFactorsCollectionNewUomCovertionFactorsToAttach);
            }
            uomCovertionFactorsCollectionNew = attachedUomCovertionFactorsCollectionNew;
            uom.setUomCovertionFactorsCollection(uomCovertionFactorsCollectionNew);
            Collection<UomCovertionFactors> attachedUomCovertionFactorsCollection1New = new ArrayList<UomCovertionFactors>();
            for (UomCovertionFactors uomCovertionFactorsCollection1NewUomCovertionFactorsToAttach : uomCovertionFactorsCollection1New) {
                uomCovertionFactorsCollection1NewUomCovertionFactorsToAttach = em.getReference(uomCovertionFactorsCollection1NewUomCovertionFactorsToAttach.getClass(), uomCovertionFactorsCollection1NewUomCovertionFactorsToAttach.getId());
                attachedUomCovertionFactorsCollection1New.add(uomCovertionFactorsCollection1NewUomCovertionFactorsToAttach);
            }
            uomCovertionFactorsCollection1New = attachedUomCovertionFactorsCollection1New;
            uom.setUomCovertionFactorsCollection1(uomCovertionFactorsCollection1New);
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            uom.setProductsCollection(productsCollectionNew);
            uom = em.merge(uom);
            for (UomCovertionFactors uomCovertionFactorsCollectionNewUomCovertionFactors : uomCovertionFactorsCollectionNew) {
                if (!uomCovertionFactorsCollectionOld.contains(uomCovertionFactorsCollectionNewUomCovertionFactors)) {
                    Uom oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors = uomCovertionFactorsCollectionNewUomCovertionFactors.getUomToId();
                    uomCovertionFactorsCollectionNewUomCovertionFactors.setUomToId(uom);
                    uomCovertionFactorsCollectionNewUomCovertionFactors = em.merge(uomCovertionFactorsCollectionNewUomCovertionFactors);
                    if (oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors != null && !oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors.equals(uom)) {
                        oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors.getUomCovertionFactorsCollection().remove(uomCovertionFactorsCollectionNewUomCovertionFactors);
                        oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors = em.merge(oldUomToIdOfUomCovertionFactorsCollectionNewUomCovertionFactors);
                    }
                }
            }
            for (UomCovertionFactors uomCovertionFactorsCollection1NewUomCovertionFactors : uomCovertionFactorsCollection1New) {
                if (!uomCovertionFactorsCollection1Old.contains(uomCovertionFactorsCollection1NewUomCovertionFactors)) {
                    Uom oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors = uomCovertionFactorsCollection1NewUomCovertionFactors.getUomFromId();
                    uomCovertionFactorsCollection1NewUomCovertionFactors.setUomFromId(uom);
                    uomCovertionFactorsCollection1NewUomCovertionFactors = em.merge(uomCovertionFactorsCollection1NewUomCovertionFactors);
                    if (oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors != null && !oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors.equals(uom)) {
                        oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors.getUomCovertionFactorsCollection1().remove(uomCovertionFactorsCollection1NewUomCovertionFactors);
                        oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors = em.merge(oldUomFromIdOfUomCovertionFactorsCollection1NewUomCovertionFactors);
                    }
                }
            }
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    productsCollectionOldProducts.setUomId(null);
                    productsCollectionOldProducts = em.merge(productsCollectionOldProducts);
                }
            }
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Uom oldUomIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getUomId();
                    productsCollectionNewProducts.setUomId(uom);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldUomIdOfProductsCollectionNewProducts != null && !oldUomIdOfProductsCollectionNewProducts.equals(uom)) {
                        oldUomIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldUomIdOfProductsCollectionNewProducts = em.merge(oldUomIdOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = uom.getId();
                if (findUom(id) == null) {
                    throw new NonexistentEntityException("The uom with id " + id + " no longer exists.");
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
            Uom uom;
            try {
                uom = em.getReference(Uom.class, id);
                uom.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The uom with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UomCovertionFactors> uomCovertionFactorsCollectionOrphanCheck = uom.getUomCovertionFactorsCollection();
            for (UomCovertionFactors uomCovertionFactorsCollectionOrphanCheckUomCovertionFactors : uomCovertionFactorsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Uom (" + uom + ") cannot be destroyed since the UomCovertionFactors " + uomCovertionFactorsCollectionOrphanCheckUomCovertionFactors + " in its uomCovertionFactorsCollection field has a non-nullable uomToId field.");
            }
            Collection<UomCovertionFactors> uomCovertionFactorsCollection1OrphanCheck = uom.getUomCovertionFactorsCollection1();
            for (UomCovertionFactors uomCovertionFactorsCollection1OrphanCheckUomCovertionFactors : uomCovertionFactorsCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Uom (" + uom + ") cannot be destroyed since the UomCovertionFactors " + uomCovertionFactorsCollection1OrphanCheckUomCovertionFactors + " in its uomCovertionFactorsCollection1 field has a non-nullable uomFromId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> productsCollection = uom.getProductsCollection();
            for (Products productsCollectionProducts : productsCollection) {
                productsCollectionProducts.setUomId(null);
                productsCollectionProducts = em.merge(productsCollectionProducts);
            }
            em.remove(uom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Uom> findUomEntities() {
        return findUomEntities(true, -1, -1);
    }

    public List<Uom> findUomEntities(int maxResults, int firstResult) {
        return findUomEntities(false, maxResults, firstResult);
    }

    private List<Uom> findUomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Uom.class));
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

    public Uom findUom(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Uom.class, id);
        } finally {
            em.close();
        }
    }

    public int getUomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Uom> rt = cq.from(Uom.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
