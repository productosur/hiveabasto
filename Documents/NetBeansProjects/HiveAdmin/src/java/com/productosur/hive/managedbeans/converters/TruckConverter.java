/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans.converters;

import com.productosur.hive.dbcontrollermanager.ControllerManager;
import com.productosur.hive.dbcontrollers.TrucksJpaController;
import com.productosur.hive.entities.Products;
import com.productosur.hive.entities.Trucks;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Productosur
 */
public class TruckConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        TrucksJpaController controller = ControllerManager.getInstance().getTrucksJpaController();//(ProductsJpaController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "productJpa");
        return controller.findTrucks(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Trucks) {
            Trucks o = (Trucks) object;
            return o.getId() == null ? "" : ""+o.getId();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: com.productosur.hive.entities.Trucks");
        }
    }

}
