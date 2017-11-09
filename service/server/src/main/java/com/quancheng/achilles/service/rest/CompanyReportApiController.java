package com.quancheng.achilles.service.rest;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quancheng.achilles.dao.ds_qc.model.CompanyRestaurantsStatistics;
import com.quancheng.achilles.service.services.CompanyReportService;
import com.quancheng.achilles.service.services.ISJOrderService;
import com.quancheng.achilles.service.utils.Response;
import com.quancheng.achilles.service.utils.TimeUtil;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@RestController
@RequestMapping(path = "/api")
public class CompanyReportApiController implements CompanyReportApi {

    private Logger       log = LoggerFactory.getLogger(CompanyReportApiController.class);
    @Autowired
    CompanyReportService companyReportService;

    @Autowired
    ISJOrderService      orderService;

    @Override
    public Response<Map<String, Object>> queryCompanyRestaurantsStatisticsByTime(@ApiParam(value = "年月份") @RequestParam String time,
                                                                                 @ApiParam(value = "公司id") @RequestParam Integer companyId) {
        log.info("queryCompanyRestaurantsStatisticsByTime param is time:{},companyId:{}", time, companyId);
        Response<Map<String, Object>> resp = new Response<>();
        resp.setStatus(-1).setMessage("fail");
        Map<String, Object> map = new HashMap<String, Object>();
        Date date;
        CompanyRestaurantsStatistics companyStatistics;
        CompanyRestaurantsStatistics previousCompanyStatistics;
        try {
            companyStatistics = companyReportService.queryCompanyRestaurantsStatisticsByTime(time, companyId);
            date = TimeUtil.parseDate("yyyy-MM", time);
            Date afterMonth = TimeUtil.addMonth(date, -1);
            String afterMonthStr = TimeUtil.formatDate("yyyy-MM", afterMonth);
            previousCompanyStatistics = companyReportService.queryCompanyRestaurantsStatisticsByTime(afterMonthStr,
                                                                                                     companyId);
            if (companyStatistics != null) {
                map.put("time", companyStatistics.getTime());
                map.put("companyId", companyStatistics.getCompanyId());
                map.put("companyName", companyStatistics.getCompanyName());
                map.put("allRestaurantsNum", companyStatistics.getAllRestaurantsNum());
                if (previousCompanyStatistics != null) {
                    map.put("previousAllRestaurantsNum", previousCompanyStatistics.getAllRestaurantsNum());
                    map.put("addRestaurantsNum", companyStatistics.getAllRestaurantsNum()
                                                 - previousCompanyStatistics.getAllRestaurantsNum());

                } else {
                    map.put("previousAllRestaurantsNum", 0);
                    map.put("addRestaurantsNum", companyStatistics.getAllRestaurantsNum());
                }
                resp.setStatus(0).setData(map).setMessage("success");
            }
        } catch (ParseException e) {
            log.error("queryCompanyStatisticsByTime rest have a error {}", e);
        }
        log.info("queryCompanyRestaurantsStatisticsByTime result is {}", resp.toString());
        return resp;
    }

    @Override
    public Response<Map<String, Object>> queryCompanyAllShelfNumByTime(@ApiParam(value = "年月份") @RequestParam String time,
                                                                       @ApiParam(value = "公司id") @RequestParam Integer companyId) {
        log.info("queryCompanyAllShelfNumByTime param is time:{},companyId:{}", time, companyId);
        Response<Map<String, Object>> resp = new Response<>();
        resp.setStatus(-1).setMessage("fail");
        Map<String, Object> map = new HashMap<String, Object>();
        Integer num;
        try {
            num = companyReportService.queryCompanyAllShelfNumByTime(time, companyId);
            if (num != null) {
                map.put("allShelfNum", num);
                resp.setStatus(0).setData(map).setMessage("success");
            }
        } catch (Exception e) {
            log.error("queryCompanyAllShelfNumByTime rest have a error {}", e);
        }
        log.info("queryCompanyAllShelfNumByTime result is {}", resp.toString());
        return resp;
    }

