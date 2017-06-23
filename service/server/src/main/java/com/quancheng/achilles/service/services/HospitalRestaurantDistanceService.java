package com.quancheng.achilles.service.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.aliyun.odps.OdpsException;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;

/**
 * <strong>描述：医院餐厅距离计算service</strong>TODO 描述 <br>
 * <strong>功能：</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author user 2017年6月15日 下午4:10:47
 * @version $Id: HospitalRestaurantDistanceService.java, v 0.0.1 2017年6月15日 下午4:10:47 user Exp $
 */
public interface HospitalRestaurantDistanceService {

    /** 清空医院信息表 */
    Boolean clearHospatalInfo();

    /** Excel信息到DB */
    Boolean saveExcelToDB(String tableName);

    /** 清空餐厅信息表 */
    Boolean clearRestaurantInfo();

    /** 查询并且保存医院信息到DB */
    Boolean queryAndSaveHospatalInfoToDB(Map<String, String> param);

    /** 查询并且保存餐厅信息到DB */
    Boolean queryAndSaveRestaurantInfoToDB(Map<String, String> param);

    /** 同步DB表信息到ODPS */
    Boolean syncDBToODPS(String tableName) throws OdpsException, IOException, ParseException;

    /** 同步DB表信息到ODPS */
    Boolean syncDBToODPS(String dbTableName, String odpsTableName) throws OdpsException, IOException, ParseException;

    /** 调用ODPS执行计算 */
    Boolean invokeODPSTask(InnConstantODPSTables.TaskHospatalRestaurantDistance type, double distances,
                           boolean compareCompany) throws OdpsException, IOException;

    /** 从ODPS拿到结果保存到DB */
    Boolean queryFromODPSAndSaveToDB() throws OdpsException, IOException;

}
