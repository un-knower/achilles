package com.quancheng.achilles.dao.model;

public class BaseResponse {
    private String status="0";
    private String msg;
    private Object data;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public BaseResponse(Object data) {
        super();
        this.data = data;
    }
    public BaseResponse(String msg, Object data) {
        super();
        this.msg = msg;
        this.data = data;
    }
    public BaseResponse() {
        super();
    }
}
