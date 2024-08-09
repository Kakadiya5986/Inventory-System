/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package BusinessOwnerCDIBeans;

import ejb.AdminBeanLocal;
import entity.Business;
import entity.User;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.ClientErrorException;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "profileBean")
@ViewScoped
public class ProfileBean implements Serializable {

    @EJB
    AdminBeanLocal bean;

    private User user;
    private Business business;

    private String errorMessage;
    @NotEmpty(message = "business name required")
    private String businessName;
    @NotEmpty(message = "business owner name required")
    private String businessOwnerName;
    @NotEmpty(message = "business address required")
    private String businessAddress;
    @NotEmpty(message = "mobile number required")
    private String businessMobile;
    @NotEmpty(message = "email address required")
    private String businessEmail;

    @PostConstruct
    public void init() {
        try {
            business = bean.getBusiness(KeepRecord.getBusinessId());
            businessName = business.getBusinessName();
            businessAddress = business.getAddress();
            user = bean.getBusinessOwner(KeepRecord.getUserId());
            businessOwnerName = user.getUsername();
            businessMobile = user.getMobileNo();
            businessEmail = user.getEmail();
        } catch (ClientErrorException e) {
            System.err.println("----ProfileBean:init()---- :" + e.getMessage());
        }
    }

    public ProfileBean() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessOwnerName() {
        return businessOwnerName;
    }

    public void setBusinessOwnerName(String businessOwnerName) {
        this.businessOwnerName = businessOwnerName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        this.businessMobile = businessMobile;
    }

    public void updateProfile() {
        if (!user.getUsername().equals(businessOwnerName) && bean.isUsernameExists(businessOwnerName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Username already exist !!!"));
            return;
        }

        boolean updateSuccessfull = bean.updateBusinessProfile(user.getId(), businessName, businessAddress, businessOwnerName, businessEmail, businessMobile, null);
        if (updateSuccessfull) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile Updated"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Profile updation failed !!!"));
        }
        init();

    }
}
