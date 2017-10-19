package com.quancheng.achilles.service.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.quancheng.achilles.dao.model.InputReceipt;
import com.quancheng.achilles.dao.model.InputRestaurantAccountInfo;
import com.quancheng.achilles.dao.modelwrite.DataItemDetail;
import com.quancheng.achilles.dao.repository.ReceiptRepository;
import com.quancheng.achilles.dao.repository.RestAccountInfoRepository;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.impl.DataItemServiceImpl;
import com.quancheng.achilles.service.utils.TimeUtil;
/**
 * 账户信息用于企业后台
 * @author zhuzhong
 *
 */
@Controller
@RequestMapping(path = "/ops")
public class OrderAccountInfoController extends ControllerAbstract {
    @Autowired
    RestAccountInfoRepository restAccountInfoRepository;
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    @Autowired
    ReceiptRepository receiptRepository;
    
    @RequestMapping(value = "/account/info/edit", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbEdit( String orderNum, ModelAndView mv) { 
        if(orderNum!= null ){
            InputRestaurantAccountInfo orderSpecialMark =restAccountInfoRepository.findOne(orderNum);
            mv.addObject("editInfo", orderSpecialMark);
        }
        mv.addObject("accountTypeList", dataItemServiceImpl.getDataItemDetail("ACCOUNT_TYPE"));
        mv.addObject("manageTypeList", dataItemServiceImpl.getDataItemDetail("MANAGE_TYPE"));
        mv.setViewName("rest_account_info/account_edit");
        return mv;
    }
    
    @RequestMapping(value = "/account/info/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbList(  
            String orderNum, 
             @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
             @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            ModelAndView mv) { 
        Page<InputRestaurantAccountInfo> page = restAccountInfoRepository.findAll(Specifications.where(new Specification<InputRestaurantAccountInfo>() {
            public Predicate toPredicate(Root<InputRestaurantAccountInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return orderNum==null||orderNum.isEmpty()?null:cb.equal(root.get("orderNum"), orderNum);
            }
        }),new PageRequest(pageNum,pageSize,Direction.DESC,"orderNum" ));
        mv.addObject("page", page);
        mv.addObject("accountTypeList", convert(dataItemServiceImpl.getDataItemDetail("ACCOUNT_TYPE")));
        mv.addObject("orderNum", orderNum);
        mv.setViewName("rest_account_info/account_list");
        return mv;
    }
    private Map<Object,String> convert(List<DataItemDetail> items){
        Map<Object,String> maps=new HashMap<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            maps.put(items.get(i).getDetailKey(), items.get(i).getDetailText());
        }
        return maps;
    }
    @RequestMapping(value = "/account/info/save", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView ucbSave( InputRestaurantAccountInfo uu,ModelAndView mv) { 
        restAccountInfoRepository.save(uu);
        mv.setViewName("redirect:/ops/account/info/list");
        return mv;
    }
    /*代收款证明*/
    @RequestMapping(value = "/receipt/save", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView receiptSave( InputReceipt uu,ModelAndView mv) { 
        uu.setPt(TimeUtil.formatDate("yyyyMMdd", new Date()));
        receiptRepository.save(uu);
        mv.setViewName("redirect:/ops/receipt/list");
        return mv;
    }
    
    @RequestMapping(value = "/receipt/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView receiptList(  
            String orderNum, 
             @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
             @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            ModelAndView mv) { 
        Page<InputReceipt> page = receiptRepository.findAll(Specifications.where(new Specification<InputReceipt>() {
            public Predicate toPredicate(Root<InputReceipt> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return orderNum==null||orderNum.isEmpty()?null:cb.equal(root.get("orderNum"), orderNum);
            }
        }),new PageRequest(pageNum,pageSize,Direction.DESC,"orderNum" ));
        mv.addObject("page", page);
        mv.addObject("orderNum", orderNum);
        mv.setViewName("rest_account_info/receipt_list");
        return mv;
    }
    
    @RequestMapping(value = "/receipt/edit", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView receiptEdit(   String orderNum ,  ModelAndView mv) { 
        if (orderNum!= null && !orderNum.isEmpty()) {
            InputReceipt editInfo = receiptRepository.findOne(orderNum);
            mv.addObject("editInfo", editInfo);
        }
        mv.setViewName("rest_account_info/receipt_edit");
        return mv;
    }
    
}
