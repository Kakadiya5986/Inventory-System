package com.mycompany.dropapplication.resources;

import JSONClasses.Business;
import JSONClasses.BusinessDetails;
import JSONClasses.BusinessOwner;
import JSONClasses.Customer;
import JSONClasses.DeliveredItem;
import JSONClasses.ErrorInfo;
import JSONClasses.ErrorResponse;
import JSONClasses.SuccessResponse;
import JSONClasses.SuccessResponseSingle;
import ejb.AdminBeanLocal;
import entity.Delivereditem;
import entity.Delivery;
import entity.Product;
import entity.Society;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.UnexpectedTypeException;
import javax.validation.constraints.Email;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author
 */
@Path("v1")
@DeclareRoles({"Business Owner", "Employee", "Customer"})
public class JakartaEE8Resource {

    @EJB
    private AdminBeanLocal adminBean;

    @GET
    @Consumes("application/json")
    public Response ping() {
        JsonObject json = Json.createObjectBuilder().add("message", "drop api running...").build();
        return Response
                .ok(json, MediaType.APPLICATION_JSON)
                .build();
    }

    //Business Operations
    //create business owner
    @RolesAllowed({"Business Owner"})
    @POST
    @Path("businessOwner")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addBusinessOwner(BusinessOwner user) {
        StringBuilder errorMessages = new StringBuilder();
        try {
            JsonObject json = null;
            if (adminBean.addBusinessOwner(user.getBusiness_owner_name(), user.getBusiness_owner_email(), user.getBusiness_owner_password(), user.getBusiness_owner_mobileno()) != null) {
                SuccessResponse<String> response;
                response = new SuccessResponse<>(200, null, "business owner created successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                ErrorInfo errorInfo = new ErrorInfo("business owner not created successfully.", "BUSINESS_OWNER_NOT_CREATED");
                ErrorResponse error = new ErrorResponse(500, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.addBusinessOwner()");
            ErrorInfo errorInfo = new ErrorInfo("error while creating business owner.", "ERROR_WHILE_CREATING_BUSINESS_OWNER");
            ErrorResponse error = new ErrorResponse(501, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //Business Operations  
    //get business by id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("business/{id}")
    @Produces("application/json")
    public Response getBusiness(@PathParam("id") int business_id) {
        entity.Business business = adminBean.getBusiness(business_id);
        JsonObject json = null;
        if (business != null) {
            SuccessResponseSingle<entity.Business> response;
            response = new SuccessResponseSingle<>(200, business, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("business not found", "BUSINESS_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get business by username
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("businessByUsername/{name}")
    @Produces("application/json")
    public Response getBusinessByUsername(@PathParam("name") String username) {
        System.out.println("\n\n\ncom.mycompany.dropapplication.resources.JakartaEE8Resource.getBusinessByUsername()");
        entity.Business business = adminBean.getBusinessByUsername(username);
        JsonObject json = null;
        if (business != null) {
            SuccessResponseSingle<entity.Business> response;
            response = new SuccessResponseSingle<>(200, business, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("business not found", "BUSINESS_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create business
    @POST
    @Path("business")
    @Consumes("application/json")
    @Produces("application/json")
    @PermitAll
    public Response addBusiness(BusinessDetails business) {
        try {
            if (adminBean.isUsernameExists(business.getBusiness_owner_name())) {
                ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.isBusinessExists(business.getBusiness_name())) {
                ErrorInfo errorInfo = new ErrorInfo("business name already exists", "BUSINESS_NAME_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.isEmailExists(business.getBusiness_owner_email())) {
                ErrorInfo errorInfo = new ErrorInfo("email address already in use.", "EMAIL_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.isMobileNumberExists(business.getBusiness_owner_mobileno())) {
                ErrorInfo errorInfo = new ErrorInfo("mobile number already in use.", "MOBILE_NUMBER_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.addBusinessWithOwner(business.getBusiness_owner_name(), business.getBusiness_owner_email(), business.getBusiness_owner_password(), business.getBusiness_owner_mobileno(), business.getBusiness_name(), business.getBusiness_address(), business.getType_id())) {
                SuccessResponseSingle<entity.Business> response;
                response = new SuccessResponseSingle<>(200, null, "business created successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                System.err.println("business addition failed");
                ErrorInfo errorInfo = new ErrorInfo("business not created successfully.", "BUSINESS_NOT_CREATED");
                ErrorResponse error = new ErrorResponse(500, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.addBusiness()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while creating business.", "ERROR_WHILE_CREATING_BUSINESS");
            ErrorResponse error = new ErrorResponse(501, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update business
    @RolesAllowed({"Business Owner"})
    @PUT
    @Path("business")
    @Consumes("application/json")
    public Response editBusiness(BusinessDetails business) {
        try {
            entity.Business business1 = adminBean.getBusiness();
            User businessOwner = adminBean.getBusinessOwner(business1.getOwnerId().getId());
            if (!businessOwner.getUsername().equals(business.getBusiness_owner_name()) && adminBean.isUsernameExists(business.getBusiness_owner_name())) {
                ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!business1.getBusinessName().equals(business.getBusiness_name()) && adminBean.isBusinessExists(business.getBusiness_name())) {
                ErrorInfo errorInfo = new ErrorInfo("business name already exists", "BUSINESS_NAME_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!businessOwner.getEmail().equals(business.getBusiness_owner_email()) && adminBean.isEmailExists(business.getBusiness_owner_email())) {
                ErrorInfo errorInfo = new ErrorInfo("email address already in use.", "EMAIL_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!businessOwner.getMobileNo().equals(business.getBusiness_owner_mobileno()) && adminBean.isMobileNumberExists(business.getBusiness_owner_mobileno())) {
                ErrorInfo errorInfo = new ErrorInfo("mobile number already in use.", "MOBILE_NUMBER_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.updateBusinessProfile(business.getBusiness_owner_id(), business.getBusiness_name(), business.getBusiness_address(), business.getBusiness_owner_name(), business.getBusiness_owner_email(), business.getBusiness_owner_mobileno(), business.getBusiness_owner_password()) == true) {
                SuccessResponseSingle<entity.Business> response;
                response = new SuccessResponseSingle<>(200, null, "business updated successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                ErrorInfo errorInfo = new ErrorInfo("business not updated successfully", "BUSINESS_NOT_UPDATED");
                ErrorResponse error = new ErrorResponse(500, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.editBusiness()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while updating business", "ERROR_UPDATING_BUSINESS");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //Product Operations
    //get all products
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("product")
    @Produces("application/json")
    public Response getProducts() {
        JsonObject json = null;
        Vector<Product> products = (Vector<Product>) adminBean.getProducts();
        if (products.size() > 0) {
            SuccessResponse<Product> response;
            response = new SuccessResponse<>(200, products, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("products not found", "PRODUCTS_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get product by id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("product/{id}")
    @Produces("application/json")
    public Response getProduct(@PathParam("id") int product_id) {
        Product product = adminBean.getProduct(product_id);
        JsonObject json = null;
        if (product != null) {
            SuccessResponseSingle<Product> response;
            response = new SuccessResponseSingle<>(200, product, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("product not found", "PRODUCT_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create product
    @RolesAllowed({"Business Owner", "Employee"})
    @POST
    @Path("product")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addProduct(JSONClasses.Product product) {
        StringBuilder errorMessages = new StringBuilder();
        try {
            JsonObject json = null;
            if (!adminBean.isProductExists(product.getName())) {
                if (adminBean.addProduct(product.getName(), product.getPrice()) == true) {
                    SuccessResponse<String> response;
                    response = new SuccessResponse<>(200, null, "product created successfully", null);
                    return Response.status(response.getStatus()).entity(response).build();
                } else {
                    ErrorInfo errorInfo = new ErrorInfo("product not created  successfully", "PRODUCT_NOT_CREATED");
                    ErrorResponse error = new ErrorResponse(500, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                }
            } else {
                ErrorInfo errorInfo = new ErrorInfo("product already exists", "PRODUCT_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.addProduct()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while creating product", "ERROR_WHILE_CREATING_PRODUCT");
            ErrorResponse error = new ErrorResponse(501, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update product
    @RolesAllowed({"Business Owner", "Employee"})
    @PUT
    @Path("product")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateProduct(JSONClasses.Product product) {
        try {
            Product product1 = adminBean.getProduct(product.getId());
            if (product1 == null) {
                ErrorInfo errorInfo = new ErrorInfo("product not found", "PRODUCT_NOT_FOUND");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else {
                System.out.println("!product1.getName().equals(product.getName()): " + !product1.getName().equals(product.getName()));
                System.out.println("!product1.getName().equals(product.getName()): " + !adminBean.isProductExists(product.getName()));
                if (!product1.getName().equals(product.getName()) && adminBean.isProductExists(product.getName())) {
                    System.err.println("product name already exist");
                    ErrorInfo errorInfo = new ErrorInfo("product already exists", "PRODUCT_ALREADY_EXISTS");
                    ErrorResponse error = new ErrorResponse(409, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                } else {
                    if (adminBean.updateProduct(product.getId(), product.getName(), product.getPrice()) == true) {
                        SuccessResponse<String> response;
                        response = new SuccessResponse<>(200, null, "product updated successfully", null);
                        return Response.status(response.getStatus()).entity(response).build();
                    } else {
                        ErrorInfo errorInfo = new ErrorInfo("product not found", "PRODUCT_NOT_FOUND");
                        ErrorResponse error = new ErrorResponse(404, errorInfo);
                        return Response.status(error.getStatus()).entity(error).build();
                    }
                }
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.updateProduct()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while updating product", "ERROR_WHILE_UPDATING_PRODUCT");
            ErrorResponse error = new ErrorResponse(501, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //delete product
    @RolesAllowed({"Business Owner", "Employee"})
    @DELETE
    @Path("product/{id}")
    @Produces("application/json")
    public Response removeProduct(@PathParam("id") int product_id) {
        Product product = adminBean.getProduct(product_id);
        if (product != null) {
            adminBean.removeProduct(product_id);
            SuccessResponse<String> response;
            response = new SuccessResponse<>(200, null, "product removed successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("product not found", "PRODUCT_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //Society Operations
    //get all societies
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("society")
    @Produces("application/json")
    public Response getSocieties() {
        JsonObject json = null;
        Vector<Society> societies = (Vector<Society>) adminBean.getSocieties();
        if (societies.size() > 0) {
            SuccessResponse<Society> response;
            response = new SuccessResponse<>(200, societies, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("societies not found", "SOCIETIES_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get society by id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("society/{id}")
    @Produces("application/json")
    public Response getSociety(@PathParam("id") int society_id) {
        Society society = adminBean.getSociety(society_id);
        JsonObject json = null;
        if (society != null) {
            SuccessResponseSingle<Society> response;
            response = new SuccessResponseSingle<>(200, society, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("society not found", "SOCIETY_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create society
    @RolesAllowed({"Business Owner", "Employee"})
    @POST
    @Path("society")
    @Produces("application/json")
    public Response addSociety(JSONClasses.Society society) {
        JsonObject json = null;
        if (!adminBean.isSocietyExists(society.getName())) {
            if (adminBean.addSociety(society.getName())) {
                SuccessResponse<String> response;
                response = new SuccessResponse<>(200, null, "society created successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                ErrorInfo errorInfo = new ErrorInfo("society not found", "SOCIETY_NOT_FOUND");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("society already exists", "SOCIETY_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update society
    @RolesAllowed(value = {"Business Owner", "Employee"})
    @PUT
    @Path("society")
    @Consumes(value = "application/json")
    @Produces(value = "application/json")
    public Response updateSociety(JSONClasses.Society society) {
        StringBuilder errorMessages = new StringBuilder();
        try {
            if (!adminBean.isSocietyExists(society.getName())) {
                Society soc = adminBean.getSociety(society.getId());
                if (soc != null) {
                    if (adminBean.updateSociety(society.getId(), society.getName()) == true) {
                        SuccessResponseSingle<Society> response;
                        response = new SuccessResponseSingle<>(200, null, "society updated successfully", null);
                        return Response.status(response.getStatus()).entity(response).build();
                    } else {
                        ErrorInfo errorInfo = new ErrorInfo("society not found", "SOCIETY_NOT_FOUND");
                        ErrorResponse error = new ErrorResponse(500, errorInfo);
                        return Response.status(error.getStatus()).entity(error).build();
                    }
                } else {
                    ErrorInfo errorInfo = new ErrorInfo("society not found", "SOCIETY_NOT_FOUND");
                    ErrorResponse error = new ErrorResponse(404, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                }
            } else {
                ErrorInfo errorInfo = new ErrorInfo("society already exists", "SOCIETY_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.updateSociety()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while updating society", "ERROR_UPDATING_SOCIETY");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //remove society
    @RolesAllowed({"Business Owner", "Employee"})
    @DELETE
    @Path("society/{id}")
    @Produces("application/json")
    public Response removeSociety(@PathParam("id") int society_id) {
        Society society = adminBean.getSociety(society_id);
        if (society != null) {
            adminBean.removeSociety(society_id);
            SuccessResponse<String> response;
            response = new SuccessResponse<>(200, null, "society removed successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("society not found", "SOCIETY_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //Employee Operations
    //get all employees
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("employee")
    @Produces("application/json")
    public Response getEmployees() {
        JsonObject json = null;
        Vector<User> employees = (Vector<User>) adminBean.getEmployees();
        if (employees.size() > 0) {
            SuccessResponse<User> response;
            response = new SuccessResponse<>(200, employees, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("employees not found", "EMPLOYEES_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get employee by id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("employee/{id}")
    @Produces("application/json")
    public Response getEmployee(@PathParam("id") int employee_id) {
        User employee = adminBean.getEmployee(employee_id);
        if (employee != null) {
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, employee, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("employee not found", "EMPLOYEE_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create employee
    @RolesAllowed({"Business Owner", "Employee"})
    @POST
    @Path("employee")
    @Produces("application/json")
    public Response addEmployee(JSONClasses.Employee employee) {
        if (adminBean.isUsernameExists(employee.getEmployee_name())) {
            ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.isEmailExists(employee.getEmployee_email())) {
            ErrorInfo errorInfo = new ErrorInfo("email address already in use.", "EMAIL_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.isMobileNumberExists(employee.getEmployee_mobile_no())) {
            ErrorInfo errorInfo = new ErrorInfo("mobile number already in use.", "MOBILE_NUMBER_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.addEmployee(employee.getEmployee_name(), employee.getEmployee_mobile_no(), employee.getEmployee_email(), employee.getEmployee_password())) {
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, null, "employee created successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("error occured while creating employee", "ERROR_CREATING_EMPLOYEE");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update employee
    @RolesAllowed({"Business Owner", "Employee"})
    @PUT
    @Path("employee")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateEmployee(JSONClasses.Employee employee) {
        try {
            User emp = adminBean.getEmployee(employee.getEmployee_id());
            if (emp != null) {
                if (!emp.getUsername().equals(employee.getEmployee_name()) && adminBean.isUsernameExists(employee.getEmployee_name())) {
                    ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
                    ErrorResponse error = new ErrorResponse(409, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                } else if (!emp.getEmail().equals(employee.getEmployee_email()) && adminBean.isEmailExists(employee.getEmployee_email())) {
                    ErrorInfo errorInfo = new ErrorInfo("email address already in use.", "EMAIL_ALREADY_EXIST");
                    ErrorResponse error = new ErrorResponse(409, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                } else if (!emp.getMobileNo().equals(employee.getEmployee_mobile_no()) && adminBean.isMobileNumberExists(employee.getEmployee_mobile_no())) {
                    ErrorInfo errorInfo = new ErrorInfo("mobile number already in use.", "MOBILE_NUMBER_ALREADY_EXIST");
                    ErrorResponse error = new ErrorResponse(409, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                } else if (adminBean.updateEmployee(employee.getEmployee_id(), employee.getEmployee_name(), employee.getEmployee_mobile_no(), employee.getEmployee_email(), employee.getEmployee_password())) {
                    SuccessResponseSingle<User> response;
                    response = new SuccessResponseSingle<>(200, null, "employee updated successfully", null);
                    return Response.status(response.getStatus()).entity(response).build();
                } else {
                    ErrorInfo errorInfo = new ErrorInfo("error while updating employee", "ERROR_UPDATING_EMPLOYEE");
                    ErrorResponse error = new ErrorResponse(500, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                }
            } else {
                ErrorInfo errorInfo = new ErrorInfo("employee not found", "EMPLOYEE_NOT_FOUND");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.updateEmployee()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while updating employee", "ERROR_UPDATING_EMPLOYEE");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //remove employee
    @RolesAllowed({"Business Owner", "Employee"})
    @DELETE
    @Path("employee/{id}")
    @Produces("application/json")
    public Response removeEmployee(@PathParam("id") int employee_id) {
        User employee = adminBean.getEmployee(employee_id);
        if (employee != null) {
            adminBean.removeEmployee(employee_id);
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, null, "employee removed successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("employee not found", "EMPLOYEE_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //Delivery Operations
    //get all deliveries
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("delivery")
    @Produces("application/json")
    public Response getDeliveries() {
        Vector<Delivery> deliveries = (Vector<Delivery>) adminBean.getDeliveries();
        if (deliveries.size() > 0) {
            SuccessResponse<Delivery> response;
            response = new SuccessResponse<>(200, deliveries, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("deliveries not found", "DELIVERIES_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get delivery by id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("delivery/{id}")
    @Produces("application/json")
    public Response getDelivery(@PathParam("id") int delivery_id) {
        Delivery delivery = adminBean.getDelivery(delivery_id);
        if (delivery != null) {
            SuccessResponseSingle<Delivery> response;
            response = new SuccessResponseSingle<>(200, delivery, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("delivery not found", "DELIVERY_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get delivery by customer id
    @RolesAllowed({"Business Owner", "Employee", "Customer"})
    @GET
    @Path("getDeliveredItemsByCustomerId/{id}")
    @Produces("application/json")
    public Response getDeliveriesByCustomerId(@PathParam("id") int customer_id) {
        List<Delivery> deliveries = (List<Delivery>) adminBean.getDeliveriesByCustomerId(customer_id);
        if (deliveries.size() > 0) {
            SuccessResponse<Delivery> response;
            response = new SuccessResponse<>(200, deliveries, null, null);
            return Response.status(response.getStatus()).entity(deliveries).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("deliveries not found for given customer", "DELIVERIES_NOT_FOUND_FOR_CUSTOMER");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create delivery
    @RolesAllowed({"Business Owner", "Employee"})
    @POST
    @Path("delivery")
    @Produces("application/json")
    public Response addDelivery(JSONClasses.Delivery delivery) {
        if (adminBean.isDeliveryExists(delivery.getCustomer_id())) {
            ErrorInfo errorInfo = new ErrorInfo("delivery already exists for today", "DELIVERY_EXISTS");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (!delivery.getDelivered_items().isEmpty()) {
            {
                List<Delivereditem> delivereditems = new ArrayList<>();
                for (JSONClasses.DeliveredItem item : delivery.getDelivered_items()) {
                    Delivereditem dItem = new Delivereditem();
                    dItem.setName(item.getName());
                    dItem.setQuantity(item.getQuantity());
                    dItem.setPrice(item.getPrice());
                    Product product = adminBean.getProduct(item.getProduct_id());
                    if (product != null) {
                        dItem.setProductId(product);
                        delivereditems.add(dItem);
                    }
                }
                if (!delivereditems.isEmpty() && adminBean.addDelivery(delivery.getCustomer_id(), delivereditems)) {
                    SuccessResponseSingle<User> response;
                    response = new SuccessResponseSingle<>(200, null, "delivery added successfully", null);
                    return Response.status(response.getStatus()).entity(response).build();
                } else {
                    ErrorInfo errorInfo = new ErrorInfo("something went wrong while adding delivery", "SOMETHING_WENT_WRONG");
                    ErrorResponse error = new ErrorResponse(500, errorInfo);
                    return Response.status(error.getStatus()).entity(error).build();
                }
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("delivered items not provided", "DELIVERED_ITEMS_NOT_PROVIDED");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update delivery
    @RolesAllowed({"Business Owner", "Employee"})
    @PUT
    @Path("delivery")
    @Produces("application/json")
    public Response updateDelivery(JSONClasses.Delivery delivery) {
        Delivery delivery1 = adminBean.getDelivery(delivery.getDelivery_id());
        if (delivery1 != null) {
            if (!delivery.getDelivered_items().isEmpty()) {
                {
                    List<Delivereditem> delivereditems = new ArrayList<>();
                    for (JSONClasses.DeliveredItem item : delivery.getDelivered_items()) {
                        Delivereditem dItem = new Delivereditem();
                        dItem.setName(item.getName());
                        dItem.setQuantity(item.getQuantity());
                        dItem.setPrice(item.getPrice());
                        Product product = adminBean.getProduct(item.getProduct_id());
                        if (product != null) {
                            dItem.setProductId(product);
                            delivereditems.add(dItem);
                        }
                    }
                    if (!delivereditems.isEmpty() && adminBean.updateDelivery(delivery.getDelivery_id(), delivereditems)) {
                        SuccessResponseSingle<User> response;
                        response = new SuccessResponseSingle<>(200, null, "delivery updated successfully", null);
                        return Response.status(response.getStatus()).entity(response).build();
                    } else {
                        ErrorInfo errorInfo = new ErrorInfo("something went wrong while updating delivery", "SOMETHING_WENT_WRONG");
                        ErrorResponse error = new ErrorResponse(500, errorInfo);
                        return Response.status(error.getStatus()).entity(error).build();
                    }
                }
            } else {
                ErrorInfo errorInfo = new ErrorInfo("delivered items not provided", "DELIVERED_ITEMS_NOT_PROVIDED");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("delivery not found", "DELIVERY_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //remove delivery
    @RolesAllowed({"Business Owner", "Employee"})
    @DELETE
    @Path("delivery/{id}")
    @Produces("application/json")
    public Response removeDelivery(@PathParam("id") int delivery_id) {
        Delivery delivery = adminBean.getDelivery(delivery_id);
        if (delivery != null) {
            if (adminBean.removeDelivery(delivery_id)) {
                SuccessResponseSingle<User> response;
                response = new SuccessResponseSingle<>(200, null, "delivery removed successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                ErrorInfo errorInfo = new ErrorInfo("smoething went wrong while removing delivery", "SOMETHING_WENT_WRONG");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("delivery not found", "DELIVERY_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //DeliveredItems Operations
    //get delivered items by delivery id
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("deliveredItems/{id}")
    @Produces("application/json")
    public Response getDeliveredItemsByDeliveryId(@PathParam("id") int delivery_id) {
        JsonObject json = null;
        List<Delivereditem> deliveredItems = adminBean.getDeliveredItems(delivery_id);
        if (deliveredItems.size() > 0) {
            SuccessResponse<Delivereditem> response;
            response = new SuccessResponse<>(200, deliveredItems, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("deliveries not found", "DELIVERIES_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //customer operations
    //get all customers
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("customer")
    @Produces("application/json")
    public Response getCustomers() {
        JsonObject json = null;
        List<User> customers = (List<User>) adminBean.getCustomers();
        if (customers.size() > 0) {
            SuccessResponse<User> response;
            response = new SuccessResponse<>(200, customers, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("customers not found", "CUSTOMERS_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }
    
    //get all customers
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("getCustomersBySociety/{societyId}")
    @Produces("application/json")
    public Response getCustomersBySociety(@PathParam("societyId") int society_id) {
        List<User> customers = (List<User>) adminBean.getCustomersBySociety(society_id);
        if (customers.size() > 0) {
            SuccessResponse<User> response;
            response = new SuccessResponse<>(200, customers, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("customers not found", "CUSTOMERS_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get customer by id
    @RolesAllowed({"Business Owner", "Employee", "Customer"})
    @GET
    @Path("customer/{id}")
    @Produces("application/json")
    public Response getCustomer(@PathParam("id") int id) {
        JsonObject json = null;
        User customer = adminBean.getCustomer(id);
        if (customer != null) {
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, customer, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("customer not found", "CUSTOMER_NOT_FOUND");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //create customer
    @RolesAllowed({"Business Owner", "Employee", "Customer"})
    @POST
    @Path("customer")
    @Produces("application/json")
    @Consumes("application/json")
    public Response addCustomer(Customer customer) {
        if (adminBean.isUsernameExists(customer.getCustomer_name())) {
            ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.isEmailExists(customer.getCustomer_email())) {
            ErrorInfo errorInfo = new ErrorInfo("email address already in use", "EMAIL_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.isMobileNumberExists(customer.getCustomer_mobile_no())) {
            ErrorInfo errorInfo = new ErrorInfo("mobile number already in use", "MOBILE_NUMBER_ALREADY_EXIST");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (!adminBean.isValidSociety(customer.getSociety_id())) {
            ErrorInfo errorInfo = new ErrorInfo("invalid society.", "INVALID_SOCIETY");
            ErrorResponse error = new ErrorResponse(409, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        } else if (adminBean.addCustomer(customer.getCustomer_name(), customer.getCustomer_house_no(), customer.getCustomer_mobile_no(), customer.getSociety_id(), customer.getCustomer_email(), customer.getCustomer_password())) {
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, null, "customer created successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("customers not created successfully", "CUSTOMERS_NOT_CREATED");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //update customer
    @RolesAllowed({"Business Owner", "Employee"})
    @PUT
    @Path("customer")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCustomer(JSONClasses.Customer customer) {
        try {
            User customer1 = adminBean.getCustomer(customer.getCustomer_id());
            if (customer1 == null) {
                ErrorInfo errorInfo = new ErrorInfo("customer not found", "CUSTOMER_NOT_FOUND");
                ErrorResponse error = new ErrorResponse(404, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!customer1.getUsername().equals(customer.getCustomer_name()) && adminBean.isUsernameExists(customer.getCustomer_name())) {
                ErrorInfo errorInfo = new ErrorInfo("username already exists", "USERNAME_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!customer1.getEmail().equals(customer.getCustomer_email()) && adminBean.isEmailExists(customer.getCustomer_email())) {
                ErrorInfo errorInfo = new ErrorInfo("email address already in use.", "EMAIL_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!customer1.getMobileNo().equals(customer.getCustomer_mobile_no()) && adminBean.isMobileNumberExists(customer.getCustomer_mobile_no())) {
                ErrorInfo errorInfo = new ErrorInfo("mobile number already in use.", "MOBILE_NUMBER_ALREADY_EXIST");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (!adminBean.isValidSociety(customer.getSociety_id())) {
                ErrorInfo errorInfo = new ErrorInfo("invalid society.", "INVALID_SOCIETY");
                ErrorResponse error = new ErrorResponse(409, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            } else if (adminBean.updateCustomer(customer.getCustomer_id(), customer.getCustomer_name(), customer.getCustomer_house_no(), customer.getCustomer_mobile_no(), customer.getSociety_id(), customer.getCustomer_email(), customer.getCustomer_password())) {
                SuccessResponseSingle<User> response;
                response = new SuccessResponseSingle<>(200, null, "customer updated successfully", null);
                return Response.status(response.getStatus()).entity(response).build();
            } else {
                ErrorInfo errorInfo = new ErrorInfo("error while updating customer", "ERROR_UPDATING_CUSTOMER");
                ErrorResponse error = new ErrorResponse(500, errorInfo);
                return Response.status(error.getStatus()).entity(error).build();
            }
        } catch (UnexpectedTypeException ex) {
            System.out.println("com.mycompany.dropapplication.resources.JakartaEE8Resource.updateCustomer()");
            ex.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo("error while updating customer", "ERROR_UPDATING_CUSTOMER");
            ErrorResponse error = new ErrorResponse(500, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //remove customer
    @RolesAllowed({"Business Owner", "Employee"})
    @DELETE
    @Path("customer/{id}")
    @Produces("application/json")
    public Response removeCustomer(@PathParam("id") int customer_id) {
        User customer = adminBean.getCustomer(customer_id);
        JsonObject json = null;
        if (customer != null) {
            adminBean.removeCustomer(customer_id);
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, null, "customer removed successfully", null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("customer not found", "CUSTOMER_NOT_EXIST");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }

    //get user by username
    @RolesAllowed({"Business Owner", "Employee"})
    @GET
    @Path("user/{name}")
    @Produces("application/json")
    public Response getUserById(@PathParam("name") String name) {
        User user = adminBean.getUserByName(name);
        JsonObject json = null;
        if (user != null) {
            SuccessResponseSingle<User> response;
            response = new SuccessResponseSingle<>(200, user, null, null);
            return Response.status(response.getStatus()).entity(response).build();
        } else {
            ErrorInfo errorInfo = new ErrorInfo("user not found", "USER_NOT_EXIST");
            ErrorResponse error = new ErrorResponse(404, errorInfo);
            return Response.status(error.getStatus()).entity(error).build();
        }
    }
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
}
