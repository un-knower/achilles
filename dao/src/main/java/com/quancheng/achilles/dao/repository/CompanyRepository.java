package com.quancheng.achilles.dao.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.quancheng.achilles.dao.model.CompanyDomain;

/**
 * @author liujiejian
 * @version 2016年9月12日
 */
@Repository
public class CompanyRepository {

    @Autowired
    private SqlSession sqlSession;

    public List<CompanyDomain> getAllCompany() {
        return sqlSession.selectList("queryAllCompany");
    }

    public CompanyDomain getCompanyById(Integer id) {
        return sqlSession.selectOne("queryCompanyById", id);
    }
}
