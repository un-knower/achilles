package com.quancheng.achilles.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.modelwrite.QCClient;
import com.quancheng.achilles.dao.write.QCClientRepository;

@Service
public class QCClientServiceImpl extends RestaurantServiceAbstract<QCClient>{
    @Autowired
    private QCClientRepository qcClientReposi;
    public Page<QCClient> pageQuery(QCClient qcc ,String[] citys,Pageable pageable){
        Specifications<QCClient> specif= Specifications.where( equal("clientName", qcc.getClientName() )) 
                .and(in("city",citys));
        return qcClientReposi.findAll(specif,pageable);
    }
}
