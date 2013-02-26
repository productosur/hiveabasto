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
import com.productosur.hive.entities.Deliveries;
import com.productosur.hive.entities.DeliveryStock;
import com.productosur.hive.entities.Products;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class DeliveryStockJpaController implements Serializable {

    public DeliveryStockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DeliveryStock deliveryStock) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deliveries deliveryId = deliveryStock.getDeliveryId();
            if (deliveryId != null) {
                deliveryId = em.getReference(deliveryId.getClass(), deliveryId.getId());
                deliveryStock.setDeliveryId(deliveryId);
            }
            Products productId = deliveryStock.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                deliveryStock.setProductId(productId);
            }
            em.persist(deliveryStock);
            if (deliveryId != null) {
                deliveryId.getDeliveryStockCollection().add(deliveryStock);
                deliveryId = em.merge(deliveryId);
            }
            if (productId != null) {
                productId.getDeliveryStockCollection().add(deliveryStock);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DeliveryStock deliveryStock) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DeliveryStock persistentDeliveryStock = em.find(DeliveryStock.class, deliveryStock.getId());
            Deliveries deliveryIdOld = persistentDeliveryStock.getDeliveryId();
            Deliveries deliveryIdNew = deliveryStock.getDeliveryId();
            Products productIdOld = persistentDeliveryStock.getProductId();
            Products productIdNew = deliveryStock.getProductId();
            if (deliveryIdNew != null) {
                deliveryIdNew = em.getReference(deliveryIdNew.getClass(), deliveryIdNew.getId());
                deliveryStock.setDeliveryId(deliveryIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                deliveryStock.setProductId(productIdNew);
            }
            deliveryStock = em.merge(deliveryStock);
            if (deliveryIdOld != null && !deliveryIdOld.equals(deliveryIdNew)) {
                deliveryIdOld.getDeliveryStockCollection().remove(deliveryStock);
                deliveryIdOld = em.merge(deliveryIdOld);
            }
            if (deliveryIdNew != null && !deliveryIdNew.equals(deliveryIdOld)) {
                deliveryIdNew.getDeliveryStockCollection().add(deliveryStock);
                deliveryIdNew = em.merge(deliveryIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getDeliveryStockCollection().remove(deliveryStock);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getDeliveryStockCollection().add(deliveryStock);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deliveryStock.getId();
                if (findDeliveryStock(id) == null) {
                    throw new NonexistentEntityException("The deliveryStock with id " + id + " no longer exists.");
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
            DeliveryStock deliveryStock;
            try {
                deliveryStock = em.getReference(DeliveryStock.class, id);
                deliveryStock.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deliveryStock with id " + id + " no longer exists.", enfe);
            }
            Deliveries deliveryId = deliveryStock.getDeliveryId();
            if (deliveryId != null) {
                deliveryId.getDeliveryStockCollection().remove(deliveryStock);
                deliveryId = em.merge(deliveryId);
            }
            Products productId = deliveryStock.getProductId();
            if (productId != null) {
                productId.getDeliveryStockCollection().remove(deliveryStock);
                productId = em.merge(productId);
            }
            em.remove(deliveryStock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DeliveryStock> findDeliveryStockEntities() {
        return findDeliveryStockEntities(true, -1, -1);
    }

    public List<DeliveryStock> findDeliveryStockEntities(int maxResults, int firstResult) {
        return findDeliveryStockEntities(false, maxResults, firstResult);
    }

    private List<DeliveryStock> findDeliveryStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DeliveryStock.class));
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

    public DeliveryStock findDeliveryStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DeliveryStock.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeliveryStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DeliveryStock> rt = cq.from(DeliveryStock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
