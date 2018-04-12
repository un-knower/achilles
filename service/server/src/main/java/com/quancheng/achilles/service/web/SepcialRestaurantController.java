package com.quancheng.achilles.service.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quancheng.achilles.dao.quancheng_db.model.SepicalRestaurant;
import com.quancheng.achilles.dao.quancheng_db.repository.SepcialRestaurantRepository;
import com.quancheng.achilles.service.model.OdpsBaseResponse;
import com.quancheng.achilles.service.services.impl.DataItemServiceImpl;
import com.quancheng.achilles.util.GouldApiUtil;
@Controller
@RequestMapping(path = "/sepcial")
public class SepcialRestaurantController {
    
    @Autowired
    SepcialRestaurantRepository sepcialRestaurantRepository;
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    
    @RequestMapping(value = "/restaurant/edit", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView edit( 
            String gouldId, 
            String companyId,
            ModelAndView mv) { 
        SepicalRestaurant sr =  gouldId==null||companyId==null?null:sepcialRestaurantRepository.findOne(Specifications.where(new Specification<SepicalRestaurant>() {
                public Predicate toPredicate(Root<SepicalRestaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("gouldId"), gouldId);
                }
            }).and(new Specification<SepicalRestaurant>() {
                public Predicate toPredicate(Root<SepicalRestaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("companyId"), companyId);
                }
            }));
        if(sr!=null) {
            mv.addObject("editInfo", sr);
        }
        mv.addObject("companyList", dataItemServiceImpl.getDataItemDetail("ALL_CLIENT_LIST"));
        mv.setViewName("sepical/restaurant_edit");
        return mv;
    } 
    @RequestMapping(value = "/restaurant/save", method = {  RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public OdpsBaseResponse<Object> save(  String id, String gouldId, String restaurantName, String restaurantAddress, String restaurantCity, String gonghaiId, String isOnline, String companyId) { 
        if(StringUtils.isEmpty(gouldId)||StringUtils.isEmpty(companyId)) {
            return OdpsBaseResponse.error("无效高德ID或企业");
        }
        SepicalRestaurant specialRestEntity =  sepcialRestaurantRepository.findOne(Specifications.where(new Specification<SepicalRestaurant>() {
                public Predicate toPredicate(Root<SepicalRestaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("gouldId"), gouldId.trim());
                }
            }).and(new Specification<SepicalRestaurant>() {
                public Predicate toPredicate(Root<SepicalRestaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("companyId"), companyId.trim());
                }
            }));
        if(specialRestEntity!=null) {
            return OdpsBaseResponse.error("重复餐厅");
        }
        JSONObject json = GouldApiUtil.gouldByID(gouldId.trim());
        if (json == null) {
            return OdpsBaseResponse.error("无效高德ID");
        }
        if(!StringUtils.isEmpty(id)) {
            specialRestEntity=sepcialRestaurantRepository.findOne(id);
        }else {
            specialRestEntity = new SepicalRestaurant();
            Date now = new Date();
            Calendar cl = Calendar.getInstance();
            int idPrex=(2700+cl.get(Calendar.HOUR_OF_DAY))*100000+ cl.get(Calendar.MINUTE)*1000+cl.get(Calendar.MILLISECOND);
            specialRestEntity.setId("restaurant"+new SimpleDateFormat("yyMMdd").format(now)+""+idPrex);
            specialRestEntity.setGouldId(gouldId.trim());
            specialRestEntity.setCompanyId(companyId);
            specialRestEntity.setGonghaiId(gonghaiId.trim());
            specialRestEntity.setAurantAddress(restaurantAddress.trim());
            specialRestEntity.setAurantCity(restaurantCity.trim());
            specialRestEntity.setType("autoPass");
            specialRestEntity.setStatus("success");
            specialRestEntity.setAurantName(restaurantName.trim());
            specialRestEntity.setGmtCreated(now);
            specialRestEntity.setGmtModified(now);
        }
        if (specialRestEntity.getAurantCity() == null || specialRestEntity.getAurantCity().isEmpty()) {
            specialRestEntity.setAurantCity(json.getString("cityname"));
        }
        if (specialRestEntity.getAurantDistrict() == null || specialRestEntity.getAurantDistrict().isEmpty()) {
            specialRestEntity.setAurantDistrict(json.getString("adname"));
        }
        if (json.getString("location") != null && !"[]".equals(json.getString("location"))) {
            String[] location = json.getString("location").split(",");
            specialRestEntity.setLatitude(location[1]);
            specialRestEntity.setLongitude(location[0]);
        }
        if (specialRestEntity.getContactPhone() == null && json.getString("tel") != null
                && !"[]".equals(json.getString("tel"))) {
            String[] phones = json.getString("tel").split(";");
            specialRestEntity.setContactPhone(phones[0]);
        }
        if (json.getString("photos") != null && !"[]".equals(json.getString("photos"))) {
            JSONArray photos = json.getJSONArray("photos");
            specialRestEntity.setOutdoorUrl(photos.getJSONObject(0).getString("url"));
            for (int i = 1; i < photos.size(); i++) {
                if (specialRestEntity.getIndoorUrls() == null) {
                    specialRestEntity.setIndoorUrls(photos.getJSONObject(i).getString("url"));
                } else {
                    specialRestEntity.setIndoorUrls( specialRestEntity.getIndoorUrls() + "," + photos.getJSONObject(i).getString("url"));
                }
            }
        }
        if (json.getString("biz_ext") != null && !"[]".equals(json.getString("biz_ext"))) {
            JSONObject biz_ext = json.getJSONObject("biz_ext");
            specialRestEntity.setRating("[]".equals(biz_ext.getString("rating")) ? null : biz_ext.getString("rating"));
            specialRestEntity.setPrice("[]".equals(biz_ext.getString("cost")) ? null : biz_ext.getString("cost"));
        }
        String[] arr = json.getString("type").split(";");
        specialRestEntity.setCategory(null);
        if (arr != null) {
            for (String string : arr) {
                specialRestEntity.setCategory(specialRestEntity.getCategory() == null ? string : (specialRestEntity.getCategory().indexOf(string) != -1  ? specialRestEntity.getCategory() : specialRestEntity.getCategory() + "|" + string));
            }
        }
        sepcialRestaurantRepository.save(specialRestEntity);
        sepcialRestaurantRepository.updateCaciaOnlineStatus(specialRestEntity.getId());
        return OdpsBaseResponse.success("DONE");
    } 
}
