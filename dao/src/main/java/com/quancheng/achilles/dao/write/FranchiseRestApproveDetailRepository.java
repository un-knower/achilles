package com.quancheng.achilles.dao.write;

//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//
//import com.quancheng.achilles.dao.modelwrite.FranchiseRestApproveDetail;
//
//public interface FranchiseRestApproveDetailRepository extends JpaRepository<FranchiseRestApproveDetail, Long>, JpaSpecificationExecutor<FranchiseRestApproveDetail> {
//    
//    @Query(value="select distinct reason from out_approve_time_detail where reason<>''",nativeQuery=true)
//    List<String> queryAllReason();
//}
