/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb;

import entity.Business;
import entity.Delivereditem;
import entity.Delivery;
import entity.Product;
import entity.Society;
import entity.Type;
import entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ritesh
 */
@Local
public interface AdminBeanLocal {

    //Business Operations
    public boolean addBusiness(String business_name, String address, User owner_id, int type_id);

    public boolean addBusinessWithOwner(String business_owner_name, String business_owner_email, String business_owner_password, String business_owner_mobile_no, String business_name, String address, int type_id);

    public boolean updateBusiness(int business_id, String address, String business_name);

    public Business getBusiness(int business_id);

    public Business getBusinessByUsername(String username);

    public boolean updateBusinessProfile(int business_owner_id, String business_name, String business_address, String business_owner_name, String business_owner_email, String business_owner_mobileno, String business_owner_password);

    public User addBusinessOwner(String business_owner_name, String business_owner_email, String business_owner_password, String business_owner_mobile_no);

    public User getBusinessOwner(int business_owner_id);

    //get User by UserId
    public User getUserByName(String name);

    //All Customer Operations
    public boolean addCustomer(String customer_name, String customer_house_no, String customer_mobile_no, int society_id, String customer_email, String customer_password);

    public boolean removeCustomer(int customer_id);

    public boolean updateCustomer(int customer_id, String customer_name, String house_no, String mobile_no, int society_id, String email, String password);

    public List<User> getCustomers();

    public User getCustomer(int customer_id);

    public List<User> getCustomersBySociety(int society_id);

    //All Society Operations
    public boolean addSociety(String society_name);

    public boolean removeSociety(int society_id);

    public boolean updateSociety(int society_id, String society_name);

    public List<Society> getSocieties();

    public Society getSociety(int society_id);

    //All Product Operations
    public boolean addProduct(String product_name, int product_price);

    public boolean removeProduct(int product_id);

    boolean updateProduct(int product_id, String product_name, int product_price);

    public List<Product> getProducts();

    public Product getProduct(int product_id);

    //All Employee Operations 
    public boolean addEmployee(String employee_name, String employee_mobile_no, String employee_email, String employee_password);

    public boolean removeEmployee(int employee_id);

    public boolean updateEmployee(int employee_id, String employee_name, String mobile_no, String email, String password);

    public List<User> getEmployees();

    public User getEmployee(int employee_id);

    //All Deliveries Operations
    public boolean addDelivery(int customer_id, List<Delivereditem> delivereryItems);

    public boolean removeDelivery(int delivery_id);

    public boolean updateDelivery(int delivery_id, List<Delivereditem> delivereryItems);

    public List<Delivery> getDeliveries();

    public List<Delivery> getDeliveriesByCustomerId(int customer_id);

    public Delivery getDelivery(int delivery_id);

    public List<Delivereditem> getDeliveredItems(int delivery_id);

    //All Type Operations
    public List<Type> getTypes();

    public boolean addType(String type_name);

    public String generateHashedPassword(String raw_password);

    //get business
    public Business getBusiness();

    //methods for validation
    public boolean isBusinessExists(String business_name);

    public boolean isUsernameExists(String username);

    public boolean isMobileNumberExists(String mobile_no);

    public boolean isEmailExists(String email);
    
    public boolean isProductExists(String product_name);
    
    public boolean isSocietyExists(String society_name);
    
    public boolean isDeliveryExists(int customer_id);
    
    public boolean isValidSociety(int society_id);
}
