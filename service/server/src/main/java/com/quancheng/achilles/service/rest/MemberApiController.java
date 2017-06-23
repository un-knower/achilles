package com.quancheng.achilles.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.dao.modelwrite.Member;
import com.quancheng.achilles.dao.write.MemberRepository;

import io.swagger.annotations.ApiParam;

/**
 * Created by XZW on 2016/9/7 0007.
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")

@Controller
@RequestMapping(path = "/api")
public class MemberApiController implements MemberApi {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public ResponseEntity<List<Member>> getMemberList(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum) {
//        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "recordTime", "description");
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);

        List<Member> members;

        members = memberRepository.findAll(pageRequest).getContent();

        ResponseEntity<List<Member>> listResponseEntity = new ResponseEntity<>(members, HttpStatus.OK);

        return listResponseEntity;

    }
}
