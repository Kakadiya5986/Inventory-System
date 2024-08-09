/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package client;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Provider
@PreMatching
//@WebFilter(filterName = "RESTFilter", urlPatterns = {"/webresources/*"})
public class RESTFilter implements ClientRequestFilter {

    public static String myToken;

    public RESTFilter(){
        
    }
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (KeepRecord.getToken() != null) {
            requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + KeepRecord.getToken());
        }
    }
}
