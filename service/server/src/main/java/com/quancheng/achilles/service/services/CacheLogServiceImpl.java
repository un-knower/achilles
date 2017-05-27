package com.quancheng.achilles.service.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.repository.CacheLogRepository;


/**
 * @author liujiejian
 * @version 2016年9月28日
 */
@Service
public class CacheLogServiceImpl implements CacheLogService {

    @Autowired
    private CacheLogRepository cacheLogRepository;

    @Override
    public String getRefreshTimeById(Integer id) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = cacheLogRepository.getRefreshTimeById(id);
        if (date == null) {
            return "unknown";
        }
        return fmt.format(date);
    }

    @Override
    public String doRefresh(Integer id) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = cacheLogRepository.doRefreshById(id);
        if (date == null) {
            return "unknown";
        }
        return fmt.format(date);
    }
    public static Date getTimesnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
