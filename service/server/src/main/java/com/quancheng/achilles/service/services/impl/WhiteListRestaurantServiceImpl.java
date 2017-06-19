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
import com.quancheng.achilles.dao.modelwrite.WhiteListRestaurant;
import com.quancheng.achilles.dao.write.WhiteListRestaurantRepository;
import com.quancheng.achilles.service.services.RestaurantServiceAbstract;

@Service
public class WhiteListRestaurantServiceImpl extends RestaurantServiceAbstract<WhiteListRestaurant>  {
    @Autowired
    WhiteListRestaurantRepository whiteListRestaurantRepository;
    public Page<WhiteListRestaurant> page(
            String applyUserName,
            String applyUserJobNum,
            String restaurantName,
            String[] cityIds,
            String[] approveTypes,
            String timeType,
            String begin,
            String end,
            String[] applyCompanys,
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
        Specifications<WhiteListRestaurant>  speci= 
                Specifications.where(like("realname",applyUserName))
                .and(like("restaurantName",restaurantName))
                .and(equal("jobNum",applyUserJobNum))
                .and(between(timeType,begin,end))
                .and(in("city",cityIds))
                .and(in("approveMethod",approveTypes))
                .and(in("title",applyCompanys));
        return whiteListRestaurantRepository.findAll(speci,pageable);
    }
    public List<String> getAllClients(){
        return whiteListRestaurantRepository.queryClientList();
    }
}
