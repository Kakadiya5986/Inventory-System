/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb;

import entity.Delivereditem;
import entity.Delivery;
import entity.Society;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author ritesh
 */
@Stateless
public class UserBean implements UserBeanLocal {

    @PersistenceContext(unitName = "drop_persistence_unit")
    private EntityManager em;

    @Inject
    Pbkdf2PasswordHash passwordHasher;

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
                    System.out.println("!!![AdminBean.updateCustomer]:\t socirty not found with given society_id");
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
    public List<Delivery> getDeliveries() {
        List<Delivery> result = em.createNamedQuery("Delivery.findAll").getResultList();
        return result;
    }

    @Override
    public List<Delivereditem> getDeliveredItems(int delivery_id) {
        Delivery delivery = em.find(Delivery.class, delivery_id);
        if (delivery != null) {
            List<Delivereditem> result = em.createNamedQuery("Delivery.findById").setParameter("Delivery", delivery).getResultList();
            return result;
        } else {
            return null;
        }
    }

    public String generateHashedPassword(String raw_password) {
        return passwordHasher.generate(raw_password.toCharArray());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
