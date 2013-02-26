/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Purchases;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="purchasereportmb")
@ViewScoped
public class PurchasesReportsMB {

    private List<Purchases> purchasesList;
    private Purchases selectedPurchase;
    private Date fromDate;
    private Date toDate;
  
    /**
     * Creates a new instance of PurchasesReportsMB
     */
    public PurchasesReportsMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        purchasesList = ControllerManager.getInstance().getPurchasesJpaController().findPurchasesEntities();
        //selectedPurchase = new Purchases();
    }

    public List<Purchases> getPurchasesList() {
        return purchasesList;
    }

    public void setPurchasesList(List<Purchases> purchasesList) {
        this.purchasesList = purchasesList;
    }

    public Purchases getSelectedPurchase() {
        return selectedPurchase;
    }

    public void setSelectedPurchase(Purchases selectedPurchase) {
        this.selectedPurchase = selectedPurchase;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    
    
}
