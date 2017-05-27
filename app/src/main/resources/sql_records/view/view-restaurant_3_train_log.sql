
 CREATE OR REPLACE VIEW v_inn_train_log AS
SELECT
    UUID() AS id, 
  train_task.restaurant_id,
  api_rest.`name` AS rest_name,
  fk_citys.`name` AS city_name,
  api_rest_master.address,
  train_result.chief_name as restuarant_header,
  train_result.chief_phone,
  train_result.train_date,
  task_rule.title as train_result,
  train_result.feedback,
  train_result.train_num,
  train_result.train_type,
  train_result.tt_type,
  train_result.tc_type,
  train_result.bq_type,
  train_result.cp_type,
  v_train.count_train, 
  sales.`name` AS sales_name
FROM
    api_train_tasks train_task
LEFT JOIN  api_train_task_rules  task_rule on task_rule.id=train_task.rule_id
LEFT JOIN api_restaurants api_rest ON train_task.restaurant_id = api_rest.id
LEFT JOIN api_restaurants_master api_rest_master ON api_rest_master.id = api_rest.gonghai_id
LEFT JOIN 16860_region fk_citys ON fk_citys.id = api_rest_master.city
 LEFT JOIN api_train_task_results train_result ON train_result.train_task_id=train_task.id
LEFT JOIN api_sales sales on sales.uid=train_task.sales_uid  AND sales.deleted_at is NULL
LEFT JOIN v_inn_train_count v_train on v_train.restaurant_id=api_rest.id;

DROP  TABLE  IF EXISTS tmp_inn_train_log;
CREATE TABLE IF  NOT EXISTS tmp_inn_train_log AS (SELECT * FROM v_inn_train_log);
