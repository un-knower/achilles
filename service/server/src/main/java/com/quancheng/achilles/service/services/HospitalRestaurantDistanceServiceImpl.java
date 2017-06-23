package com.quancheng.achilles.service.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aliyun.odps.OdpsException;
import com.github.pagehelper.PageInfo;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.service.odps.ODPSQueryService;

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
    private SqlSessionTemplate sqlSession;

    @Resource
    private ODPSQueryService   odpsService;
    @Autowired
    private BaseService        baseService;

    public Boolean saveHospatalInfoToDB(List<Object> datas) {
        int insert = sqlSession.insert("HospitalInfoMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    public Boolean saveRestaurantInfoToDB(List<Object> datas) {
        int insert = sqlSession.insert("RestaurantInfoMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    @SuppressWarnings("rawtypes")
    public Boolean saveOutHospatalRestaurantDistanceToDB(List<Map> datas) {
        int insert = sqlSession.insert("OutHospatalRestaurantDistanceMapper.batchInsert", datas);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean clearHospatalInfo() {
        return baseService.clearTable("hospatal_info");
    }

    @Override
    public Boolean saveExcelToDB(String tableName) {
        // TODO Auto-generated method stub
        // Map<String, Double> mapLocation = UtilClassHelper.getLatAndLngByAddressFromBaidu(item.getAddress(),
        // item.getCity().replace("市",
        // ""));
        // if (mapLocation == null || mapLocation.get("lat") == null || "".equals(mapLocation.get("lat"))) {
        // mapLocation = UtilClassHelper.getLatAndLngByAddressFromBaidu(item.getName(),
        // item.getCity().replace("市", ""));
        // }
        // map.put("lng", mapLocation.get("lng"));
        // map.put("lat", mapLocation.get("lat"));
        return null;
    }

    @Override
    public Boolean clearRestaurantInfo() {
        return baseService.clearTable("restaurant_info");
    }

    @Override
    public Boolean queryAndSaveHospatalInfoToDB(Map<String, String> param) {
        int insert = sqlSession.insert("HospitalInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean queryAndSaveRestaurantInfoToDB(Map<String, String> param) {
        int insert = sqlSession.insert("RestaurantInfoMapper.queryAndSaveToDB", param);
        return insert > 0 ? true : false;
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName) throws OdpsException, IOException, ParseException {
        return syncDBToODPS(dbTableName, dbTableName);
    }

    @Override
    public Boolean syncDBToODPS(String dbTableName, String odpsTableName) throws OdpsException, IOException,
                                                                          ParseException {
        boolean result = true;
        int pageSize = 5000;
        odpsService.clearODPSTable(odpsTableName);
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
    public Boolean invokeODPSTask(InnConstantODPSTables.TaskHospatalRestaurantDistance type, double distances,
                                  boolean compareCompany) throws OdpsException, IOException {
        boolean task = odpsService.taskHospatalRestaurantDistance(type, distances, compareCompany);
        return task;
    }

    @Override
    public Boolean queryFromODPSAndSaveToDB() throws OdpsException, IOException {
        int pageSize = 8000;
        String tableName = "out_hospatal_restaurant_distance";
        Boolean clearTable = baseService.clearTable(tableName);// 清空表
        if (clearTable) {
        }
        List<Map> datas;
        int pageNum = 1;
        do {
            datas = odpsService.queryFromODPS(tableName, pageNum, pageSize);// 从ODPS拿数据
            Boolean save = saveOutHospatalRestaurantDistanceToDB(datas);// 保存到DB
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
