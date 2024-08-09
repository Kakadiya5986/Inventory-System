/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author ritesh
 */
public class Customer {

    @JsonbProperty("id")
    private int customer_id;
    @JsonbProperty("business_id")
    private int business_id;
    @JsonbProperty("society_id")
    private int society_id;
    @JsonbProperty("username")
    private String customer_name;
    @JsonbProperty("house_no")
    private String customer_house_no;
    @JsonbProperty("email")
    private String customer_email;
    @JsonbProperty("password")
    private String customer_password;
    @JsonbProperty("mobile_no")
    private String customer_mobile_no;

    public Customer() {
    }

    public Customer(int customer_id, int business_id, int society_id, String customer_name, String customer_house_no, String customer_email, String customer_password, String customer_mobile_no) {
        this.customer_id = customer_id;
        this.business_id = business_id;
        this.society_id = society_id;
        this.customer_name = customer_name;
        this.customer_house_no = customer_house_no;
        this.customer_email = customer_email;
        this.customer_password = customer_password;
        this.customer_mobile_no = customer_mobile_no;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getSociety_id() {
        return society_id;
    }

    public void setSociety_id(int society_id) {
        this.society_id = society_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_house_no() {
        return customer_house_no;
    }

    public void setCustomer_house_no(String customer_house_no) {
        this.customer_house_no = customer_house_no;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_mobile_no() {
        return customer_mobile_no;
    }

    public void setCustomer_mobile_no(String customer_mobile_no) {
        this.customer_mobile_no = customer_mobile_no;
    }
    
    
}
