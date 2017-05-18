package com.quancheng.achilles.service.odps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.aliyun.odps.OdpsException;
import com.quancheng.achilles.dao.annotation.OdpsColumn;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.AbstractOdpsQuery;
import com.quancheng.achilles.dao.odps.model.OdpsCompanyRestaurant;
import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;

import net.sf.json.JSONArray;

@Service
public class ODPSQueryService extends AbstractOdpsQuery {

    public List<OdpsFlyCheck> queryUserCostByClient(String clientId, String happenDate) throws OdpsException,
                                                                                        IOException {
        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("client_id", clientId);
        paramaters.put("happen_date", happenDate);
        return query(paramaters, InnConstantODPSTables.outSjFlyCheckMonth, OdpsFlyCheck.class);
    }

    public JSONArray queryUserCostByClientReturnJson(String clientId, String happenDate) throws OdpsException,
                                                                                         IOException {
        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("client_id", clientId);
        paramaters.put("happen_date", happenDate);
        return query(paramaters, InnConstantODPSTables.outSjFlyCheckMonth);
    }

    public OdpsCompanyRestaurant queryCompanyRestaurantsStatistics(String time, String companyId) throws OdpsException,
                                                                                                  IOException {
        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("time", time);
        paramaters.put("company_id", companyId);
        String sql = "select  time, company_id, company_name, sum(restaurants_num) as all_restaurants_num from %s where time = %s and company_id=%s group by time, company_id, company_name";
        List<OdpsCompanyRestaurant> selectList = query(OdpsCompanyRestaurant.class, sql,
                                                       InnConstantODPSTables.outCompanyRestaurantsStatistics, time,
                                                       companyId);

        if (!CollectionUtils.isEmpty(selectList)) {
            return selectList.get(0);
        } else {
            return null;
        }
    }

    public Integer queryCompanyAllShelfNumByTime(String time, String companyId) throws OdpsException, IOException {
        Integer allNum = null;
        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("time", time);
        paramaters.put("company_id", companyId);
        String sql = " select  sum(shelf_num) as all_num  from %s where time = %s and company_id=%s group by company_id";
        List<Map> selectList = query(Map.class, sql, InnConstantODPSTables.outCompanyShelfRestaurant, time, companyId);
        if (!CollectionUtils.isEmpty(selectList)) {
            allNum = Integer.parseInt(selectList.get(0).get("all_num").toString());
        }

        return allNum;
    }

    public Integer queryCompanyAllSourceNumByTime(String time, String companyId) throws OdpsException, IOException {
        Integer allNum = null;
        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("time", time);
        paramaters.put("company_id", companyId);
        String sql = " select  sum(source_num) as all_num  from %s where time = %s and company_id=%s group by company_id";
        List<Map> selectList = query(Map.class, sql, InnConstantODPSTables.outCompanyRestaurantSource, time, companyId);
        if (!CollectionUtils.isEmpty(selectList)) {
            allNum = Integer.parseInt(selectList.get(0).get("all_num").toString());
        }

        return allNum;
    }
}

class TempIntegerObject {

    @OdpsColumn("all_num")
    private int allNum;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }
}
