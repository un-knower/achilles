package com.quancheng.achilles.service.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages="com.quancheng.achilles.dao.ds_st.repository", 
        entityManagerFactoryRef = "qcWriterManagerFactory",
        transactionManagerRef = "qcWriterTransactionManager")
@MapperScan(basePackages = { "com.quancheng.achilles.dao.ds_st.mapper" }, sqlSessionFactoryRef = "statisticsSqlSessionFactory")
public class DBDataServiceStatisticJPAConfiguration {

}
