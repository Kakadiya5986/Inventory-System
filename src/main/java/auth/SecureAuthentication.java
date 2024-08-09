/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auth;

import ejb.AdminBean;
import ejb.AdminBeanLocal;
import entity.Business;
import entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static jwtrest.Constants.AUTHORIZATION_HEADER;
import static jwtrest.Constants.BEARER;
import jwtrest.JWTCredential;
import jwtrest.TokenProvider;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named
@RequestScoped
public class SecureAuthentication implements HttpAuthenticationMechanism, Serializable {

    @EJB
    AdminBeanLocal bean;
    @Inject
    IdentityStoreHandler handler;
    CredentialValidationResult result;
    AuthenticationStatus status;
    @Inject
    TokenProvider tokenProvider;
    String token = null;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext ctx) throws AuthenticationException {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("validating request");
        System.out.println("username :" + request.getParameter("username"));
        try {
            if (request.getRequestURI().contains("Logout")) {
                request.logout();
                KeepRecord.reset();
                response.sendRedirect("Login.jsf");
                return ctx.doNothing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String token = extractToken(ctx);
        if (token == null) {
            token = KeepRecord.getToken();
        }
        try {
            if (token == null && request.getParameter("username") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                System.out.println("In Auth");

                Credential credential = new UsernamePasswordCredential(username, new Password(password));
                result = handler.validate(credential);

                if (result.getStatus() == Status.VALID) {
                    KeepRecord.setErrorStatus("");
                    AuthenticationStatus status = createToken(result, ctx);
                    status = ctx.notifyContainerAboutLogin(result);

                    KeepRecord.setPrincipal(result.getCallerPrincipal());
                    KeepRecord.setRoles(result.getCallerGroups());
                    KeepRecord.setCredential(credential);
                    KeepRecord.setUsername(username);

                    //setting business in record
                    Business business = bean.getBusinessByUsername(username);
                    if (business != null) {
                        KeepRecord.setBusinessId(business.getId());
                    } else {
                        System.err.println("couldn't find business for given username!!");
                    }

                    //setting userid in record
                    User user = bean.getUserByName(username);
                    if (user != null) {
                        KeepRecord.setUserId(user.getId());
                    } else {
                        System.err.println("couldn't find user for given username!!");
                    }

                    if (request.getHeader("User-Agent").contains("PostmanRuntime")) {
                            response.setHeader("X-Auth-Token", token);
                    } else {
                        if (result.getCallerGroups().contains("Business Owner")) {
                            response.sendRedirect("index.jsf");
                        } else if (result.getCallerGroups().contains("Employee")) {
                            response.sendRedirect("index.jsf");
                        } else if (result.getCallerGroups().contains("Customer")) {
                            response.sendRedirect("Customer/index.jsf");
                        }
                    }
                    return status;
                } else {
                    KeepRecord.setErrorStatus("invalid username or password");
                    response.sendRedirect("Login.jsf");
                    return ctx.doNothing();
                }
            }

            if (KeepRecord.getToken() != null) {
                //  Credential credential1 = new UsernamePasswordCredential(KeepRecord.getUsername(), new Password(KeepRecord.getPassword()));
//                result = handler.validate(KeepRecord.getCredential());
//                AuthenticationStatus status = createToken(result, ctx);
//                
//                if(request.getRequestURI().contains("admin") && result.getCallerGroups().contains("Supervisor"))
//                {
//                    ctx.responseUnauthorized();
//                }
//               else if(request.getRequestURI().contains("user") && result.getCallerGroups().contains("Admin"))
//               {
//                  ctx.responseUnauthorized();
//               }

                ctx.notifyContainerAboutLogin(KeepRecord.getPrincipal(), KeepRecord.getRoles());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (token != null) {
            // validation of the jwt credential
            return validateToken(token, ctx);
        } else if (ctx.isProtected()) {
            // A protected resource is a resource for which a constraint has been defined.
            // if there are no credentials and the resource is protected, we response with unauthorized status
            return ctx.responseUnauthorized();
        }
        return ctx.doNothing();
    }

    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (tokenProvider.validateToken(token)) {
                JWTCredential credential = tokenProvider.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            // if token invalid, response with unauthorized status
            return context.responseUnauthorized();
        } catch (ExpiredJwtException eje) {
            return context.responseUnauthorized();
        }
    }

    /**
     * Create the JWT using CredentialValidationResult received from
     * IdentityStoreHandler
     *
     * @param result the result from validation of UsernamePasswordCredential
     * @param context
     * @return the AuthenticationStatus to notify the container
     */
    private AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context) {
        //context.getRequest().getSession().setAttribute("token", jwt);

        String jwt = tokenProvider.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
        KeepRecord.setToken(jwt);
        context.getResponse().addHeader(AUTHORIZATION_HEADER, BEARER + jwt);

        this.token = jwt;
        System.out.println("token: " + jwt);
        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    /**
     * To extract the JWT from Authorization HTTP header
     *
     * @param context
     * @return The JWT access tokens
     */
    private String extractToken(HttpMessageContext context) {
        String authorizationHeader = context.getRequest().getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length(), authorizationHeader.length());
            System.out.println("\n\n\ntoken found: " + token + "\n\n\n");
            return token;
        }
        return null;
    }

}
