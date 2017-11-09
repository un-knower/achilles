package com.quancheng.achilles.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quancheng.achilles.dao.ds_qc.model.CompanyRestaurantsStatistics;
import com.quancheng.achilles.dao.ds_qc.repository.CompanyReportRepository;
import com.quancheng.achilles.service.utils.CoverageUtil;

/**
 * <strong>描述：</strong>TODO 描述 <br>
 * <strong>功能：</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author user 2017年1月12日 下午6:44:16
 * @version $Id: CompanyReportServiceImpl.java, v 0.0.1 2017年1月12日 下午6:44:16 user Exp $
 */
@Service
@Transactional(readOnly = true)
public class CompanyReportServiceImpl implements CompanyReportService {

    private Logger          log = LoggerFactory.getLogger(CompanyReportServiceImpl.class);
//    @Autowired
    CompanyReportRepository companyReport;

    @Override
    public boolean queryAndSaveCompanyCityRestaurantByTime(String time) {
        boolean saveCompanyCityRestaurant = false;
        try {
            CompanyRestaurantsStatistics beforParam = new CompanyRestaurantsStatistics();
            beforParam.setTime(time);
            List<CompanyRestaurantsStatistics> beforQuery = companyReport.queryCompanyCityStatisticsBy(beforParam);// 查看是否数据已存在
            if (!CollectionUtils.isEmpty(beforQuery)) {
                return true;// 数据已存在
            }
//            String lastDayOfMonth = TimeUtil.formatDate("yyyy-MM-dd",
//                                                        TimeUtil.getLastDayOfMonth(TimeUtil.parseDate("yyyy-MM",
//                                                                                                      time)));
            List<CompanyRestaurantsStatistics> companyCityRestaurantList = companyReport.queryCompanyCityRestaurantByTime(time
                                                                                                                          + "-01");
            if (!CollectionUtils.isEmpty(companyCityRestaurantList)) {
                for (CompanyRestaurantsStatistics c : companyCityRestaurantList) {
                    c.setTime(time);
                }
                saveCompanyCityRestaurant = companyReport.saveCompanyCityRestaurant(companyCityRestaurantList);
            }
        } catch (Exception e) {
            log.error("queryAndSaveCompanyCityRestaurantByTime have a error {}", e);
        }
        return saveCompanyCityRestaurant;
    }

    @Override
    public List<CompanyRestaurantsStatistics> queryCompanyCityStatisticsBy(CompanyRestaurantsStatistics param) {
        List<CompanyRestaurantsStatistics> queryCompanyCityStatisticsBy = null;
        try {
            queryCompanyCityStatisticsBy = companyReport.queryCompanyCityStatisticsBy(param);
        } catch (Exception e) {
            log.error("queryCompanyCityStatisticsBy have a error {}", e);
        }
        return queryCompanyCityStatisticsBy;
    }

    @Override
    public CompanyRestaurantsStatistics queryCompanyRestaurantsStatisticsByTime(String time, Integer companyId) {
        CompanyRestaurantsStatistics queryCompanyStatistics = null;
        try {
            queryCompanyStatistics = companyReport.queryCompanyRestaurantsStatisticsByTime(time, companyId);
        } catch (Exception e) {
            log.error("queryCompanyStatisticsByTime have a error {}", e);
        }
        return queryCompanyStatistics;
    }

    @Override
    public Integer queryCompanyAllShelfNumByTime(String time, Integer companyId) {
        Integer allShelfNum = null;
        try {
            allShelfNum = companyReport.queryCompanyAllShelfNumByTime(time, companyId);
        } catch (Exception e) {
            log.error("queryCompanyAllShelfNumByTime have a error {}", e);
        }
        return allShelfNum;
    }

    @Override
    public Integer queryCompanyAllSourceNumByTime(String time, Integer companyId) {
        Integer allSourceNum = null;
        try {
            allSourceNum = companyReport.queryCompanyAllSourceNumByTime(time, companyId);
        } catch (Exception e) {
            log.error("queryCompanyAllSourceNumByTime have a error {}", e);
        }
        return allSourceNum;
    }

