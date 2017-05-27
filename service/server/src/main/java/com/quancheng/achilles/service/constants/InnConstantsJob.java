package com.quancheng.achilles.service.constants;

public interface InnConstantsJob {
    public final static Integer JOB_ORDERLOGID        = 1;
    public final static Integer JOB_RESTAURANTLOGID   = 2;
    public final static Integer JOB_FLYCHECKRLOGID    = 3;
    public final static Integer JOB_CUSTOMERSERVICEID = 4;
    public final static Integer JOB_MEMBERRELOGID     = 5;
    public final static Integer JOB_CLIENT_LOGID     = 6;
    public final static Integer JOB_TRAIN_LOG_ID     = 7;
    public final static Integer JOB_VISIT_LOG_ID     = 8;
    public static Integer[] IDS={
            JOB_ORDERLOGID,
            JOB_RESTAURANTLOGID,
            JOB_FLYCHECKRLOGID,
            JOB_CUSTOMERSERVICEID,
            JOB_MEMBERRELOGID,
            JOB_CLIENT_LOGID,
            JOB_TRAIN_LOG_ID,
            JOB_VISIT_LOG_ID
            };
    
    public final static String SJ_TITLE1="用户数/活跃用户/订单数/消费金额";
	public final static String SJ_TITLE2="各城市(活跃用户/消费金额)";
	public final static String SJ_TITLE3="月度飞检情况汇总";
	public final static String SJ_TITLE4="订单评价情况";
}
