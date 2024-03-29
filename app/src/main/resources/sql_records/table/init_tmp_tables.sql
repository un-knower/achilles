-- 餐厅
drop table if exists tmp_restaurant_query;
CREATE TABLE `tmp_restaurant_query` (
  `id` varchar(36)  DEFAULT '',
  `ghid` int(10) unsigned  DEFAULT '0' COMMENT 'ID',
  `olid` int(10) unsigned DEFAULT NULL COMMENT '餐厅id',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公海餐厅名',
  `rest_invoice_title` varchar(120) DEFAULT NULL COMMENT '餐厅发票抬头',
  `city` int(10) DEFAULT NULL COMMENT '城市id',
  `city_display` varchar(120) DEFAULT NULL COMMENT '城市',
  `area` int(10) DEFAULT NULL COMMENT '区域id',
  `area_display` varchar(120) DEFAULT NULL COMMENT '区域',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '餐厅地址',
  `rest_merchant` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户名称',
  `rest_merchant_id` int(10) unsigned DEFAULT NULL COMMENT '商户id',
  `rest_status` tinyint(4) DEFAULT NULL COMMENT '餐厅状态',
  `gonghai_status` tinyint(4) DEFAULT NULL COMMENT '公海状态',
  `restaurant_sources` text COMMENT '餐厅来源',
  `gh_task_name` varchar(64)  COMMENT '公海任务名',
  `recommends_emails` text COMMENT '推荐邮箱',
  `recommends_company` text COMMENT '推荐企业',
  `priority` tinyint(4)  COMMENT '优先级',
  `prj_names` text COMMENT '项目名',
  `in_store_at` datetime  DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `online_time` datetime DEFAULT NULL COMMENT '上线时间',
  `reason` text CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT '下线原因',
  `shelf_time` timestamp NULL DEFAULT NULL COMMENT '下线时间',
  `consume` decimal(8,2) DEFAULT NULL COMMENT '人均',
  `avg_actual` decimal(55,2) DEFAULT NULL COMMENT '实际人均',
  `consume_deviation` decimal(56,2) DEFAULT NULL COMMENT '餐厅人均',
  `category_name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci COMMENT '外卖种类',
  `cook_style` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜系',
  `manage_type` tinyint(4) DEFAULT NULL COMMENT '结算类型',
  `count_train` bigint(21) DEFAULT NULL COMMENT '培训次数',
  `count_visit` bigint(21) DEFAULT NULL COMMENT '拜访次数',
  `count_money` decimal(54,2) DEFAULT NULL COMMENT '订单总额',
  `count_order` decimal(42,0) DEFAULT NULL COMMENT '订单总数',
  `err_times` bigint(21) DEFAULT NULL COMMENT '飞检异常次数',
  `user_concentration` decimal(57,2) DEFAULT NULL COMMENT '用户集中度',
  `sales_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '销售姓名',
  `support_takeout_of_food` varchar(1)  DEFAULT '' COMMENT '支持外卖',
  `support_reserve` varchar(1)  DEFAULT '' COMMENT '支持预定',
  `own_companys_id` binary(0) DEFAULT NULL COMMENT '所属企业id',
  `own_companys` binary(0) DEFAULT NULL COMMENT '所属企业',
  `rebate` text COMMENT '返点率',
  `box_num` tinyint(4) DEFAULT NULL COMMENT '包厢数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 重点关注餐厅
CREATE TABLE `tmp_check_dining` (
  `id` varchar(36) DEFAULT NULL,
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `company` varchar(30) DEFAULT NULL COMMENT '客户名称',
  `target_val` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  COMMENT '检查对象',
  `check_item` text COMMENT '检查内容',
  `restaurant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '餐厅名',
  `online_time` datetime DEFAULT NULL COMMENT '在线时间',
  `create_time` varchar(24) DEFAULT NULL COMMENT '下单时间',
  `yuyue_time` varchar(24) DEFAULT NULL  COMMENT '预约时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '餐厅状态',
  `check_date` varchar(24) DEFAULT NULL COMMENT '检查时间',
  `created_at` timestamp  DEFAULT '0000-00-00 00:00:00' COMMENT '关注起始',
  `expire` timestamp  DEFAULT '0000-00-00 00:00:00' COMMENT '关注结束',
  `fei_case` varchar(255) DEFAULT NULL COMMENT '生成case的相关来源',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  COMMENT '规则类型',
  `file_number` varchar(5) DEFAULT NULL COMMENT '存档次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 重点关注人员
CREATE TABLE `tmp_check_people` (
  `id` varchar(36) DEFAULT NULL,
  `company` varchar(30) DEFAULT NULL COMMENT '客户名称',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `email` char(32) DEFAULT NULL COMMENT '用户邮箱',
  `job_num` char(20) DEFAULT NULL COMMENT '工号',
  `target_val` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci  COMMENT '检查对象',
  `yuyue_time` varchar(24) DEFAULT NULL COMMENT '预约时间',
  `create_time` varchar(24) DEFAULT NULL COMMENT '下单时间',
  `check_item` text COMMENT '检查内容',
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `created_at` timestamp  DEFAULT '0000-00-00 00:00:00' COMMENT '关注起始',
  `expire` timestamp  DEFAULT '0000-00-00 00:00:00' COMMENT '关注结束',
  `fei_case` varchar(255) DEFAULT NULL COMMENT '生成case的相关来源',
  `check_date` int(11) DEFAULT NULL COMMENT '检查日期',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规则类型，对规则的人化描述',
  `branch` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `businessunit` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `sector` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `region` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `productgroup` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `costcenter` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `file_number` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_client_query` (
  `id` varchar(255) ,
  `branch` varchar(255) DEFAULT NULL,
  `bus` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `cost_center` varchar(255) DEFAULT NULL,
  `count_money` varchar(255) DEFAULT NULL,
  `count_order` varchar(255) DEFAULT NULL,
  `count_rest` varchar(255) DEFAULT NULL,
  `count_rest_had_cost` varchar(255) DEFAULT NULL,
  `count_rest_recom` varchar(255) DEFAULT NULL,
  `count_user` varchar(255) DEFAULT NULL,
  `productgroup` varchar(255) DEFAULT NULL,
  `rate_rest_recommender_use` varchar(255) DEFAULT NULL,
  `rate_rest_use` varchar(255) DEFAULT NULL,
  `rate_user_order` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `count_rest_recom_ol` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_flycheck_record` (
  `id` varchar(36)  DEFAULT '',
  `check_date` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `shangpu_name` varchar(50) DEFAULT NULL COMMENT '商铺（餐厅）',
  `shangpu_address` varchar(255) DEFAULT NULL,
  `check_mode` varchar(32) DEFAULT NULL COMMENT '检查方式（事中，事后）',
  `check_type` varchar(50) DEFAULT NULL COMMENT '检查类型（电话，到店）',
  `order_num` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '订单号',
  `check_reason` varchar(255) DEFAULT NULL COMMENT '抽查原因（0昨日例行，1今日例行，10>n>30关注对象规则[即预警]）',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(-1删除，0新增，1任务已指派 , 2进入订单列表，5合规，9,删除, 10异常，15申诉中，20未用餐 25疑似异常)',
  `order_money` decimal(10,2) DEFAULT NULL COMMENT '订单消费金额（仅参考）',
  `company` varchar(30) DEFAULT NULL COMMENT '客户名称',
  `check_item` text COMMENT '检查内容（多条）',
  `job_num` char(20) DEFAULT NULL COMMENT '工号',
  `realname` varchar(64) DEFAULT NULL COMMENT '姓名',
  `order_predict_cost` varchar(64) DEFAULT NULL COMMENT '预计消费金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_inn_client_rest_order_statistic` (
  `client_id` int(10)  COMMENT '客户ID',
  `city_id` int(11)  DEFAULT '0' COMMENT '所在城市ID',
  `branch` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `bus` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `sector` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `productgroup` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `cost_center` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `region` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `count_user` bigint(21)  DEFAULT '0',
  `count_user_cost` bigint(21)  DEFAULT '0',
  `count_money` decimal(30,2) DEFAULT NULL,
  `count_order` bigint(21)  DEFAULT '0',
  `count_rest_had_cost` bigint(21)  DEFAULT '0',
  `count_rest_had_cost_recom` bigint(21)  DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_inn_train_log` (
  `id` varchar(36) DEFAULT NULL,
  `restaurant_id` int(11) ,
  `rest_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '餐厅名',
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(128) DEFAULT NULL COMMENT '餐厅地址',
  `restuarant_header` varchar(16) DEFAULT NULL COMMENT '负责人',
  `chief_phone` char(11) DEFAULT NULL COMMENT '负责人手机',
  `train_date` datetime DEFAULT NULL COMMENT '培训日期',
  `train_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规则类型',
  `feedback` text COMMENT '培训记录',
  `train_num` smallint(6) DEFAULT NULL COMMENT '培训人数',
  `train_type` tinyint(4) DEFAULT NULL COMMENT '培训方式 1为到店培训 2为电话培训',
  `tt_type` tinyint(4) DEFAULT NULL COMMENT '台贴信息 1未发放 2已发放，但餐厅未贴 3已发放，餐厅已贴',
  `tc_type` tinyint(4) DEFAULT NULL COMMENT '台卡信息 1未发放 2已发放，但餐厅未使用 3已发放，餐厅已使用',
  `bq_type` tinyint(4) DEFAULT NULL COMMENT '便签信息 1未发放 2已发放',
  `cp_type` tinyint(4) DEFAULT NULL COMMENT '优惠同享 1无优惠 2有优惠但商宴通不可享受 3有优惠且商宴通可以享受',
  `count_train` bigint(21) DEFAULT NULL,
  `sales_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '销售姓名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_inn_visit_log` (
  `id` varchar(36) NOT NULL,
  `restaurant_id` int(11)  COMMENT '关联公海餐厅',
  `restaurant_name` varchar(64) DEFAULT NULL COMMENT '餐厅名称',
  `type` text,
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `visit_person` varchar(16) DEFAULT NULL COMMENT '访问人',
  `visit_person_phone` varchar(11) DEFAULT NULL COMMENT '访问人手机号',
  `status` varchar(4) DEFAULT NULL COMMENT '0:上线，1:禁用，2:审核，20:未分配，21:拓展中,历史数据(拒绝合作),22:拓展中,历史数据(不符合商务宴请),23:拓展中,历史数据(不合规),24:拓展中,历史数据(歇业、转店、装修),25:初步洽谈,26:深入洽谈,27:合同协商,28:完成签约,40:无法签约(娱乐设施),41:无法签约(人均超标),42:无法签约(发票不规范),43:无法签约(运营资质不符),44:无法签约(门店装修),45:无法签约(歇业倒闭),46:无法签约(不与第三方合作),47:无法签约(财务不接受),48:无法签约(分店无法做主)',
  `visit_content` varchar(128) COMMENT '拜访内容',
  `prj_names` text,
  `sale_name` varchar(32)  COMMENT '销售姓名',
  `start_time` datetime  COMMENT '开始时间',
  `uid` int(11)  COMMENT 'USER ID，旧系统的登录，权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_member` (
  `id` int(10) unsigned  COMMENT '用户ID',
  `cid` int(10)  COMMENT '客户ID',
  `realname` varchar(64)  DEFAULT '' COMMENT '姓名',
  `job_num` char(20)  DEFAULT '' COMMENT '工号',
  `username` char(32) DEFAULT NULL COMMENT '用户名',
  `mobile` char(15) DEFAULT NULL COMMENT '用户手机',
  `email` char(64) DEFAULT NULL COMMENT '用户邮箱',
  `client_name` varchar(30) DEFAULT NULL COMMENT '客户标识',
  `client_title` varchar(30) DEFAULT NULL COMMENT '客户名称',
  `superior_email` char(64)  DEFAULT '' COMMENT '上级邮箱',
  `superior_name` varchar(64)  DEFAULT '' COMMENT '上级姓名',
  `city` char(20)  DEFAULT '' COMMENT '所在城市',
  `city_id` int(11)  DEFAULT '0' COMMENT '所在城市ID',
  `fromtype` tinyint(1)  DEFAULT '1' COMMENT '用户来源 1：系统 导入2：注册 3：后台添加',
  `login_num` int(10)  DEFAULT '0' COMMENT '登录次数',
  `status` tinyint(4)  DEFAULT '0' COMMENT '用户状态 0禁用 1正常 2未审核 3 待认证 -1删除',
  `autologin` varchar(50)  DEFAULT '' COMMENT '自动登录，存储在cookie的加密码',
  `pid` varchar(20) DEFAULT NULL,
  `level` int(11) DEFAULT '0' COMMENT '用户等级,如主管、职员',
  `created_at` datetime  DEFAULT '0000-00-00 00:00:00',
  `updated_at` datetime  DEFAULT '0000-00-00 00:00:00',
  `deleted_at` datetime DEFAULT NULL,
  `mistype_num` int(11) DEFAULT '0' COMMENT '密码输入错误次数',
  `failure_time` int(11) DEFAULT NULL COMMENT '登录失效时间',
  `activation_time` datetime  DEFAULT '0000-00-00 00:00:00' COMMENT '激活时间',
  `invoice_title` varchar(30) DEFAULT '' COMMENT '发票抬头',
  `reason` char(40) DEFAULT NULL COMMENT '禁用原因',
  `branch` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `businessunit` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `sector` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `region` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `productgroup` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `costcenter` varchar(64) DEFAULT NULL COMMENT '架构名称',
  `reg_time` int(10) unsigned DEFAULT '0' COMMENT '注册时间',
  `max_in_3_month` decimal(30,2) DEFAULT NULL,
  `total_in_3_month` decimal(52,2) DEFAULT NULL,
  `reg_days` int(7) DEFAULT NULL,
  `order_count` bigint(21)  DEFAULT '0',
  `order_amount` decimal(30,2) DEFAULT NULL,
  `per_mean_diff` decimal(19,10) DEFAULT NULL,
  `pre_yuyue_index` decimal(15,4) DEFAULT NULL,
  `case_times` bigint(21)  DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_order_query` (
  `orderNum` varchar(32)  DEFAULT '',
  `company` int(11)  DEFAULT '0',
  `realname` varchar(64) DEFAULT NULL,
  `jobNum` char(20) DEFAULT NULL,
  `email` char(64) DEFAULT NULL,
  `mobile` char(15) DEFAULT NULL,
  `branch` varchar(64) DEFAULT NULL,
  `businessunit` varchar(64) DEFAULT NULL,
  `sector` varchar(64) DEFAULT NULL,
  `region` varchar(64) DEFAULT NULL,
  `productgroup` varchar(64) DEFAULT NULL,
  `costcenter` varchar(64) DEFAULT NULL,
  `restaurantName` varchar(255) DEFAULT NULL,
  `restaurantId` int(11) unsigned DEFAULT NULL,
  `rest_invoice_title` varchar(145) DEFAULT NULL COMMENT '餐厅厅发票抬头' ,
  `merchantName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `salesId` int(11) DEFAULT NULL,
  `cityName` varchar(120) DEFAULT NULL,
  `serviceType` varchar(6) CHARACTER SET utf8mb4  DEFAULT '',
  `orderType` varchar(2) CHARACTER SET utf8mb4  DEFAULT '',
  `payType` varchar(4) CHARACTER SET utf8mb4  DEFAULT '',
  `reportReason` varchar(255) DEFAULT NULL,
  `isRoom` tinyint(4) DEFAULT NULL,
  `createTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `receiveTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `yuyueTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `isDelivery` tinyint(4) DEFAULT NULL,
  `confirmTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `payTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `reportTime` varchar(24) CHARACTER SET utf8mb4 DEFAULT NULL,
  `status` varchar(13) CHARACTER SET utf8mb4  DEFAULT '',
  `peopleNum` int(11) DEFAULT NULL,
  `predictCost` decimal(10,2) DEFAULT NULL,
  `actualPeople` int(11) DEFAULT NULL,
  `money` decimal(10,2) DEFAULT NULL,
  `averageMoney` varchar(54) CHARACTER SET utf8mb4 DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `isRestaurantFirst` bigint(20)  DEFAULT '0',
  `manageType` varchar(2) CHARACTER SET utf8mb4 DEFAULT NULL,
  `isScore` bigint(20) DEFAULT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `orderRateCreateTime` bigint(20) DEFAULT NULL,
  `dishScore` bigint(4) DEFAULT NULL,
  `deliveryTimeScore` bigint(20) DEFAULT NULL,
  `restaurantScore` bigint(4) DEFAULT NULL,
  `packagingScore` bigint(20) DEFAULT NULL,
  `sytServiceScore` bigint(20) DEFAULT NULL,
  `reason` mediumtext,
  `replyTime` bigint(20) DEFAULT NULL,
  `replyContent` mediumtext,
  `rates` mediumtext,
  `rateMoney` decimal(32,2) DEFAULT NULL,
  `approvalCode` varchar(255) DEFAULT NULL,
  `restaurantAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `isHall` tinyint(4) DEFAULT NULL,
  `userComment` mediumtext,
  `cardNumber` mediumtext,
  `rest_area_id` varchar(45) DEFAULT NULL COMMENT '餐厅所在区域id',
  `rest_area_name` varchar(45) DEFAULT NULL COMMENT '餐厅所在区域',
  `merchant_bank_name` varchar(75) DEFAULT NULL COMMENT '商家开户银行',
  `merchant_bank_account` varchar(75) DEFAULT NULL COMMENT '商家银行帐号',
  `merchant_account_type` varchar(45) DEFAULT NULL COMMENT '对公/对私',
  `had_credence` varchar(45) DEFAULT NULL COMMENT '打款凭证',
  `had_invoice` varchar(45) DEFAULT NULL COMMENT '返点发票',
  `had_voucher` varchar(45) DEFAULT NULL COMMENT '代收款证明',
  `pay_type`  varchar(45) DEFAULT NULL COMMENT '支付渠道'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tmp_request_payment` (
  `id` varchar(36)  DEFAULT '',
  `order_num` varchar(32) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `city_name` varchar(120) DEFAULT NULL COMMENT '区域名称',
  `mobile` char(15) DEFAULT NULL COMMENT '用户手机',
  `realname` varchar(64) DEFAULT NULL COMMENT '姓名',
  `job_num` char(20) DEFAULT NULL COMMENT '工号',
  `order_state` varchar(13) DEFAULT NULL,
  `restaurant_name` varchar(255) DEFAULT NULL,
  `yuyue_time` varchar(24) DEFAULT NULL,
  `actual_people` int(11) DEFAULT NULL,
  `money` decimal(8,2) DEFAULT NULL,
  `status` tinyint(2)  DEFAULT '0' COMMENT '状态',
  `kefu` char(16)  DEFAULT '' COMMENT '用户名',
  `created_at` datetime DEFAULT NULL,
  `remark` varchar(1000)  DEFAULT '' COMMENT '日志备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
