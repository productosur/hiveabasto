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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "employees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findById", query = "SELECT e FROM Employees e WHERE e.id = :id"),
    @NamedQuery(name = "Employees.findByFirstName", query = "SELECT e FROM Employees e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "Employees.findByLastName", query = "SELECT e FROM Employees e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "Employees.findByCi", query = "SELECT e FROM Employees e WHERE e.ci = :ci"),
    @NamedQuery(name = "Employees.findByPhoneNumber", query = "SELECT e FROM Employees e WHERE e.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Employees.findByBirthdate", query = "SELECT e FROM Employees e WHERE e.birthdate = :birthdate"),
    @NamedQuery(name = "Employees.findByAddress", query = "SELECT e FROM Employees e WHERE e.address = :address")})
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Users> usersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<DailyRoutes> dailyRoutesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<TraceRoute> traceRouteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Orders> ordersCollection;
    @JoinColumn(name = "employeerole_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmployeesRoles employeeroleId;

    public Employees() {
    }

    public Employees(Integer id) {
        this.id = id;
    }

    public Employees(Integer id, String firstName, String lastName, String ci, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ci = ci;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<DailyRoutes> getDailyRoutesCollection() {
        return dailyRoutesCollection;
    }

    public void setDailyRoutesCollection(Collection<DailyRoutes> dailyRoutesCollection) {
        this.dailyRoutesCollection = dailyRoutesCollection;
    }

    @XmlTransient
    public Collection<TraceRoute> getTraceRouteCollection() {
        return traceRouteCollection;
    }

    public void setTraceRouteCollection(Collection<TraceRoute> traceRouteCollection) {
        this.traceRouteCollection = traceRouteCollection;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public EmployeesRoles getEmployeeroleId() {
        return employeeroleId;
    }

    public void setEmployeeroleId(EmployeesRoles employeeroleId) {
        this.employeeroleId = employeeroleId;
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
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Employees[ id=" + id + " ]";
    }
    
}
