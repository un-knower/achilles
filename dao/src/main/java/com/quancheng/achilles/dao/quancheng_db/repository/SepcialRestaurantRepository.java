package com.quancheng.achilles.dao.quancheng_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.quancheng.achilles.dao.quancheng_db.model.SepicalRestaurant;

public interface SepcialRestaurantRepository extends JpaRepository<SepicalRestaurant, String>, JpaSpecificationExecutor<SepicalRestaurant>{
    /***
     * 根据是否企业餐厅、黑名单、线上状态判断在线状态
     */
    @Modifying
    @Transactional  
    @Query(
            value=" update cacia.restaurant_info a " + 
                    "left join quancheng_db.api_restaurants b on a.gonghai_id=b.gonghai_id and b.status=0 " + 
                    "left join quancheng_db.`16860_restaurant_client` c on a.company_id=c.client_id and c.restaurant_id=b.id and c.deleted_at is null " + 
                    "left join quancheng_db.`api_client_blacklists` d on b.id=d.restaurant_id and d.client_id=a.company_id and d.deleted_at is null " + 
                    "set a.online_status=case when b.id is not null and c.id is not null and d.id is null then 'online' else 'offline' end   " + 
                    "where a.online_status is null and a.id=:id"  ,
            nativeQuery=true)
    public void updateCaciaOnlineStatus(@Param("id") String id);
}
