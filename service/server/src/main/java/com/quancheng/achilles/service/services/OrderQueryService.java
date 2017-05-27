package com.quancheng.achilles.service.services;

import java.util.List;
import java.util.Map;

import com.quancheng.achilles.dao.model.CompanyDomain;
import com.quancheng.achilles.dao.model.OrderRecord;
import com.quancheng.achilles.dao.model.OrderRecordVo;
import com.quancheng.achilles.dao.model.SalesDomain;

/**
 * @author liujiejian
 * @version 2016年9月10日
 */
public interface OrderQueryService {

    public List<OrderRecordVo> queryPageOrders(Map<String, Object> params);

    public Long queryPageOrdersCount(Map<String, Object> params);

    public List<CompanyDomain> getAllCompany();

    public CompanyDomain getCompanyById(Integer id);

    public List<SalesDomain> getAllSales();

    public SalesDomain getSalesModel(Integer id);
}
