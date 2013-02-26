/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Clients;
import com.productosur.hive.entities.Subsidiaries;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author Productosur
 */
@ManagedBean(name="clientsmb")
@SessionScoped
public class ClientsMB {

    /**
     * Creates a new instance of ClientsMB
     */
    private List<Clients> clientsList;
    private List<Subsidiaries> subsidiariesList;
    private Subsidiaries selectedClient;
    private Clients newClient;
    private Subsidiaries newSubsidiarie;
  
    
    public ClientsMB() {}
    
     public static ClientsMB getInstance() {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        return (LoginController)fc.getApplication().evaluateExpressionGet(fc, "#{login}", LoginController.class);
        return BeanFactory.getBeanInstance("clientsmb", ClientsMB.class);
    }
    
    @PostConstruct
    public void inicialize(){
        newClient = new Clients();
        newSubsidiarie = new Subsidiaries();
        clientsList = ControllerManager.getInstance().getClientsController().findClientsEntities();
        subsidiariesList = ControllerManager.getInstance().getSubsidiariesJpaController().findSubsidiariesEntities();
    }
    
    public List<Clients> getClientsList(){
        return clientsList;
    }

    public List<Subsidiaries> getSubsidiariesList(){
        return subsidiariesList;
    }
    
    public Subsidiaries getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Subsidiaries selectedClient) {
        this.selectedClient = selectedClient;
    }

    public Clients getNewClient() {
        return newClient;
    }

    public void setNewClient(Clients newClient) {
        this.newClient = newClient;
    }

    public Subsidiaries getNewSubsidiarie() {
        return newSubsidiarie;
    }

    public void setNewSubsidiarie(Subsidiaries newSubsidiarie) {
        this.newSubsidiarie = newSubsidiarie;
    }

    public String sales(){
        
        return "../index";
    }
    
    
    public void saveClientAction(){
        try {
            ControllerManager.getInstance().getClientsController().create(newClient);
            newSubsidiarie.setClientId(newClient);
            newSubsidiarie.setName(newClient.getBusinessName());
            ControllerManager.getInstance().getSubsidiariesJpaController().create(newSubsidiarie);
            MessagesManager.printMessage("Cliente guardado con Exito");
        } catch (Exception e) {
            MessagesManager.printMessage("Error al guardar Cliente");
        }
       
    }
    
    public void refreshList(){
        clientsList = ControllerManager.getInstance().getClientsController().findClientsEntities();
        this.subsidiariesList = ControllerManager.getInstance().getSubsidiariesJpaController().findSubsidiariesEntities();
    }
    
    public void clearDataAction(){
        newClient = new Clients();
        newSubsidiarie = new Subsidiaries();
    }
}

