/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ritesh
 */
@Named
@ViewScoped
public class CrudView implements Serializable {

    private List<Product> products = new ArrayList<>();

    private Product selectedProduct = new Product();

    private List<Product> selectedProducts = new ArrayList<>();

    private String errorMessage = "";

    @Inject
    private ProductService productService;

    @PostConstruct
    public void init() {
        this.products = this.productService.getClonedProducts(100);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        System.out.println("product setted!!!!\n\n\n");
        System.out.println("selected product id: " + this.getSelectedProduct().getId() + "\n\n\n");
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void openNew() {
        setErrorMessage("");
        this.selectedProduct = new Product();
    }

    public void saveProduct() {
        if (this.selectedProduct.getId() != null || !productService.proudctExist(this.selectedProduct.getName())) {
            if (this.selectedProduct.getId() == null) {
                productService.addProduct(this.selectedProduct);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
            } else {
                productService.updateProduct(this.selectedProduct);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Updated"));
            }
            init();
            PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        } else {
            setErrorMessage("product already exists");
        }
    }

    public void deleteProduct() {
        productService.removeProduct(this.selectedProduct.getId());
        this.selectedProduct = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedProducts.size();
            return size > 1 ? size + " products selected" : "1 product selected";
        }
        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selectedProducts != null && !this.selectedProducts.isEmpty();
    }

    public void deleteSelectedProducts() {
        for (Product product : this.selectedProducts) {
            productService.removeProduct(product.getId());
        }
        this.selectedProducts = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

}
