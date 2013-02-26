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

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "uom")
@NamedQueries({
    @NamedQuery(name = "Uom.findAll", query = "SELECT u FROM Uom u"),
    @NamedQuery(name = "Uom.findById", query = "SELECT u FROM Uom u WHERE u.id = :id"),
    @NamedQuery(name = "Uom.findByName", query = "SELECT u FROM Uom u WHERE u.name = :name"),
    @NamedQuery(name = "Uom.findBySymbol", query = "SELECT u FROM Uom u WHERE u.symbol = :symbol")})
public class Uom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "symbol")
    private String symbol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uomToId")
    private Collection<UomCovertionFactors> uomCovertionFactorsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uomFromId")
    private Collection<UomCovertionFactors> uomCovertionFactorsCollection1;
    @OneToMany(mappedBy = "uomId")
    private Collection<Products> productsCollection;

    public Uom() {
    }

    public Uom(Integer id) {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Collection<UomCovertionFactors> getUomCovertionFactorsCollection() {
        return uomCovertionFactorsCollection;
    }

    public void setUomCovertionFactorsCollection(Collection<UomCovertionFactors> uomCovertionFactorsCollection) {
        this.uomCovertionFactorsCollection = uomCovertionFactorsCollection;
    }

    public Collection<UomCovertionFactors> getUomCovertionFactorsCollection1() {
        return uomCovertionFactorsCollection1;
    }

    public void setUomCovertionFactorsCollection1(Collection<UomCovertionFactors> uomCovertionFactorsCollection1) {
        this.uomCovertionFactorsCollection1 = uomCovertionFactorsCollection1;
    }

    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
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
        if (!(object instanceof Uom)) {
            return false;
        }
        Uom other = (Uom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.ENTITIES.Uom[ id=" + id + " ]";
    }
    
}
