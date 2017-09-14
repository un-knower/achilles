package com.quancheng.achilles.service.services;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specifications;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.quancheng.achilles.dao.repository.RestaurantRecommenderRepository;
//import com.quancheng.achilles.dao.model.RestaurantRecommender;
//
//@Service
//@Transactional(readOnly = true)
//public class RecommandResServiceImpl extends RestaurantServiceAbstract<RestaurantRecommender>{
//    @Autowired
//    RestaurantRecommenderRepository rrRepository;
//    public Page<RestaurantRecommender> pageRRRest(RestaurantRecommender rest,String[] citys,
//            String start,
//            String end,
//            Pageable pageable){
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date shelfEnd = null;
//        Date shelfStart = null;
//        try {
//            if(!isEmpty(start) && !isEmpty(end)){
//                shelfStart=df.parse( start);
//                Calendar cl = Calendar.getInstance();
//                cl.setTime(df.parse(end));
//                cl.add(Calendar.DAY_OF_YEAR, 1);
//                shelfEnd=cl.getTime();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return rrRepository.findAll(
//                Specifications.where( in("cityName",citys))
//                .and(equal("recommendCompany",rest.getRecommendCompany()))
//                .and(equal("recommandMethod",rest.getRecommandMethod()))
//                .and(like("restaurantName",rest.getRestaurantName()))
//                .and(between("createdAt",shelfStart,shelfEnd)),pageable);
//    }
//}
