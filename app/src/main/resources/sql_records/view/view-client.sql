CREATE OR REPLACE VIEW v_inn_client_rest_statistic AS
SELECT
    168client_rest.client_id,
    master_rest.city,
    COUNT( DISTINCT 168client_rest.restaurant_id ) as count_client_rest,
    COUNT( DISTINCT CASE WHEN 168client_rest.deleted_at IS NULL AND api_rest.`status`=0  THEN 168client_rest.restaurant_id ELSE NULL END) as count_rest_recom_ol,
    COUNT( DISTINCT rest_res.restaurant_id) as count_rest_recom
FROM 16860_restaurant_client 168client_rest
    LEFT JOIN api_restaurants api_rest on 168client_rest.restaurant_id=api_rest.id  
    LEFT JOIN api_restaurants_master master_rest ON api_rest.gonghai_id=master_rest.id
    LEFT JOIN api_restaurant_master_sources rest_res ON rest_res.restaurant_id=master_rest.id AND rest_res.type NOT IN (1000,1005,1030)
GROUP BY
    168client_rest.client_id, master_rest.city;
--企业组织架构    
CREATE OR REPLACE VIEW v_inn_org_for_company AS 
SELECT
    mem.cid,
    mem.city_id,
    mem.uid,
    CASE WHEN mem.`status`=1 THEN mem.uid ELSE NULL END  AS uid_active,
    os1.structure_name branch ,
    os2.structure_name bus ,
    os3.structure_name sector ,
    os4.structure_name productgroup ,
    os5.structure_name cost_center ,
    os6.structure_name region 
FROM 16860_member mem
    LEFT JOIN 16860_organizational_structure os2  ON 
        case when mem.businessunit_id =0 or  mem.businessunit_id is null 
            then  mem.division_id=os2.old_id and  mem.division_id <>0
            else   mem.businessunit_id=os2.id end  
        AND `os2`.`structure_type` = 'businessunit' 
    LEFT JOIN 16860_organizational_structure os1  ON 
        case when  mem.branch_id =0 or  mem.branch_id is null   
            then mem.invoice_cid = os1.old_id  and  mem.invoice_cid <>0
            else mem.branch_id = os1.id end
        AND `os1`.`structure_type` = 'branch' and os1.id <>0
    LEFT JOIN 16860_organizational_structure os3  ON 
        case when  mem.sector_id =0 or  mem.sector_id is null   
            then mem.invoice_cid = os3.old_id and  mem.invoice_cid <>0
            else  mem.sector_id = os3.id end
        AND `os3`.`structure_type` = 'sector' and os3.id <>0
    LEFT JOIN 16860_organizational_structure os4  ON 
        case when  mem.productgroup_id =0 or  mem.productgroup_id is null   
            then mem.invoice_cid = os4.old_id and  mem.invoice_cid <>0
            else  mem.productgroup_id = os4.id end 
        AND `os4`.`structure_type` = 'productgroup' 
    LEFT JOIN 16860_organizational_structure os5  ON mem.cost_center_id = os5.id
        AND `os5`.`structure_type` = 'costcenter'   
    LEFT JOIN 16860_organizational_structure os6  ON 
        case when  mem.region_id =0 or  mem.region_id is null    
            then mem.division_id = os6.old_id and  mem.division_id <>0
            else  mem.region_id = os6.id end
        AND `os6`.`structure_type` = 'region'  ;    

--线上支付订单
CREATE OR REPLACE VIEW v_inn_client_city_user_order_info AS
SELECT
  CONCAT('yd',168order.id) as or_id,
    168order.city_id,
    168order.money,
    168order.client_id,
    168order.user_id,
    168order.restaurant_id, /*有消费的餐厅*/
    CASE WHEN 168order.restaurant_id IS NOT NULL AND arms.restaurant_id IS NOT NULL THEN master_rest.id ELSE NULL END AS rest_recommender_had_cost /*有消费的推荐餐厅*/
 FROM 16860_order 168order 
  LEFT JOIN api_restaurants ol_rest ON  ol_rest.id=168order.restaurant_id  and ol_rest.`status`=0 
  LEFT JOIN api_restaurants_master master_rest ON master_rest.id=ol_rest.gonghai_id 
  LEFT JOIN api_restaurant_master_sources arms ON master_rest.id=arms.restaurant_id AND arms.type NOT IN (1000,1005,1030)   
 where 168order.order_state in (35,36)
