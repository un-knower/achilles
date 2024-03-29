package com.quancheng.achilles.service.services;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public abstract class RestaurantServiceAbstract<T> {
    protected Specification<T> like(String field,String fieldValue) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T>  root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return isEmpty(fieldValue)? null:cb.like(root.get(field), "%"+fieldValue+"%");
            }
        };
    }
    protected Specification<T> in(String field,Object[] fieldValue) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return isEmpty(fieldValue)? null:root.get(field).in(fieldValue);
            }
        };
    }
    protected Specification<T> equal(String field,String fieldValue) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return isEmpty(fieldValue)? null:cb.equal(root.get(field), fieldValue);
            }
        };
    }
     @SuppressWarnings("rawtypes")
    public Specification<T> between(String field,Comparable  begin,Comparable  end) {
        return new Specification<T>() {
            @SuppressWarnings("unchecked")
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return begin==null ||end==null ?null : cb.between(root.get(field), begin, end);
            }
        };
    }
     protected  Specification<T> notNull(String field ) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return  cb.isNotNull(root.get(field));
            }
        };
    }
     
     protected  boolean isEmpty(String str){
        return str==null || "".equals(str);
    }
    
     protected  boolean isEmpty(Object ... str){
        return str==null || str.length==0||Arrays.asList(str).contains("-1");
    }
}
