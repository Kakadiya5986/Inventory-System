/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ritesh
 */
public class BusinessDetails {

    @JsonbProperty("business_id")
    private int business_id;
    @JsonbProperty("business_name")
    private String business_name;
    @JsonbProperty("business_owner_id")
    private int business_owner_id;
    @JsonbProperty("business_owner_name")
    private String business_owner_name;
    @JsonbProperty("business_address")
    private String business_address;
    @JsonbProperty("business_owner_mobileno")
    private String business_owner_mobileno;
    @JsonbProperty("business_owner_email")
    private String business_owner_email;
    @JsonbProperty("business_owner_password")
    private String business_owner_password;
    @JsonbProperty("type_id")
    private int type_id;

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_owner_name() {
        return business_owner_name;
    }

    public void setBusiness_owner_name(String business_owner_name) {
        this.business_owner_name = business_owner_name;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getBusiness_owner_mobileno() {
        return business_owner_mobileno;
    }

    public void setBusiness_owner_mobileno(String business_owner_mobileno) {
        this.business_owner_mobileno = business_owner_mobileno;
    }

    public String getBusiness_owner_email() {
        return business_owner_email;
    }

    public void setBusiness_owner_email(String business_owner_email) {
        this.business_owner_email = business_owner_email;
    }

    public String getBusiness_owner_password() {
        return business_owner_password;
    }

    public void setBusiness_owner_password(String business_owner_password) {
        this.business_owner_password = business_owner_password;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getBusiness_owner_id() {
        return business_owner_id;
    }

    public void setBusiness_owner_id(int business_owner_id) {
        this.business_owner_id = business_owner_id;
    }

}
