/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Clients;
import com.productosur.hive.entities.Paydocuments;
import com.productosur.hive.entities.Clientsaccounts;
import com.productosur.hive.entities.Payments;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PaymentsJpaController implements Serializable {

    public PaymentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Payments payments) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientId = payments.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                payments.setClientId(clientId);
            }
            Paydocuments receiptdocumentId = payments.getReceiptdocumentId();
            if (receiptdocumentId != null) {
                receiptdocumentId = em.getReference(receiptdocumentId.getClass(), receiptdocumentId.getId());
                payments.setReceiptdocumentId(receiptdocumentId);
            }
            Paydocuments paydocumentId = payments.getPaydocumentId();
            if (paydocumentId != null) {
                paydocumentId = em.getReference(paydocumentId.getClass(), paydocumentId.getId());
                payments.setPaydocumentId(paydocumentId);
            }
            Clientsaccounts clientaccountId = payments.getClientaccountId();
            if (clientaccountId != null) {
                clientaccountId = em.getReference(clientaccountId.getClass(), clientaccountId.getId());
                payments.setClientaccountId(clientaccountId);
            }
            em.persist(payments);
            if (clientId != null) {
                clientId.getPaymentsCollection().add(payments);
                clientId = em.merge(clientId);
            }
            
            
            if (clientaccountId != null) {
                clientaccountId.getPaymentsCollection().add(payments);
                clientaccountId = em.merge(clientaccountId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Payments payments) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments persistentPayments = em.find(Payments.class, payments.getId());
            Clients clientIdOld = persistentPayments.getClientId();
            Clients clientIdNew = payments.getClientId();
            Paydocuments receiptdocumentIdOld = persistentPayments.getReceiptdocumentId();
            Paydocuments receiptdocumentIdNew = payments.getReceiptdocumentId();
            Paydocuments paydocumentIdOld = persistentPayments.getPaydocumentId();
            Paydocuments paydocumentIdNew = payments.getPaydocumentId();
            Clientsaccounts clientaccountIdOld = persistentPayments.getClientaccountId();
            Clientsaccounts clientaccountIdNew = payments.getClientaccountId();
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                payments.setClientId(clientIdNew);
            }
            if (receiptdocumentIdNew != null) {
                receiptdocumentIdNew = em.getReference(receiptdocumentIdNew.getClass(), receiptdocumentIdNew.getId());
                payments.setReceiptdocumentId(receiptdocumentIdNew);
            }
            if (paydocumentIdNew != null) {
                paydocumentIdNew = em.getReference(paydocumentIdNew.getClass(), paydocumentIdNew.getId());
                payments.setPaydocumentId(paydocumentIdNew);
            }
            if (clientaccountIdNew != null) {
                clientaccountIdNew = em.getReference(clientaccountIdNew.getClass(), clientaccountIdNew.getId());
                payments.setClientaccountId(clientaccountIdNew);
            }
            payments = em.merge(payments);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getPaymentsCollection().remove(payments);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getPaymentsCollection().add(payments);
                clientIdNew = em.merge(clientIdNew);
            }
            
           
            if (clientaccountIdOld != null && !clientaccountIdOld.equals(clientaccountIdNew)) {
                clientaccountIdOld.getPaymentsCollection().remove(payments);
                clientaccountIdOld = em.merge(clientaccountIdOld);
            }
            if (clientaccountIdNew != null && !clientaccountIdNew.equals(clientaccountIdOld)) {
                clientaccountIdNew.getPaymentsCollection().add(payments);
                clientaccountIdNew = em.merge(clientaccountIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = payments.getId();
                if (findPayments(id) == null) {
                    throw new NonexistentEntityException("The payments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments payments;
            try {
                payments = em.getReference(Payments.class, id);
                payments.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payments with id " + id + " no longer exists.", enfe);
            }
            Clients clientId = payments.getClientId();
            if (clientId != null) {
                clientId.getPaymentsCollection().remove(payments);
                clientId = em.merge(clientId);
            }
           
            Clientsaccounts clientaccountId = payments.getClientaccountId();
            if (clientaccountId != null) {
                clientaccountId.getPaymentsCollection().remove(payments);
                clientaccountId = em.merge(clientaccountId);
            }
            em.remove(payments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Payments> findPaymentsEntities() {
        return findPaymentsEntities(true, -1, -1);
    }

    public List<Payments> findPaymentsEntities(int maxResults, int firstResult) {
        return findPaymentsEntities(false, maxResults, firstResult);
    }

    private List<Payments> findPaymentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payments.class));
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

    public Payments findPayments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payments.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payments> rt = cq.from(Payments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
}
