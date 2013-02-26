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
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Ordertypes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class OrdertypesJpaController implements Serializable {

    public OrdertypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordertypes ordertypes) {
        if (ordertypes.getOrdersCollection() == null) {
            ordertypes.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : ordertypes.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            ordertypes.setOrdersCollection(attachedOrdersCollection);
            em.persist(ordertypes);
            for (Orders ordersCollectionOrders : ordertypes.getOrdersCollection()) {
                Ordertypes oldOrdertypeIdOfOrdersCollectionOrders = ordersCollectionOrders.getOrdertypeId();
                ordersCollectionOrders.setOrdertypeId(ordertypes);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldOrdertypeIdOfOrdersCollectionOrders != null) {
                    oldOrdertypeIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldOrdertypeIdOfOrdersCollectionOrders = em.merge(oldOrdertypeIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordertypes ordertypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordertypes persistentOrdertypes = em.find(Ordertypes.class, ordertypes.getId());
            Collection<Orders> ordersCollectionOld = persistentOrdertypes.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = ordertypes.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its ordertypeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            ordertypes.setOrdersCollection(ordersCollectionNew);
            ordertypes = em.merge(ordertypes);
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Ordertypes oldOrdertypeIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getOrdertypeId();
                    ordersCollectionNewOrders.setOrdertypeId(ordertypes);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldOrdertypeIdOfOrdersCollectionNewOrders != null && !oldOrdertypeIdOfOrdersCollectionNewOrders.equals(ordertypes)) {
                        oldOrdertypeIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldOrdertypeIdOfOrdersCollectionNewOrders = em.merge(oldOrdertypeIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordertypes.getId();
                if (findOrdertypes(id) == null) {
                    throw new NonexistentEntityException("The ordertypes with id " + id + " no longer exists.");
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
            Ordertypes ordertypes;
            try {
                ordertypes = em.getReference(Ordertypes.class, id);
                ordertypes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordertypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orders> ordersCollectionOrphanCheck = ordertypes.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ordertypes (" + ordertypes + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable ordertypeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ordertypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordertypes> findOrdertypesEntities() {
        return findOrdertypesEntities(true, -1, -1);
    }

    public List<Ordertypes> findOrdertypesEntities(int maxResults, int firstResult) {
        return findOrdertypesEntities(false, maxResults, firstResult);
    }

    private List<Ordertypes> findOrdertypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordertypes.class));
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

    public Ordertypes findOrdertypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordertypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdertypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordertypes> rt = cq.from(Ordertypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
