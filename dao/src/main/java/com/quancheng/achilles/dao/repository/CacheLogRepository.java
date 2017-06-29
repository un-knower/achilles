package com.quancheng.achilles.dao.repository;

import java.util.Date;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
/**
 * @author liujiejian
 * @version 2016年9月10日
 */
@Repository
public class CacheLogRepository {

    @Qualifier("writeSqlSession")
    @Autowired
    private SqlSession           sqlSession;

    public Date getRefreshTimeById(Integer id) {
        return (Date) sqlSession.selectOne("getUpdateTimeByCacheId", id);
    }

    public Date doRefreshById(Integer id) {
        if (id == 1) {
            sqlSession.update("dropOrderTemptable");
            sqlSession.update("insertOrderTemptable");
        }
        if (id == 2) {
            sqlSession.update("dropRestaurantTemptable");
            sqlSession.update("insertNewRestaurantTemptable");
        }
        if (id == 3) {
            sqlSession.update("dropCheckPelpleTemptable");
            sqlSession.update("createNewCheckPeopleTemptable");
            sqlSession.update("dropCheckDiningTemptable");
            sqlSession.update("createNewCheckDiningTemptable");
            sqlSession.update("dropCheckRecordTemptable");
            sqlSession.update("createNewCheckRecordTemptable");
        }
        if (id == 4) {
            sqlSession.update("dropCustomerServiceTemptable");
            sqlSession.update("createNewCustomerServiceTemptable");
        }
        if (id == 5) {
            sqlSession.update("dropMemberTemptable");
            sqlSession.update("createNewMemberTemptable");
        }
        if (id == 6) {
            sqlSession.update("dropPrevClientTemptable");
            sqlSession.update("insertPrevNewClientTemptable");
            sqlSession.update("dropClientTemptable");
            sqlSession.update("insertNewClientTemptable");
        }
        if (id == 7) {
            sqlSession.update("dropTrainLogTemptable");
            sqlSession.update("createNewTrainLogTemptable");
        }
        
        if (id == 8) {
            sqlSession.update("dropVisitLogTemptable");
            sqlSession.update("insertVisitLogTemptable");
//            doinsert("insertVisitLogTemptable");
        }
        sqlSession.update("updateInnCacheLog", id);
        return getRefreshTimeById(id);
    }
}
