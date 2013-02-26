/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Productosur
 */
public class MessagesManager {
    
    
    public static void printMessage(String msg){
        FacesMessage fmsg = new FacesMessage(msg); 
  
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }
    
}
