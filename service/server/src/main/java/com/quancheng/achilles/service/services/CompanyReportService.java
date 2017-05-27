package com.quancheng.achilles.service.services;

import java.util.List;
import java.util.Map;

import com.quancheng.achilles.dao.model.CompanyRestaurantsStatistics;

/** 公司信息统计 */
public interface CompanyReportService {

    /** 根据月份查询公司城市餐厅统计信息并保存 time:yyyy-MM */
    public boolean queryAndSaveCompanyCityRestaurantByTime(String time);

    /** 根据条件查询公司城市餐厅统计信息 */
    public List<CompanyRestaurantsStatistics> queryCompanyCityStatisticsBy(CompanyRestaurantsStatistics param);

    /** 根据条件查询公司餐厅统计信息 time:yyyy-MM */
    public CompanyRestaurantsStatistics queryCompanyRestaurantsStatisticsByTime(String time, Integer companyId);

    /** 根据条件查询月公司餐厅下架数量 time:yyyy-MM */
    public Integer queryCompanyAllShelfNumByTime(String time, Integer companyId);

    /** 每月餐厅新增不同推荐来源的总数量 time:yyyy-MM */
    public Integer queryCompanyAllSourceNumByTime(String time, Integer companyId);

    /** 根据月份查询公司餐厅下架原因统计信息并保存 time:yyyy-MM */
    public boolean queryAndSaveCompanyShelfRestaurantByTime(String time);

    /** 根据月份查询公司餐厅推荐信息并保存 time:yyyy-MM */
    public boolean queryAndSaveCompanyRestaurantSourceByTime(String time);

    /**
     * 查询并保存公司医院附近公司订餐和外卖餐厅统计信息并保存 <br>
     * time:yyyy-MM <br>
     * distance:距离数组（单位米）
     */
    public boolean queryAndSaveCompanyHospitalRestaurantStatistics(String time, Integer... distance);

    /**
     * 查询公司医院附近公司订餐和外卖餐厅统计信息<br>
     * param 参数（time:yyyy-MM；companyId；companyIds（，分割字符串）；cityId；cityName） <br>
     * distance:距离数组（单位米）
     */
    public boolean queryCompanyHospitalRestaurantStatistics(Map<String, Object> param, Integer... distance);

}
