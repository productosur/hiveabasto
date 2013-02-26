/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "trace_route")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TraceRoute.findAll", query = "SELECT t FROM TraceRoute t"),
    @NamedQuery(name = "TraceRoute.findById", query = "SELECT t FROM TraceRoute t WHERE t.id = :id"),
    @NamedQuery(name = "TraceRoute.findByDateTime", query = "SELECT t FROM TraceRoute t WHERE t.dateTime = :dateTime")})
public class TraceRoute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Lob
    @Column(name = "lat")
    private String lat;
    @Lob
    @Column(name = "lng")
    private String lng;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees employeeId;

    public TraceRoute() {
    }

    public TraceRoute(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof TraceRoute)) {
            return false;
        }
        TraceRoute other = (TraceRoute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.TraceRoute[ id=" + id + " ]";
    }
    
}
