/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/JerseyClient.java to edit this template
 */
package client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:JakartaEE8Resource [v1]<br>
 * USAGE:
 * <pre>
 *        RESTClient client = new RESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author ritesh
 */
public class RESTClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/dropApplication/api";
    private RESTFilter restFilter;

    public RESTClient() {
        restFilter=new RESTFilter();
        client = javax.ws.rs.client.ClientBuilder.newClient().register(restFilter);
        webTarget = client.target(BASE_URI).path("v1");
    }

    public <T> T getUserById(Class<T> responseType, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("user/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response removeProduct(int id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("product/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public Response addProduct(Object requestEntity) throws ClientErrorException {
        return webTarget.path("product").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response updateProduct(Object requestEntity) throws ClientErrorException {
        return webTarget.path("product").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getDelivery(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("delivery/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /*public <T> T ping(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.get(responseType);
    }*/

    public <T> T getBusiness(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("business/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response removeEmployee(int id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("employee/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public Response updateSociety(Object requestEntity) throws ClientErrorException {
        return webTarget.path("society").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response addCustomer(Object requestEntity) throws ClientErrorException {
        return webTarget.path("customer").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getBusinessByUsername(Class<T> responseType, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("businessByUsername/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response addBusiness(Object requestEntity) throws ClientErrorException {
        return webTarget.path("business").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response removeDelivery(int id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("delivery/{0}", new Object[]{id})).request().post(null, Response.class);
    }

    public Response updateEmployee(Object requestEntity) throws ClientErrorException {
        return webTarget.path("employee").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response addBusinessOwner(Object requestEntity) throws ClientErrorException {
        return webTarget.path("businessOwner").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getSocieties(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("society");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getEmployee(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("employee/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response removeCustomer(int id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("customer/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public <T> T getProducts(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("product");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getProduct(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("product/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getCustomers(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("customer");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getDeliveredItemsByDeliveryId(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("deliveredItems/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response editBusiness(Object requestEntity) throws ClientErrorException {
        return webTarget.path("business").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getEmployees(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("employee");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getDeliveriesByCustomerId(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getDeliveredItemsByCustomerId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response updateDelivery() throws ClientErrorException {
        return webTarget.path("delivery").request().post(null, Response.class);
    }

    public <T> T getCustomer(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("customer/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getDeliveries(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("delivery");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response addDelivery() throws ClientErrorException {
        return webTarget.path("delivery").request().post(null, Response.class);
    }

    public Response removeSociety(int id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("society/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public Response updateCustomer(Object requestEntity) throws ClientErrorException {
        return webTarget.path("customer").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getSociety(Class<T> responseType, int id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("society/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response addSociety() throws ClientErrorException {
        return webTarget.path("society").request().post(null, Response.class);
    }

    public Response addEmployee() throws ClientErrorException {
        return webTarget.path("employee").request().post(null, Response.class);
    }

    public void close() {
        client.close();
    }
    
}
