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
import com.productosur.hive.entities.Permissions;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.Employees;
import com.productosur.hive.entities.EmployeesRoles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class EmployeesRolesJpaController implements Serializable {

    public EmployeesRolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmployeesRoles employeesRoles) {
        if (employeesRoles.getPermissionsCollection() == null) {
            employeesRoles.setPermissionsCollection(new ArrayList<Permissions>());
        }
        if (employeesRoles.getEmployeesCollection() == null) {
            employeesRoles.setEmployeesCollection(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Permissions> attachedPermissionsCollection = new ArrayList<Permissions>();
            for (Permissions permissionsCollectionPermissionsToAttach : employeesRoles.getPermissionsCollection()) {
                permissionsCollectionPermissionsToAttach = em.getReference(permissionsCollectionPermissionsToAttach.getClass(), permissionsCollectionPermissionsToAttach.getId());
                attachedPermissionsCollection.add(permissionsCollectionPermissionsToAttach);
            }
            employeesRoles.setPermissionsCollection(attachedPermissionsCollection);
            Collection<Employees> attachedEmployeesCollection = new ArrayList<Employees>();
            for (Employees employeesCollectionEmployeesToAttach : employeesRoles.getEmployeesCollection()) {
                employeesCollectionEmployeesToAttach = em.getReference(employeesCollectionEmployeesToAttach.getClass(), employeesCollectionEmployeesToAttach.getId());
                attachedEmployeesCollection.add(employeesCollectionEmployeesToAttach);
            }
            employeesRoles.setEmployeesCollection(attachedEmployeesCollection);
            em.persist(employeesRoles);
            for (Permissions permissionsCollectionPermissions : employeesRoles.getPermissionsCollection()) {
                EmployeesRoles oldEmployeeroleIdOfPermissionsCollectionPermissions = permissionsCollectionPermissions.getEmployeeroleId();
                permissionsCollectionPermissions.setEmployeeroleId(employeesRoles);
                permissionsCollectionPermissions = em.merge(permissionsCollectionPermissions);
                if (oldEmployeeroleIdOfPermissionsCollectionPermissions != null) {
                    oldEmployeeroleIdOfPermissionsCollectionPermissions.getPermissionsCollection().remove(permissionsCollectionPermissions);
                    oldEmployeeroleIdOfPermissionsCollectionPermissions = em.merge(oldEmployeeroleIdOfPermissionsCollectionPermissions);
                }
            }
            for (Employees employeesCollectionEmployees : employeesRoles.getEmployeesCollection()) {
                EmployeesRoles oldEmployeeroleIdOfEmployeesCollectionEmployees = employeesCollectionEmployees.getEmployeeroleId();
                employeesCollectionEmployees.setEmployeeroleId(employeesRoles);
                employeesCollectionEmployees = em.merge(employeesCollectionEmployees);
                if (oldEmployeeroleIdOfEmployeesCollectionEmployees != null) {
                    oldEmployeeroleIdOfEmployeesCollectionEmployees.getEmployeesCollection().remove(employeesCollectionEmployees);
                    oldEmployeeroleIdOfEmployeesCollectionEmployees = em.merge(oldEmployeeroleIdOfEmployeesCollectionEmployees);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmployeesRoles employeesRoles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmployeesRoles persistentEmployeesRoles = em.find(EmployeesRoles.class, employeesRoles.getId());
            Collection<Permissions> permissionsCollectionOld = persistentEmployeesRoles.getPermissionsCollection();
            Collection<Permissions> permissionsCollectionNew = employeesRoles.getPermissionsCollection();
            Collection<Employees> employeesCollectionOld = persistentEmployeesRoles.getEmployeesCollection();
            Collection<Employees> employeesCollectionNew = employeesRoles.getEmployeesCollection();
            List<String> illegalOrphanMessages = null;
            for (Permissions permissionsCollectionOldPermissions : permissionsCollectionOld) {
                if (!permissionsCollectionNew.contains(permissionsCollectionOldPermissions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permissions " + permissionsCollectionOldPermissions + " since its employeeroleId field is not nullable.");
                }
            }
            for (Employees employeesCollectionOldEmployees : employeesCollectionOld) {
                if (!employeesCollectionNew.contains(employeesCollectionOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesCollectionOldEmployees + " since its employeeroleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Permissions> attachedPermissionsCollectionNew = new ArrayList<Permissions>();
            for (Permissions permissionsCollectionNewPermissionsToAttach : permissionsCollectionNew) {
                permissionsCollectionNewPermissionsToAttach = em.getReference(permissionsCollectionNewPermissionsToAttach.getClass(), permissionsCollectionNewPermissionsToAttach.getId());
                attachedPermissionsCollectionNew.add(permissionsCollectionNewPermissionsToAttach);
            }
            permissionsCollectionNew = attachedPermissionsCollectionNew;
            employeesRoles.setPermissionsCollection(permissionsCollectionNew);
            Collection<Employees> attachedEmployeesCollectionNew = new ArrayList<Employees>();
            for (Employees employeesCollectionNewEmployeesToAttach : employeesCollectionNew) {
                employeesCollectionNewEmployeesToAttach = em.getReference(employeesCollectionNewEmployeesToAttach.getClass(), employeesCollectionNewEmployeesToAttach.getId());
                attachedEmployeesCollectionNew.add(employeesCollectionNewEmployeesToAttach);
            }
            employeesCollectionNew = attachedEmployeesCollectionNew;
            employeesRoles.setEmployeesCollection(employeesCollectionNew);
            employeesRoles = em.merge(employeesRoles);
            for (Permissions permissionsCollectionNewPermissions : permissionsCollectionNew) {
                if (!permissionsCollectionOld.contains(permissionsCollectionNewPermissions)) {
                    EmployeesRoles oldEmployeeroleIdOfPermissionsCollectionNewPermissions = permissionsCollectionNewPermissions.getEmployeeroleId();
                    permissionsCollectionNewPermissions.setEmployeeroleId(employeesRoles);
                    permissionsCollectionNewPermissions = em.merge(permissionsCollectionNewPermissions);
                    if (oldEmployeeroleIdOfPermissionsCollectionNewPermissions != null && !oldEmployeeroleIdOfPermissionsCollectionNewPermissions.equals(employeesRoles)) {
                        oldEmployeeroleIdOfPermissionsCollectionNewPermissions.getPermissionsCollection().remove(permissionsCollectionNewPermissions);
                        oldEmployeeroleIdOfPermissionsCollectionNewPermissions = em.merge(oldEmployeeroleIdOfPermissionsCollectionNewPermissions);
                    }
                }
            }
            for (Employees employeesCollectionNewEmployees : employeesCollectionNew) {
                if (!employeesCollectionOld.contains(employeesCollectionNewEmployees)) {
                    EmployeesRoles oldEmployeeroleIdOfEmployeesCollectionNewEmployees = employeesCollectionNewEmployees.getEmployeeroleId();
                    employeesCollectionNewEmployees.setEmployeeroleId(employeesRoles);
                    employeesCollectionNewEmployees = em.merge(employeesCollectionNewEmployees);
                    if (oldEmployeeroleIdOfEmployeesCollectionNewEmployees != null && !oldEmployeeroleIdOfEmployeesCollectionNewEmployees.equals(employeesRoles)) {
                        oldEmployeeroleIdOfEmployeesCollectionNewEmployees.getEmployeesCollection().remove(employeesCollectionNewEmployees);
                        oldEmployeeroleIdOfEmployeesCollectionNewEmployees = em.merge(oldEmployeeroleIdOfEmployeesCollectionNewEmployees);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employeesRoles.getId();
                if (findEmployeesRoles(id) == null) {
                    throw new NonexistentEntityException("The employeesRoles with id " + id + " no longer exists.");
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
            EmployeesRoles employeesRoles;
            try {
                employeesRoles = em.getReference(EmployeesRoles.class, id);
                employeesRoles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employeesRoles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Permissions> permissionsCollectionOrphanCheck = employeesRoles.getPermissionsCollection();
            for (Permissions permissionsCollectionOrphanCheckPermissions : permissionsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmployeesRoles (" + employeesRoles + ") cannot be destroyed since the Permissions " + permissionsCollectionOrphanCheckPermissions + " in its permissionsCollection field has a non-nullable employeeroleId field.");
            }
            Collection<Employees> employeesCollectionOrphanCheck = employeesRoles.getEmployeesCollection();
            for (Employees employeesCollectionOrphanCheckEmployees : employeesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmployeesRoles (" + employeesRoles + ") cannot be destroyed since the Employees " + employeesCollectionOrphanCheckEmployees + " in its employeesCollection field has a non-nullable employeeroleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employeesRoles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmployeesRoles> findEmployeesRolesEntities() {
        return findEmployeesRolesEntities(true, -1, -1);
    }

    public List<EmployeesRoles> findEmployeesRolesEntities(int maxResults, int firstResult) {
        return findEmployeesRolesEntities(false, maxResults, firstResult);
    }

    private List<EmployeesRoles> findEmployeesRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmployeesRoles.class));
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

    public EmployeesRoles findEmployeesRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmployeesRoles.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeesRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmployeesRoles> rt = cq.from(EmployeesRoles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
