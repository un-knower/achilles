package com.quancheng.achilles.dao.write;

//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//
//import com.quancheng.achilles.dao.modelwrite.AppRestaurantReportedWrong;
//
//public interface AppRreportErrorRepository extends JpaRepository<AppRestaurantReportedWrong, Long>, JpaSpecificationExecutor<AppRestaurantReportedWrong> {
//    
//    @Query(value="select distinct enterprise_name from out_app_restaurant_reported_wrong  where enterprise_name<>'' ",nativeQuery=true)
//    List<String> queryClients();
//}
