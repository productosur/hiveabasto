/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class SubsidiariesJpaController implements Serializable {

    public SubsidiariesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subsidiaries subsidiaries) {
        if (subsidiaries.getDailyRoutesCollection() == null) {
            subsidiaries.setDailyRoutesCollection(new ArrayList<DailyRoutes>());
        }
        if (subsidiaries.getOrdersCollection() == null) {
            subsidiaries.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientId = subsidiaries.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                subsidiaries.setClientId(clientId);
            }
            Collection<DailyRoutes> attachedDailyRoutesCollection = new ArrayList<DailyRoutes>();
            for (DailyRoutes dailyRoutesCollectionDailyRoutesToAttach : subsidiaries.getDailyRoutesCollection()) {
                dailyRoutesCollectionDailyRoutesToAttach = em.getReference(dailyRoutesCollectionDailyRoutesToAttach.getClass(), dailyRoutesCollectionDailyRoutesToAttach.getId());
                attachedDailyRoutesCollection.add(dailyRoutesCollectionDailyRoutesToAttach);
            }
            subsidiaries.setDailyRoutesCollection(attachedDailyRoutesCollection);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : subsidiaries.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            subsidiaries.setOrdersCollection(attachedOrdersCollection);
            em.persist(subsidiaries);
            if (clientId != null) {
                clientId.getSubsidiariesCollection().add(subsidiaries);
                clientId = em.merge(clientId);
            }
            for (DailyRoutes dailyRoutesCollectionDailyRoutes : subsidiaries.getDailyRoutesCollection()) {
                Subsidiaries oldSubsidiaryIdOfDailyRoutesCollectionDailyRoutes = dailyRoutesCollectionDailyRoutes.getSubsidiaryId();
                dailyRoutesCollectionDailyRoutes.setSubsidiaryId(subsidiaries);
                dailyRoutesCollectionDailyRoutes = em.merge(dailyRoutesCollectionDailyRoutes);
                if (oldSubsidiaryIdOfDailyRoutesCollectionDailyRoutes != null) {
                    oldSubsidiaryIdOfDailyRoutesCollectionDailyRoutes.getDailyRoutesCollection().remove(dailyRoutesCollectionDailyRoutes);
                    oldSubsidiaryIdOfDailyRoutesCollectionDailyRoutes = em.merge(oldSubsidiaryIdOfDailyRoutesCollectionDailyRoutes);
                }
            }
            for (Orders ordersCollectionOrders : subsidiaries.getOrdersCollection()) {
                Subsidiaries oldSubsidiaryIdOfOrdersCollectionOrders = ordersCollectionOrders.getSubsidiaryId();
                ordersCollectionOrders.setSubsidiaryId(subsidiaries);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldSubsidiaryIdOfOrdersCollectionOrders != null) {
                    oldSubsidiaryIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldSubsidiaryIdOfOrdersCollectionOrders = em.merge(oldSubsidiaryIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subsidiaries subsidiaries) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subsidiaries persistentSubsidiaries = em.find(Subsidiaries.class, subsidiaries.getId());
            Clients clientIdOld = persistentSubsidiaries.getClientId();
            Clients clientIdNew = subsidiaries.getClientId();
            Collection<DailyRoutes> dailyRoutesCollectionOld = persistentSubsidiaries.getDailyRoutesCollection();
            Collection<DailyRoutes> dailyRoutesCollectionNew = subsidiaries.getDailyRoutesCollection();
            Collection<Orders> ordersCollectionOld = persistentSubsidiaries.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = subsidiaries.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (DailyRoutes dailyRoutesCollectionOldDailyRoutes : dailyRoutesCollectionOld) {
                if (!dailyRoutesCollectionNew.contains(dailyRoutesCollectionOldDailyRoutes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyRoutes " + dailyRoutesCollectionOldDailyRoutes + " since its subsidiaryId field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its subsidiaryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                subsidiaries.setClientId(clientIdNew);
            }
            Collection<DailyRoutes> attachedDailyRoutesCollectionNew = new ArrayList<DailyRoutes>();
            for (DailyRoutes dailyRoutesCollectionNewDailyRoutesToAttach : dailyRoutesCollectionNew) {
                dailyRoutesCollectionNewDailyRoutesToAttach = em.getReference(dailyRoutesCollectionNewDailyRoutesToAttach.getClass(), dailyRoutesCollectionNewDailyRoutesToAttach.getId());
                attachedDailyRoutesCollectionNew.add(dailyRoutesCollectionNewDailyRoutesToAttach);
            }
            dailyRoutesCollectionNew = attachedDailyRoutesCollectionNew;
            subsidiaries.setDailyRoutesCollection(dailyRoutesCollectionNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            subsidiaries.setOrdersCollection(ordersCollectionNew);
            subsidiaries = em.merge(subsidiaries);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getSubsidiariesCollection().remove(subsidiaries);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getSubsidiariesCollection().add(subsidiaries);
                clientIdNew = em.merge(clientIdNew);
            }
            for (DailyRoutes dailyRoutesCollectionNewDailyRoutes : dailyRoutesCollectionNew) {
                if (!dailyRoutesCollectionOld.contains(dailyRoutesCollectionNewDailyRoutes)) {
                    Subsidiaries oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes = dailyRoutesCollectionNewDailyRoutes.getSubsidiaryId();
                    dailyRoutesCollectionNewDailyRoutes.setSubsidiaryId(subsidiaries);
                    dailyRoutesCollectionNewDailyRoutes = em.merge(dailyRoutesCollectionNewDailyRoutes);
                    if (oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes != null && !oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes.equals(subsidiaries)) {
                        oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes.getDailyRoutesCollection().remove(dailyRoutesCollectionNewDailyRoutes);
                        oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes = em.merge(oldSubsidiaryIdOfDailyRoutesCollectionNewDailyRoutes);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Subsidiaries oldSubsidiaryIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getSubsidiaryId();
                    ordersCollectionNewOrders.setSubsidiaryId(subsidiaries);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldSubsidiaryIdOfOrdersCollectionNewOrders != null && !oldSubsidiaryIdOfOrdersCollectionNewOrders.equals(subsidiaries)) {
                        oldSubsidiaryIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldSubsidiaryIdOfOrdersCollectionNewOrders = em.merge(oldSubsidiaryIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subsidiaries.getId();
                if (findSubsidiaries(id) == null) {
                    throw new NonexistentEntityException("The subsidiaries with id " + id + " no longer exists.");
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
            Subsidiaries subsidiaries;
            try {
                subsidiaries = em.getReference(Subsidiaries.class, id);
                subsidiaries.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subsidiaries with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DailyRoutes> dailyRoutesCollectionOrphanCheck = subsidiaries.getDailyRoutesCollection();
            for (DailyRoutes dailyRoutesCollectionOrphanCheckDailyRoutes : dailyRoutesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subsidiaries (" + subsidiaries + ") cannot be destroyed since the DailyRoutes " + dailyRoutesCollectionOrphanCheckDailyRoutes + " in its dailyRoutesCollection field has a non-nullable subsidiaryId field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = subsidiaries.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subsidiaries (" + subsidiaries + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable subsidiaryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clients clientId = subsidiaries.getClientId();
            if (clientId != null) {
                clientId.getSubsidiariesCollection().remove(subsidiaries);
                clientId = em.merge(clientId);
            }
            em.remove(subsidiaries);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subsidiaries> findSubsidiariesEntities() {
        return findSubsidiariesEntities(true, -1, -1);
    }

    public List<Subsidiaries> findSubsidiariesEntities(int maxResults, int firstResult) {
        return findSubsidiariesEntities(false, maxResults, firstResult);
    }

    private List<Subsidiaries> findSubsidiariesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subsidiaries.class));
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

    public Subsidiaries findSubsidiaries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subsidiaries.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubsidiariesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subsidiaries> rt = cq.from(Subsidiaries.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
