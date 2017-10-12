package com.quancheng.achilles.service.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages="com.quancheng.achilles.dao.quancheng_db.repository", 
        entityManagerFactoryRef = "quanchengOnLineManagerFactory",
        transactionManagerRef = "quanchengOnLineTransactionManager")
public class DBQuancheng_dbOLWriteJPAConfiguration {

}
