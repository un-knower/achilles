package com.quancheng.achilles.service.services;

//import static org.springframework.data.jpa.domain.Specifications.where;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.domain.Specifications;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.quancheng.achilles.dao.repository.CallRecordRepository;
//import com.quancheng.achilles.service.utils.DateUtils;
//import com.quancheng.achilles.dao.model.CallRecord;

//@Service
//@Transactional(readOnly = true)
//public class CallRecordServiceImpl implements CallRecordService {
//	
//	@Autowired
//	CallRecordRepository callRecordRepository;
//
//	@Override
//	public Page<CallRecord> findAll(Date startDate, Date endDate, String company, String type, String kefuName, Pageable pageable) {
//		Specifications<CallRecord> spec = where(calledOnDateRange(startDate, endDate)).and(companyEqual(company)).and(typeEqual(type)).and(kefuNameEqual(kefuName));
//
//		Page<CallRecord> page = callRecordRepository.findAll(spec, pageable);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
//		for (CallRecord callRecord : page) {
//		    callRecord.setRecordTime(callRecord.getRecordTime()==null||callRecord.getRecordTime().trim().equals("")?null:
//		        sdf.format(new Date(Long.parseLong(callRecord.getRecordTime())*1000)));
//        }
//		return page;
//	}
//	
//	@Override
//	public List<String> listAllCompany() {
//		return callRecordRepository.listAllCompany();
//	}
//
//	@Override
//	public List<String> listAllType() {
//		return callRecordRepository.listAllType();
//	}
//
//	@Override
//	public List<String> listAllKefuName() {
//		return callRecordRepository.listAllKefuName();
//	}
//	
//	public static Specification<CallRecord> calledOnDateRange(Date startDate, Date endDate) {
//	    if(startDate== null||startDate==null){
//	        return null;
//	    }
//		return new Specification<CallRecord>() {
//			@Override
//			public Predicate toPredicate(Root<CallRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Long x = DateUtils.getBeginningOfDay(startDate).getTime()/1000;
//				Long y = DateUtils.getEndOfDay(endDate).getTime()/1000;
//				return cb.between(root.<Long>get("recordTime"), x, y);
//			}
//		};
//	  }
//
//	public static Specification<CallRecord> companyEqual(String company) {
//		return new Specification<CallRecord>() {
//			@Override
//			public Predicate toPredicate(Root<CallRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if(company == null || company.isEmpty() || company.equals("-1")) {
//					return null;
//				}
//				return cb.equal(root.get("company"), company);
//			}
//		};
//	  }
//	
//	public static Specification<CallRecord> typeEqual(String type) {
//		return new Specification<CallRecord>() {
//			@Override
//			public Predicate toPredicate(Root<CallRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if(type == null || type.isEmpty() || type.equals("-1")) {
//					return null;
//				}
//				return cb.equal(root.get("type"), type);
//			}
//		};
//	  }
//	
//	public static Specification<CallRecord> kefuNameEqual(String kefuName) {
//		return new Specification<CallRecord>() {
//			@Override
//			public Predicate toPredicate(Root<CallRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if(kefuName == null || kefuName.isEmpty() || kefuName.equals("-1")) {
//					return null;
//				}
//				return cb.equal(root.get("kefuName"), kefuName);
//			}
//		};
//	  }
//
//
//
//}
