/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb;

import entity.Delivereditem;
import entity.Delivery;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ritesh
 */
@Local
public interface UserBeanLocal {

    boolean updateCustomer(int customer_id, String customer_name, String house_no, String mobile_no, int society_id, String email, String password);

    //Delivery Operations
    public List<Delivery> getDeliveries();

    public List<Delivereditem> getDeliveredItems(int delivery_id);
}
