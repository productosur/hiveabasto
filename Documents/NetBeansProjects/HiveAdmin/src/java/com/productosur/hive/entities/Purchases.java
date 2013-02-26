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
@Table(name = "purchases")
@NamedQueries({
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p"),
    @NamedQuery(name = "Purchases.findById", query = "SELECT p FROM Purchases p WHERE p.id = :id"),
    @NamedQuery(name = "Purchases.findByDate", query = "SELECT p FROM Purchases p WHERE p.date = :date"),
    @NamedQuery(name = "Purchases.findByDescription", query = "SELECT p FROM Purchases p WHERE p.description = :description"),
    @NamedQuery(name = "Purchases.findByInvoiceTotal", query = "SELECT p FROM Purchases p WHERE p.invoiceTotal = :invoiceTotal"),
    @NamedQuery(name = "Purchases.findByExpirationDate", query = "SELECT p FROM Purchases p WHERE p.expirationDate = :expirationDate"),
    @NamedQuery(name = "Purchases.findByInvoiceNumber", query = "SELECT p FROM Purchases p WHERE p.invoiceNumber = :invoiceNumber")})
public class Purchases implements Serializable {
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
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "invoice_total")
    private Double invoiceTotal;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Column(name = "invoice_number")
    private Integer invoiceNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseId")
    private Collection<PurchasesLines> purchasesLinesCollection;
    @JoinColumn(name = "manufacturers_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Manufacturers manufacturersId;
    @JoinColumn(name = "documentype_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Documentypes documentypeId;

    public Purchases() {
    }

    public Purchases(Integer id) {
        this.id = id;
    }

    public Purchases(Integer id, Date date) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Collection<PurchasesLines> getPurchasesLinesCollection() {
        return purchasesLinesCollection;
    }

    public void setPurchasesLinesCollection(Collection<PurchasesLines> purchasesLinesCollection) {
        this.purchasesLinesCollection = purchasesLinesCollection;
    }

    public Manufacturers getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(Manufacturers manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public Documentypes getDocumentypeId() {
        return documentypeId;
    }

    public void setDocumentypeId(Documentypes documentypeId) {
        this.documentypeId = documentypeId;
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
        if (!(object instanceof Purchases)) {
            return false;
        }
        Purchases other = (Purchases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Purchases[ id=" + id + " ]";
    }
    
}
