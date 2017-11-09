package com.quancheng.achilles.dao.ds_st.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.ds_st.model.OssFileInfo;

import java.util.List;

public interface OssFileInfoRepository extends JpaRepository<OssFileInfo, Long>, JpaSpecificationExecutor<OssFileInfo> {
    @Query("select t from OssFileInfo t where deletedAt IS NULL and userName= :username order by createdAt desc")
    List<OssFileInfo> findAllByUserName(@Param("username") String username);

    @Query("select t from OssFileInfo t where deletedAt IS NULL and userName= :username order by createdAt desc")
    Page<OssFileInfo> findLatest10ByUserName(@Param("username") String username, Pageable pageable);
}
