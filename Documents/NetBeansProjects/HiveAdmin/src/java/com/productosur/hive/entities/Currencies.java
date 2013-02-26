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
@Table(name = "currencies")
@NamedQueries({
    @NamedQuery(name = "Currencies.findAll", query = "SELECT c FROM Currencies c"),
    @NamedQuery(name = "Currencies.findById", query = "SELECT c FROM Currencies c WHERE c.id = :id"),
    @NamedQuery(name = "Currencies.findByName", query = "SELECT c FROM Currencies c WHERE c.name = :name"),
    @NamedQuery(name = "Currencies.findBySymbol", query = "SELECT c FROM Currencies c WHERE c.symbol = :symbol")})
public class Currencies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "symbol")
    private String symbol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId")
    private Collection<Products> productsCollection;

    public Currencies() {
    }

    public Currencies(Integer id) {
        this.id = id;
    }

    public Currencies(Integer id, String symbol) {
        this.id = id;
        this.symbol = symbol;
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
        if (!(object instanceof Currencies)) {
            return false;
        }
        Currencies other = (Currencies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
