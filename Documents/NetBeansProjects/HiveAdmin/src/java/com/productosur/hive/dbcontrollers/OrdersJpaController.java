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
import com.productosur.hive.entities.Subsidiaries;
import com.productosur.hive.entities.Ordertypes;
import com.productosur.hive.entities.Employees;
import com.productosur.hive.entities.Paydocuments;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.Orderlines;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Orderstaterecords;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) {
        if (orders.getPaydocumentsCollection() == null) {
            orders.setPaydocumentsCollection(new ArrayList<Paydocuments>());
        }
        if (orders.getOrderlinesCollection() == null) {
            orders.setOrderlinesCollection(new ArrayList<Orderlines>());
        }
        if (orders.getOrderstaterecordsCollection() == null) {
            orders.setOrderstaterecordsCollection(new ArrayList<Orderstaterecords>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subsidiaries subsidiaryId = orders.getSubsidiaryId();
            if (subsidiaryId != null) {
                subsidiaryId = em.getReference(subsidiaryId.getClass(), subsidiaryId.getId());
                orders.setSubsidiaryId(subsidiaryId);
            }
            Ordertypes ordertypeId = orders.getOrdertypeId();
            if (ordertypeId != null) {
                ordertypeId = em.getReference(ordertypeId.getClass(), ordertypeId.getId());
                orders.setOrdertypeId(ordertypeId);
            }
            Employees employeeId = orders.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getId());
                orders.setEmployeeId(employeeId);
            }
            Collection<Paydocuments> attachedPaydocumentsCollection = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionPaydocumentsToAttach : orders.getPaydocumentsCollection()) {
                paydocumentsCollectionPaydocumentsToAttach = em.getReference(paydocumentsCollectionPaydocumentsToAttach.getClass(), paydocumentsCollectionPaydocumentsToAttach.getId());
                attachedPaydocumentsCollection.add(paydocumentsCollectionPaydocumentsToAttach);
            }
            orders.setPaydocumentsCollection(attachedPaydocumentsCollection);
            Collection<Orderlines> attachedOrderlinesCollection = new ArrayList<Orderlines>();
            for (Orderlines orderlinesCollectionOrderlinesToAttach : orders.getOrderlinesCollection()) {
                orderlinesCollectionOrderlinesToAttach = em.getReference(orderlinesCollectionOrderlinesToAttach.getClass(), orderlinesCollectionOrderlinesToAttach.getId());
                attachedOrderlinesCollection.add(orderlinesCollectionOrderlinesToAttach);
            }
            orders.setOrderlinesCollection(attachedOrderlinesCollection);
            Collection<Orderstaterecords> attachedOrderstaterecordsCollection = new ArrayList<Orderstaterecords>();
            for (Orderstaterecords orderstaterecordsCollectionOrderstaterecordsToAttach : orders.getOrderstaterecordsCollection()) {
                orderstaterecordsCollectionOrderstaterecordsToAttach = em.getReference(orderstaterecordsCollectionOrderstaterecordsToAttach.getClass(), orderstaterecordsCollectionOrderstaterecordsToAttach.getId());
                attachedOrderstaterecordsCollection.add(orderstaterecordsCollectionOrderstaterecordsToAttach);
            }
            orders.setOrderstaterecordsCollection(attachedOrderstaterecordsCollection);
            em.persist(orders);
            if (subsidiaryId != null) {
                subsidiaryId.getOrdersCollection().add(orders);
                subsidiaryId = em.merge(subsidiaryId);
            }
            if (ordertypeId != null) {
                ordertypeId.getOrdersCollection().add(orders);
                ordertypeId = em.merge(ordertypeId);
            }
            if (employeeId != null) {
                employeeId.getOrdersCollection().add(orders);
                employeeId = em.merge(employeeId);
            }
            for (Paydocuments paydocumentsCollectionPaydocuments : orders.getPaydocumentsCollection()) {
                Orders oldOrderIdOfPaydocumentsCollectionPaydocuments = paydocumentsCollectionPaydocuments.getOrderId();
                paydocumentsCollectionPaydocuments.setOrderId(orders);
                paydocumentsCollectionPaydocuments = em.merge(paydocumentsCollectionPaydocuments);
                if (oldOrderIdOfPaydocumentsCollectionPaydocuments != null) {
                    oldOrderIdOfPaydocumentsCollectionPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionPaydocuments);
                    oldOrderIdOfPaydocumentsCollectionPaydocuments = em.merge(oldOrderIdOfPaydocumentsCollectionPaydocuments);
                }
            }
            for (Orderlines orderlinesCollectionOrderlines : orders.getOrderlinesCollection()) {
                Orders oldOrderIdOfOrderlinesCollectionOrderlines = orderlinesCollectionOrderlines.getOrderId();
                orderlinesCollectionOrderlines.setOrderId(orders);
                orderlinesCollectionOrderlines = em.merge(orderlinesCollectionOrderlines);
                if (oldOrderIdOfOrderlinesCollectionOrderlines != null) {
                    oldOrderIdOfOrderlinesCollectionOrderlines.getOrderlinesCollection().remove(orderlinesCollectionOrderlines);
                    oldOrderIdOfOrderlinesCollectionOrderlines = em.merge(oldOrderIdOfOrderlinesCollectionOrderlines);
                }
            }
            for (Orderstaterecords orderstaterecordsCollectionOrderstaterecords : orders.getOrderstaterecordsCollection()) {
                Orders oldOrderIdOfOrderstaterecordsCollectionOrderstaterecords = orderstaterecordsCollectionOrderstaterecords.getOrderId();
                orderstaterecordsCollectionOrderstaterecords.setOrderId(orders);
                orderstaterecordsCollectionOrderstaterecords = em.merge(orderstaterecordsCollectionOrderstaterecords);
                if (oldOrderIdOfOrderstaterecordsCollectionOrderstaterecords != null) {
                    oldOrderIdOfOrderstaterecordsCollectionOrderstaterecords.getOrderstaterecordsCollection().remove(orderstaterecordsCollectionOrderstaterecords);
                    oldOrderIdOfOrderstaterecordsCollectionOrderstaterecords = em.merge(oldOrderIdOfOrderstaterecordsCollectionOrderstaterecords);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getId());
            Subsidiaries subsidiaryIdOld = persistentOrders.getSubsidiaryId();
            Subsidiaries subsidiaryIdNew = orders.getSubsidiaryId();
            Ordertypes ordertypeIdOld = persistentOrders.getOrdertypeId();
            Ordertypes ordertypeIdNew = orders.getOrdertypeId();
            Employees employeeIdOld = persistentOrders.getEmployeeId();
            Employees employeeIdNew = orders.getEmployeeId();
            Collection<Paydocuments> paydocumentsCollectionOld = persistentOrders.getPaydocumentsCollection();
            Collection<Paydocuments> paydocumentsCollectionNew = orders.getPaydocumentsCollection();
            Collection<Orderlines> orderlinesCollectionOld = persistentOrders.getOrderlinesCollection();
            Collection<Orderlines> orderlinesCollectionNew = orders.getOrderlinesCollection();
            Collection<Orderstaterecords> orderstaterecordsCollectionOld = persistentOrders.getOrderstaterecordsCollection();
            Collection<Orderstaterecords> orderstaterecordsCollectionNew = orders.getOrderstaterecordsCollection();
            List<String> illegalOrphanMessages = null;
            for (Paydocuments paydocumentsCollectionOldPaydocuments : paydocumentsCollectionOld) {
                if (!paydocumentsCollectionNew.contains(paydocumentsCollectionOldPaydocuments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paydocuments " + paydocumentsCollectionOldPaydocuments + " since its orderId field is not nullable.");
                }
            }
            for (Orderlines orderlinesCollectionOldOrderlines : orderlinesCollectionOld) {
                if (!orderlinesCollectionNew.contains(orderlinesCollectionOldOrderlines)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderlines " + orderlinesCollectionOldOrderlines + " since its orderId field is not nullable.");
                }
            }
            for (Orderstaterecords orderstaterecordsCollectionOldOrderstaterecords : orderstaterecordsCollectionOld) {
                if (!orderstaterecordsCollectionNew.contains(orderstaterecordsCollectionOldOrderstaterecords)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderstaterecords " + orderstaterecordsCollectionOldOrderstaterecords + " since its orderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (subsidiaryIdNew != null) {
                subsidiaryIdNew = em.getReference(subsidiaryIdNew.getClass(), subsidiaryIdNew.getId());
                orders.setSubsidiaryId(subsidiaryIdNew);
            }
            if (ordertypeIdNew != null) {
                ordertypeIdNew = em.getReference(ordertypeIdNew.getClass(), ordertypeIdNew.getId());
                orders.setOrdertypeId(ordertypeIdNew);
            }
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getId());
                orders.setEmployeeId(employeeIdNew);
            }
            Collection<Paydocuments> attachedPaydocumentsCollectionNew = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionNewPaydocumentsToAttach : paydocumentsCollectionNew) {
                paydocumentsCollectionNewPaydocumentsToAttach = em.getReference(paydocumentsCollectionNewPaydocumentsToAttach.getClass(), paydocumentsCollectionNewPaydocumentsToAttach.getId());
                attachedPaydocumentsCollectionNew.add(paydocumentsCollectionNewPaydocumentsToAttach);
            }
            paydocumentsCollectionNew = attachedPaydocumentsCollectionNew;
            orders.setPaydocumentsCollection(paydocumentsCollectionNew);
            Collection<Orderlines> attachedOrderlinesCollectionNew = new ArrayList<Orderlines>();
            for (Orderlines orderlinesCollectionNewOrderlinesToAttach : orderlinesCollectionNew) {
                orderlinesCollectionNewOrderlinesToAttach = em.getReference(orderlinesCollectionNewOrderlinesToAttach.getClass(), orderlinesCollectionNewOrderlinesToAttach.getId());
                attachedOrderlinesCollectionNew.add(orderlinesCollectionNewOrderlinesToAttach);
            }
            orderlinesCollectionNew = attachedOrderlinesCollectionNew;
            orders.setOrderlinesCollection(orderlinesCollectionNew);
            Collection<Orderstaterecords> attachedOrderstaterecordsCollectionNew = new ArrayList<Orderstaterecords>();
            for (Orderstaterecords orderstaterecordsCollectionNewOrderstaterecordsToAttach : orderstaterecordsCollectionNew) {
                orderstaterecordsCollectionNewOrderstaterecordsToAttach = em.getReference(orderstaterecordsCollectionNewOrderstaterecordsToAttach.getClass(), orderstaterecordsCollectionNewOrderstaterecordsToAttach.getId());
                attachedOrderstaterecordsCollectionNew.add(orderstaterecordsCollectionNewOrderstaterecordsToAttach);
            }
            orderstaterecordsCollectionNew = attachedOrderstaterecordsCollectionNew;
            orders.setOrderstaterecordsCollection(orderstaterecordsCollectionNew);
            orders = em.merge(orders);
            if (subsidiaryIdOld != null && !subsidiaryIdOld.equals(subsidiaryIdNew)) {
                subsidiaryIdOld.getOrdersCollection().remove(orders);
                subsidiaryIdOld = em.merge(subsidiaryIdOld);
            }
            if (subsidiaryIdNew != null && !subsidiaryIdNew.equals(subsidiaryIdOld)) {
                subsidiaryIdNew.getOrdersCollection().add(orders);
                subsidiaryIdNew = em.merge(subsidiaryIdNew);
            }
            if (ordertypeIdOld != null && !ordertypeIdOld.equals(ordertypeIdNew)) {
                ordertypeIdOld.getOrdersCollection().remove(orders);
                ordertypeIdOld = em.merge(ordertypeIdOld);
            }
            if (ordertypeIdNew != null && !ordertypeIdNew.equals(ordertypeIdOld)) {
                ordertypeIdNew.getOrdersCollection().add(orders);
                ordertypeIdNew = em.merge(ordertypeIdNew);
            }
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getOrdersCollection().remove(orders);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getOrdersCollection().add(orders);
                employeeIdNew = em.merge(employeeIdNew);
            }
            for (Paydocuments paydocumentsCollectionNewPaydocuments : paydocumentsCollectionNew) {
                if (!paydocumentsCollectionOld.contains(paydocumentsCollectionNewPaydocuments)) {
                    Orders oldOrderIdOfPaydocumentsCollectionNewPaydocuments = paydocumentsCollectionNewPaydocuments.getOrderId();
                    paydocumentsCollectionNewPaydocuments.setOrderId(orders);
                    paydocumentsCollectionNewPaydocuments = em.merge(paydocumentsCollectionNewPaydocuments);
                    if (oldOrderIdOfPaydocumentsCollectionNewPaydocuments != null && !oldOrderIdOfPaydocumentsCollectionNewPaydocuments.equals(orders)) {
                        oldOrderIdOfPaydocumentsCollectionNewPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionNewPaydocuments);
                        oldOrderIdOfPaydocumentsCollectionNewPaydocuments = em.merge(oldOrderIdOfPaydocumentsCollectionNewPaydocuments);
                    }
                }
            }
            for (Orderlines orderlinesCollectionNewOrderlines : orderlinesCollectionNew) {
                if (!orderlinesCollectionOld.contains(orderlinesCollectionNewOrderlines)) {
                    Orders oldOrderIdOfOrderlinesCollectionNewOrderlines = orderlinesCollectionNewOrderlines.getOrderId();
                    orderlinesCollectionNewOrderlines.setOrderId(orders);
                    orderlinesCollectionNewOrderlines = em.merge(orderlinesCollectionNewOrderlines);
                    if (oldOrderIdOfOrderlinesCollectionNewOrderlines != null && !oldOrderIdOfOrderlinesCollectionNewOrderlines.equals(orders)) {
                        oldOrderIdOfOrderlinesCollectionNewOrderlines.getOrderlinesCollection().remove(orderlinesCollectionNewOrderlines);
                        oldOrderIdOfOrderlinesCollectionNewOrderlines = em.merge(oldOrderIdOfOrderlinesCollectionNewOrderlines);
                    }
                }
            }
            for (Orderstaterecords orderstaterecordsCollectionNewOrderstaterecords : orderstaterecordsCollectionNew) {
                if (!orderstaterecordsCollectionOld.contains(orderstaterecordsCollectionNewOrderstaterecords)) {
                    Orders oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords = orderstaterecordsCollectionNewOrderstaterecords.getOrderId();
                    orderstaterecordsCollectionNewOrderstaterecords.setOrderId(orders);
                    orderstaterecordsCollectionNewOrderstaterecords = em.merge(orderstaterecordsCollectionNewOrderstaterecords);
                    if (oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords != null && !oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords.equals(orders)) {
                        oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords.getOrderstaterecordsCollection().remove(orderstaterecordsCollectionNewOrderstaterecords);
                        oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords = em.merge(oldOrderIdOfOrderstaterecordsCollectionNewOrderstaterecords);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Paydocuments> paydocumentsCollectionOrphanCheck = orders.getPaydocumentsCollection();
            for (Paydocuments paydocumentsCollectionOrphanCheckPaydocuments : paydocumentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Paydocuments " + paydocumentsCollectionOrphanCheckPaydocuments + " in its paydocumentsCollection field has a non-nullable orderId field.");
            }
            Collection<Orderlines> orderlinesCollectionOrphanCheck = orders.getOrderlinesCollection();
            for (Orderlines orderlinesCollectionOrphanCheckOrderlines : orderlinesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Orderlines " + orderlinesCollectionOrphanCheckOrderlines + " in its orderlinesCollection field has a non-nullable orderId field.");
            }
            Collection<Orderstaterecords> orderstaterecordsCollectionOrphanCheck = orders.getOrderstaterecordsCollection();
            for (Orderstaterecords orderstaterecordsCollectionOrphanCheckOrderstaterecords : orderstaterecordsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Orderstaterecords " + orderstaterecordsCollectionOrphanCheckOrderstaterecords + " in its orderstaterecordsCollection field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Subsidiaries subsidiaryId = orders.getSubsidiaryId();
            if (subsidiaryId != null) {
                subsidiaryId.getOrdersCollection().remove(orders);
                subsidiaryId = em.merge(subsidiaryId);
            }
            Ordertypes ordertypeId = orders.getOrdertypeId();
            if (ordertypeId != null) {
                ordertypeId.getOrdersCollection().remove(orders);
                ordertypeId = em.merge(ordertypeId);
            }
            Employees employeeId = orders.getEmployeeId();
            if (employeeId != null) {
                employeeId.getOrdersCollection().remove(orders);
                employeeId = em.merge(employeeId);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Orders> findOrdersEntities(Subsidiaries client) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("Select o from Orders o where o.subsidiaryId=:arg1 and o.active=1");
            q.setParameter("arg1", client);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
     public Orders findActiveOrderEntity(Subsidiaries client) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("Select o from Orders o where o.subsidiaryId=:arg1 and o.active=1");
            q.setParameter("arg1", client);
            return (Orders) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
     public List<Object> findActiveOrdersEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("select p.id, p.name, ol.quantity from products p, orderlines ol, "
                    + "orders o where o.active = 1 and ol.order_id = o.id and ol.product_id = p.id;");
            return q.getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
    }

}
