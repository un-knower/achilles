package com.quancheng.achilles.service.utils;

public enum EnumDownLoadModel {
    REST_GH("restaurantGh","公海餐厅"),
    RECOMMANDER("recommenders","推荐餐厅");
    public String MODEL_METHOD;
    public String MODEL_DISPLAY;
    private EnumDownLoadModel(String method, String model) {
        this.MODEL_METHOD = method;
        this.MODEL_DISPLAY = model;
    }
}
