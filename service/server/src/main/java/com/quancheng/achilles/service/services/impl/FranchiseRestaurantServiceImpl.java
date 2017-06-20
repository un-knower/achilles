package com.quancheng.achilles.service.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import com.quancheng.achilles.dao.modelwrite.FranchiseRestApproveDetail;
import com.quancheng.achilles.dao.write.FranchiseRestApproveDetailRepository;
import com.quancheng.achilles.service.services.RestaurantServiceAbstract;

@Service
public class FranchiseRestaurantServiceImpl extends RestaurantServiceAbstract<FranchiseRestApproveDetail>  {
    @Autowired
    FranchiseRestApproveDetailRepository franchiseRestApproveDetailRepository;
    
    
    public Page<FranchiseRestApproveDetail> getFranchiseRest(
            String applyUserName,
            String gouldId,
            String restaurantName,
            String[] cityNames,
            String[] applyTypes,
            String timeType,
            String begin,
            String end,
            String[] finalResults,
            String[] refusedReasons,
            Pageable pageable) {
        if (isEmpty(timeType) || isEmpty(begin) || isEmpty(end)) {
            timeType =null;
            begin =null;
            end =null;
        }else{
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cl = Calendar.getInstance();
                cl.setTime(df.parse(end));
                cl.add(Calendar.DAY_OF_YEAR, 1);
                end=df.format(cl.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Specifications<FranchiseRestApproveDetail>  speci= 
                Specifications.where(like("restName",restaurantName))
                .and(like("userName",applyUserName))
                .and(equal("gouldId",gouldId))
                .and(in("city",cityNames))
                .and(in("applyType",applyTypes))
                .and(between(timeType,begin,end))
                .and(in("status",finalResults))
                .and(in("reason",refusedReasons));
        return franchiseRestApproveDetailRepository.findAll(speci,pageable);
    }
    public List<String> getAllReason(){
        return franchiseRestApproveDetailRepository.queryAllReason();
    }
}
