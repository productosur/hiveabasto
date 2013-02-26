/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.productosur.hive.dbcontrollers.exceptions.*;
import com.productosur.hive.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class CurrenciesJpaController implements Serializable {

    public CurrenciesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Currencies currencies) {
        if (currencies.getProductsCollection() == null) {
            currencies.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : currencies.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            currencies.setProductsCollection(attachedProductsCollection);
            em.persist(currencies);
            for (Products productsCollectionProducts : currencies.getProductsCollection()) {
                Currencies oldCurrencyIdOfProductsCollectionProducts = productsCollectionProducts.getCurrencyId();
                productsCollectionProducts.setCurrencyId(currencies);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldCurrencyIdOfProductsCollectionProducts != null) {
                    oldCurrencyIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldCurrencyIdOfProductsCollectionProducts = em.merge(oldCurrencyIdOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Currencies currencies) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Currencies persistentCurrencies = em.find(Currencies.class, currencies.getId());
            Collection<Products> productsCollectionOld = persistentCurrencies.getProductsCollection();
            Collection<Products> productsCollectionNew = currencies.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its currencyId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            currencies.setProductsCollection(productsCollectionNew);
            currencies = em.merge(currencies);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Currencies oldCurrencyIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getCurrencyId();
                    productsCollectionNewProducts.setCurrencyId(currencies);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldCurrencyIdOfProductsCollectionNewProducts != null && !oldCurrencyIdOfProductsCollectionNewProducts.equals(currencies)) {
                        oldCurrencyIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldCurrencyIdOfProductsCollectionNewProducts = em.merge(oldCurrencyIdOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = currencies.getId();
                if (findCurrencies(id) == null) {
                    throw new NonexistentEntityException("The currencies with id " + id + " no longer exists.");
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
            Currencies currencies;
            try {
                currencies = em.getReference(Currencies.class, id);
                currencies.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The currencies with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = currencies.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Currencies (" + currencies + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable currencyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(currencies);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Currencies> findCurrenciesEntities() {
        return findCurrenciesEntities(true, -1, -1);
    }

    public List<Currencies> findCurrenciesEntities(int maxResults, int firstResult) {
        return findCurrenciesEntities(false, maxResults, firstResult);
    }

    private List<Currencies> findCurrenciesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Currencies.class));
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

    public Currencies findCurrencies(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Currencies.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurrenciesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Currencies> rt = cq.from(Currencies.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
