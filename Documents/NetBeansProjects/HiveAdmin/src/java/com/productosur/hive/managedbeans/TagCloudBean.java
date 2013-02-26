/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="tagCloudBean")
@SessionScoped
public class TagCloudBean {

    /**
     * Creates a new instance of TagCloudBean
     */
   
    
    private TagCloudModel model;  
      
    public TagCloudBean() {  
        model = new DefaultTagCloudModel();  
        model.addTag(new DefaultTagCloudItem("Clientes","/faces/pages/clients_subsidiary.xhtml", 3));  
        model.addTag(new DefaultTagCloudItem("Productos", "/faces/pages/products.xhtml", 1));  
        model.addTag(new DefaultTagCloudItem("Cuentas Corrientes", "/faces/pages/clients_subsidiary.xhtml", 2));  
        model.addTag(new DefaultTagCloudItem("Conciliación", "/ui/tagCloud.jsf", 5));  
        model.addTag(new DefaultTagCloudItem("Resumen de Ventas", 4));  
        model.addTag(new DefaultTagCloudItem("Proveedores", "/ui/tagCloud.jsf", 2));  
        model.addTag(new DefaultTagCloudItem("Empleados", 1));  
    }  
  
    public TagCloudModel getModel() {  
        return model;  
    }  
      
    public void onSelect(SelectEvent event) {  
        TagCloudItem item = (TagCloudItem) event.getObject();  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
}
