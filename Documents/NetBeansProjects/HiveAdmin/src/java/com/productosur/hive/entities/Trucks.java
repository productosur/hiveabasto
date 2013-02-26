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
@Table(name = "trucks")
@NamedQueries({
    @NamedQuery(name = "Trucks.findAll", query = "SELECT t FROM Trucks t"),
    @NamedQuery(name = "Trucks.findById", query = "SELECT t FROM Trucks t WHERE t.id = :id"),
    @NamedQuery(name = "Trucks.findByBrand", query = "SELECT t FROM Trucks t WHERE t.brand = :brand"),
    @NamedQuery(name = "Trucks.findByModel", query = "SELECT t FROM Trucks t WHERE t.model = :model"),
    @NamedQuery(name = "Trucks.findByMadeYear", query = "SELECT t FROM Trucks t WHERE t.madeYear = :madeYear"),
    @NamedQuery(name = "Trucks.findByCapacity", query = "SELECT t FROM Trucks t WHERE t.capacity = :capacity")})
public class Trucks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "brand")
    private String brand;
    @Basic(optional = false)
    @Column(name = "model")
    private String model;
    @Column(name = "made_year")
    @Temporal(TemporalType.DATE)
    private Date madeYear;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capacity")
    private Double capacity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "truckId")
    private Collection<Deliveries> deliveriesCollection;

    public Trucks() {
    }

    public Trucks(Integer id) {
        this.id = id;
    }

    public Trucks(Integer id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getMadeYear() {
        return madeYear;
    }

    public void setMadeYear(Date madeYear) {
        this.madeYear = madeYear;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Collection<Deliveries> getDeliveriesCollection() {
        return deliveriesCollection;
    }

    public void setDeliveriesCollection(Collection<Deliveries> deliveriesCollection) {
        this.deliveriesCollection = deliveriesCollection;
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
        if (!(object instanceof Trucks)) {
            return false;
        }
        Trucks other = (Trucks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.brand +" "+ this.model;
    }
    
}
