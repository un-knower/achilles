package com.quancheng.achilles.service.model;

public class DataItemKV {
    private String key;
    
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataItemKV(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public DataItemKV() {
        super();
    }
}
