/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package JSFbeans;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import record.KeepRecord;

/**
 *
 * @author ritesh
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    private String errorstatus = KeepRecord.getErrorStatus();

    public String getErrorStatus() {
        return errorstatus;
    }

    public void setErrorStatus(String status) {
        //status = KeepRecord.getErrorStatus();
        this.errorstatus = status;
    }

    public LoginBean() {
//         errorstatus="";
    }

}
