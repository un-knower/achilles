package com.quancheng.achilles.service.services;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quancheng.achilles.dao.modelwrite.RestaurantVisitLog;
import com.quancheng.achilles.dao.write.RestaurantVisitLogRepository;
import com.quancheng.achilles.service.constants.InnConstantRestStateEnum;
import com.quancheng.achilles.service.utils.DateUtils;

@Service
@Transactional(readOnly = true)
public class RestaurantVisitLogServiceImpl implements RestaurantVisitLogService {

	@Autowired
	RestaurantVisitLogRepository restaurantVisitLogRepository;
	
	@Override 
	public Page<RestaurantVisitLog> findAll(Date startDate, Date endDate, String restStatus , String source, String[] city,
	        String restaurantName, String salesName, Pageable pageable) {
		Specifications<RestaurantVisitLog> spec = where(visitDateWithinRange(startDate, endDate))
		        .and(typeEqual(source))
		        .and(cityNameIn(city))
		        .and(salesNameEqual(salesName))
		        .and(stateEqual(restStatus))
		        .and(restaurantNameLike(restaurantName));
		return convert(restaurantVisitLogRepository.findAll(spec, pageable));
	}
	Page<RestaurantVisitLog> convert(Page<RestaurantVisitLog> page){
		Map<Object, Object> icrse =InnConstantRestStateEnum.parseSources();
		for(RestaurantVisitLog rvl:page.getContent()){
			rvl.setStatus(icrse.get(rvl.getStatus())==null?null:icrse.get(rvl.getStatus()).toString());
		}
		return page;
	}
	@Override
	public List<String> listAllType() {
		return restaurantVisitLogRepository.listAllType();
	}

	@Override
	public List<String> listAllCityName() {
		return restaurantVisitLogRepository.listAllCityName();
	}

	@Override
	public List<String> listAllSalesName() {
		return restaurantVisitLogRepository.listAllSalesName();
	}
	
	public static Specification<RestaurantVisitLog> visitDateWithinRange(Date startDate, Date endDate) {
		return new Specification<RestaurantVisitLog>() {
			@Override
			public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			    if(startDate== null || endDate == null){
			        return null;
			    }
				Date x = DateUtils.getBeginningOfDay(startDate);
				Date y = DateUtils.getEndOfDay(endDate);
				return cb.between(root.<Date> get("startTime"), x, y);
			}
		};
	}

	public static Specification<RestaurantVisitLog> typeEqual(String type) {
		return new Specification<RestaurantVisitLog>() {
			@Override
			public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (type == null || type.isEmpty() || type.equals("-1")) {
					return null;
				}
				return cb.equal(root.get("type"), type);
			}
		};
	}

	   public static Specification<RestaurantVisitLog> stateEqual(String state) {
	        return new Specification<RestaurantVisitLog>() {
	            @Override
	            public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                if (state == null || state.isEmpty() || state.equals("-1")) {
	                    return null;
	                }
	                return cb.equal(root.get("status"), state);
	            }
	        };
	    }
	
	public static Specification<RestaurantVisitLog> cityNameIn(Object[] cityName) {
		return new Specification<RestaurantVisitLog>() {
			@Override
			public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			    return cityName == null? null:root.get("cityName").in(cityName);
			}
		};
	}

	public static Specification<RestaurantVisitLog> salesNameEqual(String salesName) {
		return new Specification<RestaurantVisitLog>() {
			@Override
			public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (salesName == null || salesName.isEmpty() || salesName.equals("-1")) {
					return null;
				}
				return cb.equal(root.get("salesName"), salesName);
			}
		};
	}

	public static Specification<RestaurantVisitLog> restaurantNameLike(String restaurantName) {
		return new Specification<RestaurantVisitLog>() {
			@Override
			public Predicate toPredicate(Root<RestaurantVisitLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (restaurantName == null || restaurantName.isEmpty() || restaurantName.equals("-1")) {
					return null;
				}
				return cb.like(root.get("restaurantName"), "%" + restaurantName + "%");
			}
		};
	}
}
