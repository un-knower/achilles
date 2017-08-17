
## App工程介绍


app是个标准的springboot工程,目前是基于1.4.0版本。

每个应用都需要从基础工程中fetch出来,原则上不允许单个应用去修改里面的内容

### 商宴通组织架构
    事业部:16860_member.business_id ==16860_organizational_structure.id --> 16860_member.business_id--> ifnull[0,null] -->16860_member. division_id == 16860_organizational_structure.old_id
 
    子公司:16860_member.branch_id ==16860_organizational_structure.id --> 16860_member.branch_id--> ifnull[0,null] --> 16860_member.invoice_id == 16860_organizational_structure.old_id
 
    部门   :16860_member.sector_id ==16860_organizational_structure.id --> 16860_member.sector_id--> ifnull[0,null] --> 16860_member.invoice_id == 16860_organizational_structure.old_id
 
     产品组:16860_member.productgroup_id ==16860_organizational_structure.id --> 16860_member.productgroup_id--> ifnull[0,null] --> 16860_member.invoice_id == 16860_organizational_structure.old_id
 
    大区   :16860_member.business_id ==16860_organizational_structure.id --> 16860_member.business_id--> ifnull[0,null] --> 16860_member.division_id == 16860_organizational_structure.old_id
### 商宴通数据字典
    16860_account : 订单结算单；商户发起申请结算
    16860_account_copy : 订单结算单；商户发起申请结算
    16860_action : 系统行为表
    16860_action_log : 行为日志表
    16860_advertise : 图片轮播管理
    16860_advice : 
    16860_api_info : API用户登录信息表
    16860_app_entry : APP入口功能表
    16860_app_entry_category : APP入口分类表
    16860_app_entry_city : APP入口关联城市表
    16860_app_entry_rule : APP入口关联企业版本规则表
    16860_app_project : App项目
    16860_app_request_log : # # # # # # # # # # # # # # # # # # # # # # # # 
    16860_app_version : 
    16860_app_version_copy : 
    16860_application : 
    16860_application_auth : 
    16860_application_block : 
    16860_application_company : 
    16860_application_menu : 
    16860_application_type : 
    16860_auth_group : 权限用户组表
    16860_auth_group_access : 用户授权表
    16860_auth_rule : 权限规则表
    16860_call_record : 来电记录
    16860_category : 分类表
    16860_category_copy : 分类表
    16860_check_log : 系统断点日志 1.财务后台
    16860_check_rule : 预付款审计规则表
    16860_client : 客户表
    16860_client_invoice : 企业发票表
    16860_client_preferential : 企业优惠表
    16860_client_region : 企业开放城市表
    16860_company : 
    16860_config : 系统配置表
    16860_data_check : 
    16860_department : 
    16860_division : 企业事业部表
    16860_division_client : 企业事业部关联表
    16860_file : 
    16860_hospital : 医院表
    16860_hospital_clients : 医院企业关联表
    16860_hospitals : 医院表
    16860_incoming_log : 来电记录
    16860_jiesuan : 结算单
    16860_jiesuan_copy : 结算单
    16860_jiesuan_copy1 : 结算单
    16860_jiesuan_copy2 : 结算单
    16860_jiesuan_copy3 : 结算单
    16860_jiesuan_copy4 : 结算单
    16860_jiesuan_copy_old : 结算单
    16860_jiesuan_detail : 结算单明细
    16860_jiesuan_detail_copy : 结算单明细
    16860_jiesuan_detail_copy1 : 结算单明细
    16860_jiesuan_detail_copy2 : 结算单明细
    16860_jiesuan_detail_copy3 : 结算单明细
    16860_jiesuan_detail_copy4 : 结算单明细
    16860_jiesuan_detail_copy5-lp : 结算单明细
    16860_jiesuan_detail_old : 结算单明细
    16860_jiesuan_error : 结算单报错
    16860_lbs : LBS表
    16860_lbs_suggest : 关键词LBS表
    16860_manager : 管理员表
    16860_mc_account : 银行账户信息
    16860_mc_account_bkup : 银行账户信息
    16860_mc_account_copy : 银行账户信息
    16860_mc_account_debt : 银行账户.欠款明细
    16860_mc_account_detail : 
    16860_mc_account_detail_new : 
    16860_mc_account_records : 餐厅银行账号变更记录表
    16860_mc_check_log : 结算订单.异常检测
    16860_mc_import : 财务结算导入单
    16860_mc_import_log : 财务结算导入日志表
    16860_mc_import_sub_log : 财务分表导入日志
    16860_mc_jiesuan_note : 结算订单.笔记
    16860_mc_jiesuan_order : 结算订单【SYT对商户下单】 4个状态（is_review，is_paying，is_paid，is_finish） 4个状态变更_由其它表变更_而变更；
    is_review：审核
    is_paying：打款记录表（生成一笔新的待打款后，is_paying=1）
    is_paid：打款记录表（待打款上传打款凭证后，is_paid += 1）
    is_finish：清算.操作（ 财务标注取消（清算）：is_finish=3 已打款to商户+用户支付（清算）：is_finish=10  )
    16860_mc_jiesuan_order_copy : 结算订单【SYT对商户下单】 4个状态（is_review，is_paying，is_paid，is_finish） 4个状态变更_由其它表变更_而变更；
    is_review：审核
    is_paying：打款记录表（生成一笔新的待打款后，is_paying=1）
    is_paid：打款记录表（待打款上传打款凭证后，is_paid += 1）
    is_finish：清算.操作（ 财务标注取消（清算）：is_finish=3 已打款to商户+用户支付（清算）：is_finish=10  )
    16860_mc_jiesuan_order_note : 关联表 | 结算订单&结算笔记
    16860_mc_jiesuan_payment : 结算打款记录
    16860_mc_merge_payment : 合并打款抵扣明细表
    16860_mc_payment_voucher : 结算打款凭证
    16860_member : 用户表
    16860_member_account : 
    16860_menu : 菜单表
    16860_menu_copy : 菜单表
    16860_merchant : 商户表 2015.01.06 李竹良 添加商户说明、商户禁用、返点变更日期字段
    16860_merchant_asset : 商户资产表
    16860_merchant_ucenter : 商户用户表
    16860_message : 消息表
    16860_message_content : 消息内容表
    16860_offstaff_authority : 编外权限表
    16860_offstaff_order : 编外订单表
    16860_order : 订单表
    16860_order_account : 订单，结算单 关联表
    16860_order_account_copy : 订单，结算单 关联表
    16860_order_copy : 订单表
    16860_order_copy1 : 订单表
    16860_order_copy——20140929 : 订单表
    16860_order_history : 历史订单表
    16860_order_in_all_backup : VIEW
    16860_order_location : 订单支付定位日志表
    16860_order_merchant : 商户订单表
    16860_order_payment : 订单支付表
    16860_order_rating : 订单评分表
    16860_order_rating_copy : 订单评分表
    16860_order_rating_copy1 : 订单评分表
    16860_order_refund : 订单-申请退款
    16860_order_refund_info : 订单-申请退款（时）订单信息
    16860_order_state : 订单状态表
    16860_order_status_config : 订单状态配置表
    16860_order_waimai : VIEW
    16860_order_wait : VIEW
    16860_order_wait_opt : VIEW
    16860_orders_in_all : VIEW
    16860_orders_in_all_copy : VIEW
    16860_organizational_relationship : 公司组织架构关系表
    16860_organizational_structure : 公司组织架构表
    16860_picture : 上传图片表
    16860_prepay : 
    16860_prepay_details : 报表-预付详情表
    16860_prepay_details_copy_20140930 : 报表-预付详情表
    16860_quality_check : QC：飞检
    16860_quality_check_result : QC：飞检 结果
    16860_questionsurvey_activity : 问卷调查活动表
    16860_questionsurvey_activity_client : 问卷调查活动企业表
    16860_questionsurvey_activity_detail : 问卷调查活动明细表
    16860_questionsurvey_config : 问卷调查配置表
    16860_questionsurvey_detail : 问卷调查明细表
    16860_questionsurvey_header : 问卷调查主表
    16860_region : 区域表
    16860_region1 : 地区表
    16860_region_2015-01-23 : 区域表
    16860_region_2015-01-26 : 区域表
    16860_region_new : 区域表
    16860_restaurant : 餐厅表 2015.01.06 李竹良  添加接单人姓名、电话，返点变更日期字段
    16860_restaurant_client : 
    16860_restaurant_copy : 餐厅表
    16860_restaurant_copy_20140901 : 餐厅表
    16860_restaurant_copy_20140902 : 餐厅表
    16860_restaurant_copy_20140903 : 餐厅表
    16860_restaurant_copy_20150123 : 餐厅表 2015.01.06 李竹良 添加接单人姓名、电话，返点变更日期字段
    16860_restaurant_hits : 餐厅点击统计表
    16860_restaurant_menu : 餐厅菜单表
    16860_restaurant_menu_copy_20140921 : 餐厅菜单表
    16860_restaurant_menu_copy_20140921_2 : 餐厅菜单表
    16860_restaurant_new : 餐厅表 2015.01.06 李竹良 添加接单人姓名、电话，返点变更日期字段
    16860_restaurant_popular : 热门餐厅表
    16860_restaurant_recommend : 销售推荐餐厅表
    16860_restaurant_special : 特殊餐厅表
    16860_restaurant_statistics : 销售推荐+热门餐厅统计表
    16860_salesman : 销售信息
    16860_send_log : 邮件、短消息发送日志
    16860_session : 
    16860_staff : 商户员工表
    16860_staff2 : 商户员工表
    16860_statistics_perfect : 报表补充操作表
    16860_test_account : 
    16860_tmp_hospital : 
    16860_token : 
    16860_tool_check : 工具：检查图片，打分器
    16860_tool_check_img : 工具：检查图片，打分器
    16860_ucenter : 用户中心表
    16860_ucenter_password : 用户中心密码表
    16860_user : 用户表
    16860_user_client : 
    16860_user_common_hospital : 销售推荐餐厅表
    16860_user_common_hospital_copy : 销售推荐餐厅表
    16860_user_data : 企业提供用户数据表
    16860_user_favorite : 收藏表
    16860_user_feedback : 用户反馈表
    16860_user_hospital : 
    16860_user_hot_restaurant : VIEW
    16860_user_hot_restaurant_opt : VIEW
    16860_user_message : 用户邮件,短信,通知表
    16860_user_restaurant_feedback : 用户餐厅反馈表
    16860_user_server_read : 用户服务功能读取表
    16860_version : 数据库版本表
    16860_view_mc_jiesuan_restaurant_rates : VIEW
    16860_wm_address : 茶点水果用户地址库
    16860_wm_canceled : 茶点水果取消订单原因表
    16860_wm_order : 茶点水果订单表
    16860_wm_order_copy : 茶点水果订单表
    16860_wm_order_detail : 茶点水果订单明细表
    16860_wm_order_package : 茶点水果订单包裹表
    16860_wm_product : 茶点水果商品表
    16860_wm_product- : 茶点水果商品表
    16860_wm_product-- : 茶点水果商品表
    16860_wm_product_copy : 外卖商品表
    16860_wm_product_copy1 : 茶点水果商品表
    16860_wm_region : 茶点水果供应商配送区域表
    16860_wm_supplier : 茶点水果供应商表
    16860_wm_supplier_ : 茶点水果供应商表
    16860_wm_supplier_20150128_before : 茶点水果供应商表
    16860_xiaofei_statistic : 统计表
    api_apply_recodes : 
    api_assets : 返点结算类型
    api_auth_local : VIEW
    api_business_remind : 资质到期提醒表
    api_businesses : 
    api_cars_order_detail : 用车详情表
    api_cars_orders : 用车主表
    api_client_blacklists : 企业黑名单表
    api_client_code_orders : 企业订单关联表
    api_client_recommand_records : 
    api_client_recommand_rest : 
    api_client_recommander : 
    api_client_service : 企业供应商服务关系表
    api_code : 
    api_currency : 货币单位表
    api_delivery_address : 外卖收货地址
    api_dunning_follow : 催款订单跟踪表
    api_dunning_order_status : 催款订单状态表
    api_dunning_orders : 催款订单表
    api_express : 快递表
    api_failed_jobs : 
    api_feedback : 
    api_feedback_pics : 
    api_feijian_case : 
    api_feijian_rule : 
    api_finance_bankrefund : 
    api_finance_credence : 
    api_finance_log : 
    api_finance_order : 
    api_hotel_brands : 
    api_hotel_conf_services : 各种配置的数据
    api_hotel_guest_rooms : 
    api_hotel_images : 
    api_hotel_meeting_halls : 
    api_hotel_service_lists : 
    api_hotel_traffic : 
    api_hotels : 会务酒店
    api_lbs_infos : 
    api_lbs_view : VIEW
    api_linkmen : 
    api_logs : 
    api_meeting_place : 
    api_meeting_place_pic : 
    api_meeting_place_service : 
    api_meeting_place_service_category : 
    api_meeting_place_site_fees : 
    api_merchants : 
    api_merchants_view : VIEW
    api_message_categories : 消息中心分类
    api_messages : 消息中心，消息持久化表
    api_mice_change : 会务差额统计信息
    api_mice_company_extra : 陪同人员差旅费
    api_mice_cost_records : 会务费用记录表
    api_mice_detail_breaks : 茶歇服务详单
    api_mice_detail_cars : 会务用车详单
    api_mice_detail_company : 会务陪同人员详单
    api_mice_detail_company_extra : 陪同人员差旅费
    api_mice_detail_insurance : 会务保险详单
    api_mice_detail_items : 会务场地设施详单
    api_mice_detail_log : 会务操作日志详情表
    api_mice_detail_other_payment : 额外费用
    api_mice_detail_others : 会务其他详单
    api_mice_detail_room : 客房服务详单
    api_mice_detail_team_building : 团建需求详单
    api_mice_detail_transport : 会务旅程交通信息详单
    api_mice_extra_cars : 会务用车真实数据
    api_mice_extra_flight : 会务机票真实数据
    api_mice_extra_rooms : 会务住房真实数据
    api_mice_extra_transport : 会务旅程交通真实数据
    api_mice_favorites : 收藏酒店表
    api_mice_hotel_ucenter : 酒店用户中心表
    api_mice_hotel_ucenter_imp : 创建用户临时表
    api_mice_hotels_remark : 会务供应商主要备注信息
    api_mice_log : 会务操作日志表
    api_mice_need_breaks : 茶歇服务
    api_mice_need_breaks_detail : 会务茶歇报价
    api_mice_need_cars : 会务用车
    api_mice_need_cars_detail : 会务用车报价
    api_mice_need_company : 会务陪同人员
    api_mice_need_company_detail : 会务陪同人员
    api_mice_need_dinners : 会务用餐需求
    api_mice_need_dinners_detail : 会务用餐需求详情表
    api_mice_need_insurance : 会务保险
    api_mice_need_insurance_detail : 会务保险报价
    api_mice_need_items : 会务场地设施需求
    api_mice_need_items_detail : 会务场地设施报价
    api_mice_need_meal_detail : 酒店外用餐报价
    api_mice_need_others : 其他服务
    api_mice_need_others_detail : 会务其他项目报价
    api_mice_need_places : 会务场地需求
    api_mice_need_places_detail : 会务场地需求详情表
    api_mice_need_room : 客房服务
    api_mice_need_room_detail : 会务客房报价
    api_mice_need_rooms : 会务用房需求
    api_mice_need_rooms_detail : 会务用房需求
    api_mice_need_team_building : 团建需求
    api_mice_need_team_building_detail : 会务团建报价
    api_mice_need_transport : 会务旅程交通信息
    api_mice_need_transport_detail : 会务交通票务报价
    api_mice_order_detail_dinners : 会务订单用餐
    api_mice_order_detail_places : 会务订单场地
    api_mice_order_detail_rooms : 会务订单用房
    api_mice_order_hotels : 会务订单酒店关联表
    api_mice_order_image : 会务真实数据图片
    api_mice_orders : 会务订单主表
    api_mice_other_payment : 额外费用
    api_mice_remark : 会务各种主要备注信息
    api_mice_restaurants : 会务编外餐厅
    api_mice_sign : 会务签到表
    api_migrations : 
    api_notices : 
    api_order_daily_statistic : 每日统计表数据
    api_order_status_log : 
    api_order_waimai : 
    api_orders : 
    api_payment_order_export : 支付订单导出表
    api_payment_order_export_log : 支付订单导出日志表
    api_points_log : 用户积分日志表
    api_points_orders : 用户积分订单表
    api_points_product : 积分商品表
    api_points_rule : 用户积分规格表
    api_points_ucenter : 用户积分总表
    api_points_ucenter_detail : 用户积分年度明细表
    api_points_ucenter_receipt : 用户积分兑换收货表
    api_pre_payment_info : 订单提前支付信息
    api_production_group : 产品组负责人关系
    api_production_group_relation : 产品组所属人关系
    api_project : 
    api_qiandao : 
    api_quality_check_rate : 飞检统计
    api_rates : 
    api_recommends_view : VIEW
    api_restaurant_account_records : 餐厅银行账号变更记录表
    api_restaurant_deliver_detail_log : 商户、餐厅迁移日志明细表
    api_restaurant_deliver_log : 商户、餐厅迁移日志表
    api_restaurant_dynamic : VIEW
    api_restaurant_library : 餐厅库表（礼来）
    api_restaurant_master_project : 公海和项目关联表
    api_restaurant_master_sources : restaurants_master id
    api_restaurant_master_tuijian_info : 
    api_restaurant_view : VIEW
    api_restaurants : 
    api_restaurants_errors_config : 餐厅报错配置表
    api_restaurants_errors_detail : 餐厅报错明细表
    api_restaurants_errors_header : 餐厅报错主表
    api_restaurants_master : 
    api_restaurants_view : VIEW
    api_sales : 人员类型
    api_sales_merchants : 
    api_sales_notes : 
    api_sales_rest : 
    api_sales_task : 
    api_sales_third_party : 
    api_sales_visit_logs : 销售拜访客户日志表
    api_salevisit_log : 销售拜访客户日志表
    api_save_view : VIEW
    api_sc_posts : 
    api_search_keywords_statistics : 搜索关键词统计
    api_settlement : 结算打款
    api_test : 
    api_tmp_tuijian_restaurants : 
    api_token_requests : 
    api_tokens : 
    api_trackable_task : 
    api_trackable_task_relation : 餐厅队列任务的关联表
    api_train_rest : 餐厅培训销售表
    api_train_task_results : 培训任务结果表
    api_train_task_rules : 
    api_train_tasks : 
    api_ucb_arc : 优时比ARC编码表
    api_v_get_restaurants_type : VIEW
    api_v_get_visit_restaurants : VIEW
    api_waimai_delivery_rules : 
    api_waimai_menu : 
    api_waimai_menu_item : 
    api_waimai_order_detail : 
    api_waimai_order_items : 
    api_waimai_packing_box_fee : 
    api_waimai_restaurant_category : 
    api_waimai_restaurant_detail : 
    api_waimai_restaurant_notice : 
    api_waimai_shipping_fee : 
    api_waimai_shipping_fee_copy : 
    api_waimai_third_party : 
    api_wx_follow : 商户微信号和手机号的绑定
    api_yuding : 
    aux_2014_dec_orders : VIEW
    aux_2014_dec_orders_grid : VIEW
    duplicate_restaurant_name : 
    import_jnj_sector : 
    import_jnj_user_sector : 
    inn_cache_log : 
    inn_oss_files_mapping : 
    lbs_view : VIEW
    menu_in_all : VIEW
    oauth_access_token_scopes : 
    oauth_access_tokens : 
    oauth_auth_code_scopes : 
    oauth_auth_codes : 
    oauth_client_endpoints : 
    oauth_client_grants : 
    oauth_client_scopes : 
    oauth_clients : 
    oauth_grant_scopes : 
    oauth_grants : 
    oauth_refresh_tokens : 
    oauth_scopes : 
    oauth_session_scopes : 
    oauth_sessions : 
    order_info : VIEW
    qc_version : 数据库版本表
    sequence :
     
