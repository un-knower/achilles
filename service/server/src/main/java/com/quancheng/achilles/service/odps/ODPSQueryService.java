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
        @SuppressWarnings("rawtypes")
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
        @SuppressWarnings("rawtypes")
        List<Map> selectList = query(Map.class, sql, InnConstantODPSTables.outCompanyRestaurantSource, time, companyId);
        if (!CollectionUtils.isEmpty(selectList)) {
            allNum = Integer.parseInt(selectList.get(0).get("all_num").toString());
        }

        return allNum;
    }

    public <T> boolean taskHospitalRestaurantDistance(String uuid, T otype,
                                                      InnConstantODPSTables.TaskHospitalRestaurantDistance type,
                                                      Boolean compareCompany, Double distances, Boolean isWaimaiOk,
                                                      String sqlParam) throws OdpsException, IOException {
        String comapre = "";
        String allComapre = "";
        if (compareCompany != null && compareCompany) {
            comapre = "and h.company_id = r.company_id ";
            allComapre = "and h.company_id = a.company_id ";
        }
        String restaurantTable = "tmp_sync_restaurant_info";// 每日凌晨自动同步数据
        String hospitalTable = "tmp_sync_hospital_info";// 每日凌晨自动同步数据

        String restaurantWhere = " and r.deleted_at is null and r.status = 0 ";
        String hospitalWhere = " and h.deleted_at is null and h.status = 1 ";

        // String restaurantTableStatus = " r.status=0 and";
        if (otype != null) {// 上传了数据
            if (otype instanceof HospitalInfo) {// 上传了医院信息
                hospitalTable = "tmp_hospital_info";
                hospitalWhere = "";
            } else if (otype instanceof RestaurantInfo) {// 上传了餐厅信息
                restaurantTable = "tmp_restaurant_info";
                restaurantWhere = "";
                // restaurantTableStatus = "";
            }
        }
        String allSql = "INSERT OVERWRITE TABLE  %s  select DISTINCT h.company_id,h.company_name,h.city_id,h.city_name,h.hospital_id, h.hospital_name,h.address as hospital_address,h.lng as hospital_lng,h.lat as hospital_lat,h.settable as hospital_settable,a.restaurant_id,a.restaurant_name,a.restaurant_address,a.restaurant_lng,a.restaurant_lat,a.restaurant_settable,a.support_waimai, a.support_reserve,a.cook_style,a.consume,a.box_num,a.period,a.rate_settlement_type,a.manage_type,a.shipping_dis, a.distance,a.is_within from  %s  on a.hospital_id =h.hospital_id and a.city_id =h.city_id %s %s"
                        + hospitalWhere;

        String iswaimai = "";
        if (isWaimaiOk != null && isWaimaiOk) {
            iswaimai = " where  b.is_within=1 ";
        }
        String tableName = InnConstantODPSTables.outHospitalRestaurantDistance + "_" + uuid;
        String talle = " " + hospitalTable + " h left OUTER join " + restaurantTable + " r ";
        String allTalle = " " + hospitalTable + " h left outer  join " + tableName + " a  ";
        String sql = "INSERT OVERWRITE TABLE %s "
                     + "select DISTINCT b.company_id,b.company_name,b.city_id,b.city_name,b.hospital_id,b.hospital_name,b.hospital_address,b.hospital_lng,b.hospital_lat,b.hospital_settable,b.restaurant_id,b.restaurant_name,b.restaurant_address,b.restaurant_lng,b.restaurant_lat,b.restaurant_settable,b.support_waimai,b.support_reserve,b.cook_style,b.consume,b.box_num,b.period,b.rate_settlement_type,b.manage_type,b.shipping_dis,b.distance"
                     + ",b.is_within from ("
                     + "select DISTINCT a.company_id,a.company_name,a.city_id,a.city_name,a.hospital_id,a.hospital_name,a.hospital_address,a.hospital_lng,a.hospital_lat,a.hospital_settable,a.restaurant_id,a.restaurant_name,a.restaurant_address,a.restaurant_lng,a.restaurant_lat,a.restaurant_settable,a.support_waimai,a.support_reserve,a.cook_style,a.consume,a.box_num,a.period,a.rate_settlement_type,a.manage_type,a.shipping_dis,a.distance"
                     + ",case when a.support_waimai=1 and a.distance <=a.shipping_dis then 1 else 0 end as is_within from ("
                     + "select DISTINCT h.company_id , h.company_name , h.city_id, h.city_name"
                     + ", h.hospital_id , h.hospital_name, h.address as hospital_address"
                     + ",h.lng as hospital_lng ,h.lat as hospital_lat" + ",h.settable as hospital_settable"
                     + ",r.restaurant_id, r.restaurant_name, r.address as restaurant_address"
                     + ",r.lng as restaurant_lng ,r.lat as restaurant_lat" + ",r.settable  as restaurant_settable"
                     + ",r.support_waimai, r.support_reserve, r.cook_style  " + ",r.consume , r.box_num , r.period"
                     + ",r.rate_settlement_type" + ", r.manage_type , r.shipping_dis"
                     + ",round(6378.137*1000.0*2.0*asin(sqrt(abs(pow(sin((r.lat-h.lat)*(3.1415926/ 180.0)*0.5),2) + cos((3.1415926/ 180.0)*r.lat) * cos((3.1415926/ 180.0)*h.lat) * pow(sin((r.lng-h.lng)*(3.1415926/ 180.0)*0.5),2))))) AS distance"
                     + " from %s " + "on h.city_id = r.city_id  %s  %s %s)  a where  a.distance <= %s %s)  b %s ";
        if (InnConstantODPSTables.TaskHospitalRestaurantDistance.RestaurantHospital == type) {
            talle = " " + restaurantTable + " r  left OUTER join  " + hospitalTable + " h ";
            allSql = "INSERT OVERWRITE TABLE  %s  select DISTINCT a.company_id,a.company_name,a.city_id,a.city_name,a.hospital_id, a.hospital_name,a.hospital_address,a.hospital_lng,a.hospital_lat,a.hospital_settable,r.restaurant_id,r.restaurant_name,r.address as restaurant_address,r.lng as restaurant_lng,r.lat as restaurant_lat,r.settable as restaurant_settable,r.support_waimai, r.support_reserve,r.cook_style,r.consume,r.box_num,r.period,r.rate_settlement_type,r.manage_type,r.shipping_dis, a.distance,a.is_within from  %s  on r.restaurant_id =a.restaurant_id and a.city_id =r.city_id %s  %s"
                     + restaurantWhere;
            allTalle = " " + restaurantTable + " r left outer  join " + tableName + " a  ";
            if (compareCompany != null && compareCompany) {
                allComapre = "and r.company_id = a.company_id ";
            }
        }

        // if (StringUtils.isEmpty(sqlParam)) {
        // sqlParam = "";
        // } else {
        String sqlParam1 = sqlParam;
        String sqlParam2 = "";
        if (sqlParam.contains("or")) {
            sqlParam2 = sqlParam.replaceAll("r\\.", "a.");
            sqlParam1 = "";
        }
        // sqlParam = "where " + sqlParam;
        // }

        String createTable = "CREATE TABLE  %s (" + "company_id STRING COMMENT '公司id',"
                             + "company_name STRING COMMENT '司名称'," + "city_id STRING COMMENT '城市id',"
                             + " city_name STRING COMMENT '城市名称'," + " hospital_id STRING COMMENT '医院id',"
                             + " hospital_name STRING COMMENT '医院名称'," + " hospital_address STRING COMMENT '医院地址',"
                             + " hospital_lng DOUBLE COMMENT '医院经度'," + " hospital_lat DOUBLE COMMENT '医院纬度',"
                             + " hospital_settable STRING COMMENT '医院是否可定位（0不可，1可）',"
                             + " restaurant_id STRING COMMENT '餐厅id'," + " restaurant_name STRING COMMENT '餐厅名称',"
                             + " restaurant_address STRING COMMENT '餐厅地址'," + " restaurant_lng DOUBLE COMMENT '餐厅经度',"
                             + " restaurant_lat DOUBLE COMMENT '餐厅纬度',"
                             + " restaurant_settable STRING COMMENT '餐厅是否可定位（0不可，1可）',"
                             + " support_waimai STRING COMMENT '是否支持外卖（0不可，1可）',"
                             + " support_reserve STRING COMMENT '是否支持外预定（0不可，1可）'," + " cook_style STRING COMMENT '菜系',"
                             + " consume DOUBLE COMMENT '人均'," + " box_num BIGINT COMMENT '包厢数',"
                             + " period DOUBLE COMMENT '账期（天）',"
                             + " rate_settlement_type STRING COMMENT '返点结算类型: 1返点现结 5返点月结',"
                             + " manage_type STRING COMMENT '结算类型：t+n账期：0，餐前预付：1，账期周结：2，账期月结：3，循环预付：4',"
                             + " shipping_dis DOUBLE COMMENT '配送距离'," + " distance DOUBLE COMMENT '医院餐厅之间距离',"
                             + " is_within STRING COMMENT '是否在配送范围内（0不在，1在）'" + ")" + "COMMENT '医院餐厅距离结果表'"
                             + "LIFECYCLE 100000";
        Boolean update = update(createTable, tableName);
        update = update(sql, tableName, talle, comapre, sqlParam1, restaurantWhere + hospitalWhere, distances,
                        sqlParam2, iswaimai);
        if (!StringUtils.isEmpty(allSql)) {
            if (InnConstantODPSTables.TaskHospitalRestaurantDistance.HospitalRestaurant == type) {
                sqlParam = "";
            } else {
                if (sqlParam.contains("or")) {
                    sqlParam = " where 1=1 " + sqlParam;
                }
            }
            update = update(allSql, tableName, allTalle, allComapre, sqlParam);
        }
        return update;
    }

    /** 清空表内容 */
    public Boolean clearODPSTable(String tableName) throws OdpsException, IOException {
        String sql = "TRUNCATE TABLE  %s ";
        return update(sql, tableName);
    }

    /** 删除表 */
    public Boolean deleteODPSTable(String tableName) throws OdpsException, IOException {
        String sql = "DROP TABLE IF EXISTS %s ";
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
