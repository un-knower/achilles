package com.quancheng.achilles.service.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.ds_qc.repository.DataItemDetailRep;
import com.quancheng.achilles.dao.ds_st.model.DataItem;
import com.quancheng.achilles.dao.ds_st.model.DataItemDetail;
import com.quancheng.achilles.dao.ds_st.repository.DataItemDetailRepository;
import com.quancheng.achilles.dao.ds_st.repository.DataItemRepository;

@Service
public class DataItemServiceImpl {
    @Autowired
    DataItemDetailRepository dataItemDetailRepository;
    @Autowired
    DataItemRepository dataItemRepository;
    @Autowired
    DataItemDetailRep dataItemDetailRep;

    public List<DataItem> getDataItem(Object... itemKes) {
        return dataItemRepository.findAll(new Specification<DataItem>() {
            public Predicate toPredicate(Root<DataItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return itemKes==null||itemKes.length==0?null:root.get("itemKey").in(itemKes);
            }
        });
    }

    public DataItem getDataItem(Object itemKey) {
        return dataItemRepository.findOne(new Specification<DataItem>() {
            public Predicate toPredicate(Root<DataItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("itemKey"), itemKey);
            }
        });
    }

    public List<DataItemDetail> getDataItemDetail(Long itemId) {
        return dataItemDetailRepository.findAll(new Specification<DataItemDetail>() {
            public Predicate toPredicate(Root<DataItemDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("itemId"), itemId);
            }
        });
    }

    public List<DataItemDetail> getDataItemDetail(Map<String, Object> map) {
        return dataItemDetailRep.queryDataDetails(map);
    }

    public List<DataItemDetail> getDataItemDetail(Object itemKey) {
        DataItem di = getDataItem(itemKey);
        if (di != null && "DETAIL_KV".equals(di.getItemType())) {
            return getDataItemDetail(di.getId());
        } else if (di != null && "VIEW".equals(di.getItemType())) {
            // 视图
            Map<String, Object> map = new HashMap<>();
            map.put("viewName", di.getItemContent());
            return getDataItemDetail(map);
        }
        return new ArrayList<DataItemDetail>();
    }
    
    public Map<Object,Object> getDataItemDetailToMap(Object itemKey){
        List<DataItemDetail>  items = getDataItemDetail(itemKey);
        Map<Object,Object> result = new HashMap<>(items.size());
        for (DataItemDetail dataItemDetail : items) {
            result.put(dataItemDetail.getDetailKey(), dataItemDetail.getDetailText());
        }
        return result;
    }
    
    
    public Map<Object,Object> getDataItemDetailReverseMap(Object itemKey){
        List<DataItemDetail>  items = getDataItemDetail(itemKey);
        Map<Object,Object> result = new HashMap<>(items.size());
        for (DataItemDetail dataItemDetail : items) {
            result.put(dataItemDetail.getDetailText(),dataItemDetail.getDetailKey());
        }
        return result;
    }
}
