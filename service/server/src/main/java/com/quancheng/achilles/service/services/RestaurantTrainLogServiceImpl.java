package com.quancheng.achilles.service.services;

//import static org.springframework.data.jpa.domain.Specifications.where;
//
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
//import com.quancheng.achilles.dao.modelwrite.RestaurantTrainLog;
//import com.quancheng.achilles.dao.write.RestaurantTrainLogRepository;
//import com.quancheng.achilles.service.utils.DateUtils;
//
//@Service
//@Transactional(readOnly = true)
//public class RestaurantTrainLogServiceImpl implements RestaurantTrainLogService {
//	@Autowired
//	RestaurantTrainLogRepository restaurantTrainLogRepository;
//
//	@Override
//	public Page<RestaurantTrainLog> findAll(Date startDate, Date endDate, String company,  String[] city,
//	        String restaurantName, String salesName, Pageable pageable) {
//		Specifications<RestaurantTrainLog> spec = where(trainDateWithinRange(startDate, endDate))
//		        .and(companyEqual(company)).and(cityNameIn(city)).and(salesNameEqual(salesName)).and(restaurantNameLike(restaurantName));
//		return restaurantTrainLogRepository.findAll(spec, pageable);
//	}
//
//	@Override
//	public List<String> listAllCityName() {
//		return restaurantTrainLogRepository.listAllCityName();
//	}
//
//	@Override
//	public List<String> listAllSalesName() {
//		return restaurantTrainLogRepository.listAllSalesName();
//	}
//
//	public static Specification<RestaurantTrainLog> trainDateWithinRange(Date startDate, Date endDate) {
//		return new Specification<RestaurantTrainLog>() {
//			@Override
//			public Predicate toPredicate(Root<RestaurantTrainLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//			    if(startDate== null || endDate == null){
//                    return null;
//                }
//				Date x = DateUtils.getBeginningOfDay(startDate);
//				Date y = DateUtils.getEndOfDay(endDate);
//				return cb.between(root.<Date> get("trainDate"), x, y);
//			}
//		};
//	}
//
//	public static Specification<RestaurantTrainLog> companyEqual(String company) {
//		return new Specification<RestaurantTrainLog>() {
//			@Override
//			public Predicate toPredicate(Root<RestaurantTrainLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if (company == null || company.isEmpty() || company.equals("-1")) {
//					return null;
//				}
//				return cb.equal(root.get("ownCompanys"), company);
//			}
//		};
//	}
//
//	public static Specification<RestaurantTrainLog> cityNameIn(Object[] cityName) {
//		return new Specification<RestaurantTrainLog>() {
//			@Override
//			public Predicate toPredicate(Root<RestaurantTrainLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				return cityName == null? null:root.get("cityName").in(cityName);
//			}
//		};
//	}
//
//	public static Specification<RestaurantTrainLog> salesNameEqual(String salesName) {
//		return new Specification<RestaurantTrainLog>() {
//			@Override
//			public Predicate toPredicate(Root<RestaurantTrainLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if (salesName == null || salesName.isEmpty() || salesName.equals("-1")) {
//					return null;
//				}
//				return cb.equal(root.get("salesName"), salesName);
//			}
//		};
//	}
//
//	public static Specification<RestaurantTrainLog> restaurantNameLike(String restaurantName) {
//		return new Specification<RestaurantTrainLog>() {
//			@Override
//			public Predicate toPredicate(Root<RestaurantTrainLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				if (restaurantName == null || restaurantName.isEmpty() || restaurantName.equals("-1")) {
//					return null;
//				}
//				return cb.like(root.get("restName"), "%" + restaurantName + "%");
//			}
//		};
//	}
//
//}
