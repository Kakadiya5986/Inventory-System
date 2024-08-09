/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author ritesh
 */
public class Delivery {

    @JsonbProperty("customer_id")
    int customer_id;
    @JsonbProperty("delivery_id")
    int delivery_id;
    @JsonbProperty("delivered_items")
    List<DeliveredItem> delivered_items;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public List<DeliveredItem> getDelivered_items() {
        return delivered_items;
    }

    public void setDelivered_items(List<DeliveredItem> delivered_items) {
        this.delivered_items = delivered_items;
    }

}
