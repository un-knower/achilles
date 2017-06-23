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
import com.quancheng.achilles.dao.modelwrite.QuanlityCheckRecord;
import com.quancheng.achilles.dao.write.QuanlityCheckRecordRepository;
import com.quancheng.achilles.service.utils.DateUtils;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Controller
@RequestMapping(path = "/api")
public class QuanlityCheckApiController implements QuanlityCheckApi {

    @Autowired
    QuanlityCheckRecordRepository quanlityCheckRecordRepository;

    @Override
    public ResponseEntity<List<QuanlityCheckRecord>> FlyCheckRecordListGet(@ApiParam(value = "开始时间(yyyy-MM-dd HH:mm:ss)") @RequestParam(value = "startTime", required = false) String startTime,
                                                                           @ApiParam(value = "结束时间(yyyy-MM-dd HH:mm:ss)") @RequestParam(value = "endTime", required = false) String endTime,
                                                                           @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                                                                           @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum) {
        // do some magic!
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "checkDate");
        List<QuanlityCheckRecord> list = new ArrayList<>();
        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            list = quanlityCheckRecordRepository.findAll(pageable).getContent();
        } else {
            list = quanlityCheckRecordRepository.findAllWithinTimeRange(DateUtils.toDate(startTime,
                                                                                         DateUtils.SDF_DATETIME).getTime()
                                                                        / 1000,
                                                                        DateUtils.toDate(endTime,
                                                                                         DateUtils.SDF_DATETIME).getTime()
                                                                                / 1000,
                                                                        pageable).getContent();
        }

        ResponseEntity<List<QuanlityCheckRecord>> response = new ResponseEntity<List<QuanlityCheckRecord>>(list,
                                                                                                           HttpStatus.OK);
        return response;
    }
}
