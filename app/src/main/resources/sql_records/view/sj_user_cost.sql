drop table if exists sj_user_cost;
drop table if exists sj_user_cost_city;
drop table if exists sj_fly_check_month_stastic;
drop table if exists sj_order_score_month;

CREATE TABLE `sj_user_cost` (
  `id` varchar(36) DEFAULT NULL,
  `client_id` int(10) NOT NULL COMMENT '客户ID',
  `happen_date` varchar(7) NOT NULL DEFAULT '' COMMENT '日期',
  `data_type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `current_value` bigint(21) NOT NULL DEFAULT '0' ,
  `last_month_than` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '环比上月'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户数据';


CREATE TABLE `sj_user_cost_city` (
  `id` varchar(36) DEFAULT NULL,
  `client_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户',
  `city` varchar(120) DEFAULT '' COMMENT '区域名称',
  `current_value` decimal(32,2) DEFAULT NULL ,
   `data_type` varchar(9) NOT NULL DEFAULT ''  COMMENT '类型',
  `happen_date` varchar(7) NOT NULL DEFAULT '' COMMENT '日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8  comment '用户/消费金额(城市)';

CREATE TABLE `sj_fly_check_month_stastic` (
  `id` varchar(36) DEFAULT NULL,
  `client_id` int(11) NOT NULL DEFAULT '0'  COMMENT '企业',
  `happen_date` varchar(7) NOT NULL DEFAULT '' COMMENT '日期',
  `city` varchar(120) DEFAULT '' COMMENT '区域名称',
  `order_count` bigint(21) NOT NULL DEFAULT '0'  COMMENT '订单数',
  `check_count` bigint(21) NOT NULL DEFAULT '0'  COMMENT '飞检数',
  `ing_count_correct` bigint(21) NOT NULL DEFAULT '0'  COMMENT '事中-正常',
  `ing_count_incorrect` bigint(21) NOT NULL DEFAULT '0'  COMMENT '事中-异常',
  `ing_check_rate` decimal(26,2) DEFAULT NULL  COMMENT '事中-飞检比例',
  `ing_count_reason` text CHARACTER SET utf8mb4  COMMENT '事中-异常原因',
  `after_count_correct` bigint(21) NOT NULL DEFAULT '0'  COMMENT '事后-正常',
  `after_count_incorrect` bigint(21) NOT NULL DEFAULT '0' COMMENT '事后-异常',
  `after_check_rate` decimal(26,2) DEFAULT NULL  COMMENT '事后-检查比例',
  `after_count_reason` text CHARACTER SET utf8mb4  COMMENT '事后-异常原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8  comment '飞检月度统计';

CREATE TABLE `sj_order_score_month` (
  `id` varchar(36) DEFAULT NULL,
  `client_id` int(10) DEFAULT NULL COMMENT '企业',
  `happen_date` varchar(7) NOT NULL DEFAULT '' COMMENT '日期',
  `score` varchar(6) DEFAULT NULL   COMMENT '评分',
  `order_count` bigint(21) NOT NULL DEFAULT '0'  COMMENT '订单数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '订单评分';

create or replace view v_inn_sj_order_had_payment as 
	select 
		`o`.order_num,
		`o`.client_id,
		`o`.user_id ,
		`o`.money,
		`o`.`city_id`,
        FROM_UNIXTIME(`o`.`pay_time`,'%Y-%m-%d %H:%i:%s')  as pay_at
	from 
		`16860_order` `o`
	where o.order_state in (35,36)
union all
	select 
		`o`.order_num,
		`o`.client_id,
		`o`.user_id ,
		`detail`.actual_cost as money,
		`o`.`city_id`,
        `detail`.`paid_time`as pay_at
	from 
		`api_orders` `o`
		LEFT JOIN `api_waimai_order_detail` `detail` ON `detail`.`order_id` = `o`.`id`
	where `o`.`status` in (2002,2030)
union all
	select 
		`o`.order_num,
		`o`.client_id ,
        `o`.user_id ,
		`o`.`money` AS `money`,
		`o`.`city_id`,
        `o`.update_time as pay_at
	from
		`16860_offstaff_order` `o`
	where `o`.`status`=20
	;
	
DELIMITER $$
CREATE FUNCTION 
	fn_parseJson(
		p_jsonstr VARCHAR(4000) character set utf8, 
        p_key VARCHAR(255)) 
RETURNS VARCHAR(255)
  BEGIN
	DECLARE rtnVal VARCHAR(255);
	DECLARE v_key VARCHAR(255);
	SET v_key = CONCAT('"', p_key, '":');
	SET @v_flag = p_jsonstr REGEXP v_key;
	IF(@v_flag = 0) THEN 
		SET rtnVal = '';
	ELSE
	SELECT val INTO rtnVal FROM (
	SELECT @start_pos := locate(v_key, p_jsonstr), 
	@end_pos := @start_pos + length(v_key), 
	@tail_pos := if(locate(",", p_jsonstr, @end_pos) = 0, locate("}", p_jsonstr, @end_pos), locate(",", p_jsonstr, @end_pos)), 
	substring(p_jsonstr, @end_pos + 1, @tail_pos - @end_pos - 2) as val) as t;   
  END IF;
  RETURN rtnVal;  
END $$	
