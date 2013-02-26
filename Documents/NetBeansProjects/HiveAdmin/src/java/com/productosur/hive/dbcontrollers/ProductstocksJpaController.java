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
import com.productosur.hive.entities.Productstocks;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class ProductstocksJpaController implements Serializable {

    public ProductstocksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productstocks productstocks) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products productId = productstocks.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                productstocks.setProductId(productId);
            }
            em.persist(productstocks);
            if (productId != null) {
                productId.getProductstocksCollection().add(productstocks);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productstocks productstocks) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productstocks persistentProductstocks = em.find(Productstocks.class, productstocks.getId());
            Products productIdOld = persistentProductstocks.getProductId();
            Products productIdNew = productstocks.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                productstocks.setProductId(productIdNew);
            }
            productstocks = em.merge(productstocks);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getProductstocksCollection().remove(productstocks);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getProductstocksCollection().add(productstocks);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productstocks.getId();
                if (findProductstocks(id) == null) {
                    throw new NonexistentEntityException("The productstocks with id " + id + " no longer exists.");
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
            Productstocks productstocks;
            try {
                productstocks = em.getReference(Productstocks.class, id);
                productstocks.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productstocks with id " + id + " no longer exists.", enfe);
            }
            Products productId = productstocks.getProductId();
            if (productId != null) {
                productId.getProductstocksCollection().remove(productstocks);
                productId = em.merge(productId);
            }
            em.remove(productstocks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productstocks> findProductstocksEntities() {
        return findProductstocksEntities(true, -1, -1);
    }

    public List<Productstocks> findProductstocksEntities(int maxResults, int firstResult) {
        return findProductstocksEntities(false, maxResults, firstResult);
    }

    private List<Productstocks> findProductstocksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productstocks.class));
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

    public Productstocks findProductstocks(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productstocks.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductstocksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productstocks> rt = cq.from(Productstocks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public Productstocks findProductStockEntities(Products prod) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("Select o from Productstocks o where o.productId=:arg1");
            q.setParameter("arg1", prod);
            return (Productstocks) q.getSingleResult();
        }catch (Exception e) {
            return null;
          }
        finally {
            em.close();
        }
    }
    
}
