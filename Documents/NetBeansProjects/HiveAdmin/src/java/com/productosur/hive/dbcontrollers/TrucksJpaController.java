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
import com.productosur.hive.entities.Deliveries;
import com.productosur.hive.entities.Trucks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class TrucksJpaController implements Serializable {

    public TrucksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trucks trucks) {
        if (trucks.getDeliveriesCollection() == null) {
            trucks.setDeliveriesCollection(new ArrayList<Deliveries>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Deliveries> attachedDeliveriesCollection = new ArrayList<Deliveries>();
            for (Deliveries deliveriesCollectionDeliveriesToAttach : trucks.getDeliveriesCollection()) {
                deliveriesCollectionDeliveriesToAttach = em.getReference(deliveriesCollectionDeliveriesToAttach.getClass(), deliveriesCollectionDeliveriesToAttach.getId());
                attachedDeliveriesCollection.add(deliveriesCollectionDeliveriesToAttach);
            }
            trucks.setDeliveriesCollection(attachedDeliveriesCollection);
            em.persist(trucks);
            for (Deliveries deliveriesCollectionDeliveries : trucks.getDeliveriesCollection()) {
                Trucks oldTruckIdOfDeliveriesCollectionDeliveries = deliveriesCollectionDeliveries.getTruckId();
                deliveriesCollectionDeliveries.setTruckId(trucks);
                deliveriesCollectionDeliveries = em.merge(deliveriesCollectionDeliveries);
                if (oldTruckIdOfDeliveriesCollectionDeliveries != null) {
                    oldTruckIdOfDeliveriesCollectionDeliveries.getDeliveriesCollection().remove(deliveriesCollectionDeliveries);
                    oldTruckIdOfDeliveriesCollectionDeliveries = em.merge(oldTruckIdOfDeliveriesCollectionDeliveries);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trucks trucks) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trucks persistentTrucks = em.find(Trucks.class, trucks.getId());
            Collection<Deliveries> deliveriesCollectionOld = persistentTrucks.getDeliveriesCollection();
            Collection<Deliveries> deliveriesCollectionNew = trucks.getDeliveriesCollection();
            List<String> illegalOrphanMessages = null;
            for (Deliveries deliveriesCollectionOldDeliveries : deliveriesCollectionOld) {
                if (!deliveriesCollectionNew.contains(deliveriesCollectionOldDeliveries)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deliveries " + deliveriesCollectionOldDeliveries + " since its truckId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Deliveries> attachedDeliveriesCollectionNew = new ArrayList<Deliveries>();
            for (Deliveries deliveriesCollectionNewDeliveriesToAttach : deliveriesCollectionNew) {
                deliveriesCollectionNewDeliveriesToAttach = em.getReference(deliveriesCollectionNewDeliveriesToAttach.getClass(), deliveriesCollectionNewDeliveriesToAttach.getId());
                attachedDeliveriesCollectionNew.add(deliveriesCollectionNewDeliveriesToAttach);
            }
            deliveriesCollectionNew = attachedDeliveriesCollectionNew;
            trucks.setDeliveriesCollection(deliveriesCollectionNew);
            trucks = em.merge(trucks);
            for (Deliveries deliveriesCollectionNewDeliveries : deliveriesCollectionNew) {
                if (!deliveriesCollectionOld.contains(deliveriesCollectionNewDeliveries)) {
                    Trucks oldTruckIdOfDeliveriesCollectionNewDeliveries = deliveriesCollectionNewDeliveries.getTruckId();
                    deliveriesCollectionNewDeliveries.setTruckId(trucks);
                    deliveriesCollectionNewDeliveries = em.merge(deliveriesCollectionNewDeliveries);
                    if (oldTruckIdOfDeliveriesCollectionNewDeliveries != null && !oldTruckIdOfDeliveriesCollectionNewDeliveries.equals(trucks)) {
                        oldTruckIdOfDeliveriesCollectionNewDeliveries.getDeliveriesCollection().remove(deliveriesCollectionNewDeliveries);
                        oldTruckIdOfDeliveriesCollectionNewDeliveries = em.merge(oldTruckIdOfDeliveriesCollectionNewDeliveries);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trucks.getId();
                if (findTrucks(id) == null) {
                    throw new NonexistentEntityException("The trucks with id " + id + " no longer exists.");
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
            Trucks trucks;
            try {
                trucks = em.getReference(Trucks.class, id);
                trucks.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trucks with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Deliveries> deliveriesCollectionOrphanCheck = trucks.getDeliveriesCollection();
            for (Deliveries deliveriesCollectionOrphanCheckDeliveries : deliveriesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trucks (" + trucks + ") cannot be destroyed since the Deliveries " + deliveriesCollectionOrphanCheckDeliveries + " in its deliveriesCollection field has a non-nullable truckId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(trucks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trucks> findTrucksEntities() {
        return findTrucksEntities(true, -1, -1);
    }

    public List<Trucks> findTrucksEntities(int maxResults, int firstResult) {
        return findTrucksEntities(false, maxResults, firstResult);
    }

    private List<Trucks> findTrucksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trucks.class));
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

    public Trucks findTrucks(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trucks.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrucksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trucks> rt = cq.from(Trucks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
