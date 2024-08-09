/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package BusinessOwnerCDIBeans;

import ejb.AdminBeanLocal;
import entity.Society;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.ws.rs.ClientErrorException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author alvis
 */
@Named(value = "societiesBean")
@ViewScoped
public class SocietiesBean implements Serializable {

    @EJB
    AdminBeanLocal adminBean;

    private List<Society> societies;
    private Society selectedSociety;
    private List<Society> selectedSocieties;

    private String errorMessage;

    @PostConstruct
    public void init() {
        try {
            societies = adminBean.getSocieties();
        } catch (ClientErrorException e) {
            System.err.println("----SocietiesBean:init()---- :" + e.getMessage());
        }
    }

    public List<Society> getSocities() {
        return societies;
    }

    public Society getSelectedSociety() {
        return selectedSociety;
    }

    public void setSelectedSociety(Society selectedSociety) {
        this.selectedSociety = selectedSociety;
        System.out.println("Society setted!!!!\n\n\n");
        System.out.println("selected society id: " + this.getSelectedSociety().getId() + "\n\n\n");
    }

    public List<Society> getSelectedSocieties() {
        return selectedSocieties;
    }

    public void setSelectedSocieties(List<Society> selectedSocieties) {
        this.selectedSocieties = selectedSocieties;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void openNew() {
        setErrorMessage("");
        this.selectedSociety = new Society();
    }

    public boolean hasSelectedSocieties() {
        return this.selectedSocieties != null && !this.selectedSocieties.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSocieties()) {
            int size = this.selectedSocieties.size();
            return size > 1 ? size + " societies selected" : "1 society selected";
        }
        return "Delete";
    }

    public void deleteSelectedSocieties() {
        for (Society society : this.selectedSocieties) {
            adminBean.removeSociety(society.getId());
        }
        this.selectedSocieties = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Societies Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void deleteSociety() {
        adminBean.removeSociety(this.selectedSociety.getId());
        this.selectedSociety = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Society Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void saveSociety() {
        try {
            if (this.selectedSociety.getId() != null || !isExist(this.selectedSociety.getName())) {
                if (this.selectedSociety.getId() == null) {
                    adminBean.addSociety(selectedSociety.getName());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Society Added"));
                } else {
                    adminBean.updateSociety(selectedSociety.getId(), selectedSociety.getName());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Society Updated"));
                }
                init();
                PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            } else {
                setErrorMessage("society already exists");
            }
        } catch (Exception e) {
            System.err.println("----SocietiesBean:saveSociety()---- :" + e.getMessage());
        }
    }

    public boolean isExist(String society_name) {
        return adminBean.isSocietyExists(society_name);
    }
}
