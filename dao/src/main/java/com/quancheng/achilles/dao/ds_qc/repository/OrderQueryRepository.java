package com.quancheng.achilles.dao.ds_qc.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class OrderQueryRepository {
//    @Resource(name="writeSqlSession")
//    private SqlSession sqlSession;
//    
//    @Resource(name="readSqlSession")
//    private SqlSession readSqlSession;
    
//    public List<Map<String,Object>> queryPageData(Map<String,Object> sql){
//        return sqlSession.selectList("queryPageData", sql);                
//    }
//    public Long queryPageDataCount(Map<String,Object> sql){
//        return sqlSession.selectOne("queryPageDataCount", sql);                
//    }
//    public List<Map<String,Object>>  queryPageDataLimit(Map<String,Object> sql){
//        return sqlSession.selectList("queryPageDataLimit", sql);
//    }
    
    
    
    public List<Map<String,Object>> queryPageData(Map<String,Object> sql,SqlSession ds){
        return ds.selectList("queryPageData", sql);                
    }
    public Long queryPageDataCount(Map<String,Object> sql,SqlSession ds){
        return ds.selectOne("queryPageDataCount", sql);                
    }
    public List<Map<String,Object>>  queryPageDataLimit(Map<String,Object> sql,SqlSession ds){
        return ds.selectList("queryPageDataLimit", sql);
    }
}
