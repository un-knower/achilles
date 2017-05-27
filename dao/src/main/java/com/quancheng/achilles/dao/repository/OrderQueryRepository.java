package com.quancheng.achilles.dao.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.quancheng.achilles.dao.model.OrderRecordVo;

/**
 * @author liujiejian
 * @version 2016年9月10日
 */
@Repository
public class OrderQueryRepository {

    @Autowired
    private SqlSession sqlSession;

    public List<OrderRecordVo> queryPageOrders(Map<String, Object> map) {
        return sqlSession.selectList("queryPageOrders", map);
    }

    public Long queryPageOrdersCount(Map<String, Object> map) {
        return sqlSession.selectOne("queryPageOrdersCounts", map);
    }
}
