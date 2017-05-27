package com.quancheng.achilles.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.quancheng.achilles.dao.model.CompanyRestaurantsStatistics;

/**
 * <strong>描述：</strong>TODO 描述 <br>
 * <strong>功能：</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author liujun 2017年1月12日 下午6:47:19
 * @version $Id: CompanyReportRepository.java, v 0.0.1 2017年1月12日 下午6:47:19 user Exp $
 */
@Repository
public class CompanyReportRepository {

    @Qualifier("writeSqlSession")
    @Autowired
    private SqlSession sqlSession;

    /** 根据月份查询公司城市餐厅统计信息 */
    public List<CompanyRestaurantsStatistics> queryCompanyCityRestaurantByTime(String time) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        return sqlSession.selectList("CompanyReportMapper.queryCompanyCityRestaurantByTime", param);
    }

    /** 批量保存公司城市餐厅统计信息 */
    public boolean saveCompanyCityRestaurant(List<CompanyRestaurantsStatistics> list) throws Exception {
        int insert = sqlSession.insert("CompanyRestaurantsStatisticsMapper.batchInsert", list);
        return insert > 0 ? true : false;
    }

    /** 根据条件查询公司城市餐厅统计信息 */
    public List<CompanyRestaurantsStatistics> queryCompanyCityStatisticsBy(CompanyRestaurantsStatistics param) throws Exception {
        return sqlSession.selectList("CompanyRestaurantsStatisticsMapper.queryCompanyStatisticsBy", param);
    }

    /** 根据条件查询公司餐厅统计信息 */
    public CompanyRestaurantsStatistics queryCompanyRestaurantsStatisticsByTime(String time,
                                                                                Integer companyId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        if (companyId != null) {
            param.put("companyId", companyId);
        }
        List<CompanyRestaurantsStatistics> selectList = sqlSession.selectList("CompanyRestaurantsStatisticsMapper.queryCompanyStatisticsByTime",
                                                                              param);
        if (!CollectionUtils.isEmpty(selectList)) {
            return selectList.get(0);
        } else {
            return null;
        }
    }

    /** 根据条件查询公司餐厅下架原因统计信息 */
    public List<Map<String, Object>> queryCompanyShelfRestaurantByTime(CompanyRestaurantsStatistics param) throws Exception {
        return sqlSession.selectList("CompanyReportMapper.queryCompanyShelfRestaurantByTime", param);
    }

    /** 根据条件查询月公司餐厅下架数量 */
    public Integer queryCompanyAllShelfNumByTime(String time, Integer companyId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        if (companyId != null) {
            param.put("companyId", companyId);
        }
        return sqlSession.selectOne("CompanyShelfRestaurantsMapper.queryAllShelfNumByTime", param);
    }

    /** 每月餐厅新增不同推荐来源的总数量 */
    public Integer queryCompanyAllSourceNumByTime(String time, Integer companyId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        if (companyId != null) {
            param.put("companyId", companyId);
        }
        return sqlSession.selectOne("CompanyRestaurantsSourceMapper.queryAllSourceNumByTime", param);
    }

    /** 根据条件查询月公司餐厅下架数量信息 */
    public List<Map<String, Object>> queryCompanyShelfNumInfoBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyShelfRestaurantsMapper.queryBy", param);
    }

    /** 根据条件查询月公司餐增加数量信息 */
    public List<Map<String, Object>> queryCompanySourceNumInfoBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyRestaurantsSourceMapper.queryBy", param);
    }

    /** 查询公司餐厅下架原因统计信息 */
    public List<Map<String, Object>> queryCompanyShelfRestaurantByTime(String time) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        return sqlSession.selectList("CompanyReportMapper.queryCompanyShelfRestaurantByTime", param);
    }

    /** 查询公司餐厅推荐信息 */
    public List<Map<String, Object>> queryCompanyRestaurantSourceByTime(String time) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        return sqlSession.selectList("CompanyReportMapper.queryCompanyRestaurantSourceByTime", param);
    }

    /** 批量保存公司餐厅下架原因统计信息 */
    public boolean saveCompanyShelfRestaurants(List<Map<String, Object>> list) throws Exception {
        int insert = sqlSession.insert("CompanyShelfRestaurantsMapper.batchInsert", list);
        return insert > 0 ? true : false;
    }

    /** 处理公司餐厅下架原因统计信息全为0的公司统计信息 */
    public boolean deleteCompanyShelfRestaurantsEmptyByTime(String time) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(time)) {
            param.put("time", time);
        }
        int delete = sqlSession.delete("CompanyShelfRestaurantsMapper.deleteEmptyByTime", param);
        return delete > 0 ? true : false;
    }

    /** 批量保存公司餐厅推荐统计信息 */
    public boolean saveCompanyRestaurantsSource(List<Map<String, Object>> list) throws Exception {
        int insert = sqlSession.insert("CompanyRestaurantsSourceMapper.batchInsert", list);
        return insert > 0 ? true : false;
    }

    /** 查询公司医院信息 */
    public List<Map<String, Object>> queryCompanyHospitalInfoByCompanyIdAndTime(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyReportMapper.queryCompanyHospitalInfoByCompanyIdAndTime", param);
    }

    /** 选择查询公司医院信息 selectContent以c.开头 */
    public List<Map<String, Object>> queryHospitalContentBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyReportMapper.queryHospitalContentBy", param);
    }

    /** 查询公司订餐和外卖餐厅信息 */
    public List<Map<String, Object>> queryCompanyRestaurantInfoByCompanyIdAndTime(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyReportMapper.queryCompanyRestaurantInfoByCompanyIdAndTime", param);
    }

    /** 根据条件查询月公司医院覆盖餐厅数量统计信息 */
    public List<Map<String, Object>> queryCompanyHospitalRestaurantStatisticsBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectList("CompanyHospitalRestaurantStatisticsMapper.queryBy", param);
    }

    /** 根据条件查询月公司医院覆盖餐厅数量统计信息数量 */
    public Integer queryCompanyHospitalRestaurantStatisticsCountBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectOne("CompanyHospitalRestaurantStatisticsMapper.queryCountBy", param);
    }

    /** 根据条件删除月公司医院覆盖餐厅数量统计信息数量 */
    public Integer deleteCompanyHospitalRestaurantStatisticsCountBy(Map<String, Object> param) throws Exception {
        return sqlSession.selectOne("CompanyHospitalRestaurantStatisticsMapper.deleteBy", param);
    }

    /** 批量保存月公司医院覆盖餐厅数量统计信息 */
    public boolean saveCompanyHospitalRestaurantStatistics(List<Map<String, Object>> list) throws Exception {
        int insert = sqlSession.insert("CompanyHospitalRestaurantStatisticsMapper.batchInsert", list);
        return insert > 0 ? true : false;
    }
}
