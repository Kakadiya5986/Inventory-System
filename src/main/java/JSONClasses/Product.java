/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ritesh
 */
public class Product {

    @JsonbProperty("id")
    private int id;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("price")
    private int price;
    @JsonbProperty("business_id")
    private int business_id;

    public Product() {
        this.id=0;
    }

    public Product(int id, String name, int price, int business_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.business_id = business_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }
}
