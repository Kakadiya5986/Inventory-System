/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package JSFbeans;

import ejb.AdminBeanLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author ritesh
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean {

    @EJB
    AdminBeanLocal bean;

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
    @NotEmpty(message = "password required")
    private String password;

    public RegisterBean() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        this.businessMobile = businessMobile;
    }

    public String submit() {
        if (validateFields()) {
            boolean addBusinessWithOwner = bean.addBusinessWithOwner(businessOwnerName, businessEmail, password, businessMobile, businessName, businessAddress, 0);
            if (addBusinessWithOwner) {
                return "Login";
            } else {
                setErrorMessage("something went wrong while registering business");
                return null;
            }
        } else {
            return null;
        }
    }
    
    public String updateProfile() {
        if (validateFields()) {
            boolean addBusinessWithOwner = bean.addBusinessWithOwner(businessOwnerName, businessEmail, password, businessMobile, businessName, businessAddress, 0);
            if (addBusinessWithOwner) {
                return "Login";
            } else {
                setErrorMessage("something went wrong while registering business");
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean validateFields() {
        if (!validateBusinessName()) {
            setErrorMessage("business name already exist");
            return false;
        } else if (!validateUsername()) {
            setErrorMessage("username already exist");
            return false;
        } else if (!validateEmail()) {
            setErrorMessage("email address already exist");
            return false;
        } else if (!validateMobileNo()) {
            setErrorMessage("mobile number already exist");
            return false;
        }
        return true;
    }

    public boolean validateUsername() {
        return !bean.isUsernameExists(businessOwnerName);
    }

    public boolean validateBusinessName() {
        return !bean.isBusinessExists(businessName);
    }

    public boolean validateEmail() {
        return !bean.isEmailExists(businessEmail);
    }

    public boolean validateMobileNo() {
        return !bean.isMobileNumberExists(businessName);
    }
}
