package com.quancheng.achilles.service.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.model.CheckEmphasisPeople;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Api(value = "CheckPeople", description = "the CheckPeople API")
public interface CheckPeopleRestApi {

    @ApiOperation(value = "", notes = "数据中心集市表 》飞检", response = CheckEmphasisPeople.class, responseContainer = "List", tags = { "飞检服务", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "订单列表", response = CheckEmphasisPeople.class) })
    @RequestMapping(value = "/checkemphasisp/list", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<List<CheckEmphasisPeople>> FlyCheckRecordListGet(@ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                                                                    @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum);

}
