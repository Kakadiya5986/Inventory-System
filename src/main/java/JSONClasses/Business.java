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
public class Business {

    @JsonbProperty("business_id")
    private int business_id;
    @JsonbProperty("owner_id")
    @NotNull
    @NotBlank(message ="Please provide typeId")
    private int owner_id;
    @JsonbProperty("type_id")
    private int type_id;
    @JsonbProperty("business_address")
    private String business_address;
    @JsonbProperty("business_name")
    private String business_name;

    public Business() {
    }

    public Business(int business_id, int owner_id, int type_id, String busness_address, String business_name) {
        this.business_id = business_id;
        this.owner_id = owner_id;
        this.type_id = type_id;
        this.business_address = busness_address;
        this.business_name = business_name;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String busness_address) {
        this.business_address = busness_address;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    @Override
    public String toString(){
        return "["+this.getBusiness_name()+", "+this.getBusiness_address()+", "+this.getOwner_id()+", "+this.getType_id()+"]";
    }
}
