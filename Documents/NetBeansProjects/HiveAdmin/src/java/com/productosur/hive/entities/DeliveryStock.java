/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "delivery_stock")
@NamedQueries({
    @NamedQuery(name = "DeliveryStock.findAll", query = "SELECT d FROM DeliveryStock d"),
    @NamedQuery(name = "DeliveryStock.findById", query = "SELECT d FROM DeliveryStock d WHERE d.id = :id"),
    @NamedQuery(name = "DeliveryStock.findByInitialStock", query = "SELECT d FROM DeliveryStock d WHERE d.initialStock = :initialStock"),
    @NamedQuery(name = "DeliveryStock.findByFinalStock", query = "SELECT d FROM DeliveryStock d WHERE d.finalStock = :finalStock")})
public class DeliveryStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "initial_stock")
    private Double initialStock;
    @Column(name = "final_stock")
    private Double finalStock;
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Deliveries deliveryId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;

    public DeliveryStock() {
    }

    public DeliveryStock(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(Double initialStock) {
        this.initialStock = initialStock;
    }

    public Double getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(Double finalStock) {
        this.finalStock = finalStock;
    }

    public Deliveries getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Deliveries deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryStock)) {
            return false;
        }
        DeliveryStock other = (DeliveryStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.DeliveryStock[ id=" + id + " ]";
    }
    
}
