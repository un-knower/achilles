package com.quancheng.achilles.service.model;

import java.util.List;

public class OdpsBaseResponse<T> {
    
    private List<T> data;
    
    private String status = "success";
    
    private String message ;
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public OdpsBaseResponse(List<T> data) {
        super();
        this.data = data;
    } 
    public OdpsBaseResponse( ) {
        super();
    } 
    public OdpsBaseResponse( String message ) {
        this.message =message;
        this.status="failed";
    }
    public OdpsBaseResponse( String message,String status) {
        this.message =message;
        this.status=status;
    }
    public static <T>OdpsBaseResponse<T> success( String message) {
        return new OdpsBaseResponse<T>(message,"success");
    }
    public static <T>OdpsBaseResponse<T> error( String message) {
        return new OdpsBaseResponse<T>(message,"failed");
    }
}