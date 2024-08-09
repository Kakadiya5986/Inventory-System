/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author ritesh
 */
//Identity Store
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/drop-db",
        callerQuery = "select password from user where username=?",
        groupsQuery = "select groupname from roles where username=?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 30
)
@ApplicationScoped
public class Security {

}
