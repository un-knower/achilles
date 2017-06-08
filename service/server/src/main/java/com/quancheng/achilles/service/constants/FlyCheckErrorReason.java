package com.quancheng.achilles.service.constants;

public enum FlyCheckErrorReason {
    is_standard("is_standard","0","用餐过程是否合规",new ErrorKeys[]{
            ErrorKeys.illegal_entertain,
            ErrorKeys.other_risks,
            ErrorKeys.illegal_falseConsumption,
            ErrorKeys.illegal_tokenCard,
            ErrorKeys.illegal_overBudget,
            ErrorKeys.illegal_split,
            ErrorKeys.illegal_purchaseGift,
            }),
    is_same_money("is_same_money","0","发票金额与订单金额是否一致",ErrorKeys.money_diff_reason),
    is_same_peopleNum("is_same_peopleNum","0","用户消费人数与观察到的人数是否统一",ErrorKeys.peopleNum_diff_reason),
    is_in_oneHour("is_in_oneHour","0","用户是否在预约用餐时间前后1h内用餐",ErrorKeys.oneHour_reason),
    ;
    private String key;
    private String text;
    private String errorValue;
    private ErrorKeys[] errorKeys;
    private FlyCheckErrorReason(String key,String errorValue,String text,ErrorKeys ... errorKeys){
        this.errorValue=errorValue;
        this.key=key;
        this.text=text;
        this.errorKeys=errorKeys;
    }
    public String getKey() {
        return key;
    }
    public String getText() {
        return text;
    }
    public String getErrorValue() {
        return errorValue;
    }
    public ErrorKeys[] getErrorKeys() {
        return errorKeys;
    }
}

