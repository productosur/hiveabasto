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
@Table(name = "accesses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesses.findAll", query = "SELECT a FROM Accesses a"),
    @NamedQuery(name = "Accesses.findById", query = "SELECT a FROM Accesses a WHERE a.id = :id"),
    @NamedQuery(name = "Accesses.findByName", query = "SELECT a FROM Accesses a WHERE a.name = :name"),
    @NamedQuery(name = "Accesses.findByLink", query = "SELECT a FROM Accesses a WHERE a.link = :link"),
    @NamedQuery(name = "Accesses.findByFather", query = "SELECT a FROM Accesses a WHERE a.father = :father")})
public class Accesses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "father")
    private Integer father;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accId")
    private Collection<Permissions> permissionsCollection;

    public Accesses() {
    }

    public Accesses(Integer id) {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getFather() {
        return father;
    }

    public void setFather(Integer father) {
        this.father = father;
    }

    @XmlTransient
    public Collection<Permissions> getPermissionsCollection() {
        return permissionsCollection;
    }

    public void setPermissionsCollection(Collection<Permissions> permissionsCollection) {
        this.permissionsCollection = permissionsCollection;
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
        if (!(object instanceof Accesses)) {
            return false;
        }
        Accesses other = (Accesses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Accesses[ id=" + id + " ]";
    }
    
}