* 默沙东数加报表 
    
    sj_company_hospital_restaurant_statistics : 公司医院覆盖餐厅数量统计表
    sj_company_restaurant_source : 公司餐厅来源统计表
    sj_company_restaurant_sources_map : 公司-本公司餐厅来源映射表
    sj_company_restaurants_statistics : 公司餐厅统计表
    sj_company_shelf_restaurant : 公司餐厅下架原因统计表
    sj_fly_check_month_stastic : 飞检月度统计
    sj_order_score_month : 订单评分
    sj_user_cost : 用户数据
    sj_user_cost_city : 用户/消费金额(城市)
    tem_account_id : 
    tem_finish_yue_account : 
    tem_jiesuan_order_bank_info : 
    tem_mc_account_add : 
    tem_mc_account_add_another : 
    tem_restaurant_detail : 
    tem_yue : 
    tem_yue_account : 
    tem_yue_bank_info : 
    tem_yue_finish_bank_info : 
    tem_yue_merchants : 
    tem_yue_order_shanghu : 
    tem_yue_order_shangpu : 
    tem_yue_restaurants : 
    tem_yue_standard : 
    temp_a : 
    temp_apply_recodes : VIEW
    tmp_api_rates : 
    tmp_b : 
    tmp_bank_account : 
    tmp_check_dining : 
    tmp_check_people : 
    tmp_client : 
    tmp_client_query : 
    tmp_division : 
    tmp_division_client : 
    tmp_flycheck_record : 
    tmp_gsk_hosp_0815 : 
    tmp_gsk_hosp_0921 : 
    tmp_hospital1130_new : 
    tmp_inn_client_rest_order_statistic : 
    tmp_inn_order : 
    tmp_inn_train_log : 
    tmp_inn_visit_log : 
    tmp_member : 
    tmp_order_query : 
    tmp_rates_parse : 
    tmp_request_payment : 
    tmp_restaurant : 
    tmp_restaurant813 : 
    tmp_restaurant_query : 
    tmp_restaurant_sources : 
    tmp_standard_rates : 
    tmp_tuijian_restaurants : 
 * 全程客栈视图
 
    v_inn_checkemphasisdining : VIEW
    v_inn_checkemphasisp : VIEW
    v_inn_client_city_user_order_info : VIEW
    v_inn_client_rest_order_statistic : VIEW
    v_inn_client_rest_statistic : VIEW
    v_inn_company_own_rest : VIEW
    v_inn_flycheckrecord : VIEW
    v_inn_is_support_reserve : VIEW
    v_inn_member : VIEW
    v_inn_order_info : VIEW
    v_inn_order_query : VIEW
    v_inn_order_rate : VIEW
    v_inn_order_restaurant_offstaff : VIEW
    v_inn_order_restaurant_waimai : VIEW
    v_inn_order_restaurant_yuding : VIEW
    v_inn_order_statistic_for_client : VIEW
    v_inn_org_for_company : VIEW
    v_inn_quanlity_error_times : VIEW
    v_inn_request_patment_log : VIEW
    v_inn_requestpatment : VIEW
    v_inn_restaurant_average_actual : VIEW
    v_inn_restaurant_company_user_recom : VIEW
    v_inn_restaurant_consume_actual : VIEW
    v_inn_restaurant_gh_statistic : VIEW
    v_inn_restaurant_project : VIEW
    v_inn_restaurant_rate : VIEW
    v_inn_restaurant_recom_email : VIEW
    v_inn_restaurant_resources : VIEW
    v_inn_restaurant_shelf_time : VIEW
    v_inn_restaurant_takeout_type : VIEW
    v_inn_restaurant_user_cost_total : VIEW
    v_inn_restaurant_user_cost_total_max : VIEW
    v_inn_restaurant_user_cost_union : VIEW
    v_inn_sj_order_had_payment : VIEW
    v_inn_train_count : VIEW
    v_inn_train_log : VIEW
    v_inn_user_restaurant_exp_max_total_3_month : VIEW
    v_inn_user_restaurant_expense_3_month : VIEW
    v_inn_visit_count : VIEW
    v_inn_visit_log : VIEW
    
    v_total_advance_restaurants : VIEW
    v_total_advance_restaurants_detail : VIEW
    v_total_nopay_restaurants : VIEW
    v_total_nopay_restaurants_detail : VIEW
    v_total_online_restaurants : VIEW
    v_total_online_restaurants_detail : VIEW
    v_total_period_restaurants : VIEW
    v_total_period_restaurants_detail : VIEW
    v_total_rate_restaurants : VIEW
    v_total_reserve_restaurants : VIEW
    v_total_reserve_restaurants_detail : VIEW
    v_total_takeaway_restaurants : VIEW
    v_total_takeaway_restaurants_detail : VIEW
    view_jiesuan_order : VIEW
    view_y_orders : VIEW
    wx_follow : 商户微信号和手机号的绑定
