/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Paydocuments;
import com.productosur.hive.entities.Payments;
import com.productosur.hive.entities.Subsidiaries;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="ctactemb")
@ViewScoped
public class CtaCteMB {

     private Subsidiaries client;
     private List<Paydocuments> paydocumentList;
     private Paydocuments selectedPaydocument;
    /**
     * Creates a new instance of CtaCteMB
     */
    public CtaCteMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        client = ClientsMB.getInstance().getSelectedClient();
        paydocumentList = ControllerManager.getInstance().getPaydocumentsJpaController().findPaydocumentsEntities(client.getClientId());
    }

    public Subsidiaries getClient() {
        return client;
    }

    public void setClient(Subsidiaries client) {
        this.client = client;
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
