/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Productosur
 */
@Entity
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findById", query = "SELECT p FROM Products p WHERE p.id = :id"),
    @NamedQuery(name = "Products.findByCode", query = "SELECT p FROM Products p WHERE p.code = :code"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByOffer", query = "SELECT p FROM Products p WHERE p.offer = :offer"),
    @NamedQuery(name = "Products.findByInacCode", query = "SELECT p FROM Products p WHERE p.inacCode = :inacCode")})
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "image")
    private byte[] image;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "offer")
    private Boolean offer;
    @Column(name = "inac_code")
    private Integer inacCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Productstocks> productstocksCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<DeliveryStock> deliveryStockCollection;
    @JoinColumn(name = "uom_id", referencedColumnName = "id")
    @ManyToOne
    private Uom uomId;
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    @ManyToOne
    private Taxes taxId;
    @JoinColumn(name = "productscategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Productscategories productscategoryId;
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    @ManyToOne
    private Manufacturers manufacturerId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currencies currencyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<PurchasesLines> purchasesLinesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Orderlines> orderlinesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Specialdiscounts> specialdiscountsCollection;

    public Products() {
    }

    public Products(Integer id) {
        this.id = id;
    }

    public Products(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getOffer() {
        return offer;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

    public Integer getInacCode() {
        return inacCode;
    }

    public void setInacCode(Integer inacCode) {
        this.inacCode = inacCode;
    }

    public Collection<Productstocks> getProductstocksCollection() {
        return productstocksCollection;
    }

    public void setProductstocksCollection(Collection<Productstocks> productstocksCollection) {
        this.productstocksCollection = productstocksCollection;
    }

    public Collection<DeliveryStock> getDeliveryStockCollection() {
        return deliveryStockCollection;
    }

    public void setDeliveryStockCollection(Collection<DeliveryStock> deliveryStockCollection) {
        this.deliveryStockCollection = deliveryStockCollection;
    }

    public Uom getUomId() {
        return uomId;
    }

    public void setUomId(Uom uomId) {
        this.uomId = uomId;
    }

    public Taxes getTaxId() {
        return taxId;
    }

    public void setTaxId(Taxes taxId) {
        this.taxId = taxId;
    }

    public Productscategories getProductscategoryId() {
        return productscategoryId;
    }

    public void setProductscategoryId(Productscategories productscategoryId) {
        this.productscategoryId = productscategoryId;
    }

    public Manufacturers getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Manufacturers manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Currencies getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currencies currencyId) {
        this.currencyId = currencyId;
    }

    public Collection<PurchasesLines> getPurchasesLinesCollection() {
        return purchasesLinesCollection;
    }

    public void setPurchasesLinesCollection(Collection<PurchasesLines> purchasesLinesCollection) {
        this.purchasesLinesCollection = purchasesLinesCollection;
    }

    public Collection<Orderlines> getOrderlinesCollection() {
        return orderlinesCollection;
    }

    public void setOrderlinesCollection(Collection<Orderlines> orderlinesCollection) {
        this.orderlinesCollection = orderlinesCollection;
    }

    public Collection<Specialdiscounts> getSpecialdiscountsCollection() {
        return specialdiscountsCollection;
    }

    public void setSpecialdiscountsCollection(Collection<Specialdiscounts> specialdiscountsCollection) {
        this.specialdiscountsCollection = specialdiscountsCollection;
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
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.productosur.hive.entities.Products[ id=" + id + " ]";
    }
    
    
    public boolean isIvaMin(){
        if(this.taxId.getId() == 2){
            return true;
        }
        return false;
    }
    
    public boolean isIvaBas(){
        if(this.taxId.getId() == 1){
            return true;
        }
        return false;
    }
    
}
