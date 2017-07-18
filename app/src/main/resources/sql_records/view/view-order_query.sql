CREATE OR REPLACE VIEW `v_inn_order_restaurant_offstaff` AS
    SELECT 
        MIN(`16860_offstaff_order`.`create_time`) AS `minRestTime`,
        `16860_offstaff_order`.`restaurant_name` AS `resid`
    FROM
        `16860_offstaff_order`
    GROUP BY `16860_offstaff_order`.`restaurant_name`;
CREATE OR REPLACE VIEW `v_inn_order_restaurant_yuding` AS
    SELECT 
        MIN(`16860_order`.`create_time`) AS `minRestTime`,
        `16860_order`.`restaurant_id` AS `resid`
    FROM
        `16860_order`
    GROUP BY `16860_order`.`restaurant_id`;
CREATE OR REPLACE VIEW `v_inn_order_restaurant_waimai` AS
    SELECT 
        MIN(`api_orders`.`created_at`) AS `minRestTime`,
        `api_orders`.`restaurant_id` AS `resid`
    FROM
        `api_orders`
    GROUP BY `api_orders`.`restaurant_id`;

CREATE OR REPLACE VIEW v_inn_order_rate AS
    SELECT 
        sittlement.order_num, MAX(sittlement.rates) AS rates
    FROM
        quancheng_db.api_settlement sittlement
    GROUP BY sittlement.order_num;
/*
 * 返点金额、had_credence是否有打款凭证、had_invoice是否有返点发票
 * */
CREATE OR REPLACE VIEW v_inn_order_rate_money AS
    SELECT 
        sittlement.order_num,
        SUM(sittlement.rebate) AS rate_money,
        CASE
            WHEN MAX(iscredence) = 2 THEN '有'
            ELSE '无'
        END AS had_credence,
        CASE
            WHEN MAX(is_invoice) = 1 THEN '有'
            ELSE '无'
        END AS had_invoice
    FROM
        quancheng_db.api_finance_order sittlement
    WHERE
        status = 20 AND deleted_at IS NULL
    GROUP BY sittlement.order_num;

create or replace view v_inn_order_rating as 
select max(id) as id, ora.order_num  from 16860_order_rating ora
where ora.is_new=1
 group by ora.order_num;
 
  -- 订单支付卡号
create or replace view v_inn_order_pay_card as 
SELECT 
    order_num, GROUP_CONCAT(distinct bank_card) as bank_card,
    case when max(type)=1 then '银商'when  max(type)=2 then '易宝' when  max(type)=3 then '支付宝' end  as pay_type
FROM
    16860_order_payment
    where is_paid=1
GROUP BY order_num;        
 
--外卖接单时间(可能,最早响应时间)
 create or replace view v_inn_waimai_receive_time as 
SELECT 
    record_id,from_unixtime(min(create_time)) as waimai_receive_time
FROM
    quancheng_db.`16860_action_log` al
WHERE
    al.action_id IN (12 , 13)
        AND al.model = 'waimai_order'
        group by record_id;
        
--预定拒单时间(可能,最早响应时间)
create or replace view v_inn_reversion_receive_time as 
SELECT 
    record_id,from_unixtime(min(create_time)) as action_time
FROM
    quancheng_db.`16860_action_log` al
