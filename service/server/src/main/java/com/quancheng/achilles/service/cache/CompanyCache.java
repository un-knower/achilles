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
import com.quancheng.achilles.dao.repository.CompanyRepository;
import com.quancheng.achilles.dao.model.CompanyDomain;

/**
 * @author liujiejian
 * @version 2016年9月12日
 */
@Component
public class CompanyCache {

    private static Logger                               LOGGER = LoggerFactory.getLogger(CompanyCache.class);

    // 用于缓存机场信息
    private static LoadingCache<Integer, CompanyDomain> companyCache;

    // 类是否被初始化
    private static boolean                              ISINIT = false;
    @Autowired
    private CompanyRepository                           companyRepository;

    public CompanyCache(){

    }

    private void init() {
        synchronized (CompanyCache.class) {
            if (ISINIT) {
                return;
            }
            try {
                LOGGER.info("Start loading company info into the system cache.");

                List<CompanyDomain> allCompanyModel = getAllCompanyFromDB();
                LOGGER.debug("Loaded from the database of company:[{}]", allCompanyModel);

                Map<Integer, CompanyDomain> companyMap = new HashMap<Integer, CompanyDomain>();
                for (CompanyDomain model : allCompanyModel) {
                    companyMap.put(model.getId(), model);
                }

                initCompanyCache(companyMap.size());
                companyCache.putAll(companyMap);

                LOGGER.info("Load planeModel into the system cache end.");

                ISINIT = true;
            } catch (Exception e) {
                LOGGER.error("Load data into the system cache error!", e);
            }
        }
    }

    private List<CompanyDomain> getAllCompanyFromDB() {
        List<CompanyDomain> CompanyDomains = companyRepository.getAllCompany();
        return CompanyDomains;
    }

    /**
     * @param size
     */
    private void initCompanyCache(int size) {
        companyCache = CacheBuilder.newBuilder().concurrencyLevel(5).expireAfterAccess(2,
                                                                                       TimeUnit.HOURS).initialCapacity(size).maximumSize(size
                                                                                                                                         * 2L).recordStats() // 开启Guava
                                                                                                                                                             // Cache的统计功能
                                   .build(new CacheLoader<Integer, CompanyDomain>() {

                                       @Override
                                       public CompanyDomain load(Integer code) throws Exception {
                                           LOGGER.debug("Data is not found in the cache:[{}].Reloading from db.", code);
                                           metricsAirportCache();
                                           return getCompanyFromDB(code);
                                       }
                                   });
    }

    /**
     * 
     */
    private void metricsAirportCache() {
        CacheStats cs = companyCache.stats();
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
    private CompanyDomain getCompanyFromDB(Integer code) {
        init();
        CompanyDomain dto = companyRepository.getCompanyById(code);
        return dto;
    }

    /**
     * Get company cache by code
     * 
     * @param code
     * @return
     * @throws CommonException
     * @throws BaseException
     * @throws ExecutionException
     */
    public CompanyDomain getCompanyModel(Integer code) {
        init();
        try {
            return companyCache.get(code);
        } catch ( Exception e) {
            LOGGER.error("Get company from cache failed!", e);
        }
        return new CompanyDomain();
    }

    public List<CompanyDomain> getAllCompany() {
        init();
        List<CompanyDomain> list = new ArrayList<>();
        ConcurrentMap<Integer, CompanyDomain> map = companyCache.asMap();
        for (Entry<Integer, CompanyDomain> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

}
