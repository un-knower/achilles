package com.quancheng.achilles.service.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.quancheng.achilles.dao.repository.SalesRepository;
import com.quancheng.achilles.dao.model.SalesDomain;

/**
 * @author liujiejian
 * @version 2016年9月12日
 */
@Component
public class SalesCache {

    private static Logger                             LOGGER = LoggerFactory.getLogger(SalesCache.class);

    // 用于缓存机场信息
    private static LoadingCache<Integer, SalesDomain> salesCache;

    // 类是否被初始化
    private static boolean                            ISINIT = false;
    @Autowired
    private SalesRepository                           salesRepository;

    public SalesCache(){

    }

    private void init() {
        synchronized (SalesCache.class) {
            if (ISINIT) {
                return;
            }
            try {
                LOGGER.info("Start loading sales info into the system cache.");

                List<SalesDomain> allSalesModel = getAllSalesFromDB();
                LOGGER.debug("Loaded from the database of sales:[{}]", allSalesModel);

                Map<Integer, SalesDomain> salesMap = new HashMap<Integer, SalesDomain>();
                for (SalesDomain model : allSalesModel) {
                    salesMap.put(model.getId(), model);
                }

                initSalesCache(salesMap.size());
                salesCache.putAll(salesMap);

                LOGGER.info("Load planeModel into the system cache end.");

                ISINIT = true;
            } catch (Exception e) {
                LOGGER.error("Load data into the system cache error!", e);
            }
        }
    }

    private List<SalesDomain> getAllSalesFromDB() {
        List<SalesDomain> SalesDomains = salesRepository.getAllSales();
        return SalesDomains;
    }

    /**
     * @param size
     */
    private void initSalesCache(int size) {
        salesCache = CacheBuilder.newBuilder().concurrencyLevel(5).expireAfterAccess(2,
                                                                                     TimeUnit.HOURS).initialCapacity(size).maximumSize(size
                                                                                                                                       * 2L).recordStats() // 开启Guava
                                                                                                                                                           // Cache的统计功能
                                 .build(new CacheLoader<Integer, SalesDomain>() {

                                     @Override
                                     public SalesDomain load(Integer code) throws Exception {
                                         LOGGER.debug("Data is not found in the cache:[{}].Reloading from db.", code);
                                         metricsAirportCache();
                                         return getSalesFromDB(code);
                                     }
                                 });
    }

    /**
     * 
     */
    private void metricsAirportCache() {
        CacheStats cs = salesCache.stats();
        LOGGER.debug("Airport cache hit rate:[{}],The average time to load the new value:[{}nanosecond]", cs.hitRate(),
                     cs.averageLoadPenalty());
    }

    /**
     * @param size
     */

    /**
     * @param key
     * @return
     * @throws ExecutionException
     * @throws CommonException
     * @throws BaseException
     */
    private SalesDomain getSalesFromDB(Integer code) {
        init();
        SalesDomain dto = salesRepository.getSalesById(code);
        return dto;
    }

    /**
     * Get sales cache by code
     * 
     * @param code
     * @return
     * @throws CommonException
     * @throws BaseException
     * @throws ExecutionException
     */
    public SalesDomain getSalesModel(Integer code) {
        init();
        SalesDomain sales = null;
        try {
            sales = salesCache.get(code);
        } catch (ExecutionException e) {
            LOGGER.error("Get sales from cache failed!", e);
        }
        return sales;
    }

    public List<SalesDomain> getAllSales() {
        init();
        List<SalesDomain> list = new ArrayList<>();
        ConcurrentMap<Integer, SalesDomain> map = salesCache.asMap();
        for (Entry<Integer, SalesDomain> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

}
