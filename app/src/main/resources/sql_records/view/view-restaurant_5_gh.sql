CREATE OR REPLACE VIEW v_inn_restaurant_recom_email AS 
SELECT
    uuid() as id,
    restaurant_id,
    GROUP_CONCAT(user_email) as user_email
FROM
    api_restaurant_master_tuijian_info
GROUP BY
    restaurant_id;
    
CREATE OR REPLACE VIEW v_inn_company_own_rest as 
SELECT
    uuid() as id,
	rc.restaurant_id AS olid,
	168client.id AS own_companys_id,
	168client.title   AS own_companys
	FROM  16860_restaurant_client rc 
		LEFT JOIN 16860_client 168client on 168client.id=rc.client_id 
where  rc.deleted_at is null;

create view v_inn_restaurant_rate as
SELECT 
    rateable_id,
    GROUP_CONCAT(CONCAT( ifnull(`start`,''), '-', ifnull(`end`,''), ':', `rate`)) as rebate
FROM
    api_rates
WHERE
    deleted_at IS NULL
 GROUP BY rateable_id;


CREATE or REPLACE VIEW v_inn_restaurant_gh_statistic AS
SELECT
  uuid() as id,
  gh.id as ghid,
  ol.id as olid,
  ol.name AS store_name,
  ol.invoice_title as rest_invoice_title,
  gh.city,
  restaurant_citys.`name` as city_display,
  gh.area,
  restaurant_area.`name` as area_display,
  ifnull(ali.address,gh.address) as address,
  amc.`name` as rest_merchant,
  amc.id AS rest_merchant_id,
  ol.`status` AS rest_status,
  gh.`status` AS gonghai_status,
  arms.type as restaurant_sources,
  gh.`name` as gh_task_name,
  armti.user_email as recommends_emails,
  arms.type as recommends_company,
  gh.priority,
  rest_proj.prj_names,
  gh.created_at as in_store_at,
  ol.created_at as created_at,
  gh.online_time,
  arr.reason,
  arr.shelf_time,
  ol.consume,
  ROUND(order_info.count_money/order_info.count_peops,2) AS  avg_actual,
  ol.consume-ROUND(order_info.count_money/order_info.count_peops,2) as consume_deviation,
  awrd.category_name,
  ol.cook_style,
  aa.manage_type,
  train_times.count_train,
  visit_log.count_visit,
  order_info.count_money,
  order_info.count_order,
  qua_ck_err_times.err_times,
  ROUND(rest_user_max.rest_user_max_cost/order_info.count_money,2) AS user_concentration,
  sales.`name` as sales_name,
  CASE WHEN awrd.restaurant_id IS NULL THEN'1' ELSE '0' END as support_takeout_of_food,
  CASE WHEN sup_reserve.restaurant_id IS NULL THEN '1' ELSE '0' END support_reserve,
  null  AS own_companys_id,
  null  AS own_companys,
  rest_rate.rebate,
  ay.box_num
FROM api_restaurants_master gh
LEFT JOIN api_restaurants ol ON gh.id = ol.gonghai_id
LEFT JOIN 16860_region restaurant_citys ON restaurant_citys.id=gh.city
LEFT JOIN 16860_region restaurant_area ON restaurant_area.id=gh.area
LEFT JOIN api_lbs_infos ali ON ol.lbs_id=ali.id /*地址*/
LEFT JOIN api_merchants amc ON ol.merchant_id=amc.id /* 商户*/
LEFT JOIN v_inn_restaurant_resources  arms ON gh.id=arms.restaurant_id  /*餐厅来源*/
LEFT JOIN v_inn_restaurant_recom_email armti ON gh.id=armti.restaurant_id /*推荐人邮箱*/
LEFT JOIN v_inn_restaurant_project rest_proj ON gh.id=rest_proj.restaurant_master_id /*项目名 */
LEFT JOIN v_inn_restaurant_shelf_time arr ON arr.pid=ol.id /* 下线原因和下线时间*/
LEFT JOIN v_inn_restaurant_average_actual order_info ON ol.id=order_info.restaurant_id /*实际人均*/
LEFT JOIN v_inn_restaurant_takeout_type awrd ON awrd.restaurant_id=ol.id
LEFT JOIN api_assets aa ON ol.asset_id=aa.id
LEFT JOIN v_inn_train_count train_times ON train_times.restaurant_id=ol.id
LEFT JOIN v_inn_visit_count visit_log ON visit_log.restaurant_id=gh.id
LEFT JOIN v_inn_quanlity_error_times qua_ck_err_times ON qua_ck_err_times.restaurant_id=ol.id
LEFT JOIN v_inn_restaurant_user_cost_total_max rest_user_max ON rest_user_max.restaurant_id=ol.id
LEFT JOIN api_sales_rest asr ON asr.restaurant_id=ol.id AND asr.deleted_at IS NULL
LEFT JOIN api_sales sales on asr.sales_id=sales.id AND sales.deleted_at is null
LEFT JOIN v_inn_is_support_reserve sup_reserve ON sup_reserve.restaurant_id=ol.id
LEFT JOIN v_inn_restaurant_rate  rest_rate ON rest_rate.rateable_id=ol.asset_id
LEFT JOIN api_yuding ay ON ay.restaurant_id=ol.id
WHERE ol.deleted_at is null and gh.deleted_at is null;

DROP  TABLE  IF EXISTS tmp_restaurant_query;
CREATE TABLE IF  NOT EXISTS tmp_restaurant_query   AS (SELECT * FROM v_inn_restaurant_gh_statistic);
 