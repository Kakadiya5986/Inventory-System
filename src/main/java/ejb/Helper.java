/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejb;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author ritesh
 */
public class Helper {


    public static Timestamp getCurrentTimestamp() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        return timestamp;
    }

    
}
