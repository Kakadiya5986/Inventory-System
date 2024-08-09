/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import client.RESTClient;
import ejb.AdminBeanLocal;
import entity.Delivereditem;
import entity.Delivery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "customerService")
@RequestScoped
public class CustomerService {

    @EJB
    AdminBeanLocal adminBean;
    private List<OrderedItem> items;

    RESTClient client;

    Response response;

    GenericType<List<Delivery>> genericDeliveries;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
        client = new RESTClient();
    }

    public List<OrderedItem> getOrderedItems(int number) {
        try {
            Collection<entity.Delivery> deliveries = adminBean.getDeliveriesByCustomerId(KeepRecord.getUserId());
            System.err.println("deliveries: "+deliveries.size());
            if (deliveries.size()>0) {
                for (entity.Delivery d : deliveries) {
                    Collection<Delivereditem> d_items = d.getDelivereditemCollection();
                    for (Delivereditem i : d_items) {
                        OrderedItem o_item = new OrderedItem(i.getId(), i.getName(), i.getPrice(), i.getQuantity(), i.getCreatedAt(), i.getBusinessId(), i.getDeliveryId(), i.getProductId());
                        items.add(o_item);
                    }
                }
                return this.items;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("CDIBeans.CustomerService.getOrderedItems()\n\n");
            ex.printStackTrace();
            unauthorizedUser();
            return null;
        }
    }

    public void unauthorizedUser() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/dropApplication/common/unauthorized.jsf");
        } catch (IOException ex) {
            System.err.println("exception: ");
            ex.printStackTrace();
        }
    }
}