    @Override
    public boolean queryAndSaveCompanyShelfRestaurantByTime(String time) {
        boolean saveResult = false;
        try {
            Map<String, Object> beforParam = new HashMap<>();
            beforParam.put("time", time);
            List<Map<String, Object>> beforQuery = companyReport.queryCompanyShelfNumInfoBy(beforParam);// 查看是否数据已存在
            if (!CollectionUtils.isEmpty(beforQuery)) {
                return true;// 数据已存在
            }
            List<Map<String, Object>> list = companyReport.queryCompanyShelfRestaurantByTime(time);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> c : list) {
                    c.put("time", time);
                }
                saveResult = companyReport.saveCompanyShelfRestaurants(list);
                companyReport.deleteCompanyShelfRestaurantsEmptyByTime(time);// 处理公司餐厅下架原因统计信息全为0的公司统计信息
            }
        } catch (Exception e) {
            log.error("queryAndSaveCompanyShelfRestaurantByTime have a error {}", e);
        }
        return saveResult;
    }

    @Override
    public boolean queryAndSaveCompanyRestaurantSourceByTime(String time) {
        boolean saveResult = false;
        try {
            Map<String, Object> beforParam = new HashMap<>();
            beforParam.put("time", time);
            List<Map<String, Object>> beforQuery = companyReport.queryCompanySourceNumInfoBy(beforParam);// 查看是否数据已存在
            if (!CollectionUtils.isEmpty(beforQuery)) {
                return true;// 数据已存在
            }
            List<Map<String, Object>> list = companyReport.queryCompanyRestaurantSourceByTime(time);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> c : list) {
                    c.put("time", time);
                }
                saveResult = companyReport.saveCompanyRestaurantsSource(list);
            }
        } catch (Exception e) {
            log.error("queryAndSaveCompanyRestaurantSourceByTime have a error {}", e);
        }
        return saveResult;
    }

    /** 获取经纬度坐标 */
    public static Map<String, Double> getLatAndLng(Map<String, Object> map) {
        Map<String, Double> resultMap = new HashMap<>();
        Object lngO = map.get("lng");
        Object latO = map.get("lat");
        if (lngO == null || latO == null || (lngO != null && "".equals(lngO.toString()))
            || (latO != null && "".equals(latO.toString()))) {
            resultMap = CoverageUtil.getLatAndLngByAddressFromBaidu(map.get("address").toString(),
                                                                    map.get("city_name").toString());// 尝试从百度API获取经纬度
        } else {
            double lng = Double.parseDouble(lngO.toString());
            double lat = Double.parseDouble(latO.toString());
            resultMap.put("lng", lng);
            resultMap.put("lat", lat);
        }
        return resultMap;
    }

    /** 获取距离类型，小于distance[]里某个范围的类型 */
    public static String getDistanceType(double distance_result, Integer... distance) {
        Arrays.sort(distance);// 排序
        String distanceType = null;
        for (int i = 0; i < distance.length; i++) {
            if (0 <= distance_result && distance_result <= distance[i]) {
                distanceType = String.valueOf(distance[i]);
                break;
            }
        }
        return distanceType;
    }

    /** 获取公司医院覆盖餐厅数统计信息 */
    public static Map<String, Map<String, Integer>> getCompanyHospitalRestaurantStatistics(HashSet<String> companyList,
                                                                                           List<Map<String, Object>> hospitalList,
                                                                                           List<Map<String, Object>> restaurantList,
                                                                                           Integer... distance) {

        Map<String, List<Map<String, Object>>> company_city_map = new HashMap<>();
        for (Map<String, Object> rl : restaurantList) {// 按公司城市分类
            String r_company_id = rl.get("company_id").toString();
            String r_city_name = rl.get("city_name").toString();
            String key = r_company_id + "#" + r_city_name;
            List<Map<String, Object>> list = company_city_map.get(key);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(rl);
            company_city_map.put(key, list);
        }

        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        for (Map<String, Object> hl : hospitalList) {
            Map<String, Double> hl_latAndLng = getLatAndLng(hl);
            if (hl_latAndLng == null) {
                continue;
            }
            Double hl_lng = hl_latAndLng.get("lng");
            Double hl_lat = hl_latAndLng.get("lat");
            String company_id = hl.get("company_id").toString();
            String company_name = hl.get("company_name").toString();
            String city_name = hl.get("city_name").toString();
            String hospital_name = hl.get("hospital_name").toString();
            String keyBase = company_id + "#" + company_name + "#" + hospital_name;
            Map<String, Integer> sMap = new HashMap<>();
            for (Integer d : distance) {
                sMap.put(d + "#1", 0);// 0：未知类型1：订餐餐厅2：外卖餐厅
                sMap.put(d + "#2", 0);
            }
            resultMap.put(keyBase, sMap);
            companyList.add(company_id + "#" + company_name);
            List<Map<String, Object>> companyCityRestaurantList = company_city_map.get(company_id + "#" + city_name);
            if (companyCityRestaurantList == null) {// 医院不存在同公司城市的餐厅
                continue;
            }
            Map<String, Integer> tempMap = resultMap.get(keyBase);
            for (Map<String, Object> rl : companyCityRestaurantList) {
                Map<String, Double> rl_latAndLng = getLatAndLng(rl);
                if (rl_latAndLng == null) {
                    continue;
                }
                Double rl_lng = rl_latAndLng.get("lng");
                Double rl_lat = rl_latAndLng.get("lat");
                double distance_result = CoverageUtil.getDistance(rl_lng, rl_lat, hl_lng, hl_lat);
                String distanceType = getDistanceType(distance_result, distance);
                if (distanceType != null) {
                    String support_takeout_of_food = rl.get("support_takeout_of_food").toString();
                    String support_reserve = rl.get("support_reserve").toString();
                    if ("1".equals(support_takeout_of_food)) {
                        Integer num = tempMap.get(distanceType + "#2");// 0：未知类型1：订餐餐厅2：外卖餐厅
                        tempMap.put(distanceType + "#2", num + 1);
                    }
                    if ("1".equals(support_reserve)) {
                        Integer num = tempMap.get(distanceType + "#1");
                        tempMap.put(distanceType + "#1", num + 1);
                    }
                } else {
                    continue;
                }
            }
        }
        Arrays.sort(distance);// 排序
        for (Map.Entry<String, Map<String, Integer>> entry : resultMap.entrySet()) {// 处理大范围覆盖小范围，引起的数据包含问题，即：小范围的值需要加到到范围中
            Map<String, Integer> valueMap = entry.getValue();
            for (int i = distance.length - 1; i >= 0; i--) {
                int num1 = valueMap.get(distance[i] + "#1");// 0：未知类型1：订餐餐厅2：外卖餐厅
                int num2 = valueMap.get(distance[i] + "#2");
                for (int j = i - 1; j >= 0; j--) {
                    num1 = num1 + valueMap.get(distance[j] + "#1");
                    num2 = num2 + valueMap.get(distance[j] + "#2");
                }
                valueMap.put(distance[i] + "#1", num1);// 0：未知类型1：订餐餐厅2：外卖餐厅
                valueMap.put(distance[i] + "#2", num2);
            }
        }
        return resultMap;
    }

    /** 获取餐厅数量类型 */
    public static String getRestaurantNumType(Integer num) {
        String type = null;
        switch (num) {
            case 0:
                type = "未覆盖";
                break;
            case 1:
                type = "1家";
                break;
            case 2:
                type = "2家";
                break;
            case 3:
                type = "3家";
                break;

            default:
                if (4 <= num && num <= 9) {
                    type = "4-9家";
                }
                if (10 <= num && num <= 29) {
                    type = "10-29家";
                }
                if (30 <= num) {
                    type = "30家以上";
                }
                break;
        }
        return type;
    }

    /** 处理获取公司医院覆盖餐厅数统计信息 */
    public static Map<String, Integer> handleCompanyHospitalRestaurantStatistics(HashSet<String> companyList,
                                                                                 Map<String, Map<String, Integer>> companyHospitalRestaurantStatistics,
                                                                                 Integer... distance) {
        Map<String, Integer> sMap = new HashMap<>();
        for (String company : companyList) {
            String[] restaurantNumType = { "未覆盖", "1家", "2家", "3家", "4-9家", "10-29家", "30家以上" };
            for (Integer d : distance) {
                for (String rnt : restaurantNumType) {
                    sMap.put(company + "#" + d + "#1#" + rnt, 0);
                    sMap.put(company + "#" + d + "#2#" + rnt, 0);
                }
            }
        }
        for (Map.Entry<String, Map<String, Integer>> entry : companyHospitalRestaurantStatistics.entrySet()) {
            String key = entry.getKey();
            String[] split = key.split("#");
            String baseKey = split[0] + "#" + split[1];

            Map<String, Integer> valueMap = entry.getValue();
            for (Map.Entry<String, Integer> sEntry : valueMap.entrySet()) {
                String skey = sEntry.getKey();
                Integer svalue = sEntry.getValue();
                String sRestaurantNumType = getRestaurantNumType(svalue);
                String tempKey = baseKey + "#" + skey + "#" + sRestaurantNumType;
                Integer num = sMap.get(tempKey);
                sMap.put(tempKey, num + 1);
            }
        }
        return sMap;

    }

    /** 转化医院餐厅统计信息结构 */
    public static List<Map<String, Object>> converHospitalRestaurantStatisticsMapToList(Map<String, Integer> map,
                                                                                        String time) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            String[] split = key.split("#");
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("time", time);
            tempMap.put("companyId", split[0]);
            tempMap.put("companyName", split[1]);
            tempMap.put("distance", split[2]);
            tempMap.put("restaurantType", split[3]);
            tempMap.put("restaurantNumType", split[4]);
            tempMap.put("hospitalNum", value);
            list.add(tempMap);
        }
        return list;
    }

    // 创建一个线程池
    ExecutorService pool = Executors.newFixedThreadPool(2);

    /** 获取餐厅数目类型排序值 */
    private Integer getRestaurantNumTypeIndex(String restaurantNumType) {
        Integer r = 0;
        switch (restaurantNumType) {
            case "未覆盖":
                r = 0;
                break;
            case "1家":
                r = 1;
                break;
            case "2家":
                r = 2;
                break;
            case "3家":
                r = 3;
                break;
            case "4-9家":
                r = 4;
                break;
            case "10-29家":
                r = 5;
                break;
            default:
                r = 6;
                break;
        }
        return r;
    }

    @Override
    public boolean queryAndSaveCompanyHospitalRestaurantStatistics(String time, Integer... distance) {
        boolean saveResult = false;
        try {
            Map<String, Object> beforParam = new HashMap<>();
            beforParam.put("time", time);
            Integer beforQuery = companyReport.queryCompanyHospitalRestaurantStatisticsCountBy(beforParam);// 查看是否数据已存在
            if (beforQuery != null && beforQuery > 0) {
                return true;// 数据已存在
            }
            beforParam.put("selectContent", "distinct c.company_id,c.company_name");
            List<Map<String, Object>> hospitalCityList = companyReport.queryHospitalContentBy(beforParam);
            Map<String, Object> param = new HashMap<>();
            beforParam.put("time", time + "-01");
            List<Map<String, Object>> hospitalList = null;
            List<Map<String, Object>> restaurantList = null;
            // Future fu;
            for (Map<String, Object> hcity : hospitalCityList) {// 根据城市分批次计算
                param.put("companyId", hcity.get("company_id"));
                hospitalList = companyReport.queryCompanyHospitalInfoByCompanyIdAndTime(param);
                restaurantList = companyReport.queryCompanyRestaurantInfoByCompanyIdAndTime(param);
                HashSet<String> companyList = new HashSet<String>();
                Map<String, Map<String, Integer>> companyHospitalRestaurantStatistics = getCompanyHospitalRestaurantStatistics(companyList,
                                                                                                                               hospitalList,
                                                                                                                               restaurantList,
                                                                                                                               distance);
                Map<String, Integer> map = handleCompanyHospitalRestaurantStatistics(companyList,
                                                                                     companyHospitalRestaurantStatistics,
                                                                                     distance);
                List<Map<String, Object>> list = converHospitalRestaurantStatisticsMapToList(map, time);

                // fu = pool.submit(new HandelCompanyHospitalRestaurantRun(time, companyList, hospitalList,
                // restaurantList,
                // distance));
                // List<Map<String, Object>> list = (List<Map<String, Object>>) fu.get();
                Collections.sort(list, new Comparator<Map<String, Object>>() {

                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String key1 = o1.get("time").toString() + o1.get("companyId") + o1.get("distance")
                                      + getRestaurantNumTypeIndex(o1.get("restaurantNumType").toString())
                                      + o1.get("restaurantType");
                        String key2 = o2.get("time").toString() + o2.get("companyId") + o2.get("distance")
                                      + getRestaurantNumTypeIndex(o2.get("restaurantNumType").toString())
                                      + o2.get("restaurantType");
                        return key1.compareTo(key2);
                    }
                });

                saveResult = companyReport.saveCompanyHospitalRestaurantStatistics(list);
            }

        } catch (Exception e) {
            log.error("queryCompanyHospitalRestaurantStatistics have a error {}", e);
            Map<String, Object> param = new HashMap<>();
            param.put("time", time);
            try {
                companyReport.deleteCompanyHospitalRestaurantStatisticsCountBy(param);
            } catch (Exception e1) {
                log.error("deleteCompanyHospitalRestaurantStatisticsCountBy have a error {}", e);
            }
        }
        return saveResult;
    }

    @Override
    public boolean queryCompanyHospitalRestaurantStatistics(Map<String, Object> param, Integer... distance) {
        try {
//            List<Map<String, Object>> hospitalCityList = companyReport.queryCompanyHospitalInfoByCompanyIdAndTime(param);
        } catch (Exception e) {
            log.error("queryCompanyHospitalInfoByCompanyIdAndTime have a error {}", e);
        }
        return false;
    }

}

