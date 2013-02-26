/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.IllegalOrphanException;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Manufacturers;
import com.productosur.hive.entities.Documentypes;
import com.productosur.hive.entities.Purchases;
import com.productosur.hive.entities.PurchasesLines;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PurchasesJpaController implements Serializable {

    public PurchasesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Purchases purchases) {
        if (purchases.getPurchasesLinesCollection() == null) {
            purchases.setPurchasesLinesCollection(new ArrayList<PurchasesLines>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Manufacturers manufacturersId = purchases.getManufacturersId();
            if (manufacturersId != null) {
                manufacturersId = em.getReference(manufacturersId.getClass(), manufacturersId.getId());
                purchases.setManufacturersId(manufacturersId);
            }
            Documentypes documentypeId = purchases.getDocumentypeId();
            if (documentypeId != null) {
                documentypeId = em.getReference(documentypeId.getClass(), documentypeId.getId());
                purchases.setDocumentypeId(documentypeId);
            }
            Collection<PurchasesLines> attachedPurchasesLinesCollection = new ArrayList<PurchasesLines>();
            for (PurchasesLines purchasesLinesCollectionPurchasesLinesToAttach : purchases.getPurchasesLinesCollection()) {
                purchasesLinesCollectionPurchasesLinesToAttach = em.getReference(purchasesLinesCollectionPurchasesLinesToAttach.getClass(), purchasesLinesCollectionPurchasesLinesToAttach.getId());
                attachedPurchasesLinesCollection.add(purchasesLinesCollectionPurchasesLinesToAttach);
            }
            purchases.setPurchasesLinesCollection(attachedPurchasesLinesCollection);
            em.persist(purchases);
            if (manufacturersId != null) {
                manufacturersId.getPurchasesCollection().add(purchases);
                manufacturersId = em.merge(manufacturersId);
            }
           
            for (PurchasesLines purchasesLinesCollectionPurchasesLines : purchases.getPurchasesLinesCollection()) {
                Purchases oldPurchaseIdOfPurchasesLinesCollectionPurchasesLines = purchasesLinesCollectionPurchasesLines.getPurchaseId();
                purchasesLinesCollectionPurchasesLines.setPurchaseId(purchases);
                purchasesLinesCollectionPurchasesLines = em.merge(purchasesLinesCollectionPurchasesLines);
                if (oldPurchaseIdOfPurchasesLinesCollectionPurchasesLines != null) {
                    oldPurchaseIdOfPurchasesLinesCollectionPurchasesLines.getPurchasesLinesCollection().remove(purchasesLinesCollectionPurchasesLines);
                    oldPurchaseIdOfPurchasesLinesCollectionPurchasesLines = em.merge(oldPurchaseIdOfPurchasesLinesCollectionPurchasesLines);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Purchases purchases) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Purchases persistentPurchases = em.find(Purchases.class, purchases.getId());
            Manufacturers manufacturersIdOld = persistentPurchases.getManufacturersId();
            Manufacturers manufacturersIdNew = purchases.getManufacturersId();
            Documentypes documentypeIdOld = persistentPurchases.getDocumentypeId();
            Documentypes documentypeIdNew = purchases.getDocumentypeId();
            Collection<PurchasesLines> purchasesLinesCollectionOld = persistentPurchases.getPurchasesLinesCollection();
            Collection<PurchasesLines> purchasesLinesCollectionNew = purchases.getPurchasesLinesCollection();
            List<String> illegalOrphanMessages = null;
            for (PurchasesLines purchasesLinesCollectionOldPurchasesLines : purchasesLinesCollectionOld) {
                if (!purchasesLinesCollectionNew.contains(purchasesLinesCollectionOldPurchasesLines)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PurchasesLines " + purchasesLinesCollectionOldPurchasesLines + " since its purchaseId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (manufacturersIdNew != null) {
                manufacturersIdNew = em.getReference(manufacturersIdNew.getClass(), manufacturersIdNew.getId());
                purchases.setManufacturersId(manufacturersIdNew);
            }
            if (documentypeIdNew != null) {
                documentypeIdNew = em.getReference(documentypeIdNew.getClass(), documentypeIdNew.getId());
                purchases.setDocumentypeId(documentypeIdNew);
            }
            Collection<PurchasesLines> attachedPurchasesLinesCollectionNew = new ArrayList<PurchasesLines>();
            for (PurchasesLines purchasesLinesCollectionNewPurchasesLinesToAttach : purchasesLinesCollectionNew) {
                purchasesLinesCollectionNewPurchasesLinesToAttach = em.getReference(purchasesLinesCollectionNewPurchasesLinesToAttach.getClass(), purchasesLinesCollectionNewPurchasesLinesToAttach.getId());
                attachedPurchasesLinesCollectionNew.add(purchasesLinesCollectionNewPurchasesLinesToAttach);
            }
            purchasesLinesCollectionNew = attachedPurchasesLinesCollectionNew;
            purchases.setPurchasesLinesCollection(purchasesLinesCollectionNew);
            purchases = em.merge(purchases);
            if (manufacturersIdOld != null && !manufacturersIdOld.equals(manufacturersIdNew)) {
                manufacturersIdOld.getPurchasesCollection().remove(purchases);
                manufacturersIdOld = em.merge(manufacturersIdOld);
            }
            if (manufacturersIdNew != null && !manufacturersIdNew.equals(manufacturersIdOld)) {
                manufacturersIdNew.getPurchasesCollection().add(purchases);
                manufacturersIdNew = em.merge(manufacturersIdNew);
            }
            
            for (PurchasesLines purchasesLinesCollectionNewPurchasesLines : purchasesLinesCollectionNew) {
                if (!purchasesLinesCollectionOld.contains(purchasesLinesCollectionNewPurchasesLines)) {
                    Purchases oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines = purchasesLinesCollectionNewPurchasesLines.getPurchaseId();
                    purchasesLinesCollectionNewPurchasesLines.setPurchaseId(purchases);
                    purchasesLinesCollectionNewPurchasesLines = em.merge(purchasesLinesCollectionNewPurchasesLines);
                    if (oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines != null && !oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines.equals(purchases)) {
                        oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines.getPurchasesLinesCollection().remove(purchasesLinesCollectionNewPurchasesLines);
                        oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines = em.merge(oldPurchaseIdOfPurchasesLinesCollectionNewPurchasesLines);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = purchases.getId();
                if (findPurchases(id) == null) {
                    throw new NonexistentEntityException("The purchases with id " + id + " no longer exists.");
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
            Purchases purchases;
            try {
                purchases = em.getReference(Purchases.class, id);
                purchases.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchases with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PurchasesLines> purchasesLinesCollectionOrphanCheck = purchases.getPurchasesLinesCollection();
            for (PurchasesLines purchasesLinesCollectionOrphanCheckPurchasesLines : purchasesLinesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Purchases (" + purchases + ") cannot be destroyed since the PurchasesLines " + purchasesLinesCollectionOrphanCheckPurchasesLines + " in its purchasesLinesCollection field has a non-nullable purchaseId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Manufacturers manufacturersId = purchases.getManufacturersId();
            if (manufacturersId != null) {
                manufacturersId.getPurchasesCollection().remove(purchases);
                manufacturersId = em.merge(manufacturersId);
            }
            
            em.remove(purchases);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Purchases> findPurchasesEntities() {
        return findPurchasesEntities(true, -1, -1);
    }

    public List<Purchases> findPurchasesEntities(int maxResults, int firstResult) {
        return findPurchasesEntities(false, maxResults, firstResult);
    }

    private List<Purchases> findPurchasesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Purchases.class));
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

    public Purchases findPurchases(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Purchases.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchasesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Purchases> rt = cq.from(Purchases.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
