/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb;

import entity.Business;
import entity.Delivereditem;
import entity.Delivery;
import entity.Product;
import entity.Roles;
import entity.Society;
import entity.Type;
import entity.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Stateless
public class AdminBean implements AdminBeanLocal {

    @PersistenceContext(unitName = "drop_persistence_unit")
    private EntityManager em;

    @Inject
    Pbkdf2PasswordHash passwordHasher;

    private static final String ROLE_CUSTOMER = "Customer", ROLE_EMPLOYEE = "Employee", ROLE_BUSINESS_OWNER = "Business Owner";

    @Override
    public boolean addBusiness(String business_name, String address, User owner, int type_id) {
        Business business = new Business();
        business.setBusinessName(business_name);
        business.setAddress(address);
//        User business_owner = em.find(User.class, owner_id);
        if (owner != null) {
            business.setOwnerId(owner);
            Type businessType = em.find(Type.class, 1);
            if (businessType != null) {
                business.setTypeId(businessType);
                business.setCreatedAt(Helper.getCurrentTimestamp());
                em.persist(business);
                em.flush();
                em.refresh(business);
                owner.setBusinessId(business);
                em.merge(owner);
                return true;
            } else {
                //businessType not found with given type_id
                System.out.println("!!![AdminBean.addBusiness]:\tbusinessType not found with given type_id");
                return false;
            }
        } else {
            //businessOwner not found with given owner_id
            System.out.println("!!![AdminBean.addBusiness]:\tbusinessOwner not found with given owner_id");
            return false;
        }
    }

    @Override
    @PermitAll
    public User addBusinessOwner(String business_owner_name, String business_owner_email, String business_owner_password, String business_owner_mobile_no) {
        User businessOwner = new User();
        businessOwner.setUsername(business_owner_name);
        businessOwner.setEmail(business_owner_email);
        businessOwner.setMobileNo(business_owner_mobile_no);
        businessOwner.setPassword(generateHashedPassword(business_owner_password));
        businessOwner.setCreatedAt(Helper.getCurrentTimestamp());
        em.persist(businessOwner);
        Roles role = new Roles();
        role.setUsername(business_owner_name);
        role.setGroupname(ROLE_BUSINESS_OWNER);
        em.persist(role);
        em.flush();
        em.refresh(businessOwner);
        return businessOwner;
    }

    @Override
    public User getBusinessOwner(int business_owner_id) {
//        User customer = em.find(User.class, customer_id);
        Business business = getBusiness();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username IN (SELECT r.username FROM Roles r WHERE r.groupname= :roleName) AND u.id= :business_owner_id AND u.businessId=:business_id");
        query.setParameter("roleName", ROLE_BUSINESS_OWNER);
        query.setParameter("business_owner_id", business_owner_id);
        query.setParameter("business_id", business);
        try {
            User business_owner = (User) query.getSingleResult();
            return business_owner;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public boolean updateBusiness(int business_id, String business_address, String business_name) {
        Business business = em.find(Business.class, business_id);
        if (business != null) {
            business.setAddress(business_address);
            business.setBusinessName(business_name);
            em.merge(business);
            return true;
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.updateBusiness]:\tbusiness not found with given business_id");
            return false;
        }
    }

    @Override
    public Business getBusiness(int business_id) {
        Business business = em.find(Business.class, business_id);
        if (business != null) {
            return business;
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.updateBusiness]:\tbusiness not found with given business_id");
            return null;
        }
    }

