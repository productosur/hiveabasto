/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p"),
    @NamedQuery(name = "Payments.findById", query = "SELECT p FROM Payments p WHERE p.id = :id"),
    @NamedQuery(name = "Payments.findByDate", query = "SELECT p FROM Payments p WHERE p.date = :date"),
    @NamedQuery(name = "Payments.findByValue", query = "SELECT p FROM Payments p WHERE p.value = :value")})
public class Payments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @Lob
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Clients clientId;
    @JoinColumn(name = "receiptdocument_id", referencedColumnName = "id")
    @ManyToOne
    private Paydocuments receiptdocumentId;
    @JoinColumn(name = "paydocument_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paydocuments paydocumentId;
    @JoinColumn(name = "clientaccount_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clientsaccounts clientaccountId;

    public Payments() {
    }

    public Payments(Integer id) {
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    public Paydocuments getReceiptdocumentId() {
        return receiptdocumentId;
    }

    public void setReceiptdocumentId(Paydocuments receiptdocumentId) {
        this.receiptdocumentId = receiptdocumentId;
    }

    public Paydocuments getPaydocumentId() {
        return paydocumentId;
    }

    public void setPaydocumentId(Paydocuments paydocumentId) {
        this.paydocumentId = paydocumentId;
    }

    public Clientsaccounts getClientaccountId() {
        return clientaccountId;
    }

    public void setClientaccountId(Clientsaccounts clientaccountId) {
        this.clientaccountId = clientaccountId;
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
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Payments[ id=" + id + " ]";
    }
    
}
