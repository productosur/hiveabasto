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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "daily_routes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DailyRoutes.findAll", query = "SELECT d FROM DailyRoutes d"),
    @NamedQuery(name = "DailyRoutes.findById", query = "SELECT d FROM DailyRoutes d WHERE d.id = :id"),
    @NamedQuery(name = "DailyRoutes.findByDate", query = "SELECT d FROM DailyRoutes d WHERE d.date = :date"),
    @NamedQuery(name = "DailyRoutes.findByVisitationOrder", query = "SELECT d FROM DailyRoutes d WHERE d.visitationOrder = :visitationOrder")})
public class DailyRoutes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "visitation_order")
    private Integer visitationOrder;
    @JoinColumn(name = "subsidiary_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Subsidiaries subsidiaryId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees employeeId;

    public DailyRoutes() {
    }

    public DailyRoutes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVisitationOrder() {
        return visitationOrder;
    }

    public void setVisitationOrder(Integer visitationOrder) {
        this.visitationOrder = visitationOrder;
    }

    public Subsidiaries getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Subsidiaries subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
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
        if (!(object instanceof DailyRoutes)) {
            return false;
        }
        DailyRoutes other = (DailyRoutes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.DailyRoutes[ id=" + id + " ]";
    }
    
}
