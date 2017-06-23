package com.quancheng.achilles.service.services;

import com.quancheng.achilles.dao.model.Region;
import com.quancheng.achilles.dao.modelwrite.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by XZW on 2016/9/10 0010.
 */
public interface MemberService {

    Page<Member> findAll(String startDate, String endDate, String company, String[] cityArray, String type, String name, String[] statusArray, Pageable pageable);

    List<String> listAllCompany();

    Map<Integer,String> listUserStatus();

    List<Region> listCity();
}
