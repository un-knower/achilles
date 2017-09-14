package com.quancheng.achilles.service.web;

//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.thymeleaf.dialect.springdata.util.Strings;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.quancheng.achilles.service.constants.InnConstantPage;
//import com.quancheng.achilles.service.constants.InnConstantsOrderStatus;
//import com.quancheng.achilles.service.services.OrderQueryService;
//import com.quancheng.achilles.service.services.RestaurantServiceImpl;
//import com.quancheng.achilles.dao.model.BaseResponse;
//import com.quancheng.achilles.dao.model.CompanyDomain;
//import com.quancheng.achilles.dao.model.OrderRecordVo;
//import com.quancheng.achilles.dao.model.SalesDomain;
//import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
//import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
//import com.quancheng.achilles.service.utils.BatisPage;
//import com.quancheng.achilles.service.utils.DownloadBuilder;
//import com.quancheng.achilles.service.utils.OssServiceDBUtil;
//
//import io.swagger.annotations.ApiParam;
//
///**
// * @author liujiejian
// * @version 2016年9月10日
// */
//@Controller
//@RequestMapping(path = "/ops")
//public class OrderQueryController extends ControllerAbstract{
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(OrderQueryController.class);
//    
//    @Autowired
//    OrderQueryService orderQueryService;
//    @Autowired
//    RestaurantServiceImpl restService;
//
//    @RequestMapping(value = "/orderquery/{scope}", method = RequestMethod.GET, produces = {
//            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
//    public ModelAndView queryPageOrders(@PathVariable("scope") String scope,
//            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "startTime", required = false) String startTime,
//            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "endTime", required = false) String endTime,
//            @ApiParam(value = "业务类型") @RequestParam(value = "serviceType", required = false) String serviceType,
//            @ApiParam(value = "时间类型") @RequestParam(value = "timeType", required = false) String timeType,
//            @ApiParam(value = "订单类型") @RequestParam(value = "orderType", required = false) String orderType,
//            @ApiParam(value = "付款类型") @RequestParam(value = "payType", required = false) String payType,
//            @ApiParam(value = "城市") @RequestParam(value = "city", required = false, defaultValue = "-1") String city,
//            @ApiParam(value = "订单状态") @RequestParam(value = "orderStatus", required = false) String[] orderStatus,
//            @ApiParam(value = "维护销售") @RequestParam(value = "sales", required = false) Integer sales,
//            @ApiParam(value = "搜索关键字") @RequestParam(value = "searchKey", required = false) String searchKey,
//            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false) Integer company,
//            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
//            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
//            Long templateId,
//            ModelAndView mv) {
//        // do some magic!
//        BatisPage<OrderRecordVo> page = new BatisPage<OrderRecordVo>(pageNum, pageSize);
//        Map<String, Object> params = generateParams(startTime, endTime, timeType, serviceType, orderType, payType,
//                pageSize, pageNum, company, city, 
//                orderStatus==null?null:InnConstantsOrderStatus.getList(orderStatus).toArray(new String[orderStatus.length]), 
//                sales);
//        params.put("searchKey", searchKey);
//        List<OrderRecordVo> orders = orderQueryService.queryPageOrders(params);
//        List<CompanyDomain> allCompany = orderQueryService.getAllCompany();
//        convert2Vo(orders, allCompany,scope);
//        page.setContent(orders);
//        page.setTotal(orderQueryService.queryPageOrdersCount(params));
//        
//        List<SalesDomain> allSales = orderQueryService.getAllSales();
//        List<String> citylist = restService.queryAllCitys();
//        mv.setViewName("orderquery/index-" + scope);
//        mv.addObject("serviceType", serviceType);
//        mv.addObject("companyList", allCompany);
//        mv.addObject("salesList", allSales);
//        mv.addObject("orderType", orderType);
//        mv.addObject("startTime", startTime);
//        mv.addObject("cityList", citylist);
//        mv.addObject("timeType", timeType);
//        mv.addObject("payType", payType);
//        mv.addObject("endTime", endTime);
//        mv.addObject("company", company);
//        mv.addObject("searchKey", searchKey);
//        mv.addObject("orderStatus", orderStatus==null?new String[1]:orderStatus);
//        mv.addObject("orderStatusMap", InnConstantsOrderStatus.getMap());
//        mv.addObject("sales", sales);
//        mv.addObject("page", page);
//        mv.addObject("city", city.split(","));
//        configTemplate(templateId, mv);
//        return mv;
//    }
//
//    private void convert2Vo(List<OrderRecordVo> orders, List<CompanyDomain> allCompany,String scope) {
//    		Map<String, String> titles = new HashMap<>(allCompany.size());
//		for (CompanyDomain cd : allCompany) {
//			titles.put(cd.getId()+"", cd.getTitle());
//		}
//        for (OrderRecordVo vo : orders) {
//            try {
//				vo.setIsDelivery(getBooleanString(vo.getIsDelivery()));
//				vo.setIsRoom(getBooleanString(vo.getIsRoom()));
//				vo.setIsRestaurantFirst(getBooleanString(vo.getIsRestaurantFirst()));
//				vo.setIsScore(getBooleanString(vo.getIsScore()));
//				vo.setServiceType(getServiceTypeCN(vo.getServiceType()));
//				vo.setPayType(getPayType(vo.getPayType()));
//				vo.setOrderSatus(handleStatus(vo.getStatus()));
//				vo.setCityName(vo.getCityName());
//				vo.setIsHall(getBooleanString(vo.getIsHall()));
//				if (vo.getYuyueTime() != null && vo.getCreateTime() != null) {
//				    vo.setPreOrderTime(getMiniteByMili(vo.getYuyueTime(), vo.getCreateTime()));
//				}
//				if (vo.getReceiveTime() != null && vo.getCreateTime() != null) {
//				    vo.setResponseOrderTime(getMiniteByMili(vo.getReceiveTime(), vo.getCreateTime()));
//				}
//				vo.setCompany(titles.get(vo.getCompany()));
//				convertRate( vo);
//			} catch (Exception e) {
//				LOGGER.info("convert error :{}, {}", vo.toString(),e);
//			}
//        } 
//    }
//
//    // handle order status
//    private String handleStatus(String status) {
//    	    InnConstantsOrderStatus icos = InnConstantsOrderStatus.getByKey(status);
//        return icos==null?null:icos.getValue();
//    }
//
//    private String getMiniteByMili(String fdate, String sdate) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long time;
//        try {
//            time = df.parse(fdate).getTime() - df.parse( sdate).getTime();
//            time = time / 1000 ;
//            if(time> 60 * 60){
//                return (time/(60 * 60))+ "H";
//            }else if(time < 60 * 60 && time >= 60){
//                long minnutes = (time/60);
//                return minnutes+ "M";
//            }else if(time < 60 && time >= 0){
//                long seconds = (time/60);
//                return seconds+ "S";
//            }
//            return  null; 
//        } catch (ParseException e) {
//            LOGGER.error("parse time error begin:{},end:{}",sdate,fdate);
//        }
//       return null ;
//    }
//
//
//    private String getBooleanString(String booleanInt) {
//        if (booleanInt == null) {
//            return null;
//        }
//        return "1".equals(booleanInt)? "是" : "否";
//    }
//
//    private Map<String, Object> generateParams(String startDate, String endDate, String timeType, String serviceType,
//            String orderType, String payType, Integer pageSize, Integer pageNum, Integer company, String city,
//            String[] orderStatus, Integer sales) {
//        // startDate, endDate,timeType, sevivceType, orderType, payType,
//        // pageSize, pageNum, company, city
//        Map<String, Object> params = new HashMap<>();
//        
//        if(startDate!=null && endDate != null && !endDate.isEmpty() && !startDate.isEmpty() && !"-1".equals(timeType)){
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date dt = df.parse(endDate);
//                Calendar cl = Calendar.getInstance();
//                cl.setTime(dt);
//                cl.add(Calendar.DAY_OF_YEAR, 1);
//                endDate =  df.format(cl.getTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
////            if ("yuyue".equalsIgnoreCase(timeType)) {
////                params.put("startYuyueTime", startDate );
////                params.put("endYuyueTime", endDate );
////            } else if ("payTimes".equalsIgnoreCase(timeType)) {
////                params.put("payTimesCreateTime", startDate );
////                params.put("payTimesEndTime", endDate );
////            } else if("reportTime".equalsIgnoreCase(timeType)){
////                params.put("startReportTime", startDate );
////                params.put("endReportTime", endDate );
////            } else if("create".equalsIgnoreCase(timeType)){
////                params.put("startCreateTime", startDate );
////                params.put("endCreateTime", endDate );
////            }
//            params.put("conditionTime", timeType);
//            params.put("beginTime", startDate);
//            params.put("endTime", endDate);
//        }
//        params.put("serviceType", getServiceType(serviceType));
//        if (!"-1".equals(orderType)) {
//            params.put("orderType", orderType);
//        }
//        if (sales != null && sales != -1) {
//            params.put("sales", sales);
//        }
//        params.put("payTypes", getPayTypeStrList(payType));
//        if (company != null && company != -1) {
//            params.put("company", company);
//        }
//        if (!"-1".equals(city)) {
//            String[] split = city.split(",");
//            params.put("cityNames", split);
//        }
//        if (!"-1".equals(orderStatus)) {
//            params.put("status", orderStatus);
//        }
//        Integer startNum = pageNum * pageSize;
//        params.put("startNum", startNum);
//        params.put("pageSize", pageSize);
//        return params;
//    }
//
//    private String getPayType(String code) {
//        if (code == null || ("-1").equals(code)) {
//            return null;
//        }
//        if ("35".equals(code) || "线上".equals(code)) {
//            return "线上";
//        }
//        if ("36".equals(code) || "线下".equals(code)) {
//            return "线下";
//        }
//        return null;
//    }
//
//    private List<String> getPayTypeStrList(String payType) {
//        if (payType == null || ("-1").equals(payType)) {
//            return null;
//        }
//        List<String> list = new ArrayList<>();
//        list.add(payType);
//        return list;
//    }
//
//    private String[] getServiceType(String serviceType) {
//        if (serviceType == null || ("-1").equals(serviceType)) {
//            return null;
//        }
//        if ("1".equals(serviceType)) {
//            return new String[]{"1","yuding"};
//        } else if ("2".equals(serviceType)) {
//            return  new String[]{"2","waimai"};
//        }
//        return null;
//    }
//
//    private String getServiceTypeCN(String serviceTypeEN) {
//        if ("2".equalsIgnoreCase(serviceTypeEN) || "waimai".equalsIgnoreCase(serviceTypeEN)) {
//            return "外卖";
//        } else {
//            return "订座";
//        }
//    }
//
//    @RequestMapping(value = "/orderquery/download/{scope}", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResponse download(@PathVariable("scope") String scope,
//            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "startTime", required = false) String startTime,
//            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "endTime", required = false) String endTime,
//            @ApiParam(value = "业务类型") @RequestParam(value = "serviceType", required = false) String sevivceType,
//            @ApiParam(value = "时间类型") @RequestParam(value = "timeType", required = false) String timeType,
//            @ApiParam(value = "订单类型") @RequestParam(value = "orderType", required = false) String orderType,
//            @ApiParam(value = "付款类型") @RequestParam(value = "payType", required = false) String payType,
//            @ApiParam(value = "城市") @RequestParam(value = "city", required = false, defaultValue = "-1") String city,
//            @ApiParam(value = "订单状态") @RequestParam(value = "orderStatus", required = false) String[] orderStatus,
//            @ApiParam(value = "维护销售") @RequestParam(value = "sales", required = false) Integer sales,
//            @ApiParam(value = "搜索关键字") @RequestParam(value = "searchKey", required = false) String searchKey,
//            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false) Integer company,
//            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
//            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
//            Long templateId,
//            HttpServletRequest request, ModelAndView mv, HttpServletResponse response) throws IOException {
//
//        class AsyncUploadToOSS implements Runnable {
//            private ModelAndView mv;
//            private String username;
//
//            public AsyncUploadToOSS(ModelAndView mv, String username) {
//                this.mv = mv;
//                this.username = username;
//            }
//
//            @Override
//            public void run() {
//                AchillesDiyTemplate adt = achillesDiyColumnsServiceImpl.getTemplate(templateId);
//                final List<AchillesDiyTemplateColumns> tempcols = templateId == null?null : achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
//                DownloadBuilder<OrderRecordVo> downloadBuilder = new DownloadBuilder<>(OrderRecordVo.class,null,tempcols);
//                mv = queryPageOrders(scope, startTime, endTime, sevivceType, timeType, orderType, payType, city,
//                        orderStatus, sales, searchKey, company, 5000, pageNum,templateId, mv);
//                Page<OrderRecordVo> page = (Page<OrderRecordVo>) mv.getModel().get("page");
//                downloadBuilder.append(page.getContent());
//
//                while (page.hasNext()) {
//                    Pageable pageable = page.nextPageable();
//                    mv = queryPageOrders(scope, startTime, endTime, sevivceType, timeType, orderType, payType, city,
//                            orderStatus, sales, searchKey, company, 5000, pageable.getPageNumber(),templateId, mv);
//                    page = (Page<OrderRecordVo>) mv.getModel().get("page");
//                    downloadBuilder.append(page.getContent());
//                    mv.getModel().remove("page");
//                }
//                String filePath = downloadBuilder.saveOnServer();
//                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath,adt.getTemplateName()==null?adt.getTableName():adt.getTemplateName(), username);
//            }
//        }
//        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
//        return new BaseResponse();
//    }
//
//    private void convertRate( OrderRecordVo vo){
//        if(Strings.isEmpty(vo.getRates())){
//            return ;
//        }
//        ObjectMapper om = new ObjectMapper();
//        JsonNode jn = null;
//        try {
//            jn = om.readTree(vo.getRates());
//        } catch ( Exception e) {
//            LOGGER.error("PARSE RATE ERROR:{}", vo.getRates());
//            LOGGER.error("PARSE RATE EXCEPTIONS:{}",e);
//        }
//        if(jn.size() == 1){
//            vo.setRates(jn.get(0).get("start").asText()+"~"+(jn.get(0).get("end")==null||"null".equals(jn.get(0).get("end").asText())?"?":jn.get(0).get("end").asText())+":"+jn.get(0).get("rate").asText()+"(固定)");
//            return;
//        }
//        String[] rates = new String[jn.size()];
//        for (int i = 0;jn != null && i < jn.size(); i++) {
//            JsonNode node = jn.get(i);
////            try {
////                if(vo.getMoney() >= node.get("start").asDouble() && vo.getMoney() <= node.get("end").asDouble()){
////                    vo.setRates(node.get("rate").asText());
////                }
////            } catch (Exception e) {
////                LOGGER.error("PARSE RATE NODE ERROR {}:{}",node.toString(),e);
////                vo.setRates(node.toString());
////            }
//            if( node != null && node.get("rate")!=null ){
//                rates[i]=node.get("start").asText()+"~"+(node.get("end")==null||"null".equals(node.get("end").asText())?"?":node.get("end").asText())+":"+node.get("rate").asText();
//            }
//        }
//        vo.setRates(Arrays.toString(rates)+"(阶梯)");
//    }
//    @Autowired
//    OssServiceDBUtil ossServiceDBUtil;
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
//}
