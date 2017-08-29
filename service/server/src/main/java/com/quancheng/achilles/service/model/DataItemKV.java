package com.quancheng.achilles.service.model;

public class DataItemKV {
    private String key;
    
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataItemKV(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public DataItemKV() {
        super();
    }
}
