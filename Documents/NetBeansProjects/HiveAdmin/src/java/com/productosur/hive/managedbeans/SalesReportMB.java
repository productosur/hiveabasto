/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Paydocuments;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="salesreportmb")
@ViewScoped
public class SalesReportMB {

    private List<Paydocuments> paydocumentList;
    private Paydocuments selectedPaydocument;
     
    /**
     * Creates a new instance of SalesReportMB
     */
    public SalesReportMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        paydocumentList = ControllerManager.getInstance().getPaydocumentsJpaController().findPaydocumentsEntities();
    }

    public List<Paydocuments> getPaydocumentList() {
        return paydocumentList;
    }

    public void setPaydocumentList(List<Paydocuments> paydocumentList) {
        this.paydocumentList = paydocumentList;
    }

    public Paydocuments getSelectedPaydocument() {
        return selectedPaydocument;
    }

    public void setSelectedPaydocument(Paydocuments selectedPaydocument) {
        this.selectedPaydocument = selectedPaydocument;
    }
    
    
    
}
