/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package BusinessOwnerCDIBeans;

import JSONClasses.DeliveredItem;
import ejb.AdminBeanLocal;
import entity.Delivereditem;
import entity.Product;
import entity.Society;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.ClientErrorException;
import org.primefaces.PrimeFaces;
import record.KeepRecord;

/**
 *
 * @author alvis
 */
@Named(value = "indexBean")
@ViewScoped
public class IndexBean implements Serializable {

    @EJB
    AdminBeanLocal adminBean;
    private List<User> customers;

    private List<Society> societies;
    private int selectedSocietyId = 0;

    private List<Product> products;
    private int selectedDeliveryId = 0;
    private int selectedCustomerId = 0;
    private List<DeliveredItem> deliveryProducts = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            products = adminBean.getProducts();
            societies = adminBean.getSocieties();
            if (!societies.isEmpty()) {
                if (selectedSocietyId == 0) {
                    selectedSocietyId = societies.get(0).getId();
                }
                customers = adminBean.getCustomersBySociety(selectedSocietyId);
            }
        } catch (ClientErrorException e) {
            System.err.println("----IndexBean:init()---- :" + e.getMessage());
        }
    }

    public void onSocietyChange() {
        if (selectedSocietyId != 0) {
            customers = adminBean.getCustomersBySociety(selectedSocietyId);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<DeliveredItem> getDeliveryProducts() {
        return deliveryProducts;
    }

    public int getSelectedDeliveryId() {
        return selectedDeliveryId;
    }

    public void setSelectedDeliveryId(int selectedDeliveryId) {
        this.selectedDeliveryId = selectedDeliveryId;
    }

    public int getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(int selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

    public void setDeliveryProducts(List<Delivereditem> deliveryProducts) {
        this.deliveryProducts.clear();

        // We are iterating because we want to display all products available at the moment
        // Already delivered products will be displayed with the existing quantity and there's with 0
        for (Product product : products) {

            boolean productAdded = false;
            DeliveredItem productToBeAdded = new DeliveredItem();

            if (deliveryProducts != null) {
                for (Delivereditem deliveryProduct : deliveryProducts) {
                    if (deliveryProduct.getProductId().getName().equals(product.getName())) {
                        productToBeAdded.setProduct_id(deliveryProduct.getProductId().getId());
                        productToBeAdded.setName(deliveryProduct.getProductId().getName());
                        productToBeAdded.setQuantity(deliveryProduct.getQuantity());
                        productToBeAdded.setPrice(deliveryProduct.getProductId().getPrice());
                        this.deliveryProducts.add(productToBeAdded);
                        productAdded = true;
                        break;
                    }
                }
            }
            if (!productAdded) {
                productToBeAdded.setProduct_id(product.getId());
                productToBeAdded.setName(product.getName());
                productToBeAdded.setPrice(product.getPrice());
                productToBeAdded.setQuantity(0);
                this.deliveryProducts.add(productToBeAdded);
            }
        }
    }

    public void decreaseQuantity(DeliveredItem item) {
        if (item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public void increaseQuantity(DeliveredItem item) {
        item.setQuantity(item.getQuantity() + 1);
    }

    public void addUpdateDelivery() {
        List<Delivereditem> deliveryItems = new ArrayList<>();

        for (DeliveredItem deliveryProduct : deliveryProducts) {
            if (deliveryProduct.getQuantity() != 0) {
                Delivereditem item = new Delivereditem();
                item.setId(deliveryProduct.getProduct_id());
                item.setName(deliveryProduct.getName());
                item.setPrice(deliveryProduct.getPrice());
                item.setQuantity(deliveryProduct.getQuantity());
                item.setProductId(new Product());
                item.getProductId().setId(deliveryProduct.getProduct_id());
                item.getProductId().setName(deliveryProduct.getName());
                item.getProductId().setPrice(deliveryProduct.getPrice());
                deliveryItems.add(item);
            }
        }

        try {
            if (selectedDeliveryId == 0) {
                adminBean.addDelivery(selectedCustomerId, deliveryItems);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Delivery added"));
            } else {
                adminBean.updateDelivery(selectedDeliveryId, deliveryItems);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Delivery updated"));
            }
            init();
            PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        } catch (Exception e) {
            System.err.println("IndexBean: addUpdateDelivery :" + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error updating delivery !!!"));
        }
    }

    public void deleteDelivery() {
        try {
            adminBean.removeDelivery(selectedDeliveryId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Delivery Deleted"));
            init();
            PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        } catch (Exception e) {
            System.err.println("IndexBean: deleteDelivery :" + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting delivery !!!"));
        }
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public List<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(List<Society> societies) {
        this.societies = societies;
    }

    public int getSelectedSocietyId() {
        return selectedSocietyId;
    }

    public void setSelectedSocietyId(int selectedSocietyId) {
        this.selectedSocietyId = selectedSocietyId;
    }
    
    public boolean isBusinessOwner() {
        return KeepRecord.getRoles().contains("Business Owner");
    }
}
