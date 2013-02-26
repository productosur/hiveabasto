/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Orderlines;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Subsidiaries;
import com.productosur.hive.managedbeans.converters.ProductConverter;
import com.productosur.hive.util.JsfUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="conciliationmb")
@ViewScoped
public class ConciliationMB {

    private List<Products> productsList;
    private List<Products> selectedProductsList;
    private List<Orderlines> orderLinesList;
    private ProductConverter converter;
    private Orders order;
    private Orderlines orderLine;
    private Double total;
    private Double ivaMin;
    private Double ivaBas;
    private Subsidiaries client;
    private DecimalFormat decimalFormat =  new DecimalFormat("#.##");
    

    /**
     * Creates a new instance of SalesMB
     */
    public ConciliationMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        productsList = ControllerManager.getInstance().getProductsJpaController().findProductsEntities(); 
        selectedProductsList = new ArrayList<Products>();
        orderLinesList = new ArrayList<Orderlines>();
        converter = new ProductConverter();
        order = new Orders();
        orderLine = new Orderlines();
        orderLine.setProductId(new Products());
        total = 0.0;
        ivaMin = 0.0;
        ivaBas = 0.0;
        client = ClientsMB.getInstance().getSelectedClient();
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public Orders getOrder() {
        return order;
    }


    public List<Orderlines> getOrderLinesList() {
        return orderLinesList;
    }

    public void setOrderLinesList(List<Orderlines> orderLinesList) {
        this.orderLinesList = orderLinesList;
    }

    public Orderlines getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(Orderlines orderLine) {
        this.orderLine = orderLine;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public void setSelectedProductsList(List<Products> selectedProductsList) {
        this.selectedProductsList = selectedProductsList;
    }

    public void setConverter(ProductConverter converter) {
        this.converter = converter;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Double getIvaMin() {
        return ivaMin;
    }

    public void setIvaMin(Double ivaMin) {
        this.ivaMin = ivaMin;
    }

    public Double getIvaBas() {
        return ivaBas;
    }

    public void setIvaBas(Double ivaBas) {
        this.ivaBas = ivaBas;
    }

    public Subsidiaries getClient() {
        return client;
    }
    
    public void reinit(){
        orderLine = new Orderlines();
        
    }
    
    public List<Products> completeProducts(String query) {  
        List<Products> suggestions = new ArrayList<Products>();  
          
        for(Products p : productsList) {  
            if(p.getName().toLowerCase().startsWith(query)){
                suggestions.add(p);  
            }
        }  
          
        return suggestions;  
    }  

    public List<Products> getSelectedProductsList() {
        return selectedProductsList;
    }

    public ProductConverter getConverter() {
        return converter;
    }
    
    public void processLine(){
        //ol.setProductId(selectedProduct);
        //ol.setQuantity(quantity);
       
        if(orderLine.getQuantity() == 0){
            JsfUtil.getInstance().addErrorMessage("Debe poner una cantidad");
        }else{
             if(orderLine.getPrice() == 0){
                orderLine.setPrice(orderLine.getProductId().getPrice().doubleValue());
            }
            if(orderLine.getProductId().isIvaBas()){
                 ivaBas = ivaBas + ((orderLine.getPrice() * orderLine.getProductId().getTaxId().getValue())/100); 
                 decimalFormat.format(ivaBas);
            }else if(orderLine.getProductId().isIvaMin()){
                 ivaMin = ivaMin + ((orderLine.getPrice() * orderLine.getProductId().getTaxId().getValue())/100); 
                 decimalFormat.format(ivaMin);
            }
            total = total + orderLine.getPrice();
            decimalFormat.format(total);
            this.orderLinesList.add(orderLine);
        }        
    }
    
    public String saveOrder(){
        
        return "clients_subsidiary?faces-redirect=true";
    }
}
