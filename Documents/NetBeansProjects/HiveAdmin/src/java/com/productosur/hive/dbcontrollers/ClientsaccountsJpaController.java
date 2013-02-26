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
import com.productosur.hive.entities.Clients;
import com.productosur.hive.entities.Clientsaccounts;
import com.productosur.hive.entities.Payments;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class ClientsaccountsJpaController implements Serializable {

    public ClientsaccountsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientsaccounts clientsaccounts) {
        if (clientsaccounts.getPaymentsCollection() == null) {
            clientsaccounts.setPaymentsCollection(new ArrayList<Payments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientId = clientsaccounts.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                clientsaccounts.setClientId(clientId);
            }
            Collection<Payments> attachedPaymentsCollection = new ArrayList<Payments>();
            for (Payments paymentsCollectionPaymentsToAttach : clientsaccounts.getPaymentsCollection()) {
                paymentsCollectionPaymentsToAttach = em.getReference(paymentsCollectionPaymentsToAttach.getClass(), paymentsCollectionPaymentsToAttach.getId());
                attachedPaymentsCollection.add(paymentsCollectionPaymentsToAttach);
            }
            clientsaccounts.setPaymentsCollection(attachedPaymentsCollection);
            em.persist(clientsaccounts);
           
            for (Payments paymentsCollectionPayments : clientsaccounts.getPaymentsCollection()) {
                Clientsaccounts oldClientaccountIdOfPaymentsCollectionPayments = paymentsCollectionPayments.getClientaccountId();
                paymentsCollectionPayments.setClientaccountId(clientsaccounts);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
                if (oldClientaccountIdOfPaymentsCollectionPayments != null) {
                    oldClientaccountIdOfPaymentsCollectionPayments.getPaymentsCollection().remove(paymentsCollectionPayments);
                    oldClientaccountIdOfPaymentsCollectionPayments = em.merge(oldClientaccountIdOfPaymentsCollectionPayments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientsaccounts clientsaccounts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientsaccounts persistentClientsaccounts = em.find(Clientsaccounts.class, clientsaccounts.getId());
            Clients clientIdOld = persistentClientsaccounts.getClientId();
            Clients clientIdNew = clientsaccounts.getClientId();
            Collection<Payments> paymentsCollectionOld = persistentClientsaccounts.getPaymentsCollection();
            Collection<Payments> paymentsCollectionNew = clientsaccounts.getPaymentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Payments paymentsCollectionOldPayments : paymentsCollectionOld) {
                if (!paymentsCollectionNew.contains(paymentsCollectionOldPayments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payments " + paymentsCollectionOldPayments + " since its clientaccountId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                clientsaccounts.setClientId(clientIdNew);
            }
            Collection<Payments> attachedPaymentsCollectionNew = new ArrayList<Payments>();
            for (Payments paymentsCollectionNewPaymentsToAttach : paymentsCollectionNew) {
                paymentsCollectionNewPaymentsToAttach = em.getReference(paymentsCollectionNewPaymentsToAttach.getClass(), paymentsCollectionNewPaymentsToAttach.getId());
                attachedPaymentsCollectionNew.add(paymentsCollectionNewPaymentsToAttach);
            }
            paymentsCollectionNew = attachedPaymentsCollectionNew;
            clientsaccounts.setPaymentsCollection(paymentsCollectionNew);
            clientsaccounts = em.merge(clientsaccounts);
           
            for (Payments paymentsCollectionNewPayments : paymentsCollectionNew) {
                if (!paymentsCollectionOld.contains(paymentsCollectionNewPayments)) {
                    Clientsaccounts oldClientaccountIdOfPaymentsCollectionNewPayments = paymentsCollectionNewPayments.getClientaccountId();
                    paymentsCollectionNewPayments.setClientaccountId(clientsaccounts);
                    paymentsCollectionNewPayments = em.merge(paymentsCollectionNewPayments);
                    if (oldClientaccountIdOfPaymentsCollectionNewPayments != null && !oldClientaccountIdOfPaymentsCollectionNewPayments.equals(clientsaccounts)) {
                        oldClientaccountIdOfPaymentsCollectionNewPayments.getPaymentsCollection().remove(paymentsCollectionNewPayments);
                        oldClientaccountIdOfPaymentsCollectionNewPayments = em.merge(oldClientaccountIdOfPaymentsCollectionNewPayments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientsaccounts.getId();
                if (findClientsaccounts(id) == null) {
                    throw new NonexistentEntityException("The clientsaccounts with id " + id + " no longer exists.");
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
            Clientsaccounts clientsaccounts;
            try {
                clientsaccounts = em.getReference(Clientsaccounts.class, id);
                clientsaccounts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientsaccounts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Payments> paymentsCollectionOrphanCheck = clientsaccounts.getPaymentsCollection();
            for (Payments paymentsCollectionOrphanCheckPayments : paymentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientsaccounts (" + clientsaccounts + ") cannot be destroyed since the Payments " + paymentsCollectionOrphanCheckPayments + " in its paymentsCollection field has a non-nullable clientaccountId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clients clientId = clientsaccounts.getClientId();
            
            em.remove(clientsaccounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientsaccounts> findClientsaccountsEntities() {
        return findClientsaccountsEntities(true, -1, -1);
    }

    public List<Clientsaccounts> findClientsaccountsEntities(int maxResults, int firstResult) {
        return findClientsaccountsEntities(false, maxResults, firstResult);
    }

    private List<Clientsaccounts> findClientsaccountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientsaccounts.class));
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

    public Clientsaccounts findClientsaccounts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientsaccounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsaccountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientsaccounts> rt = cq.from(Clientsaccounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
