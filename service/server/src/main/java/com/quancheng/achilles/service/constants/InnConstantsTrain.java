package com.quancheng.achilles.service.constants;

public enum InnConstantsTrain {
    /**培训方式 1为到店培训 2为电话培训*/
    INN_TRAIN_TYPE_FIRST("1","到店培训"),
    INN_TRAIN_TYPE_SECOND("2","为电话培训"),
    
    /**优惠同享 1无优惠 2有优惠但商宴通不可享受 3有优惠且商宴通可以享受*/
    INN_TRAIN_CP_TYPE_FIRST("1","无优惠"),
    INN_TRAIN_CP_TYPE_SECOND("2","有优惠但商宴通不可享受"),
    INN_TRAIN_CP_TYPE_THRID("3","有优惠且商宴通可以享受"),
    
    /**台卡信息 1未发放 2已发放，但餐厅未使用 3已发放，餐厅已使用*/
    INN_TRAIN_TC_TYPE_FIRST("1","未发放"),
    INN_TRAIN_TC_TYPE_SECOND("2","已发放，但餐厅未使用"),
    INN_TRAIN_TC_TYPE_THRID("3","已发放，餐厅已使用"),
    
    /**台贴信息 1未发放 2已发放，但餐厅未贴 3已发放，餐厅已贴*/
    INN_TRAIN_TT_TYPE_FIRST("1","未发放"),
    INN_TRAIN_TT_TYPE_SECOND("2","已发放，但餐厅未贴"),
    INN_TRAIN_TT_TYPE_THRID("3","已发放，餐厅已贴"),
    
    /**便签信息 1未发放 2已发放*/
    INN_TRAIN_BQ_TYPE_FIRST("1","未发放"),
    INN_TRAIN_BQ_TYPE_SECOND("2","已发放");
   
    public String value;
    public String name;
    private InnConstantsTrain(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
