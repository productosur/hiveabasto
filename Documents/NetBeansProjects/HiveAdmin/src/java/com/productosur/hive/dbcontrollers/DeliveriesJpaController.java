/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.IllegalOrphanException;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Deliveries;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Trucks;
import com.productosur.hive.entities.DeliveryStock;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.Paydocuments;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class DeliveriesJpaController implements Serializable {

    public DeliveriesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deliveries deliveries) {
        if (deliveries.getDeliveryStockCollection() == null) {
            deliveries.setDeliveryStockCollection(new ArrayList<DeliveryStock>());
        }
        if (deliveries.getPaydocumentsCollection() == null) {
            deliveries.setPaydocumentsCollection(new ArrayList<Paydocuments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trucks truckId = deliveries.getTruckId();
            if (truckId != null) {
                truckId = em.getReference(truckId.getClass(), truckId.getId());
                deliveries.setTruckId(truckId);
            }
            Collection<DeliveryStock> attachedDeliveryStockCollection = new ArrayList<DeliveryStock>();
            for (DeliveryStock deliveryStockCollectionDeliveryStockToAttach : deliveries.getDeliveryStockCollection()) {
                deliveryStockCollectionDeliveryStockToAttach = em.getReference(deliveryStockCollectionDeliveryStockToAttach.getClass(), deliveryStockCollectionDeliveryStockToAttach.getId());
                attachedDeliveryStockCollection.add(deliveryStockCollectionDeliveryStockToAttach);
            }
            deliveries.setDeliveryStockCollection(attachedDeliveryStockCollection);
            Collection<Paydocuments> attachedPaydocumentsCollection = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionPaydocumentsToAttach : deliveries.getPaydocumentsCollection()) {
                paydocumentsCollectionPaydocumentsToAttach = em.getReference(paydocumentsCollectionPaydocumentsToAttach.getClass(), paydocumentsCollectionPaydocumentsToAttach.getId());
                attachedPaydocumentsCollection.add(paydocumentsCollectionPaydocumentsToAttach);
            }
            deliveries.setPaydocumentsCollection(attachedPaydocumentsCollection);
            em.persist(deliveries);
            if (truckId != null) {
                truckId.getDeliveriesCollection().add(deliveries);
                truckId = em.merge(truckId);
            }
            for (DeliveryStock deliveryStockCollectionDeliveryStock : deliveries.getDeliveryStockCollection()) {
                Deliveries oldDeliveryIdOfDeliveryStockCollectionDeliveryStock = deliveryStockCollectionDeliveryStock.getDeliveryId();
                deliveryStockCollectionDeliveryStock.setDeliveryId(deliveries);
                deliveryStockCollectionDeliveryStock = em.merge(deliveryStockCollectionDeliveryStock);
                if (oldDeliveryIdOfDeliveryStockCollectionDeliveryStock != null) {
                    oldDeliveryIdOfDeliveryStockCollectionDeliveryStock.getDeliveryStockCollection().remove(deliveryStockCollectionDeliveryStock);
                    oldDeliveryIdOfDeliveryStockCollectionDeliveryStock = em.merge(oldDeliveryIdOfDeliveryStockCollectionDeliveryStock);
                }
            }
            for (Paydocuments paydocumentsCollectionPaydocuments : deliveries.getPaydocumentsCollection()) {
                Deliveries oldDeliveryIdOfPaydocumentsCollectionPaydocuments = paydocumentsCollectionPaydocuments.getDeliveryId();
                paydocumentsCollectionPaydocuments.setDeliveryId(deliveries);
                paydocumentsCollectionPaydocuments = em.merge(paydocumentsCollectionPaydocuments);
                if (oldDeliveryIdOfPaydocumentsCollectionPaydocuments != null) {
                    oldDeliveryIdOfPaydocumentsCollectionPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionPaydocuments);
                    oldDeliveryIdOfPaydocumentsCollectionPaydocuments = em.merge(oldDeliveryIdOfPaydocumentsCollectionPaydocuments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deliveries deliveries) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deliveries persistentDeliveries = em.find(Deliveries.class, deliveries.getId());
            Trucks truckIdOld = persistentDeliveries.getTruckId();
            Trucks truckIdNew = deliveries.getTruckId();
            Collection<DeliveryStock> deliveryStockCollectionOld = persistentDeliveries.getDeliveryStockCollection();
            Collection<DeliveryStock> deliveryStockCollectionNew = deliveries.getDeliveryStockCollection();
            Collection<Paydocuments> paydocumentsCollectionOld = persistentDeliveries.getPaydocumentsCollection();
            Collection<Paydocuments> paydocumentsCollectionNew = deliveries.getPaydocumentsCollection();
            List<String> illegalOrphanMessages = null;
            for (DeliveryStock deliveryStockCollectionOldDeliveryStock : deliveryStockCollectionOld) {
                if (!deliveryStockCollectionNew.contains(deliveryStockCollectionOldDeliveryStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DeliveryStock " + deliveryStockCollectionOldDeliveryStock + " since its deliveryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (truckIdNew != null) {
                truckIdNew = em.getReference(truckIdNew.getClass(), truckIdNew.getId());
                deliveries.setTruckId(truckIdNew);
            }
            Collection<DeliveryStock> attachedDeliveryStockCollectionNew = new ArrayList<DeliveryStock>();
            for (DeliveryStock deliveryStockCollectionNewDeliveryStockToAttach : deliveryStockCollectionNew) {
                deliveryStockCollectionNewDeliveryStockToAttach = em.getReference(deliveryStockCollectionNewDeliveryStockToAttach.getClass(), deliveryStockCollectionNewDeliveryStockToAttach.getId());
                attachedDeliveryStockCollectionNew.add(deliveryStockCollectionNewDeliveryStockToAttach);
            }
            deliveryStockCollectionNew = attachedDeliveryStockCollectionNew;
            deliveries.setDeliveryStockCollection(deliveryStockCollectionNew);
            Collection<Paydocuments> attachedPaydocumentsCollectionNew = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionNewPaydocumentsToAttach : paydocumentsCollectionNew) {
                paydocumentsCollectionNewPaydocumentsToAttach = em.getReference(paydocumentsCollectionNewPaydocumentsToAttach.getClass(), paydocumentsCollectionNewPaydocumentsToAttach.getId());
                attachedPaydocumentsCollectionNew.add(paydocumentsCollectionNewPaydocumentsToAttach);
            }
            paydocumentsCollectionNew = attachedPaydocumentsCollectionNew;
            deliveries.setPaydocumentsCollection(paydocumentsCollectionNew);
            deliveries = em.merge(deliveries);
            if (truckIdOld != null && !truckIdOld.equals(truckIdNew)) {
                truckIdOld.getDeliveriesCollection().remove(deliveries);
                truckIdOld = em.merge(truckIdOld);
            }
            if (truckIdNew != null && !truckIdNew.equals(truckIdOld)) {
                truckIdNew.getDeliveriesCollection().add(deliveries);
                truckIdNew = em.merge(truckIdNew);
            }
            for (DeliveryStock deliveryStockCollectionNewDeliveryStock : deliveryStockCollectionNew) {
                if (!deliveryStockCollectionOld.contains(deliveryStockCollectionNewDeliveryStock)) {
                    Deliveries oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock = deliveryStockCollectionNewDeliveryStock.getDeliveryId();
                    deliveryStockCollectionNewDeliveryStock.setDeliveryId(deliveries);
                    deliveryStockCollectionNewDeliveryStock = em.merge(deliveryStockCollectionNewDeliveryStock);
                    if (oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock != null && !oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock.equals(deliveries)) {
                        oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock.getDeliveryStockCollection().remove(deliveryStockCollectionNewDeliveryStock);
                        oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock = em.merge(oldDeliveryIdOfDeliveryStockCollectionNewDeliveryStock);
                    }
                }
            }
            for (Paydocuments paydocumentsCollectionOldPaydocuments : paydocumentsCollectionOld) {
                if (!paydocumentsCollectionNew.contains(paydocumentsCollectionOldPaydocuments)) {
                    paydocumentsCollectionOldPaydocuments.setDeliveryId(null);
                    paydocumentsCollectionOldPaydocuments = em.merge(paydocumentsCollectionOldPaydocuments);
                }
            }
            for (Paydocuments paydocumentsCollectionNewPaydocuments : paydocumentsCollectionNew) {
                if (!paydocumentsCollectionOld.contains(paydocumentsCollectionNewPaydocuments)) {
                    Deliveries oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments = paydocumentsCollectionNewPaydocuments.getDeliveryId();
                    paydocumentsCollectionNewPaydocuments.setDeliveryId(deliveries);
                    paydocumentsCollectionNewPaydocuments = em.merge(paydocumentsCollectionNewPaydocuments);
                    if (oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments != null && !oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments.equals(deliveries)) {
                        oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionNewPaydocuments);
                        oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments = em.merge(oldDeliveryIdOfPaydocumentsCollectionNewPaydocuments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deliveries.getId();
                if (findDeliveries(id) == null) {
                    throw new NonexistentEntityException("The deliveries with id " + id + " no longer exists.");
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
            Deliveries deliveries;
            try {
                deliveries = em.getReference(Deliveries.class, id);
                deliveries.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deliveries with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DeliveryStock> deliveryStockCollectionOrphanCheck = deliveries.getDeliveryStockCollection();
            for (DeliveryStock deliveryStockCollectionOrphanCheckDeliveryStock : deliveryStockCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deliveries (" + deliveries + ") cannot be destroyed since the DeliveryStock " + deliveryStockCollectionOrphanCheckDeliveryStock + " in its deliveryStockCollection field has a non-nullable deliveryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Trucks truckId = deliveries.getTruckId();
            if (truckId != null) {
                truckId.getDeliveriesCollection().remove(deliveries);
                truckId = em.merge(truckId);
            }
            Collection<Paydocuments> paydocumentsCollection = deliveries.getPaydocumentsCollection();
            for (Paydocuments paydocumentsCollectionPaydocuments : paydocumentsCollection) {
                paydocumentsCollectionPaydocuments.setDeliveryId(null);
                paydocumentsCollectionPaydocuments = em.merge(paydocumentsCollectionPaydocuments);
            }
            em.remove(deliveries);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deliveries> findDeliveriesEntities() {
        return findDeliveriesEntities(true, -1, -1);
    }

    public List<Deliveries> findDeliveriesEntities(int maxResults, int firstResult) {
        return findDeliveriesEntities(false, maxResults, firstResult);
    }

    private List<Deliveries> findDeliveriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deliveries.class));
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

    public Deliveries findDeliveries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deliveries.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeliveriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deliveries> rt = cq.from(Deliveries.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
