/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "clientsaccounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientsaccounts.findAll", query = "SELECT c FROM Clientsaccounts c"),
    @NamedQuery(name = "Clientsaccounts.findById", query = "SELECT c FROM Clientsaccounts c WHERE c.id = :id"),
    @NamedQuery(name = "Clientsaccounts.findByCreationDate", query = "SELECT c FROM Clientsaccounts c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "Clientsaccounts.findByBalance", query = "SELECT c FROM Clientsaccounts c WHERE c.balance = :balance")})
public class Clientsaccounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private BigDecimal balance;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientaccountId")
    private Collection<Payments> paymentsCollection;

    public Clientsaccounts() {
    }

    public Clientsaccounts(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    @XmlTransient
    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
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
        if (!(object instanceof Clientsaccounts)) {
            return false;
        }
        Clientsaccounts other = (Clientsaccounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Clientsaccounts[ id=" + id + " ]";
    }
    
}
