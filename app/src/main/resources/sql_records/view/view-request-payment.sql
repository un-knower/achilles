-- 催款的sql
CREATE
OR REPLACE VIEW v_inn_order_info AS SELECT
    concat('wm',`a_order`.`status`)  AS order_state,
    a_order.created_at AS yuyue_time,
    a_order.total_num as actual_people,
    a_order.money,
    a_order.order_num,
    a_order.restaurant_id,
    a_order.user_id,
    a_order.city_id
FROM
    api_orders a_order
UNION ALL
    SELECT
        concat('yd',`168order`.`order_state`)   as order_state,
        FROM_UNIXTIME( 168order.yuyue_time,  '%Y-%m-%d %H:%i:%s'  ) AS yuyue_time,
        168order.actual_people,
        168order.money,
        168order.order_num,
        168order.restaurant_id,
        168order.user_id,
        168order.city_id
    FROM
        16860_order 168order;
        
create or replace view `v_inn_request_patment_log` as 
select
  if(model = 'order_dunning', (select order_num from 16860_order where id = al.record_id), (select order_num from api_orders where id = al.record_id)) as 'order_num',
  m.realname as 'kefu',
  al.remark as 'remark',
  FROM_UNIXTIME(al.create_time) as 'created_at',
  al.status
from 16860_action_log al
inner join 16860_manager m on m.uid = al.user_id
 where al.action_id = 14;
 
create or replace view  `v_inn_requestpatment` as 
  SELECT
        UUID() AS id,
        record.order_num,
        orderinfo.city_id,
        `reg`.`name` AS `city_name`,
        u.mobile,
        m.realname,
        m.job_num,
		orderinfo.order_state,
        CONVERT (`a_rest`.`name` USING utf8) AS `restaurant_name`,
        orderinfo.yuyue_time as yuyue_time,
        orderinfo.actual_people,
        orderinfo.money,
        record.status,
        record.kefu,
        record.created_at,
        record.remark
     FROM v_inn_request_patment_log record
        LEFT JOIN v_inn_order_info orderinfo ON orderinfo.order_num = record.order_num
	    LEFT JOIN 16860_ucenter u ON u.id = orderinfo.user_id
	    LEFT JOIN 16860_member m ON m.uid = orderinfo.user_id
 	    LEFT JOIN api_restaurants a_rest ON a_rest.id = orderinfo.restaurant_id
		LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `orderinfo`.`city_id`
    where record.order_num is not NULL;
 
DROP  TABLE  IF EXISTS tmp_request_payment  ;
CREATE TABLE IF  NOT EXISTS tmp_request_payment   AS(SELECT * FROM v_inn_requestpatment);
