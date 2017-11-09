package com.quancheng.achilles.dao.ds_qc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.ds_qc.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {

    @Query("select id,regionName  from Region  t where type=1")
    List<Region> listAllCity();

    @Query("select t  from Region  t where type=1 and level=:level and status=:status")
    List<Region> findRegionsByStatusByLevel(@Param("status") int status, @Param("level") int level);


}
