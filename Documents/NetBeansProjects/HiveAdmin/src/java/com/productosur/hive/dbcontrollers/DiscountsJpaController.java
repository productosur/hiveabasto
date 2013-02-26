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
import com.productosur.hive.entities.Paymentformats;
import com.productosur.hive.entities.Clients;
import com.productosur.hive.entities.Discounts;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class DiscountsJpaController implements Serializable {

    public DiscountsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Discounts discounts) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paymentformats paymentformatId = discounts.getPaymentformatId();
            if (paymentformatId != null) {
                paymentformatId = em.getReference(paymentformatId.getClass(), paymentformatId.getId());
                discounts.setPaymentformatId(paymentformatId);
            }
            Clients clientId = discounts.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                discounts.setClientId(clientId);
            }
            em.persist(discounts);
            if (paymentformatId != null) {
                paymentformatId.getDiscountsCollection().add(discounts);
                paymentformatId = em.merge(paymentformatId);
            }
            
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discounts discounts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discounts persistentDiscounts = em.find(Discounts.class, discounts.getId());
            Paymentformats paymentformatIdOld = persistentDiscounts.getPaymentformatId();
            Paymentformats paymentformatIdNew = discounts.getPaymentformatId();
            Clients clientIdOld = persistentDiscounts.getClientId();
            Clients clientIdNew = discounts.getClientId();
            if (paymentformatIdNew != null) {
                paymentformatIdNew = em.getReference(paymentformatIdNew.getClass(), paymentformatIdNew.getId());
                discounts.setPaymentformatId(paymentformatIdNew);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                discounts.setClientId(clientIdNew);
            }
            discounts = em.merge(discounts);
            if (paymentformatIdOld != null && !paymentformatIdOld.equals(paymentformatIdNew)) {
                paymentformatIdOld.getDiscountsCollection().remove(discounts);
                paymentformatIdOld = em.merge(paymentformatIdOld);
            }
            if (paymentformatIdNew != null && !paymentformatIdNew.equals(paymentformatIdOld)) {
                paymentformatIdNew.getDiscountsCollection().add(discounts);
                paymentformatIdNew = em.merge(paymentformatIdNew);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discounts.getId();
                if (findDiscounts(id) == null) {
                    throw new NonexistentEntityException("The discounts with id " + id + " no longer exists.");
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
            Discounts discounts;
            try {
                discounts = em.getReference(Discounts.class, id);
                discounts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discounts with id " + id + " no longer exists.", enfe);
            }
            Paymentformats paymentformatId = discounts.getPaymentformatId();
            if (paymentformatId != null) {
                paymentformatId.getDiscountsCollection().remove(discounts);
                paymentformatId = em.merge(paymentformatId);
            }
            Clients clientId = discounts.getClientId();
           
            em.remove(discounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discounts> findDiscountsEntities() {
        return findDiscountsEntities(true, -1, -1);
    }

    public List<Discounts> findDiscountsEntities(int maxResults, int firstResult) {
        return findDiscountsEntities(false, maxResults, firstResult);
    }

    private List<Discounts> findDiscountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discounts.class));
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

    public Discounts findDiscounts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discounts> rt = cq.from(Discounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
