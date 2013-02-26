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
import com.productosur.hive.entities.Orderstaterecords;
import com.productosur.hive.entities.Orderstates;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class OrderstatesJpaController implements Serializable {

    public OrderstatesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderstates orderstates) {
        if (orderstates.getOrderstaterecordsCollection() == null) {
            orderstates.setOrderstaterecordsCollection(new ArrayList<Orderstaterecords>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Orderstaterecords> attachedOrderstaterecordsCollection = new ArrayList<Orderstaterecords>();
            for (Orderstaterecords orderstaterecordsCollectionOrderstaterecordsToAttach : orderstates.getOrderstaterecordsCollection()) {
                orderstaterecordsCollectionOrderstaterecordsToAttach = em.getReference(orderstaterecordsCollectionOrderstaterecordsToAttach.getClass(), orderstaterecordsCollectionOrderstaterecordsToAttach.getId());
                attachedOrderstaterecordsCollection.add(orderstaterecordsCollectionOrderstaterecordsToAttach);
            }
            orderstates.setOrderstaterecordsCollection(attachedOrderstaterecordsCollection);
            em.persist(orderstates);
            for (Orderstaterecords orderstaterecordsCollectionOrderstaterecords : orderstates.getOrderstaterecordsCollection()) {
                Orderstates oldOrderstateIdOfOrderstaterecordsCollectionOrderstaterecords = orderstaterecordsCollectionOrderstaterecords.getOrderstateId();
                orderstaterecordsCollectionOrderstaterecords.setOrderstateId(orderstates);
                orderstaterecordsCollectionOrderstaterecords = em.merge(orderstaterecordsCollectionOrderstaterecords);
                if (oldOrderstateIdOfOrderstaterecordsCollectionOrderstaterecords != null) {
                    oldOrderstateIdOfOrderstaterecordsCollectionOrderstaterecords.getOrderstaterecordsCollection().remove(orderstaterecordsCollectionOrderstaterecords);
                    oldOrderstateIdOfOrderstaterecordsCollectionOrderstaterecords = em.merge(oldOrderstateIdOfOrderstaterecordsCollectionOrderstaterecords);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderstates orderstates) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderstates persistentOrderstates = em.find(Orderstates.class, orderstates.getId());
            Collection<Orderstaterecords> orderstaterecordsCollectionOld = persistentOrderstates.getOrderstaterecordsCollection();
            Collection<Orderstaterecords> orderstaterecordsCollectionNew = orderstates.getOrderstaterecordsCollection();
            List<String> illegalOrphanMessages = null;
            for (Orderstaterecords orderstaterecordsCollectionOldOrderstaterecords : orderstaterecordsCollectionOld) {
                if (!orderstaterecordsCollectionNew.contains(orderstaterecordsCollectionOldOrderstaterecords)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderstaterecords " + orderstaterecordsCollectionOldOrderstaterecords + " since its orderstateId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Orderstaterecords> attachedOrderstaterecordsCollectionNew = new ArrayList<Orderstaterecords>();
            for (Orderstaterecords orderstaterecordsCollectionNewOrderstaterecordsToAttach : orderstaterecordsCollectionNew) {
                orderstaterecordsCollectionNewOrderstaterecordsToAttach = em.getReference(orderstaterecordsCollectionNewOrderstaterecordsToAttach.getClass(), orderstaterecordsCollectionNewOrderstaterecordsToAttach.getId());
                attachedOrderstaterecordsCollectionNew.add(orderstaterecordsCollectionNewOrderstaterecordsToAttach);
            }
            orderstaterecordsCollectionNew = attachedOrderstaterecordsCollectionNew;
            orderstates.setOrderstaterecordsCollection(orderstaterecordsCollectionNew);
            orderstates = em.merge(orderstates);
            for (Orderstaterecords orderstaterecordsCollectionNewOrderstaterecords : orderstaterecordsCollectionNew) {
                if (!orderstaterecordsCollectionOld.contains(orderstaterecordsCollectionNewOrderstaterecords)) {
                    Orderstates oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords = orderstaterecordsCollectionNewOrderstaterecords.getOrderstateId();
                    orderstaterecordsCollectionNewOrderstaterecords.setOrderstateId(orderstates);
                    orderstaterecordsCollectionNewOrderstaterecords = em.merge(orderstaterecordsCollectionNewOrderstaterecords);
                    if (oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords != null && !oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords.equals(orderstates)) {
                        oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords.getOrderstaterecordsCollection().remove(orderstaterecordsCollectionNewOrderstaterecords);
                        oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords = em.merge(oldOrderstateIdOfOrderstaterecordsCollectionNewOrderstaterecords);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderstates.getId();
                if (findOrderstates(id) == null) {
                    throw new NonexistentEntityException("The orderstates with id " + id + " no longer exists.");
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
            Orderstates orderstates;
            try {
                orderstates = em.getReference(Orderstates.class, id);
                orderstates.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderstates with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orderstaterecords> orderstaterecordsCollectionOrphanCheck = orderstates.getOrderstaterecordsCollection();
            for (Orderstaterecords orderstaterecordsCollectionOrphanCheckOrderstaterecords : orderstaterecordsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orderstates (" + orderstates + ") cannot be destroyed since the Orderstaterecords " + orderstaterecordsCollectionOrphanCheckOrderstaterecords + " in its orderstaterecordsCollection field has a non-nullable orderstateId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(orderstates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderstates> findOrderstatesEntities() {
        return findOrderstatesEntities(true, -1, -1);
    }

    public List<Orderstates> findOrderstatesEntities(int maxResults, int firstResult) {
        return findOrderstatesEntities(false, maxResults, firstResult);
    }

    private List<Orderstates> findOrderstatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderstates.class));
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

    public Orderstates findOrderstates(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderstates.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderstatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderstates> rt = cq.from(Orderstates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
