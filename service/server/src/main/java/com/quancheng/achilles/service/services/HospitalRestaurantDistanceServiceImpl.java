package com.quancheng.achilles.service.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.odps.OdpsException;
import com.github.pagehelper.PageInfo;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;
import com.quancheng.achilles.dao.odps.model.OutHospitalRestaurantDistance;
import com.quancheng.achilles.dao.odps.model.RestaurantInfo;
import com.quancheng.achilles.service.odps.ODPSQueryService;
import com.quancheng.achilles.util.JsonUtil;
import com.quancheng.achilles.util.UtilClassHelper;

/**
 * <strong>描述：医院餐厅距离计算service</strong>TODO 描述 <br>
 * <strong>功能：</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author user 2017年6月15日 下午3:54:42
 * @version $Id: HospitalRestaurantDistanceServiceImpl.java, v 0.0.1 2017年6月15日 下午3:54:42 user Exp $
 */

@Service
public class HospitalRestaurantDistanceServiceImpl implements HospitalRestaurantDistanceService {
//    private static final QcLog logger = LogUtil.getLogger(HospitalRestaurantDistanceServiceImpl.class);

    @Qualifier("statisticsSqlSessionTemplate")
    @Resource(name = "statisticsSqlSessionTemplate")
    private SqlSessionTemplate sqlSession;

