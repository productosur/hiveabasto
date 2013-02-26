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
@Table(name = "employees_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeesRoles.findAll", query = "SELECT e FROM EmployeesRoles e"),
    @NamedQuery(name = "EmployeesRoles.findById", query = "SELECT e FROM EmployeesRoles e WHERE e.id = :id"),
    @NamedQuery(name = "EmployeesRoles.findByDescription", query = "SELECT e FROM EmployeesRoles e WHERE e.description = :description")})
public class EmployeesRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeroleId")
    private Collection<Permissions> permissionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeroleId")
    private Collection<Employees> employeesCollection;

    public EmployeesRoles() {
    }

    public EmployeesRoles(Integer id) {
        this.id = id;
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
    public Collection<Permissions> getPermissionsCollection() {
        return permissionsCollection;
    }

    public void setPermissionsCollection(Collection<Permissions> permissionsCollection) {
        this.permissionsCollection = permissionsCollection;
    }

    @XmlTransient
    public Collection<Employees> getEmployeesCollection() {
        return employeesCollection;
    }

    public void setEmployeesCollection(Collection<Employees> employeesCollection) {
        this.employeesCollection = employeesCollection;
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
        if (!(object instanceof EmployeesRoles)) {
            return false;
        }
        EmployeesRoles other = (EmployeesRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.EmployeesRoles[ id=" + id + " ]";
    }
    
}
