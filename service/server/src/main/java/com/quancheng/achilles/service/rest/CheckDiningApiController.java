package com.quancheng.achilles.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.modelwrite.CheckEmphasisDining;
import com.quancheng.achilles.dao.write.CheckDiningRepository;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Controller
@RequestMapping(path = "/api")
public class CheckDiningApiController implements CheckDiningApi {

    @Autowired
    CheckDiningRepository checkDiningRepository;

    @Override
    public ResponseEntity<List<CheckEmphasisDining>> FlyCheckRecordListGet(@ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
                                                                           @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum) {
        // do some magic!
        Pageable pageable = new PageRequest(pageNum, pageSize);
        List<CheckEmphasisDining> list = new ArrayList<>();
        list = checkDiningRepository.findAll(pageable).getContent();
        ResponseEntity<List<CheckEmphasisDining>> response = new ResponseEntity<List<CheckEmphasisDining>>(list,
                                                                                                           HttpStatus.OK);
        return response;
    }
}
