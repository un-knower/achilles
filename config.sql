CREATE TABLE `doris_table_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chart_name` varchar(200) DEFAULT NULL COMMENT '图表名称',
  `chart_type` varchar(200) DEFAULT NULL COMMENT '图表类型',
  `chart_title` varchar(200) DEFAULT NULL COMMENT '图表标题',
  `chart_sql` text COMMENT '定义sql',
  `remark` text COMMENT '备注',
  `latitude_cols` text COMMENT '纬度字段，逗号分隔',
  `valus_cols` text COMMENT '值字段，逗号分隔',
  `show_cols` text COMMENT '展示字段,json格式（eg:[{"col":"id","name":"id","type":"int"}]）',
  `data_source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='自定义图表';

CREATE TABLE `doris_table_param` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chart_info_id` int(11) DEFAULT NULL COMMENT '配置表id',
  `param_key` varchar(200) DEFAULT NULL COMMENT '参数key',
  `param_name` text COMMENT '参数名称',
  `data_type` varchar(200) DEFAULT NULL COMMENT '数据类型',
  `control_type` varchar(200) DEFAULT NULL COMMENT '控件类型（时间，选择，单选，隐藏）（下拉需要与数据字典关联）',
  `data_item_id` varchar(200) DEFAULT NULL COMMENT '选择控件数据源(字典id)',
  `order_sort` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8 COMMENT='自定义图表参数信息';

CREATE TABLE `dp_data_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_key` varchar(45) DEFAULT NULL COMMENT '字典key',
  `item_mark` varchar(45) DEFAULT NULL COMMENT '字典描述',
  `server_group` varchar(45) DEFAULT NULL COMMENT '提供字典的应用',
  `item_status` varchar(45) DEFAULT NULL COMMENT 'y启用 n禁用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `item_type` varchar(10) DEFAULT NULL COMMENT '字典类型参考dp_data_item.item_key=DATA_ITEM_TYPE',
  `item_content` varchar(500) DEFAULT NULL COMMENT '视图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='数据资源字典';

CREATE TABLE `dp_data_item_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT 'dp_data_item.id',
  `detail_key` varchar(45) DEFAULT NULL COMMENT '对应枚举key',
  `detail_text` varchar(45) DEFAULT NULL COMMENT 'key描述',
  `detail_status` varchar(10) DEFAULT NULL COMMENT '状态参照dp_data_item.item_key=FLAG_AVAILABILITY',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;

CREATE TABLE `doris_table_columns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) DEFAULT NULL,
  `col_name` varchar(245) DEFAULT NULL,
  `show_name` varchar(45) DEFAULT NULL,
  `data_type` varchar(45) DEFAULT NULL,
  `col_excel` varchar(45) DEFAULT NULL,
  `extra` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1392 DEFAULT CHARSET=utf8;


