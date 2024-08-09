/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package record;

import java.io.Serializable;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;

/**
 *
 * @author root
 */
@Named(value = "keepRecord")
@SessionScoped
public class KeepRecord implements Serializable {
   private static CredentialValidationResult result;
   private static CallerPrincipal principal;
   private static Set<String> roles;
   private static String token;
   private static String username;
   private static String password;
   private static Integer userId;
   private static Integer businessId;
   private static String errorStatus;
   private static Credential credential;

    public static String getErrorStatus() {
        return errorStatus;
    }

    public static Credential getCredential() {
        return credential;
    }

    public static void setCredential(Credential credential) {
        KeepRecord.credential = credential;
    }

    public static void setErrorStatus(String errorStatus) {
        KeepRecord.errorStatus = errorStatus;
    }

    public KeepRecord() {
       principal=null;
       token=null;
       username=null;
       password=null;
       token=null;
       errorStatus="";
       businessId=null;
       userId=null;
    }

   
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        KeepRecord.username = username;
    }

    public  static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        KeepRecord.password = password;
    }

    public static CredentialValidationResult getResult() {
        return result;
    }

    public static void setResult(CredentialValidationResult result) {
        KeepRecord.result = result;
    }

    public static CallerPrincipal getPrincipal() {
        return principal;
    }

    public static void setPrincipal(CallerPrincipal principal) {
        KeepRecord.principal = principal;
    }

    public static Set<String> getRoles() {
        return roles;
    }

    public static void setRoles(Set<String> roles) {
        KeepRecord.roles = roles;
    }

    public static String getToken() {
        return token;
    }

    public  static void setToken(String token) {
        KeepRecord.token = token;
    }

    public static Integer getBusinessId() {
        return businessId;
    }

    public static void setBusinessId(Integer businessId) {
        KeepRecord.businessId = businessId;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        KeepRecord.userId = userId;
    }
   
    public static void reset()
    {
        setPrincipal(null);
        setToken(null);
        setUserId(null);
        setUsername(null);
        setPassword(null);
        setBusinessId(null);
        setErrorStatus(null);
        setCredential(null);
        setRoles(null);
    }
    
    
}