package com.quancheng.achilles.service.utils;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response<T> implements Serializable {

    public static ObjectMapper objectMapper;
    private Logger             log              = LoggerFactory.getLogger(Response.class);
    private static final long  serialVersionUID = 1L;
    private Integer            status;
    private String             message;
    private T                  data;

    public Response(){
        status = Integer.valueOf(-1);
    }

    public Integer getStatus() {
        return status;
    }

    public Response<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        String writeValueAsString = null;
        try {
            writeValueAsString = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Response Object to json String have a error {}", e);
        }
        return writeValueAsString;
    }

}
