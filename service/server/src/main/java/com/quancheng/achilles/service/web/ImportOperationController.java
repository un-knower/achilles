package com.quancheng.achilles.service.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.quancheng.achilles.dao.quancheng_db.model.UcbUser;
import com.quancheng.achilles.dao.quancheng_db.repository.UcbUserRepository;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.impl.DataItemServiceImpl;
import io.swagger.annotations.ApiParam;
@Controller
@RequestMapping(path = "/ops")
public class ImportOperationController extends ControllerAbstract {
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    @Autowired
    UcbUserRepository ucbUserRepository;
    
    @RequestMapping(value = "/import/ucb/user", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbEdit(  ModelAndView mv) { 
        mv.addObject("cityList",     dataItemServiceImpl.getDataItemDetail("ALL_CITY_LIST"));
        mv.addObject("sectorList", dataItemServiceImpl.getDataItemDetail("UCB_SECTORS"));
        mv.addObject("regionList", dataItemServiceImpl.getDataItemDetail("UCB_REGIONS"));
        mv.setViewName("import/ucb_user");
        return mv;
    }
    
    @RequestMapping(value = "/import/ucb/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbList(  
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            ModelAndView mv) { 
        Page<UcbUser> page = ucbUserRepository.findAll(new PageRequest(pageNum,pageSize,Direction.DESC,"id" ));
        mv.addObject("page", page);
        mv.setViewName("import/ucb_user_list");
        return mv;
    }
    
    @RequestMapping(value = "/import/ucb/save", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbSave( UcbUser uu,ModelAndView mv) { 
        List<UcbUser> list =ucbUserRepository.findAll(Specifications.where(new Specification<UcbUser>() {
            public Predicate toPredicate(Root<UcbUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("jobNum"), uu.getJobNum());
            }
        }));
        if(list== null || list.isEmpty()){
            String dt =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            uu.setUpdatedAt(dt);
            uu.setCreatedAt(dt);
            ucbUserRepository.save(uu);
        }
        mv.setViewName("redirect:import/ucb_user_list");
        return mv;
    }
}
