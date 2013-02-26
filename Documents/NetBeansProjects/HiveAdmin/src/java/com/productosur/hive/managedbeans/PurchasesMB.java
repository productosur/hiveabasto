/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Documentypes;
import com.productosur.hive.entities.Manufacturers;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Productstocks;
import com.productosur.hive.entities.Purchases;
import com.productosur.hive.entities.PurchasesLines;
import com.productosur.hive.managedbeans.converters.DocumentTypeConverter;
import com.productosur.hive.managedbeans.converters.ManufacturerConverter;
import com.productosur.hive.managedbeans.converters.ProductConverter;
import com.productosur.hive.util.JsfUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="purchasesmb")
@ViewScoped
public class PurchasesMB {

    private List<Products> productsList;
    private List<Products> selectedProductsList;
    private List<Manufacturers> manufactersList;
    private List<Documentypes> documentTypesList;
    private Documentypes selectedDocumentypes;
    private Manufacturers selectedManufacturer;
    private ProductConverter productConverter; 
    private ManufacturerConverter manufacturConverter;
    private DocumentTypeConverter documentTypeConverter;
    private Purchases purchase;
    private PurchasesLines purchaseLine;
    private List<PurchasesLines> purchasesLinesList;
    private Double total;
    private DecimalFormat decimalFormat =  new DecimalFormat("#.##");
    private String description; 
    private Date purchaseDate;
    private Date expirationDate;
    private Integer invoiceNumber;
    
     
    /**
     * Creates a new instance of PurchasesMB
     */
    public PurchasesMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        productsList = ControllerManager.getInstance().getProductsJpaController().findProductsEntities(); 
        manufactersList = ControllerManager.getInstance().getManufacturersJpaController().findManufacturersEntities();
        documentTypesList = ControllerManager.getInstance().getDocumentypesJpaController().findDocumentypesEntities();
        selectedProductsList = new ArrayList<Products>();
        productConverter = new ProductConverter();
        manufacturConverter = new ManufacturerConverter();
        documentTypeConverter = new DocumentTypeConverter();
        purchase = new Purchases();
        purchasesLinesList = new ArrayList<PurchasesLines>();
        purchaseLine = new PurchasesLines();
        purchaseLine.setProductId(new Products());
        total = 0.0;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public List<Products> getSelectedProductsList() {
        return selectedProductsList;
    }

    public void setSelectedProductsList(List<Products> selectedProductsList) {
        this.selectedProductsList = selectedProductsList;
    }

    public ProductConverter getProductConverter() {
        return productConverter;
    }

    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public ManufacturerConverter getManufacturConverter() {
        return manufacturConverter;
    }

    public void setManufacturConverter(ManufacturerConverter manufacturConverter) {
        this.manufacturConverter = manufacturConverter;
    }

    public Purchases getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchases purchase) {
        this.purchase = purchase;
    }

    public PurchasesLines getPurchaseLine() {
        return purchaseLine;
    }

    public void setPurchaseLine(PurchasesLines purchaseLine) {
        this.purchaseLine = purchaseLine;
    }

    public List<PurchasesLines> getPurchasesLinesList() {
        return purchasesLinesList;
    }

    public void setPurchasesLinesList(List<PurchasesLines> purchasesLinesList) {
        this.purchasesLinesList = purchasesLinesList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Manufacturers> getManufactersList() {
        return manufactersList;
    }

    public void setManufactersList(List<Manufacturers> manufactersList) {
        this.manufactersList = manufactersList;
    }

    public Manufacturers getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturers selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Documentypes> getDocumentTypesList() {
        return documentTypesList;
    }

    public void setDocumentTypesList(List<Documentypes> documentTypesList) {
        this.documentTypesList = documentTypesList;
    }

    public Documentypes getSelectedDocumentypes() {
        return selectedDocumentypes;
    }

    public void setSelectedDocumentypes(Documentypes selectedDocumentypes) {
        this.selectedDocumentypes = selectedDocumentypes;
    }

    public DocumentTypeConverter getDocumentTypeConverter() {
        return documentTypeConverter;
    }

    public void setDocumentTypeConverter(DocumentTypeConverter documentTypeConverter) {
        this.documentTypeConverter = documentTypeConverter;
    }
    
     public List<Products> completeProducts(String query) {  
        List<Products> suggestions = new ArrayList<Products>();  
          
        for(Products p : productsList) {  
            if(p.getName().toLowerCase().startsWith(query)){
                suggestions.add(p);  
            }
        }  
          
        return suggestions;  
    }  
     
     public List<Manufacturers> completeManufacturers(String query) {  
        List<Manufacturers> suggestions = new ArrayList<Manufacturers>();  
          
        for(Manufacturers p : manufactersList) {  
            if(p.getName().toLowerCase().startsWith(query)){
                suggestions.add(p);  
            }
        }  
          
        return suggestions;  
    }  
     
     public void handleDateSelect(SelectEvent event) {  
        try {  
            purchaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(""+event.getObject());
        } catch (ParseException ex) {
            Logger.getLogger(PurchasesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }  
     
     public void reinit(){
        purchaseLine = new PurchasesLines(); 
    }
     
    public void processLine(){
        //ol.setProductId(selectedProduct);
        //ol.setQuantity(quantity);
       
        if(purchaseLine.getQuantity() == 0){
            JsfUtil.getInstance().addErrorMessage("Debe poner una cantidad");
        }else{
            purchaseLine.setPurchaseId(purchase);
            total = total + purchaseLine.getBuyPrice() * purchaseLine.getQuantity();
            decimalFormat.format(total);
            this.purchasesLinesList.add(purchaseLine);
        }        
    }
    
    public String savePurchase(){
        try {
            purchase.setInvoiceNumber(invoiceNumber);
            purchase.setDescription(description);
            purchase.setDate(purchaseDate);
            purchase.setExpirationDate(expirationDate);
            purchase.setManufacturersId(selectedManufacturer);
            purchase.setInvoiceTotal(total);
            purchase.setDocumentypeId(selectedDocumentypes);
            ControllerManager.getInstance().getPurchasesJpaController().create(purchase);
           
            for (PurchasesLines pl : purchasesLinesList) {
                pl.setPurchaseId(purchase);
                ControllerManager.getInstance().getPurchasesLinesJpaController().create(pl);
                Productstocks ps = ControllerManager.getInstance().getProductstocksJpaController().
                        findProductStockEntities(pl.getProductId());
                if(ps != null){
                    ps.setProductId(pl.getProductId());
                    ps.setValue(ps.getValue() + pl.getQuantity());
                    ps.setLastChange(Calendar.getInstance().getTime());
                    ControllerManager.getInstance().getProductstocksJpaController().edit(ps);
                }else{
                    ps = new Productstocks();
                    ps.setProductId(pl.getProductId());
                    ps.setValue(ps.getValue() + pl.getQuantity());
                    ps.setLastChange(Calendar.getInstance().getTime());
                    ControllerManager.getInstance().getProductstocksJpaController().create(ps);
                }
            }
              
            JsfUtil.getInstance().addSuccessMessage("Compra guardada con éxito");
            
        } catch (Exception e) {
            JsfUtil.getInstance().addErrorMessage("Ocurrió un error al guardar la compra");
            e.printStackTrace();
            
        }
        
        
        return "../welcomePrimefaces.xhtml";
    }
}