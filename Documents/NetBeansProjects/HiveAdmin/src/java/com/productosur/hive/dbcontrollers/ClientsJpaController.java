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
public class ClientsJpaController implements Serializable {

    public ClientsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clients clients) {
        if (clients.getClientsaccountsCollection() == null) {
            clients.setClientsaccountsCollection(new ArrayList<Clientsaccounts>());
        }
        if (clients.getSubsidiariesCollection() == null) {
            clients.setSubsidiariesCollection(new ArrayList<Subsidiaries>());
        }
        if (clients.getPaydocumentsCollection() == null) {
            clients.setPaydocumentsCollection(new ArrayList<Paydocuments>());
        }
        if (clients.getDiscountsCollection() == null) {
            clients.setDiscountsCollection(new ArrayList<Discounts>());
        }
        if (clients.getPaymentsCollection() == null) {
            clients.setPaymentsCollection(new ArrayList<Payments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Clientsaccounts> attachedClientsaccountsCollection = new ArrayList<Clientsaccounts>();
            for (Clientsaccounts clientsaccountsCollectionClientsaccountsToAttach : clients.getClientsaccountsCollection()) {
                clientsaccountsCollectionClientsaccountsToAttach = em.getReference(clientsaccountsCollectionClientsaccountsToAttach.getClass(), clientsaccountsCollectionClientsaccountsToAttach.getId());
                attachedClientsaccountsCollection.add(clientsaccountsCollectionClientsaccountsToAttach);
            }
            clients.setClientsaccountsCollection(attachedClientsaccountsCollection);
            Collection<Subsidiaries> attachedSubsidiariesCollection = new ArrayList<Subsidiaries>();
            for (Subsidiaries subsidiariesCollectionSubsidiariesToAttach : clients.getSubsidiariesCollection()) {
                subsidiariesCollectionSubsidiariesToAttach = em.getReference(subsidiariesCollectionSubsidiariesToAttach.getClass(), subsidiariesCollectionSubsidiariesToAttach.getId());
                attachedSubsidiariesCollection.add(subsidiariesCollectionSubsidiariesToAttach);
            }
            clients.setSubsidiariesCollection(attachedSubsidiariesCollection);
            Collection<Paydocuments> attachedPaydocumentsCollection = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionPaydocumentsToAttach : clients.getPaydocumentsCollection()) {
                paydocumentsCollectionPaydocumentsToAttach = em.getReference(paydocumentsCollectionPaydocumentsToAttach.getClass(), paydocumentsCollectionPaydocumentsToAttach.getId());
                attachedPaydocumentsCollection.add(paydocumentsCollectionPaydocumentsToAttach);
            }
            clients.setPaydocumentsCollection(attachedPaydocumentsCollection);
            Collection<Discounts> attachedDiscountsCollection = new ArrayList<Discounts>();
            for (Discounts discountsCollectionDiscountsToAttach : clients.getDiscountsCollection()) {
                discountsCollectionDiscountsToAttach = em.getReference(discountsCollectionDiscountsToAttach.getClass(), discountsCollectionDiscountsToAttach.getId());
                attachedDiscountsCollection.add(discountsCollectionDiscountsToAttach);
            }
            clients.setDiscountsCollection(attachedDiscountsCollection);
            Collection<Payments> attachedPaymentsCollection = new ArrayList<Payments>();
            for (Payments paymentsCollectionPaymentsToAttach : clients.getPaymentsCollection()) {
                paymentsCollectionPaymentsToAttach = em.getReference(paymentsCollectionPaymentsToAttach.getClass(), paymentsCollectionPaymentsToAttach.getId());
                attachedPaymentsCollection.add(paymentsCollectionPaymentsToAttach);
            }
            clients.setPaymentsCollection(attachedPaymentsCollection);
            em.persist(clients);
            for (Clientsaccounts clientsaccountsCollectionClientsaccounts : clients.getClientsaccountsCollection()) {
                Clients oldClientIdOfClientsaccountsCollectionClientsaccounts = clientsaccountsCollectionClientsaccounts.getClientId();
                clientsaccountsCollectionClientsaccounts.setClientId(clients);
                clientsaccountsCollectionClientsaccounts = em.merge(clientsaccountsCollectionClientsaccounts);
                if (oldClientIdOfClientsaccountsCollectionClientsaccounts != null) {
                    oldClientIdOfClientsaccountsCollectionClientsaccounts.getClientsaccountsCollection().remove(clientsaccountsCollectionClientsaccounts);
                    oldClientIdOfClientsaccountsCollectionClientsaccounts = em.merge(oldClientIdOfClientsaccountsCollectionClientsaccounts);
                }
            }
            for (Subsidiaries subsidiariesCollectionSubsidiaries : clients.getSubsidiariesCollection()) {
                Clients oldClientIdOfSubsidiariesCollectionSubsidiaries = subsidiariesCollectionSubsidiaries.getClientId();
                subsidiariesCollectionSubsidiaries.setClientId(clients);
                subsidiariesCollectionSubsidiaries = em.merge(subsidiariesCollectionSubsidiaries);
                if (oldClientIdOfSubsidiariesCollectionSubsidiaries != null) {
                    oldClientIdOfSubsidiariesCollectionSubsidiaries.getSubsidiariesCollection().remove(subsidiariesCollectionSubsidiaries);
                    oldClientIdOfSubsidiariesCollectionSubsidiaries = em.merge(oldClientIdOfSubsidiariesCollectionSubsidiaries);
                }
            }
            for (Paydocuments paydocumentsCollectionPaydocuments : clients.getPaydocumentsCollection()) {
                Clients oldClientsIdOfPaydocumentsCollectionPaydocuments = paydocumentsCollectionPaydocuments.getClientsId();
                paydocumentsCollectionPaydocuments.setClientsId(clients);
                paydocumentsCollectionPaydocuments = em.merge(paydocumentsCollectionPaydocuments);
                if (oldClientsIdOfPaydocumentsCollectionPaydocuments != null) {
                    oldClientsIdOfPaydocumentsCollectionPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionPaydocuments);
                    oldClientsIdOfPaydocumentsCollectionPaydocuments = em.merge(oldClientsIdOfPaydocumentsCollectionPaydocuments);
                }
            }
            for (Discounts discountsCollectionDiscounts : clients.getDiscountsCollection()) {
                Clients oldClientIdOfDiscountsCollectionDiscounts = discountsCollectionDiscounts.getClientId();
                discountsCollectionDiscounts.setClientId(clients);
                discountsCollectionDiscounts = em.merge(discountsCollectionDiscounts);
                if (oldClientIdOfDiscountsCollectionDiscounts != null) {
                    oldClientIdOfDiscountsCollectionDiscounts.getDiscountsCollection().remove(discountsCollectionDiscounts);
                    oldClientIdOfDiscountsCollectionDiscounts = em.merge(oldClientIdOfDiscountsCollectionDiscounts);
                }
            }
            for (Payments paymentsCollectionPayments : clients.getPaymentsCollection()) {
                Clients oldClientIdOfPaymentsCollectionPayments = paymentsCollectionPayments.getClientId();
                paymentsCollectionPayments.setClientId(clients);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
                if (oldClientIdOfPaymentsCollectionPayments != null) {
                    oldClientIdOfPaymentsCollectionPayments.getPaymentsCollection().remove(paymentsCollectionPayments);
                    oldClientIdOfPaymentsCollectionPayments = em.merge(oldClientIdOfPaymentsCollectionPayments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clients clients) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients persistentClients = em.find(Clients.class, clients.getId());
            Collection<Clientsaccounts> clientsaccountsCollectionOld = persistentClients.getClientsaccountsCollection();
            Collection<Clientsaccounts> clientsaccountsCollectionNew = clients.getClientsaccountsCollection();
            Collection<Subsidiaries> subsidiariesCollectionOld = persistentClients.getSubsidiariesCollection();
            Collection<Subsidiaries> subsidiariesCollectionNew = clients.getSubsidiariesCollection();
            Collection<Paydocuments> paydocumentsCollectionOld = persistentClients.getPaydocumentsCollection();
            Collection<Paydocuments> paydocumentsCollectionNew = clients.getPaydocumentsCollection();
            Collection<Discounts> discountsCollectionOld = persistentClients.getDiscountsCollection();
            Collection<Discounts> discountsCollectionNew = clients.getDiscountsCollection();
            Collection<Payments> paymentsCollectionOld = persistentClients.getPaymentsCollection();
            Collection<Payments> paymentsCollectionNew = clients.getPaymentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Clientsaccounts clientsaccountsCollectionOldClientsaccounts : clientsaccountsCollectionOld) {
                if (!clientsaccountsCollectionNew.contains(clientsaccountsCollectionOldClientsaccounts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientsaccounts " + clientsaccountsCollectionOldClientsaccounts + " since its clientId field is not nullable.");
                }
            }
            for (Subsidiaries subsidiariesCollectionOldSubsidiaries : subsidiariesCollectionOld) {
                if (!subsidiariesCollectionNew.contains(subsidiariesCollectionOldSubsidiaries)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subsidiaries " + subsidiariesCollectionOldSubsidiaries + " since its clientId field is not nullable.");
                }
            }
            for (Paydocuments paydocumentsCollectionOldPaydocuments : paydocumentsCollectionOld) {
                if (!paydocumentsCollectionNew.contains(paydocumentsCollectionOldPaydocuments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paydocuments " + paydocumentsCollectionOldPaydocuments + " since its clientsId field is not nullable.");
                }
            }
            for (Discounts discountsCollectionOldDiscounts : discountsCollectionOld) {
                if (!discountsCollectionNew.contains(discountsCollectionOldDiscounts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discounts " + discountsCollectionOldDiscounts + " since its clientId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Clientsaccounts> attachedClientsaccountsCollectionNew = new ArrayList<Clientsaccounts>();
            for (Clientsaccounts clientsaccountsCollectionNewClientsaccountsToAttach : clientsaccountsCollectionNew) {
                clientsaccountsCollectionNewClientsaccountsToAttach = em.getReference(clientsaccountsCollectionNewClientsaccountsToAttach.getClass(), clientsaccountsCollectionNewClientsaccountsToAttach.getId());
                attachedClientsaccountsCollectionNew.add(clientsaccountsCollectionNewClientsaccountsToAttach);
            }
            clientsaccountsCollectionNew = attachedClientsaccountsCollectionNew;
            clients.setClientsaccountsCollection(clientsaccountsCollectionNew);
            Collection<Subsidiaries> attachedSubsidiariesCollectionNew = new ArrayList<Subsidiaries>();
            for (Subsidiaries subsidiariesCollectionNewSubsidiariesToAttach : subsidiariesCollectionNew) {
                subsidiariesCollectionNewSubsidiariesToAttach = em.getReference(subsidiariesCollectionNewSubsidiariesToAttach.getClass(), subsidiariesCollectionNewSubsidiariesToAttach.getId());
                attachedSubsidiariesCollectionNew.add(subsidiariesCollectionNewSubsidiariesToAttach);
            }
            subsidiariesCollectionNew = attachedSubsidiariesCollectionNew;
            clients.setSubsidiariesCollection(subsidiariesCollectionNew);
            Collection<Paydocuments> attachedPaydocumentsCollectionNew = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionNewPaydocumentsToAttach : paydocumentsCollectionNew) {
                paydocumentsCollectionNewPaydocumentsToAttach = em.getReference(paydocumentsCollectionNewPaydocumentsToAttach.getClass(), paydocumentsCollectionNewPaydocumentsToAttach.getId());
                attachedPaydocumentsCollectionNew.add(paydocumentsCollectionNewPaydocumentsToAttach);
            }
            paydocumentsCollectionNew = attachedPaydocumentsCollectionNew;
            clients.setPaydocumentsCollection(paydocumentsCollectionNew);
            Collection<Discounts> attachedDiscountsCollectionNew = new ArrayList<Discounts>();
            for (Discounts discountsCollectionNewDiscountsToAttach : discountsCollectionNew) {
                discountsCollectionNewDiscountsToAttach = em.getReference(discountsCollectionNewDiscountsToAttach.getClass(), discountsCollectionNewDiscountsToAttach.getId());
                attachedDiscountsCollectionNew.add(discountsCollectionNewDiscountsToAttach);
            }
            discountsCollectionNew = attachedDiscountsCollectionNew;
            clients.setDiscountsCollection(discountsCollectionNew);
            Collection<Payments> attachedPaymentsCollectionNew = new ArrayList<Payments>();
            for (Payments paymentsCollectionNewPaymentsToAttach : paymentsCollectionNew) {
                paymentsCollectionNewPaymentsToAttach = em.getReference(paymentsCollectionNewPaymentsToAttach.getClass(), paymentsCollectionNewPaymentsToAttach.getId());
                attachedPaymentsCollectionNew.add(paymentsCollectionNewPaymentsToAttach);
            }
            paymentsCollectionNew = attachedPaymentsCollectionNew;
            clients.setPaymentsCollection(paymentsCollectionNew);
            clients = em.merge(clients);
            for (Clientsaccounts clientsaccountsCollectionNewClientsaccounts : clientsaccountsCollectionNew) {
                if (!clientsaccountsCollectionOld.contains(clientsaccountsCollectionNewClientsaccounts)) {
                    Clients oldClientIdOfClientsaccountsCollectionNewClientsaccounts = clientsaccountsCollectionNewClientsaccounts.getClientId();
                    clientsaccountsCollectionNewClientsaccounts.setClientId(clients);
                    clientsaccountsCollectionNewClientsaccounts = em.merge(clientsaccountsCollectionNewClientsaccounts);
                    if (oldClientIdOfClientsaccountsCollectionNewClientsaccounts != null && !oldClientIdOfClientsaccountsCollectionNewClientsaccounts.equals(clients)) {
                        oldClientIdOfClientsaccountsCollectionNewClientsaccounts.getClientsaccountsCollection().remove(clientsaccountsCollectionNewClientsaccounts);
                        oldClientIdOfClientsaccountsCollectionNewClientsaccounts = em.merge(oldClientIdOfClientsaccountsCollectionNewClientsaccounts);
                    }
                }
            }
            for (Subsidiaries subsidiariesCollectionNewSubsidiaries : subsidiariesCollectionNew) {
                if (!subsidiariesCollectionOld.contains(subsidiariesCollectionNewSubsidiaries)) {
                    Clients oldClientIdOfSubsidiariesCollectionNewSubsidiaries = subsidiariesCollectionNewSubsidiaries.getClientId();
                    subsidiariesCollectionNewSubsidiaries.setClientId(clients);
                    subsidiariesCollectionNewSubsidiaries = em.merge(subsidiariesCollectionNewSubsidiaries);
                    if (oldClientIdOfSubsidiariesCollectionNewSubsidiaries != null && !oldClientIdOfSubsidiariesCollectionNewSubsidiaries.equals(clients)) {
                        oldClientIdOfSubsidiariesCollectionNewSubsidiaries.getSubsidiariesCollection().remove(subsidiariesCollectionNewSubsidiaries);
                        oldClientIdOfSubsidiariesCollectionNewSubsidiaries = em.merge(oldClientIdOfSubsidiariesCollectionNewSubsidiaries);
                    }
                }
            }
            for (Paydocuments paydocumentsCollectionNewPaydocuments : paydocumentsCollectionNew) {
                if (!paydocumentsCollectionOld.contains(paydocumentsCollectionNewPaydocuments)) {
                    Clients oldClientsIdOfPaydocumentsCollectionNewPaydocuments = paydocumentsCollectionNewPaydocuments.getClientsId();
                    paydocumentsCollectionNewPaydocuments.setClientsId(clients);
                    paydocumentsCollectionNewPaydocuments = em.merge(paydocumentsCollectionNewPaydocuments);
                    if (oldClientsIdOfPaydocumentsCollectionNewPaydocuments != null && !oldClientsIdOfPaydocumentsCollectionNewPaydocuments.equals(clients)) {
                        oldClientsIdOfPaydocumentsCollectionNewPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionNewPaydocuments);
                        oldClientsIdOfPaydocumentsCollectionNewPaydocuments = em.merge(oldClientsIdOfPaydocumentsCollectionNewPaydocuments);
                    }
                }
            }
            for (Discounts discountsCollectionNewDiscounts : discountsCollectionNew) {
                if (!discountsCollectionOld.contains(discountsCollectionNewDiscounts)) {
                    Clients oldClientIdOfDiscountsCollectionNewDiscounts = discountsCollectionNewDiscounts.getClientId();
                    discountsCollectionNewDiscounts.setClientId(clients);
                    discountsCollectionNewDiscounts = em.merge(discountsCollectionNewDiscounts);
                    if (oldClientIdOfDiscountsCollectionNewDiscounts != null && !oldClientIdOfDiscountsCollectionNewDiscounts.equals(clients)) {
                        oldClientIdOfDiscountsCollectionNewDiscounts.getDiscountsCollection().remove(discountsCollectionNewDiscounts);
                        oldClientIdOfDiscountsCollectionNewDiscounts = em.merge(oldClientIdOfDiscountsCollectionNewDiscounts);
                    }
                }
            }
            for (Payments paymentsCollectionOldPayments : paymentsCollectionOld) {
                if (!paymentsCollectionNew.contains(paymentsCollectionOldPayments)) {
                    paymentsCollectionOldPayments.setClientId(null);
                    paymentsCollectionOldPayments = em.merge(paymentsCollectionOldPayments);
                }
            }
            for (Payments paymentsCollectionNewPayments : paymentsCollectionNew) {
                if (!paymentsCollectionOld.contains(paymentsCollectionNewPayments)) {
                    Clients oldClientIdOfPaymentsCollectionNewPayments = paymentsCollectionNewPayments.getClientId();
                    paymentsCollectionNewPayments.setClientId(clients);
                    paymentsCollectionNewPayments = em.merge(paymentsCollectionNewPayments);
                    if (oldClientIdOfPaymentsCollectionNewPayments != null && !oldClientIdOfPaymentsCollectionNewPayments.equals(clients)) {
                        oldClientIdOfPaymentsCollectionNewPayments.getPaymentsCollection().remove(paymentsCollectionNewPayments);
                        oldClientIdOfPaymentsCollectionNewPayments = em.merge(oldClientIdOfPaymentsCollectionNewPayments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getId();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
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
            Clients clients;
            try {
                clients = em.getReference(Clients.class, id);
                clients.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clients with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Clientsaccounts> clientsaccountsCollectionOrphanCheck = clients.getClientsaccountsCollection();
            for (Clientsaccounts clientsaccountsCollectionOrphanCheckClientsaccounts : clientsaccountsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Clientsaccounts " + clientsaccountsCollectionOrphanCheckClientsaccounts + " in its clientsaccountsCollection field has a non-nullable clientId field.");
            }
            Collection<Subsidiaries> subsidiariesCollectionOrphanCheck = clients.getSubsidiariesCollection();
            for (Subsidiaries subsidiariesCollectionOrphanCheckSubsidiaries : subsidiariesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Subsidiaries " + subsidiariesCollectionOrphanCheckSubsidiaries + " in its subsidiariesCollection field has a non-nullable clientId field.");
            }
            Collection<Paydocuments> paydocumentsCollectionOrphanCheck = clients.getPaydocumentsCollection();
            for (Paydocuments paydocumentsCollectionOrphanCheckPaydocuments : paydocumentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Paydocuments " + paydocumentsCollectionOrphanCheckPaydocuments + " in its paydocumentsCollection field has a non-nullable clientsId field.");
            }
            Collection<Discounts> discountsCollectionOrphanCheck = clients.getDiscountsCollection();
            for (Discounts discountsCollectionOrphanCheckDiscounts : discountsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Discounts " + discountsCollectionOrphanCheckDiscounts + " in its discountsCollection field has a non-nullable clientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Payments> paymentsCollection = clients.getPaymentsCollection();
            for (Payments paymentsCollectionPayments : paymentsCollection) {
                paymentsCollectionPayments.setClientId(null);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
            }
            em.remove(clients);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clients> findClientsEntities() {
        return findClientsEntities(true, -1, -1);
    }

    public List<Clients> findClientsEntities(int maxResults, int firstResult) {
        return findClientsEntities(false, maxResults, firstResult);
    }

    private List<Clients> findClientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clients.class));
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

    public Clients findClients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clients.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clients> rt = cq.from(Clients.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
