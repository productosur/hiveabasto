/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import com.productosur.hive.entities.Documentypes;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Paynumberselections;
import com.productosur.hive.entities.Subsidiaries;
import com.productosur.hive.managedbeans.converters.DocumentTypeConverter;
import com.productosur.hive.util.JsfUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="invoicemb")
@ViewScoped
public class InvoiceMB {

    private List<Documentypes> documentTypesList;
    private Documentypes selectedDocumentypes;
    private DocumentTypeConverter documentTypeConverter;
    private Double total;
    private DecimalFormat decimalFormat =  new DecimalFormat("#.##");
    private String description; 
    private Date invoiceDate;
    private Date expirationDate;
    private Integer invoiceNumber;
    private String serial;
    private Subsidiaries client;
    private Orders activeOrder;
    
     
    /**
     * Creates a new instance of PurchasesMB
     */
    public InvoiceMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        documentTypesList = ControllerManager.getInstance().getDocumentypesJpaController().findDocumentypesEntities();
        documentTypeConverter = new DocumentTypeConverter();
        client = ClientsMB.getInstance().getSelectedClient();
        activeOrder = ControllerManager.getInstance().getOrdersJpaController().findActiveOrderEntity(client);
        invoiceDate = Calendar.getInstance().getTime();
        total = 0.0;
        getNextInvoiceNumber();
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

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Subsidiaries getClient() {
        return client;
    }

    public void setClient(Subsidiaries client) {
        this.client = client;
    }

    public Orders getActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(Orders activeOrder) {
        this.activeOrder = activeOrder;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public void handleDateSelect(SelectEvent event) {  
        try {  
            invoiceDate = new SimpleDateFormat("dd/MM/yyyy").parse(""+event.getObject());
        } catch (ParseException ex) {
            Logger.getLogger(InvoiceMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }  
     
   
     
   
    public String savePurchase(){
        try {
           
            
            JsfUtil.getInstance().addSuccessMessage("Compra guardada con éxito");
            
        } catch (Exception e) {
            JsfUtil.getInstance().addErrorMessage("Ocurrió un error al guardar la compra");
            e.printStackTrace();
            
        }
        
        return "../welcomePrimefaces.xhtml";
    }
    
    private void getNextInvoiceNumber(){
        Paynumberselections number = ControllerManager.getInstance().getPaynumberselectionsJpaController().findActiveInvoiceNumber();
        if(number != null){
            invoiceNumber = number.getCurrent();
            serial = number.getSerial();
            number.setCurrent(invoiceNumber + 1);
            try {
                ControllerManager.getInstance().getPaynumberselectionsJpaController().edit(number);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(InvoiceMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(InvoiceMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}