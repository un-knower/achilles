package com.quancheng.achilles.service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
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
 * @author user 2017年6月15日 下午3:54:42
 * @version $Id: HospitalRestaurantDistanceServiceImpl.java, v 0.0.1 2017年6月15日 下午3:54:42 user Exp $
 */

@Service
public class BaseServiceImpl implements BaseService {

    @Qualifier("statisticsSqlSessionTemplate")
    @Resource(name = "statisticsSqlSessionTemplate")
    private SqlSessionTemplate sqlSession;

    @Override
    public PageInfo<Map<String, Object>> queryFromDB(String tableName, Integer pageNum, Integer pageSize) {
        Map<String, String> sqlParam = new HashMap<>();
        sqlParam.put("tableName", tableName);
        PageHelper.startPage(pageNum, pageSize, true);
        List<Map<String, Object>> selectList = sqlSession.selectList("BaseMapper.queryAll", sqlParam);
        return new PageInfo<Map<String, Object>>(selectList);
    }

    @Override
    public PageInfo<Map<String, Object>> queryFromDB(String sql, Object[] params, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(sql)) {
            return null;
        }
        if (params != null) {
            sql = String.format(sql, params);
        }

        Map<String, String> sqlParam = new HashMap<>();
        sqlParam.put("sqlString", sql);
        PageHelper.startPage(pageNum, pageSize, true);
        List<Map<String, Object>> selectList = sqlSession.selectList("BaseMapper.queryBySql", sqlParam);
        return new PageInfo<Map<String, Object>>(selectList);
    }

    @Override
    public <T> PageInfo<T> queryFromDB(String statement, Object parameter, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<T> selectList = sqlSession.selectList(statement, parameter);
        return new PageInfo<T>(selectList);
    }

    @Override
    public Boolean clearTable(String tableName) {
        Map<String, String> sqlParam = new HashMap<>();
        sqlParam.put("tableName", tableName);
        int update = sqlSession.update("BaseMapper.truncateTable", sqlParam);
        return update > 0 ? true : false;
    }
}
