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
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Specialdiscounts;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class SpecialdiscountsJpaController implements Serializable {

    public SpecialdiscountsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Specialdiscounts specialdiscounts) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products productId = specialdiscounts.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                specialdiscounts.setProductId(productId);
            }
            em.persist(specialdiscounts);
            if (productId != null) {
                productId.getSpecialdiscountsCollection().add(specialdiscounts);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Specialdiscounts specialdiscounts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialdiscounts persistentSpecialdiscounts = em.find(Specialdiscounts.class, specialdiscounts.getId());
            Products productIdOld = persistentSpecialdiscounts.getProductId();
            Products productIdNew = specialdiscounts.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                specialdiscounts.setProductId(productIdNew);
            }
            specialdiscounts = em.merge(specialdiscounts);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getSpecialdiscountsCollection().remove(specialdiscounts);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getSpecialdiscountsCollection().add(specialdiscounts);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = specialdiscounts.getId();
                if (findSpecialdiscounts(id) == null) {
                    throw new NonexistentEntityException("The specialdiscounts with id " + id + " no longer exists.");
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
            Specialdiscounts specialdiscounts;
            try {
                specialdiscounts = em.getReference(Specialdiscounts.class, id);
                specialdiscounts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The specialdiscounts with id " + id + " no longer exists.", enfe);
            }
            Products productId = specialdiscounts.getProductId();
            if (productId != null) {
                productId.getSpecialdiscountsCollection().remove(specialdiscounts);
                productId = em.merge(productId);
            }
            em.remove(specialdiscounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Specialdiscounts> findSpecialdiscountsEntities() {
        return findSpecialdiscountsEntities(true, -1, -1);
    }

    public List<Specialdiscounts> findSpecialdiscountsEntities(int maxResults, int firstResult) {
        return findSpecialdiscountsEntities(false, maxResults, firstResult);
    }

    private List<Specialdiscounts> findSpecialdiscountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Specialdiscounts.class));
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

    public Specialdiscounts findSpecialdiscounts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Specialdiscounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpecialdiscountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Specialdiscounts> rt = cq.from(Specialdiscounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
