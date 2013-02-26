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
import com.productosur.hive.entities.Uom;
import com.productosur.hive.entities.UomCovertionFactors;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class UomCovertionFactorsJpaController implements Serializable {

    public UomCovertionFactorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UomCovertionFactors uomCovertionFactors) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Uom uomToId = uomCovertionFactors.getUomToId();
            if (uomToId != null) {
                uomToId = em.getReference(uomToId.getClass(), uomToId.getId());
                uomCovertionFactors.setUomToId(uomToId);
            }
            Uom uomFromId = uomCovertionFactors.getUomFromId();
            if (uomFromId != null) {
                uomFromId = em.getReference(uomFromId.getClass(), uomFromId.getId());
                uomCovertionFactors.setUomFromId(uomFromId);
            }
            em.persist(uomCovertionFactors);
            if (uomToId != null) {
                uomToId.getUomCovertionFactorsCollection().add(uomCovertionFactors);
                uomToId = em.merge(uomToId);
            }
            if (uomFromId != null) {
                uomFromId.getUomCovertionFactorsCollection().add(uomCovertionFactors);
                uomFromId = em.merge(uomFromId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UomCovertionFactors uomCovertionFactors) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UomCovertionFactors persistentUomCovertionFactors = em.find(UomCovertionFactors.class, uomCovertionFactors.getId());
            Uom uomToIdOld = persistentUomCovertionFactors.getUomToId();
            Uom uomToIdNew = uomCovertionFactors.getUomToId();
            Uom uomFromIdOld = persistentUomCovertionFactors.getUomFromId();
            Uom uomFromIdNew = uomCovertionFactors.getUomFromId();
            if (uomToIdNew != null) {
                uomToIdNew = em.getReference(uomToIdNew.getClass(), uomToIdNew.getId());
                uomCovertionFactors.setUomToId(uomToIdNew);
            }
            if (uomFromIdNew != null) {
                uomFromIdNew = em.getReference(uomFromIdNew.getClass(), uomFromIdNew.getId());
                uomCovertionFactors.setUomFromId(uomFromIdNew);
            }
            uomCovertionFactors = em.merge(uomCovertionFactors);
            if (uomToIdOld != null && !uomToIdOld.equals(uomToIdNew)) {
                uomToIdOld.getUomCovertionFactorsCollection().remove(uomCovertionFactors);
                uomToIdOld = em.merge(uomToIdOld);
            }
            if (uomToIdNew != null && !uomToIdNew.equals(uomToIdOld)) {
                uomToIdNew.getUomCovertionFactorsCollection().add(uomCovertionFactors);
                uomToIdNew = em.merge(uomToIdNew);
            }
            if (uomFromIdOld != null && !uomFromIdOld.equals(uomFromIdNew)) {
                uomFromIdOld.getUomCovertionFactorsCollection().remove(uomCovertionFactors);
                uomFromIdOld = em.merge(uomFromIdOld);
            }
            if (uomFromIdNew != null && !uomFromIdNew.equals(uomFromIdOld)) {
                uomFromIdNew.getUomCovertionFactorsCollection().add(uomCovertionFactors);
                uomFromIdNew = em.merge(uomFromIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = uomCovertionFactors.getId();
                if (findUomCovertionFactors(id) == null) {
                    throw new NonexistentEntityException("The uomCovertionFactors with id " + id + " no longer exists.");
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
            UomCovertionFactors uomCovertionFactors;
            try {
                uomCovertionFactors = em.getReference(UomCovertionFactors.class, id);
                uomCovertionFactors.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The uomCovertionFactors with id " + id + " no longer exists.", enfe);
            }
            Uom uomToId = uomCovertionFactors.getUomToId();
            if (uomToId != null) {
                uomToId.getUomCovertionFactorsCollection().remove(uomCovertionFactors);
                uomToId = em.merge(uomToId);
            }
            Uom uomFromId = uomCovertionFactors.getUomFromId();
            if (uomFromId != null) {
                uomFromId.getUomCovertionFactorsCollection().remove(uomCovertionFactors);
                uomFromId = em.merge(uomFromId);
            }
            em.remove(uomCovertionFactors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UomCovertionFactors> findUomCovertionFactorsEntities() {
        return findUomCovertionFactorsEntities(true, -1, -1);
    }

    public List<UomCovertionFactors> findUomCovertionFactorsEntities(int maxResults, int firstResult) {
        return findUomCovertionFactorsEntities(false, maxResults, firstResult);
    }

    private List<UomCovertionFactors> findUomCovertionFactorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UomCovertionFactors.class));
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

    public UomCovertionFactors findUomCovertionFactors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UomCovertionFactors.class, id);
        } finally {
            em.close();
        }
    }

    public int getUomCovertionFactorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UomCovertionFactors> rt = cq.from(UomCovertionFactors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
