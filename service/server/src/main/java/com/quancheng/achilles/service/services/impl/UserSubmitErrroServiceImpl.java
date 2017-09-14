package com.quancheng.achilles.service.services.impl;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specifications;
//import org.springframework.stereotype.Service;
//import com.quancheng.achilles.dao.modelwrite.AppRestaurantReportedWrong;
//import com.quancheng.achilles.dao.write.AppRreportErrorRepository;
//import com.quancheng.achilles.service.services.RestaurantServiceAbstract;
//
//@Service
//public class UserSubmitErrroServiceImpl extends RestaurantServiceAbstract<AppRestaurantReportedWrong>  {
//    @Autowired
//    AppRreportErrorRepository appRreportErrorRepository;
//    public Page<AppRestaurantReportedWrong> page(
//            String submitUserName,
//            String submitPhone,
//            String restaurantName,
//            Long[] cityIds,
//            String timeType,
//            String begin,
//            String end,
//            String[] applyCompanys,
//            Pageable pageable) {
//        if (isEmpty(timeType) || isEmpty(begin) || isEmpty(end)) {
//            timeType =null;
//            begin =null;
//            end =null;
//        }else{
//            try {
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                Calendar cl = Calendar.getInstance();
//                cl.setTime(df.parse(end));
//                cl.add(Calendar.DAY_OF_YEAR, 1);
//                end=df.format(cl.getTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        Specifications<AppRestaurantReportedWrong>  speci= 
//                Specifications.where(like("realname",submitUserName))
//                .and(like("storeName",restaurantName))
//                .and(equal("mobile",submitPhone))
//                .and(between(timeType,begin,end))
//                .and(in("title",applyCompanys))
//                .and(in("city",cityIds));
//        return appRreportErrorRepository.findAll(speci,pageable);
//    }
//    public List<String> getAllClients(){
//        return appRreportErrorRepository.queryClients();
//    }
//}
