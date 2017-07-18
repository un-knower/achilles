--- 组织架构
CREATE TABLE qc_user_original (
    uid BIGINT COMMENT 'member表uid',
    client_id BIGINT COMMENT '企业id',
    city_id BIGINT COMMENT '城市id',
    user_status STRING COMMENT '用户状态',
    branch STRING COMMENT '子公司',
    branch_id BIGINT COMMENT '子公司id',
    businessunit STRING COMMENT '事业部',
    businessunit_id BIGINT COMMENT '事业部id',
    sector STRING COMMENT '部门',
    sector_id BIGINT COMMENT '部门id',
    region STRING COMMENT '大区',
    region_id BIGINT COMMENT '大区id',
    productgroup STRING COMMENT '产品组',
    productgroup_id BIGINT COMMENT '产品组id',
    costcenter STRING COMMENT '成本中心',
    costcenter_id BIGINT COMMENT '成本中心id'
)
LIFECYCLE 100000;