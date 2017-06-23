package com.quancheng.achilles.service.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.dao.modelwrite.RequestPayment;
import com.quancheng.achilles.service.constants.InnConstantPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Api(value = "requestpay-record", description = "the requestpay-record API")
public interface RequestPayRecordApi {

    @ApiOperation(value = "", notes = "数据中心集市表 》客户服务 》 催款记录", response = RequestPayment.class, responseContainer = "List", tags = { "催款记录", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "订单列表", response = RequestPayment.class) })
    @RequestMapping(value = "/requestpay-record/list", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<List<RequestPayment>> callRecordListGet(@ApiParam(value = "开始时间(yyyy-MM-dd HH:mm:ss)") @RequestParam(value = "startTime", required = false) String startTime,
                                                           @ApiParam(value = "结束时间(yyyy-MM-dd HH:mm:ss)") @RequestParam(value = "endTime", required = false) String endTime,
                                                           @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                                                           @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum);

}