    @Override
    public boolean updateBusinessProfile(int business_owner_id, String business_name, String business_address, String business_owner_name, String business_owner_email, String business_owner_mobileno, String business_owner_password) {
        Business business = getBusiness();
        User businessOwner = getBusinessOwner(business_owner_id);
        Roles role = (Roles) em.createNamedQuery("Roles.findByUsername").setParameter("username", businessOwner.getUsername()).getSingleResult();
        System.err.println("business: " + business);
        System.err.println("business owner: " + businessOwner);
        if (business != null && businessOwner != null && role != null) {
            //update business details
            business.setBusinessName(business_name);
            business.setAddress(business_address);
            em.merge(business);
            //update business owner details
            businessOwner.setEmail(business_owner_email);
            businessOwner.setMobileNo(business_owner_mobileno);
            if (business_owner_password != null) {
                businessOwner.setPassword(generateHashedPassword(business_owner_password));
            }
            businessOwner.setUsername(business_owner_name);
            em.merge(businessOwner);
            role.setUsername(business_owner_name);
            em.merge(role);
            return true;
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.updateProfile]:\tbusiness not found!");
            return false;
        }
    }

    @Override
    public boolean addCustomer(String customer_name, String customer_house_no, String customer_mobile_no, int society_id, String customer_email, String customer_password) {
        User customer = new User();
        customer.setEmail(customer_email);
        customer.setHouseNo(customer_house_no);
        customer.setMobileNo(customer_mobile_no);
        customer.setUsername(customer_name);
        customer.setPassword(generateHashedPassword(customer_password));
        customer.setCreatedAt(Helper.getCurrentTimestamp());
        int business_id = KeepRecord.getBusinessId();
        Business business = em.find(Business.class, business_id);
        if (business != null) {
            customer.setBusinessId(business);
            Society society = em.find(Society.class, society_id);
            if (society != null) {
                customer.setSocietyId(society);
                em.persist(customer);
                Roles role = new Roles();
                role.setGroupname(ROLE_CUSTOMER);
                role.setUsername(customer_name);
                em.persist(role);
                System.out.println("customer added successfully.");
                return true;
            } else {
                //society not found with given business_id
                System.out.println("!!![AdminBean.addCustomer]:\tsociety not found with given society_id");
                return false;
            }
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.addCustomer]:\tbusiness not found with given business_id");
            return false;
        }
    }

    @Override
    public boolean removeCustomer(int customer_id) {
        User customer = em.find(User.class, customer_id);
        if (customer != null) {
            Query query = em.createQuery("DELETE from Roles r WHERE r.username=:customer_name");
            query.setParameter("customer_name", customer.getUsername());
            System.out.println("username: " + customer.getUsername());
            if (query.executeUpdate() == 1) {
                em.remove(customer);
                System.out.println("customer removed successfully.");
                return true;
            } else {
                System.out.println("!!![AdminBean.removeCustomer]:\t something went wrong while deleting username in roles table!");
                return false;
            }
        } else {
            System.out.println("!!![AdminBean.removeCustomer]:\t customer not found with given customer_id");
            return false;
        }
    }

    @Override
    public boolean updateCustomer(int customer_id, String customer_name, String customer_house_no, String customer_mobile_no, int society_id, String customer_email, String customer_password) {
        User customer = em.find(User.class, customer_id);
        if (customer != null) {
            Query query = em.createQuery("UPDATE Roles r SET r.username=:new_username WHERE r.username=:old_username");
            query.setParameter("old_username", customer.getUsername());
            query.setParameter("new_username", customer_name);
            if (query.executeUpdate() == 1) {
                customer.setUsername(customer_name);
                customer.setEmail(customer_email);
                if (customer_password != null) {
                    customer.setPassword(generateHashedPassword(customer_password));
                }
                customer.setHouseNo(customer_house_no);
                customer.setMobileNo(customer_mobile_no);
                Society society = em.find(Society.class, society_id);
                if (society != null) {
                    customer.setSocietyId(society);
                    em.merge(customer);
                    System.out.println("customer updated successfully.");
                    return true;
                } else {
                    System.out.println("!!![AdminBean.updateCustomer]:\t society not found with given society_id");
                    return false;
                }
            } else {
                System.out.println("!!![AdminBean.updateCustomer]:\t something went wrong while updating username in roles table!");
                return false;
            }
        } else {
            System.out.println("!!![AdminBean.updateCustomer]:\t customer not found with given customer_id");
            return false;
        }
    }

    @Override
    public List<User> getCustomers() {
        Business business = getBusiness();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username IN (SELECT r.username FROM Roles r WHERE r.groupname= :roleName) AND u.businessId=:business_id");
        query.setParameter("roleName", ROLE_CUSTOMER);
        query.setParameter("business_id", business);
        List<User> result = query.getResultList();
        return result;
    }

    @Override
    public User getCustomer(int customer_id) {
        System.out.println("get customer called with id: " + customer_id);
        User customer = em.find(User.class, customer_id);
        if (customer != null) {
            System.err.println("\n\n1.user not null");
            Business business = getBusiness();
            if (customer.getBusinessId() == business) {
                System.err.println("\n\n2.business matched");
                Roles role = (Roles) em.createNamedQuery("Roles.findByUsername").setParameter("username", customer.getUsername()).getSingleResult();
                if (role != null && role.getGroupname().equals(ROLE_CUSTOMER)) {
                    System.err.println("\n\n\n3. role matched finish!!!");
                    return customer;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        /* Query query = em.createQuery("SELECT u FROM User u WHERE u.username IN (SELECT r.username FROM Roles r WHERE r.groupname= :roleName) AND u.id= :customer_id AND u.businessId=:business_id");
        query.setParameter("roleName", ROLE_CUSTOMER);
        query.setParameter("customer_id", customer_id);
        query.setParameter("business_id", business);
        try {
            User customer = (User) query.getSingleResult();
            return customer;
        } catch (NoResultException ex) {
            return null;
        }*/
    }

    @Override
    public List<User> getCustomersBySociety(int society_id) {
        Society society = em.find(Society.class, society_id);
        Business business = getBusiness();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username IN (SELECT r.username FROM Roles r WHERE r.groupname= :roleName) AND u.societyId=:society_id AND u.businessId=:business_id");
        query.setParameter("roleName", ROLE_CUSTOMER);
        query.setParameter("business_id", business);
        query.setParameter("society_id", society);

        // Get the current date
        LocalDate today = LocalDate.now();

        List<User> customers = query.getResultList();
        customers.forEach(user -> {
            List<Delivery> deliveries = (List<Delivery>) user.getDeliveryCollection();
            List<Delivery> todaysDeliveries = deliveries.stream().filter(delivery -> delivery.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(today)).collect(Collectors.toList());
            if (!todaysDeliveries.isEmpty()) {
                user.setDeliveryCollection(todaysDeliveries);
            } else {
                user.setDeliveryCollection(null);
            }
        });
        return customers;
    }

    @Override
    public boolean addSociety(String society_name) {
        Society society = new Society();
        society.setName(society_name);
        society.setCreatedAt(Helper.getCurrentTimestamp());
        Business business = getBusiness();

        if (business != null) {
            society.setBusinessId(business);
            em.persist(society);
            System.out.println("society added successfully.");
            return true;
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.addSociety]:\tbusiness not found with given business_id");
            return false;
        }
    }

    @Override
    public boolean isSocietyExists(String society_name) {
        try {
            Business business = getBusiness();
            Society society = (Society) em.createNamedQuery("Society.findByName").setParameter("name", society_name).setParameter("business_id", business).getSingleResult();
            return society != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean removeSociety(int society_id) {
        Society society = em.find(Society.class, society_id);
        Business business = getBusiness();
        if (society != null && society.getBusinessId() == business) {
            em.remove(society);
            return true;
        } else {
            System.out.println("!!![AdminBean.removeSociety]:\t society not found with given society_id");
            return false;
        }
    }

    @Override
    public boolean updateSociety(int society_id, String society_name) {
        Society society = em.find(Society.class, society_id);
        Business business = getBusiness();
        if (society != null && society.getBusinessId() == business) {
            society.setName(society_name);
            em.merge(society);
            System.out.println("society updated successfully.");
            return true;
        } else {
            System.out.println("!!![AdminBean.updateSociety]:\t society not found with given society_id");
            return false;
        }
    }

    @Override
    public List<Society> getSocieties() {
        Business business = getBusiness();
        if (business != null) {
            List<Society> result = em.createNamedQuery("Society.findAll").setParameter("business_id", business).getResultList();
            return result;
        }
        return null;
    }

    @Override
    public Society getSociety(int society_id) {
        Society society = em.find(Society.class, society_id);
        Business business = getBusiness();
        if (society != null && society.getBusinessId() == business) {
            return society;
        } else {
            System.out.println("!!![AdminBean.getSociety]:\t society not found with given society_id");
            return null;
        }
    }

    @Override
    public boolean isProductExists(String product_name) {
        try {
            Business business = getBusiness();
            Product product = (Product) em.createNamedQuery("Product.findByName").setParameter("name", product_name).setParameter("business_id", business).getSingleResult();
            return product != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean addProduct(String product_name, int product_price) {
        Product product = new Product();
        product.setName(product_name);
        product.setPrice(product_price);
        product.setCreatedAt(Helper.getCurrentTimestamp());
        Business business = getBusiness();
        if (business != null) {
            product.setBusinessId(business);
            em.persist(product);
            System.out.println("product added successfully.");
            return true;
        } else {
            //business not found with given business_id
            System.out.println("!!![AdminBean.addProduct]:\tbusiness not found with given business_id");
            return false;
        }
    }

    @Override
    public boolean removeProduct(int product_id) {
        System.out.println("\n\n\nproduct id inside business logic: " + product_id + "\n\n\n");
        Product product = em.find(Product.class, product_id);
        Business business = getBusiness();

        if (product != null && product.getBusinessId() == business) {
            em.remove(product);
            return true;
        } else {
            System.out.println("!!![AdminBean.removeProduct]:\t product not found with given product_id");
            return false;
        }
    }

    @Override
    public boolean updateProduct(int product_id, String product_name, int product_price) {
        Product product = em.find(Product.class, product_id);
        Business business = getBusiness();
        if (product != null && product.getBusinessId() == business) {
            product.setName(product_name);
            product.setPrice(product_price);
            em.merge(product);
            System.out.println("product updated successfully.");
            return true;
        } else {
            System.out.println("!!![AdminBean.updateProduct]:\t product not found with given product_id");
            return false;
        }
    }

    @Override
    public List<Product> getProducts() {
        Business business = getBusiness();
        List<Product> result = em.createNamedQuery("Product.findAll").setParameter("business_id", business).getResultList();
        System.err.println("business id: " + business.getId());
        System.err.println("business name: " + business.getBusinessName());
        return result;
    }

    @Override
    public Product getProduct(int product_id) {
        Product product = em.find(Product.class, product_id);
        Business business = getBusiness();
        if (product != null && product.getBusinessId() == business) {
            return product;
        } else {
            System.out.println("!!![AdminBean.getProduct]:\t product not found with given society_id");
            return null;
        }
    }

    @Override
    public boolean addEmployee(String employee_name, String employee_mobile_no, String employee_email, String employee_password) {
        User employee = new User();
        employee.setUsername(employee_name);
        employee.setEmail(employee_email);
        employee.setMobileNo(employee_mobile_no);
        employee.setPassword(generateHashedPassword(employee_password));
        employee.setCreatedAt(Helper.getCurrentTimestamp());
        Business business = getBusiness();
        if (business != null) {
            employee.setBusinessId(business);
            em.persist(employee);
            Roles role = new Roles();
            role.setUsername(employee_name);
            role.setGroupname(ROLE_EMPLOYEE);
            em.persist(role);
            System.out.println("employee added successfully.");
            return true;
        } else {
            System.out.println("!!![AdminBean.addEmployee]:\t business not found with given business_id");
            return false;
        }

    }

    @Override
    public boolean removeEmployee(int employee_id) {
        System.err.println("removeEmployee()->employee id: " + employee_id);
        User employee = em.find(User.class, employee_id);
        Business business = getBusiness();
        if (employee != null && employee.getBusinessId() == business) {
            Query query = em.createQuery("DELETE from Roles r WHERE r.username=:customer_name");
            query.setParameter("customer_name", employee.getUsername());
            if (query.executeUpdate() == 1) {
                em.remove(employee);
                System.out.println("employee removed successfully.");
                return true;
            } else {
                System.out.println("!!![AdminBean.removeEmployee]::\t something went wrong while deleting username in roles table!");
                return false;
            }
        } else {
            System.out.println("!!![AdminBean.removeEmployee]:\t customer not found with given customer_id");
            return false;
        }
    }

    @Override
    public boolean updateEmployee(int employee_id, String employee_name, String mobile_no, String employee_email, String employee_password) {
        User employee = em.find(User.class, employee_id);
        Business business = getBusiness();
        if (employee != null && employee.getBusinessId() == business) {
            Roles role = (Roles) em.createNamedQuery("Roles.findByUsername").setParameter("username", employee.getUsername()).getSingleResult();
            if (role != null) {
                role.setUsername(employee_name);
                em.merge(role);
                employee.setUsername(employee_name);
                employee.setEmail(employee_email);
                if (employee_password != null) {
                    employee.setPassword(generateHashedPassword(employee_password));
                }
                employee.setMobileNo(mobile_no);
                em.merge(employee);
                System.out.println("employee updated successfully.");
                return true;
            } else {
                System.out.println("!!![AdminBean.updateCustomer]:\t something went wrong while updating username in roles table!");
                return false;
            }
        } else {
            System.out.println("!!![AdminBean.updateCustomer]:\t employee not found with given employee_id");
            return false;
        }
    }

    @Override
    public List<User> getEmployees() {
        Business business=getBusiness();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username IN (SELECT r.username FROM Roles r WHERE r.groupname= :roleName) AND u.businessId= :business");
        query.setParameter("roleName", ROLE_EMPLOYEE);
        query.setParameter("business",business);
        List<User> result = query.getResultList();
        System.out.println("result contains " + result.size() + " records.");
        return result;
    }

    @Override
    public User getEmployee(int employee_id) {
        User employee = em.find(User.class, employee_id);
        if (employee != null) {
            Business business = getBusiness();
            if (employee.getBusinessId() == business) {
                return employee;
            } else {
                System.out.println("[ejb.AdminBean.getEmployee], employee not found with given business_id!");
                return null;
            }
        } else {
            System.out.println("[ejb.AdminBean.getEmployee], employee not found with given employee_id!");
            return null;
        }
    }

    @Override
    public boolean addDelivery(int customer_id, List<Delivereditem> delivereryItems) {
        System.err.println("\n\n\n total delivery items: " + delivereryItems.size());
        Delivery delivery = new Delivery();
        Business business = getBusiness();
        if (business != null) {
            delivery.setBusinessId(business);
            User customer = em.find(User.class, customer_id);
            if (customer != null) {
                Roles role = (Roles) em.createNamedQuery("Roles.findByUsername").setParameter("username", customer.getUsername()).getResultList().get(0);
                if (role.getGroupname().equals(ROLE_CUSTOMER)) {
                    delivery.setCustomerId(customer);
                    delivery.setCreatedAt(Helper.getCurrentTimestamp());
                    for (Delivereditem item : delivereryItems) {
                        item.setBusinessId(business);
                        item.setDeliveryId(delivery);
                        item.setCreatedAt(Helper.getCurrentTimestamp());
                        em.persist(item);
                    }
                    delivery.setDelivereditemCollection(delivereryItems);
                    Collection<Delivery> customerDeliveries = customer.getDeliveryCollection();
                    customerDeliveries.add(delivery);
                    customer.setDeliveryCollection(customerDeliveries);
                    em.persist(customer);
                    em.persist(delivery);
                    em.flush();
                    return true;
                } else {
                    System.out.println("!!![AdminBean.addDelivery]:\t invalid customer!");
                    return false;
                }
            } else {
                System.out.println("!!![AdminBean.addDelivery]:\t customer not found with given customer_id");
                return false;
            }
        } else {
            System.out.println("!!![AdminBean.addDelivery]:\t business not found with given business_id");
            return false;
        }
    }

    @Override
    public boolean updateDelivery(int delivery_id, List<Delivereditem> delivereryItems) {
        Delivery delivery = em.find(Delivery.class, delivery_id);
        Business business = getBusiness();
        if (delivery != null && delivery.getBusinessId() == business) {
            //get old delivered items and remove each and every items
            List<Delivereditem> deliveredItems = (List<Delivereditem>) delivery.getDelivereditemCollection();
            for (Delivereditem item : deliveredItems) {
                em.remove(item);
            }
            //now add given delivered items
            for (Delivereditem item : delivereryItems) {
                item.setBusinessId(business);
                item.setDeliveryId(delivery);
                item.setCreatedAt(Helper.getCurrentTimestamp());
                em.persist(item);
            }
            delivery.setDelivereditemCollection(delivereryItems);
            em.persist(delivery);
            em.flush();
            return true;
        } else {
            System.out.println("!!![AdminBean.addDelivery]:\t delivery not found with given delivery_id");
            return false;
        }
    }

    @Override
    public boolean removeDelivery(int delivery_id) {
        Delivery delivery = em.find(Delivery.class, delivery_id);
        Business business = getBusiness();
        User customer = delivery.getCustomerId();
        if (delivery != null && delivery.getBusinessId() == business) {
            em.remove(delivery);
            Collection<Delivery> customerDeliveries = customer.getDeliveryCollection();
            customerDeliveries.remove(delivery);
            em.persist(customer);
            return true;
        } else {
            System.out.println("!!![AdminBean.removeDelivery]:\t delivery not found with given delivery_id");
            return false;
        }
    }

    @Override
    public List<Delivery> getDeliveries() {
        Business business = getBusiness();
        List<Delivery> result = em.createNamedQuery("Delivery.findAll").setParameter("business_id", business).getResultList();
        return result;
    }

    @Override
    public List<Delivery> getDeliveriesByCustomerId(int customer_id) {
        User customer = em.find(User.class, customer_id);
        List<Delivery> result = new ArrayList<>();
        if (customer != null) {
            result = em.createNamedQuery("Delivery.findByCustomerId").setParameter("customer_id", em.find(User.class, customer_id)).getResultList();
        } else {
            System.out.println("!!![AdminBean.removeDelivery]:\t customer not found with given customer_id");
        }
        return result;
    }

    @Override
    public Delivery getDelivery(int delivery_id) {
        Business business = getBusiness();
        Delivery delivery = em.find(Delivery.class, delivery_id);
        if (delivery != null && delivery.getBusinessId() == business) {
            return delivery;
        } else {
            //Delivery not found with given delivery_id!!
            System.out.println("!!![AdminBean.getDelivery]:\t delivery not found with given delivery_id");
            return null;
        }
    }

    @Override
    public List<Delivereditem> getDeliveredItems(int delivery_id) {
        Delivery delivery = em.find(Delivery.class, delivery_id);
        Business business = getBusiness();
        List<Delivereditem> result = new ArrayList<Delivereditem>();
        if (delivery != null && delivery.getBusinessId() == business) {
            result = (List<Delivereditem>) delivery.getDelivereditemCollection();
        } else {
            System.out.println("!!![AdminBean.getDeliveredItems]:\t delivery not found with given delivery_id");
        }
        return result;
    }

    @Override
    public List<Type> getTypes() {
        List<Type> types = em.createNamedQuery("Type.findAll").getResultList();
        return types;
    }

    @Override
    public boolean addType(String type_name) {
        Type type = new Type();

        type.setName(type_name);
        type.setCreatedAt(Helper.getCurrentTimestamp());
        em.persist(type);
        return true;
    }

    public String generateHashedPassword(String raw_password) {
        return passwordHasher.generate(raw_password.toCharArray());
    }

    @Override
    public Business getBusinessByUsername(String username) {
        try {
            User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
            return user.getBusinessId();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUserByName(String name) {
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", name).getSingleResult();
        return user;
    }

    @Override
    public boolean addBusinessWithOwner(String business_owner_name, String business_owner_email, String business_owner_password, String business_owner_mobile_no, String business_name, String address, int type_id) {
        User owner = addBusinessOwner(business_owner_name, business_owner_email, business_owner_password, business_owner_mobile_no);
        if (owner != null) {
            return addBusiness(business_name, address, owner, type_id);
        } else {
            return false;
        }
    }

    @Override
    public Business getBusiness() {
        int business_id = KeepRecord.getBusinessId();
        if (business_id != 0) {
            Business business = em.find(Business.class, business_id);
            return business;
        } else {
            return null;
        }
    }

    @Override
    public boolean isBusinessExists(String business_name) {
        try {
            Business business = (Business) em.createNamedQuery("Business.findByBusinessName").setParameter("businessName", business_name).getSingleResult();
            return business != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isMobileNumberExists(String mobile_no) {
        try {
            User user = (User) em.createNamedQuery("User.findByMobileNo").setParameter("mobileNo", mobile_no).getSingleResult();
            return user != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try {
            User user = (User) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
            return user != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        try {
            User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
            return user != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isDeliveryExists(int customer_id) {
        User customer = getCustomer(customer_id);
        Business business = getBusiness();
        LocalDate today = LocalDate.now();
        Query query = em.createQuery("SELECT COUNT(d) FROM Delivery d WHERE d.businessId=:business_id AND d.customerId=:customer_id and FUNCTION('DATE',d.createdAt)=:today");
        query.setParameter("customer_id", customer);
        query.setParameter("business_id", business);
        query.setParameter("today", today);
        try {
            Long deliveryCount = (Long) query.getSingleResult();
            return deliveryCount > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isValidSociety(int society_id) {
        Society society = em.find(Society.class, society_id);
        Business business = getBusiness();
        if (society != null && society.getBusinessId() == business) {
            return true;
        } else {
            return false;
        }
    }
}
