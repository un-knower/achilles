package com.quancheng.achilles.service.config;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.quancheng.achilles.dao.model.CallRecord;
import com.quancheng.achilles.dao.modelwrite.OssFileInfo;

@Configuration
public class DBConfiguration {

    @Primary
    @Bean(name="quanchengDBReadDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.readonly")
    public DataSource dataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
    
    @Primary
	@Bean
	public SqlSessionFactoryBean readSqlSessionFactoryBean(@Qualifier("quanchengDBReadDataSource")DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-read-config.xml"));
		return sqlSessionFactory;
	}
	
	@Bean
	@Primary
	public SqlSession readSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	

	
	@Bean(name="writeSqlSession") 
	public SqlSession sqlSessionTemplate(@Qualifier("writeSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
    @Bean(name="quanchengDBWriteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.writedb")
    public DataSource quanchengDBWriteDataSource() {
	    DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
    
    @Bean(name="writeSqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("quanchengDBWriteDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-write-config.xml"));
        return sqlSessionFactory;
    }
    
    /**
     * QC WRITER
     * @param builder
     * @return
     */
    @Bean(name = "qcWriterManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaWriterManagerFactory(
            @Qualifier("quanchengDBWriteDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages(OssFileInfo.class).persistenceUnit("qc_write_db").build();
    }
    @Bean(name="qcWriterTransactionManager")
    public JpaTransactionManager jpaWriterTransactionManager(
            @Qualifier("qcWriterManagerFactory")LocalContainerEntityManagerFactoryBean builder) {
        return new JpaTransactionManager( builder .getObject());
    }
    /**
     * QC READ
     * @param builder
     * @return
     */
	@Primary
    @Bean(name = "qcReaderManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaReaderManagerFactory(
            @Qualifier("quanchengDBReadDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
	    LocalContainerEntityManagerFactoryBean loefb =   builder.dataSource(dataSource).packages( CallRecord.class).persistenceUnit("qc_read_db").build();
	    loefb.setSharedCacheMode(SharedCacheMode.NONE);
	    return loefb;
    }
	@Primary
    @Bean(name="qcReaderTransactionManager")
    public JpaTransactionManager jpaReaderTransactionManager(@Qualifier("qcReaderManagerFactory")LocalContainerEntityManagerFactoryBean builder) {
        return new JpaTransactionManager(builder.getObject());
    }
}
