package com.quancheng.achilles.service.services;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.quancheng.achilles.dao.repository.CompanyRepository;
//import com.quancheng.achilles.dao.repository.OrderQueryRepository;
//import com.quancheng.achilles.service.cache.SalesCache;
//import com.quancheng.achilles.dao.model.CompanyDomain;
//import com.quancheng.achilles.dao.model.OrderRecord;
//import com.quancheng.achilles.dao.model.OrderRecordVo;
//import com.quancheng.achilles.dao.model.SalesDomain;
//
///**
// * @author liujiejian
// * @version 2016年9月10日
// */
//@Service
//@Transactional(readOnly = true)
//public class OrderQueryServiceImpl implements OrderQueryService {
//
//	@Autowired
//	OrderQueryRepository orderQueryRepository;
//	@Autowired
//	private CompanyRepository companyRepository;
//	@Autowired
//	SalesCache salesCache;
//
//	@Override
//	public List<OrderRecordVo> queryPageOrders(Map<String, Object> params) {
//		List<OrderRecordVo> orders = orderQueryRepository.queryPageOrders(params);
//		return orders;
//	}
//
//	@Override
//	public Long queryPageOrdersCount(Map<String, Object> params) {
//		return orderQueryRepository.queryPageOrdersCount(params);
//	}
//
//	@Override
//	public List<CompanyDomain> getAllCompany() {
//		return companyRepository.getAllCompany();
//	}
//
//	@Override
//	public CompanyDomain getCompanyById(Integer id) {
//		return companyRepository.getCompanyById(id);
//	}
//
//	@Override
//	public List<SalesDomain> getAllSales() {
//		return salesCache.getAllSales();
//	}
//
//	@Override
//	public SalesDomain getSalesModel(Integer id) {
//		return salesCache.getSalesModel(id);
//	}
//
//}
