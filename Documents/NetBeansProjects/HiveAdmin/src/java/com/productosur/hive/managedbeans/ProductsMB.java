/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Currencies;
import com.productosur.hive.entities.Manufacturers;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Productscategories;
import com.productosur.hive.entities.Taxes;
import com.productosur.hive.managedbeans.converters.CategoryConverter;
import com.productosur.hive.managedbeans.converters.CurrencieConverter;
import com.productosur.hive.managedbeans.converters.ManufacturerConverter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="productsmb")
@SessionScoped
public class ProductsMB {

    private List<Products> productList;
    private Products selectedProduct;
    private Products newProduct;
    private List<Productscategories> productsCategoriesList;
    private List<Manufacturers> manufactersList;
    private Productscategories selectedCategory;
    private Manufacturers selectedManufacturer;
    private List<Currencies> currencies;
    private Currencies selectedCurrencie;
    private CurrencieConverter currencieConverter;
    private CategoryConverter categoryConverter;
    private ManufacturerConverter manufacturerConverter;
    
    
    /**
     * Creates a new instance of ProductsMB
     */
    public ProductsMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        productList = ControllerManager.getInstance().getProductsJpaController().findProductsEntities();
        currencies = ControllerManager.getInstance().getCurrenciesJpaController().findCurrenciesEntities();
        manufactersList = ControllerManager.getInstance().getManufacturersJpaController().findManufacturersEntities();
        productsCategoriesList = ControllerManager.getInstance().getProductscategoriesJpaController().findProductscategoriesEntities();
        newProduct = new Products();
        
        currencieConverter = new CurrencieConverter();
        manufacturerConverter = new ManufacturerConverter();
        categoryConverter = new CategoryConverter();
        selectedProduct = new Products();
    }

    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }

    public Products getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Products getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Products newProduct) {
        this.newProduct = newProduct;
    }

   
    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public Currencies getSelectedCurrencie() {
        return selectedCurrencie;
    }

    public void setSelectedCurrencie(Currencies selectedCurrencie) {
        this.selectedCurrencie = selectedCurrencie;
    }

    public CurrencieConverter getCurrencieConverter() {
        return currencieConverter;
    }

    public void setCurrencieConverter(CurrencieConverter currencieConverter) {
        this.currencieConverter = currencieConverter;
    }

    public List<Productscategories> getProductsCategoriesList() {
        return productsCategoriesList;
    }

    public void setProductsCategoriesList(List<Productscategories> productsCategoriesList) {
        this.productsCategoriesList = productsCategoriesList;
    }

    public List<Manufacturers> getManufactersList() {
        return manufactersList;
    }

    public void setManufactersList(List<Manufacturers> manufactersList) {
        this.manufactersList = manufactersList;
    }

    public Productscategories getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Productscategories selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Manufacturers getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturers selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }

    public CategoryConverter getCategoryConverter() {
        return categoryConverter;
    }

    public void setCategoryConverter(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    public ManufacturerConverter getManufacturerConverter() {
        return manufacturerConverter;
    }

    public void setManufacturerConverter(ManufacturerConverter manufacturerConverter) {
        this.manufacturerConverter = manufacturerConverter;
    }
    
    
    
    public void saveDataAction(){
        try {
            Taxes defaultTax = ControllerManager.getInstance().getTaxesJpaController().findDefaultTax();
            newProduct.setTaxId(defaultTax);
            newProduct.setManufacturerId(selectedManufacturer);
            newProduct.setProductscategoryId(selectedCategory);
            newProduct.setCurrencyId(selectedCurrencie);
            
            ControllerManager.getInstance().getProductsJpaController().create(newProduct);
            MessagesManager.printMessage("Producto guardado con Exito");
        } catch (Exception e) {
            MessagesManager.printMessage("Error al guardar Producto");
        }
        
    }
    
    public void refreshList(){
        productList = ControllerManager.getInstance().getProductsJpaController().findProductsEntities();
    }
    
    public void clearDataAction(){
        newProduct = new Products();
    }
}
