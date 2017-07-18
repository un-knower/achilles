-- 重点关注人员
CREATE or REPLACE VIEW v_inn_checkemphasisp AS
SELECT    uuid() as id,  
client.title as company,  
member.`realname` as `name`,
u.email,
member.job_num,
fe.target_val,
FROM_UNIXTIME(o.yuyue_time,'%Y-%m-%d %H:%i:%s') as yuyue_time,
FROM_UNIXTIME(o.create_time,'%Y-%m-%d %H:%i:%s') as create_time,
r.check_item,
region.`name` as city_name ,
fe.created_at,
fe.expire,
fe.`case` as fei_case ,
ch.check_date,rule.title,
`viofc`.`branch` AS `branch`,
`viofc`.`bus` AS `businessunit`,
`viofc`.`sector` AS `sector`,
`viofc`.`region` AS `region`,
`viofc`.`productgroup` AS `productgroup`,
 viofc.cost_center as costcenter,
 CASE WHEN rule.care=1 THEN    '不限次数' 
 WHEN rule.care=2 THEN CONCAT(rule.times,'次') 
 ELSE '' END AS file_number
from api_feijian_case fe LEFT JOIN api_feijian_rule rule on rule.id=fe.rule_id
                         LEFT JOIN 16860_member member on fe.target_id=member.uid
                         LEFT JOIN 16860_client client on client.id=member.cid
                         LEFT JOIN 16860_ucenter u on u.id=member.uid
                         LEFT JOIN v_inn_org_for_company viofc on member.uid=viofc.uid
                         LEFT JOIN 16860_order o on o.user_id= u.id
                         LEFT JOIN 16860_quality_check ch on  ch.order_num=o.order_num
                         LEFT JOIN 16860_quality_check_result  r on  r.quality_check_id=ch.id
                         LEFT JOIN 16860_region region on  region.id=o.city_id
                         where  rule.check_field_id='uid' ;


DROP  TABLE  IF EXISTS tmp_check_people  ;
CREATE TABLE IF  NOT EXISTS tmp_check_people   AS(SELECT * FROM v_inn_checkemphasisp);



                         
-- 重点关注餐厅
 CREATE or REPLACE VIEW v_inn_checkemphasisdining AS  
SELECT  
    uuid() AS id,
    region.`name` AS city_name,
    client.title AS company,
    fe.target_val,
    r.check_item,
    reta.`name` AS restaurant_name,
    api_rest_master.online_time,
    FROM_UNIXTIME(o.create_time,'%Y-%m-%d %H:%i:%s') as create_time,
    FROM_UNIXTIME(o.yuyue_time,'%Y-%m-%d %H:%i:%s') as yuyue_time,
    reta.`status`,
    FROM_UNIXTIME(ch.check_date,'%Y-%m-%d %H:%i:%s') as check_date,
    fe.created_at,
    fe.expire,
    fe.`case` AS fei_case,
    rule.title,
 CASE WHEN rule.care=1 THEN   '不限次数' 
 WHEN rule.care=2 THEN CONCAT(rule.times,'次') 
 ELSE '' END AS file_number
from api_feijian_case fe  INNER JOIN  api_feijian_rule rule on rule.id=fe.rule_id  
                           
                          LEFT JOIN 16860_client client on client.id=fe.client_id
                          LEFT JOIN 16860_ucenter u on u.id=client.uid
                          LEFT JOIN api_restaurants reta on reta.id= fe.target_id
                          LEFT JOIN api_restaurants_master api_rest_master ON api_rest_master.id = reta.gonghai_id
                          LEFT JOIN 16860_order o on o.order_num=  CONVERT (fe.`case` USING utf8)                
                          LEFT JOIN 16860_quality_check ch on  ch.order_num=o.order_num
                          LEFT JOIN 16860_quality_check_result  r on  r.quality_check_id=ch.id
                          LEFT JOIN api_lbs_infos infos on infos.id=reta.lbs_id
                          LEFT JOIN 16860_region region on  region.id=infos.city_id
                          where  rule.check_field_id='restaurant_id';    
 
                          
DROP  TABLE  IF EXISTS tmp_check_dining  ;
CREATE TABLE IF  NOT EXISTS tmp_check_dining   AS(SELECT * FROM v_inn_checkemphasisdining);


                       
-- 飞检记录
 CREATE or REPLACE VIEW v_inn_flycheckrecord AS
SELECT uuid() as id,
	FROM_UNIXTIME(c.check_date,'%Y-%m-%d %H:%i:%s')  check_date, 
	 region.`name` as city_name ,
	c.shangpu_name,
	c.shangpu_address,
	c.check_mode,
	c.check_type,
	c.order_num,
	c.check_reason,
	c.`status`,
	c.order_money,
	ent.title as company,
	r.check_item,
	m.job_num,
	m.realname,
	c.order_predict_cost
from      16860_quality_check c
	LEFT JOIN 16860_quality_check_result r on r.quality_check_id=c.id
	LEFT JOIN 16860_client ent on ent.id=c.client_id
	LEFT JOIN 16860_region region on  region.id=c.shangpu_city_id
	LEFT JOIN 16860_member m ON m.uid=c.order_user_id ;

DROP  TABLE  IF EXISTS tmp_flycheck_record;
CREATE TABLE tmp_flycheck_record   AS(SELECT * FROM v_inn_flycheckrecord);
 

