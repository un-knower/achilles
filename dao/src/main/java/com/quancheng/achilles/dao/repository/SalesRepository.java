package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.quancheng.achilles.dao.model.SalesDomain;

/**
 * @author liujiejian
 * @version 2016年9月12日
 */
@Repository
public class SalesRepository {

    @Autowired
    private SqlSession sqlSession;

    public List<SalesDomain> getAllSales() {
        return sqlSession.selectList("queryAllSales");
    }

    public SalesDomain getSalesById(Integer id) {
        return sqlSession.selectOne("getSalesById", id);
    }
}
