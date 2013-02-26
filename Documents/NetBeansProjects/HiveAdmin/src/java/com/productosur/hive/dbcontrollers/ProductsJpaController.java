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
import com.productosur.hive.entities.Uom;
import com.productosur.hive.entities.Taxes;
import com.productosur.hive.entities.Productscategories;
import com.productosur.hive.entities.Manufacturers;
import com.productosur.hive.entities.Currencies;
import com.productosur.hive.entities.Productstocks;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.PurchasesLines;
import com.productosur.hive.entities.Orderlines;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Specialdiscounts;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class ProductsJpaController implements Serializable {

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) {
        if (products.getProductstocksCollection() == null) {
            products.setProductstocksCollection(new ArrayList<Productstocks>());
        }
        if (products.getPurchasesLinesCollection() == null) {
            products.setPurchasesLinesCollection(new ArrayList<PurchasesLines>());
        }
        if (products.getOrderlinesCollection() == null) {
            products.setOrderlinesCollection(new ArrayList<Orderlines>());
        }
        if (products.getSpecialdiscountsCollection() == null) {
            products.setSpecialdiscountsCollection(new ArrayList<Specialdiscounts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Uom uomId = products.getUomId();
            if (uomId != null) {
                uomId = em.getReference(uomId.getClass(), uomId.getId());
                products.setUomId(uomId);
            }
            Taxes taxId = products.getTaxId();
            if (taxId != null) {
                taxId = em.getReference(taxId.getClass(), taxId.getId());
                products.setTaxId(taxId);
            }
            Productscategories productscategoryId = products.getProductscategoryId();
            if (productscategoryId != null) {
                productscategoryId = em.getReference(productscategoryId.getClass(), productscategoryId.getId());
                products.setProductscategoryId(productscategoryId);
            }
            Manufacturers manufacturerId = products.getManufacturerId();
            if (manufacturerId != null) {
                manufacturerId = em.getReference(manufacturerId.getClass(), manufacturerId.getId());
                products.setManufacturerId(manufacturerId);
            }
            Currencies currencyId = products.getCurrencyId();
            if (currencyId != null) {
                currencyId = em.getReference(currencyId.getClass(), currencyId.getId());
                products.setCurrencyId(currencyId);
            }
            Collection<Productstocks> attachedProductstocksCollection = new ArrayList<Productstocks>();
            for (Productstocks productstocksCollectionProductstocksToAttach : products.getProductstocksCollection()) {
                productstocksCollectionProductstocksToAttach = em.getReference(productstocksCollectionProductstocksToAttach.getClass(), productstocksCollectionProductstocksToAttach.getId());
                attachedProductstocksCollection.add(productstocksCollectionProductstocksToAttach);
            }
            products.setProductstocksCollection(attachedProductstocksCollection);
            Collection<PurchasesLines> attachedPurchasesLinesCollection = new ArrayList<PurchasesLines>();
            for (PurchasesLines purchasesLinesCollectionPurchasesLinesToAttach : products.getPurchasesLinesCollection()) {
                purchasesLinesCollectionPurchasesLinesToAttach = em.getReference(purchasesLinesCollectionPurchasesLinesToAttach.getClass(), purchasesLinesCollectionPurchasesLinesToAttach.getId());
                attachedPurchasesLinesCollection.add(purchasesLinesCollectionPurchasesLinesToAttach);
            }
            products.setPurchasesLinesCollection(attachedPurchasesLinesCollection);
            Collection<Orderlines> attachedOrderlinesCollection = new ArrayList<Orderlines>();
            for (Orderlines orderlinesCollectionOrderlinesToAttach : products.getOrderlinesCollection()) {
                orderlinesCollectionOrderlinesToAttach = em.getReference(orderlinesCollectionOrderlinesToAttach.getClass(), orderlinesCollectionOrderlinesToAttach.getId());
                attachedOrderlinesCollection.add(orderlinesCollectionOrderlinesToAttach);
            }
            products.setOrderlinesCollection(attachedOrderlinesCollection);
            Collection<Specialdiscounts> attachedSpecialdiscountsCollection = new ArrayList<Specialdiscounts>();
            for (Specialdiscounts specialdiscountsCollectionSpecialdiscountsToAttach : products.getSpecialdiscountsCollection()) {
                specialdiscountsCollectionSpecialdiscountsToAttach = em.getReference(specialdiscountsCollectionSpecialdiscountsToAttach.getClass(), specialdiscountsCollectionSpecialdiscountsToAttach.getId());
                attachedSpecialdiscountsCollection.add(specialdiscountsCollectionSpecialdiscountsToAttach);
            }
            products.setSpecialdiscountsCollection(attachedSpecialdiscountsCollection);
            em.persist(products);
            if (uomId != null) {
                uomId.getProductsCollection().add(products);
                uomId = em.merge(uomId);
            }
            if (taxId != null) {
                taxId.getProductsCollection().add(products);
                taxId = em.merge(taxId);
            }
            if (productscategoryId != null) {
                productscategoryId.getProductsCollection().add(products);
                productscategoryId = em.merge(productscategoryId);
            }
            if (manufacturerId != null) {
                manufacturerId.getProductsCollection().add(products);
                manufacturerId = em.merge(manufacturerId);
            }
            if (currencyId != null) {
                currencyId.getProductsCollection().add(products);
                currencyId = em.merge(currencyId);
            }
            for (Productstocks productstocksCollectionProductstocks : products.getProductstocksCollection()) {
                Products oldProductIdOfProductstocksCollectionProductstocks = productstocksCollectionProductstocks.getProductId();
                productstocksCollectionProductstocks.setProductId(products);
                productstocksCollectionProductstocks = em.merge(productstocksCollectionProductstocks);
                if (oldProductIdOfProductstocksCollectionProductstocks != null) {
                    oldProductIdOfProductstocksCollectionProductstocks.getProductstocksCollection().remove(productstocksCollectionProductstocks);
                    oldProductIdOfProductstocksCollectionProductstocks = em.merge(oldProductIdOfProductstocksCollectionProductstocks);
                }
            }
            for (PurchasesLines purchasesLinesCollectionPurchasesLines : products.getPurchasesLinesCollection()) {
                Products oldProductIdOfPurchasesLinesCollectionPurchasesLines = purchasesLinesCollectionPurchasesLines.getProductId();
                purchasesLinesCollectionPurchasesLines.setProductId(products);
                purchasesLinesCollectionPurchasesLines = em.merge(purchasesLinesCollectionPurchasesLines);
                if (oldProductIdOfPurchasesLinesCollectionPurchasesLines != null) {
                    oldProductIdOfPurchasesLinesCollectionPurchasesLines.getPurchasesLinesCollection().remove(purchasesLinesCollectionPurchasesLines);
                    oldProductIdOfPurchasesLinesCollectionPurchasesLines = em.merge(oldProductIdOfPurchasesLinesCollectionPurchasesLines);
                }
            }
            for (Orderlines orderlinesCollectionOrderlines : products.getOrderlinesCollection()) {
                Products oldProductIdOfOrderlinesCollectionOrderlines = orderlinesCollectionOrderlines.getProductId();
                orderlinesCollectionOrderlines.setProductId(products);
                orderlinesCollectionOrderlines = em.merge(orderlinesCollectionOrderlines);
                if (oldProductIdOfOrderlinesCollectionOrderlines != null) {
                    oldProductIdOfOrderlinesCollectionOrderlines.getOrderlinesCollection().remove(orderlinesCollectionOrderlines);
                    oldProductIdOfOrderlinesCollectionOrderlines = em.merge(oldProductIdOfOrderlinesCollectionOrderlines);
                }
            }
            for (Specialdiscounts specialdiscountsCollectionSpecialdiscounts : products.getSpecialdiscountsCollection()) {
                Products oldProductIdOfSpecialdiscountsCollectionSpecialdiscounts = specialdiscountsCollectionSpecialdiscounts.getProductId();
                specialdiscountsCollectionSpecialdiscounts.setProductId(products);
                specialdiscountsCollectionSpecialdiscounts = em.merge(specialdiscountsCollectionSpecialdiscounts);
                if (oldProductIdOfSpecialdiscountsCollectionSpecialdiscounts != null) {
                    oldProductIdOfSpecialdiscountsCollectionSpecialdiscounts.getSpecialdiscountsCollection().remove(specialdiscountsCollectionSpecialdiscounts);
                    oldProductIdOfSpecialdiscountsCollectionSpecialdiscounts = em.merge(oldProductIdOfSpecialdiscountsCollectionSpecialdiscounts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getId());
            Uom uomIdOld = persistentProducts.getUomId();
            Uom uomIdNew = products.getUomId();
            Taxes taxIdOld = persistentProducts.getTaxId();
            Taxes taxIdNew = products.getTaxId();
            Productscategories productscategoryIdOld = persistentProducts.getProductscategoryId();
            Productscategories productscategoryIdNew = products.getProductscategoryId();
            Manufacturers manufacturerIdOld = persistentProducts.getManufacturerId();
            Manufacturers manufacturerIdNew = products.getManufacturerId();
            Currencies currencyIdOld = persistentProducts.getCurrencyId();
            Currencies currencyIdNew = products.getCurrencyId();
            Collection<Productstocks> productstocksCollectionOld = persistentProducts.getProductstocksCollection();
            Collection<Productstocks> productstocksCollectionNew = products.getProductstocksCollection();
            Collection<PurchasesLines> purchasesLinesCollectionOld = persistentProducts.getPurchasesLinesCollection();
            Collection<PurchasesLines> purchasesLinesCollectionNew = products.getPurchasesLinesCollection();
            Collection<Orderlines> orderlinesCollectionOld = persistentProducts.getOrderlinesCollection();
            Collection<Orderlines> orderlinesCollectionNew = products.getOrderlinesCollection();
            Collection<Specialdiscounts> specialdiscountsCollectionOld = persistentProducts.getSpecialdiscountsCollection();
            Collection<Specialdiscounts> specialdiscountsCollectionNew = products.getSpecialdiscountsCollection();
            List<String> illegalOrphanMessages = null;
            for (Productstocks productstocksCollectionOldProductstocks : productstocksCollectionOld) {
                if (!productstocksCollectionNew.contains(productstocksCollectionOldProductstocks)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productstocks " + productstocksCollectionOldProductstocks + " since its productId field is not nullable.");
                }
            }
            for (PurchasesLines purchasesLinesCollectionOldPurchasesLines : purchasesLinesCollectionOld) {
                if (!purchasesLinesCollectionNew.contains(purchasesLinesCollectionOldPurchasesLines)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PurchasesLines " + purchasesLinesCollectionOldPurchasesLines + " since its productId field is not nullable.");
                }
            }
            for (Orderlines orderlinesCollectionOldOrderlines : orderlinesCollectionOld) {
                if (!orderlinesCollectionNew.contains(orderlinesCollectionOldOrderlines)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderlines " + orderlinesCollectionOldOrderlines + " since its productId field is not nullable.");
                }
            }
            for (Specialdiscounts specialdiscountsCollectionOldSpecialdiscounts : specialdiscountsCollectionOld) {
                if (!specialdiscountsCollectionNew.contains(specialdiscountsCollectionOldSpecialdiscounts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Specialdiscounts " + specialdiscountsCollectionOldSpecialdiscounts + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (uomIdNew != null) {
                uomIdNew = em.getReference(uomIdNew.getClass(), uomIdNew.getId());
                products.setUomId(uomIdNew);
            }
            if (taxIdNew != null) {
                taxIdNew = em.getReference(taxIdNew.getClass(), taxIdNew.getId());
                products.setTaxId(taxIdNew);
            }
            if (productscategoryIdNew != null) {
                productscategoryIdNew = em.getReference(productscategoryIdNew.getClass(), productscategoryIdNew.getId());
                products.setProductscategoryId(productscategoryIdNew);
            }
            if (manufacturerIdNew != null) {
                manufacturerIdNew = em.getReference(manufacturerIdNew.getClass(), manufacturerIdNew.getId());
                products.setManufacturerId(manufacturerIdNew);
            }
            if (currencyIdNew != null) {
                currencyIdNew = em.getReference(currencyIdNew.getClass(), currencyIdNew.getId());
                products.setCurrencyId(currencyIdNew);
            }
            Collection<Productstocks> attachedProductstocksCollectionNew = new ArrayList<Productstocks>();
            for (Productstocks productstocksCollectionNewProductstocksToAttach : productstocksCollectionNew) {
                productstocksCollectionNewProductstocksToAttach = em.getReference(productstocksCollectionNewProductstocksToAttach.getClass(), productstocksCollectionNewProductstocksToAttach.getId());
                attachedProductstocksCollectionNew.add(productstocksCollectionNewProductstocksToAttach);
            }
            productstocksCollectionNew = attachedProductstocksCollectionNew;
            products.setProductstocksCollection(productstocksCollectionNew);
            Collection<PurchasesLines> attachedPurchasesLinesCollectionNew = new ArrayList<PurchasesLines>();
            for (PurchasesLines purchasesLinesCollectionNewPurchasesLinesToAttach : purchasesLinesCollectionNew) {
                purchasesLinesCollectionNewPurchasesLinesToAttach = em.getReference(purchasesLinesCollectionNewPurchasesLinesToAttach.getClass(), purchasesLinesCollectionNewPurchasesLinesToAttach.getId());
                attachedPurchasesLinesCollectionNew.add(purchasesLinesCollectionNewPurchasesLinesToAttach);
            }
            purchasesLinesCollectionNew = attachedPurchasesLinesCollectionNew;
            products.setPurchasesLinesCollection(purchasesLinesCollectionNew);
            Collection<Orderlines> attachedOrderlinesCollectionNew = new ArrayList<Orderlines>();
            for (Orderlines orderlinesCollectionNewOrderlinesToAttach : orderlinesCollectionNew) {
                orderlinesCollectionNewOrderlinesToAttach = em.getReference(orderlinesCollectionNewOrderlinesToAttach.getClass(), orderlinesCollectionNewOrderlinesToAttach.getId());
                attachedOrderlinesCollectionNew.add(orderlinesCollectionNewOrderlinesToAttach);
            }
            orderlinesCollectionNew = attachedOrderlinesCollectionNew;
            products.setOrderlinesCollection(orderlinesCollectionNew);
            Collection<Specialdiscounts> attachedSpecialdiscountsCollectionNew = new ArrayList<Specialdiscounts>();
            for (Specialdiscounts specialdiscountsCollectionNewSpecialdiscountsToAttach : specialdiscountsCollectionNew) {
                specialdiscountsCollectionNewSpecialdiscountsToAttach = em.getReference(specialdiscountsCollectionNewSpecialdiscountsToAttach.getClass(), specialdiscountsCollectionNewSpecialdiscountsToAttach.getId());
                attachedSpecialdiscountsCollectionNew.add(specialdiscountsCollectionNewSpecialdiscountsToAttach);
            }
            specialdiscountsCollectionNew = attachedSpecialdiscountsCollectionNew;
            products.setSpecialdiscountsCollection(specialdiscountsCollectionNew);
            products = em.merge(products);
            if (uomIdOld != null && !uomIdOld.equals(uomIdNew)) {
                uomIdOld.getProductsCollection().remove(products);
                uomIdOld = em.merge(uomIdOld);
            }
            if (uomIdNew != null && !uomIdNew.equals(uomIdOld)) {
                uomIdNew.getProductsCollection().add(products);
                uomIdNew = em.merge(uomIdNew);
            }
            if (taxIdOld != null && !taxIdOld.equals(taxIdNew)) {
                taxIdOld.getProductsCollection().remove(products);
                taxIdOld = em.merge(taxIdOld);
            }
            if (taxIdNew != null && !taxIdNew.equals(taxIdOld)) {
                taxIdNew.getProductsCollection().add(products);
                taxIdNew = em.merge(taxIdNew);
            }
            if (productscategoryIdOld != null && !productscategoryIdOld.equals(productscategoryIdNew)) {
                productscategoryIdOld.getProductsCollection().remove(products);
                productscategoryIdOld = em.merge(productscategoryIdOld);
            }
            if (productscategoryIdNew != null && !productscategoryIdNew.equals(productscategoryIdOld)) {
                productscategoryIdNew.getProductsCollection().add(products);
                productscategoryIdNew = em.merge(productscategoryIdNew);
            }
            if (manufacturerIdOld != null && !manufacturerIdOld.equals(manufacturerIdNew)) {
                manufacturerIdOld.getProductsCollection().remove(products);
                manufacturerIdOld = em.merge(manufacturerIdOld);
            }
            if (manufacturerIdNew != null && !manufacturerIdNew.equals(manufacturerIdOld)) {
                manufacturerIdNew.getProductsCollection().add(products);
                manufacturerIdNew = em.merge(manufacturerIdNew);
            }
            if (currencyIdOld != null && !currencyIdOld.equals(currencyIdNew)) {
                currencyIdOld.getProductsCollection().remove(products);
                currencyIdOld = em.merge(currencyIdOld);
            }
            if (currencyIdNew != null && !currencyIdNew.equals(currencyIdOld)) {
                currencyIdNew.getProductsCollection().add(products);
                currencyIdNew = em.merge(currencyIdNew);
            }
            for (Productstocks productstocksCollectionNewProductstocks : productstocksCollectionNew) {
                if (!productstocksCollectionOld.contains(productstocksCollectionNewProductstocks)) {
                    Products oldProductIdOfProductstocksCollectionNewProductstocks = productstocksCollectionNewProductstocks.getProductId();
                    productstocksCollectionNewProductstocks.setProductId(products);
                    productstocksCollectionNewProductstocks = em.merge(productstocksCollectionNewProductstocks);
                    if (oldProductIdOfProductstocksCollectionNewProductstocks != null && !oldProductIdOfProductstocksCollectionNewProductstocks.equals(products)) {
                        oldProductIdOfProductstocksCollectionNewProductstocks.getProductstocksCollection().remove(productstocksCollectionNewProductstocks);
                        oldProductIdOfProductstocksCollectionNewProductstocks = em.merge(oldProductIdOfProductstocksCollectionNewProductstocks);
                    }
                }
            }
            for (PurchasesLines purchasesLinesCollectionNewPurchasesLines : purchasesLinesCollectionNew) {
                if (!purchasesLinesCollectionOld.contains(purchasesLinesCollectionNewPurchasesLines)) {
                    Products oldProductIdOfPurchasesLinesCollectionNewPurchasesLines = purchasesLinesCollectionNewPurchasesLines.getProductId();
                    purchasesLinesCollectionNewPurchasesLines.setProductId(products);
                    purchasesLinesCollectionNewPurchasesLines = em.merge(purchasesLinesCollectionNewPurchasesLines);
                    if (oldProductIdOfPurchasesLinesCollectionNewPurchasesLines != null && !oldProductIdOfPurchasesLinesCollectionNewPurchasesLines.equals(products)) {
                        oldProductIdOfPurchasesLinesCollectionNewPurchasesLines.getPurchasesLinesCollection().remove(purchasesLinesCollectionNewPurchasesLines);
                        oldProductIdOfPurchasesLinesCollectionNewPurchasesLines = em.merge(oldProductIdOfPurchasesLinesCollectionNewPurchasesLines);
                    }
                }
            }
            for (Orderlines orderlinesCollectionNewOrderlines : orderlinesCollectionNew) {
                if (!orderlinesCollectionOld.contains(orderlinesCollectionNewOrderlines)) {
                    Products oldProductIdOfOrderlinesCollectionNewOrderlines = orderlinesCollectionNewOrderlines.getProductId();
                    orderlinesCollectionNewOrderlines.setProductId(products);
                    orderlinesCollectionNewOrderlines = em.merge(orderlinesCollectionNewOrderlines);
                    if (oldProductIdOfOrderlinesCollectionNewOrderlines != null && !oldProductIdOfOrderlinesCollectionNewOrderlines.equals(products)) {
                        oldProductIdOfOrderlinesCollectionNewOrderlines.getOrderlinesCollection().remove(orderlinesCollectionNewOrderlines);
                        oldProductIdOfOrderlinesCollectionNewOrderlines = em.merge(oldProductIdOfOrderlinesCollectionNewOrderlines);
                    }
                }
            }
            for (Specialdiscounts specialdiscountsCollectionNewSpecialdiscounts : specialdiscountsCollectionNew) {
                if (!specialdiscountsCollectionOld.contains(specialdiscountsCollectionNewSpecialdiscounts)) {
                    Products oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts = specialdiscountsCollectionNewSpecialdiscounts.getProductId();
                    specialdiscountsCollectionNewSpecialdiscounts.setProductId(products);
                    specialdiscountsCollectionNewSpecialdiscounts = em.merge(specialdiscountsCollectionNewSpecialdiscounts);
                    if (oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts != null && !oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts.equals(products)) {
                        oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts.getSpecialdiscountsCollection().remove(specialdiscountsCollectionNewSpecialdiscounts);
                        oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts = em.merge(oldProductIdOfSpecialdiscountsCollectionNewSpecialdiscounts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = products.getId();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
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
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Productstocks> productstocksCollectionOrphanCheck = products.getProductstocksCollection();
            for (Productstocks productstocksCollectionOrphanCheckProductstocks : productstocksCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Productstocks " + productstocksCollectionOrphanCheckProductstocks + " in its productstocksCollection field has a non-nullable productId field.");
            }
            Collection<PurchasesLines> purchasesLinesCollectionOrphanCheck = products.getPurchasesLinesCollection();
            for (PurchasesLines purchasesLinesCollectionOrphanCheckPurchasesLines : purchasesLinesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the PurchasesLines " + purchasesLinesCollectionOrphanCheckPurchasesLines + " in its purchasesLinesCollection field has a non-nullable productId field.");
            }
            Collection<Orderlines> orderlinesCollectionOrphanCheck = products.getOrderlinesCollection();
            for (Orderlines orderlinesCollectionOrphanCheckOrderlines : orderlinesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Orderlines " + orderlinesCollectionOrphanCheckOrderlines + " in its orderlinesCollection field has a non-nullable productId field.");
            }
            Collection<Specialdiscounts> specialdiscountsCollectionOrphanCheck = products.getSpecialdiscountsCollection();
            for (Specialdiscounts specialdiscountsCollectionOrphanCheckSpecialdiscounts : specialdiscountsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Specialdiscounts " + specialdiscountsCollectionOrphanCheckSpecialdiscounts + " in its specialdiscountsCollection field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Uom uomId = products.getUomId();
            if (uomId != null) {
                uomId.getProductsCollection().remove(products);
                uomId = em.merge(uomId);
            }
            Taxes taxId = products.getTaxId();
            if (taxId != null) {
                taxId.getProductsCollection().remove(products);
                taxId = em.merge(taxId);
            }
            Productscategories productscategoryId = products.getProductscategoryId();
            if (productscategoryId != null) {
                productscategoryId.getProductsCollection().remove(products);
                productscategoryId = em.merge(productscategoryId);
            }
            Manufacturers manufacturerId = products.getManufacturerId();
            if (manufacturerId != null) {
                manufacturerId.getProductsCollection().remove(products);
                manufacturerId = em.merge(manufacturerId);
            }
            Currencies currencyId = products.getCurrencyId();
            if (currencyId != null) {
                currencyId.getProductsCollection().remove(products);
                currencyId = em.merge(currencyId);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
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

    public Products findProducts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
