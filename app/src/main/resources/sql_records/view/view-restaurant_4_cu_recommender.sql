CREATE OR REPLACE VIEW v_inn_restaurant_company_user_recom AS
 
SELECT
    uuid() as id,
    fk_citys.`name`   AS city_name,
    CONVERT(app_recomm.`name`  USING utf8) AS restaurant_name ,
    fk_areas.`name`   AS area_name,
    CONVERT(app_recomm.address USING utf8) as restaurant_address,
    'APP' AS recommand_method,
    CONVERT(ac_recomm.`name` USING utf8)  AS recommand_user,
    CONVERT(ac_recomm.email USING utf8)  as email,
    fk_member.job_num,
    fk_client.title AS recommend_company,
    recommed_record.created_at,
    CONVERT(app_recomm.source_id USING utf8) as source_id
FROM
    api_client_recommand_records recommed_record
LEFT JOIN api_client_recommand_rest app_recomm ON recommed_record.reco_rest_id = app_recomm.id
LEFT JOIN api_client_recommander ac_recomm ON ac_recomm.id = recommed_record.recommander_id
LEFT JOIN 16860_member fk_member ON fk_member.uid = ac_recomm.member_uid
LEFT JOIN 16860_region fk_citys ON app_recomm.region_id = fk_citys.id
LEFT JOIN 16860_region fk_areas ON app_recomm.area_id = fk_areas.id
LEFT JOIN 16860_client fk_client ON app_recomm.client_id = fk_client.id
 
UNION ALL
 SELECT
        uuid() as id,
        fk_citys.`name` AS city_name,
        offer_recomm.restaurant_name AS restaurant_name ,
        fk_areas.`name` AS area_name,
        offer_recomm.restaurant_address,
        '编外推荐' AS recommand_method,
        fk_member.realname AS recommand_user,
        168ucenter.email as email,
        fk_member.job_num as job_num,
        fk_client.title AS recommend_company,
        offer_recomm.create_time as created_at ,
        offer_recomm.source_id
    FROM
        16860_offstaff_order offer_recomm
    LEFT JOIN 16860_member fk_member ON fk_member.uid = offer_recomm.user_id
        LEFT JOIN 16860_ucenter 168ucenter ON 168ucenter.id=offer_recomm.user_id
    LEFT JOIN 16860_region fk_citys ON fk_citys.id = offer_recomm.city_id
    LEFT JOIN 16860_region fk_areas ON offer_recomm.area_id = fk_areas.id
    LEFT JOIN 16860_client fk_client ON fk_client.id = fk_member.cid
    where offer_recomm.need_recommend='1';