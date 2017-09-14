package com.quancheng.achilles.dao.write;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.quancheng.achilles.dao.modelwrite.QuanlityCheckRecord;
//
//public interface QuanlityCheckRecordRepository extends JpaRepository<QuanlityCheckRecord, String>, JpaSpecificationExecutor<QuanlityCheckRecord> {
//
//    @Query("select t from QuanlityCheckRecord t where t.checkDate>= :startTime and t.checkDate <= :endTime")
//    Page<QuanlityCheckRecord> findAllWithinTimeRange(@Param("startTime") long startTime, @Param("endTime") long endTime,
//                                                     Pageable pageable);
//
//    @Query("select distinct(t.company) from QuanlityCheckRecord t where t.company is not null and t.company != ''")
//    List<String> listAllCompany();
//
//    // 检查类型
//    @Query("select distinct(t.checkType) from QuanlityCheckRecord t where t.checkType is not null and t.checkType != ''")
//    List<String> listAllCheckType();
//
//    // 检查类型
//    @Query("select distinct(t.checkMode) from QuanlityCheckRecord t where t.checkMode is not null and t.checkMode != ''")
//    List<String> listAllCheckMode();
//
//    // 城市
//    @Query("select distinct(t.cityName) from QuanlityCheckRecord t where t.cityName is not null and t.cityName != ''")
//    List<String> listAllCityName();
//}
