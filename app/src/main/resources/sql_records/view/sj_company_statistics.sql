drop table if exists sj_company_restaurants_statistics;
drop table if exists sj_company_restaurant_sources_map;
drop table if exists sj_company_restaurant_source;
drop table if exists sj_company_hospital_restaurant_statistics;
drop table if exists sj_company_shelf_restaurant;

CREATE TABLE `sj_company_restaurants_statistics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `time` varchar(8) NOT NULL DEFAULT '' COMMENT '时间',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `region_id` int(11) DEFAULT NULL COMMENT '区域id',
  `region_name` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `restaurants_num` int(11) NOT NULL DEFAULT '0' COMMENT '餐厅数量',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司餐厅统计表';

CREATE TABLE `sj_company_restaurant_sources_map` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `sources_code` int(11) DEFAULT NULL COMMENT '餐厅来源码',
  `sources` varchar(255) DEFAULT NULL COMMENT '来源',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司-本公司餐厅来源映射表';

insert into `sj_company_restaurant_sources_map` (`id`, `company_id`, `company_name`, `sources_code`, `sources`, `created_at`, `updated_at`) values 
(1, '1', '辉瑞投资有限公司', 1, '辉瑞官方推荐', now(), now()),
(2, '1', '辉瑞投资有限公司', 2001, '辉瑞APP推荐', now(), now()),
(3, '2', '全程电子商务（上海）有限公司', 1005, '自行扩展', now(), now()),
(4, '3', '上海奥维思市场营销服务有限公司', null, null, now(), now()),
(5, '4', '阿斯利康制药有限公司', 4, '阿斯利康官方推荐', now(), now()),
(6, '4', '阿斯利康制药有限公司', 2004, '阿斯利康APP推荐', now(), now()),
(7, '5', '惠氏制药有限公司',5, '惠氏官方推荐', now(), now()),
(8, '5', '惠氏制药有限公司',2005, '惠氏APP推荐', now(), now()),
(9, '6', '葛兰素史克', null, null, now(), now()),
(10, '9', '强生', 2009, '强生官方推荐', now(), now()),
(11, '9', '强生', 2009, '强生APP推荐', now(), now()),
(12, '24', '上海罗氏制药有限公司', 24, '罗氏官方推荐', now(), now()),
(13, '24', '上海罗氏制药有限公司', 2024, '罗氏APP推荐', now(), now()),
(14, '39', '费控', null, null, now(), now()),
(15, '42', '优时比制药公司', 42, '优时比官方推荐', now(), now()),
(16, '42', '优时比制药公司', 2042, '优时比APP推荐', now(), now()),
(17, '43', '艾尔建制药公司', 43, '艾尔建官方推荐', now(), now()),
(18, '43', '艾尔建制药公司', 2043, '艾尔建APP推荐', now(), now()),
(19, '124', '默沙东(中国)有限公司', 124, '默沙东官方推荐', now(), now()),
(20, '124', '默沙东(中国)有限公司', 2124, '默沙东APP推荐', now(), now()),
(21, '125', '雅培贸易（上海）有限公司', 125, '雅培官方推荐', now(), now()),
(22, '125', '雅培贸易（上海）有限公司', 2125, '雅培APP推荐', now(), now()),
(23, '126', '住友制药', 126, '住友官方推荐', now(), now()),
(24, '126', '住友制药', 2126, '住友APP推荐', now(), now()),
(25, '127', '宴通卡', null, null, now(), now()),
(26, '128', '拜耳', 128, '拜耳官方推荐', now(), now()),
(27, '128', '拜耳', 2128, '拜耳APP推荐', now(), now()),
(28, '129', '礼来国际贸易（上海）有限公司', 129, '礼来官方推荐', now(), now()),
(29, '129', '礼来国际贸易（上海）有限公司', 2129, '礼来APP推荐', now(), now()),
(30, '129', '礼来国际贸易（上海）有限公司', 3129, '礼来零售基层推荐', now(), now()),
(31, '130', '亿腾医药中国有限公司', null, null, now(), now()),
(32, '2', '全程电子商务（上海）有限公司', 1000, '历史数据', now(), now()),
(33, '2', '全程电子商务（上海）有限公司', 1010, '渠道KA餐厅', now(), now()),
(34, '2', '全程电子商务（上海）有限公司', 1030, '云纵拓展', now(), now());

CREATE TABLE `sj_company_restaurant_source` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `time` varchar(8) NOT NULL DEFAULT '' COMMENT '时间',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `source_type` varchar(100) DEFAULT NULL COMMENT '餐厅推荐来源类型',
  `source_num` int(11) NOT NULL DEFAULT '0' COMMENT '推荐数量',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司餐厅来源统计表';

CREATE TABLE `sj_company_shelf_restaurant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `time` varchar(8) NOT NULL DEFAULT '' COMMENT '时间',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `reason` varchar(500) DEFAULT NULL COMMENT '餐厅下架原因',
  `shelf_num` int(11) NOT NULL DEFAULT '0' COMMENT '下架数量',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司餐厅下架原因统计表';

CREATE TABLE `sj_company_hospital_restaurant_statistics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `time` varchar(8) NOT NULL DEFAULT '' COMMENT '时间',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `restaurant_type` int(2) NOT NULL DEFAULT '0' COMMENT '餐厅类型（0：未知类型1：订餐餐厅2：外卖餐厅）',
  `distance` int(11) NOT NULL DEFAULT '0' COMMENT '医院周边距离（单位:米）',
  `restaurant_num_type` varchar(500) DEFAULT NULL COMMENT '覆盖餐厅数类型',
  `hospital_num` int(11) NOT NULL DEFAULT '0' COMMENT '医院数量',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司医院覆盖餐厅数量统计表';

