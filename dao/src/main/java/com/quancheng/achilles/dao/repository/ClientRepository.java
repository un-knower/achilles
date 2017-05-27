package com.quancheng.achilles.dao.repository;

import com.quancheng.achilles.dao.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by XZW on 2016/9/10 0010.
 */
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select c.title from Client c where status=1")
    List<String> listAllCompany();
    
    @Query("select c.id ,c.title from Client c where status=1")
    List<Client> listAllClient();
}