CREATE TABLE `achilles_diy_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `data_base` varchar(255) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `template_config_id` bigint(20) DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

CREATE TABLE `achilles_diy_template_columns` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `col_titile` varchar(255) DEFAULT NULL,
  `table_col` varchar(255) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11054 DEFAULT CHARSET=utf8;
INSERT INTO `doris_table_info` (`id`,`chart_name`,`chart_type`,`chart_title`,`chart_sql`,`remark`,`latitude_cols`,`valus_cols`,`show_cols`,`data_source`) VALUES (56,'订单','list','测试','from out_order_detail where 1=1\n<if> and city_id in (${cityList})</if>\n<if> and salesId in (${salesList})</if>\n<if> and company in (${clientList})</if>\n<if> and status in (#{orderState})</if>\n<if> and ${orderTimeType}  between #{begin} and DATE_ADD(#{end},INTERVAL 1 day)</if>\n<if> and orderType in (#{orderType})</if>\n<if> and (orderNum like \'${likeKey}%\' or mobile like \'${likeKey}%\'  or restaurantName like \'${likeKey}%\' or merchantName like \'${likeKey}%\')</if>','out_order_detail','orderNum',NULL,NULL,NULL),
 (57,'餐厅','list','测试','from tmp_restaurant_query  aa where 1=1 \n<if> and city in (#{cityIds}) </if>\n<if> and (Store_name like \'${likeKey}%\' or gh_task_name like \'${likeKey}%\'  or recommends_emails like \'${likeKey}%\' or prj_names like \'${likeKey}%\' ) </if>\n<if> and exists (select 1  from quancheng_db.16860_restaurant_client rc where rc.restaurant_id=aa.olid and rc.deleted_at is null and rc.client_id=#{clientId}) </if>\n<if> and rest_status in (#{restStatus}) </if>\n<if> and support_takeout_of_food in (#{supportTakeoutOfFood}) </if>\n<if> and support_reserve in (#{supportReserve}) </if>\n<if> and sale_id in (#{salesId}) </if>\n<if> and ${restTimeType}  between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if>','tmp_restaurant_query','id',NULL,NULL,NULL),
 (58,'特许商户','list',NULL,'from out_approve_time_detail where  1=1 <if> and city_id in (#{cityIds}) </if>  <if> and ${timeType} between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if>  <if> and (rest_name like \'%${likeKey}%\' or  user_name like \'%${likeKey}%\'  or gould_id like  \'%${likeKey}%\'  ) </if> <if> and company_id =#{companyId}  </if>  ','out_approve_time_detail','id',NULL,NULL,NULL),
 (59,'app报错','list',NULL,'from out_app_restaurant_reported_wrong \nwhere 1=1\n<if> and (store_name like  \'%${likeKey}%\'  or error_man_name  like  \'%${likeKey}%\'   or mobile  like  \'%${likeKey}%’) </if>\n<if> and city_id in (${cityIds}) </if>\n<if> and enterprise_name_num in (#{clientShortNum}) </if>\n<if> and ${timeType} between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if> ','out_app_restaurant_reported_wrong','id',NULL,NULL,NULL),
 (60,'礼来白名单','list',NULL,'from out_white_list_restaurant where 1=1\n<if> and (restaurant_name like  \'%${likeKey}%\'  or job_num  like  \'%${likeKey}%\'   or applicant_name  like  \'%${likeKey}%\' )</if>\n<if> and city in (#{citys}) </if>\n<if> and approve_method in (#{approveMethod}) </if>\n<if> and ${timeType} between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if> ','out_white_list_restaurant','id',NULL,NULL,NULL),
 (61,'餐厅拜访记录','list','aaa','from out_restaurant_master_visit where 1=1\n<if> and (restaurant_master_name like  \'%${likeKey}%\'   ) </if>\n<if> and city_id in (${cityIds}) </if>\n<if> and restaurant_master_status in (#{restStatus}) </if>\n<if> and sale_id in (#{saleId}) </if>\n<if> and visit_time between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if> ','out_restaurant_master_visit','id',NULL,NULL,NULL),
 (62,'餐厅培训记录','list',NULL,'from tmp_inn_train_log where 1=1\n<if> and (rest_name like  \'%${likeKey}%\'   ) </if>\n<if> and city_id in (${cityIds}) </if>\n<if> and sale_id in (#{saleId}) </if>\n<if> and train_date between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if> ','tmp_inn_train_log','id',NULL,NULL,NULL),
 (63,'推荐餐厅','list',NULL,'from out_restaurant_company_user_recom where 1=1 <if> and (restaurant_name like  \'%${likeKey}%\'   ) </if> <if> and city_id in (${cityIds}) </if> <if> and recommand_method in (#{recommendMethod}) </if> <if> and recommender_time between #{begin} and DATE_ADD(#{end},INTERVAL 1 day) </if> ','out_restaurant_company_user_recom','id',NULL,NULL,NULL),
 (65,'企业信息','list',NULL,'from tmp_client_query where 1=1\n<if> and city_id in (${cityIds}) </if>\n<if> and client_id in (#{clientId}) </if>\n','tmp_client_query','id',NULL,NULL,NULL),
 (66,'通话记录','list',NULL,'from out_user_call_record where 1=1 \n<if> and city in (${city}) </if>  \n<if> and type in (#{callTypes}) </if>  \n<if> and client_id in (${clientId}) </if>  \n<if> and record_time between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if>\n<if> and company in (#{company}) </if>     \n<if> and (issue  like \'%${likeKey}%\') </if>\n<if> and complain in (#{complain}) </if>     ','out_user_call_record','id',NULL,NULL,NULL),
 (67,'催款记录','list',NULL,'from out_kefu_request_pay_record where 1=1 \n<if> and city_id in (${cityids}) </if>   \n<if>  and request_time between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if>    \n<if>  and (realname  like \'%${likeKey}%\' or job_num like \'%${likeKey}%\' or order_num like \'%${likeKey}%\') </if>','out_kefu_request_pay_record','id',NULL,NULL,NULL),
 (68,'用户信息','list',NULL,'from tmp_member where 1=1  <if>  and cid in (${clientId}) </if> <if>  and realname like \'%${likeKey}%\' </if>','tmp_member','id',NULL,NULL,NULL),
 (69,'飞检记录','list',NULL,'from out_fly_check where 1=1 <if>  and company_id in (${clientIds}) </if> <if>  and city_id in (#{cityIds}) </if>     <if>  and status_id in (#{checkStatus})</if>       <if>  and check_mode in (#{checkType})</if>      <if>  and check_date between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if>   <if>  and (shangpu_name  like \'%${likeKey}%\' or order_num  like \'${likeKey}%\' ) </if> <if> and company_id in (#{clientId}) </if> ','out_fly_check','id',NULL,NULL,NULL),
 (70,'重点关注人员','list',NULL,'from out_inn_check_people where 1=1','out_inn_check_people','id',NULL,NULL,NULL),
 (71,'重点关注餐厅','list',NULL,'from out_inn_check_restaurant where 1=1','out_inn_check_restaurant','id',NULL,NULL,NULL),
 (72,'工单响应时间','list',NULL,'from out_kefu_workform_receipt where 1=1  <if>  and gmt_created between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if> ','out_kefu_workform_receipt','id',NULL,NULL,NULL),
 (73,'商宴通统计','list',NULL,'from out_syt_data_statistic where happen_date_pt =date_format(DATE_ADD(now(),INTERVAL -1 day), \'%Y%m%d\')  ','out_syt_data_statistic','id',NULL,NULL,NULL),
 (74,'支付失败记录','list',NULL,'from out_order_payfail where 1=1 <if>  and createtime between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if> <if>and client_id in (#{clientId}) </if>   <if>and ordernum=  #{likeKey}</if>  ','out_order_payfail','id',NULL,NULL,NULL),
 (76,'催单记录','list',NULL,'from out_request_supplier_record orsr where 1=1 <if>  and ${dateType} between #{begin} and  DATE_ADD(#{end},INTERVAL 1 day) </if> <if>  and (order_num  like \'%${likeKey}%\' or kefu_name like \'%${likeKey}%\'  ) </if>','out_request_supplier_record','id',NULL,NULL,NULL),
 (77,'订单操作记录','list',NULL,'from quancheng_db.16860_action_log where 1=1 \n<if> and action_id in (#{actionId}) </if>  \n<if>  and (business_id  like \'%${likeKey}%\' or remark like \'%${likeKey}%\'  ) </if> \n<if>  and create_time between  #{begin} and   DATE_ADD(#{end},INTERVAL 1 day) </if>','quancheng_db.16860_action_log','id',NULL,NULL,NULL),
 (78,'财务对账测试','list',NULL,'from out_financial_reconciliation where 1=1  <if> and (restaurant_name like \'%${likeKey}%\'  or merchant_name like \'%${likeKey}%\' or order_id like \'%${likeKey}%\' ) </if>  ','out_financial_reconciliation','settment_id',NULL,NULL,NULL),
 (79,'医院导入模版','list',NULL,'from statistics_db.dtmp_16860_hospital where 1=1','statistics_db.dtmp_16860_hospital','id',NULL,NULL,NULL),
 (80,'线上医院列表','list',NULL,'from quancheng_db.16860_hospitals aa \nleft join quancheng_db.16860_region rg_p on aa.province=rg_p.id\nleft join quancheng_db.16860_region rg_c on aa.city=rg_c.id\nleft join quancheng_db.16860_region rg_d on aa.area=rg_d.id\nwhere  \naa.status=1\n<if> and exists (select 1  from quancheng_db.16860_hospital_clients rc where rc. hospital_id =aa.id and rc.deleted_at is null and rc.client_id=#{clientId}) </if>\n<if> and aa.name like \'%${likeKey}%\'   </if>','quancheng_db.16860_hospitals','aa.id',NULL,NULL,'readSqlSession'),
 (81,'罗氏覆盖','list',NULL,'from tt_hospital_rest_dis aa where   1=1   <if> and bu = #{likeKey}   </if>','tt_hospital_rest_dis','id',NULL,NULL,NULL),
 (82,'外卖菜单','list',NULL,' FROM\n    quancheng_db.api_waimai_restaurant_detail wres\n        LEFT JOIN\n    quancheng_db.api_restaurants res ON wres.restaurant_id = res.id\n        LEFT JOIN\n    quancheng_db.api_waimai_menu menu ON menu.waimai_restaurant_id = wres.id\n        AND menu.deleted_at IS NULL\n        LEFT JOIN\n    quancheng_db.api_waimai_menu_item item ON item.menu_id = menu.id\n          WHERE\n    res.status = 0\n        AND res.deleted_at IS NULL\n        AND wres.deleted_at IS NULL\n        and menu.id is not null\n        and item.id is not null\n<if> and res.id=#{searchKey}</if>',NULL,NULL,NULL,NULL,'readSqlSession'),
 (83,'财务结算(未验证-实时数据)','list',NULL,'FROM\n    terra.settlement_record sr\n        LEFT JOIN\n    halia.halia_shop_info si ON si.id = sr.shop_id\n        LEFT JOIN\n    terra.terra_order_bill bl ON bl.bill_id = sr.bill_id\n        LEFT JOIN\n    zeus.zeus_company cp ON bl.supplier_id = cp.id\n        LEFT JOIN\n    terra.settlement_term st ON st.term_id = bl.supplier_term_id\nwhere 1=1 \n<if> and sr.settlment_record_id = #{id}</if>\n<if> and sr.settlment_record_id in (#{idList})</if>\n<if> and sr.gmt_modified between #{startTime} and  #{end}</if>\n<if> and sr.supplier_id = #{companyId}</if>',NULL,NULL,NULL,NULL,'readSqlSession'),
 (84,'餐厅联系人','list',NULL,'FROM\n    quancheng_db.api_merchants am\n        LEFT JOIN\n    quancheng_db.api_sales_merchants asm ON am.id = asm.merchant_id\n        AND asm.deleted_at IS NULL\n        LEFT JOIN\n    quancheng_db.api_sales ass ON ass.id = asm.sales_id and ass.deleted_at is null\n        LEFT JOIN\n    quancheng_db.api_linkmen al ON am.id = al.linkable_id\n        AND linkable_type = \'QC\\\\Rest\\\\Merchant\'\n        and al.deleted_at is null\n        and al.type=0\n    left join\n    quancheng_db.api_restaurants ar on ar.merchant_id=am.id\n    left join \n    quancheng_db.api_linkmen alr ON ar.id = alr.linkable_id\n        AND alr.linkable_type = \'QC\\\\Rest\\\\Restaurant\'\n    and alr.deleted_at is null\n    and alr.type=1\n    left join \n        quancheng_db.api_linkmen alra ON ar.id = alra.linkable_id\n        AND alra.linkable_type = \'QC\\\\Rest\\\\Restaurant\'\n    and alra.deleted_at is null\n    and alra.type=2\n    left join \n        quancheng_db.api_linkmen alrb ON ar.id = alrb.linkable_id\n        AND alrb.linkable_type = \'QC\\\\Rest\\\\Restaurant\'\n    and alrb.deleted_at is null\n    and alrb.type=3\n        left join \n        quancheng_db.api_linkmen alrc ON ar.id = alrc.linkable_id\n        AND alrc.linkable_type = \'QC\\\\Rest\\\\Restaurant\'\n    and alrc.deleted_at is null\n    and alrc.type=3\n    where am.status=0 and ar.status=0\n <if> and ar.name like \'%${likeKey}%\'   </if>',NULL,NULL,NULL,NULL,'readSqlSession'),
 (85,'gsk餐厅覆盖医院(线上)','list',NULL,'from (\nselect \n  restaurant_id, \n  count(case when type= \'DDT\' then 1 else null end )as ddt, \n  count(case when type= \'Vx\'  then 1 else null end ) as vx, \n    count(case when type= \'Rx\'  then 1 else null end ) as rx, \n    count(case when type= \'TSKF\'then 1 else null end ) as tskf,\n    max(gsk_hospital_name) as gsk_hospital_name\n  from gsk_db. gsk_hospital_restaurants\n where  restaurant_id is not null and restaurant_id!=\'\'\n <if>and restaurant_id in(${restaurantIds})</if>\n group by restaurant_id ) aa',NULL,'restaurant_id',NULL,NULL,'readSqlSession'),
 (86,'外卖信息','list',NULL,'from  \nquancheng_db.api_waimai_restaurant_detail  awrd   \nleft join quancheng_db.api_restaurants ar on ar.id=awrd.restaurant_id\nleft join (select waimai_restaurant_id ,group_concat(concat(start_time,\'-\' ,end_time)) as deliver_time ,count(id) as count_delivery \n			from quancheng_db.api_waimai_delivery_rules where deleted_at is null group by waimai_restaurant_id)    awdr \n	on awrd.id=awdr.waimai_restaurant_id    \nleft join	quancheng_db.api_waimai_packing_box_fee awpbf \n on awpbf.waimai_restaurant_id=awrd.id and awpbf.deleted_at is null\n left join quancheng_db.api_waimai_shipping_fee awsf on awsf.waimai_restaurant_id=awrd.id and awsf.deleted_at is null\n where awrd.deleted_at is null and ar.status=0',NULL,NULL,NULL,NULL,'readSqlSession'),
 (87,'提前支付说明','list',NULL,'from api_pre_payment_info',NULL,NULL,NULL,NULL,'readSqlSession'),
 (89,'gsk餐厅覆盖医院(临时)','list',NULL,'from ( select    restaurant_id,    count(case when type= \'DDT\' then 1 else null end )as ddt,    count(case when type= \'Vx\'  then 1 else null end ) as vx,      count(case when type= \'Rx\'  then 1 else null end ) as rx,      count(case when type= \'TSKF\'then 1 else null end ) as tskf,     max(gsk_hospital_name) as gsk_hospital_name   from gsk_db. gsk_hospital_restaurants_prepared  where  restaurant_id is not null and restaurant_id!=\'\'  <if>and restaurant_id in(${restaurantIds})</if>  group by restaurant_id ) aa',NULL,NULL,NULL,NULL,'readSqlSession'),
 (90,'财务小工具上传图片查询','list',NULL,' from aoede.order_picture  a   left join aoede.picture_ b on a.picture_id=b.id  where   1=1 <if> and a.order_id=#{orderNum} </if>',NULL,NULL,NULL,NULL,'readSqlSession'),
 (91,'gsk餐厅覆盖医院(预处理)','list',NULL,'from ( select    restaurant_id,    count(case when type= \'DDT\' then 1 else null end )as ddt,    count(case when type= \'Vx\'  then 1 else null end ) as vx,      count(case when type= \'Rx\'  then 1 else null end ) as rx,      count(case when type= \'TSKF\'then 1 else null end ) as tskf,     max(gsk_hospital_name) as gsk_hospital_name   from gsk_db. gsk_hospital_restaurants_pretreatment  where  restaurant_id is not null and restaurant_id!=\'\'  <if>and restaurant_id in(${restaurantIds})</if>  group by restaurant_id ) aa',NULL,NULL,NULL,NULL,'readSqlSession');
 
 
 INSERT INTO `doris_table_param` (`id`,`chart_info_id`,`param_key`,`param_name`,`data_type`,`control_type`,`data_item_id`,`order_sort`) VALUES (128,57,'likeKey','线上餐厅、公海任务、推荐邮箱、项目','string','search',NULL,10),
 (129,57,'restTimeType','时间筛选','string','single','REST_TIME_TYPE',2),
 (130,57,'restStatus','餐厅状态','string','multiple','REST_STATE',1),
 (131,57,'begin','时间','string','datetime',NULL,3),
 (132,57,'end','时间','string','datetime',NULL,3),
 (133,56,'orderTimeType','时间类型','string','single','DORIS_ORDER_TIME_TYPE',0),
 (134,56,'begin','时间','string','datetime',NULL,0),
 (135,56,'end','时间','string','datetime',NULL,0),
 (136,56,'cityList','城市','string','multiple','ALL_CITY_LIST',0),
 (137,56,'salesList','销售','string','multiple','ALL_SALE_LIST',0),
 (138,56,'clientList','所属企业','string','multiple','ALL_CLIENT_LIST',0),
 (139,56,'orderType','订单类型','string','multiple','BIANNEI_BIANWAI',0),
 (140,56,'orderState','订单状态','string','multiple','SYT_ORDER_STATUS',0),
 (141,56,'likeKey','根据订单号、下单人手机、餐厅名、商户','string','search',NULL,0),
 (142,57,'clientId','所属企业','string','single','ALL_CLIENT_LIST',4),
 (143,57,'supportTakeoutOfFood','支持外卖','string','multiple','FLAG_YES_NO',5),
 (144,57,'supportReserve','支持订座','string','multiple','FLAG_YES_NO',6),
 (145,57,'salesId','销售','string','multiple','ALL_SALE_LIST',7),
 (146,58,'timeType','时间类型','string','single','SPICIAL_TIME_TYPE',1),
 (147,58,'begin','开始','string','datetime',NULL,2),
 (148,58,'end','结束','string','datetime',NULL,3),
 (150,58,'likeKey','餐厅名、申请人、高德id','string','search',NULL,6),
 (151,59,'likeKey','餐厅名、提交人、提交人手机','string','search',NULL,10),
 (152,59,'cityIds','城市','string','multiple','ALL_CITY_LIST',1),
 (153,59,'clientShortNum','企业','string','multiple','ALL_CLIENT_SHORTNUM',2),
 (154,59,'timeType','时间类型','string','single','APP_REPORT_DATE_TYPE',3),
 (155,59,'begin','开始','string','datetime',NULL,4),
 (156,59,'end','结束','string','datetime',NULL,4),
 (157,60,'timeType','时间类型','string','single','WHITE_REST_DATE_TYPE',0),
 (158,60,'begin','开始','string','datetime',NULL,0),
 (159,60,'end','结束','string','datetime',NULL,0),
 (160,60,'citys','城市','string','multiple','WHITE_RESTAURANT_CITY',0),
 (161,60,'approveMethod','审批方式','string','multiple','WHITE_REST_APPROVE_TYPE',0),
 (163,60,'likeKey','餐厅名、申请人、申请人工号','string','search',NULL,0),
 (164,61,'cityIds','城市','string','multiple','ALL_CITY_LIST',0),
 (165,61,'restStatus','餐厅状态','string','multiple','GONGHAI_REST_STATUS',0),
 (166,61,'saleId','销售','string','multiple','ALL_SALE_LIST',0),
 (167,61,'begin','开始','string','datetime',NULL,0),
 (168,61,'end','结束','string','datetime',NULL,0),
 (169,61,'likeKey','餐厅名','string','search',NULL,0),
 (170,62,'cityIds','城市','string','multiple','ALL_CITY_LIST',0),
 (171,62,'saleId','销售','string','multiple','ALL_SALE_LIST',0),
 (172,62,'begin','开始','string','datetime',NULL,0),
 (173,62,'end','结束','string','datetime',NULL,0),
 (174,62,'likeKey','餐厅名','string','search',NULL,0),
 (175,63,'cityIds','城市','string','multiple','ALL_CITY_LIST',0),
 (176,63,'recommendMethod','推荐方式','string','multiple','RECOMMAND_METHOD',0),
 (177,63,'begin','开始','string','datetime',NULL,0),
 (178,63,'end','结束','string','datetime',NULL,0),
 (179,63,'likeKey','餐厅名','string','search',NULL,0),
 (180,69,'cityIds','城市','string','multiple','ALL_CITY_LIST',2),
 (181,69,'checkStatus','飞检状态','string','multiple','FLY_CHECK_STATUS_YP',3),
 (182,69,'check_mode','飞检类型','string','multiple','FLY_CHECK_TYPE',4),
 (183,69,'begin','开始','string','datetime',NULL,5),
 (184,69,'end','结束','string','datetime',NULL,6),
 (185,69,'likeKey','餐厅名','string','search',NULL,7),
 (186,66,'city','城市','string','multiple','CALL_RECORD_CITY',1),
 (187,66,'clientId','企业','string','multiple','ALL_CLIENT_LIST',2),
 (188,66,'begin','开始','string','datetime',NULL,3),
 (189,66,'end','结束','string','datetime',NULL,4),
 (190,66,'likeKey','来电事宜','string','search',NULL,8),
 (191,67,'cityids','城市','string','multiple','ALL_CITY_LIST',0),
 (192,67,'begin','开始','string','datetime',NULL,0),
 (193,67,'end','结束','string','datetime',NULL,0),
 (194,67,'likeKey','下单人、工号、订单号','string','search',NULL,0),
 (195,65,'cityIds','城市','string','multiple','ALL_CITY_LIST',0),
 (196,65,'clientId','企业','string','multiple','ALL_CLIENT_LIST',0),
 (197,72,'begin','开始','string','datetime',NULL,0),
 (198,72,'end','结束','string','datetime',NULL,0),
 (199,74,'begin','开始','string','datetime',NULL,0),
 (200,74,'end','结束','string','datetime',NULL,0),
 (201,74,'clientId','所属企业','string','multiple','ALL_CLIENT_LIST',0),
 (202,74,'likeKey','订单号','string','search',NULL,0),
 (203,68,'clientId','企业','string','multiple','ALL_CLIENT_LIST',0),
 (204,69,'clientId','企业','string','multiple','ALL_CLIENT_LIST',1),
 (205,75,'orderTimeType','时间类型',NULL,'single','DORIS_ORDER_TIME_TYPE',0),
 (206,75,'begin','时间',NULL,'datetime',NULL,0),
 (207,75,'end','时间',NULL,'datetime',NULL,0),
 (208,75,'cityList','城市',NULL,'multiple','ALL_CITY_LIST',0),
 (209,75,'salesList','销售',NULL,'multiple','ALL_SALE_LIST',0),
 (210,75,'clientList','所属企业',NULL,'multiple','ALL_CLIENT_LIST',0),
 (211,75,'orderType','订单类型',NULL,'multiple','BIANNEI_BIANWAI',0),
 (212,75,'orderState','订单状态',NULL,'multiple','SYT_ORDER_STATUS',0),
 (213,75,'likeKey','根据订单号、下单人手机、餐厅名',NULL,'search',NULL,0),
 (214,66,'callTypes','通话类型','string','multiple','CALL_RECORD_TYPE',5),
 (215,68,'likeKey','用户','string','search',NULL,0),
 (216,76,'begin','时间','string','datetime',NULL,2),
 (217,76,'end','时间','string','datetime',NULL,3),
 (218,76,'dateType','时间类型','string','single','REQUEST_SUPPLIER_DATE_TYPE',1),
 (219,76,'likeKey','订单号、客服','string','search',NULL,4),
 (220,77,'actionId','操作类型','string','multiple','ORDER_ACTION_MAPPING',1),
 (221,77,'likeKey','订单号、备注内容','string','search',NULL,4),
 (222,77,'begin','时间','string','datetime',NULL,2),
 (223,77,'end','时间','string','datetime',NULL,3),
 (224,57,'cityIds','城市','string','multiple','ALL_CITY_LIST',8),
 (225,78,'likeKey','订单号、餐厅、商户名','string','search',NULL,0),
 (226,58,'companyId','企业','string','single','ALL_CLIENT_LIST',5),
 (227,80,'clientId','所属企业','string','single','ALL_CLIENT_LIST',0),
 (228,80,'likeKey','医院名称','string','search',NULL,0),
 (229,81,'likeKey','level','string','search',NULL,0),
 (230,84,'likeKey','餐厅名',NULL,'search',NULL,0),
 (232,66,'company','来电单位','string','multiple','CALL_RECORD_COMPANY',0),
 (233,85,'restaurantIds','餐厅id,多个用英文逗号相隔','string','search',NULL,0),
 (234,89,'restaurantIds','餐厅id,多个用英文逗号相隔','string','search',NULL,0),
 (235,90,'orderNum','订单号','string','search',NULL,0),
 (236,82,'searchKey','餐厅id','string','search','',0),
 (237,91,'restaurantIds','餐厅id,多个用英文逗号相隔','string','search',NULL,0),
 (238,66,'complain','是否投诉','string','single','COMPLAIN_FLAG',9);

/*
-- Query: SELECT *FROM statistics_db.dp_data_item
LIMIT 0, 500

-- Date: 2018-06-26 16:13
*/
INSERT INTO `dp_data_item` (`id`,`item_key`,`item_mark`,`server_group`,`item_status`,`created_at`,`item_type`,`item_content`) VALUES (1,'FLAG_YES_NO','布尔值','data_platform','ACTIVE','2017-07-05 14:10:18','DETAIL_KV',NULL)
, (2,'FLAG_AVAILABILITY','是否可用','data_platform','ACTIVE','2017-07-05 14:15:12','DETAIL_KV',NULL)
, (3,'DATA_ITEM_TYPE','词典类型','data_platform','ACTIVE','2017-07-07 11:30:12','DETAIL_KV',NULL)
, (4,'CHART_TYPE','图表类型','data_platform','ACTIVE','2017-07-10 16:31:03','DETAIL_KV',NULL)
, (5,'ORDER_PICTURE_TYPE','订单图片类型','data_platform','ACTIVE','2017-07-12 16:09:19','DETAIL_KV',NULL)
, (6,'CHART_CONTROL_TYPE','图表控件类型','data_platform','ACTIVE','2017-07-12 16:09:19','DETAIL_KV',NULL)
, (7,'ALL_CITY_LIST','城市列表','data_platform','ACTIVE','2017-07-18 10:28:35','VIEW','(select id as detail_key,name as detail_text from quancheng_db.`16860_region` where level=2 and deleted_at is null) citys')
, (8,'SYT_ORDER_TYPE','业务类型','data_platform','ACTIVE','2017-07-19 14:52:07','DETAIL_KV',NULL)
, (9,'CHART_SHOW_DATA_TYPE','图表显示字段数据类型','data_platform','ACTIVE','2017-07-19 14:52:07','DETAIL_KV',NULL)
, (10,'SYT_SERVICE_TYPE','订单类型','data_platform','ACTIVE','2017-07-26 16:21:29','DETAIL_KV',NULL)
, (11,'RATING_SCORE','订单评分','data_platform','ACTIVE','2017-07-26 17:09:03','DETAIL_KV',NULL)
, (12,'FLY_CHECK_STATUS_YP','飞检状态-雅培','data_platform','ACTIVE','2017-07-27 09:48:38','DETAIL_KV',NULL)
, (13,'FLY_CHECK_TYPE','飞检类型','data_platform','ACTIVE','2017-07-27 09:48:38','DETAIL_KV',NULL)
, (14,'DORIS_FLY_TIME_TYPE','企业后台飞检订单时间筛选类型','data_platform','ACTIVE','2017-07-27 10:01:45','DETAIL_KV',NULL)
, (15,'SYT_ORDER_STATUS','订单状态','data_platform','ACTIVE','2017-07-27 14:17:20','DETAIL_KV',NULL)
, (16,'USER_STATUS','用户状态','data_platform','ACTIVE','2017-07-27 17:13:04','DETAIL_KV',NULL)
, (17,'WHITE_RESTAURANT_CITY','白名单城市','data_platform','ACTIVE','2017-07-27 18:44:54','VIEW','(select distinct city as detail_key,city as detail_text from out_white_list_restaurant where city is not null ) citys')
, (18,'DORIS_DETAIL_INFO_TYPE','详情页面类型','data_platform','ACTIVE','2017-07-28 13:25:00','DETAIL_KV',NULL)
, (19,'ORDER_DETAIL_CONFIG','订单详情配置','data_platform','ACTIVE','2017-07-28 17:37:08','DETAIL_KV',NULL)
, (20,'DORIS_ORDER_TIME_TYPE','企业后台订单时间筛选类型','data_platform','ACTIVE','2017-07-31 17:14:47','DETAIL_KV',NULL)
, (21,'SORT_TYPE','排序类型','data_platform','ACTIVE','2017-07-05 14:10:18','DETAIL_KV',NULL)
, (22,'FLAG_0_1','布尔值(0/1)','data_platform','ACTIVE','2017-07-05 14:10:18','DETAIL_KV',NULL)
, (23,'FLY_CHECK_STATUS_COMMON','飞检状态通用','data_platform','ACTIVE','2017-08-02 10:47:30','DETAIL_KV',NULL)
, (24,'BIANNEI_BIANWAI','订单类型','data_platform','ACTIVE','2017-08-04 14:07:24','DETAIL_KV',NULL)
, (25,'CONSUME_TYPE','消费类型','data_platform','ACTIVE','2017-08-09 16:07:27','DETAIL_KV',NULL)
, (26,'OPEN_CITY','城市','data_platform','ACTIVE','2017-08-16 09:49:56','VIEW','(select  rg.id as detail_key,rg.name as detail_text from quancheng_db.`16860_client_region`  cr  left join  quancheng_db.`16860_region` rg on cr.city_id=rg.id where level=2 and deleted_at is null and client_id=@companyId@ and delete_time=0) citys')
, (27,'ALL_SALE_LIST','销售','data_platform','ACTIVE','2017-09-04 15:59:07','VIEW','( select uid as detail_key,name as  detail_text from quancheng_db.api_sales ) aa')
, (28,'ALL_CLIENT_LIST','企业','data_platform','ACTIVE','2017-09-04 16:02:02','VIEW','(select id as detail_key,title as detail_text from quancheng_db.16860_client where status=1) aa')
, (29,'REST_TIME_TYPE','餐厅时间','data_platform','ACTIVE','2017-09-07 09:38:36','DETAIL_KV',NULL)
, (30,'REST_STATE','线上餐厅状态','data_platform','ACTIVE','2017-09-07 10:50:32','DETAIL_KV',NULL)
, (31,'SPICIAL_TIME_TYPE','特许商户时间','data_platform','ACTIVE','2017-09-07 11:06:28','DETAIL_KV',NULL)
, (32,'ALL_CLIENT_SHORTNUM','企业编号','data_platform','ACTIVE','2017-09-07 14:34:04','VIEW','(select name as detail_key,title as detail_text from quancheng_db.16860_client where status=1) aa')
, (33,'APP_REPORT_DATE_TYPE','时间类型','data_platform','ACTIVE','2017-09-07 14:36:22','DETAIL_KV',NULL)
, (34,'WHITE_REST_DATE_TYPE','时间类型','data_platform','ACTIVE','2017-09-07 15:00:16','DETAIL_KV',NULL)
, (35,'WHITE_REST_APPROVE_TYPE','审判类型','data_platform','ACTIVE','2017-09-07 15:02:08','DETAIL_KV',NULL)
, (36,'GONGHAI_REST_STATUS','公海状态','data_platform','ACTIVE','2017-09-07 15:21:01','DETAIL_KV',NULL)
, (37,'CALL_RECORD_CITY','通话记录城市','data_platform','ACTIVE','2017-09-13 11:30:15','VIEW','(select distinct city as detail_key,city as detail_text from out_user_call_record where city is not null ) citys')
, (38,'UCB_SECTORS','ucb部门','data_platform','ACTIVE','2017-10-11 14:39:00','VIEW','(select id as detail_key ,structure_name as detail_text from quancheng_db.`16860_organizational_structure` where client_id = 42 and structure_type = \'sector\'  and status=1) sector')
, (39,'UCB_REGIONS','ucb大区','data_platform','ACTIVE','2017-10-11 14:40:00','VIEW','(select id as detail_key ,structure_name as detail_text from quancheng_db.`16860_organizational_structure` where client_id = 42 and structure_type = \'region\' and status=1) region')
, (40,'ALL_CLIENT_INVOICE_TITLE','企业发票抬头','data_platform','ACTIVE','2017-10-11 16:57:27','VIEW','(select id as detail_key, invoice_title as detail_text from quancheng_db.16860_client where status=1 and invoice_title<>\'\') aa')
, (41,'RECOMMAND_METHOD','推荐方式','data_platform','ACTIVE','2017-10-13 15:53:26','DETAIL_KV',NULL)
, (42,'ACCOUNT_TYPE','账户类型','data_platform','ACTIVE','2017-10-19 16:54:28','DETAIL_KV',NULL)
, (43,'MANAGE_TYPE','结算类型','data_platform','ACTIVE','2017-10-19 16:54:28','DETAIL_KV',NULL)
, (44,'CALL_RECORD_TYPE','通话记录','data_platform','ACTIVE','2017-10-23 10:13:56','DETAIL_KV',NULL)
, (45,'REQUEST_SUPPLIER_DATE_TYPE','催单记录时间','data_platform','ACTIVE','2017-10-25 10:33:01','DETAIL_KV',NULL)
, (46,'ORDER_ACTION_MAPPING','订单操作行为','data_platform','ACTIVE','2017-10-25 14:22:44','DETAIL_KV',NULL)
, (47,'UCB_USER_LIST','ucb用户','data_platform','ACTIVE','2017-10-25 18:15:45','VIEW','(select  job_num as detail_key, realname as detail_text from tmp_member where member_status =1 and cid=42) citys')
, (48,'ALL_PROVINCE','省/直辖市','data_platform','ACTIVE','2017-11-15 09:40:22','VIEW','(select id as detail_key,name as detail_text from quancheng_db.`16860_region` where level=1 and deleted_at is null) citys')
, (49,'ALL_AREA','区/县','data_platform','ACTIVE','2017-11-15 09:40:22','VIEW','(select id as detail_key,name as detail_text from quancheng_db.`16860_region` where level=3 and deleted_at is null) citys')
, (50,'CALL_RECORD_COMPANY','通话记录企业','data_platform','ACTIVE','2018-01-24 10:33:40','VIEW','(SELECT distinct company as detail_key, company as detail_text FROM statistics_db.out_user_call_record where company is not null) companys')
, (51,'COMPLAIN_FLAG','是否投诉','data_platform','ACTIVE','2018-06-26 14:09:28','DETAIL_KV',NULL);
/*
-- Query: SELECT *FROM statistics_db.dp_data_item_detail
LIMIT 0, 500

-- Date: 2018-06-26 16:15
*/
INSERT INTO `dp_data_item_detail` (`id`,`item_id`,`detail_key`,`detail_text`,`detail_status`) VALUES (1,2,'ACTIVE','启用','ACTIVE')
,(2,2,'DISABLED','禁用','ACTIVE')
,(3,1,'Y','是','ACTIVE')
,(4,1,'N','否','ACTIVE')
,(5,3,'VIEW','视图','ACTIVE')
,(6,3,'DETAIL_KV','key-value','ACTIVE')
,(7,4,'line','折线图','ACTIVE')
,(8,4,'bar','柱状图','ACTIVE')
,(9,4,'pie','饼图','ACTIVE')
,(10,4,'area','区域图','ACTIVE')
,(11,4,'table','表格','ACTIVE')
,(12,5,'VOUCHER','打款凭证','ACTIVE')
,(13,5,'INVOICE','返点发票','ACTIVE')
,(14,5,'RECEIPT','代收款证明','ACTIVE')
,(15,6,'select','选择控件','ACTIVE')
,(16,6,'time_day','时间（天）控件','ACTIVE')
,(17,6,'time_week','时间（周）控件','ACTIVE')
,(18,6,'time_month','时间（月）控件','ACTIVE')
,(19,6,'input','输入框控件','ACTIVE')
,(20,8,'yuding','预定','ACTIVE')
,(21,8,'waimai','外卖','ACTIVE')
,(22,9,'string','字符串类型','ACTIVE')
,(23,9,'number','数字类型','ACTIVE')
,(24,9,'chartId','图表id类型','ACTIVE')
,(25,9,'details','详情类型','ACTIVE')
,(26,9,'url','超链接类型','ACTIVE')
,(27,9,'image','图片(url)类型','ACTIVE')
,(28,4,'datatable','分页表','ACTIVE')
,(29,10,'D','预定','ACTIVE')
,(30,10,'W','外卖','ACTIVE')
,(31,11,'1','1分','ACTIVE')
,(32,11,'2','2分','ACTIVE')
,(33,11,'3','3分','ACTIVE')
,(34,11,'4','4分','ACTIVE')
,(35,11,'5','5分','ACTIVE')
,(40,12,'5','合规','ACTIVE')
,(42,12,'10','异常','ACTIVE')
,(45,12,'25','疑似异常','ACTIVE')
,(46,13,'事中','事中','ACTIVE')
,(47,13,'事后','事后','ACTIVE')
,(48,14,'yuyue_time','预约时间','ACTIVE')
,(49,14,'order_time','下单时间','ACTIVE')
,(50,14,'pay_time','支付时间','ACTIVE')
,(52,6,'single_select','单选','ACTIVE')
,(53,15,'yd1','预定-待确认','ACTIVE')
,(54,15,'yd7','预定-客服拒绝','ACTIVE')
,(55,15,'yd11','预定-客服取消','ACTIVE')
,(56,15,'yd6','预定-供应商拒绝','ACTIVE')
,(57,15,'yd10','预定-供应商取消','ACTIVE')
,(58,15,'yd15','预定-用户取消','ACTIVE')
,(59,15,'yd5','预定-预定成功','ACTIVE')
,(60,15,'yd30','预定-待支付','ACTIVE')
,(61,15,'yd35','预定-线上支付','ACTIVE')
,(63,15,'yd36','预定-线下支付','ACTIVE')
,(64,15,'wm2001','外卖-待确认','ACTIVE')
,(65,15,'wm2007','外卖-客服拒绝','ACTIVE')
,(66,15,'wm2008','外卖-客服取消','ACTIVE')
,(67,15,'wm2006','外卖-供应商拒绝','ACTIVE')
,(68,15,'wm2005','外卖-预定成功','ACTIVE')
,(69,15,'wm2002','外卖-已支付','ACTIVE')
,(71,15,'wm2003','外卖-线下支付','ACTIVE')
,(72,16,'0','禁用','ACTIVE')
,(73,16,'1','正常','ACTIVE')
,(74,16,'2','待激活','ACTIVE')
,(75,18,'order','订单','ACTIVE')
,(76,18,'restarurant','餐厅','ACTIVE')
,(77,18,'fly_check','飞检','ACTIVE')
,(78,19,'42','ucb','ACTIVE')
,(79,19,'43','agn','ACTIVE')
,(80,19,'132','syttakeda','ACTIVE')
,(82,20,'payTime','支付时间','ACTIVE')
,(83,20,'createTime','下单时间','ACTIVE')
,(84,20,'yuyueTime','预约时间','ACTIVE')
,(85,21,'asc','正序','ACTIVE')
,(86,21,'desc','倒序','ACTIVE')
,(87,22,'1','是','ACTIVE')
,(88,22,'0','否','ACTIVE')
,(89,9,'list','数组类型','ACTIVE')
,(90,5,'OUTDOOR','户外图片','ACTIVE')
,(91,5,'INDOOR','户内图片','ACTIVE')
,(92,9,'date','时间类型','ACTIVE')
,(93,23,'5','合规','ACTIVE')
,(94,23,'10','异常','ACTIVE')
,(95,24,'编内','编内','ACTIVE')
,(96,24,'编外','编外','ACTIVE')
,(97,25,'-1','全部类型','ACTIVE')
,(98,25,'0,waimai','支持外卖','ACTIVE')
,(99,25,'reserve,0','支持预定','ACTIVE')
,(102,25,'reserve,waimai','支持预定,外卖','ACTIVE')
,(103,29,'created_at','入库时间','ACTIVE')
,(104,29,'shelf_time','下线时间','ACTIVE')
,(105,29,'online_time','上线时间','ACTIVE')
,(106,30,'0','正常','ACTIVE')
,(107,30,'1','禁用','ACTIVE')
,(108,31,'created_at','申请时间','ACTIVE')
,(109,31,'approved_time','审批时间','ACTIVE')
,(110,33,'create_time','纠错时间','ACTIVE')
,(111,34,'created_at','申请时间','ACTIVE')
,(112,34,'approve_time','审批时间','ACTIVE')
,(113,35,'APP','APP','ACTIVE')
,(114,35,'EMAIL','EMAIL','ACTIVE')
,(115,36,'0','上线','ACTIVE')
,(116,36,'1','禁用','ACTIVE')
,(117,36,'2','审核','ACTIVE')
,(118,36,'20','未分配','ACTIVE')
,(119,36,'21','拓展中,历史数据(拒绝合作)','ACTIVE')
,(120,36,'22','拓展中,历史数据(不符合商务宴请)','ACTIVE')
,(121,36,'23','拓展中,历史数据(不合规)','ACTIVE')
,(122,36,'24','拓展中,历史数据(歇业、转店、装修)','ACTIVE')
,(123,36,'25','初步洽谈','ACTIVE')
,(124,36,'26','深入洽谈','ACTIVE')
,(125,36,'27','合同协商','ACTIVE')
,(126,36,'28','完成签约','ACTIVE')
,(127,36,'40','无法签约(娱乐设施)','ACTIVE')
,(128,36,'41','无法签约(人均超标)','ACTIVE')
,(129,36,'42','无法签约(发票不规范)','ACTIVE')
,(130,36,'43','无法签约(运营资质不符)','ACTIVE')
,(131,36,'44','无法签约(门店装修)','ACTIVE')
,(132,36,'45','无法签约(歇业倒闭)','ACTIVE')
,(133,36,'46','无法签约(不与第三方合作)','ACTIVE')
,(134,36,'47','无法签约(财务不接受)','ACTIVE')
,(135,36,'48','无法签约(分店无法做主)','ACTIVE')
,(136,20,'reportTime','报备时间','ACTIVE')
,(137,20,'confirmTime','确认时间','ACTIVE')
,(138,24,'特许','特许','ACTIVE')
,(139,41,'APP','APP','ACTIVE')
,(140,41,'编外推荐','编外推荐','ACTIVE')
,(141,15,'bw10','编外-待审核','ACTIVE')
,(142,15,'bw12','编外-审核通过','ACTIVE')
,(143,15,'bw20','编外-完成、支付','ACTIVE')
,(144,15,'bw30','编外-审核拒绝','ACTIVE')
,(145,15,'bw35','编外-订单取消','ACTIVE')
,(146,42,'personal','对私','ACTIVE')
,(147,42,'company','对公','ACTIVE')
,(148,43,'AFTER_DAY','T+n账期','ACTIVE')
,(149,43,'BEFORE_DINNER','餐前预付','ACTIVE')
,(150,43,'AFTER_WEEK','账期周结','ACTIVE')
,(151,43,'AFTER_MONTH','账期月结','ACTIVE')
,(152,43,'BEFORE_WIPE','循环预付','ACTIVE')
,(153,44,'去电','去电','ACTIVE')
,(154,44,'来电','来电','ACTIVE')
,(155,44,'打给商户','打给商户','ACTIVE')
,(156,44,'打给用户','打给用户','ACTIVE')
,(157,44,'商户来电','商户来电','ACTIVE')
,(158,44,'用户来电','用户来电','ACTIVE')
,(159,45,'yuyue_time','预约时间','ACTIVE')
,(160,45,'cuidan_time','催单时间','ACTIVE')
,(161,46,'1','管理员登录',NULL)
,(162,46,'2','用户注册',NULL)
,(163,46,'3','用户下单',NULL)
,(164,46,'4','支付短信',NULL)
,(165,46,'5','报销单邮件',NULL)
,(166,46,'6','订单短信',NULL)
,(167,46,'7','发起付款请求',NULL)
,(168,46,'8','付款完成',NULL)
,(169,46,'9','重新发起支付',NULL)
,(170,46,'10','支付前台通知',NULL)
,(171,46,'11','支付后台通知',NULL)
,(172,46,'12','订单日志',NULL)
,(173,46,'13','订单备注',NULL)
,(174,46,'14','催款记录',NULL)
,(175,46,'15','催单成功',NULL)
,(176,46,'16','销售催单',NULL)
,(177,46,'17','催单失败',NULL)
,(178,46,'18','企业后台用户登录',NULL)
,(179,20,'settlement_time','结算时间','ACTIVE')
,(180,24,'代购','代购','ACTIVE')
,(181,51,'投诉','投诉',NULL)
,(182,51,'非投诉','非投诉',NULL);


