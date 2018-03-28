package com.quancheng.achilles.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quancheng.achilles.dao.ds_st.model.SpecialRestEntity;
import com.quancheng.achilles.dao.ds_st.repository.SpecialRspository;
import com.quancheng.achilles.util.GouldApiUtil;

public class CaciaRestaurantServiceImpl {
    
    @Autowired
    SpecialRspository specialRspository;
    
    private String ignoreStr = "[]";
    public void caculate() {
        Page<SpecialRestEntity> specialRestEntities =null;
        int j=0;
        do{
            Pageable page=specialRestEntities.nextPageable();
            specialRestEntities = specialRestEntities==null?specialRspository.findAll(new PageRequest(0,1000)):  specialRspository.findAll(new PageRequest(page.getPageNumber(),1000));
            JSONObject json = null;
            System.out.println("total_size:"+specialRestEntities.getSize());
            for (SpecialRestEntity specialRestEntity : specialRestEntities) {
                System.out.println("total_size:"+(j)+"/"+specialRestEntities.getTotalElements());
                j++;
                if (specialRestEntity.getGould_id() != null && !specialRestEntity.getGould_id().isEmpty()) {
                    json = GouldApiUtil.gouldByID(specialRestEntity.getGould_id());
                } 
                if(specialRestEntity.getGould_id() == null||json==null){
                    json = GouldApiUtil.gould(specialRestEntity.getAurant_name(), specialRestEntity.getAurant_city());
                    json = json==null&&specialRestEntity.getAurant_address()!=null&&!specialRestEntity.getAurant_address().isEmpty()?GouldApiUtil.gould(specialRestEntity.getAurant_address(), specialRestEntity.getAurant_city()):json;
                    specialRestEntity.setGould_id(json==null?null:json.getString("id"));
                }
                if (json == null) {
                    continue;
                }
                if (specialRestEntity.getAurant_name() == null || specialRestEntity.getAurant_name().isEmpty()) {
                    specialRestEntity.setAurant_name(json.getString("name"));
                }
                if (specialRestEntity.getAurant_address() == null || specialRestEntity.getAurant_address().isEmpty()) {
                    specialRestEntity.setAurant_address(json.getString("address"));
                }
                if (specialRestEntity.getAurant_city() == null || specialRestEntity.getAurant_city().isEmpty()) {
                    specialRestEntity.setAurant_city(json.getString("cityname"));
                }
                if (specialRestEntity.getAurant_district() == null || specialRestEntity.getAurant_district().isEmpty()) {
                    specialRestEntity.setAurant_district(json.getString("adname"));
                }
                if (json.getString("location") != null && !ignoreStr.equals(json.getString("location"))) {
                    String[] location = json.getString("location").split(",");
                    specialRestEntity.setLatitude(location[1]);
                    specialRestEntity.setLongitude(location[0]);
                }
                if (specialRestEntity.getContact_phone() == null && json.getString("tel") != null && !ignoreStr.equals(json.getString("tel"))) {
                    String[] phones = json.getString("tel").split(";");
                    specialRestEntity.setContact_phone(phones[0]);
                }
                if (json.getString("photos") != null && !ignoreStr.equals(json.getString("photos"))) {
                    JSONArray photos = json.getJSONArray("photos");
                    specialRestEntity.setOutdoorUrl(photos.getJSONObject(0).getString("url"));
                    for (int i = 1; i < photos.size(); i++) {
                        if (specialRestEntity.getIndoorUrls() == null) {
                            specialRestEntity.setIndoorUrls(photos.getJSONObject(i).getString("url"));
                        } else {
                            specialRestEntity.setIndoorUrls(
                                    specialRestEntity.getIndoorUrls() + "," + photos.getJSONObject(i).getString("url"));
                        }
                    }
                }
                if (json.getString("biz_ext") != null && !ignoreStr.equals(json.getString("biz_ext"))) {
                    JSONObject biz_ext = json.getJSONObject("biz_ext");
                    specialRestEntity.setRating(ignoreStr.equals(biz_ext.getString("rating")) ? null : biz_ext.getString("rating"));
                    specialRestEntity.setPrice(ignoreStr.equals(biz_ext.getString("cost")) ? null : biz_ext.getString("cost"));
                }
                String[] arr = json.getString("type").split(";");
                specialRestEntity.setCategory(null);
                if (arr != null) {
                    for (String string : arr) {
                        specialRestEntity.setCategory(specialRestEntity.getCategory() == null ? string
                                        : (specialRestEntity.getCategory().indexOf(string) != -1
                                                ? specialRestEntity.getCategory()
                                                : specialRestEntity.getCategory() + "|" + string));
                    }
                }
            }
            specialRspository.save(specialRestEntities);
        }while(specialRestEntities.hasNext()) ;
        //验证无效数据(无高德id,无company_id)
        
        //验证是否重复-导入表数据重复/与线上重复
    }
}
