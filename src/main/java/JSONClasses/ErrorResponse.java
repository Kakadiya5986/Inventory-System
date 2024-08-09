/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONClasses;

/**
 *
 * @author ritesh
 */
public class ErrorResponse {
  private int status;
  private ErrorInfo error;

  // Constructor
  public ErrorResponse(int status, ErrorInfo error) {
    this.status = status;
    this.error = error;
  }

  // Getters and setters
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ErrorInfo getError() {
    return error;
  }

  public void setError(ErrorInfo error) {
    this.error = error;
  }    
}
