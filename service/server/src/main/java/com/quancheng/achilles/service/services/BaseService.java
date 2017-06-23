package com.quancheng.achilles.service.services;

import java.util.Map;

import com.github.pagehelper.PageInfo;

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
public interface BaseService {

    /** 分页查询表 */
    PageInfo<Map<String, Object>> queryFromDB(String tableName, Integer pageNum, Integer pageSize);

    /** 分页查询表 */
    PageInfo<Map<String, Object>> queryFromDB(String sql, Object[] params, Integer pageNum, Integer pageSize);

    /** 分页查询表 */
    <T> PageInfo<T> queryFromDB(String statement, Object parameter, Integer pageNum, Integer pageSize);

    /** 清空表内容 */
    Boolean clearTable(String tableName);

}
