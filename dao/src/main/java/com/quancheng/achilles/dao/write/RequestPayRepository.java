package com.quancheng.achilles.dao.write;

//import java.util.Date;
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.quancheng.achilles.dao.modelwrite.RequestPayment;
//
///**
// * <strong>描述：</strong>TODO<br>
// * 催款记录
// * 
// * @author jianglijun 2016年9月14日 下午2:19:55
// * @version $Id: RequestPayRepository.java, v 0.0.1 2016年9月14日 下午2:19:55 jianglijun Exp $
// */
//public interface RequestPayRepository extends JpaRepository<RequestPayment, Long>, JpaSpecificationExecutor<RequestPayment> {
//
//    @Query("select t from RequestPayment t where t.createdAt>= :startTime and t.createdAt <= :endTime")
//    Page<RequestPayment> findAllWithinTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
//                                                Pageable pageable);
//
//    @Query("select distinct(t.cityName) from RequestPayment t where t.cityName is not null and t.cityName != '' ")
//    List<String> listAllCity();
//
//}
