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
@Table(name = "orderstates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderstates.findAll", query = "SELECT o FROM Orderstates o"),
    @NamedQuery(name = "Orderstates.findById", query = "SELECT o FROM Orderstates o WHERE o.id = :id"),
    @NamedQuery(name = "Orderstates.findByDescription", query = "SELECT o FROM Orderstates o WHERE o.description = :description")})
public class Orderstates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderstateId")
    private Collection<Orderstaterecords> orderstaterecordsCollection;

    public Orderstates() {
    }

    public Orderstates(Integer id) {
        this.id = id;
    }

    public Orderstates(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Orderstaterecords> getOrderstaterecordsCollection() {
        return orderstaterecordsCollection;
    }

    public void setOrderstaterecordsCollection(Collection<Orderstaterecords> orderstaterecordsCollection) {
        this.orderstaterecordsCollection = orderstaterecordsCollection;
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
        if (!(object instanceof Orderstates)) {
            return false;
        }
        Orderstates other = (Orderstates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Orderstates[ id=" + id + " ]";
    }
    
}
