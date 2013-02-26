/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "productstocks")
@NamedQueries({
    @NamedQuery(name = "Productstocks.findAll", query = "SELECT p FROM Productstocks p"),
    @NamedQuery(name = "Productstocks.findById", query = "SELECT p FROM Productstocks p WHERE p.id = :id"),
    @NamedQuery(name = "Productstocks.findByValue", query = "SELECT p FROM Productstocks p WHERE p.value = :value"),
    @NamedQuery(name = "Productstocks.findByLastChange", query = "SELECT p FROM Productstocks p WHERE p.lastChange = :lastChange"),
    @NamedQuery(name = "Productstocks.findByStockMin", query = "SELECT p FROM Productstocks p WHERE p.stockMin = :stockMin")})
public class Productstocks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "value")
    private double value;
    @Basic(optional = false)
    @Column(name = "last_change")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChange;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stock_min")
    private Double stockMin;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;

    public Productstocks() {
    }

    public Productstocks(Integer id) {
        this.id = id;
    }

    public Productstocks(Integer id, double value, Date lastChange) {
        this.id = id;
        this.value = value;
        this.lastChange = lastChange;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Double getStockMin() {
        return stockMin;
    }

    public void setStockMin(Double stockMin) {
        this.stockMin = stockMin;
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
        if (!(object instanceof Productstocks)) {
            return false;
        }
        Productstocks other = (Productstocks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Productstocks[ id=" + id + " ]";
    }
    
}
