/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "specialdiscounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specialdiscounts.findAll", query = "SELECT s FROM Specialdiscounts s"),
    @NamedQuery(name = "Specialdiscounts.findById", query = "SELECT s FROM Specialdiscounts s WHERE s.id = :id"),
    @NamedQuery(name = "Specialdiscounts.findByName", query = "SELECT s FROM Specialdiscounts s WHERE s.name = :name"),
    @NamedQuery(name = "Specialdiscounts.findByValue", query = "SELECT s FROM Specialdiscounts s WHERE s.value = :value"),
    @NamedQuery(name = "Specialdiscounts.findByCreateDate", query = "SELECT s FROM Specialdiscounts s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "Specialdiscounts.findByExpirationDate", query = "SELECT s FROM Specialdiscounts s WHERE s.expirationDate = :expirationDate"),
    @NamedQuery(name = "Specialdiscounts.findByFromStock", query = "SELECT s FROM Specialdiscounts s WHERE s.fromStock = :fromStock"),
    @NamedQuery(name = "Specialdiscounts.findByToStock", query = "SELECT s FROM Specialdiscounts s WHERE s.toStock = :toStock")})
public class Specialdiscounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "value")
    private long value;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "from_stock")
    private BigDecimal fromStock;
    @Column(name = "to_stock")
    private BigDecimal toStock;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products productId;

    public Specialdiscounts() {
    }

    public Specialdiscounts(Integer id) {
        this.id = id;
    }

    public Specialdiscounts(Integer id, long value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getFromStock() {
        return fromStock;
    }

    public void setFromStock(BigDecimal fromStock) {
        this.fromStock = fromStock;
    }

    public BigDecimal getToStock() {
        return toStock;
    }

    public void setToStock(BigDecimal toStock) {
        this.toStock = toStock;
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
        if (!(object instanceof Specialdiscounts)) {
            return false;
        }
        Specialdiscounts other = (Specialdiscounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Specialdiscounts[ id=" + id + " ]";
    }
    
}
