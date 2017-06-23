package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.constants.InnConstantsOrderStatus;
import com.quancheng.achilles.service.services.RequestPayService;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.modelwrite.RequestPayment;
import com.quancheng.achilles.service.utils.DateUtils;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/ops/reqpayment")
public class RequestPayController {

    @Autowired
    RequestPayService requestPayService;
    
//    @Value("${achilles.order.status}")
//    String orderStatus;
    
    
    @Value("${achilles.request.payment.status}")
    String requestPayment;
    
    @ResponseBody
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public BaseResponse downLoad(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false, defaultValue = "-1") String[] city,
            @ApiParam(value = "订单号") @RequestParam(value = "relateOrder", required = false) String relateOrder,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            HttpServletRequest request, ModelAndView mv, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                Map<String, Map<Object, Object>> convert = new HashMap<>();
                convert.put("orderState", InnConstantsOrderStatus.getMapAll());
                convert.put("status", parse(requestPayment));
                DownloadBuilder<RequestPayment> downLoadBuild = new DownloadBuilder(RequestPayment.class,convert);
                mv = list(start, end, city, relateOrder, 1000, 0, mv);
                Page<RequestPayment> page = (Page<RequestPayment>) mv.getModel().get("page");
                downLoadBuild.append(page.getContent());
                while (page.hasNext()) {
                    Pageable pageable = page.nextPageable();
                    mv = list(start, end, city, relateOrder, pageable.getPageSize(), pageable.getPageNumber(), mv);
                    page = (Page<RequestPayment>) mv.getModel().get("page");
                    downLoadBuild.append(page.getContent());
                }
                String filePath = downLoadBuild.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "催款记录", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false) String[] city,
            @ApiParam(value = "订单号") @RequestParam(value = "relateOrder", required = false) String relateOrder,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            ModelAndView mv) {
        Date startDate = null;
        Date endDate = null;
        if (start != null  && !start.isEmpty() ) {
              startDate = DateUtils.toDate(start, DateUtils.SDF_DATE);
        }
        if (end != null && !end.isEmpty() ) {
              endDate = DateUtils.toDate(end, DateUtils.SDF_DATE);
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "createdAt");
        Page<RequestPayment> page = new PageImpl<RequestPayment>(new ArrayList<RequestPayment>());
        page = requestPayService.findAll(startDate, endDate, relateOrder, city, pageable);
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("relateOrder", relateOrder);
        mv.addObject("city", city==null ? new String[0]:city);
        mv.addObject("page", page);
        mv.addObject("orderStateMap",InnConstantsOrderStatus.getMapAll());
        mv.addObject("status",parse(requestPayment));
        mv.addObject("cityList", requestPayService.listAllCityName());
        mv.setViewName("callrecord/payment");
        return mv;
    }
    private final Map<Object, Object> parse(String str) {
        Map<Object, Object> result = new HashMap<>();
        for (String iterable_element : str.split(",")) {
            String[] entry = iterable_element.split(":");
            result.put(entry[0], entry[1]);
        }
        return result;
    }
}
