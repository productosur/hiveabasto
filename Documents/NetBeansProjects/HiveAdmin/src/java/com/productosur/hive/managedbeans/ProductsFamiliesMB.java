/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.entities.Productscategories;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="productsfamiliesmb")
@SessionScoped
public class ProductsFamiliesMB {

    private Productscategories newProductFamily;
    private Productscategories selectedProductFamily;
    private List<Productscategories> productsFamiliesList;
    
    
    /**
     * Creates a new instance of ProductsFamiliesMB
     */
    public ProductsFamiliesMB() {
    }
    
    @PostConstruct
    public void inicialize(){
        this.newProductFamily = new Productscategories();
        this.selectedProductFamily = new Productscategories();
        this.productsFamiliesList = ControllerManager.getInstance().getProductscategoriesJpaController().findProductscategoriesEntities();
    }

    public Productscategories getNewProductFamily() {
        return newProductFamily;
    }

    public void setNewProductFamily(Productscategories newProductFamily) {
        this.newProductFamily = newProductFamily;
    }

    public Productscategories getSelectedProductFamily() {
        return selectedProductFamily;
    }

    public void setSelectedProductFamily(Productscategories selectedProductFamily) {
        this.selectedProductFamily = selectedProductFamily;
    }

    public List<Productscategories> getProductsFamiliesList() {
        return productsFamiliesList;
    }

    public void setProductsFamiliesList(List<Productscategories> productsFamiliesList) {
        this.productsFamiliesList = productsFamiliesList;
    }
    
    public void saveDataAction(){
        try {
            ControllerManager.getInstance().getProductscategoriesJpaController().create(newProductFamily);
             MessagesManager.printMessage("Familia de Producto guardada con Exito");
        } catch (Exception e) {
            MessagesManager.printMessage("Error al guardar Familia de Producto");
        }
        
    }
    
     public void refreshList(){
         this.productsFamiliesList = ControllerManager.getInstance().getProductscategoriesJpaController().findProductscategoriesEntities();
     }

      public void clearDataAction(){
          this.newProductFamily = new Productscategories();
      }
}
