/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "subsidiaries")
@NamedQueries({
    @NamedQuery(name = "Subsidiaries.findAll", query = "SELECT s FROM Subsidiaries s"),
    @NamedQuery(name = "Subsidiaries.findById", query = "SELECT s FROM Subsidiaries s WHERE s.id = :id"),
    @NamedQuery(name = "Subsidiaries.findByName", query = "SELECT s FROM Subsidiaries s WHERE s.name = :name"),
    @NamedQuery(name = "Subsidiaries.findByAddress", query = "SELECT s FROM Subsidiaries s WHERE s.address = :address"),
    @NamedQuery(name = "Subsidiaries.findByContactName", query = "SELECT s FROM Subsidiaries s WHERE s.contactName = :contactName"),
    @NamedQuery(name = "Subsidiaries.findByPhoneNumber", query = "SELECT s FROM Subsidiaries s WHERE s.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Subsidiaries.findBySubsidiaryNumber", query = "SELECT s FROM Subsidiaries s WHERE s.subsidiaryNumber = :subsidiaryNumber"),
    @NamedQuery(name = "Subsidiaries.findByEmail", query = "SELECT s FROM Subsidiaries s WHERE s.email = :email"),
    @NamedQuery(name = "Subsidiaries.findByInacNumber", query = "SELECT s FROM Subsidiaries s WHERE s.inacNumber = :inacNumber"),
    @NamedQuery(name = "Subsidiaries.findByMaxCredit", query = "SELECT s FROM Subsidiaries s WHERE s.maxCredit = :maxCredit"),
    @NamedQuery(name = "Subsidiaries.findByDescription", query = "SELECT s FROM Subsidiaries s WHERE s.description = :description")})
public class Subsidiaries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "lat")
    private String lat;
    @Lob
    @Column(name = "lng")
    private String lng;
    @Column(name = "address")
    private String address;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "subsidiary_number")
    private Integer subsidiaryNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "inac_number")
    private Integer inacNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "max_credit")
    private Double maxCredit;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subsidiaryId")
    private Collection<DailyRoutes> dailyRoutesCollection;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subsidiaryId")
    private Collection<Orders> ordersCollection;

    public Subsidiaries() {
    }

    public Subsidiaries(Integer id) {
        this.id = id;
    }

    public Subsidiaries(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getSubsidiaryNumber() {
        return subsidiaryNumber;
    }

    public void setSubsidiaryNumber(Integer subsidiaryNumber) {
        this.subsidiaryNumber = subsidiaryNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getInacNumber() {
        return inacNumber;
    }

    public void setInacNumber(Integer inacNumber) {
        this.inacNumber = inacNumber;
    }

    public Double getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(Double maxCredit) {
        this.maxCredit = maxCredit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<DailyRoutes> getDailyRoutesCollection() {
        return dailyRoutesCollection;
    }

    public void setDailyRoutesCollection(Collection<DailyRoutes> dailyRoutesCollection) {
        this.dailyRoutesCollection = dailyRoutesCollection;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
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
        if (!(object instanceof Subsidiaries)) {
            return false;
        }
        Subsidiaries other = (Subsidiaries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.ENTITIES.Subsidiaries[ id=" + id + " ]";
    }
    
}
