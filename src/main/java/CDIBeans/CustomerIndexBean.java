/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import ejb.AdminBeanLocal;
import ejb.UserBeanLocal;
import entity.Delivereditem;
import entity.Delivery;
import entity.Society;
import entity.User;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.ws.rs.ClientErrorException;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "customerIndexBean")
@ViewScoped
public class CustomerIndexBean implements Serializable {

    @EJB
    AdminBeanLocal adminBean;

    @EJB
    UserBeanLocal userBean;

    private Integer id;

    private String username;

    private String email;

    private String password;

    private String mobileNo;

    private String houseNo;

    private List<User> customers;

    private entity.Society societyId;

    private String errorMessage;

    private List<Society> societies;

    private List<Delivery> deliveries;

    private int selectedSocietyId = 0;

    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private String serverTimeZone = ZoneId.systemDefault().toString();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        deliveries = adminBean.getDeliveriesByCustomerId(KeepRecord.getUserId());

        for (Delivery delivery : deliveries) {
            double total = 0;
            for (Delivereditem item : delivery.getDelivereditemCollection()) {
                total += (item.getQuantity() * item.getProductId().getPrice());

                DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                        .title(item.getProductId().getName() + " - " + item.getQuantity() + " (₹ " + item.getProductId().getPrice() + ")")
                        .startDate(delivery.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .endDate(delivery.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusSeconds(1))
                        .draggable(false)
                        .borderColor("orange")
                        .build();
                eventModel.addEvent(event);
            }
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title("Total: ₹ " + total)
                    .startDate(delivery.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .endDate(delivery.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .draggable(false)
                    .allDay(true)
                    .textColor("yellow")
                    .styleClass(username)
                    .build();
            eventModel.addEvent(event);
        }

        try {
            try {
                User customer = adminBean.getCustomer(KeepRecord.getUserId());
                if (customer != null) {
                    List<Society> societies = adminBean.getSocieties();
                    if (!societies.isEmpty()) {
                        this.setSocieties(societies);
                    }
                    System.out.println("\n\n\ncustomer found-----\n\n\n");
                    this.setEmail(customer.getEmail());
                    this.setHouseNo(customer.getHouseNo());
                    this.setId(customer.getId());
                    this.setMobileNo(customer.getMobileNo());
                    this.setSocietyId(customer.getSocietyId());
                    this.setSelectedSocietyId(customer.getSocietyId().getId());
                    this.setUsername(customer.getUsername());
                }
            } catch (Exception ex) {
                System.err.println("\n\n\nCustomer():::unauthorized user\n\n\n");
                unauthorizedUser();
            }
        } catch (ClientErrorException e) {
            System.err.println("----customersBean:init()---- :" + e.getMessage());
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public Society getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Society societyId) {
        this.societyId = societyId;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Creates a new instance of Customer
     */
    public CustomerIndexBean() {
    }

    public void unauthorizedUser() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/dropApplication/common/unauthorized.jsf");
        } catch (IOException ex) {
            System.err.println("exception: ");
        }
    }

    public String editProfile() {
        return "profile";
    }

    public void updateProfile() {
        User oldRecord = adminBean.getCustomer(KeepRecord.getUserId());
        if (!oldRecord.getUsername().equals(getUsername()) && adminBean.isUsernameExists(getUsername())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "username already exists."));
        } else if (!oldRecord.getMobileNo().equals(getMobileNo()) && adminBean.isMobileNumberExists(getMobileNo())) {
            setErrorMessage("mobile number already exists.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "mobile number already exists."));
        } else if (!oldRecord.getEmail().equals(getEmail()) && adminBean.isEmailExists(getEmail())) {
            setErrorMessage("email address already exists.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "email address already exists."));
        } else if (userBean.updateCustomer(KeepRecord.getUserId(), getUsername(), getHouseNo(), getMobileNo(), getSelectedSocietyId(), getEmail(), null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile Updated"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Profile updation failed!"));

        }
    }
}
