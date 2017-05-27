package com.quancheng.achilles.service.constants;

public enum InnConstantsQualityState {
    A("-1","删除"),
    B("0","未飞检"),
    C("1","任务已指派"),
    D("2","进入订单列表"),
    E("5","合规"),
    F("9","删除"),
    G("10","异常"),
    H("15","申诉中"),
    I("20","未用餐"),
    J("25","疑似异常");
   
    public String value;
    public String name;
    private InnConstantsQualityState(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
