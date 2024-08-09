/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import client.RESTClient;
import entity.User;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ritesh
 */
@Named(value = "business")
@Dependent
public class Business {

    /**
     * Creates a new instance of Business
     */
    private Integer id;

    private String businessName;

    private String address;

    private Date createdAt;

    private User ownerId;
    
    RESTClient client;
    
    GenericType<Business> gBusiness;
    Response response;
    
    @PostConstruct
    public void init() {
       client=new RESTClient();
       response=client.getBusiness(Response.class, 1);
       gBusiness=new GenericType<Business>(){};
       Business business=response.readEntity(gBusiness);
       this.setId(business.getId());
       this.setAddress(business.getAddress());
       this.setBusinessName(business.getBusinessName());
       this.setCreatedAt(business.getCreatedAt());
       this.setOwnerId(business.getOwnerId());
    }

    public Business() {
//       client=new RESTClient();
//       response=client.getBusiness(Response.class, 1);
//       gBusiness=new GenericType<Business>(){};
//       Business business=response.readEntity(gBusiness);
//       this.setId(business.getId());
//       this.setAddress(business.getAddress());
//       this.setBusinessName(business.getBusinessName());
//       this.setCreatedAt(business.getCreatedAt());
//       this.setOwnerId(business.getOwnerId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }   
}
