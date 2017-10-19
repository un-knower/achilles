package com.quancheng.achilles.service.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.quancheng.achilles.service.services.CompanyReportService;
import com.quancheng.achilles.service.services.ISJOrderService;
import com.quancheng.achilles.service.utils.TimeUtil;

/**
 * 数加数据报表定时器
 * 
 * @author zhuzhong
 */
//@Component
//@EnableScheduling
public class SJJob extends QuartzJobBean {

    private static final Logger  log = LoggerFactory.getLogger(SJJob.class);

    @Autowired
    ISJOrderService              orderService;
    @Autowired
    private CompanyReportService companyReportService;

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("fresh tmp tables start!");
        Date start = new Date();
        try {
            orderService.userCost(null, 0);
            String timeOfMonth = TimeUtil.formatDate("yyyy-MM", TimeUtil.addMonth(new Date(), -1));
            companyReportService.queryAndSaveCompanyCityRestaurantByTime(timeOfMonth);
            companyReportService.queryAndSaveCompanyRestaurantSourceByTime(timeOfMonth);
            companyReportService.queryAndSaveCompanyShelfRestaurantByTime(timeOfMonth);
            companyReportService.queryAndSaveCompanyHospitalRestaurantStatistics(timeOfMonth, 1000, 2000, 3000);
        } catch (Exception e) {
            log.error("the task  have a error {}", e);
        }
        Date end = new Date();
        log.info("fresh tmp tables finished !cost time:" + (end.getTime() - start.getTime()));
    }

    // @Scheduled(cron = "0 15 03 1 * ?") // 每月1日早上03:15触发
    // @Scheduled(cron = "0 0/1 * * * ?") // 每隔1分钟触发
    public void test() {
        log.info("the task  queryAndSaveCompanyCityRestaurants start ... ");
        Date start = new Date();
        String timeOfMonth = TimeUtil.formatDate("yyyy-MM", new Date());
        companyReportService.queryAndSaveCompanyCityRestaurantByTime(timeOfMonth);
        companyReportService.queryAndSaveCompanyRestaurantSourceByTime(timeOfMonth);// 2015-09
        companyReportService.queryAndSaveCompanyShelfRestaurantByTime(timeOfMonth);// 2015-09
        companyReportService.queryAndSaveCompanyHospitalRestaurantStatistics(timeOfMonth, 1000, 2000, 3000);
        Date end = new Date();
        log.info("the task  queryAndSaveCompanyCityRestaurant end ...cost time:" + (end.getTime() - start.getTime()));
    }

}
