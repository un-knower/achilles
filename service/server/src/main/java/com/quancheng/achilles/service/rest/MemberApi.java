package com.quancheng.achilles.service.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.model.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * Created by XZW on 2016/9/7 0007.
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Api(value = "member", description = "the member API")
public interface MemberApi {

        @ApiOperation(value = "", notes = "数据中心集市表 》用户 》 用户列表", response = Member.class, responseContainer = "List", tags = {"用户",})
        @ApiResponses(value = { @ApiResponse(code = 200, message = "订单列表", response = Member.class) })
        @RequestMapping(value = "/member/list", produces = { "application/json" }, method = RequestMethod.GET)
        ResponseEntity<List<Member>> getMemberList(
                @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue=InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue="0") int pageNum
        );

}
