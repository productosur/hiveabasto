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
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByNumber", query = "SELECT o FROM Orders o WHERE o.number = :number"),
    @NamedQuery(name = "Orders.findByDate", query = "SELECT o FROM Orders o WHERE o.date = :date"),
    @NamedQuery(name = "Orders.findByRequiresKitchen", query = "SELECT o FROM Orders o WHERE o.requiresKitchen = :requiresKitchen"),
    @NamedQuery(name = "Orders.findByInstantDelivery", query = "SELECT o FROM Orders o WHERE o.instantDelivery = :instantDelivery"),
    @NamedQuery(name = "Orders.findByDeliveryScheduleTo", query = "SELECT o FROM Orders o WHERE o.deliveryScheduleTo = :deliveryScheduleTo"),
    @NamedQuery(name = "Orders.findByDeliveryScheduleFrom", query = "SELECT o FROM Orders o WHERE o.deliveryScheduleFrom = :deliveryScheduleFrom"),
    @NamedQuery(name = "Orders.findByHomeDelivery", query = "SELECT o FROM Orders o WHERE o.homeDelivery = :homeDelivery"),
    @NamedQuery(name = "Orders.findByTotalPrice", query = "SELECT o FROM Orders o WHERE o.totalPrice = :totalPrice"),
    @NamedQuery(name = "Orders.findByTotalDiscount", query = "SELECT o FROM Orders o WHERE o.totalDiscount = :totalDiscount"),
    @NamedQuery(name = "Orders.findByPaymentformatId", query = "SELECT o FROM Orders o WHERE o.paymentformatId = :paymentformatId")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "requires_kitchen")
    private boolean requiresKitchen;
    @Column(name = "instant_delivery")
    private boolean instantDelivery;
    @Column(name = "delivery_schedule_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryScheduleTo;
    @Column(name = "delivery_schedule_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryScheduleFrom;
    @Column(name = "home_delivery")
    private boolean homeDelivery;
    @Basic(optional = false)
    @Column(name = "total_price")
    private double totalPrice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_discount")
    private Double totalDiscount;
    @Column(name = "paymentformat_id")
    private Integer paymentformatId;
    @Column(name = "active")
    private Short active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<Paydocuments> paydocumentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<Orderlines> orderlinesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<Orderstaterecords> orderstaterecordsCollection;
    @JoinColumn(name = "subsidiary_id", referencedColumnName = "id")
    private Subsidiaries subsidiaryId;
    @JoinColumn(name = "ordertype_id", referencedColumnName = "id")
    private Ordertypes ordertypeId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees employeeId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, String number, boolean requiresKitchen, boolean instantDelivery, boolean homeDelivery, double totalPrice) {
        this.id = id;
        this.number = number;
        this.requiresKitchen = requiresKitchen;
        this.instantDelivery = instantDelivery;
        this.homeDelivery = homeDelivery;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getRequiresKitchen() {
        return requiresKitchen;
    }

    public void setRequiresKitchen(boolean requiresKitchen) {
        this.requiresKitchen = requiresKitchen;
    }

    public boolean getInstantDelivery() {
        return instantDelivery;
    }

    public void setInstantDelivery(boolean instantDelivery) {
        this.instantDelivery = instantDelivery;
    }

    public Date getDeliveryScheduleTo() {
        return deliveryScheduleTo;
    }

    public void setDeliveryScheduleTo(Date deliveryScheduleTo) {
        this.deliveryScheduleTo = deliveryScheduleTo;
    }

    public Date getDeliveryScheduleFrom() {
        return deliveryScheduleFrom;
    }

    public void setDeliveryScheduleFrom(Date deliveryScheduleFrom) {
        this.deliveryScheduleFrom = deliveryScheduleFrom;
    }

    public boolean getHomeDelivery() {
        return homeDelivery;
    }

    public void setHomeDelivery(boolean homeDelivery) {
        this.homeDelivery = homeDelivery;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Integer getPaymentformatId() {
        return paymentformatId;
    }

    public void setPaymentformatId(Integer paymentformatId) {
        this.paymentformatId = paymentformatId;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }
    
    @XmlTransient
    public Collection<Paydocuments> getPaydocumentsCollection() {
        return paydocumentsCollection;
    }

    public void setPaydocumentsCollection(Collection<Paydocuments> paydocumentsCollection) {
        this.paydocumentsCollection = paydocumentsCollection;
    }

    @XmlTransient
    public Collection<Orderlines> getOrderlinesCollection() {
        return orderlinesCollection;
    }

    public void setOrderlinesCollection(Collection<Orderlines> orderlinesCollection) {
        this.orderlinesCollection = orderlinesCollection;
    }

    @XmlTransient
    public Collection<Orderstaterecords> getOrderstaterecordsCollection() {
        return orderstaterecordsCollection;
    }

    public void setOrderstaterecordsCollection(Collection<Orderstaterecords> orderstaterecordsCollection) {
        this.orderstaterecordsCollection = orderstaterecordsCollection;
    }

    public Subsidiaries getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Subsidiaries subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public Ordertypes getOrdertypeId() {
        return ordertypeId;
    }

    public void setOrdertypeId(Ordertypes ordertypeId) {
        this.ordertypeId = ordertypeId;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Orders[ id=" + id + " ]";
    }
    
}
