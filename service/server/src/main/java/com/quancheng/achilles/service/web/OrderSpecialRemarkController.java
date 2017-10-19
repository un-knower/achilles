package com.quancheng.achilles.service.web;

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
import com.quancheng.achilles.dao.model.API_OrderSpecialMark;
import com.quancheng.achilles.dao.repository.API_OrderSpecialMarkRepository;
import com.quancheng.achilles.service.constants.InnConstantPage;
/**
 * 订单特殊备注 用于企业后台
 * @author zhuzhong
 *
 */
@Controller
@RequestMapping(path = "/ops")
public class OrderSpecialRemarkController extends ControllerAbstract {
    @Autowired
    API_OrderSpecialMarkRepository orderSpecialRepository;
    
    @RequestMapping(value = "/order/special/mark/edit", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbEdit( String orderNum, ModelAndView mv) { 
        if(orderNum!= null ){
            API_OrderSpecialMark orderSpecialMark =orderSpecialRepository.findOne(orderNum);
            mv.addObject("orderInfo", orderSpecialMark);
        }
        mv.setViewName("order_special/special_edit");
        return mv;
    }
    
    @RequestMapping(value = "/order/special/mark/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbList(  
            String orderNum, 
             @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
             @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            ModelAndView mv) { 
        Page<API_OrderSpecialMark> page = orderSpecialRepository.findAll(Specifications.where(new Specification<API_OrderSpecialMark>() {
            public Predicate toPredicate(Root<API_OrderSpecialMark> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return orderNum==null||orderNum.isEmpty()?null:cb.equal(root.get("orderNum"), orderNum);
            }
        }),new PageRequest(pageNum,pageSize,Direction.DESC,"orderNum" ));
        mv.addObject("page", page);
        mv.addObject("orderNum", orderNum);
        mv.setViewName("order_special/special_list");
        return mv;
    }
    
    @RequestMapping(value = "/order/special/mark/save", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbSave( API_OrderSpecialMark uu,ModelAndView mv) { 
        API_OrderSpecialMark orderSpecialMark =orderSpecialRepository.findOne(uu.getOrderNum());
        orderSpecialMark = orderSpecialMark== null ?uu: orderSpecialMark.setRemarks(uu.getRemarks());
        orderSpecialRepository.save(orderSpecialMark);
        mv.setViewName("redirect:/ops/order/special/mark/list");
        return mv;
    }
}
