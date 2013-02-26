/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Orderlines;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class OrderlinesJpaController implements Serializable {

    public OrderlinesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderlines orderlines) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products productId = orderlines.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                orderlines.setProductId(productId);
            }
            Orders orderId = orderlines.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                orderlines.setOrderId(orderId);
            }
            em.persist(orderlines);
            
            if (orderId != null) {
                orderId.getOrderlinesCollection().add(orderlines);
                orderId = em.merge(orderId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderlines orderlines) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderlines persistentOrderlines = em.find(Orderlines.class, orderlines.getId());
            Products productIdOld = persistentOrderlines.getProductId();
            Products productIdNew = orderlines.getProductId();
            Orders orderIdOld = persistentOrderlines.getOrderId();
            Orders orderIdNew = orderlines.getOrderId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                orderlines.setProductId(productIdNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                orderlines.setOrderId(orderIdNew);
            }
            orderlines = em.merge(orderlines);
            
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderlinesCollection().remove(orderlines);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderlinesCollection().add(orderlines);
                orderIdNew = em.merge(orderIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderlines.getId();
                if (findOrderlines(id) == null) {
                    throw new NonexistentEntityException("The orderlines with id " + id + " no longer exists.");
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
            Orderlines orderlines;
            try {
                orderlines = em.getReference(Orderlines.class, id);
                orderlines.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderlines with id " + id + " no longer exists.", enfe);
            }
            
            Orders orderId = orderlines.getOrderId();
            if (orderId != null) {
                orderId.getOrderlinesCollection().remove(orderlines);
                orderId = em.merge(orderId);
            }
            em.remove(orderlines);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderlines> findOrderlinesEntities() {
        return findOrderlinesEntities(true, -1, -1);
    }

    public List<Orderlines> findOrderlinesEntities(int maxResults, int firstResult) {
        return findOrderlinesEntities(false, maxResults, firstResult);
    }

    private List<Orderlines> findOrderlinesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderlines.class));
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

    public Orderlines findOrderlines(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderlines.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderlinesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderlines> rt = cq.from(Orderlines.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
