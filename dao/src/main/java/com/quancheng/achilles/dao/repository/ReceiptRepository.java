package com.quancheng.achilles.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.model.InputReceipt;
public interface ReceiptRepository extends JpaRepository<InputReceipt, String>, JpaSpecificationExecutor<InputReceipt> {
}
