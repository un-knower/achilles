package com.quancheng.achilles.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.quancheng.achilles.dao.model.QCClient;

public interface QCClientRepository extends JpaRepository<QCClient, Long>,JpaSpecificationExecutor<QCClient> {
	@Query("select t from QCClient t ")
	Page<QCClient> findAllWithinTimeRange( Pageable o);
 
}
