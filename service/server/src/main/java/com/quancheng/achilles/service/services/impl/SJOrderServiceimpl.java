package com.quancheng.achilles.service.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.quancheng.achilles.service.constants.InnConstantsJob_SJ;
import com.quancheng.achilles.service.services.ISJOrderService;

@Component
public class SJOrderServiceimpl implements ISJOrderService {

	@Qualifier("writeSqlSession")
	@Autowired
	private SqlSession sqlSession;

	public void userCost(String date, Integer job) throws Exception {
		Map<String, Object> paramaters = getParamaters(date);
		if (job == 0 || job == InnConstantsJob_SJ.SJ_USER_COST.key) {
			List<Object> list = query(paramaters.get("data_current_date").toString(),InnConstantsJob_SJ.SJ_USER_COST.key,null);
			if(list==null || list.isEmpty()){
				for (String sqlid : InnConstantsJob_SJ.SJ_USER_COST.sqlIds) {
					sqlSession.update(sqlid, paramaters);
				}
			}
		}
		if (job == 0 || job == InnConstantsJob_SJ.SJ_USER_COST_BY_CITY.key) {
			List<Object> list = query(paramaters.get("data_current_date").toString(),InnConstantsJob_SJ.SJ_USER_COST_BY_CITY.key,null);
			if(list==null || list.isEmpty()){
				for (String sqlid : InnConstantsJob_SJ.SJ_USER_COST_BY_CITY.sqlIds) {
					sqlSession.update(sqlid, paramaters);
				}
			}
		}
		if (job == 0 || job == InnConstantsJob_SJ.SJ_FLY_CHECK_BY_CITY.key) {
			List<Object> list = query(paramaters.get("data_current_date").toString(),InnConstantsJob_SJ.SJ_FLY_CHECK_BY_CITY.key,null);
			if(list==null || list.isEmpty()){
				for (String sqlid : InnConstantsJob_SJ.SJ_FLY_CHECK_BY_CITY.sqlIds) {
					sqlSession.update(sqlid, paramaters);
				}
			}
		}
		if (job == 0 || job == InnConstantsJob_SJ.SJ_ORDer_SCORE.key) {
			List<Object> list = query(paramaters.get("data_current_date").toString(),InnConstantsJob_SJ.SJ_ORDer_SCORE.key,null);
			if(list==null || list.isEmpty()){
				for (String sqlid : InnConstantsJob_SJ.SJ_ORDer_SCORE.sqlIds) {
					sqlSession.update(sqlid, paramaters);
				}
			}
		}
	}

	private static Map<String, Object> getParamaters(String date) throws ParseException {
		// 用户消费信息
		Map<String, Object> paramaters = new HashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfm = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		if (date == null) {
			//2017-03-01
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			//2017-02-01
		} else {
			//2017-02
			calendar.setTime(dfm.parse(date));
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		paramaters.put("date_begin", df.format(calendar.getTime()));
		paramaters.put("data_current_date", dfm.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, 1);
		paramaters.put("date_end", df.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, -2);
		paramaters.put("data_last_date", dfm.format(calendar.getTime()));
		return paramaters;
	}
	@Override
	public <T> List<T> query(String date, Integer job,String clientId) throws Exception {
		InnConstantsJob_SJ sjs = InnConstantsJob_SJ.get(job);
		if(sjs== null){
			return new ArrayList<>();
		}
		Map<String, Object> paramaters = new HashMap<>();
		if(clientId!=null&& !clientId.isEmpty()){
			paramaters.put("clientId", clientId);
		}
		paramaters.put("happenDate", date);
		return sqlSession.selectList(sjs.sqlIds[0]+"Query", paramaters);
	}
}
