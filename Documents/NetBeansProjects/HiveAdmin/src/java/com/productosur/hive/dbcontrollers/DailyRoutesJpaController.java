/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.DailyRoutes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Subsidiaries;
import com.productosur.hive.entities.Employees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class DailyRoutesJpaController implements Serializable {

    public DailyRoutesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DailyRoutes dailyRoutes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subsidiaries subsidiaryId = dailyRoutes.getSubsidiaryId();
            if (subsidiaryId != null) {
                subsidiaryId = em.getReference(subsidiaryId.getClass(), subsidiaryId.getId());
                dailyRoutes.setSubsidiaryId(subsidiaryId);
            }
            Employees employeeId = dailyRoutes.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getId());
                dailyRoutes.setEmployeeId(employeeId);
            }
            em.persist(dailyRoutes);
           
            if (employeeId != null) {
                employeeId.getDailyRoutesCollection().add(dailyRoutes);
                employeeId = em.merge(employeeId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DailyRoutes dailyRoutes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DailyRoutes persistentDailyRoutes = em.find(DailyRoutes.class, dailyRoutes.getId());
            Subsidiaries subsidiaryIdOld = persistentDailyRoutes.getSubsidiaryId();
            Subsidiaries subsidiaryIdNew = dailyRoutes.getSubsidiaryId();
            Employees employeeIdOld = persistentDailyRoutes.getEmployeeId();
            Employees employeeIdNew = dailyRoutes.getEmployeeId();
            if (subsidiaryIdNew != null) {
                subsidiaryIdNew = em.getReference(subsidiaryIdNew.getClass(), subsidiaryIdNew.getId());
                dailyRoutes.setSubsidiaryId(subsidiaryIdNew);
            }
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getId());
                dailyRoutes.setEmployeeId(employeeIdNew);
            }
            dailyRoutes = em.merge(dailyRoutes);
            
            
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getDailyRoutesCollection().remove(dailyRoutes);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getDailyRoutesCollection().add(dailyRoutes);
                employeeIdNew = em.merge(employeeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dailyRoutes.getId();
                if (findDailyRoutes(id) == null) {
                    throw new NonexistentEntityException("The dailyRoutes with id " + id + " no longer exists.");
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
            DailyRoutes dailyRoutes;
            try {
                dailyRoutes = em.getReference(DailyRoutes.class, id);
                dailyRoutes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dailyRoutes with id " + id + " no longer exists.", enfe);
            }
            
            Employees employeeId = dailyRoutes.getEmployeeId();
            if (employeeId != null) {
                employeeId.getDailyRoutesCollection().remove(dailyRoutes);
                employeeId = em.merge(employeeId);
            }
            em.remove(dailyRoutes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DailyRoutes> findDailyRoutesEntities() {
        return findDailyRoutesEntities(true, -1, -1);
    }

    public List<DailyRoutes> findDailyRoutesEntities(int maxResults, int firstResult) {
        return findDailyRoutesEntities(false, maxResults, firstResult);
    }

    private List<DailyRoutes> findDailyRoutesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DailyRoutes.class));
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

    public DailyRoutes findDailyRoutes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DailyRoutes.class, id);
        } finally {
            em.close();
        }
    }

    public int getDailyRoutesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DailyRoutes> rt = cq.from(DailyRoutes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