    @Override
    public Response<Map<String, Object>> queryCompanyAllSourceNumByTime(@ApiParam(value = "年月份") @RequestParam String time,
                                                                        @ApiParam(value = "公司id") @RequestParam Integer companyId) {
        log.info("queryCompanyAllSourceNumByTime param is time:{},companyId:{}", time, companyId);
        Response<Map<String, Object>> resp = new Response<>();
        resp.setStatus(-1).setMessage("fail");
        Map<String, Object> map = new HashMap<String, Object>();
        Integer num;
        try {
            num = companyReportService.queryCompanyAllSourceNumByTime(time, companyId);
            if (num != null) {
                map.put("allSourceNum", num);
                resp.setStatus(0).setData(map).setMessage("success");
            }
        } catch (Exception e) {
            log.error("queryCompanyAllSourceNumByTime rest have a error {}", e);
        }
        log.info("queryCompanyAllSourceNumByTime result is {}", resp.toString());
        return resp;
    }

    @Override
    public Response<Object> updateUserCostOrOrder(@ApiParam(value = "yyyy-MM") @RequestParam String time,
                                                  @ApiParam(value = "jobid") @RequestParam Integer jobid) {
        log.info("updateUserCostOrOrder param is time:{},jobid:{}", time, jobid);
        Response<Object> resp = new Response<>();
        try {
            resp.setStatus(0);
            resp.setMessage("success");
            orderService.userCost(time, jobid);
        } catch (Exception e) {
            log.error("updateUserCostOrOrder rest have a error {}", e);
            resp.setMessage("failed");
            resp.setStatus(-1);
        }
        log.info("updateUserCostOrOrder result is {}", resp.toString());
        return resp;
    }

    @Override
    public Response<Object> query(@ApiParam(value = "yyyy-MM") @RequestParam String time,
                                  @ApiParam(value = "jobid") @RequestParam Integer jobid,
                                  @ApiParam(value = "企业id") @RequestParam(required=false) String clientId) {
        log.info("query param is time:{},jobid:{},clientId:{}", time, jobid, clientId);
        Response<Object> resp = new Response<>();
        try {
            resp.setStatus(0);
            resp.setMessage("success");
            resp.setData(orderService.query(time, jobid, clientId));
        } catch (Exception e) {
            log.error("updateUserCostOrOrder rest have a error {}", e);
            resp.setMessage("failed");
            resp.setStatus(-1);
        }
        log.info("query result is {}", resp.toString());
        return resp;
    }

    @Override
    public Response<String> startJob(@ApiParam(value = "年月份") @RequestParam(required = false) String time,
                                     @ApiParam(value = "类型") @RequestParam(required = false) String type) {
        log.info("startJob param is time:{},type:{}", time, type);
        Response<String> resp = new Response<>();
        resp.setStatus(-1).setMessage("fail");
        try {
            String timeOfMonth = time;
            if (StringUtils.isEmpty(time)) {
                timeOfMonth = TimeUtil.formatDate("yyyy-MM", new Date());
            }
            boolean result = false;
            if (type == null) {
                type = "";
            }
            String message = "success";
            switch (type) {
                case "city":
                    result = companyReportService.queryAndSaveCompanyCityRestaurantByTime(timeOfMonth);
                    break;
                case "sourse":
                    result = companyReportService.queryAndSaveCompanyRestaurantSourceByTime(timeOfMonth);
                    break;
                case "shelf":
                    result = companyReportService.queryAndSaveCompanyShelfRestaurantByTime(timeOfMonth);
                    break;
                case "hospital":
                    result = companyReportService.queryAndSaveCompanyHospitalRestaurantStatistics(timeOfMonth, 1000,
                                                                                                  2000, 3000);
                    break;
                default:
                    result = companyReportService.queryAndSaveCompanyCityRestaurantByTime(timeOfMonth);
                    if (!result) {
                        message = "根据月份查询公司城市餐厅统计信息并保存error,";
                    }
                    result = companyReportService.queryAndSaveCompanyRestaurantSourceByTime(timeOfMonth);
                    if (!result) {
                        message = message + "根据月份查询公司餐厅推荐信息并保存error,";
                    }
                    result = companyReportService.queryAndSaveCompanyShelfRestaurantByTime(timeOfMonth);
                    if (!result) {
                        message = message + "根据月份查询公司餐厅下架原因统计信息并保存 error,";
                    }
                    result = companyReportService.queryAndSaveCompanyHospitalRestaurantStatistics(timeOfMonth, 1000,
                                                                                                  2000, 3000);
                    if (!result) {
                        message = message + "查询并保存公司医院附近公司订餐和外卖餐厅统计信息并保存 error,";
                    }
                    result = true;
                    break;
            }
            if (result) {
                resp.setStatus(0).setMessage(message);
            }
        } catch (Exception e) {
            log.error("startJob rest have a error {}", e);
        }
        log.info("startJob result is {}", resp.toString());
        return resp;
    }

}
