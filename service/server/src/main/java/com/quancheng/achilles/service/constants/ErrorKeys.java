package com.quancheng.achilles.service.constants;

public enum ErrorKeys{
    other_risks("other_risks",null),
    illegal_entertain("illegal_entertain","有进行娱乐活动"),
    illegal_falseConsumption("illegal_falseConsumption","虚假消费"),
    illegal_tokenCard("illegal_tokenCard","购买储值卡"),
    illegal_overBudget("illegal_overBudget","超过人均餐标300"),
    illegal_split("illegal_split","拆分消费开发票"),
    illegal_purchaseGift("illegal_purchaseGift","购买烟酒及带走礼品"),
    peopleNum_diff_reason("peopleNum_diff_reason",null),
    money_diff_reason("money_diff_reason",null),
    oneHour_reason("oneHour_reason",null)
 ;
    private String key,text;
    private ErrorKeys(String key,String text){
        this.key=key;
        this.text=text;
    }
    public String getKey() {
        return key;
    }
    public String getText() {
        return text;
    }
}