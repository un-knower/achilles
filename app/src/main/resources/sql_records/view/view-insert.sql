
DROP  TABLE  IF EXISTS `inn_cache_log`  ;
CREATE TABLE IF NOT EXISTS `inn_cache_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cache_type` varchar(50) DEFAULT NULL,
  `refresh_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `inn_cache_log` VALUES ('1', 'order', now());
INSERT INTO `inn_cache_log` VALUES ('2', 'restaurant', now());
INSERT INTO `inn_cache_log` VALUES ('3', 'flycheck', now());
INSERT INTO `inn_cache_log` VALUES ('4', 'customerservice', now());
INSERT INTO `inn_cache_log` VALUES ('5', 'member', now());
INSERT INTO `inn_cache_log` VALUES ('6', 'fkclient', now());
INSERT INTO `inn_cache_log` VALUES ('7', 'trainlog', now());
INSERT INTO `inn_cache_log` VALUES ('8', 'visitlog', now());

DROP  TABLE  IF EXISTS `tmp_restaurant_sources`  ;
CREATE TABLE IF NOT EXISTS  `tmp_restaurant_sources` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `code` int(8) DEFAULT NULL,
  `code_view` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_INN_REST_SOURCES` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (1, 24, '罗氏官方推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (2, 1000, '历史数据', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (3, 2001, '辉瑞APP推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (4, 1010, '渠道KA餐厅', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (5, 2043, '艾尔建APP推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (6, 1030, '云纵拓展', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (7, 2042, '优时比APP推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (8, 2009, '强生APP推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (9, 1, '辉瑞官方推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (10, 124, '默沙东官方推荐', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (11, 1005, '自行拓展', '2016-12-13 20:39:12', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (12, 4, '阿斯利康官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (13, 125, '雅培官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (14, 2005, '惠氏APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (15, 2126, '住友APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (16, 5, '惠氏官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (17, 126, '住友官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (18, 2004, '阿斯利康APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (19, 2125, '雅培APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (20, 2124, '默沙东APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (21, 2024, '罗氏APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (22, 9, '强生官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (23, 42, '优时比官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (24, 43, '艾尔建官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (25, 129, '礼来官方推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (26, 2129, '礼来APP推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);
INSERT INTO `tmp_restaurant_sources` (`id`, `code`, `code_view`, `created_at`, `updated_at`, `deleted_at`) VALUES (27, 3129, '礼来零售基层推荐', '2016-12-13 20:39:13', '2016-12-13 20:39:59', NULL);

