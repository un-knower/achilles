package com.quancheng.achilles.service.services;

/**
 * @author liujiejian
 * @version 2016年9月28日
 */
public interface CacheLogService {

    String getRefreshTimeById(Integer id);

    String doRefresh(Integer id);
}
