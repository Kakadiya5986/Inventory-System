/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBeans;

import ejb.AdminBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author ritesh
 */
@Named
@ApplicationScoped
public class ProductService {

    @EJB
    AdminBeanLocal bean;
    private List<Product> products;

    @PostConstruct
    public void init() {
        
    }

    public List<Product> getProducts() {
        products = new ArrayList<>();
        List<entity.Product> eProducts = bean.getProducts();
        for (entity.Product p : eProducts) {
            products.add(new Product(p.getId(), p.getName(), p.getPrice(), p.getCreatedAt()));
        }
        setProducts(new ArrayList<>(products));
        return this.products;
        
    }
    
    public List<Product> getProducts(int size) {
        products = new ArrayList<>();
        List<entity.Product> eProducts = bean.getProducts();
        for (entity.Product p : eProducts) {
            products.add(new Product(p.getId(), p.getName(), p.getPrice(), p.getCreatedAt()));
        }
        setProducts(new ArrayList<>(products));
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public boolean addProduct(Product product)
    {
        return bean.addProduct(product.getName(), product.getPrice());
    }
    
    public boolean updateProduct(Product product)
    {
        return bean.updateProduct(product.getId(), product.getName(), product.getPrice());
    }
    
    public boolean proudctExist(String product_name)
    {
        return bean.isProductExists(product_name);
    }
    
    public void removeProduct(int product_id)
    {
        System.out.println("product id: "+product_id);
        bean.removeProduct(product_id);
    }
    
    public List<Product> getClonedProducts(int size) {
        List<Product> results = new ArrayList<>();
        List<Product> originals = getProducts(size);
        for (Product original : originals) {
            results.add(original.clone());
        }
        return results;
    }
}
