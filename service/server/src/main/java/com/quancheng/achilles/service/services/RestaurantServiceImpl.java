package com.quancheng.achilles.service.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quancheng.achilles.dao.repository.ClientRepository;
import com.quancheng.achilles.dao.repository.RegionRepository;
import com.quancheng.achilles.dao.repository.RestaurantGHRepository;
import com.quancheng.achilles.dao.repository.RestaurantOwnCompanyRepository;
import com.quancheng.achilles.dao.repository.RestaurantRecommenderRepository;
import com.quancheng.achilles.dao.repository.SalesRepository;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.model.Client;
import com.quancheng.achilles.dao.model.RestaurantGonghai;
import com.quancheng.achilles.dao.model.RestaurantOwnCompany;
import com.quancheng.achilles.dao.model.RestaurantOwnCompanyRelation;
import com.quancheng.achilles.dao.model.SalesDomain;

@Service
@Transactional(readOnly = true)
public class RestaurantServiceImpl extends RestaurantServiceAbstract<RestaurantGonghai>{
    
    @Autowired
    RestaurantGHRepository callRecordRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RegionRepository regionRep;
    @Autowired
    RestaurantRecommenderRepository rrRepository;
    @Autowired
    SalesRepository salesRespository;
    @Autowired
    RestaurantOwnCompanyRepository rocRepository;
    
