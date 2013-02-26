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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "orderlines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderlines.findAll", query = "SELECT o FROM Orderlines o"),
    @NamedQuery(name = "Orderlines.findById", query = "SELECT o FROM Orderlines o WHERE o.id = :id"),
    @NamedQuery(name = "Orderlines.findByQuantity", query = "SELECT o FROM Orderlines o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "Orderlines.findByPrice", query = "SELECT o FROM Orderlines o WHERE o.price = :price"),
    @NamedQuery(name = "Orderlines.findByTotalDiscount", query = "SELECT o FROM Orderlines o WHERE o.totalDiscount = :totalDiscount"),
    @NamedQuery(name = "Orderlines.findByComments", query = "SELECT o FROM Orderlines o WHERE o.comments = :comments"),
    @NamedQuery(name = "Orderlines.findByIsReady", query = "SELECT o FROM Orderlines o WHERE o.isReady = :isReady")})
public class Orderlines implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private Double quantity;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_discount")
    private Double totalDiscount;
    @Column(name = "comments")
    private String comments;
    @Column(name = "is_ready")
    private Boolean isReady;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;

    public Orderlines() {
    }

    public Orderlines(Integer id) {
        this.id = id;
    }

    public Orderlines(Integer id, double quantity, double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
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
        if (!(object instanceof Orderlines)) {
            return false;
        }
        Orderlines other = (Orderlines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Orderlines[ id=" + id + " ]";
    }
    
}
