/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.dbcontrollers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Employees;
import com.productosur.hive.entities.TraceRoute;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class TraceRouteJpaController implements Serializable {

    public TraceRouteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TraceRoute traceRoute) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees employeeId = traceRoute.getEmployeeId();
            if (employeeId != null) {
                employeeId = em.getReference(employeeId.getClass(), employeeId.getId());
                traceRoute.setEmployeeId(employeeId);
            }
            em.persist(traceRoute);
            if (employeeId != null) {
                employeeId.getTraceRouteCollection().add(traceRoute);
                employeeId = em.merge(employeeId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTraceRoute(traceRoute.getId()) != null) {
                throw new PreexistingEntityException("TraceRoute " + traceRoute + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TraceRoute traceRoute) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TraceRoute persistentTraceRoute = em.find(TraceRoute.class, traceRoute.getId());
            Employees employeeIdOld = persistentTraceRoute.getEmployeeId();
            Employees employeeIdNew = traceRoute.getEmployeeId();
            if (employeeIdNew != null) {
                employeeIdNew = em.getReference(employeeIdNew.getClass(), employeeIdNew.getId());
                traceRoute.setEmployeeId(employeeIdNew);
            }
            traceRoute = em.merge(traceRoute);
            if (employeeIdOld != null && !employeeIdOld.equals(employeeIdNew)) {
                employeeIdOld.getTraceRouteCollection().remove(traceRoute);
                employeeIdOld = em.merge(employeeIdOld);
            }
            if (employeeIdNew != null && !employeeIdNew.equals(employeeIdOld)) {
                employeeIdNew.getTraceRouteCollection().add(traceRoute);
                employeeIdNew = em.merge(employeeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = traceRoute.getId();
                if (findTraceRoute(id) == null) {
                    throw new NonexistentEntityException("The traceRoute with id " + id + " no longer exists.");
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
            TraceRoute traceRoute;
            try {
                traceRoute = em.getReference(TraceRoute.class, id);
                traceRoute.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The traceRoute with id " + id + " no longer exists.", enfe);
            }
            Employees employeeId = traceRoute.getEmployeeId();
            if (employeeId != null) {
                employeeId.getTraceRouteCollection().remove(traceRoute);
                employeeId = em.merge(employeeId);
            }
            em.remove(traceRoute);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TraceRoute> findTraceRouteEntities() {
        return findTraceRouteEntities(true, -1, -1);
    }

    public List<TraceRoute> findTraceRouteEntities(int maxResults, int firstResult) {
        return findTraceRouteEntities(false, maxResults, firstResult);
    }

    private List<TraceRoute> findTraceRouteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TraceRoute.class));
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

    public TraceRoute findTraceRoute(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TraceRoute.class, id);
        } finally {
            em.close();
        }
    }

    public int getTraceRouteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TraceRoute> rt = cq.from(TraceRoute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
