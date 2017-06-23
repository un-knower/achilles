package com.quancheng.achilles.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.modelwrite.RequestPayment;
import com.quancheng.achilles.dao.write.RequestPayRepository;
import com.quancheng.achilles.service.utils.DateUtils;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Controller
@RequestMapping(path = "/api")
public class RequestPayApiController implements RequestPayRecordApi {

    @Autowired
    RequestPayRepository requestPayRepository;

    @Override
    public ResponseEntity<List<RequestPayment>> callRecordListGet(@ApiParam(value = "开始时间(yyyy-MM-dd )") @RequestParam(value = "startTime", required = false) String startTime,
                                                                  @ApiParam(value = "结束时间(yyyy-MM-dd)") @RequestParam(value = "endTime", required = false) String endTime,
                                                                  @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                                                                  @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum) {
        // do some ma
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "createdAt");
        List<RequestPayment> list = new ArrayList<>();
        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            list = requestPayRepository.findAll(pageable).getContent();
        } else {
            list = requestPayRepository.findAllWithinTimeRange(DateUtils.toDate(startTime, DateUtils.SDF_DATE),
                                                               DateUtils.toDate(endTime, DateUtils.SDF_DATE),
                                                               pageable).getContent();
        }

        ResponseEntity<List<RequestPayment>> response = new ResponseEntity<List<RequestPayment>>(list, HttpStatus.OK);
        return response;
    }

}
