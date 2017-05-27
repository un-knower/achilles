package com.quancheng.achilles.service.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.quancheng.achilles.service.constants.InnConstantsJob;
import com.quancheng.achilles.dao.repository.CacheLogRepository;

/**
 * @author liujiejian
 * @version 2016年9月26日
 */
@Component
@EnableScheduling
public class OrderCacheSyncJob extends QuartzJobBean {

    private static final Logger  LOGGER            = LoggerFactory.getLogger(OrderCacheSyncJob.class);

    @Autowired
    private CacheLogRepository           cr;

    public void refreshOrderTemptable(Integer id) throws JobExecutionException {
        try {
            cr.doRefreshById(id);
        } catch (Exception e) {
            LOGGER.error("Quartz job failed ,{}", e);
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("fresh tmp tables start!");
        for (Integer id : InnConstantsJob.IDS) {
            refreshOrderTemptable(id);
        }
        LOGGER.info("fresh tmp tables finished !");
    }
}
