/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Productosur
 */
public class PaynumberselectionsJpaController implements Serializable {

    public PaynumberselectionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paynumberselections paynumberselections) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(paynumberselections);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paynumberselections paynumberselections) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            paynumberselections = em.merge(paynumberselections);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paynumberselections.getId();
                if (findPaynumberselections(id) == null) {
                    throw new NonexistentEntityException("The paynumberselections with id " + id + " no longer exists.");
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
            Paynumberselections paynumberselections;
            try {
                paynumberselections = em.getReference(Paynumberselections.class, id);
                paynumberselections.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paynumberselections with id " + id + " no longer exists.", enfe);
            }
            em.remove(paynumberselections);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paynumberselections> findPaynumberselectionsEntities() {
        return findPaynumberselectionsEntities(true, -1, -1);
    }

    public List<Paynumberselections> findPaynumberselectionsEntities(int maxResults, int firstResult) {
        return findPaynumberselectionsEntities(false, maxResults, firstResult);
    }

    private List<Paynumberselections> findPaynumberselectionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paynumberselections.class));
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

    public Paynumberselections findPaynumberselections(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paynumberselections.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaynumberselectionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paynumberselections> rt = cq.from(Paynumberselections.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
