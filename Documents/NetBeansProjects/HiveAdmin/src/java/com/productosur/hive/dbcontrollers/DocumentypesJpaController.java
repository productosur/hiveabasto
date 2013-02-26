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
public class DocumentypesJpaController implements Serializable {

    public DocumentypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentypes documentypes) {
        if (documentypes.getPaydocumentsCollection() == null) {
            documentypes.setPaydocumentsCollection(new ArrayList<Paydocuments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Paydocuments> attachedPaydocumentsCollection = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionPaydocumentsToAttach : documentypes.getPaydocumentsCollection()) {
                paydocumentsCollectionPaydocumentsToAttach = em.getReference(paydocumentsCollectionPaydocumentsToAttach.getClass(), paydocumentsCollectionPaydocumentsToAttach.getId());
                attachedPaydocumentsCollection.add(paydocumentsCollectionPaydocumentsToAttach);
            }
            documentypes.setPaydocumentsCollection(attachedPaydocumentsCollection);
            em.persist(documentypes);
            for (Paydocuments paydocumentsCollectionPaydocuments : documentypes.getPaydocumentsCollection()) {
                Documentypes oldDocumentypeIdOfPaydocumentsCollectionPaydocuments = paydocumentsCollectionPaydocuments.getDocumentypeId();
                paydocumentsCollectionPaydocuments.setDocumentypeId(documentypes);
                paydocumentsCollectionPaydocuments = em.merge(paydocumentsCollectionPaydocuments);
                if (oldDocumentypeIdOfPaydocumentsCollectionPaydocuments != null) {
                    oldDocumentypeIdOfPaydocumentsCollectionPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionPaydocuments);
                    oldDocumentypeIdOfPaydocumentsCollectionPaydocuments = em.merge(oldDocumentypeIdOfPaydocumentsCollectionPaydocuments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentypes documentypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentypes persistentDocumentypes = em.find(Documentypes.class, documentypes.getId());
            Collection<Paydocuments> paydocumentsCollectionOld = persistentDocumentypes.getPaydocumentsCollection();
            Collection<Paydocuments> paydocumentsCollectionNew = documentypes.getPaydocumentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Paydocuments paydocumentsCollectionOldPaydocuments : paydocumentsCollectionOld) {
                if (!paydocumentsCollectionNew.contains(paydocumentsCollectionOldPaydocuments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paydocuments " + paydocumentsCollectionOldPaydocuments + " since its documentypeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Paydocuments> attachedPaydocumentsCollectionNew = new ArrayList<Paydocuments>();
            for (Paydocuments paydocumentsCollectionNewPaydocumentsToAttach : paydocumentsCollectionNew) {
                paydocumentsCollectionNewPaydocumentsToAttach = em.getReference(paydocumentsCollectionNewPaydocumentsToAttach.getClass(), paydocumentsCollectionNewPaydocumentsToAttach.getId());
                attachedPaydocumentsCollectionNew.add(paydocumentsCollectionNewPaydocumentsToAttach);
            }
            paydocumentsCollectionNew = attachedPaydocumentsCollectionNew;
            documentypes.setPaydocumentsCollection(paydocumentsCollectionNew);
            documentypes = em.merge(documentypes);
            for (Paydocuments paydocumentsCollectionNewPaydocuments : paydocumentsCollectionNew) {
                if (!paydocumentsCollectionOld.contains(paydocumentsCollectionNewPaydocuments)) {
                    Documentypes oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments = paydocumentsCollectionNewPaydocuments.getDocumentypeId();
                    paydocumentsCollectionNewPaydocuments.setDocumentypeId(documentypes);
                    paydocumentsCollectionNewPaydocuments = em.merge(paydocumentsCollectionNewPaydocuments);
                    if (oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments != null && !oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments.equals(documentypes)) {
                        oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments.getPaydocumentsCollection().remove(paydocumentsCollectionNewPaydocuments);
                        oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments = em.merge(oldDocumentypeIdOfPaydocumentsCollectionNewPaydocuments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentypes.getId();
                if (findDocumentypes(id) == null) {
                    throw new NonexistentEntityException("The documentypes with id " + id + " no longer exists.");
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
            Documentypes documentypes;
            try {
                documentypes = em.getReference(Documentypes.class, id);
                documentypes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Paydocuments> paydocumentsCollectionOrphanCheck = documentypes.getPaydocumentsCollection();
            for (Paydocuments paydocumentsCollectionOrphanCheckPaydocuments : paydocumentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documentypes (" + documentypes + ") cannot be destroyed since the Paydocuments " + paydocumentsCollectionOrphanCheckPaydocuments + " in its paydocumentsCollection field has a non-nullable documentypeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(documentypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentypes> findDocumentypesEntities() {
        return findDocumentypesEntities(true, -1, -1);
    }

    public List<Documentypes> findDocumentypesEntities(int maxResults, int firstResult) {
        return findDocumentypesEntities(false, maxResults, firstResult);
    }

    private List<Documentypes> findDocumentypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentypes.class));
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

    public Documentypes findDocumentypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentypes> rt = cq.from(Documentypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
