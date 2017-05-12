package com.quancheng.achilles.service.rest;

import java.util.List;

public class BaseResponse<T> {
    
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

    public BaseResponse(List<T> data) {
        super();
        this.data = data;
    } 
    public BaseResponse( ) {
        super();
    } 
    public BaseResponse( String message) {
        this.message =message;
        this.status="failed";
    } 
}
