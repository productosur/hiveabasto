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

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "paydocuments")
@NamedQueries({
    @NamedQuery(name = "Paydocuments.findAll", query = "SELECT p FROM Paydocuments p"),
    @NamedQuery(name = "Paydocuments.findById", query = "SELECT p FROM Paydocuments p WHERE p.id = :id"),
    @NamedQuery(name = "Paydocuments.findByDate", query = "SELECT p FROM Paydocuments p WHERE p.date = :date"),
    @NamedQuery(name = "Paydocuments.findByNumber", query = "SELECT p FROM Paydocuments p WHERE p.number = :number"),
    @NamedQuery(name = "Paydocuments.findBySerial", query = "SELECT p FROM Paydocuments p WHERE p.serial = :serial"),
    @NamedQuery(name = "Paydocuments.findByControl", query = "SELECT p FROM Paydocuments p WHERE p.control = :control"),
    @NamedQuery(name = "Paydocuments.findByCanceled", query = "SELECT p FROM Paydocuments p WHERE p.canceled = :canceled")})
public class Paydocuments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "number")
    private Integer number;
    @Column(name = "serial")
    private String serial;
    @Column(name = "control")
    private Boolean control;
    @Column(name = "canceled")
    private Boolean canceled;
    @JoinColumn(name = "clients_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientsId;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;
    @JoinColumn(name = "documentype_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Documentypes documentypeId;
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    @ManyToOne
    private Deliveries deliveryId;
    @OneToMany(mappedBy = "receiptdocumentId")
    private Collection<Payments> paymentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paydocumentId")
    private Collection<Payments> paymentsCollection1;

    public Paydocuments() {
    }

    public Paydocuments(Integer id) {
        this.id = id;
    }

    public Paydocuments(Integer id, Date date) {
        this.id = id;
        this.date = date;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Boolean getControl() {
        return control;
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Clients getClientsId() {
        return clientsId;
    }

    public void setClientsId(Clients clientsId) {
        this.clientsId = clientsId;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Documentypes getDocumentypeId() {
        return documentypeId;
    }

    public void setDocumentypeId(Documentypes documentypeId) {
        this.documentypeId = documentypeId;
    }

    public Deliveries getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Deliveries deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
    }

    public Collection<Payments> getPaymentsCollection1() {
        return paymentsCollection1;
    }

    public void setPaymentsCollection1(Collection<Payments> paymentsCollection1) {
        this.paymentsCollection1 = paymentsCollection1;
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
        if (!(object instanceof Paydocuments)) {
            return false;
        }
        Paydocuments other = (Paydocuments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.ENTITIES.Paydocuments[ id=" + id + " ]";
    }
    
}
