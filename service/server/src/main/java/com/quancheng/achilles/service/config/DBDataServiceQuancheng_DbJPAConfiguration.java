package com.quancheng.achilles.service.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages="com.quancheng.achilles.dao.ds_qc.repository", 
        entityManagerFactoryRef = "qcReaderManagerFactory",
        transactionManagerRef = "qcReaderTransactionManager")
public class DBDataServiceQuancheng_DbJPAConfiguration {

}
