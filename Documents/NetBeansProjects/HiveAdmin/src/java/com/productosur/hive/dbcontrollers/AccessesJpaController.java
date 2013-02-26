/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.IllegalOrphanException;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Accesses;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.Permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class AccessesJpaController implements Serializable {

    public AccessesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accesses accesses) {
        if (accesses.getPermissionsCollection() == null) {
            accesses.setPermissionsCollection(new ArrayList<Permissions>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Permissions> attachedPermissionsCollection = new ArrayList<Permissions>();
            for (Permissions permissionsCollectionPermissionsToAttach : accesses.getPermissionsCollection()) {
                permissionsCollectionPermissionsToAttach = em.getReference(permissionsCollectionPermissionsToAttach.getClass(), permissionsCollectionPermissionsToAttach.getId());
                attachedPermissionsCollection.add(permissionsCollectionPermissionsToAttach);
            }
            accesses.setPermissionsCollection(attachedPermissionsCollection);
            em.persist(accesses);
            for (Permissions permissionsCollectionPermissions : accesses.getPermissionsCollection()) {
                Accesses oldAccIdOfPermissionsCollectionPermissions = permissionsCollectionPermissions.getAccId();
                permissionsCollectionPermissions.setAccId(accesses);
                permissionsCollectionPermissions = em.merge(permissionsCollectionPermissions);
                if (oldAccIdOfPermissionsCollectionPermissions != null) {
                    oldAccIdOfPermissionsCollectionPermissions.getPermissionsCollection().remove(permissionsCollectionPermissions);
                    oldAccIdOfPermissionsCollectionPermissions = em.merge(oldAccIdOfPermissionsCollectionPermissions);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accesses accesses) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accesses persistentAccesses = em.find(Accesses.class, accesses.getId());
            Collection<Permissions> permissionsCollectionOld = persistentAccesses.getPermissionsCollection();
            Collection<Permissions> permissionsCollectionNew = accesses.getPermissionsCollection();
            List<String> illegalOrphanMessages = null;
            for (Permissions permissionsCollectionOldPermissions : permissionsCollectionOld) {
                if (!permissionsCollectionNew.contains(permissionsCollectionOldPermissions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permissions " + permissionsCollectionOldPermissions + " since its accId field is not nullable.");
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
            accesses.setPermissionsCollection(permissionsCollectionNew);
            accesses = em.merge(accesses);
            for (Permissions permissionsCollectionNewPermissions : permissionsCollectionNew) {
                if (!permissionsCollectionOld.contains(permissionsCollectionNewPermissions)) {
                    Accesses oldAccIdOfPermissionsCollectionNewPermissions = permissionsCollectionNewPermissions.getAccId();
                    permissionsCollectionNewPermissions.setAccId(accesses);
                    permissionsCollectionNewPermissions = em.merge(permissionsCollectionNewPermissions);
                    if (oldAccIdOfPermissionsCollectionNewPermissions != null && !oldAccIdOfPermissionsCollectionNewPermissions.equals(accesses)) {
                        oldAccIdOfPermissionsCollectionNewPermissions.getPermissionsCollection().remove(permissionsCollectionNewPermissions);
                        oldAccIdOfPermissionsCollectionNewPermissions = em.merge(oldAccIdOfPermissionsCollectionNewPermissions);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accesses.getId();
                if (findAccesses(id) == null) {
                    throw new NonexistentEntityException("The accesses with id " + id + " no longer exists.");
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
            Accesses accesses;
            try {
                accesses = em.getReference(Accesses.class, id);
                accesses.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accesses with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Permissions> permissionsCollectionOrphanCheck = accesses.getPermissionsCollection();
            for (Permissions permissionsCollectionOrphanCheckPermissions : permissionsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Accesses (" + accesses + ") cannot be destroyed since the Permissions " + permissionsCollectionOrphanCheckPermissions + " in its permissionsCollection field has a non-nullable accId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(accesses);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accesses> findAccessesEntities() {
        return findAccessesEntities(true, -1, -1);
    }

    public List<Accesses> findAccessesEntities(int maxResults, int firstResult) {
        return findAccessesEntities(false, maxResults, firstResult);
    }

    private List<Accesses> findAccessesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accesses.class));
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

    public Accesses findAccesses(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accesses.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccessesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accesses> rt = cq.from(Accesses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