// @SuppressWarnings("rawtypes")
// class HandelCompanyHospitalRestaurantRun implements Callable {
//
// HashSet<String> companyList;
// List<Map<String, Object>> hospitalList;
// List<Map<String, Object>> restaurantList;
// Integer[] distance;
// String time;
//
// HandelCompanyHospitalRestaurantRun(String time, HashSet<String> companyList, List<Map<String, Object>> hospitalList,
// List<Map<String, Object>> restaurantList, Integer... distance){
// this.companyList = companyList;
// this.hospitalList = hospitalList;
// this.restaurantList = restaurantList;
// this.distance = distance;
// this.time = time;
// }
//
// @Override
// public List<Map<String, Object>> call() throws Exception {
// Map<String, Map<String, Integer>> companyHospitalRestaurantStatistics =
// CompanyReportServiceImpl.getCompanyHospitalRestaurantStatistics(companyList,
// hospitalList,
// restaurantList,
// distance);
// Map<String, Integer> map = CompanyReportServiceImpl.handleCompanyHospitalRestaurantStatistics(companyList,
// companyHospitalRestaurantStatistics,
// distance);
// List<Map<String, Object>> list = CompanyReportServiceImpl.converHospitalRestaurantStatisticsMapToList(map,
// time);
// return list;
// }
// }
