package com.quancheng.achilles.service.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.odps.OdpsException;
import com.github.pagehelper.PageInfo;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.model.OutHospitalRestaurantDistance;

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
    Boolean clearHospitalInfo();

    /** Excel信息到DB */
    <T> Boolean saveExcelToDB(MultipartFile file, T type, String companyId, Boolean clearTable) throws IOException;

    /** 清空餐厅信息表 */
    Boolean clearRestaurantInfo();

    /** 查询并且保存医院信息到DB */
    Boolean queryAndSaveHospitalInfoToDB(Map<String, String> param, Boolean clearTable);

    /** 查询并且保存餐厅信息到DB */
    Boolean queryAndSaveRestaurantInfoToDB(Map<String, String> param, Boolean clearTable);

    /** 同步DB表信息到ODPS */
    Boolean syncDBToODPS(String tableName, Boolean isDelete) throws OdpsException, IOException, ParseException,
                                                             ExecutionException, TimeoutException;

    /** 同步DB表信息到ODPS */
    Boolean syncDBToODPS(String dbTableName, String odpsTableName, Boolean isDelete) throws OdpsException, IOException,
                                                                                     ParseException, ExecutionException,
                                                                                     TimeoutException;

    /**
     * 调用ODPS执行计算
     * 
     * @param <T>
     */
    <T> Boolean invokeODPSTask(String uuid, T otype, InnConstantODPSTables.TaskHospitalRestaurantDistance type,
                               Boolean compareCompany, Double distances, Boolean isWaimaiOk,
                               String sqlParam) throws OdpsException, IOException;

    /** 从ODPS拿到结果保存到DB */
    Boolean queryFromODPSAndSaveToDB(String uuid) throws OdpsException, IOException, TimeoutException;

    /** 获取城市列表 */
    List<Map<String, Object>> queryCityInfo();

    /** 获取公司列表 */
    List<Map<String, String>> queryCompanyInfo();

    /** 获取结果表数据 */
    PageInfo<OutHospitalRestaurantDistance> queryOutHospitalRestaurantDistanceFromDB(Map<String, Object> param,
                                                                                     Integer pageNum, Integer pageSize);

    /** 删除表 */
    Boolean deleteODPSTable(String ODPSTableName);

}
