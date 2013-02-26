/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Purchases;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.PurchasesLines;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PurchasesLinesJpaController implements Serializable {

    public PurchasesLinesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PurchasesLines purchasesLines) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Purchases purchaseId = purchasesLines.getPurchaseId();
            if (purchaseId != null) {
                purchaseId = em.getReference(purchaseId.getClass(), purchaseId.getId());
                purchasesLines.setPurchaseId(purchaseId);
            }
            Products productId = purchasesLines.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                purchasesLines.setProductId(productId);
            }
            em.persist(purchasesLines);
            if (purchaseId != null) {
                purchaseId.getPurchasesLinesCollection().add(purchasesLines);
                purchaseId = em.merge(purchaseId);
            }
            if (productId != null) {
                productId.getPurchasesLinesCollection().add(purchasesLines);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PurchasesLines purchasesLines) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PurchasesLines persistentPurchasesLines = em.find(PurchasesLines.class, purchasesLines.getId());
            Purchases purchaseIdOld = persistentPurchasesLines.getPurchaseId();
            Purchases purchaseIdNew = purchasesLines.getPurchaseId();
            Products productIdOld = persistentPurchasesLines.getProductId();
            Products productIdNew = purchasesLines.getProductId();
            if (purchaseIdNew != null) {
                purchaseIdNew = em.getReference(purchaseIdNew.getClass(), purchaseIdNew.getId());
                purchasesLines.setPurchaseId(purchaseIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                purchasesLines.setProductId(productIdNew);
            }
            purchasesLines = em.merge(purchasesLines);
            if (purchaseIdOld != null && !purchaseIdOld.equals(purchaseIdNew)) {
                purchaseIdOld.getPurchasesLinesCollection().remove(purchasesLines);
                purchaseIdOld = em.merge(purchaseIdOld);
            }
            if (purchaseIdNew != null && !purchaseIdNew.equals(purchaseIdOld)) {
                purchaseIdNew.getPurchasesLinesCollection().add(purchasesLines);
                purchaseIdNew = em.merge(purchaseIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getPurchasesLinesCollection().remove(purchasesLines);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getPurchasesLinesCollection().add(purchasesLines);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = purchasesLines.getId();
                if (findPurchasesLines(id) == null) {
                    throw new NonexistentEntityException("The purchasesLines with id " + id + " no longer exists.");
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
            PurchasesLines purchasesLines;
            try {
                purchasesLines = em.getReference(PurchasesLines.class, id);
                purchasesLines.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchasesLines with id " + id + " no longer exists.", enfe);
            }
            Purchases purchaseId = purchasesLines.getPurchaseId();
            if (purchaseId != null) {
                purchaseId.getPurchasesLinesCollection().remove(purchasesLines);
                purchaseId = em.merge(purchaseId);
            }
            Products productId = purchasesLines.getProductId();
            if (productId != null) {
                productId.getPurchasesLinesCollection().remove(purchasesLines);
                productId = em.merge(productId);
            }
            em.remove(purchasesLines);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PurchasesLines> findPurchasesLinesEntities() {
        return findPurchasesLinesEntities(true, -1, -1);
    }

    public List<PurchasesLines> findPurchasesLinesEntities(int maxResults, int firstResult) {
        return findPurchasesLinesEntities(false, maxResults, firstResult);
    }

    private List<PurchasesLines> findPurchasesLinesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PurchasesLines.class));
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

    public PurchasesLines findPurchasesLines(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PurchasesLines.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchasesLinesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PurchasesLines> rt = cq.from(PurchasesLines.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
