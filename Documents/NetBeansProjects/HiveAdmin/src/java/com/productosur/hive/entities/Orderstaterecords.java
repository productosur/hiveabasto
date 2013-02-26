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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "orderstaterecords")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderstaterecords.findAll", query = "SELECT o FROM Orderstaterecords o"),
    @NamedQuery(name = "Orderstaterecords.findById", query = "SELECT o FROM Orderstaterecords o WHERE o.id = :id"),
    @NamedQuery(name = "Orderstaterecords.findByIsActual", query = "SELECT o FROM Orderstaterecords o WHERE o.isActual = :isActual"),
    @NamedQuery(name = "Orderstaterecords.findByDate", query = "SELECT o FROM Orderstaterecords o WHERE o.date = :date"),
    @NamedQuery(name = "Orderstaterecords.findByCause", query = "SELECT o FROM Orderstaterecords o WHERE o.cause = :cause")})
public class Orderstaterecords implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "is_actual")
    private boolean isActual;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "cause")
    private String cause;
    @JoinColumn(name = "orderstate_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orderstates orderstateId;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;

    public Orderstaterecords() {
    }

    public Orderstaterecords(Integer id) {
        this.id = id;
    }

    public Orderstaterecords(Integer id, boolean isActual, Date date) {
        this.id = id;
        this.isActual = isActual;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsActual() {
        return isActual;
    }

    public void setIsActual(boolean isActual) {
        this.isActual = isActual;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Orderstates getOrderstateId() {
        return orderstateId;
    }

    public void setOrderstateId(Orderstates orderstateId) {
        this.orderstateId = orderstateId;
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
        if (!(object instanceof Orderstaterecords)) {
            return false;
        }
        Orderstaterecords other = (Orderstaterecords) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Orderstaterecords[ id=" + id + " ]";
    }
    
}
