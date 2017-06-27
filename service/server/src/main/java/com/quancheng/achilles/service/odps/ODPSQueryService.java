package com.quancheng.achilles.service.odps;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.aliyun.odps.OdpsException;
import com.quancheng.achilles.dao.annotation.OdpsColumn;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.AbstractOdpsQuery;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;
import com.quancheng.achilles.dao.odps.model.OdpsCompanyRestaurant;
import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
import com.quancheng.achilles.dao.odps.model.RestaurantInfo;

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

    public <T> boolean taskHospitalRestaurantDistance(T otype,
                                                      InnConstantODPSTables.TaskHospitalRestaurantDistance type,
                                                      Boolean compareCompany, Double distances, Boolean isWaimaiOk,
                                                      String sqlParam) throws OdpsException, IOException {
        String comapre = "";
        String allComapre = "";
        if (compareCompany != null && compareCompany) {
            comapre = "and h.company_id = r.company_id ";
            allComapre = "and h.company_id = a.company_id ";
        }
        String allSql = "INSERT OVERWRITE TABLE  %s  select h.company_id,h.company_name,h.city_id,h.city_name,h.hospital_id, h.hospital_name,h.address as hospital_address,h.lng as hospital_lng,h.lat as hospital_lat,h.settable as hospital_settable,a.restaurant_id,a.restaurant_name,a.restaurant_address,a.restaurant_lng,a.restaurant_lat,a.restaurant_settable,a.support_waimai, a.support_reserve,a.cook_style,a.consume,a.box_num,a.period,a.rate_settlement_type,a.manage_type,a.shipping_dis, a.distance,a.is_within from  %s  on a.hospital_name =h.hospital_name and a.city_id =h.city_id %s %s";
        String allFrom;
        String restaurantTable = "tmp_sync_restaurant_info";// 每日凌晨自动同步数据
        String hospitalTable = "tmp_sync_hospital_info";// 每日凌晨自动同步数据
        if (otype != null) {// 上传了数据
            if (otype instanceof HospitalInfo) {// 上传了医院信息
                hospitalTable = "tmp_hospital_info";
            } else if (otype instanceof RestaurantInfo) {// 上传了餐厅信息
                restaurantTable = "tmp_restaurant_info";
            }
        }
        String iswaimai = "";
        if (isWaimaiOk != null && isWaimaiOk) {
            iswaimai = " where  b.is_within=1 ";
        }

        String talle = " " + hospitalTable + " h left OUTER join " + restaurantTable + " r ";
        String allTalle = " " + hospitalTable + " h left outer  join out_hospital_restaurant_distance a  ";
        String sql = "INSERT OVERWRITE TABLE %s "
                     + "select DISTINCT b.company_id,b.company_name,b.city_id,b.city_name,b.hospital_id,b.hospital_name,b.hospital_address,b.hospital_lng,b.hospital_lat,b.hospital_settable,b.restaurant_id,b.restaurant_name,b.restaurant_address,b.restaurant_lng,b.restaurant_lat,b.restaurant_settable,b.support_waimai,b.support_reserve,b.cook_style,b.consume,b.box_num,b.period,b.rate_settlement_type,b.manage_type,b.shipping_dis,b.distance"
                     + ",b.is_within from ("
                     + "select DISTINCT a.company_id,a.company_name,a.city_id,a.city_name,a.hospital_id,a.hospital_name,a.hospital_address,a.hospital_lng,a.hospital_lat,a.hospital_settable,a.restaurant_id,a.restaurant_name,a.restaurant_address,a.restaurant_lng,a.restaurant_lat,a.restaurant_settable,a.support_waimai,a.support_reserve,a.cook_style,a.consume,a.box_num,a.period,a.rate_settlement_type,a.manage_type,a.shipping_dis,a.distance"
                     + ",case when a.support_waimai=1 and a.distance <=a.shipping_dis then 1 else 0 end as is_within from ("
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
                     + ",round(6378.137*1000.0*2.0*asin(sqrt(abs(pow(sin((r.lat-h.lat)*(3.1415926/ 180.0)*0.5),2) + cos((3.1415926/ 180.0)*r.lat) * cos((3.1415926/ 180.0)*h.lat) * pow(sin((r.lng-h.lng)*(3.1415926/ 180.0)*0.5),2))))) AS distance"
                     + " from %s " + "on h.city_id = r.city_id  %s  %s)  a where a.distance <= %s )  b %s ";
        if (InnConstantODPSTables.TaskHospitalRestaurantDistance.RestaurantHospital == type) {
            talle = " " + restaurantTable + " r  left OUTER join  " + hospitalTable + " h ";
            allSql = "INSERT OVERWRITE TABLE  %s  select a.company_id,a.company_name,a.city_id,a.city_name,a.hospital_id, a.hospital_name,a.address as hospital_address,a.lng as hospital_lng,a.lat as hospital_lat,a.settable as hospital_settable,r.restaurant_id,r.restaurant_name,r.address as restaurant_address,r.lng as restaurant_lng,r.lat as restaurant_lat,r.settable as restaurant_settable,r.support_waimai, r.support_reserve,r.cook_style,r.consume,r.box_num,r.period,r.rate_settlement_type,r.manage_type,r.shipping_dis, a.distance,a.is_within from  %s  on r.restaurant_name =a.restaurant_name and a.city_id =r.city_id %s  %s";
            allTalle = " " + restaurantTable + " r left outer  join out_hospital_restaurant_distance a  ";
            if (compareCompany != null && compareCompany) {
                allComapre = "and r.company_id = a.company_id ";
            }
        }

        // if (StringUtils.isEmpty(sqlParam)) {
        // sqlParam = "";
        // } else {
        // sqlParam = "where " + sqlParam;
        // }
        Boolean update = update(sql, InnConstantODPSTables.outHospitalRestaurantDistance, talle, comapre, sqlParam,
                                distances, iswaimai);
        if (!StringUtils.isEmpty(allSql)) {
            update = update(allSql, InnConstantODPSTables.outHospitalRestaurantDistance, allTalle, allComapre,
                            sqlParam);
        }
        return update;
    }

    /** 清空表内容 */
    public Boolean clearODPSTable(String tableName) throws OdpsException, IOException {
        String sql = "TRUNCATE TABLE  %s ";
        return update(sql, tableName);
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
    public List<Map> queryFromODPS(String tableName, Integer size) throws OdpsException, IOException {
        return queryFromODPS(tableName, null, size);
    }

    @SuppressWarnings("rawtypes")
    public List<Map> queryFromODPS(String tableName, Map<String, Object> param, Integer size) throws OdpsException,
                                                                                              IOException {
        String sql = " select * from %s %s limit  %s";
        String where = "";
        if (param != null && !param.isEmpty()) {
            where = "1=1";
            for (String col : param.keySet()) {
                where += " and " + col + "='" + param.get(col) + "'";
            }
        }
        List<Map> selectList = query(Map.class, sql, tableName, where, size);
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
