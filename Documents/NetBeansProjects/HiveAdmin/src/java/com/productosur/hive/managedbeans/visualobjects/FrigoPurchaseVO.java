/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans.visualobjects;

/**
 *
 * @author Productosur
 */
public class FrigoPurchaseVO implements Comparable{
    
    private Integer id;
    private Double quantity;
    private String name;

    public FrigoPurchaseVO(Integer id, Double quantity, String name) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
       if (((Integer)o).intValue() == (this.id).intValue()){
           return 1;
       }else{
           return 0;
       }
    }
    
    
}
