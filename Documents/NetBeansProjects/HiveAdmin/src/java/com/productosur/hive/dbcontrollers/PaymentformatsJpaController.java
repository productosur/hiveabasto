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
import com.productosur.hive.entities.Discounts;
import com.productosur.hive.entities.Paymentformats;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PaymentformatsJpaController implements Serializable {

    public PaymentformatsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paymentformats paymentformats) {
        if (paymentformats.getDiscountsCollection() == null) {
            paymentformats.setDiscountsCollection(new ArrayList<Discounts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Discounts> attachedDiscountsCollection = new ArrayList<Discounts>();
            for (Discounts discountsCollectionDiscountsToAttach : paymentformats.getDiscountsCollection()) {
                discountsCollectionDiscountsToAttach = em.getReference(discountsCollectionDiscountsToAttach.getClass(), discountsCollectionDiscountsToAttach.getId());
                attachedDiscountsCollection.add(discountsCollectionDiscountsToAttach);
            }
            paymentformats.setDiscountsCollection(attachedDiscountsCollection);
            em.persist(paymentformats);
            for (Discounts discountsCollectionDiscounts : paymentformats.getDiscountsCollection()) {
                Paymentformats oldPaymentformatIdOfDiscountsCollectionDiscounts = discountsCollectionDiscounts.getPaymentformatId();
                discountsCollectionDiscounts.setPaymentformatId(paymentformats);
                discountsCollectionDiscounts = em.merge(discountsCollectionDiscounts);
                if (oldPaymentformatIdOfDiscountsCollectionDiscounts != null) {
                    oldPaymentformatIdOfDiscountsCollectionDiscounts.getDiscountsCollection().remove(discountsCollectionDiscounts);
                    oldPaymentformatIdOfDiscountsCollectionDiscounts = em.merge(oldPaymentformatIdOfDiscountsCollectionDiscounts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paymentformats paymentformats) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paymentformats persistentPaymentformats = em.find(Paymentformats.class, paymentformats.getId());
            Collection<Discounts> discountsCollectionOld = persistentPaymentformats.getDiscountsCollection();
            Collection<Discounts> discountsCollectionNew = paymentformats.getDiscountsCollection();
            List<String> illegalOrphanMessages = null;
            for (Discounts discountsCollectionOldDiscounts : discountsCollectionOld) {
                if (!discountsCollectionNew.contains(discountsCollectionOldDiscounts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discounts " + discountsCollectionOldDiscounts + " since its paymentformatId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Discounts> attachedDiscountsCollectionNew = new ArrayList<Discounts>();
            for (Discounts discountsCollectionNewDiscountsToAttach : discountsCollectionNew) {
                discountsCollectionNewDiscountsToAttach = em.getReference(discountsCollectionNewDiscountsToAttach.getClass(), discountsCollectionNewDiscountsToAttach.getId());
                attachedDiscountsCollectionNew.add(discountsCollectionNewDiscountsToAttach);
            }
            discountsCollectionNew = attachedDiscountsCollectionNew;
            paymentformats.setDiscountsCollection(discountsCollectionNew);
            paymentformats = em.merge(paymentformats);
            for (Discounts discountsCollectionNewDiscounts : discountsCollectionNew) {
                if (!discountsCollectionOld.contains(discountsCollectionNewDiscounts)) {
                    Paymentformats oldPaymentformatIdOfDiscountsCollectionNewDiscounts = discountsCollectionNewDiscounts.getPaymentformatId();
                    discountsCollectionNewDiscounts.setPaymentformatId(paymentformats);
                    discountsCollectionNewDiscounts = em.merge(discountsCollectionNewDiscounts);
                    if (oldPaymentformatIdOfDiscountsCollectionNewDiscounts != null && !oldPaymentformatIdOfDiscountsCollectionNewDiscounts.equals(paymentformats)) {
                        oldPaymentformatIdOfDiscountsCollectionNewDiscounts.getDiscountsCollection().remove(discountsCollectionNewDiscounts);
                        oldPaymentformatIdOfDiscountsCollectionNewDiscounts = em.merge(oldPaymentformatIdOfDiscountsCollectionNewDiscounts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paymentformats.getId();
                if (findPaymentformats(id) == null) {
                    throw new NonexistentEntityException("The paymentformats with id " + id + " no longer exists.");
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
            Paymentformats paymentformats;
            try {
                paymentformats = em.getReference(Paymentformats.class, id);
                paymentformats.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paymentformats with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Discounts> discountsCollectionOrphanCheck = paymentformats.getDiscountsCollection();
            for (Discounts discountsCollectionOrphanCheckDiscounts : discountsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paymentformats (" + paymentformats + ") cannot be destroyed since the Discounts " + discountsCollectionOrphanCheckDiscounts + " in its discountsCollection field has a non-nullable paymentformatId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(paymentformats);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paymentformats> findPaymentformatsEntities() {
        return findPaymentformatsEntities(true, -1, -1);
    }

    public List<Paymentformats> findPaymentformatsEntities(int maxResults, int firstResult) {
        return findPaymentformatsEntities(false, maxResults, firstResult);
    }

    private List<Paymentformats> findPaymentformatsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paymentformats.class));
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

    public Paymentformats findPaymentformats(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paymentformats.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentformatsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paymentformats> rt = cq.from(Paymentformats.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