    @Resource
    private ODPSQueryService   odpsService;
    @Autowired
    private BaseService        baseService;

    
    public <T> Boolean saveHospitalInfoToDB(List<T> datas) {
        int insert = sqlSession.insert("HospitalInfoMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    public <T> Boolean saveRestaurantInfoToDB(List<T> datas) {
        int insert = sqlSession.insert("RestaurantInfoMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    public <T> Boolean saveInfoToDB(InnConstantODPSTables.HospitalRestaurantType type, List<T> datas) {
        boolean result = false;
        switch (type) {
            case Hospital:
                result = saveHospitalInfoToDB(datas);
                break;
            case Restaurant:
                result = saveRestaurantInfoToDB(datas);
                break;
            default:
                break;
        }
        return result;
    }

    public <T> Boolean saveInfoToDB(T t, List<T> datas) {
        boolean result = false;
        if (t instanceof HospitalInfo) {
            result = saveHospitalInfoToDB(datas);
        } else if (t instanceof RestaurantInfo) {
            result = saveRestaurantInfoToDB(datas);
        }
        return result;
    }

    public Boolean saveOutHospitalRestaurantDistanceToDB(List<Map<String, Object>> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return true;
        }
        int insert = sqlSession.insert("OutHospitalRestaurantDistanceMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    @Override
    public PageInfo<OutHospitalRestaurantDistance> queryOutHospitalRestaurantDistanceFromDB(Map<String, Object> param,
                                                                                            Integer pageNum,
                                                                                            Integer pageSize) {
        return baseService.queryFromDB("OutHospitalRestaurantDistanceMapper.queryInfo", param, pageNum, pageSize);
    }

    public Map<String, String> queryCityInfoMap() {
        List<Map<String, Object>> selectList = queryCityInfo();
        Map<String, String> map = new HashMap<>();
        for (Map<String, Object> m : selectList) {
            map.put(m.get("name") + "", m.get("id") + "");
        }

        return map;
    }

    @Override
    public List<Map<String, Object>> queryCityInfo() {
        List<Map<String, Object>> selectList = sqlSession.selectList("OutHospitalRestaurantDistanceMapper.queryCityInfo");
        return selectList;
    }

    @Override
    public List<Map<String, String>> queryCompanyInfo() {
        Map<String, String> param = new HashMap<>();
        return queryCompanyInfo(param);
    }

    public List<Map<String, String>> queryCompanyInfo(Map<String, String> param) {
        List<Map<String, String>> selectList = sqlSession.selectList("OutHospitalRestaurantDistanceMapper.queryCompanyInfo",
                                                                     param);
        return selectList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Boolean saveExcelToDB(MultipartFile file, T type, String companyId,
                                     Boolean clearTable) throws IOException {
        if (clearTable != null && clearTable) {
            if (type instanceof HospitalInfo) {
                clearHospitalInfo();
            } else if (type instanceof RestaurantInfo) {
                clearRestaurantInfo();// 清空表
            }
        }

        List<Map<String, String>> mapsFromExcel = UtilClassHelper.getAttributeMapsFromExcel(file);
        if (CollectionUtils.isEmpty(mapsFromExcel)) {
            return false;
        }
        Map<String, String> cityInfo = queryCityInfoMap();// 获取城市信息

        List<T> list = new ArrayList<>();
        String companyName = null;
        if (!StringUtils.isEmpty(companyId)) {
            Map<String, String> param = new HashMap<>();
            param.put("companyId", companyId);
            List<Map<String, String>> queryCompanyInfo = queryCompanyInfo(param);
            if (!CollectionUtils.isEmpty(queryCompanyInfo)) {
                Map<String, String> companyInfoMap = queryCompanyInfo.get(0);
                companyName = companyInfoMap.get("companyName");
            }
        }
        for (Map<String, String> map : mapsFromExcel) {
            Map<String, String> mapParam = new HashMap<>();
            mapParam.put("name", StringUtils.isEmpty(map.get("hospitalName")) ? map.get("restaurantName") : map.get("hospitalName"));
            String cityName = map.get("cityName").replaceAll("直辖", "").replaceAll("市", "");
            mapParam.put("cityName", cityName);
            String address = map.get("address");
            mapParam.put("address", StringUtils.isEmpty(address) ? "" : address);
            String idName = "";
            if (type instanceof HospitalInfo) {
                idName = "hospitalId";
            } else if (type instanceof RestaurantInfo) {
                idName = "restaurantId";
            }
            String idVal = map.get(idName);
            if (idVal == null || idVal.trim().equals("")) {
                map.put(idName, UUID.randomUUID().toString().replaceAll("-", ""));
            }
            //有对应id直接查线上坐标
            if(map.containsKey("lng")&&map.containsKey("lat")&&map.get("lng")!=null&&map.get("lat")!=null) {
                map.put("lng",  map.get("lng"));
                map.put("lat",  map.get("lat"));
            }else {
                Map<String, Double> mapLocation = UtilClassHelper.getLatAndLngByAddressFromBaidu(mapParam);
                if (mapLocation != null) {
                    Double lng = mapLocation.get("lng");
                    Double lat = mapLocation.get("lat");
                    if (lng != null && lat != null) {
                        map.put("lng", mapLocation.get("lng").toString());
                        map.put("lat", mapLocation.get("lat").toString());
                        map.put("settable", "1");
                    } else {
                        map.put("settable", "0");
                    }
                }
            }
            String cityId = cityInfo.get(cityName);
            if (!StringUtils.isEmpty(cityId)) {
                map.put("cityId", cityId);
            }
            if (!StringUtils.isEmpty(companyName)) {
                map.put("companyName", companyName);
            }
            if (!StringUtils.isEmpty(companyId)) {
                map.put("companyId", companyId);
            }
            

            map.put("companyId", companyId);
            // String jsonString = JSON.toJSONString(map);
            String jsonString = JsonUtil.objectToJsonByGson(map);
            try {
                type = (T) JsonUtil.jsonToObjectBy(jsonString, type.getClass());
                list.add(type);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
//                logger.error("jsonToObjectBy have a error {}", e);
            }

        }
        return saveInfoToDB(type, list);
    }

    @Override
    public Boolean clearHospitalInfo() {
        return baseService.clearTable("hospital_info");
    }

    @Override
    public Boolean clearRestaurantInfo() {
        return baseService.clearTable("restaurant_info");
    }

    @Override
    public Boolean queryAndSaveHospitalInfoToDB(Map<String, String> param, Boolean clearTable) {
        if (clearTable != null && clearTable) {
            clearHospitalInfo(); // 清空表
        }
        int insert = sqlSession.insert("HospitalInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean queryAndSaveRestaurantInfoToDB(Map<String, String> param, Boolean clearTable) {
        if (clearTable != null && clearTable) {
            clearRestaurantInfo();// 清空表
        }
        int insert = sqlSession.insert("RestaurantInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName, Boolean isDelete) throws OdpsException, IOException, ParseException,
                                                                      ExecutionException, TimeoutException {
        return syncDBToODPS(dbTableName, dbTableName, isDelete);
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName, String odpsTableName,
                                Boolean isDelete) throws OdpsException, IOException, ParseException, ExecutionException,
                                                  TimeoutException {
        boolean result = true;
        int pageSize = 20000;
        if (isDelete != null && isDelete) {
            odpsService.clearODPSTable(odpsTableName);
        }

        PageInfo<Map<String, Object>> queryFromDB = baseService.queryFromDB(dbTableName, 1, pageSize);

        List<Map<String, Object>> datas = queryFromDB.getList();
        if (!CollectionUtils.isEmpty(datas)) {
            result = odpsService.insertToOdps(odpsTableName, datas);
        }
        int pages = queryFromDB.getPages();
        if (pages > 1) {
            for (int i = 2; i <= pages; i++) {
                queryFromDB = baseService.queryFromDB(dbTableName, i, pageSize);
                datas = queryFromDB.getList();
                if (!CollectionUtils.isEmpty(datas)) {
                    result = odpsService.insertToOdps(odpsTableName, datas);
                }
            }
        }
        return result;
    }

    @Override
    public <T> Boolean invokeODPSTask(String uuid, T otype, InnConstantODPSTables.TaskHospitalRestaurantDistance type,
                                      Boolean compareCompany, Double distances, Boolean isWaimaiOk, String sqlParam,
                                      Boolean isIncludeSpecial) throws OdpsException, IOException {
        return odpsService.taskHospitalRestaurantDistance(uuid, otype, type, compareCompany, distances, isWaimaiOk,
                                                          sqlParam, isIncludeSpecial);
    }

    @Override
    public Boolean queryFromODPSAndSaveToDB(String uuid) throws OdpsException, IOException, TimeoutException {
        String tableName = "out_hospital_restaurant_distance";
        baseService.clearTable(tableName);// 清空表
        return odpsService.getAllAndSaveToDB(tableName + "_" + uuid, (dataList) -> {
            return saveOutHospitalRestaurantDistanceToDB(dataList);// 保存到DB
        });
    }

    @Override
    public Boolean deleteODPSTable(String ODPSTableName) throws OdpsException, IOException {
        return odpsService.deleteODPSTable(ODPSTableName);
    }

}
