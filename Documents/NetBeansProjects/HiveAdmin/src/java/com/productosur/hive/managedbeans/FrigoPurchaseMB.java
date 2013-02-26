/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Products;
import com.productosur.hive.managedbeans.visualobjects.FrigoPurchaseVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PostLoad;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="frigopurchasesmb")
@ViewScoped
public class FrigoPurchaseMB {

    private List<Orders> orders;
    private List<FrigoPurchaseVO> products;
    /**
     * Creates a new instance of FrigoPurchaseMB
     */
    public FrigoPurchaseMB() {
        
    }
    
    @PostConstruct
    public void initialize(){
       products = new ArrayList<FrigoPurchaseVO>();
       makePurchase();
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<FrigoPurchaseVO> getProducts() {
        return products;
    }

    public void setProducts(List<FrigoPurchaseVO> products) {
        this.products = products;
    }
        
    void makePurchase(){
        Vector<Object[]> data = (Vector) ControllerManager.getInstance().getOrdersJpaController().findActiveOrdersEntities();
        for (Object[] d : data) {
            boolean findIt = false;
            iter:
            for(FrigoPurchaseVO p : products){
                if(p.compareTo((Integer)d[0]) == 1){
                    p.setQuantity(p.getQuantity() + (Double)d[2]);
                    findIt = true;
                    break iter;
                }
            }
            if(!findIt){
                FrigoPurchaseVO p = new FrigoPurchaseVO((Integer)d[0], (Double)d[2], (String)d[1]);
                products.add(p);
            }
        }
    }
    
    
}
