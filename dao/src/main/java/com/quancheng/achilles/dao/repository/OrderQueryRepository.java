package com.quancheng.achilles.dao.repository;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.quancheng.achilles.dao.model.OrderRecordVo;

/**
 * @author liujiejian
 * @version 2016年9月10日
 */
@Repository
public class OrderQueryRepository {

    @Resource(name="writeSqlSession")
//    @Autowired
    private SqlSession sqlSession;

    public List<OrderRecordVo> queryPageOrders(Map<String, Object> map) {
        return sqlSession.selectList("queryPageOrders", map);
    }

    public Long queryPageOrdersCount(Map<String, Object> map) {
        return sqlSession.selectOne("queryPageOrdersCounts", map);
    }
    
    public List<Map<String,Object>> queryPageData(Map<String,Object> sql){
        return sqlSession.selectList("queryPageData", sql);                
    }
    
    public Long queryPageDataCount(Map<String,Object> sql){
        return sqlSession.selectOne("queryPageDataCount", sql);                
    }
}
