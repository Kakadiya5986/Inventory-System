/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import ejb.AdminBean;
import ejb.AdminBeanLocal;
import entity.Delivereditem;
import entity.Delivery;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ritesh
 */
//@DeclareRoles({"Business Owner","Employee","Customer"})
//@ServletSecurity(@HttpConstraint(rolesAllowed = {"Business Onwer","Employee"}))
@WebServlet(name = "testServlet", urlPatterns = {"/test"})
public class testServlet extends HttpServlet {

    @EJB
    AdminBeanLocal adminBean;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet testServlet at " + adminBean.getTypes() + "</h1>");
//            adminBean.addType("Cold Coco");
            out.println("<h1>Servlet testServlet at " + adminBean.getTypes() + "</h1>");
//----------------Business CRUD-----------------
//           adminBean.addBusinessOwner("Tanay", "tanay09@email.com", "tanay@123", "63895895689");
//            adminBean.addBusiness("RS Solutions", "Himachal Pradesh", 2, 2);
//adminBean.updateBusiness(1, "Surat","RS Solutions");
//adminBean.addSociety("Sky View Heights", 1);
//---------------------Customer CRUD----------------------
//adminBean.addCustomer("TEST Yadav", "898","95689887844", 1, 1, "testung@email.com", "test@123");
//            adminBean.removeCustomer(5);
//            adminBean.updateCustomer(7, "TEST Yadav UPDATED", "P-599", "64757954798", 1, "testung@email.com", "test@123");
//            adminBean.getCustomers();

//-----------------------Employee CRUD---------------------
//            adminBean.addEmployee("jadu jadhav", "95647856450", "jadujadhav@email.com", "emp1@123", 1);
//adminBean.removeEmployee(8);
//            List<User> employees = adminBean.getEmployees();
//            for (User item : employees) {
//                out.println("<div><p>" + item.getUsername() + "</p><p>" + item.getEmail() + "</p><p>" + item.getBusinessCollection() + "</p><p>" + item.getBusinessId().getBusinessName() + "</p></div>");
//            }
//            adminBean.updateEmployee(9, "Jaydev Jadhav", "88565948784", "jaydevjadhav34@hotmail.com", "jaydev@123");
//            out.println("<p style='color:green'>Employee Updated Successfully.</p>");
//           employees = adminBean.getEmployees();
//            for (User item : employees) {
//                out.println("<div><p>" + item.getUsername() + "</p><p>" + item.getEmail() + "</p><p>" + item.getBusinessCollection() + "</p><p>" + item.getBusinessId().getBusinessName() + "</p></div>");
//            }
//            User item = adminBean.getEmployee(9);
//            out.println("Employee with id 9: ");
//            out.println("<div><p>" + item.getUsername() + "</p><p>" + item.getEmail() + "</p><p>" + item.getBusinessCollection() + "</p><p>" + item.getBusinessId().getBusinessName() + "</p></div>");
//            Product product2=adminBean.getProduct(2);
//            adminBean.addDelivery(1, 7, orderItems);
//-----------------Product CRUD--------------------------------
//adminBean.addProduct("Nakli Milk", 90, 1);
//adminBean.getProduct(1);
//adminBean.removeProduct(3);
//adminBean.updateProduct(2, "Buffalo MILK", 150);
//-------------------Add delivery------------------------------
/*
            List<Delivereditem> orderItems = new ArrayList<Delivereditem>();
            Delivereditem item2 = new Delivereditem();
            item2.setName("butter");
            item2.setPrice(450);
            Product product2 = adminBean.getProduct(11);
            item2.setProductId(product2);
            item2.setQuantity(4);
            orderItems.add(item2);
            Delivereditem item = new Delivereditem();
            item.setName("Buffelo Milk");
            item.setPrice(85);
            Product product1 = adminBean.getProduct(6);
            item.setProductId(product1);
            item.setQuantity(2);
            orderItems.add(item);

            System.out.println("\n\norderItems: " + orderItems.size());

            if (!adminBean.isDeliveryExists(14)) {
                System.out.println("delivery not exist for today.");
                adminBean.addDelivery(14, orderItems);
            } else {
                System.out.println("delivery already exist for today.");
            }
            
             */
//-------------------Update delivery------------------------------
            List<Delivereditem> orderItems = new ArrayList<Delivereditem>();
            Delivereditem item2 = new Delivereditem();
            item2.setName("butter");
            item2.setPrice(450);
            Product product2 = adminBean.getProduct(11);
            item2.setProductId(product2);
            item2.setQuantity(4);
            orderItems.add(item2);
            Delivereditem item = new Delivereditem();
            item.setName("Buffelo Milk");
            item.setPrice(85);
            Product product1 = adminBean.getProduct(6);
            item.setProductId(product1);
            item.setQuantity(2);
            if (adminBean.updateDelivery(18, orderItems)) {
                System.out.println("delivery updated successfully.");
            } else {
                System.out.println("delivery not updated successfully.");
            }
//            orderItems.add(item);

//---------------Remove Delivery------------------------
//adminBean.removeDelivery(8);
//----------------------------Get Delivery By Customer Id------------------------
            /*List<Delivery> deliveries = adminBean.getDeliveriesByCustomerId(9);
            for (Delivery d : deliveries) {
                out.println("customer Name:" + d.getCustomerId().getUsername());
                out.println("delivery Id:" + d.getId());
                out.println("Total Items Ordered:" + d.getDelivereditemCollection().size());
                out.println("<hr/>");
            }*/
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
