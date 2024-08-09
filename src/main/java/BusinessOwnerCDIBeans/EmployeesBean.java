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
@Named(value = "employeesBean")
@ViewScoped
public class EmployeesBean implements Serializable {

    @EJB
    AdminBeanLocal adminBean;

    private List<User> employees;
    private User selectedEmployee;
    private List<User> selectedEmployees;

    private String errorMessage;

    @PostConstruct
    public void init() {
        try {
            employees = adminBean.getEmployees();
        } catch (ClientErrorException e) {
            System.err.println("----EmployeesBean:init()---- :" + e.getMessage());
        }
    }

    public List<User> getEmployees() {
        return employees;
    }

    public User getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(User selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
        System.out.println("Employee setted!!!!\n\n\n");
        System.out.println("selected employee id: " + this.getSelectedEmployee().getId() + "\n\n\n");
    }

    public List<User> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<User> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void openNew() {
        setErrorMessage("");
        this.selectedEmployee = new User();
    }

    public boolean hasSelectedEmployees() {
        return this.selectedEmployees != null && !this.selectedEmployees.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployees()) {
            int size = this.selectedEmployees.size();
            return size > 1 ? size + " employees selected" : "1 employee selected";
        }
        return "Delete";
    }

    public void deleteSelectedEmployees() {
        for (User employee : this.selectedEmployees) {
            adminBean.removeEmployee(employee.getId());
        }
        this.selectedEmployees = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employees Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void deleteEmployee() {
        adminBean.removeEmployee(this.selectedEmployee.getId());
        this.selectedEmployee = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void saveEmployee() {
        try {
            if (this.selectedEmployee.getId() != null || !isExist(this.selectedEmployee.getUsername())) {
                if (this.selectedEmployee.getId() == null) {
                    adminBean.addEmployee(selectedEmployee.getUsername(), selectedEmployee.getMobileNo(), selectedEmployee.getEmail(), selectedEmployee.getPassword());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee Added"));
                } else {
                    // We don't allow user to change the Password in edit because of that hash thing
                    // I[Alvish] found out that we can't decrypt that hash
                    adminBean.updateEmployee(selectedEmployee.getId(), selectedEmployee.getUsername(), selectedEmployee.getMobileNo(), selectedEmployee.getEmail(), null);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employee Updated"));
                }
                init();
                PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            } else {
                setErrorMessage("employee already exists");
            }
        } catch (Exception e) {
            System.err.println("----EmployeesBean:saveEmployee()---- :" + e.getMessage());
        }
    }

    public boolean isExist(String username) {
        return adminBean.isUsernameExists(username);
    }
}
