/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package BusinessOwnerCDIBeans;

import ejb.AdminBeanLocal;
import entity.Society;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.ClientErrorException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author alvis
 */
@Named(value = "customersBean")
@ViewScoped
public class CustomersBean implements Serializable {

    @EJB
    AdminBeanLocal adminBean;

    private List<User> customers;
    private User selectedCustomer;
    private List<User> selectedCustomers;

    private List<Society> societies;
    private int selectedSocietyId = 0;

    private String errorMessage;

    @PostConstruct
    public void init() {
        try {
            societies = adminBean.getSocieties();
            customers = adminBean.getCustomers();
        } catch (ClientErrorException e) {
            System.err.println("----customersBean:init()---- :" + e.getMessage());
        }
    }

    public List<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(List<Society> societies) {
        this.societies = societies;
    }

    public int getSelectedSocietyId() {
        return selectedSocietyId;
    }

    public void setSelectedSocietyId(int selectedSocietyId) {
        this.selectedSocietyId = selectedSocietyId;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public User getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(User selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        this.selectedSocietyId = this.selectedCustomer.getSocietyId().getId();
        System.out.println("Customer setted!!!!\n\n\n");
        System.out.println("selected customer id: " + this.getSelectedCustomer().getId() + "\n\n\n");
    }

    public List<User> getSelectedCustomers() {
        return selectedCustomers;
    }

    public void setSelectedCustomers(List<User> selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void openNew() {
        setErrorMessage("");
        this.selectedCustomer = new User();
    }

    public boolean hasSelectedCustomers() {
        return this.selectedCustomers != null && !this.selectedCustomers.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCustomers()) {
            int size = this.selectedCustomers.size();
            return size > 1 ? size + " customers selected" : "1 customer selected";
        }
        return "Delete";
    }

    public void deleteSelectedCustomers() {
        for (User customer : this.selectedCustomers) {
            adminBean.removeCustomer(customer.getId());
        }
        this.selectedCustomers = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Customers Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void deleteCustomer() {
        adminBean.removeCustomer(this.selectedCustomer.getId());
        this.selectedCustomer = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Customer Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void saveCustomer() {
        try {
            if (this.selectedCustomer.getId() != null || !isExist(this.selectedCustomer.getUsername())) {
                if (this.selectedCustomer.getId() == null) {
                    adminBean.addCustomer(selectedCustomer.getUsername(), selectedCustomer.getHouseNo(), selectedCustomer.getMobileNo(), selectedSocietyId, selectedCustomer.getEmail(), selectedCustomer.getPassword());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Customer Added"));
                } else {
                    // We don't allow user to change the Password in edit because of that hash thing
                    // I[Alvish] found out that we can't decrypt that hash
                    adminBean.updateCustomer(selectedCustomer.getId(), selectedCustomer.getUsername(), selectedCustomer.getHouseNo(), selectedCustomer.getMobileNo(), selectedSocietyId, selectedCustomer.getEmail(), null);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Customer Updated"));
                }
                init();
                PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            } else {
                setErrorMessage("customer already exists");
            }
        } catch (Exception e) {
            System.err.println("----CustomersBean:saveCustomer()---- :" + e.getMessage());
        }
    }

    public boolean isExist(String username) {
        return adminBean.isUsernameExists(username);
    }
}
