package com.quancheng.achilles.service.services;

import com.quancheng.achilles.dao.repository.ClientRepository;
import com.quancheng.achilles.dao.repository.RegionRepository;
import com.quancheng.achilles.dao.write.MemberRepository;
import com.quancheng.achilles.service.utils.DateUtils;
import com.quancheng.achilles.dao.model.Region;
import com.quancheng.achilles.dao.modelwrite.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by XZW on 2016/9/10 0010.
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RegionRepository regionRepository;

    @Override
    public Page<Member> findAll(String startDate, String endDate, String company, String[] cityArray, String type, String name, String[] statusArray, Pageable pageable) {
        Specifications<Member> spec = where(companyEqual(company)).and(cityIn(cityArray)).and(regOnDate(startDate, endDate)).and(nameEqual(name, type)).and(statusIn(statusArray));
        return memberRepository.findAll(spec, pageable);
    }

    @Override
    public List<String> listAllCompany() {
        return clientRepository.listAllCompany();
    }

    public static Specification<Member> regOnDate(String startDate, String endDate) {


        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(startDate == null || endDate == null ){
                    return null;
                }
                Date start = DateUtils.toDate(startDate, DateUtils.SDF_DATE);
                Date end = DateUtils.toDate(endDate, DateUtils.SDF_DATE);
                long startDay = DateUtils.getBeginningOfDay(start).getTime() / 1000;
                long endDay = DateUtils.getEndOfDay(end).getTime() / 1000;
                return criteriaBuilder.between(root.get("regTime"), startDay, endDay);
            }
        };
    }

    @Override
    public Map<Integer, String> listUserStatus() {

        return new HashMap<Integer, String>() {{
            put(Member.STATUS_ENABLE, "正常");
            put(Member.STATUS_UNACTIVATED, "未激活");
            put(Member.STATUS_UNAUTHENTICATION, "未认证");
            put(Member.STATUS_DELETED, "已删除");
            put(Member.STATUS_DISABLE, "已禁用");
        }};
    }


    @Override
    public List<Region> listCity() {
        return regionRepository.findRegionsByStatusByLevel(Region.STATUS_ENABLE, Region.LEVEL_CITY);
    }


    public static Specification<Member> companyEqual(String company) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (company == null || company.isEmpty() || company.equals("-1")) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("clientTitle"), company);
            }
        };
    }

    public static Specification<Member> cityIn(String[] city) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (city == null || city.length == 0 || Arrays.asList(city).contains("-1")) {
                    return null;
                } 
                
                return criteriaBuilder.isTrue(root.get("cityId").in(Arrays.asList(city)));
            }
        };
    }
    
    public static Specification<Member> statusIn(String[] status) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (status == null || status.length == 0 || Arrays.asList(status).contains("-999")) {
                    return null;
                } 
                
                return criteriaBuilder.isTrue(root.get("status").in(Arrays.asList(status)));
            }
        };
    }

    public static Specification<Member> nameEqual(String keyword, String keytype) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (keytype == null || keytype.isEmpty() || keytype.equals("-1")) {
                    return null;
                }
                if (keyword == null || keyword.isEmpty() || keyword.equals("-1")) {
                    return null;
                }
                String name= String.format("%%%s%%", keyword);
                Predicate predicate = null;
                switch (keytype) {
                    case "realname":
                        predicate = criteriaBuilder.like(root.get("realname"), name);
                        break;
                    case "mobile":
                        predicate = criteriaBuilder.like(root.get("mobile"), name);
                        break;
                    case "email":
                        predicate = criteriaBuilder.like(root.get("email"), name);
                        break;
                    case "jobnum":
                        predicate = criteriaBuilder.like(root.get("jobNum"), name);
                        break;
                    default:
                }
                return predicate;
            }
        };
    }

    public static Specification<Member> statusEqual(String status) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (status == null || status.isEmpty()) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }
}
