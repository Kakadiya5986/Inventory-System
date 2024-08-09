/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import client.RESTClient;
import ejb.AdminBean;
import ejb.AdminBeanLocal;
import entity.Business;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "businessOwner")
@RequestScoped
public class BusinessOwner {

    private String businessName;
    RESTClient cl;
    String message = "";
    boolean isUnauthorized;
    @EJB
    AdminBeanLocal bean;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of BusinessOwner
     */
    public BusinessOwner() {

    }

    public String getBusinessName() {
        Business business = bean.getBusiness();
        setBusinessName(business.getBusinessName());
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void unauthorizedUser() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/dropApplication/common/unauthorized.jsf");
        } catch (IOException ex) {
            System.err.println("exception: ");
            ex.printStackTrace();
        }
    }

    public String homePage() {
        return "index";
    }
}