WHERE
    al.action_id IN (12 , 13)
        AND al.model = 'order'
        group by record_id;
        
 create or replace view  `v_inn_order_query` AS  
 (SELECT
        `o`.`order_num` AS `orderNum`,
        `o`.`client_id` AS `company`,
        `m`.`realname` AS `realname`,
        `m`.`job_num` AS `jobNum`,
        `u`.`email` AS `email`,
        `u`.`mobile` AS `mobile`,
        `viofc`.`branch` AS `branch`,
        `viofc`.`bus` AS `businessunit`,
        `viofc`.`sector` AS `sector`,
        `viofc`.`region` AS `region`,
        `viofc`.`productgroup` AS `productgroup`,
        viofc.cost_center as costcenter,
        CONVERT (`rants`.`name` USING utf8) AS `restaurantName`,
        `rants`.`id` AS `restaurantId`,
        rants.invoice_title as rest_invoice_title,
        `mer`.`name` AS `merchantName`,
        `sal`.`sales_id` AS `salesId`,
        `reg`.`name` AS `cityName`,
        'yuding' AS `serviceType`,
        '编内' AS `orderType`,
        case `o`.`order_state` when 35 then '线上' when 36 then  '线下' end AS `payType`  ,
        `o`.`report_reason` AS `reportReason`,
        `o`.`is_rooms` AS `isRoom`,
        FROM_UNIXTIME(`o`.`create_time` ) AS `createTime`,
        CASE WHEN order_state in(7,11,15)  THEN  action_time ELSE FROM_UNIXTIME(`o`.`receive_time` ) end AS `receiveTime`,
        FROM_UNIXTIME(`o`.`yuyue_time` ) AS `yuyueTime`,
        NULL AS `isDelivery`,
        FROM_UNIXTIME(`o`.`confirm_time` ) AS `confirmTime`,
        FROM_UNIXTIME(`o`.`pay_time` ) AS `payTime`,
        FROM_UNIXTIME(`o`.`report_time` ) AS `reportTime`,
        concat('yd',`o`.`order_state`) AS `status`,
        `o`.`people_num` AS `peopleNum`,
        `o`.`predict_cost` AS `predictCost`,
        `o`.`actual_people` AS `actualPeople`,
        `o`.`money` AS `money`,
        format(( `o`.`money` / `o`.`actual_people` ), 2 ) AS `averageMoney`,
        NULL AS `address`,
        IF((`restmin`.`minRestTime` = `o`.`create_time` ), 1, 0 ) AS `isRestaurantFirst`,
        CASE `asset`.`manage_type` WHEN 0 THEN '账期' WHEN 1 THEN '预付' WHEN 2 THEN '账期' WHEN 3 THEN '账期'  WHEN 4 THEN '预付' END   AS `manageType`,
        IF ((`ora`.`id`is not null), 1, 0) AS `isScore`,
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
        convert(ali.address using utf8) AS restaurantAddress,
        `o`.is_hall AS isHall,
        `o`.message AS userComment,
        opc.bank_card as cardNumber,
        ali.id as rest_area_id,
        reg_area.name as rest_area_name,
        asset.bank_name as merchant_bank_name,
        asset.bank_account as merchant_bank_account,
        -- case asset.account_type when 1 then '对私' when 2 then '对公' end  as merchant_account_type,
        case asset.account_type when length(bank_account_name)<=12 then '对私' when length(bank_account_name)>12 then '对公' end as merchant_account_type,
        rate_money.had_credence as had_credence,/*是否有打款凭证*/
        rate_money.had_invoice as had_invoice,/*是否有返点发票*/
        case when busi.voucher  is not null then '有'  else '无' end as had_voucher, /*代收款证明*/
        opc.pay_type 
FROM `16860_order` `o`
    LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
    LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
    LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
    LEFT JOIN  v_inn_org_for_company viofc on m.uid=viofc.uid
    LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
    LEFT JOIN `api_restaurants` `rants` ON `o`.`restaurant_id` = `rants`.`id`
    LEFT JOIN  `api_businesses` busi on busi.id=rants.business_id and busi.deleted_at is null
    LEFT JOIN `api_lbs_infos` ali ON rants.lbs_id=ali.id
    LEFT JOIN `api_sales_rest` `sal` ON `sal`.`restaurant_id` = `rants`.`id`and sal.deleted_at is NULL
    LEFT JOIN `api_merchants` `mer` ON `rants`.`merchant_id` = `mer`.`id`
    LEFT JOIN `v_inn_order_restaurant_yuding` `restmin` ON `o`.`restaurant_id` = `restmin`.`resid`
    LEFT JOIN `v_inn_order_rating` oraa on oraa.order_num = o.order_num
    LEFT JOIN `16860_order_rating` `ora` ON oraa.id=ora.id
    LEFT JOIN `api_assets` `asset` ON `asset`.`id` = `rants`.`asset_id`
    LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`
    LEFT JOIN `16860_region` `reg_area` ON `reg_area`.`id` =ali.area_id
    LEFT JOIN v_inn_order_pay_card opc ON   o.order_num = opc.order_num
    LEFT JOIN v_inn_reversion_receive_time viwrt on  `o`.id=viwrt.record_id
    )
UNION ALL
   (
      SELECT
                `o`.`order_num` AS `orderNum`,
                `o`.`client_id` AS `company`,
                `m`.`realname` AS `realname`,
                `m`.`job_num` AS `jobNum`,
                `u`.`email` AS `email`,
                `u`.`mobile` AS `mobile`,
                `viofc`.`branch` AS `branch`,
		        `viofc`.`bus` AS `businessunit`,
		        `viofc`.`sector` AS `sector`,
		        `viofc`.`region` AS `region`,
		        `viofc`.`productgroup` AS `productgroup`,
		        viofc.cost_center as costcenter,
                CONVERT (`rants`.`name` USING utf8) AS `restaurantName`,
                `rants`.`id` AS `restaurantId`,
                rants.invoice_title as rest_invoice_title,
                `mer`.`name` AS `merchantName`,
                `sal`.`sales_id` AS `salesId`,
                `reg`.`name` AS `cityName`,
                'waimai' AS `serviceType`,
                '编内' AS `orderType`,
                case `o`.`status` when 2002 then '线上' when 2003 then  '线下' end AS `payType`,
                NULL AS `reportReason`,
                NULL AS `isRoom`,
                `o`.`created_at` AS `createTime`,
                viwrt.waimai_receive_time AS `receiveTime`,
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
                IF ((`ora`.`id`is not null), 1, 0) AS `isScore`,
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
                convert(ali.address using utf8) AS restaurantAddress,
                NULL AS isHall,
                CONVERT (`o`.comment USING utf8) as userComment,
                opc.bank_card as cardNumber,
                ali.area_id as rest_area_id,
                reg_area.name as rest_area_name,
                asset.bank_name as merchant_bank_name,
                asset.bank_account as merchant_bank_account,
                -- case asset.account_type when 1 then '对私' when 2 then '对公' end as merchant_account_type,
                case asset.account_type when length(bank_account_name)<=12 then '对私' when length(bank_account_name)>12 then '对公' end as merchant_account_type,
                rate_money.had_credence as had_credence, /*是否有打款凭证*/
                rate_money.had_invoice as had_invoice,/*是否有返点发票*/
                case when busi.voucher  is not null then '有'  else '无' end as had_voucher, /*代收款证明*/
                opc.pay_type 
FROM `api_orders` `o`
    LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
    LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
    LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
    LEFT JOIN v_inn_org_for_company viofc on m.uid=viofc.uid
    LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
    LEFT JOIN `api_restaurants` `rants` ON `o`.`restaurant_id` = `rants`.`id`
    LEFT JOIN  `api_businesses` busi on busi.id=rants.business_id and busi.deleted_at is null
    LEFT JOIN `api_lbs_infos` ali ON `rants`.lbs_id=ali.id
    LEFT JOIN `api_sales_rest` `sal` ON `sal`.`restaurant_id` = `rants`.`id` and sal.deleted_at is NULL
    LEFT JOIN `api_merchants` `mer` ON `rants`.`merchant_id` = `mer`.`id`
    LEFT JOIN `api_waimai_order_detail` `detail` ON `detail`.`order_id` = `o`.`id`
    LEFT JOIN `v_inn_order_restaurant_waimai` `restmin` ON `o`.`restaurant_id` = `restmin`.`resid` 
    LEFT JOIN `api_assets` `asset` ON `asset`.`id` = `rants`.`asset_id`
    LEFT JOIN `v_inn_order_rating` oraa on oraa.order_num = o.order_num
    LEFT JOIN `16860_order_rating` `ora` ON oraa.id=ora.id
    LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`
    LEFT JOIN `16860_region` `reg_area` ON `reg_area`.`id` =ali.area_id
    LEFT JOIN v_inn_order_pay_card opc ON   o.order_num = opc.order_num
    LEFT JOIN v_inn_waimai_receive_time viwrt  on `o`.id=viwrt.record_id)
