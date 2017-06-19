package com.quancheng.achilles.service.odps;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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

    public boolean taskHospitalRestaurantDistance(InnConstantODPSTables.TaskHospitalRestaurantDistance type,
                                                  double distances,
                                                  boolean compareCompany) throws OdpsException, IOException {
        String comapre = "";
        if (compareCompany) {
            comapre = "and h.company_id = r.company_id ";
        }
        String talle = " hospital_info h left OUTER join restaurant_info r ";
        String sql = "INSERT OVERWRITE TABLE %s"
                     + "select DISTINCT a.company_id,a.company_name,a.city_id,a.city_name,a.hospital_id,a.hospital_name,a.hospital_address,a.hospital_lng,a.hospital_lat,a.hospital_settable,a.restaurant_id,a.restaurant_name,a.restaurant_address,a.restaurant_lng,a.restaurant_lat,a.restaurant_settable,a.support_waimai,a.support_reserve,a.cook_style,a.consume,a.box_num,a.period,a.rate_settlement_type,a.manage_type,a.shipping_dis,a.distance"
                     + ",case when a.support_waimai=1 and a.distances <=a.shipping_dis then 1 else 0 end as is_within from ("
                     + "select DISTINCT h.company_id , h.company_name , h.city_id, h.city_name"
                     + ", h.hospital_id , h.hospital_name, h.address as hospital_address"
                     + ",h.lng as hospital_lng ,h.lat as hospital_lat"
                     + ",case when h.settable=1 then '是' else '否' end  as hospital_settable"
                     + ",r.restaurant_id, r.restaurant_name, r.address as restaurant_address"
                     + ",r.lng as restaurant_lng ,r.lat as restaurant_lat"
                     + ",case when r.settable=1 then '是'  else '否' end  as restaurant_settable"
                     + ",r.support_waimai, r.support_reserve, r.cook_style  " + ",r.consume , r.box_num , r.period"
                     + ",case when r.rate_settlement_type =1 then '返点现结'"
                     + " when r.rate_settlement_type =5 then '返点月结'  else '' end as rate_settlement_type"
                     + ",case when r.manage_type = 1 then '餐前预付'  when r.manage_type = 2 then '账期周结'"
                     + " when r.manage_type = 3 then '账期月结'  when r.manage_type = 4 then '循环预付'"
                     + " when r.manage_type = 0 then 'T+n账期'  end AS manage_type , r.shipping_dis"
                     + ",round(6378.137*1000.0*2.0*asin(sqrt(abs(pow(sin((r.lat-h.lat)*(3.1415926/ 180.0)*0.5),2) + cos(r.lat) * cos(h.lat) * pow(sin((r.lng-h.lng)*(3.1415926/ 180.0)*0.5),2))))) as distances"
                     + "from %s " + "on h.city_id = r.city_id  %s ) a where a.distances<= %s";
        if (InnConstantODPSTables.TaskHospitalRestaurantDistance.RestaurantHospital == type) {
            talle = " tmp_restaurant_info r  left OUTER join  tmp_hospital_info h ";
        }

        List<Map> selectList = query(Map.class, sql, InnConstantODPSTables.outHospitalRestaurantDistance, talle,
                                     comapre, distances);
        if (!CollectionUtils.isEmpty(selectList)) {
            System.err.println(JSON.toJSONString(selectList));
        }

        return true;
    }

    /** 清空表内容 */
    public boolean clearODPSTable(String tableName) throws OdpsException, IOException {
        String sql = "TRUNCATE TABLE  %s ";
        List<Map> selectList = query(Map.class, sql, tableName);
        if (!CollectionUtils.isEmpty(selectList)) {
            System.err.println(JSON.toJSONString(selectList));
        }
        return true;
    }

    /**
     * 插入内容到指定的表
     * 
     * @throws TimeoutException
     * @throws ExecutionException
     */
    public boolean insertToOdps(String tableName, List<Map<String, Object>> datas) throws OdpsException, IOException,
                                                                                   ParseException, ExecutionException,
                                                                                   TimeoutException {
        return insert(tableName, datas);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> queryFromODPS(String tableName, Integer pageNum, Integer pageSize) throws OdpsException,
                                                                                        IOException {
        return queryFromODPS(tableName, null, pageNum, pageSize);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> queryFromODPS(String tableName, Map<String, Object> param, Integer pageNum,
                                   Integer pageSize) throws OdpsException, IOException {
        String sql = " select * from %s %s  (%s-1)*%s,%s";
        String where = "";
        if (param != null && !param.isEmpty()) {
            where = "1=1";
            for (String col : param.keySet()) {
                where += " and " + col + "='" + param.get(col) + "'";
            }
        }
        List<Map> selectList = query(Map.class, sql, tableName, where, pageNum, pageSize, pageSize);
        return selectList;
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
