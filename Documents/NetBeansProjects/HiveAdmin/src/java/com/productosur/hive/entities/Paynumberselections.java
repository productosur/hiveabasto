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
@Table(name = "paynumberselections")
@NamedQueries({
    @NamedQuery(name = "Paynumberselections.findAll", query = "SELECT p FROM Paynumberselections p"),
    @NamedQuery(name = "Paynumberselections.findById", query = "SELECT p FROM Paynumberselections p WHERE p.id = :id"),
    @NamedQuery(name = "Paynumberselections.findByFrom", query = "SELECT p FROM Paynumberselections p WHERE p.from = :from"),
    @NamedQuery(name = "Paynumberselections.findByTo", query = "SELECT p FROM Paynumberselections p WHERE p.to = :to"),
    @NamedQuery(name = "Paynumberselections.findBySerial", query = "SELECT p FROM Paynumberselections p WHERE p.serial = :serial"),
    @NamedQuery(name = "Paynumberselections.findByCurrent", query = "SELECT p FROM Paynumberselections p WHERE p.current = :current"),
    @NamedQuery(name = "Paynumberselections.findByActive", query = "SELECT p FROM Paynumberselections p WHERE p.active = :active"),
    @NamedQuery(name = "Paynumberselections.findByExpirationDate", query = "SELECT p FROM Paynumberselections p WHERE p.expirationDate = :expirationDate"),
    @NamedQuery(name = "Paynumberselections.findByStockMin", query = "SELECT p FROM Paynumberselections p WHERE p.stockMin = :stockMin")})
public class Paynumberselections implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "from_")
    private Integer from;
    @Column(name = "to_")
    private Integer to;
    @Column(name = "serial")
    private String serial;
    @Column(name = "current")
    private Integer current;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @Column(name = "stock_min")
    private Integer stockMin;

    public Paynumberselections() {
    }

    public Paynumberselections(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
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
        if (!(object instanceof Paynumberselections)) {
            return false;
        }
        Paynumberselections other = (Paynumberselections) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.ENTITIES.Paynumberselections[ id=" + id + " ]";
    }
    
}
