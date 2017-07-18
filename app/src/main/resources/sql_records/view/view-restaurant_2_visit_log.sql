CREATE or REPLACE VIEW v_inn_visit_log AS
SELECT
    UUID() as id,
    visit_log.restaurant_id,
    visit_log.restaurant_name,
    arms.type,
    fk_citys.`name` as city_name,
    visit_log.address,
    visit_log.visit_person,
    visit_log.visit_person_phone,
    visit_log.`rest_status`,
    visit_log.visit_content,
    api_proj.prj_names,
    sales.`name` as sale_name,
    visit_log.start_time,
    sales.uid
FROM api_sales_visit_logs visit_log 
    LEFT JOIN api_restaurants_master api_rest on api_rest.id=visit_log.restaurant_id
    LEFT JOIN v_inn_restaurant_resources  arms on  arms.restaurant_id=visit_log.restaurant_id  
    LEFT JOIN v_inn_restaurant_project api_proj ON api_proj.restaurant_master_id=api_rest.id
    LEFT JOIN api_sales sales on visit_log.user_id=sales.uid and sales.deleted_at IS NULL
    LEFT JOIN 16860_region fk_citys on fk_citys.id = visit_log.city;
    

DROP  TABLE  IF EXISTS tmp_inn_visit_log;
CREATE TABLE IF  NOT EXISTS tmp_inn_visit_log   AS (SELECT * FROM v_inn_visit_log);    