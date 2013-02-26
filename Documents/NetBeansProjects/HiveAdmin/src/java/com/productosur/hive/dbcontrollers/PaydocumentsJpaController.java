/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PaydocumentsJpaController implements Serializable {

    public PaydocumentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paydocuments paydocuments) {
        if (paydocuments.getPaymentsCollection() == null) {
            paydocuments.setPaymentsCollection(new ArrayList<Payments>());
        }
        if (paydocuments.getPaymentsCollection1() == null) {
            paydocuments.setPaymentsCollection1(new ArrayList<Payments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientsId = paydocuments.getClientsId();
            if (clientsId != null) {
                clientsId = em.getReference(clientsId.getClass(), clientsId.getId());
                paydocuments.setClientsId(clientsId);
            }
            Orders orderId = paydocuments.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                paydocuments.setOrderId(orderId);
            }
            Documentypes documentypeId = paydocuments.getDocumentypeId();
            if (documentypeId != null) {
                documentypeId = em.getReference(documentypeId.getClass(), documentypeId.getId());
                paydocuments.setDocumentypeId(documentypeId);
            }
            Deliveries deliveryId = paydocuments.getDeliveryId();
            if (deliveryId != null) {
                deliveryId = em.getReference(deliveryId.getClass(), deliveryId.getId());
                paydocuments.setDeliveryId(deliveryId);
            }
            Collection<Payments> attachedPaymentsCollection = new ArrayList<Payments>();
            for (Payments paymentsCollectionPaymentsToAttach : paydocuments.getPaymentsCollection()) {
                paymentsCollectionPaymentsToAttach = em.getReference(paymentsCollectionPaymentsToAttach.getClass(), paymentsCollectionPaymentsToAttach.getId());
                attachedPaymentsCollection.add(paymentsCollectionPaymentsToAttach);
            }
            paydocuments.setPaymentsCollection(attachedPaymentsCollection);
            Collection<Payments> attachedPaymentsCollection1 = new ArrayList<Payments>();
            for (Payments paymentsCollection1PaymentsToAttach : paydocuments.getPaymentsCollection1()) {
                paymentsCollection1PaymentsToAttach = em.getReference(paymentsCollection1PaymentsToAttach.getClass(), paymentsCollection1PaymentsToAttach.getId());
                attachedPaymentsCollection1.add(paymentsCollection1PaymentsToAttach);
            }
            paydocuments.setPaymentsCollection1(attachedPaymentsCollection1);
            em.persist(paydocuments);
            if (clientsId != null) {
                clientsId.getPaydocumentsCollection().add(paydocuments);
                clientsId = em.merge(clientsId);
            }
            if (orderId != null) {
                orderId.getPaydocumentsCollection().add(paydocuments);
                orderId = em.merge(orderId);
            }
            if (documentypeId != null) {
                documentypeId.getPaydocumentsCollection().add(paydocuments);
                documentypeId = em.merge(documentypeId);
            }
            if (deliveryId != null) {
                deliveryId.getPaydocumentsCollection().add(paydocuments);
                deliveryId = em.merge(deliveryId);
            }
            for (Payments paymentsCollectionPayments : paydocuments.getPaymentsCollection()) {
                Paydocuments oldReceiptdocumentIdOfPaymentsCollectionPayments = paymentsCollectionPayments.getReceiptdocumentId();
                paymentsCollectionPayments.setReceiptdocumentId(paydocuments);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
                if (oldReceiptdocumentIdOfPaymentsCollectionPayments != null) {
                    oldReceiptdocumentIdOfPaymentsCollectionPayments.getPaymentsCollection().remove(paymentsCollectionPayments);
                    oldReceiptdocumentIdOfPaymentsCollectionPayments = em.merge(oldReceiptdocumentIdOfPaymentsCollectionPayments);
                }
            }
            for (Payments paymentsCollection1Payments : paydocuments.getPaymentsCollection1()) {
                Paydocuments oldPaydocumentIdOfPaymentsCollection1Payments = paymentsCollection1Payments.getPaydocumentId();
                paymentsCollection1Payments.setPaydocumentId(paydocuments);
                paymentsCollection1Payments = em.merge(paymentsCollection1Payments);
                if (oldPaydocumentIdOfPaymentsCollection1Payments != null) {
                    oldPaydocumentIdOfPaymentsCollection1Payments.getPaymentsCollection1().remove(paymentsCollection1Payments);
                    oldPaydocumentIdOfPaymentsCollection1Payments = em.merge(oldPaydocumentIdOfPaymentsCollection1Payments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paydocuments paydocuments) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paydocuments persistentPaydocuments = em.find(Paydocuments.class, paydocuments.getId());
            Clients clientsIdOld = persistentPaydocuments.getClientsId();
            Clients clientsIdNew = paydocuments.getClientsId();
            Orders orderIdOld = persistentPaydocuments.getOrderId();
            Orders orderIdNew = paydocuments.getOrderId();
            Documentypes documentypeIdOld = persistentPaydocuments.getDocumentypeId();
            Documentypes documentypeIdNew = paydocuments.getDocumentypeId();
            Deliveries deliveryIdOld = persistentPaydocuments.getDeliveryId();
            Deliveries deliveryIdNew = paydocuments.getDeliveryId();
            Collection<Payments> paymentsCollectionOld = persistentPaydocuments.getPaymentsCollection();
            Collection<Payments> paymentsCollectionNew = paydocuments.getPaymentsCollection();
            Collection<Payments> paymentsCollection1Old = persistentPaydocuments.getPaymentsCollection1();
            Collection<Payments> paymentsCollection1New = paydocuments.getPaymentsCollection1();
            List<String> illegalOrphanMessages = null;
            for (Payments paymentsCollection1OldPayments : paymentsCollection1Old) {
                if (!paymentsCollection1New.contains(paymentsCollection1OldPayments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payments " + paymentsCollection1OldPayments + " since its paydocumentId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientsIdNew != null) {
                clientsIdNew = em.getReference(clientsIdNew.getClass(), clientsIdNew.getId());
                paydocuments.setClientsId(clientsIdNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                paydocuments.setOrderId(orderIdNew);
            }
            if (documentypeIdNew != null) {
                documentypeIdNew = em.getReference(documentypeIdNew.getClass(), documentypeIdNew.getId());
                paydocuments.setDocumentypeId(documentypeIdNew);
            }
            if (deliveryIdNew != null) {
                deliveryIdNew = em.getReference(deliveryIdNew.getClass(), deliveryIdNew.getId());
                paydocuments.setDeliveryId(deliveryIdNew);
            }
            Collection<Payments> attachedPaymentsCollectionNew = new ArrayList<Payments>();
            for (Payments paymentsCollectionNewPaymentsToAttach : paymentsCollectionNew) {
                paymentsCollectionNewPaymentsToAttach = em.getReference(paymentsCollectionNewPaymentsToAttach.getClass(), paymentsCollectionNewPaymentsToAttach.getId());
                attachedPaymentsCollectionNew.add(paymentsCollectionNewPaymentsToAttach);
            }
            paymentsCollectionNew = attachedPaymentsCollectionNew;
            paydocuments.setPaymentsCollection(paymentsCollectionNew);
            Collection<Payments> attachedPaymentsCollection1New = new ArrayList<Payments>();
            for (Payments paymentsCollection1NewPaymentsToAttach : paymentsCollection1New) {
                paymentsCollection1NewPaymentsToAttach = em.getReference(paymentsCollection1NewPaymentsToAttach.getClass(), paymentsCollection1NewPaymentsToAttach.getId());
                attachedPaymentsCollection1New.add(paymentsCollection1NewPaymentsToAttach);
            }
            paymentsCollection1New = attachedPaymentsCollection1New;
            paydocuments.setPaymentsCollection1(paymentsCollection1New);
            paydocuments = em.merge(paydocuments);
            if (clientsIdOld != null && !clientsIdOld.equals(clientsIdNew)) {
                clientsIdOld.getPaydocumentsCollection().remove(paydocuments);
                clientsIdOld = em.merge(clientsIdOld);
            }
            if (clientsIdNew != null && !clientsIdNew.equals(clientsIdOld)) {
                clientsIdNew.getPaydocumentsCollection().add(paydocuments);
                clientsIdNew = em.merge(clientsIdNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getPaydocumentsCollection().remove(paydocuments);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getPaydocumentsCollection().add(paydocuments);
                orderIdNew = em.merge(orderIdNew);
            }
            if (documentypeIdOld != null && !documentypeIdOld.equals(documentypeIdNew)) {
                documentypeIdOld.getPaydocumentsCollection().remove(paydocuments);
                documentypeIdOld = em.merge(documentypeIdOld);
            }
            if (documentypeIdNew != null && !documentypeIdNew.equals(documentypeIdOld)) {
                documentypeIdNew.getPaydocumentsCollection().add(paydocuments);
                documentypeIdNew = em.merge(documentypeIdNew);
            }
            if (deliveryIdOld != null && !deliveryIdOld.equals(deliveryIdNew)) {
                deliveryIdOld.getPaydocumentsCollection().remove(paydocuments);
                deliveryIdOld = em.merge(deliveryIdOld);
            }
            if (deliveryIdNew != null && !deliveryIdNew.equals(deliveryIdOld)) {
                deliveryIdNew.getPaydocumentsCollection().add(paydocuments);
                deliveryIdNew = em.merge(deliveryIdNew);
            }
            for (Payments paymentsCollectionOldPayments : paymentsCollectionOld) {
                if (!paymentsCollectionNew.contains(paymentsCollectionOldPayments)) {
                    paymentsCollectionOldPayments.setReceiptdocumentId(null);
                    paymentsCollectionOldPayments = em.merge(paymentsCollectionOldPayments);
                }
            }
            for (Payments paymentsCollectionNewPayments : paymentsCollectionNew) {
                if (!paymentsCollectionOld.contains(paymentsCollectionNewPayments)) {
                    Paydocuments oldReceiptdocumentIdOfPaymentsCollectionNewPayments = paymentsCollectionNewPayments.getReceiptdocumentId();
                    paymentsCollectionNewPayments.setReceiptdocumentId(paydocuments);
                    paymentsCollectionNewPayments = em.merge(paymentsCollectionNewPayments);
                    if (oldReceiptdocumentIdOfPaymentsCollectionNewPayments != null && !oldReceiptdocumentIdOfPaymentsCollectionNewPayments.equals(paydocuments)) {
                        oldReceiptdocumentIdOfPaymentsCollectionNewPayments.getPaymentsCollection().remove(paymentsCollectionNewPayments);
                        oldReceiptdocumentIdOfPaymentsCollectionNewPayments = em.merge(oldReceiptdocumentIdOfPaymentsCollectionNewPayments);
                    }
                }
            }
            for (Payments paymentsCollection1NewPayments : paymentsCollection1New) {
                if (!paymentsCollection1Old.contains(paymentsCollection1NewPayments)) {
                    Paydocuments oldPaydocumentIdOfPaymentsCollection1NewPayments = paymentsCollection1NewPayments.getPaydocumentId();
                    paymentsCollection1NewPayments.setPaydocumentId(paydocuments);
                    paymentsCollection1NewPayments = em.merge(paymentsCollection1NewPayments);
                    if (oldPaydocumentIdOfPaymentsCollection1NewPayments != null && !oldPaydocumentIdOfPaymentsCollection1NewPayments.equals(paydocuments)) {
                        oldPaydocumentIdOfPaymentsCollection1NewPayments.getPaymentsCollection1().remove(paymentsCollection1NewPayments);
                        oldPaydocumentIdOfPaymentsCollection1NewPayments = em.merge(oldPaydocumentIdOfPaymentsCollection1NewPayments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paydocuments.getId();
                if (findPaydocuments(id) == null) {
                    throw new NonexistentEntityException("The paydocuments with id " + id + " no longer exists.");
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
            Paydocuments paydocuments;
            try {
                paydocuments = em.getReference(Paydocuments.class, id);
                paydocuments.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paydocuments with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Payments> paymentsCollection1OrphanCheck = paydocuments.getPaymentsCollection1();
            for (Payments paymentsCollection1OrphanCheckPayments : paymentsCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paydocuments (" + paydocuments + ") cannot be destroyed since the Payments " + paymentsCollection1OrphanCheckPayments + " in its paymentsCollection1 field has a non-nullable paydocumentId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clients clientsId = paydocuments.getClientsId();
            if (clientsId != null) {
                clientsId.getPaydocumentsCollection().remove(paydocuments);
                clientsId = em.merge(clientsId);
            }
            Orders orderId = paydocuments.getOrderId();
            if (orderId != null) {
                orderId.getPaydocumentsCollection().remove(paydocuments);
                orderId = em.merge(orderId);
            }
            Documentypes documentypeId = paydocuments.getDocumentypeId();
            if (documentypeId != null) {
                documentypeId.getPaydocumentsCollection().remove(paydocuments);
                documentypeId = em.merge(documentypeId);
            }
            Deliveries deliveryId = paydocuments.getDeliveryId();
            if (deliveryId != null) {
                deliveryId.getPaydocumentsCollection().remove(paydocuments);
                deliveryId = em.merge(deliveryId);
            }
            Collection<Payments> paymentsCollection = paydocuments.getPaymentsCollection();
            for (Payments paymentsCollectionPayments : paymentsCollection) {
                paymentsCollectionPayments.setReceiptdocumentId(null);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
            }
            em.remove(paydocuments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paydocuments> findPaydocumentsEntities() {
        return findPaydocumentsEntities(true, -1, -1);
    }

    public List<Paydocuments> findPaydocumentsEntities(int maxResults, int firstResult) {
        return findPaydocumentsEntities(false, maxResults, firstResult);
    }

    private List<Paydocuments> findPaydocumentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paydocuments.class));
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

    public Paydocuments findPaydocuments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paydocuments.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaydocumentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paydocuments> rt = cq.from(Paydocuments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public List<Paydocuments> findPaydocumentsEntities(Clients client) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("Select o from Paydocuments o where o.clientsId=:arg1");
            q.setParameter("arg1", client);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
