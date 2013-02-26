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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "paymentformats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paymentformats.findAll", query = "SELECT p FROM Paymentformats p"),
    @NamedQuery(name = "Paymentformats.findById", query = "SELECT p FROM Paymentformats p WHERE p.id = :id"),
    @NamedQuery(name = "Paymentformats.findByName", query = "SELECT p FROM Paymentformats p WHERE p.name = :name")})
public class Paymentformats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentformatId")
    private Collection<Discounts> discountsCollection;

    public Paymentformats() {
    }

    public Paymentformats(Integer id) {
        this.id = id;
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

    @XmlTransient
    public Collection<Discounts> getDiscountsCollection() {
        return discountsCollection;
    }

    public void setDiscountsCollection(Collection<Discounts> discountsCollection) {
        this.discountsCollection = discountsCollection;
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
        if (!(object instanceof Paymentformats)) {
            return false;
        }
        Paymentformats other = (Paymentformats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Paymentformats[ id=" + id + " ]";
    }
    
}
