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
@Table(name = "discounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discounts.findAll", query = "SELECT d FROM Discounts d"),
    @NamedQuery(name = "Discounts.findById", query = "SELECT d FROM Discounts d WHERE d.id = :id"),
    @NamedQuery(name = "Discounts.findByValue", query = "SELECT d FROM Discounts d WHERE d.value = :value")})
public class Discounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "value")
    private long value;
    @JoinColumn(name = "paymentformat_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paymentformats paymentformatId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientId;

    public Discounts() {
    }

    public Discounts(Integer id) {
        this.id = id;
    }

    public Discounts(Integer id, long value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Paymentformats getPaymentformatId() {
        return paymentformatId;
    }

    public void setPaymentformatId(Paymentformats paymentformatId) {
        this.paymentformatId = paymentformatId;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
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
        if (!(object instanceof Discounts)) {
            return false;
        }
        Discounts other = (Discounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Discounts[ id=" + id + " ]";
    }
    
}
