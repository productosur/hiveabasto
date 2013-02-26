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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "clients")
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findById", query = "SELECT c FROM Clients c WHERE c.id = :id"),
    @NamedQuery(name = "Clients.findByBusinessName", query = "SELECT c FROM Clients c WHERE c.businessName = :businessName"),
    @NamedQuery(name = "Clients.findByRut", query = "SELECT c FROM Clients c WHERE c.rut = :rut"),
    @NamedQuery(name = "Clients.findByFantasyName", query = "SELECT c FROM Clients c WHERE c.fantasyName = :fantasyName")})
public class Clients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "business_name")
    private String businessName;
    @Basic(optional = false)
    @Column(name = "rut")
    private String rut;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Column(name = "fantasy_name")
    private String fantasyName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<Clientsaccounts> clientsaccountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<Discounts> discountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<Subsidiaries> subsidiariesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientsId")
    private Collection<Paydocuments> paydocumentsCollection;
    @OneToMany(mappedBy = "clientId")
    private Collection<Payments> paymentsCollection;

    public Clients() {
    }

    public Clients(Integer id) {
        this.id = id;
    }

    public Clients(Integer id, String businessName, String rut) {
        this.id = id;
        this.businessName = businessName;
        this.rut = rut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public Collection<Clientsaccounts> getClientsaccountsCollection() {
        return clientsaccountsCollection;
    }

    public void setClientsaccountsCollection(Collection<Clientsaccounts> clientsaccountsCollection) {
        this.clientsaccountsCollection = clientsaccountsCollection;
    }

    public Collection<Discounts> getDiscountsCollection() {
        return discountsCollection;
    }

    public void setDiscountsCollection(Collection<Discounts> discountsCollection) {
        this.discountsCollection = discountsCollection;
    }

    public Collection<Subsidiaries> getSubsidiariesCollection() {
        return subsidiariesCollection;
    }

    public void setSubsidiariesCollection(Collection<Subsidiaries> subsidiariesCollection) {
        this.subsidiariesCollection = subsidiariesCollection;
    }

    public Collection<Paydocuments> getPaydocumentsCollection() {
        return paydocumentsCollection;
    }

    public void setPaydocumentsCollection(Collection<Paydocuments> paydocumentsCollection) {
        this.paydocumentsCollection = paydocumentsCollection;
    }

    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
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
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clients[ id=" + id + " ]";
    }
    
}
