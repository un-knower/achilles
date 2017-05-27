package com.quancheng.achilles.service.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantsJob;
import com.quancheng.achilles.dao.model.CompanyRestaurantsStatistics;
import com.quancheng.achilles.service.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")
@Api(value = "CompanyReport", description = "the CompanyReport API")
public interface CompanyReportApi {

    @ApiOperation(value = "", notes = "数据中心集市表 》公司统计信息", response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "公司餐厅统计信息", response = CompanyRestaurantsStatistics.class) })
    @RequestMapping(value = "/company/statistics/restaurants", produces = { "application/json" }, method = RequestMethod.GET)
    Response<Map<String, Object>> queryCompanyRestaurantsStatisticsByTime(@ApiParam(value = "年月份") @RequestParam String time,
                                                                          @ApiParam(value = "公司id") @RequestParam Integer companyId);

    @ApiOperation(value = "", notes = "数据中心集市表 》公司统计信息", response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "根据条件查询月公司餐厅下架数量", response = CompanyRestaurantsStatistics.class) })
    @RequestMapping(value = "/company/statistics/allshelfnum", produces = { "application/json" }, method = RequestMethod.GET)
    public Response<Map<String, Object>> queryCompanyAllShelfNumByTime(String time, Integer companyId);

    @ApiOperation(value = "", notes = "数据中心集市表 》公司统计信息", response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "每月餐厅新增不同推荐来源的总数量", response = CompanyRestaurantsStatistics.class) })
    @RequestMapping(value = "/company/statistics/allsourcenum", produces = { "application/json" }, method = RequestMethod.GET)
    public Response<Map<String, Object>> queryCompanyAllSourceNumByTime(String time, Integer companyId);

    @ApiOperation(value = "", notes = "数据中心集市表 》公司统计信息", response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "根据月份查询公司城市餐厅统计信息并保存,根据月份查询公司餐厅推荐信息并保存,根据月份查询公司餐厅下架原因统计信息并保存,查询并保存公司医院附近公司订餐和外卖餐厅统计信息并保存  time:yyyy-MM", response = CompanyRestaurantsStatistics.class) })
    @RequestMapping(value = "/company/statistics/startjob", produces = { "application/json" }, method = RequestMethod.GET)
    public Response<String> startJob(String time, String type);

    @ApiOperation(value = "", notes = "1:"+InnConstantsJob.SJ_TITLE1 + "||2:" + InnConstantsJob.SJ_TITLE2 + "||3:"
                                      + InnConstantsJob.SJ_TITLE3 + "||4:"
                                      + InnConstantsJob.SJ_TITLE4, response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = InnConstantsJob.SJ_TITLE1 + "||"
                                                               + InnConstantsJob.SJ_TITLE2 + "||"
                                                               + InnConstantsJob.SJ_TITLE3 + "||"
                                                               + InnConstantsJob.SJ_TITLE4, response = Response.class) })
    @RequestMapping(value = "/fresh/cost_order/statistics", method = RequestMethod.GET)
    public Response<Object> updateUserCostOrOrder(String time, Integer jobid);

    @ApiOperation(value = "", notes = "1:"+InnConstantsJob.SJ_TITLE1 + "||2:" + InnConstantsJob.SJ_TITLE2 + "||3:"
                                      + InnConstantsJob.SJ_TITLE3 + "||4:"
                                      + InnConstantsJob.SJ_TITLE4, response = CompanyRestaurantsStatistics.class, responseContainer = "List", tags = { "公司统计信息", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = InnConstantsJob.SJ_TITLE1 + "||"
                                                               + InnConstantsJob.SJ_TITLE2 + "||"
                                                               + InnConstantsJob.SJ_TITLE3 + "||"
                                                               + InnConstantsJob.SJ_TITLE4, response = Response.class) })
    @RequestMapping(value = "/query/cost_order/statistics",produces = { "application/json" }, method = RequestMethod.GET)
    public Response<Object> query(String time, Integer jobid, String clientId);
}