UNION ALL
    SELECT
        CONCAT('wm',ao.id) as or_id,
        ao.city_id,
        awod.actual_cost AS money,
        ao.client_id,
        ao.user_id,
        ao.restaurant_id,
        CASE WHEN ao.restaurant_id IS NOT NULL AND arms.restaurant_id IS NOT NULL THEN master_rest.id ELSE NULL END AS rest_recommender_had_cost /*有消费的推荐餐厅*/
    FROM  api_orders ao  
            LEFT JOIN api_waimai_order_detail awod ON ao.id = awod.order_id  
            LEFT JOIN api_restaurants ol_rest  ON ol_rest.id=ao.restaurant_id  
            LEFT JOIN api_restaurants_master master_rest ON master_rest.id=ol_rest.gonghai_id and ol_rest.`status`=0 
        LEFT JOIN api_restaurant_master_sources arms ON master_rest.id=arms.restaurant_id AND arms.type NOT IN (1000,1005,1030)
    WHERE ao.`status` = '2002';
    

CREATE or REPLACE  VIEW v_inn_client_rest_order_statistic AS
SELECT
    org.cid as client_id,
    org.city_id,
    org.branch ,
    org.bus ,
    org.sector ,
    org.productgroup ,
    org.cost_center ,
    org.region,
    COUNT(DISTINCT org.uid) as count_user,
    COUNT(DISTINCT cc_order_info.user_id) as count_user_cost,
    SUM(cc_order_info.money) as count_money,
    COUNT(cc_order_info.or_id) as count_order,
    COUNT(DISTINCT cc_order_info.restaurant_id) as count_rest_had_cost,
    COUNT(DISTINCT cc_order_info.rest_recommender_had_cost) as count_rest_had_cost_recom
    FROM  v_inn_org_for_company org  
            LEFT JOIN v_inn_client_city_user_order_info cc_order_info  
                    ON  org.cid = cc_order_info.client_id
                    AND cc_order_info.city_id = org.city_id
                    AND org.uid = cc_order_info.user_id
    GROUP BY org.cid,org.city_id, org.branch , org.bus , org.sector , org.productgroup , org.cost_center , org.region;

 DROP  TABLE  IF EXISTS tmp_inn_client_rest_order_statistic  ;
CREATE TABLE IF  NOT EXISTS tmp_inn_client_rest_order_statistic   AS (SELECT * FROM v_inn_client_rest_order_statistic);    
    
CREATE or REPLACE view v_inn_order_statistic_for_client AS
SELECT
    UUID() as id,
    168client.title as client_name,
    reg.`name` as city,
    order_statistic.branch ,
    order_statistic.bus ,
    order_statistic.sector ,
    order_statistic.productgroup ,
    order_statistic.cost_center ,
    order_statistic.region ,
    order_statistic.count_order,
    order_statistic.count_money,
    order_statistic.count_user,
    CONCAT(ROUND(order_statistic.count_user_cost*100 / order_statistic.count_user,2),'%') as rate_user_order,
    168client_rest.count_rest_recom,
    168client_rest.count_rest_recom_ol,
    CONCAT(ROUND(order_statistic.count_rest_had_cost_recom*100 / 168client_rest.count_rest_recom_ol,2),'%') as rate_rest_recommender_use,
    168client_rest.count_client_rest as count_rest,
    order_statistic.count_rest_had_cost,
    CONCAT(ROUND(order_statistic.count_rest_had_cost*100/168client_rest.count_client_rest,2),'%') as rate_rest_use
FROM 16860_client 168client
    LEFT JOIN   v_inn_client_rest_statistic 168client_rest 
        ON 168client.id=168client_rest.client_id
    LEFT JOIN tmp_inn_client_rest_order_statistic order_statistic 
        ON 168client_rest.client_id = order_statistic.client_id 
            AND 168client_rest.city=order_statistic.city_id
            AND order_statistic.city_id = 168client_rest.city
    LEFT JOIN 16860_region reg ON 168client_rest.city=reg.id
    where 168client.`status`=1;
    
DROP  TABLE  IF EXISTS tmp_client_query  ;
CREATE TABLE IF  NOT EXISTS tmp_client_query   AS (SELECT * FROM v_inn_order_statistic_for_client);
    
