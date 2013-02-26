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
import com.productosur.hive.entities.EmployeesRoles;
import com.productosur.hive.entities.Accesses;
import com.productosur.hive.entities.Permissions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class PermissionsJpaController implements Serializable {

    public PermissionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permissions permissions) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmployeesRoles employeeroleId = permissions.getEmployeeroleId();
            if (employeeroleId != null) {
                employeeroleId = em.getReference(employeeroleId.getClass(), employeeroleId.getId());
                permissions.setEmployeeroleId(employeeroleId);
            }
            Accesses accId = permissions.getAccId();
            if (accId != null) {
                accId = em.getReference(accId.getClass(), accId.getId());
                permissions.setAccId(accId);
            }
            em.persist(permissions);
            if (employeeroleId != null) {
                employeeroleId.getPermissionsCollection().add(permissions);
                employeeroleId = em.merge(employeeroleId);
            }
            if (accId != null) {
                accId.getPermissionsCollection().add(permissions);
                accId = em.merge(accId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permissions permissions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permissions persistentPermissions = em.find(Permissions.class, permissions.getId());
            EmployeesRoles employeeroleIdOld = persistentPermissions.getEmployeeroleId();
            EmployeesRoles employeeroleIdNew = permissions.getEmployeeroleId();
            Accesses accIdOld = persistentPermissions.getAccId();
            Accesses accIdNew = permissions.getAccId();
            if (employeeroleIdNew != null) {
                employeeroleIdNew = em.getReference(employeeroleIdNew.getClass(), employeeroleIdNew.getId());
                permissions.setEmployeeroleId(employeeroleIdNew);
            }
            if (accIdNew != null) {
                accIdNew = em.getReference(accIdNew.getClass(), accIdNew.getId());
                permissions.setAccId(accIdNew);
            }
            permissions = em.merge(permissions);
            if (employeeroleIdOld != null && !employeeroleIdOld.equals(employeeroleIdNew)) {
                employeeroleIdOld.getPermissionsCollection().remove(permissions);
                employeeroleIdOld = em.merge(employeeroleIdOld);
            }
            if (employeeroleIdNew != null && !employeeroleIdNew.equals(employeeroleIdOld)) {
                employeeroleIdNew.getPermissionsCollection().add(permissions);
                employeeroleIdNew = em.merge(employeeroleIdNew);
            }
            if (accIdOld != null && !accIdOld.equals(accIdNew)) {
                accIdOld.getPermissionsCollection().remove(permissions);
                accIdOld = em.merge(accIdOld);
            }
            if (accIdNew != null && !accIdNew.equals(accIdOld)) {
                accIdNew.getPermissionsCollection().add(permissions);
                accIdNew = em.merge(accIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permissions.getId();
                if (findPermissions(id) == null) {
                    throw new NonexistentEntityException("The permissions with id " + id + " no longer exists.");
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
            Permissions permissions;
            try {
                permissions = em.getReference(Permissions.class, id);
                permissions.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permissions with id " + id + " no longer exists.", enfe);
            }
            EmployeesRoles employeeroleId = permissions.getEmployeeroleId();
            if (employeeroleId != null) {
                employeeroleId.getPermissionsCollection().remove(permissions);
                employeeroleId = em.merge(employeeroleId);
            }
            Accesses accId = permissions.getAccId();
            if (accId != null) {
                accId.getPermissionsCollection().remove(permissions);
                accId = em.merge(accId);
            }
            em.remove(permissions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permissions> findPermissionsEntities() {
        return findPermissionsEntities(true, -1, -1);
    }

    public List<Permissions> findPermissionsEntities(int maxResults, int firstResult) {
        return findPermissionsEntities(false, maxResults, firstResult);
    }

    private List<Permissions> findPermissionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permissions.class));
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

    public Permissions findPermissions(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permissions.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermissionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permissions> rt = cq.from(Permissions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
