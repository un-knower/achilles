##推荐人邮箱表
ALTER TABLE `api_restaurant_master_tuijian_info` DROP INDEX index_tuij_restaurant_id; 
ALTER TABLE `api_restaurant_master_tuijian_info` ADD INDEX index_tuij_restaurant_id ( `restaurant_id` ) USING BTREE;

#1#####################################################
CREATE OR REPLACE VIEW `v_inn_restaurant_project` AS SELECT
    `armp`.`restaurant_master_id` AS `restaurant_master_id`,
    group_concat(`ap`.`name` SEPARATOR ',') AS `prj_names`
FROM
    `api_restaurant_master_project` `armp`
LEFT JOIN `api_project` `ap` ON `armp`.`project_id` = `ap`.`id`
GROUP BY
    `armp`.`restaurant_master_id`;
    
#2#####################################################   
CREATE  OR REPLACE VIEW `v_inn_restaurant_shelf_time` AS SELECT
    `api_apply_recodes`.`pid` AS `pid`,
    `api_apply_recodes`.`updated_at` AS `shelf_time`,
    `api_apply_recodes`.`text` AS `reason`
FROM
    `api_apply_recodes`
WHERE
    `api_apply_recodes`.`id` IN (
        SELECT
            max(`aars`.`id`)
        FROM
            `api_apply_recodes` `aars`
        WHERE
            `aars`.`status` = 1
        AND `aars`.`table_name` = 'restaurant'
        AND `aars`.`type` = 2
        AND `aars`.`pid` IS NOT NULL
        GROUP BY
            `aars`.`pid`
    );
    
#3#####################################################
 CREATE OR REPLACE VIEW v_inn_restaurant_user_cost_union as 
SELECT
    sum(16or.predict_cost) AS count_money,
    sum(16or.actual_people) AS count_peops,
    count(1) as count_order,
    user_id,
    client_id,
    16or.restaurant_id
FROM
    16860_order 16or
WHERE
    actual_people IS NOT NULL
and actual_people <> 0
GROUP BY
    restaurant_id,client_id,user_id
UNION ALL
SELECT
    SUM(awod.actual_cost) AS count_money,
    SUM(awod.actual_people) AS count_peops,
    count(1) as count_order,
    user_id,
        client_id,
    ao.restaurant_id
FROM
    api_orders ao,
    api_waimai_order_detail awod
WHERE
    ao.id = awod.order_id and `status`='2002'
GROUP BY
    ao.restaurant_id,client_id,user_id;
        
#4#####################################################
CREATE OR REPLACE VIEW `v_inn_restaurant_consume_actual` AS SELECT
    `v_inn_restaurant_user_cost_union`.`restaurant_id` AS `restaurant_id`,
    `v_inn_restaurant_user_cost_union`.client_id,
    sum( `v_inn_restaurant_user_cost_union`.`count_money` ) AS `count_money`,
    sum( `v_inn_restaurant_user_cost_union`.`count_peops` ) AS `count_peops`,
    sum( `v_inn_restaurant_user_cost_union`.`count_order` ) AS `count_order`
FROM
    `v_inn_restaurant_user_cost_union`
GROUP BY
    `v_inn_restaurant_user_cost_union`.`restaurant_id`,client_id   ;

#4#####################################################
CREATE OR REPLACE VIEW `v_inn_restaurant_average_actual` AS SELECT
    `v_inn_restaurant_user_cost_union`.`restaurant_id` AS `restaurant_id`,
    sum( `v_inn_restaurant_user_cost_union`.`count_money` ) AS `count_money`,
    sum( `v_inn_restaurant_user_cost_union`.`count_peops` ) AS `count_peops`,
    sum( `v_inn_restaurant_user_cost_union`.`count_order` ) AS `count_order`
FROM
    `v_inn_restaurant_user_cost_union`
GROUP BY
    `v_inn_restaurant_user_cost_union`.`restaurant_id` ;    
#5#####################################################   
CREATE  OR REPLACE VIEW `v_inn_train_count` AS SELECT
    `api_train_tasks`.`restaurant_id` AS `restaurant_id`,
    count(1) AS `count_train`
FROM
    `api_train_tasks`
GROUP BY
    `api_train_tasks`.`restaurant_id`;

#6#####################################################   
CREATE  OR REPLACE VIEW `v_inn_visit_count` AS SELECT
    `api_sales_visit_logs`.`restaurant_id` AS `restaurant_id`,
    count(1) AS `count_visit`
FROM
    `api_sales_visit_logs`
GROUP BY
    `api_sales_visit_logs`.`restaurant_id`;

#7#####################################################   
CREATE  OR REPLACE VIEW `v_inn_quanlity_error_times` AS SELECT
    `qua_check`.`shanghu_id` AS `restaurant_id`,
    count(1) AS `err_times`
FROM
    `16860_quality_check` `qua_check`
WHERE
    (`qua_check`.`status` = '10')
GROUP BY
    `qua_check`.`shanghu_id`;

#8#####################################################       
CREATE OR REPLACE VIEW `v_inn_restaurant_user_cost_total` AS SELECT
    `user_cost_count`.`restaurant_id` AS `restaurant_id`,
    `user_cost_count`.`user_id` AS `user_id`,
    sum( `user_cost_count`.`count_money` ) AS `count_money`
FROM
    `v_inn_restaurant_user_cost_union` `user_cost_count`
GROUP BY
    `user_cost_count`.`restaurant_id`,
    `user_cost_count`.`user_id`;
#9#####################################################        
CREATE OR REPLACE VIEW `v_inn_restaurant_user_cost_total_max` AS SELECT
    `rest_user_cost_max`.`restaurant_id` AS `restaurant_id`,
    max( `rest_user_cost_max`.`count_money` ) AS `rest_user_max_cost`
FROM
    `v_inn_restaurant_user_cost_total` `rest_user_cost_max`
GROUP BY
    `rest_user_cost_max`.`restaurant_id`;
    
#10##################################################### 
CREATE OR REPLACE VIEW v_inn_is_support_reserve AS SELECT
    restaurant_id
FROM
    api_yuding
GROUP BY
    restaurant_id;
#11#####################################################
CREATE OR REPLACE VIEW v_inn_restaurant_takeout_type AS
SELECT
    awrd.restaurant_id,GROUP_CONCAT(awrc.`name`) as category_name
FROM
    api_waimai_restaurant_detail awrd
LEFT JOIN api_waimai_restaurant_category awrc ON awrd.category_id = awrc.id
GROUP BY
    awrd.restaurant_id;
    
CREATE OR REPLACE VIEW v_inn_restaurant_resources as 
SELECT restaurant_id ,GROUP_CONCAT( sources_code.name) as type  
    FROM api_restaurant_master_sources rest_sources
LEFT JOIN api_restaurant_source_type sources_code on rest_sources.type=sources_code.code
where rest_sources.deleted_at is NULL GROUP BY restaurant_id;