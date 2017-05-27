create or replace view  `v_inn_order_restaurant_offstaff` AS select min(`16860_offstaff_order`.`create_time`) AS `minRestTime`,`16860_offstaff_order`.`restaurant_name` AS `resid` from `16860_offstaff_order` group by `16860_offstaff_order`.`restaurant_name`;
create or replace view  `v_inn_order_restaurant_yuding` AS select min(`16860_order`.`create_time`) AS `minRestTime`,`16860_order`.`restaurant_id` AS `resid` from `16860_order` group by `16860_order`.`restaurant_id`;
create or replace view `v_inn_order_restaurant_waimai` AS select min(`api_orders`.`created_at`) AS `minRestTime`,`api_orders`.`restaurant_id` AS `resid` from `api_orders` group by `api_orders`.`restaurant_id`;
create or replace view v_inn_order_rate as 
SELECT sittlement.order_num,max(sittlement.rates) as rates
FROM quancheng_db.api_settlement sittlement 
group by sittlement.order_num;

create or replace view v_inn_order_rate_money as 
SELECT sittlement.order_num,sum(sittlement.rebate) as rate_money
FROM quancheng_db.api_finance_order sittlement 
where status =20
group by sittlement.order_num;

create or replace view v_inn_order_rating as 
select max(id) as id, ora.order_num  from 16860_order_rating ora
where ora.is_new=1
 group by ora.order_num;

 create or replace view  `v_inn_order_query` AS  
 (SELECT
        `o`.`order_num` AS `orderNum`,
        `o`.`client_id` AS `company`,
        `m`.`realname` AS `realname`,
        `m`.`job_num` AS `jobNum`,
        `u`.`email` AS `email`,
        `u`.`mobile` AS `mobile`,
        `os_branch`.`structure_name` AS `branch`,
        `os_businessunit`.`structure_name` AS `businessunit`,
        `os_sector`.`structure_name` AS `sector`,
        `os_region`.`structure_name` AS `region`,
        `os_productgroup`.`structure_name` AS `productgroup`,
        os_costcenter.structure_name as costcenter,
        CONVERT (`rants`.`name` USING utf8) AS `restaurantName`,
        `rants`.`id` AS `restaurantId`,
        `mer`.`name` AS `merchantName`,
        `sal`.`sales_id` AS `salesId`,
        `reg`.`name` AS `cityName`,
        'yuding' AS `serviceType`,
        '编内' AS `orderType`,
        `o`.`order_state` AS `payType`,
        `o`.`report_reason` AS `reportReason`,
        `o`.`is_rooms` AS `isRoom`,
        FROM_UNIXTIME(`o`.`create_time`,'%Y-%m-%d %H:%i:%s') AS `createTime`,
        FROM_UNIXTIME(`o`.`receive_time`,'%Y-%m-%d %H:%i:%s') AS `receiveTime`,
        FROM_UNIXTIME(`o`.`yuyue_time`,'%Y-%m-%d %H:%i:%s') AS `yuyueTime`,
        NULL AS `isDelivery`,
        FROM_UNIXTIME(`o`.`confirm_time`,'%Y-%m-%d %H:%i:%s') AS `confirmTime`,
        FROM_UNIXTIME(`o`.`pay_time`,'%Y-%m-%d %H:%i:%s') AS `payTime`,
        FROM_UNIXTIME(`o`.`report_time`,'%Y-%m-%d %H:%i:%s') AS `reportTime`,
        concat('yd',`o`.`order_state`) AS `status`,
        `o`.`people_num` AS `peopleNum`,
        `o`.`predict_cost` AS `predictCost`,
        `o`.`actual_people` AS `actualPeople`,
        `o`.`money` AS `money`,
        format(( `o`.`money` / `o`.`actual_people` ), 2 ) AS `averageMoney`,
        NULL AS `address`,
        IF((`restmin`.`minRestTime` = `o`.`create_time` ), 1, 0 ) AS `isRestaurantFirst`,
        CASE `asset`.`manage_type` WHEN 0 THEN '账期' WHEN 1 THEN '预付' WHEN 2 THEN '账期' WHEN 3 THEN '账期'  WHEN 4 THEN '预付' END   AS `manageType`,
        IF ((`ora`.`score` > 0), 1, 0) AS `isScore`,
        `ora`.`score` AS `score`,
        ifnull(`ora`.`create_time`, 0) AS `orderRateCreateTime`,
        ifnull(`ora`.`dish_score`, 0) AS `dishScore`,
        NULL AS `deliveryTimeScore`,
        ifnull(`ora`.`restaurant_score`, 0) AS `restaurantScore`,
        NULL AS `packagingScore`,
        ifnull( `ora`.`syt_service_score`,0) AS `sytServiceScore`,
        `ora`.`reason` AS `reason`,
        ifnull(`ora`.`reply_time`, 0) AS `replyTime`,
        ifnull(`ora`.`reply_content`, 0) AS `replyContent`,
        setment.rates,
        rate_money.rate_money AS rateMoney,
        o.approval_code   AS  approvalCode,
        ali.address AS restaurantAddress,
        `o`.is_hall AS isHall,
        `o`.message AS userComment
FROM `16860_order` `o`
    LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
    LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
    LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
    LEFT JOIN `16860_organizational_structure` `os_branch` ON `m`.`branch_id` = `os_branch`.`id`
        AND `os_branch`.`structure_type` = 'branch'
    LEFT JOIN `16860_organizational_structure` `os_businessunit` ON `m`.`businessunit_id` = `os_businessunit`.`id`
        AND `os_businessunit`.`structure_type` = 'businessunit'
    LEFT JOIN `16860_organizational_structure` `os_sector` ON `m`.`sector_id` = `os_sector`.`id`
        AND `os_sector`.`structure_type` = 'sector'
    LEFT JOIN `16860_organizational_structure` `os_productgroup` ON `m`.`productgroup_id` = `os_productgroup`.`id`
        AND `os_productgroup`.`structure_type` = 'productgroup'
    LEFT JOIN `16860_organizational_structure` `os_costcenter` ON `m`.`cost_center_id` = `os_costcenter`.`id`
        AND `os_costcenter`.`structure_type` = 'costcenter'
    LEFT JOIN `16860_organizational_structure` `os_region` ON `os_region`.`id` = `m`.`region_id`
        AND `os_region`.`structure_type` = 'region'
    LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
    LEFT JOIN `api_restaurants` `rants` ON `o`.`restaurant_id` = `rants`.`id`
    LEFT JOIN `api_lbs_infos` ali ON rants.lbs_id=ali.id
    LEFT JOIN `api_sales_rest` `sal` ON `sal`.`restaurant_id` = `rants`.`id`and sal.deleted_at is NULL
    LEFT JOIN `api_merchants` `mer` ON `rants`.`merchant_id` = `mer`.`id`
    LEFT JOIN `v_inn_order_restaurant_yuding` `restmin` ON `o`.`restaurant_id` = `restmin`.`resid`
    left join `v_inn_order_rating` oraa on oraa.order_num = o.order_num
    LEFT JOIN `16860_order_rating` `ora` ON oraa.id=ora.id
    LEFT JOIN `api_assets` `asset` ON `asset`.`id` = `rants`.`asset_id`
    LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`)
UNION ALL
   (
      SELECT
                `o`.`order_num` AS `orderNum`,
                `o`.`client_id` AS `company`,
                `m`.`realname` AS `realname`,
                `m`.`job_num` AS `jobNum`,
                `u`.`email` AS `email`,
                `u`.`mobile` AS `mobile`,
                `os_branch`.`structure_name` AS `branch`,
                `os_businessunit`.`structure_name` AS `businessunit`,
                `os_sector`.`structure_name` AS `sector`,
                `os_region`.`structure_name` AS `region`,
                `os_productgroup`.`structure_name` AS `productgroup`,
                os_costcenter.structure_name as costcenter,
                CONVERT (`rants`.`name` USING utf8) AS `restaurantName`,
                `rants`.`id` AS `restaurantId`,
                `mer`.`name` AS `merchantName`,
                `sal`.`sales_id` AS `salesId`,
                `reg`.`name` AS `cityName`,
                'waimai' AS `serviceType`,
                '编内' AS `orderType`,
                '线下' AS `payType`,
                NULL AS `reportReason`,
                NULL AS `isRoom`,
                `o`.`created_at` AS `createTime`,
                NULL AS `receiveTime`,
                `detail`.`yuyue_time` AS `yuyueTime`,
                `detail`.`is_delivery` AS `isDelivery`,
                `detail`.`confirm_time` AS `confirmTime`,
                `detail`.`paid_time`AS `payTime`,
                NULL AS `reportTime`,
                concat('wm',`o`.`status`) AS `status`,
                `detail`.`people_num` AS `peopleNum`,
                `detail`.`predict_cost` AS `predictCost`,
                `detail`.`actual_people` AS `actualPeople`,
                `detail`.`actual_cost` AS `money`,
                format( ( `detail`.`actual_cost` / `detail`.`actual_people` ),  2 ) AS `averageMoney`,
                `detail`.`address` AS `address`,
                IF((`restmin`.`minRestTime` = `o`.`created_at` ), 1, 0 ) AS `isRestaurantFirst`,
                CASE `asset`.`manage_type` WHEN 0 THEN '账期' WHEN 1 THEN '预付' WHEN 2 THEN '账期' WHEN 3 THEN '账期'  WHEN 4 THEN '预付' END  AS `manageType`,
                IF ((`ora`.`score` > 0), 1, 0) AS `isScore`,
                `ora`.`score` AS `score`,
                ifnull(`ora`.`create_time`, 0) AS `orderRateCreateTime`,
                NULL AS `dishScore`,
                ifnull( `ora`.`delivery_time_score`, 0 ) AS `deliveryTimeScore`,
                NULL AS `restaurantScore`,
                ifnull( `ora`.`commodity_packaging_score`, 0 ) AS `packagingScore`,
                ifnull( `ora`.`syt_service_score`, 0 ) AS `sytServiceScore`,
                `ora`.`reason` AS `reason`,
                ifnull(`ora`.`reply_time`, 0) AS `replyTime`,
                ifnull(`ora`.`reply_content`, 0) AS `replyContent`,
                setment.rates,
                rate_money.rate_money AS rateMoney,
                detail.approval_code   AS  approvalCode,
                ali.address AS restaurantAddress,
                NULL AS isHall,
                CONVERT (`o`.comment USING utf8) as userComment
FROM `api_orders` `o`
    LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
    LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
    LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
    LEFT JOIN `16860_organizational_structure` `os_branch` ON `m`.`branch_id` = `os_branch`.`id`
        AND `os_branch`.`structure_type` = 'branch'
    LEFT JOIN `16860_organizational_structure` `os_businessunit` ON `m`.`businessunit_id` = `os_businessunit`.`id`
        AND `os_businessunit`.`structure_type` = 'businessunit'
    LEFT JOIN `16860_organizational_structure` `os_sector` ON `m`.`sector_id` = `os_sector`.`id`
        AND `os_sector`.`structure_type` = 'sector'
    LEFT JOIN `16860_organizational_structure` `os_productgroup` ON `m`.`productgroup_id` = `os_productgroup`.`id`
        AND `os_productgroup`.`structure_type` = 'productgroup'
    LEFT JOIN `16860_organizational_structure` `os_costcenter` ON `m`.`cost_center_id` = `os_costcenter`.`id`
        AND `os_costcenter`.`structure_type` = 'costcenter'
    LEFT JOIN `16860_organizational_structure` `os_region` ON `os_region`.`id` = `m`.`region_id`
        AND `os_region`.`structure_type` = 'region'
    LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
    LEFT JOIN `api_restaurants` `rants` ON `o`.`restaurant_id` = `rants`.`id`
    LEFT JOIN `api_lbs_infos` ali ON `rants`.lbs_id=ali.id
    LEFT JOIN `api_sales_rest` `sal` ON `sal`.`restaurant_id` = `rants`.`id` and sal.deleted_at is NULL
    LEFT JOIN `api_merchants` `mer` ON `rants`.`merchant_id` = `mer`.`id`
    LEFT JOIN `api_waimai_order_detail` `detail` ON `detail`.`order_id` = `o`.`id`
    LEFT JOIN `v_inn_order_restaurant_waimai` `restmin` ON `o`.`restaurant_id` = `restmin`.`resid` 
    LEFT JOIN `api_assets` `asset` ON `asset`.`id` = `rants`.`asset_id`
    LEFT JOIN `v_inn_order_rating` oraa on oraa.order_num = o.order_num
    LEFT JOIN `16860_order_rating` `ora` ON oraa.id=ora.id
    LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`)
