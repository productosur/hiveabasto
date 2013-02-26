/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Manufacturers;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="manufacturermb")
@SessionScoped
public class ManufacturerMB {

    private Manufacturers newManufacturer;
    private Manufacturers selectedManufacturer;
    private List<Manufacturers> manufacturersList;
    /**
     * Creates a new instance of ManufacterMB
     */
    public ManufacturerMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        this.manufacturersList = ControllerManager.getInstance().getManufacturersJpaController().findManufacturersEntities();
        this.newManufacturer = new Manufacturers();
        this.selectedManufacturer = new Manufacturers();
    }

    public Manufacturers getNewManufacturer() {
        return newManufacturer;
    }

    public void setNewManufacturer(Manufacturers newManufacturer) {
        this.newManufacturer = newManufacturer;
    }

    public Manufacturers getSelectedManufacturer() {
        return selectedManufacturer;
    }

    public void setSelectedManufacturer(Manufacturers selectedManufacturer) {
        this.selectedManufacturer = selectedManufacturer;
    }

    public List<Manufacturers> getManufacturersList() {
        return manufacturersList;
    }

    public void setManufacturersList(List<Manufacturers> manufacturersList) {
        this.manufacturersList = manufacturersList;
    }
    
    public void saveDataAction(){
        try {
            ControllerManager.getInstance().getManufacturersJpaController().create(newManufacturer);
         MessagesManager.printMessage("Proveedor guardado con Exito");
        } catch (Exception e) {
            MessagesManager.printMessage("Error al guardar Proveedor");
        }
    }
    
    public void refreshList(){
        this.manufacturersList = ControllerManager.getInstance().getManufacturersJpaController().findManufacturersEntities();
    }
    
    public void clearDataAction(){
        this.newManufacturer = new Manufacturers();
    }
}
