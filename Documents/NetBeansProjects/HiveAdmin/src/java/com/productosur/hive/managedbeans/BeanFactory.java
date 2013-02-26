/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import javax.faces.context.FacesContext;

/**
 *
 * @author Productosur
 */
class BeanFactory {
    public static <T> T getBeanInstance(final String beanName, final  Class<? extends T> expectedType){
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getApplication().evaluateExpressionGet(fc, "#{" + beanName + "}", expectedType);
    } 
}
