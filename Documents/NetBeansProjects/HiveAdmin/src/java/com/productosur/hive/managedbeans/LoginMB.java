/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Productosur
 */
@ManagedBean(name="loginmb")
@RequestScoped
public class LoginMB {

    private String user;
    private String password;
    
    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String validateUser(){
        return "welcomePrimefaces";
    }
}