UNION ALL
   (
      SELECT
            `o`.`order_num` AS `orderNum`,
            `o`.`client_id` AS `company`,
            `m`.`realname` AS `realname`,
            `m`.`job_num` AS `jobNum`,
            `u`.`email` AS `email`,
            `u`.`mobile` AS `mobile`,
            `os_branch`.`structure_name` AS `branch`,
            `os_businessunit`.`structure_name` AS `businessunit`,
            `os_sector`.`structure_name` AS `sector`,
            `os_region`.`structure_name` AS `region`,
            `os_productgroup`.`structure_name` AS `productgroup`,
            os_costcenter.structure_name as costcenter,
            `o`.`restaurant_name` AS `restaurantName`,
            NULL AS `restaurantId`,
            NULL AS `merchantName`,
            NULL AS `salesId`,
            `reg`.`name` AS `cityName`,
            `o`.`dining_type` AS `serviceType`,
            '编外' AS `orderType`,
            '线下' AS `payType`,
            NULL AS `reportReason`,
            NULL AS `isRoom`,
            `o`.`create_time` AS `createTime`,
            NULL AS `receiveTime`,
            `o`.`yuyue_time` AS `yuyueTime`,
            NULL AS `isDelivery`,
            `o`.`confirm_time`AS `confirmTime`,
            NULL AS `payTime`,
            NULL AS `reportTime`,
            concat('bw',`o`.`status`) AS `status`,
            o.people_Num AS `peopleNum`,
            `o`.`predict_cost` AS `predictCost`,
            `o`.`actual_people` AS `actualPeople`,
            `o`.`money` AS `money`,
            format( ( `o`.`money` / `o`.`actual_people` ), 2 ) AS `averageMoney`,
            NULL AS `address`,
            IF ( ( `restmin`.`minRestTime` = `o`.`create_time` ), 1, 0 ) AS `isRestaurantFirst`,
			NULL AS `manageType`,
			NULL AS `isScore`,
			NULL AS `score`,
			NULL AS `orderRateCreateTime`,
			NULL AS `dishScore`,
			NULL AS `deliveryTimeScore`,
			NULL AS `restaurantScore`,
			NULL AS `packagingScore`,
			NULL AS `sytServiceScore`,
			NULL AS `reason`,
			NULL AS `replyTime`,
			NULL AS `replyContent`,
			setment.rates,
			rate_money.rate_money as rateMoney,
			`o`.approval_code AS  approvalCode,
			NULL AS restaurantAddress,
			NULL AS isHall,
			NULL AS userComment
   FROM
        `16860_offstaff_order` `o`
        LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
        LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
        LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
        LEFT JOIN `16860_organizational_structure` `os_branch` ON `m`.`branch_id` = `os_branch`.`id`
            AND `os_branch`.`structure_type` = 'branch'
        LEFT JOIN `16860_organizational_structure` `os_businessunit` ON `m`.`businessunit_id` = `os_businessunit`.`id`
            AND `os_businessunit`.`structure_type` = 'businessunit'
        LEFT JOIN `16860_organizational_structure` `os_sector` ON `m`.`sector_id` = `os_sector`.`id`
            AND `os_sector`.`structure_type` = 'sector'
        LEFT JOIN `16860_organizational_structure` `os_productgroup` ON `m`.`productgroup_id` = `os_productgroup`.`id`
            AND `os_productgroup`.`structure_type` = 'productgroup'
        LEFT JOIN `16860_organizational_structure` `os_costcenter` ON `m`.`cost_center_id` = `os_costcenter`.`id`
            AND `os_costcenter`.`structure_type` = 'costcenter'
        LEFT JOIN `16860_organizational_structure` `os_region` ON `os_region`.`id` = `m`.`region_id`
            AND `os_region`.`structure_type` = 'region'
        LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
        LEFT JOIN `v_inn_order_restaurant_offstaff` `restmin` ON `o`.`restaurant_name` = `restmin`.`resid`
        LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`
   );
DROP  TABLE  IF EXISTS tmp_order_query  ;
CREATE TABLE IF  NOT EXISTS tmp_order_query   AS(SELECT * FROM v_inn_order_query);