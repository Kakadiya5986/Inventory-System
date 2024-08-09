/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ritesh
 */
public class SuccessResponseSingle<T> {
    private int status;
    private T data;
    private String message;
    private Map<String, Object> metadata;

    public SuccessResponseSingle() {
    }

    public SuccessResponseSingle(int status, T data, String message, Map<String, Object> metadata) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.metadata = metadata;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    
}
