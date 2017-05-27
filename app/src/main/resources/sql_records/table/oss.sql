CREATE TABLE `inn_oss_files_mapping` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '',
  `down_model` varchar(20) DEFAULT NULL COMMENT '下载模块',
  `oss_url` varchar(500) DEFAULT NULL COMMENT 'oss生成的下载URL',
  `error_msg` varchar(500) DEFAULT NULL COMMENT '写入文件或者上传OSS时异常信息',
  `oss_status` char(1) DEFAULT NULL COMMENT '0正常 1失败' ,
  `created_at` varchar(20) DEFAULT NULL ,
  `updated_at` varchar(20) DEFAULT NULL,
  `deleted_at` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

