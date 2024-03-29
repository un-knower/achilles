package com.quancheng.achilles.dao.ds_qc.repository;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.quancheng.achilles.dao.ds_st.model.DataItemDetail;
@Repository
public class DataItemDetailRep {

    @Resource(name="writeSqlSession")
    private SqlSession sqlSession;

    public List<DataItemDetail> queryDataDetails(Map<String, Object> map) {
        return sqlSession.selectList("selectItemDetailByView", map);
    }
}
