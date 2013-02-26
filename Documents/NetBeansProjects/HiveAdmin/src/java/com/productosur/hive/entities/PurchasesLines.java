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
@Table(name = "purchases_lines")
@NamedQueries({
    @NamedQuery(name = "PurchasesLines.findAll", query = "SELECT p FROM PurchasesLines p"),
    @NamedQuery(name = "PurchasesLines.findById", query = "SELECT p FROM PurchasesLines p WHERE p.id = :id"),
    @NamedQuery(name = "PurchasesLines.findByQuantity", query = "SELECT p FROM PurchasesLines p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PurchasesLines.findByBuyPrice", query = "SELECT p FROM PurchasesLines p WHERE p.buyPrice = :buyPrice")})
public class PurchasesLines implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private Double quantity;
    @Column(name = "buy_price")
    private Double buyPrice;
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Purchases purchaseId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;

    public PurchasesLines() {
    }

    public PurchasesLines(Integer id) {
        this.id = id;
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

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Purchases getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Purchases purchaseId) {
        this.purchaseId = purchaseId;
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
        if (!(object instanceof PurchasesLines)) {
            return false;
        }
        PurchasesLines other = (PurchasesLines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.PurchasesLines[ id=" + id + " ]";
    }
    
}
