/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans.visualobjects;

import com.productosur.hive.entities.Productstocks;


/**
 *
 * @author Productosur
 */
public class DeliveryStockVO {
    
    private Integer id;
    private Double productStock;
    private Double deliveryStock;
    private Productstocks productStockEntitie;

    public DeliveryStockVO(Integer id, Double productStock, Double deliveryStock, Productstocks productStockEntitie) {
        this.id = id;
        this.productStock = productStock;
        this.deliveryStock = deliveryStock;
        this.productStockEntitie = productStockEntitie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Double getProductStock() {
        return productStock;
    }

    public void setProductStock(Double productStock) {
        this.productStock = productStock;
    }

    public Double getDeliveryStock() {
        return deliveryStock;
    }

    public void setDeliveryStock(Double deliveryStock) {
        this.deliveryStock = deliveryStock;
    }

    public Productstocks getProductStockEntitie() {
        return productStockEntitie;
    }

    public void setProductStockEntitie(Productstocks productStockEntitie) {
        this.productStockEntitie = productStockEntitie;
    }

    
   
}