    public Page<RestaurantGonghai> pageGhRest(RestaurantGonghai rest,
            String inStoreDateStart,
            String inStoreDateEnd,
            String shelfTimeStart,
            String shelfTimeEnd,
            Pageable pageable){
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date inStoreEnd = null;
        Date inStoreStart = null;
        Date shelfEnd = null;
        Date shelfStart = null;
        try {
            if(!isEmpty(inStoreDateStart) && !isEmpty(inStoreDateEnd)){
                inStoreStart=df.parse(inStoreDateStart);
                Calendar cl = Calendar.getInstance();
                cl.setTime(df.parse(inStoreDateEnd));
                cl.add(Calendar.DAY_OF_YEAR, 1);
                inStoreEnd=cl.getTime();
            }
            if(!isEmpty(shelfTimeStart) && !isEmpty(shelfTimeEnd)){
                shelfStart=df.parse(shelfTimeStart);
                Calendar cl = Calendar.getInstance();
                cl.setTime(df.parse(shelfTimeEnd));
                cl.add(Calendar.DAY_OF_YEAR, 1);
                shelfEnd=cl.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Specifications<RestaurantGonghai> specif= Specifications.where(like("storeName" , rest.getStoreName()))//餐厅名称
                .and(in("cityDisplay",rest.getCity()==null ? null : rest.getCity().split(",")))//城市
                .and(equal("ownCompanys", rest.getOwnCompanys()))//所属公司
                .and(equal("restaurantState", rest.getRestaurantState()))//餐厅状态
                .and(equal("restaurantSources", rest.getRestaurantSources()))//餐厅来源
                .and(like("ghTaskName", rest.getGhTaskName()))//公海名称
                .and(like("recommendsEmails",rest.getRecommendsEmails()))//推荐人邮箱
                .and(equal("recommendsCompany", rest.getRecommendsCompany()))//推荐企业
                .and(like("projectNames",rest.getProjectNames()))//项目经常
                .and(equal("supportTakeoutOfFood", rest.getSupportTakeoutOfFood()))//是否支持外卖
                .and(equal("supportReserve", rest.getSupportReserve()))//是否支持订座
                .and(equal("salesName", rest.getSalesName()))//销售
                .and(between("inStoreAt",inStoreStart,inStoreEnd))//入库时间
                .and(between("shelfTime",shelfStart,shelfEnd))//下线时间
        			.and(exists(rest.getOwnCompanysId()));
        return convert(callRecordRepository.findAll(specif, pageable),rest.getOwnCompanysId());
    }
    
    Specification<RestaurantGonghai> exists(String companyid){
  	  return companyid==null||companyid.isEmpty()?null:new Specification<RestaurantGonghai>(){
  			public Predicate toPredicate(Root<RestaurantGonghai> arg0, CriteriaQuery<?> arg1, CriteriaBuilder cb) {
  				Subquery<RestaurantOwnCompanyRelation> subquery=arg1.subquery(RestaurantOwnCompanyRelation.class);
  				Root<RestaurantOwnCompanyRelation>  root =subquery.from(RestaurantOwnCompanyRelation.class);
  				subquery.select(root);
  				List<Predicate> subQueryPredicates = new ArrayList<Predicate>(); 
  				subQueryPredicates.add(cb.equal(root.get("olRestaurantId"),arg0.get("olRestaurantId")));
  				subQueryPredicates.add(cb.equal(root.get("clientId"), companyid));
  				subQueryPredicates.add(cb.isNull(root.get("deleteAt")));
  				subquery.where(subQueryPredicates.toArray(new Predicate[]{}));
  				return cb.exists(subquery);
  			}
  	    	};
      }
    
    public Page<RestaurantGonghai> pageOlRest(RestaurantGonghai rest,
            String colunm,
            String start,
            String end,
            Pageable pageable){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date shelfEnd = null;
        Date shelfStart = null;
        try {
            if(!isEmpty(start) && !isEmpty(end)){
                shelfStart=df.parse( start);
                Calendar cl = Calendar.getInstance();
                cl.setTime(df.parse(end));
                cl.add(Calendar.DAY_OF_YEAR, 1);
                shelfEnd=cl.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Specifications<RestaurantGonghai> specif= Specifications.
                where(between(colunm,shelfStart,shelfEnd))//入库时间
//                .and(notNull("olTime"))
                .and(equal("restaurantState", rest.getRestaurantState()))
                .and(in("cityDisplay",rest.getCity()==null ? null : rest.getCity().split(",")))
                .and(equal("supportTakeoutOfFood", rest.getSupportTakeoutOfFood()))//是否支持外卖
                .and(equal("supportReserve", rest.getSupportReserve()))//是否支持订座
                .and(equal("ownCompanys", rest.getOwnCompanys())) //所属公司
                .and(exists(rest.getOwnCompanysId()));
        return convert(callRecordRepository.findAll(specif, pageable),rest.getOwnCompanysId());
    }
    
    public Page<RestaurantGonghai> pageOnlineRest(RestaurantGonghai rest,
            String colunm,
            String start,
            String end,
            Pageable pageable){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date shelfEnd = null;
        Date shelfStart = null;
        try {
            if(!isEmpty(start) && !isEmpty(end)){
                shelfStart=df.parse( start);
                Calendar cl = Calendar.getInstance();
                cl.setTime(df.parse(end));
                cl.add(Calendar.DAY_OF_YEAR, 1);
                shelfEnd=cl.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Specifications<RestaurantGonghai> specif= Specifications.where(between(colunm,shelfStart,shelfEnd))//入库时间
                .and(notNull("olRestaurantId"))
                .and(equal("ownCompanys", rest.getOwnCompanys()))//所属公司
                .and(equal("restaurantState",rest.getRestaurantState() ))
                .and(exists(rest.getOwnCompanysId()));
        return convert(callRecordRepository.findAll(specif, pageable),rest.getOwnCompanysId());
    }
    public Page<RestaurantGonghai> convert(Page<RestaurantGonghai> page , String companyId){
    		List<RestaurantGonghai> list = page.getContent();
    		List<Long> restaurantIds = new ArrayList<>(list.size());
    		if(list.size() <=InnConstantPage.PAGE_SIZE_NUMBER){
        		for (RestaurantGonghai restaurantGonghai : list) {
        			if(restaurantGonghai.getOlRestaurantId() != null){
        				restaurantIds.add(restaurantGonghai.getOlRestaurantId());
        			}
        		}
    		}
    		List<RestaurantOwnCompany> ownCompanys =null;
    		if(list.size()>InnConstantPage.PAGE_SIZE_NUMBER){
    			ownCompanys=rocRepository.findAll();
    		}else  if(companyId != null){
    			ownCompanys = rocRepository.findAllByRestIds(restaurantIds,companyId);
    		}else if(!restaurantIds.isEmpty()){
    			ownCompanys = rocRepository.findAllByRestIds(restaurantIds);
    		}else{
    			return page;
    		}
    		
    		Map<Long,String> ownCompanysMap = new HashMap<>();
    		for(RestaurantOwnCompany roc:ownCompanys){
    			ownCompanysMap.put(roc.getOlRestaurantId(), 
    					ownCompanysMap.containsKey(roc.getOlRestaurantId()) 
    					? ownCompanysMap.get(roc.getOlRestaurantId())+" , "+roc.getOwnCompanys()
    					:roc.getOwnCompanys());
    		}
    		for (RestaurantGonghai restaurantGonghai : list) {
    			restaurantGonghai.setOwnCompanys(ownCompanysMap.get(restaurantGonghai.getOlRestaurantId()));
		}
    		return page;
    }
    
    public List<Client> queryAllCompanys(){
        return clientRepository.findAll();
    }
    public List<String> queryAllCitys(){
        return callRecordRepository.listAllCity();
    }
    
    public List<SalesDomain> querySales(){
        return salesRespository.getAllSales();
    }
}
