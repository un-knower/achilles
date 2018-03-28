package com.quancheng.achilles.dao.quancheng_db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.quancheng_db.model.OrderStatisticCity;

public interface OrderStatisticCityRepository extends JpaRepository<OrderStatisticCity, Long>, JpaSpecificationExecutor<OrderStatisticCity>{
    
    @Query(nativeQuery=true,value=" SELECT  e.attribute " + 
            " FROM terra.terra_order o LEFT JOIN terra.`terra_order_entry` e ON o.order_id = e.order_id "
            + "WHERE o.`biz_type` IN ('TAKEAWAY' , 'RESERVATION', 'AFTERNOON_TEA', 'FRANCHISE', 'OFF_STAFF_RESERVATION', "
            + "'OFF_STAFF_TAKEAWAY', 'PROXY_TAKEAWAY') "
            + "AND (o.`parent_order_id` = '' OR o.`parent_order_id` IS NULL) and attribute is not null and attribute<>'' and o.id>:minId and o.id<:maxId limit :beginIndex,:pageSize")
    public List<String> queryOrderAttribute(
            @Param("minId")Integer minId,@Param("maxId")Integer maxId,
            @Param("beginIndex")int begin,@Param("pageSize")int pageSize);
}
