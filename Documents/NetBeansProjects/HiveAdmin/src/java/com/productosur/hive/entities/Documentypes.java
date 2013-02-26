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
@Table(name = "documentypes")
@NamedQueries({
    @NamedQuery(name = "Documentypes.findAll", query = "SELECT d FROM Documentypes d"),
    @NamedQuery(name = "Documentypes.findById", query = "SELECT d FROM Documentypes d WHERE d.id = :id"),
    @NamedQuery(name = "Documentypes.findByName", query = "SELECT d FROM Documentypes d WHERE d.name = :name"),
    @NamedQuery(name = "Documentypes.findByAddValue", query = "SELECT d FROM Documentypes d WHERE d.addValue = :addValue")})
public class Documentypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "add_value")
    private boolean addValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentypeId")
    private Collection<Paydocuments> paydocumentsCollection;

    public Documentypes() {
    }

    public Documentypes(Integer id) {
        this.id = id;
    }

    public Documentypes(Integer id, boolean addValue) {
        this.id = id;
        this.addValue = addValue;
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

    public boolean getAddValue() {
        return addValue;
    }

    public void setAddValue(boolean addValue) {
        this.addValue = addValue;
    }

    public Collection<Paydocuments> getPaydocumentsCollection() {
        return paydocumentsCollection;
    }

    public void setPaydocumentsCollection(Collection<Paydocuments> paydocumentsCollection) {
        this.paydocumentsCollection = paydocumentsCollection;
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
        if (!(object instanceof Documentypes)) {
            return false;
        }
        Documentypes other = (Documentypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
