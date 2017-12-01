package com.quancheng.achilles.service.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.dao.quancheng_db.model.SytHospital;
import com.quancheng.achilles.dao.quancheng_db.model.SytHospitalRelation;
import com.quancheng.achilles.dao.quancheng_db.repository.SytHospitalRelationRepository;
import com.quancheng.achilles.dao.quancheng_db.repository.SytHospitalRepository;
import com.quancheng.achilles.service.model.ChartDataResp;
import com.quancheng.achilles.service.model.PageInfo;
import com.quancheng.achilles.service.services.DataImportService;
import com.quancheng.achilles.util.BaiduApiUtil;
import com.quancheng.achilles.util.model.PoiInfo;

@Service
public class ImportToOnlineDbServiceImpl {
    
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    DataImportService dataImportService;
    
    @Value("${hospital.api.key}")
    String hospitalCareKey;

    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    
    @Autowired
    SytHospitalRelationRepository sytHospitalRelationRepository;
    
    @Autowired
    SytHospitalRepository sytHospitalRepository;
    
    public void calculate(Long clientId,Long dorisTableId,String targetTable,String apiType){
        switch (targetTable) {
        case "hospital":
            importHospital(clientId, dorisTableId, apiType);
            break;
        }
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void importHospital(Long clientId,Long dorisTableId ,String apiType){
        Map<String,String[]> paramaters = new HashMap<>();
        paramaters.put("pageSize", new String[]{"200"});
        List<DorisTableColumns>cols = dorisTableServiceImpl.getTableColumns(dorisTableId);
        DorisTableInfo table= dorisTableServiceImpl.dorisTableInfo(dorisTableId);
        ChartDataResp cdr =null;
        Map<Object,Object> cities = dataItemServiceImpl.getDataItemDetailToMap("ALL_CITY_LIST");
        Map<Object,Object> cityMap = dataItemServiceImpl.getDataItemDetailReverseMap("ALL_CITY_LIST");
        Map<Object,Object> areaMap = dataItemServiceImpl.getDataItemDetailReverseMap("ALL_AREA");
        Map<Object,Object> provinceMap = dataItemServiceImpl.getDataItemDetailReverseMap("ALL_PROVINCE");
        String date = df.format(new Date());
        do{
            cdr = dataImportService.dataView(paramaters, cols, table);
            cdr.getDataList().stream().forEach(new Consumer<Map<String, Object>>() {
                public void accept(Map<String, Object> t) {
                    List<SytHospital> hospitals= sytHospitalRepository.findAll(new Specification<SytHospital>() {
                        public Predicate toPredicate(Root<SytHospital> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                            return cb.equal(root.get("name"), getValue(t,"name",String.class));
                        }
                    });
                    SytHospital hospital = hospitals==null||hospitals.isEmpty()?null:hospitals.get(0);
                    if(hospital!=null){
                        final Long id = hospital.getId();
                        System.out.println(id);
                        List<SytHospitalRelation>  srList = sytHospitalRelationRepository.findAll(Specifications.where(new Specification<SytHospitalRelation>() {
                            public Predicate toPredicate(Root<SytHospitalRelation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                return cb.equal(root.get("hospitalId"),id);
                            }
                        }).and(new Specification<SytHospitalRelation>() {
                            public Predicate toPredicate(Root<SytHospitalRelation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                return cb.equal(root.get("clientId"), clientId);
                            }
                        }).and(new Specification<SytHospitalRelation>() {
                            public Predicate toPredicate(Root<SytHospitalRelation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                return cb.isNull(root.get("deletedAt"));
                            }
                        })
                                );
                        SytHospitalRelation sr = null;
                        if( srList==null || srList.isEmpty()){
                            sytHospitalRelationRepository.save(new SytHospitalRelation(hospital.getId(),clientId,0L,hospital.getCreatedAt(),hospital.getCreatedAt()));
                        } else if(srList!=null && !srList.isEmpty()){
                            sr = srList.get(0);
                            sr.setDeletedAt(null);
                            sr.setUpdatedAt(date);
                            sytHospitalRelationRepository.save(sr);
                        }
                        return ;
                    }
                    Object obj = cities.get(t.get("city"));
                    String city = obj==null?null:obj.toString();
                    hospital = new SytHospital();
                    hospital.setName(getValue(t,"name",String.class));
                    hospital.setAddress(getValue(t,"address",String.class,""));
                    hospital.setArea(getValue(t,"area",Integer.class,0));
                    hospital.setCity(getValue(t,"city",Integer.class,0));
                    hospital.setProvince(getValue(t,"province",Integer.class,0));
                    hospital.setContact(getValue(t,"contact",String.class,""));
                    hospital.setCreatedAt(getValue(t,"created_at",Object.class)==null?date:getValue(t,"created_at",Object.class).toString());
                    hospital.setUpdatedAt(getValue(t,"created_at",Object.class)==null?date:getValue(t,"created_at",Object.class).toString());
                    hospital.setLevel(getValue(t,"level",String.class,"0") );
                    hospital.setUserId(getValue(t,"user_id",Integer.class,0) );
                    hospital.setType(getValue(t,"type",String.class));
                    if(getValue(t,"lng",String.class)==null || getValue(t,"lat",String.class)==null ){
                        PoiInfo pi =BaiduApiUtil.getHospitalPoiInfoFromBaiduToBean( t.get("name").toString(),t.get("name").toString(), city, hospitalCareKey);
                        if(pi == null && t.get("address")  != null){
                            pi =BaiduApiUtil.getHospitalPoiInfoFromBaiduToBean( t.get("address").toString(),t.get("name").toString(), city, hospitalCareKey);
                        }
                        if(pi != null){
                            hospital.setLat(pi.getLat());
                            hospital.setLng(pi.getLng());
                            if(hospital.getAddress()==null || hospital.getAddress().isEmpty()){
                                hospital.setAddress(pi.getAddress());
                            }
                            if((hospital.getCity()==0||hospital.getCity()==null) && pi.getCity() != null){
                                hospital.setCity(cityMap.get(pi.getCity())==null?0:Integer.parseInt(cityMap.get(pi.getCity()).toString()));
                            }
                            if((hospital.getArea()==0 || hospital.getArea()==null) && pi.getArea() != null){
                                hospital.setArea(areaMap.get(pi.getArea())==null?0:Integer.parseInt(areaMap.get(pi.getArea()).toString()));
                            }
                            if((hospital.getProvince()==0 || hospital.getProvince()==null) && pi.getProvince()!= null){
                                hospital.setProvince(provinceMap.get(pi.getProvince())==null?0:Integer.parseInt(provinceMap.get(pi.getProvince()).toString()));
                            }
                        }
                    }else{
                        hospital.setLat(getValue(t,"lat",String.class));
                        hospital.setLng(getValue(t,"lng",String.class));
                    }
                    hospital.setStatus(1);
//                    list.add(hospital);
                    sytHospitalRepository.save(hospital);
                    sytHospitalRelationRepository.save(new SytHospitalRelation(hospital.getId(),clientId,0L,hospital.getCreatedAt(),hospital.getCreatedAt()));
                }
            });
            try {
//                save(list, listRel,clientId);
            } catch (Exception e) {
                
            }finally{
                if(cdr.getPageInfo().hasNext()){
                    PageInfo page = cdr.getPageInfo().next();
                    paramaters.put("pageNum", new String[]{page.getNumber()+""});
                }
            }
        }while(cdr.getPageInfo().hasNext());
    }
    public void save(List<SytHospital> list,List<SytHospitalRelation> listRel,Long clientId){
        try {
            sytHospitalRepository.save(list);
            for (SytHospital sytHospital : list) {
                listRel.add(new SytHospitalRelation(sytHospital.getId(),clientId,0L,sytHospital.getCreatedAt(),sytHospital.getCreatedAt()));
            }
            sytHospitalRelationRepository.save(listRel);
            list.clear();
            listRel.clear();
        } catch (Exception e) {
            List<String> errorHospitalList= new ArrayList<>();
            for (SytHospital sytHospital : list) {
                if(sytHospital.getId()==null){
                    errorHospitalList.add(sytHospital.getName());
                }
            }
            logger.error("error message:{},cause:{}",e.getMessage(),e.getCause()!=null?"nothing":e.getCause().getMessage());
            throw e;
        }
    }
    @SuppressWarnings("unchecked")
    public <T> T getValue(Map<String,Object> map,String key,Class<T> t){
        Object obj = map.get(key);
        return obj==null?null:(T)obj;
    }
    
    public <T> T getValue(Map<String,Object> map,String key,Class<T> t,T defaultValue){
       T tt=  getValue(map, key, t);
        return tt==null?defaultValue:tt;
    }
}
