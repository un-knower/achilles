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

import com.quancheng.achilles.dao.modelwrite.QuanlityCheckRecord;
import com.quancheng.achilles.dao.write.QuanlityCheckRecordRepository;

/**
 * @author lijun jiang
 * @version 创建时间：2016年9月9日下午2:40:46
 */
@Service
@Transactional(readOnly = true)
public class QuanlityCheckServiceImpl implements QuanlityCheckService {

    @Autowired
    QuanlityCheckRecordRepository quanlityCheckRecordRepository;

    @Override
    public Page<QuanlityCheckRecord> findAll(Pageable pageable) {
        return quanlityCheckRecordRepository.findAll(pageable);
    }

    // 条件查询
    @Override
    public Page<QuanlityCheckRecord> findAll(String[] state,String checkreason, Date startDate, Date endDate, String company, String checkType,
                                             String checkMode, String[] cityName, String shangpuName,
                                             Pageable pageable) {
        Specifications<QuanlityCheckRecord> spec = where(recordOnDateRange(startDate,
                                                                           endDate))
                .and(companyEqual(company))
                .and(checkTypeEqual(checkType))
                .and(reasonLike(checkreason))
                .and(checkModeEqual(checkMode))
               .and(checkStateEqual(state))
                .and(cityNameEqual(cityName))
                .and(shangpuNameLike(shangpuName));
        return quanlityCheckRecordRepository.findAll(spec, pageable);
    }

    private Specification<QuanlityCheckRecord> shangpuNameLike(String shangpuName) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (shangpuName == null || shangpuName.isEmpty() || shangpuName.equals("-1")) {
                    return null;
                }
                return cb.like(root.get("shangpuName"), "%" + shangpuName + "%");
            }
        };
    }
    
    private Specification<QuanlityCheckRecord> reasonLike(String reason) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (reason == null || reason.isEmpty()  ) {
                    return null;
                }
                return cb.like(root.get("checkReason"), "%" + reason + "%");
            }
        };
    }
    private Specification<QuanlityCheckRecord> cityNameEqual(String[] city) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                if (city == null || city.length == 0 || Arrays.asList(city).contains("-1")) {
                    return null;
                }
                return criteriaBuilder.isTrue(root.get("cityName").in(Arrays.asList(city)));
            }
        };
    }

    private Specification<QuanlityCheckRecord> checkModeEqual(String checkMode) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (checkMode == null || checkMode.isEmpty() || checkMode.equals("-1")) {
                    return null;
                }
                return cb.equal(root.get("checkMode"), checkMode);
            }
        };
    }

    @Override
    public List<String> listAllCompany() {
        return quanlityCheckRecordRepository.listAllCompany();
    }

    @Override
    public List<String> listAllCheckType() {
        return quanlityCheckRecordRepository.listAllCheckType();
    }

    private Specification<QuanlityCheckRecord> checkTypeEqual(String checkType) {

        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (checkType == null || checkType.isEmpty() || checkType.equals("-1")) {
                    return null;
                }
                return cb.equal(root.get("checkType"), checkType);
            }
        };

    }

    private Specification<QuanlityCheckRecord> checkStateEqual(String[] status) {
        return new Specification<QuanlityCheckRecord>() {
            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return status == null  ?null : root.get("status").in( status);
            }
        };

    }
    
    private Specification<QuanlityCheckRecord> companyEqual(String company) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (company == null || company.isEmpty() || company.equals("-1")) {
                    return null;
                }
                return cb.equal(root.get("company"), company);
            }
        };

    }

    private Specification<QuanlityCheckRecord> recordOnDateRange(Date startDate, Date endDate) {
        return new Specification<QuanlityCheckRecord>() {

            @Override
            public Predicate toPredicate(Root<QuanlityCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(startDate==null || endDate==null){
                    return null;
                }
                return cb.between(root.<Date> get("checkDate"), startDate, endDate);
            }
        };

    }

    @Override
    public List<String> listAllCheckMode() {
        return quanlityCheckRecordRepository.listAllCheckMode();
    }

    @Override
    public List<String> listAllCityName() {
        return quanlityCheckRecordRepository.listAllCityName();
    }
}
