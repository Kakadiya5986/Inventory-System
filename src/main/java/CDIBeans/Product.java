/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ritesh
 */
public class Product implements Serializable {
   
    private Integer id;
    private String name;
    private int price;
    private Date createdAt;

    public Product() {
    }

    public Product(Integer id, String name, int price, Date createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    @Override
    public Product clone() {
        return new Product(getId(),getName(),getPrice(),getCreatedAt());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
