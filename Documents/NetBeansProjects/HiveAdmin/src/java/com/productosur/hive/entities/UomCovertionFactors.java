/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "uom_covertion_factors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UomCovertionFactors.findAll", query = "SELECT u FROM UomCovertionFactors u"),
    @NamedQuery(name = "UomCovertionFactors.findById", query = "SELECT u FROM UomCovertionFactors u WHERE u.id = :id"),
    @NamedQuery(name = "UomCovertionFactors.findByValue", query = "SELECT u FROM UomCovertionFactors u WHERE u.value = :value")})
public class UomCovertionFactors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @JoinColumn(name = "uom_to_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Uom uomToId;
    @JoinColumn(name = "uom_from_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Uom uomFromId;

    public UomCovertionFactors() {
    }

    public UomCovertionFactors(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Uom getUomToId() {
        return uomToId;
    }

    public void setUomToId(Uom uomToId) {
        this.uomToId = uomToId;
    }

    public Uom getUomFromId() {
        return uomFromId;
    }

    public void setUomFromId(Uom uomFromId) {
        this.uomFromId = uomFromId;
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
        if (!(object instanceof UomCovertionFactors)) {
            return false;
        }
        UomCovertionFactors other = (UomCovertionFactors) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.UomCovertionFactors[ id=" + id + " ]";
    }
    
}
