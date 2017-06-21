package com.quancheng.achilles.service.constants;

import java.util.HashMap;
import java.util.List;
import java.util.*;

public enum InnConstantsOrderStatus {
        
        //外卖 wm
        TOBE_CONFRIM_2001("1",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2001,InnOrderType.PRE_YD+ 1 }) ,"待确认"),
        TOBE_CONFRIM_2002("2",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2002})  ,"已送达(线上支付)"),
        RESERVE_SUCCESS_2005("3",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2005,InnOrderType.PRE_YD +2,InnOrderType.PRE_YD+5}) ,"预订成功"),
        REFUSED_APPLIER("4",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2006}) ,"供应商拒绝"),
        REFUSED_SERVICE("5",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2007})  , "客服拒绝"),
        CANCLE_SERVICE_2008("6",Arrays.asList( new String[]{InnOrderType.PRE_WM + 2008,InnOrderType.PRE_YD + 11}) ,  "客服取消"),
        TOBE_RECEIVE("7",Arrays.asList(new String[]{InnOrderType.PRE_WM + 2030}) , "确定收货"),
        //预定
//        NEED_CONFRIM_1(InnOrderType.PRE_YD+ 1 ,"待确认"),
//        PAYMENT_TO_BE_CONFIRM(InnOrderType.PRE_YD +2, "预订成功"),
//        RESERVE_SUCCESS_5(InnOrderType.PRE_YD+5, "预订成功"),
        REFUSED("8",Arrays.asList( new String[]{InnOrderType.PRE_YD + 6}),"商户拒绝"),
        REFUSED_("9",Arrays.asList( new String[]{InnOrderType.PRE_YD +  7}),"客服拒绝"),
        CANCLE_10("10",Arrays.asList( new String[]{InnOrderType.PRE_YD + 10}) , "商户取消"),
//        CANCLE_11(InnOrderType.PRE_YD+  11,"客服取消"),
        CANCLE_USER("11",Arrays.asList( new String[]{InnOrderType.PRE_YD + 15}), "用户取消"),
        PAYMENT_TO_BE_PAID("12",Arrays.asList( new String[]{InnOrderType.PRE_YD + 30}), "待支付"),
        PAYMENT_ONLINE("13",Arrays.asList( new String[]{InnOrderType.PRE_YD + 35}), "已支付(线上)"),
        PAYMENT_CASH("14",Arrays.asList( new String[]{InnOrderType.PRE_YD + 36}),"已支付(线下)"),
//        //编外
        TOBE_NEW("15",Arrays.asList( new String[]{InnOrderType.PRE_BW+10}),"新增(待审核)"),
        APPROVE_PASS("16",Arrays.asList( new String[]{InnOrderType.PRE_BW+12}),"订单审核通过"),
        ORDER_COMPLETED("17",Arrays.asList( new String[]{InnOrderType.PRE_BW+20}), "订单完成。支付"),
        ORDER_APPROVE_REFUSED("18",Arrays.asList( new String[]{InnOrderType.PRE_BW+30}),"订单审核拒绝"),
        ORDER_CANCLE("19",Arrays.asList( new String[]{InnOrderType.PRE_BW+35}),"订单取消"),
        /**
        EXPENSED( 45 , "已报销"),
        TOBE_EXPENSE( 40 , "待报销"),
        */;
    String order;
    private List<String> type;
    private String value;
   
    public static InnConstantsOrderStatus getByKey(String key){
        for (InnConstantsOrderStatus iterable_element : InnConstantsOrderStatus.values()) {
            if(iterable_element.getType().contains(key)){
                return iterable_element;
            }
        }
        return null;
    }

    public static Map<Object, Object> getMap() {
        Map<Object, Object> result = new HashMap<>();
        for (InnConstantsOrderStatus ico : InnConstantsOrderStatus.values()) {
            result.put(ico.getOrder(), ico.getValue());
        }
        return result;
    }
    
    public static List<String> getList(String[] key){
       List<String> keys = Arrays.asList(key);
       List<String> result = new ArrayList<>();
       for (InnConstantsOrderStatus ico : InnConstantsOrderStatus.values()) {
           if(keys.contains(ico.getOrder())){
                      result.addAll(ico.getType());
           }
        }
       return result;
   }

    public static Map<Object, Object> getMapAll() {
        Map<Object, Object> result = new HashMap<>();
        for (InnConstantsOrderStatus ico : InnConstantsOrderStatus.values()) {
            for (String iterable_element : ico.type) {
                result.put(iterable_element, ico.getValue());
            }
        }
        return result;
    }
    
    private InnConstantsOrderStatus(String order,List<String> type , String value){
            this.order=order;
        this.type=type;
        this.value=value;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
}
