/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Orderlines;
import com.productosur.hive.entities.Orders;
import com.productosur.hive.entities.Subsidiaries;
import com.productosur.hive.util.JsfUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="ordersmb")
@ViewScoped
public class OrdersMB {

    private List<Orders> ordersList;
    private Subsidiaries client;
    private Orders selectedOrder;
    private Orderlines selectedOrderLine;
   
    /**
     * Creates a new instance of OrdersMB
     */
    public OrdersMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        client = ClientsMB.getInstance().getSelectedClient();
        ordersList = ControllerManager.getInstance().getOrdersJpaController().findOrdersEntities(client);
       
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public Subsidiaries getClient() {
        return client;
    }

    public void setClient(Subsidiaries client) {
        this.client = client;
    }

    public Orders getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Orders selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public Orderlines getSelectedOrderLine() {
        return selectedOrderLine;
    }

    public void setSelectedOrderLine(Orderlines selectedOrderLine) {
        this.selectedOrderLine = selectedOrderLine;
    }
    
    public void onEdit(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Linea Modificada", ""+((Orderlines) event.getObject()).getId()); 
        
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
      
    public void onCancel(RowEditEvent event) {  
       FacesMessage msg = new FacesMessage("Cancelado", ""+((Orderlines) event.getObject()).getId()); 
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
        
    
   public void saveOrder(){
        try {
          
            for (Orderlines ol : selectedOrder.getOrderlinesCollection()) {          
                ControllerManager.getInstance().getOrderlinesJpaController().edit(ol);
            }
            ControllerManager.getInstance().getOrdersJpaController().edit(selectedOrder);
            
            JsfUtil.getInstance().addSuccessMessage("Pedido Modificado con Exito");
        } catch (Exception e) {
            JsfUtil.getInstance().addErrorMessage("Error de Persistencia al Guardar Pedido");
                Logger.getLogger(DeliveryMB.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        //return "clients_subsidiary?faces-redirect=true";
    }

    
}
