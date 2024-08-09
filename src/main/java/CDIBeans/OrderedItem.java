/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CDIBeans;

import java.util.Date;

/**
 *
 * @author ritesh
 */
public class OrderedItem {

    private Integer id;
    private String name;
    private int price;
    private int quantity;
    private Date createdAt;
    private entity.Business businessId;
    private entity.Delivery deliveryId;
    private entity.Product productId;

    public OrderedItem() {
    }

    public OrderedItem(Integer id, String name, int price, int quantity, Date createdAt, entity.Business businessId, entity.Delivery deliveryId, entity.Product productId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.businessId = businessId;
        this.deliveryId = deliveryId;
        this.productId = productId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {        
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public entity.Business getBusinessId() {
        return businessId;
    }

    public void setBusinessId(entity.Business businessId) {
        this.businessId = businessId;
    }

    public entity.Delivery getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(entity.Delivery deliveryId) {
        this.deliveryId = deliveryId;
    }

    public entity.Product getProductId() {
        return productId;
    }

    public void setProductId(entity.Product productId) {
        this.productId = productId;
    }

}
