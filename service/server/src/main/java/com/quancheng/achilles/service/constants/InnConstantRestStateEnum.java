package com.quancheng.achilles.service.constants;

import java.util.HashMap;
import java.util.Map;

public enum InnConstantRestStateEnum {
	REST_STATE_0("0"  , "在线"),
	REST_STATE_1("1" , "禁用"),
	REST_STATE_A("2"  , "上线审核中"),
	REST_STATE_B("19" , "等待开拓"),
	REST_STATE_C("21" , "拓展中),历史数据(拒绝合作)"),
	REST_STATE_D("22" , "拓展中),历史数据(不符合商务宴请)"),
	REST_STATE_E("23" , "拓展中),历史数据(不合规)"),
	REST_STATE_F("24" , "拓展中),历史数据(歇业、转店、装修)"),
	REST_STATE_G("25" , "初步接洽"),
	REST_STATE_H("26" , "深入谈判"),
	REST_STATE_I("27" , "合同协商"),
	REST_STATE_J("28" , "完成签约"),
	REST_STATE_K("40" , "商户不符合签约条件(提供娱乐设施)"),
	REST_STATE_L("41" , "商户不符合签约条件(人均超标)"),
	REST_STATE_O("42" , "商户不符合签约条件(发票不规范)"),
	REST_STATE_P("43" , "商户不符合签约条件(运营资质不符)"),
	REST_STATE_Q("44" , "商户不符合签约条件(门店装修)"),
	REST_STATE_R("45" , "商户不符合签约条件(歇业倒闭)"),
	REST_STATE_S("46" , "商户拒绝(不与任何第三方合作)"),
	REST_STATE_T("47" , "商户拒绝(财务系统冲突)"),
	REST_STATE_U("48" , "商户拒绝(分店无法做主)"),
	REST_STATE_V("49" , "商户拒绝(商务条件苛刻)"),
	REST_STATE_W("50" , "商户拒绝(拒绝优惠同享)"),
	REST_STATE_X("99" , "重复餐厅");
	public String key;
	public String value;
	private InnConstantRestStateEnum(String key,String value){
		this.key = key;
		this.value = value;
	}

	public static Map<Object, Object> parseSources() {
		Map<Object, Object> map = new HashMap<>();
		for (InnConstantRestStateEnum icrr : InnConstantRestStateEnum.values()) {
			map.put(icrr.key, icrr.value);
		}
		return map;
	}
}
