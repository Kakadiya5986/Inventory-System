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
public class BusinessOwner {
    @JsonbProperty("business_owner_id")
    private int business_owner_id;
    @JsonbProperty("business_owner_name")
    private String business_owner_name;
    @JsonbProperty("business_owner_email")
    private String business_owner_email;
    @JsonbProperty("business_owner_mobileno")
    private String business_owner_mobileno;
    @JsonbProperty("business_owner_password")
    private String business_owner_password;

    public BusinessOwner() {
    }

    public BusinessOwner(int business_owner_id, String business_owner_name, String business_owner_email, String business_owner_mobileno, String business_owner_password) {
        this.business_owner_id = business_owner_id;
        this.business_owner_name = business_owner_name;
        this.business_owner_email = business_owner_email;
        this.business_owner_mobileno = business_owner_mobileno;
        this.business_owner_password = business_owner_password;
    }

    public int getBusiness_owner_id() {
        return business_owner_id;
    }

    public void setBusiness_owner_id(int business_owner_id) {
        this.business_owner_id = business_owner_id;
    }

    public String getBusiness_owner_name() {
        return business_owner_name;
    }

    public void setBusiness_owner_name(String business_owner_name) {
        this.business_owner_name = business_owner_name;
    }

    public String getBusiness_owner_email() {
        return business_owner_email;
    }

    public void setBusiness_owner_email(String business_owner_email) {
        this.business_owner_email = business_owner_email;
    }

    public String getBusiness_owner_mobileno() {
        return business_owner_mobileno;
    }

    public void setBusiness_owner_mobileno(String business_owner_mobileno) {
        this.business_owner_mobileno = business_owner_mobileno;
    }

    public String getBusiness_owner_password() {
        return business_owner_password;
    }

    public void setBusiness_owner_password(String business_owner_password) {
        this.business_owner_password = business_owner_password;
    }
    
}
