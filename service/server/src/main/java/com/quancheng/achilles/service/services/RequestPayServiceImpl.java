package com.quancheng.achilles.service.services;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.quancheng.achilles.dao.repository.RequestPayRepository;
import com.quancheng.achilles.dao.model.RequestPayment;

@Service
@Transactional(readOnly = true)
public class RequestPayServiceImpl implements RequestPayService {

    @Autowired
    RequestPayRepository requestPayRepository;

    @Override
    public Page<RequestPayment> findAll(Date startTime, Date endTime, String relateOrder, String[] city,
                                        Pageable pageable) {
        Specifications<RequestPayment> spec = where(payDateRecord(startTime,
                                                                  endTime)).and(relateOrderEqual(relateOrder)).and(relateCityEqual(city));
        return requestPayRepository.findAll(spec, pageable);
    }

    private static Specification<RequestPayment> relateCityEqual(String[] city) {
        return new Specification<RequestPayment>() {

            @Override
            public Predicate toPredicate(Root<RequestPayment> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                if (city == null || city.length == 0 || Arrays.asList(city).contains("-1")) {
                    return null;
                }
                return criteriaBuilder.isTrue(root.get("city").in(Arrays.asList(city)));
            }
        };
    }

    private static Specification<RequestPayment> relateOrderEqual(String relateOrder) {
        return new Specification<RequestPayment>() {

            @Override
            public Predicate toPredicate(Root<RequestPayment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (relateOrder == null || relateOrder.isEmpty() || relateOrder.equals("-1")) {
                    return null;
                }
                return cb.equal(root.get("relateOrder"), relateOrder);
            }
        };
    }

    private Specification<RequestPayment> payDateRecord(Date startTime, Date endTime) {
        if(startTime==null || endTime == null){
            return null;
        }
        return new Specification<RequestPayment>() {

            @Override
            public Predicate toPredicate(Root<RequestPayment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.<Date> get("createdAt"), startTime, endTime);
            }
        };
    }

    @Override
    public List<String> listAllCityName() {
        return requestPayRepository.listAllCity();
    }

}
