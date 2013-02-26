/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Productstocks;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="productstockmb")
@ViewScoped
public class ProductsStocks {

    private List<Productstocks> productStockList;
    private Productstocks selectedProductstocks;
    /**
     * Creates a new instance of ProductsStocks
     */
    public ProductsStocks() {
    }
    
    @PostConstruct
    public void inicialize(){
        productStockList = ControllerManager.getInstance().getProductstocksJpaController().findProductstocksEntities();
        selectedProductstocks = new Productstocks();
    }

    public List<Productstocks> getProductStockList() {
        return productStockList;
    }

    public void setProductStockList(List<Productstocks> productStockList) {
        this.productStockList = productStockList;
    }

    public Productstocks getSelectedProductstocks() {
        return selectedProductstocks;
    }

    public void setSelectedProductstocks(Productstocks selectedProductstocks) {
        this.selectedProductstocks = selectedProductstocks;
    }
    
    
}
