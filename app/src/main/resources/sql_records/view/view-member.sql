# 最近三个月用户在各餐厅的消费
CREATE OR REPLACE VIEW v_inn_user_restaurant_expense_3_month AS
    SELECT 
        CONCAT(user_id, '+', restaurant_id) AS user_restaurant,
        user_id,
        restaurant_id,
        SUM(money) AS total_money
    FROM
        quancheng_db.`16860_order`
    WHERE
        pay_time > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 3 MONTH))
    GROUP BY user_restaurant;

# 最近三个月用户在各餐厅的最大消费和总消费
CREATE OR REPLACE VIEW v_inn_user_restaurant_exp_max_total_3_month AS
    SELECT 
        user_id,
        MAX(total_money) AS max_in_3_month,
        SUM(total_money) AS total_in_3_month
    FROM
        v_inn_user_restaurant_expense_3_month
    GROUP BY user_id;

# 用户

CREATE OR REPLACE VIEW `v_inn_member` AS
    SELECT 
        `m`.`uid` AS `id`,
        `m`.`cid` AS `cid`,
        `m`.`realname` AS `realname`,
        `m`.`job_num` AS `job_num`,
        `uc`.`username` AS `username`,
        `uc`.`mobile` AS `mobile`,
        `uc`.`email` AS `email`,
        `c`.`name` AS `client_name`,
        `c`.`title` AS `client_title`,
        `m`.`superior_email` AS `superior_email`,
        `m`.`superior_name` AS `superior_name`,
        `m`.`city` AS `city`,
        `m`.`city_id` AS `city_id`,
        `m`.`fromtype` AS `fromtype`,
        `m`.`login_num` AS `login_num`,
        `m`.`status` AS `status`,
        `m`.`autologin` AS `autologin`,
        `m`.`pid` AS `pid`,
        `m`.`level` AS `level`,
        `m`.`created_at` AS `created_at`,
        `m`.`updated_at` AS `updated_at`,
        `m`.`deleted_at` AS `deleted_at`,
        `m`.`mistype_num` AS `mistype_num`,
        `m`.`failure_time` AS `failure_time`,
        `m`.`activation_time` AS `activation_time`,
        `ci`.`invoice_title` AS `invoice_title`,
        `m`.`reason` AS `reason`,
        `os_branch`.`structure_name` AS `branch`,
        `os_businessunit`.`structure_name` AS `businessunit`,
        `os_sector`.`structure_name` AS `sector`,
        `os_region`.`structure_name` AS `region`,
        `os_productgroup`.`structure_name` AS `productgroup`,
         os_costcenter.structure_name as costcenter,
        `uc`.`reg_time` AS `reg_time`,
        v1.max_in_3_month,
        v1.total_in_3_month,
        (CASE
            WHEN (`uc`.`reg_time` > 0) THEN (TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(`uc`.`reg_time`)))
            WHEN (`uc`.`created_at` > '2014-04-01') THEN (TO_DAYS(NOW()) - TO_DAYS(`uc`.`created_at`))
            ELSE (TO_DAYS(NOW()) - TO_DAYS('2014-04-01'))
        END) AS `reg_days`,
        COUNT(0) AS `order_count`,
        SUM(`o`.`money`) AS `order_amount`,
        AVG(ABS(((`o`.`original_cost` / `o`.`actual_people`) - (`o`.`predict_cost` / `o`.`people_num`)))) AS `per_mean_diff`,
        AVG((`o`.`yuyue_time` - `o`.`create_time`)) AS `pre_yuyue_index`,
        COUNT(DISTINCT (CASE
                WHEN (`fcase`.`id` > 0) THEN 1
                ELSE NULL
            END)) AS `case_times`
    FROM
        ((((((((((((`16860_member` `m`
        LEFT JOIN `16860_organizational_structure` `os_branch` ON (((`m`.`branch_id` = `os_branch`.`id`)
            AND (`os_branch`.`structure_type` = 'branch'))))
        LEFT JOIN `16860_organizational_structure` `os_businessunit` ON (((`m`.`businessunit_id` = `os_businessunit`.`id`)
            AND (`os_businessunit`.`structure_type` = 'businessunit'))))
        LEFT JOIN `16860_organizational_structure` `os_sector` ON (((`m`.`sector_id` = `os_sector`.`id`)
            AND (`os_sector`.`structure_type` = 'sector'))))
        LEFT JOIN `16860_organizational_structure` `os_productgroup` ON (((`m`.`productgroup_id` = `os_productgroup`.`id`)
            AND (`os_productgroup`.`structure_type` = 'productgroup'))))
        LEFT JOIN `16860_client` `c` ON ((`m`.`cid` = `c`.`id`)))
        LEFT JOIN `16860_client_invoice` `ci` ON ((`m`.`invoice_cid` = `ci`.`id`)))
        LEFT JOIN `16860_organizational_structure` `os_costcenter` ON (((`m`.`cost_center_id` = `os_costcenter`.`id`)
            AND (`os_costcenter`.`structure_type` = 'costcenter'))))
        LEFT JOIN `16860_organizational_structure` `os_region` ON (((`os_region`.`id` = `m`.`region_id`)
            AND (`os_region`.`structure_type` = 'region'))))
        LEFT JOIN `api_feijian_case` `fcase` ON ((  CONCAT(`m`.`uid`,'')  = `fcase`.`target_id`)))
        LEFT JOIN `api_feijian_rule` `frule` ON (((`fcase`.`rule_id` = `frule`.`id`)
            AND (`frule`.`check_field_id` = 'uid'))))
        LEFT JOIN `16860_order` `o` ON ((`m`.`uid` = `o`.`user_id`)))
        LEFT JOIN `16860_ucenter` `uc` ON ((`m`.`uid` = `uc`.`id`)))
        LEFT JOIN v_inn_user_restaurant_exp_max_total_3_month v1 ON (`m`.`uid` = v1.user_id)
        WHERE m.deleted_at is NULL
        GROUP BY `m`.`uid`;
        
DROP  TABLE  IF EXISTS tmp_member;
CREATE TABLE tmp_member   AS(SELECT * FROM v_inn_member);

