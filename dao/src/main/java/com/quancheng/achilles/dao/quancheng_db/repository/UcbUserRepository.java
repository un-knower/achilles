package com.quancheng.achilles.dao.quancheng_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.quancheng_db.model.UcbUser;

public interface UcbUserRepository extends JpaRepository<UcbUser, Long>, JpaSpecificationExecutor<UcbUser> {
    
    
}
