package com.quancheng.achilles.service.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger                     logger = LogManager.getLogger(HospitalRestaurantDistanceServiceImpl.class);
    @Qualifier("statisticsSqlSessionTemplate")
    @Resource(name = "statisticsSqlSessionTemplate")
    private SqlSessionTemplate sqlSession;

    @Resource
    private ODPSQueryService   odpsService;
    @Autowired
    private BaseService        baseService;

    public <T> Boolean saveHospitalInfoToDB(List<T> datas) {
        clearHospitalInfo();
        int insert = sqlSession.insert("HospitalInfoMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    public <T> Boolean saveRestaurantInfoToDB(List<T> datas) {
        clearRestaurantInfo();// 清空表
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

    @SuppressWarnings("rawtypes")
    public Boolean saveOutHospitalRestaurantDistanceToDB(List<Map> datas) {
        int insert = sqlSession.insert("OutHospitalRestaurantDistanceMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    public List<Map<String, String>> queryCompanyInfo(Map<String, String> param) {
        List<Map<String, String>> selectList = sqlSession.selectList("OutHospitalRestaurantDistanceMapper.queryCompanyInfo",
                                                                     param);
        return selectList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Boolean saveExcelToDB(MultipartFile file, T type, String companyId) throws IOException {
        if (type instanceof HospitalInfo) {
            clearHospitalInfo();
        } else if (type instanceof RestaurantInfo) {
            clearRestaurantInfo();// 清空表
        }

        List<Map<String, String>> mapsFromExcel = UtilClassHelper.getAttributeMapsFromExcel(file);
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
            mapParam.put("name",
                         StringUtils.isEmpty(map.get("hospitalName")) ? map.get("restaurantName") : map.get("hospitalName"));
            String cityName = map.get("cityName").replaceAll("直辖", "");
            mapParam.put("cityName", cityName);
            mapParam.put("address", cityName + map.get("address"));
            Map<String, Double> mapLocation = UtilClassHelper.getLatAndLngByAddressFromBaidu(mapParam);
            if (mapLocation != null) {
                map.put("lng", mapLocation.get("lng").toString());
                map.put("lat", mapLocation.get("lat").toString());
                map.put("settable", "1");
            }
            if (!StringUtils.isEmpty(companyName)) {
                map.put("companyName", companyName);
            }
            if (!StringUtils.isEmpty(companyId)) {
                map.put("companyId", companyId);
            }
            // String jsonString = JSON.toJSONString(map);
            String jsonString = JsonUtil.objectToJsonByGson(map);
            try {
                type = (T) JsonUtil.jsonToObjectBy(jsonString, type.getClass());
                list.add(type);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                logger.error("jsonToObjectBy have a error {}", e);
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
    public Boolean queryAndSaveHospitalInfoToDB(Map<String, String> param) {
        int insert = sqlSession.insert("HospitalInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean queryAndSaveRestaurantInfoToDB(Map<String, String> param) {
        // queryInfo
        // baseService.queryFromDB(tableName, pageNum, pageSize)
        int insert = sqlSession.insert("RestaurantInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName) throws OdpsException, IOException, ParseException,
                                                    ExecutionException, TimeoutException {
        return syncDBToODPS(dbTableName, dbTableName);
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName, String odpsTableName) throws OdpsException, IOException,
                                                                          ParseException, ExecutionException,
                                                                          TimeoutException {
        boolean result = true;
        int pageSize = 50000;
        // odpsService.clearODPSTable(odpsTableName);
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
    public Boolean invokeODPSTask(InnConstantODPSTables.TaskHospitalRestaurantDistance type, double distances,
                                  boolean compareCompany) throws OdpsException, IOException {
        boolean task = odpsService.taskHospitalRestaurantDistance(type, distances, compareCompany);
        return task;
    }

    @Override
    public Boolean queryFromODPSAndSaveToDB() throws OdpsException, IOException {
        int pageSize = 20000;
        String tableName = "out_hospital_restaurant_distance";
        Boolean clearTable = baseService.clearTable(tableName);// 清空表
        if (clearTable) {
        }
        List<Map> datas;
        int pageNum = 1;
        do {
            datas = odpsService.queryFromODPS(tableName, pageNum, pageSize);// 从ODPS拿数据
            Boolean save = saveOutHospitalRestaurantDistanceToDB(datas);// 保存到DB
            if (!save) {
                return false;
            }
            if (CollectionUtils.isEmpty(datas)) {
                break;
            }
            pageNum++;
        } while (true);
        return true;
    }

}