UNION ALL
   (
      SELECT
            `o`.`order_num` AS `orderNum`,
            `o`.`client_id` AS `company`,
            `m`.`realname` AS `realname`,
            `m`.`job_num` AS `jobNum`,
            `u`.`email` AS `email`,
            `u`.`mobile` AS `mobile`,
            `viofc`.`branch` AS `branch`,
	        `viofc`.`bus` AS `businessunit`,
	        `viofc`.`sector` AS `sector`,
	        `viofc`.`region` AS `region`,
	        `viofc`.`productgroup` AS `productgroup`,
	        viofc.cost_center as costcenter,
            `o`.`restaurant_name` AS `restaurantName`,
            NULL AS `restaurantId`,
            NULL AS `rest_invoice_title`,
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
			`o`.restaurant_address AS restaurantAddress,
			NULL AS isHall,
			NULL AS userComment,
			NULL as  cardNumber,
			o.area_id as rest_area_id,
			o.area as rest_area_name,
			NULL as merchant_bank_name,
            NULL as merchant_bank_account,
            NULL as merchant_account_type,
            rate_money.had_credence as had_credence,
            rate_money.had_invoice as had_invoice,
            null as had_voucher, /*代收款证明*/
            null as pay_type 
   FROM
        `16860_offstaff_order` `o`
        LEFT JOIN v_inn_order_rate setment ON setment.order_num = o.order_num
        LEFT JOIN v_inn_order_rate_money rate_money ON rate_money.order_num= o.order_num
        LEFT JOIN `16860_member` `m` ON `o`.`user_id` = `m`.`uid`
        LEFT JOIN v_inn_org_for_company viofc on m.uid=viofc.uid
        LEFT JOIN `16860_ucenter` `u` ON `u`.`id` = `m`.`uid`
        LEFT JOIN `v_inn_order_restaurant_offstaff` `restmin` ON `o`.`restaurant_name` = `restmin`.`resid`
        LEFT JOIN `16860_region` `reg` ON `reg`.`id` = `o`.`city_id`
   );
DROP  TABLE  IF EXISTS tmp_order_query  ;
CREATE TABLE IF  NOT EXISTS tmp_order_query   AS(SELECT * FROM v_inn_order_query);