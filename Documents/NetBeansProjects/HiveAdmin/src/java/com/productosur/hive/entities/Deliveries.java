/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "deliveries")
@NamedQueries({
    @NamedQuery(name = "Deliveries.findAll", query = "SELECT d FROM Deliveries d"),
    @NamedQuery(name = "Deliveries.findById", query = "SELECT d FROM Deliveries d WHERE d.id = :id"),
    @NamedQuery(name = "Deliveries.findByInitTime", query = "SELECT d FROM Deliveries d WHERE d.initTime = :initTime"),
    @NamedQuery(name = "Deliveries.findByFinishTime", query = "SELECT d FROM Deliveries d WHERE d.finishTime = :finishTime"),
    @NamedQuery(name = "Deliveries.findByClosed", query = "SELECT d FROM Deliveries d WHERE d.closed = :closed")})
public class Deliveries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "init_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initTime;
    @Basic(optional = false)
    @Column(name = "finish_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;
    @Column(name = "closed")
    private Short closed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryId")
    private Collection<DeliveryStock> deliveryStockCollection;
    @OneToMany(mappedBy = "deliveryId")
    private Collection<Paydocuments> paydocumentsCollection;
    @JoinColumn(name = "truck_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Trucks truckId;

    public Deliveries() {
    }

    public Deliveries(Integer id) {
        this.id = id;
    }

    public Deliveries(Integer id, Date initTime, Date finishTime) {
        this.id = id;
        this.initTime = initTime;
        this.finishTime = finishTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Short getClosed() {
        return closed;
    }

    public void setClosed(Short closed) {
        this.closed = closed;
    }

    public Collection<DeliveryStock> getDeliveryStockCollection() {
        return deliveryStockCollection;
    }

    public void setDeliveryStockCollection(Collection<DeliveryStock> deliveryStockCollection) {
        this.deliveryStockCollection = deliveryStockCollection;
    }

    public Collection<Paydocuments> getPaydocumentsCollection() {
        return paydocumentsCollection;
    }

    public void setPaydocumentsCollection(Collection<Paydocuments> paydocumentsCollection) {
        this.paydocumentsCollection = paydocumentsCollection;
    }

    public Trucks getTruckId() {
        return truckId;
    }

    public void setTruckId(Trucks truckId) {
        this.truckId = truckId;
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
        if (!(object instanceof Deliveries)) {
            return false;
        }
        Deliveries other = (Deliveries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Deliveries[ id=" + id + " ]";
    }
    
}
