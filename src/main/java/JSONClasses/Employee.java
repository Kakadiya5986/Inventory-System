/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author ritesh
 */
public class Employee {
    @JsonbProperty("id")
    private int employee_id;
    @JsonbProperty("business_id")
    private int business_id;
    @JsonbProperty("username")
    private String employee_name;
    @JsonbProperty("email")
    private String employee_email;
    @JsonbProperty("password")
    private String employee_password;
    @JsonbProperty("mobile_no")
    private String employee_mobile_no;

    public Employee() {
    }

    public Employee(int employee_id, int business_id, String employee_name, String employee_email, String employee_password, String employee_mobile_no) {
        this.employee_id = employee_id;
        this.business_id = business_id;
        this.employee_name = employee_name;
        this.employee_email = employee_email;
        this.employee_password = employee_password;
        this.employee_mobile_no = employee_mobile_no;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_email() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }

    public String getEmployee_mobile_no() {
        return employee_mobile_no;
    }

    public void setEmployee_mobile_no(String employee_mobile_no) {
        this.employee_mobile_no = employee_mobile_no;
    }
    
}
