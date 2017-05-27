package com.quancheng.achilles.service.constants;

public enum InnConstantsJob_SJ  {
	SJ_USER_COST(1,InnConstantsJob.SJ_TITLE1,  new String[]{  "sjUserCountLastMonth" , "sjActiveUserCountLastMonth" , "sjPaymentOrderCountLastMonth", "sjPaymentOrderMoneyLastMonth"} ), 
	SJ_USER_COST_BY_CITY(2,InnConstantsJob.SJ_TITLE2,  new String[]{ "sjCityPaymentOrderMoneyLastMonth",  "sjCityActiveUserCountLastMonth"} ),
	SJ_FLY_CHECK_BY_CITY(3,InnConstantsJob.SJ_TITLE3,  new String[]{ "sjFlyCheckMonthStastic"}),
	SJ_ORDer_SCORE(4,InnConstantsJob.SJ_TITLE4,  new String[]{ "sjOrderScoreMonthStastic"});
	
	public final int key;
	public final String title;
	public final String[] sqlIds;
	
	private InnConstantsJob_SJ(int key, String title, String[] sqlIds) {
		this.key = key;
		this.title = title;
		this.sqlIds = sqlIds;
	}
	public static InnConstantsJob_SJ get(int key){
		for(InnConstantsJob_SJ sj:InnConstantsJob_SJ.values()){
			if(sj.key==key){
				return sj;
			}
		}
		return null;
	}
}
