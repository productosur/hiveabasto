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
import com.productosur.hive.entities.Orderstates;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Orderstaterecords;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class OrderstaterecordsJpaController implements Serializable {

    public OrderstaterecordsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderstaterecords orderstaterecords) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderstates orderstateId = orderstaterecords.getOrderstateId();
            if (orderstateId != null) {
                orderstateId = em.getReference(orderstateId.getClass(), orderstateId.getId());
                orderstaterecords.setOrderstateId(orderstateId);
            }
            Orders orderId = orderstaterecords.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                orderstaterecords.setOrderId(orderId);
            }
            em.persist(orderstaterecords);
            if (orderstateId != null) {
                orderstateId.getOrderstaterecordsCollection().add(orderstaterecords);
                orderstateId = em.merge(orderstateId);
            }
            if (orderId != null) {
                orderId.getOrderstaterecordsCollection().add(orderstaterecords);
                orderId = em.merge(orderId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderstaterecords orderstaterecords) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderstaterecords persistentOrderstaterecords = em.find(Orderstaterecords.class, orderstaterecords.getId());
            Orderstates orderstateIdOld = persistentOrderstaterecords.getOrderstateId();
            Orderstates orderstateIdNew = orderstaterecords.getOrderstateId();
            Orders orderIdOld = persistentOrderstaterecords.getOrderId();
            Orders orderIdNew = orderstaterecords.getOrderId();
            if (orderstateIdNew != null) {
                orderstateIdNew = em.getReference(orderstateIdNew.getClass(), orderstateIdNew.getId());
                orderstaterecords.setOrderstateId(orderstateIdNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                orderstaterecords.setOrderId(orderIdNew);
            }
            orderstaterecords = em.merge(orderstaterecords);
            if (orderstateIdOld != null && !orderstateIdOld.equals(orderstateIdNew)) {
                orderstateIdOld.getOrderstaterecordsCollection().remove(orderstaterecords);
                orderstateIdOld = em.merge(orderstateIdOld);
            }
            if (orderstateIdNew != null && !orderstateIdNew.equals(orderstateIdOld)) {
                orderstateIdNew.getOrderstaterecordsCollection().add(orderstaterecords);
                orderstateIdNew = em.merge(orderstateIdNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderstaterecordsCollection().remove(orderstaterecords);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderstaterecordsCollection().add(orderstaterecords);
                orderIdNew = em.merge(orderIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderstaterecords.getId();
                if (findOrderstaterecords(id) == null) {
                    throw new NonexistentEntityException("The orderstaterecords with id " + id + " no longer exists.");
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
            Orderstaterecords orderstaterecords;
            try {
                orderstaterecords = em.getReference(Orderstaterecords.class, id);
                orderstaterecords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderstaterecords with id " + id + " no longer exists.", enfe);
            }
            Orderstates orderstateId = orderstaterecords.getOrderstateId();
            if (orderstateId != null) {
                orderstateId.getOrderstaterecordsCollection().remove(orderstaterecords);
                orderstateId = em.merge(orderstateId);
            }
            Orders orderId = orderstaterecords.getOrderId();
            if (orderId != null) {
                orderId.getOrderstaterecordsCollection().remove(orderstaterecords);
                orderId = em.merge(orderId);
            }
            em.remove(orderstaterecords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderstaterecords> findOrderstaterecordsEntities() {
        return findOrderstaterecordsEntities(true, -1, -1);
    }

    public List<Orderstaterecords> findOrderstaterecordsEntities(int maxResults, int firstResult) {
        return findOrderstaterecordsEntities(false, maxResults, firstResult);
    }

    private List<Orderstaterecords> findOrderstaterecordsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderstaterecords.class));
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

    public Orderstaterecords findOrderstaterecords(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderstaterecords.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderstaterecordsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderstaterecords> rt = cq.from(Orderstaterecords.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
