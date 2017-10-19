package com.quancheng.achilles.service.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import com.github.pagehelper.PageHelper;
import com.quancheng.achilles.dao.model.Region;
import com.quancheng.achilles.dao.modelwrite.OssFileInfo;
import com.quancheng.achilles.dao.quancheng_db.model.UcbUser;

@Configuration
public class DBConfiguration {
    /**数据平台数据库实例数据源*/
    /*=====ds read begin======*/
    @Primary
    @Bean(name = "quanchengDBReadDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.readonly")
    public DataSource dataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }

    @Primary
    @Bean
    public SqlSessionFactoryBean readSqlSessionFactoryBean(@Qualifier("quanchengDBReadDataSource") DataSource dataSource) {
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
    /*=====ds read end======*/
    /*=====ds write begin======*/
 

    @Bean(name = "quanchengDBWriteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.writedb")
    public DataSource quanchengDBWriteDataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }

    @Bean(name = "writeSqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("quanchengDBWriteDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-write-config.xml"));
        return sqlSessionFactory;
    }
    @Bean(name = "writeSqlSession")
    public SqlSession sqlSessionTemplate(@Qualifier("writeSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    /*=====ds write end======*/
    // ---------------------------------------------

    @Bean(name = "statisticsSqlSessionFactoryBean")
    public SqlSessionFactoryBean statisticsSqlSessionFactoryBean(@Qualifier("quanchengDBWriteDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        // new Resource[] { new ClassPathResource("aaa-sqlmap.xml") }
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 将加载多个绝对匹配的所有Resource
        // 将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分
        // 然后进行遍历模式匹配
        Resource[] resources = null;
        try {
            resources = resolver.getResources("classpath:/sqlmapper/*-sqlmap.xml");
            sqlSessionFactory.setMapperLocations(resources);
        } catch (IOException e) {
            System.err.println("statisticsSqlSessionFactoryBean have a error " + e.getMessage());
        }
        sqlSessionFactory.setTypeAliasesPackage("com.quancheng.achilles.dao.model");
        // 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        props.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(props);
        // 添加插件
        sqlSessionFactory.setPlugins(new Interceptor[] { pageHelper });
        return sqlSessionFactory;
    }

    @Bean(name = "statisticsSqlSessionTemplate")
    public SqlSession statisticsSqlSessionTemplate(@Qualifier("statisticsSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // ---------------------------------------------
    /**
     * QC WRITER
     * 
     * @param builder
     * @return
     */
    @Bean(name = "qcWriterManagerFactory")

    public LocalContainerEntityManagerFactoryBean jpaWriterManagerFactory(@Qualifier("quanchengDBWriteDataSource") DataSource dataSource,
                                                                          EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages(OssFileInfo.class).persistenceUnit("qc_write_db").build();
    }

    @Bean(name = "qcWriterTransactionManager")
    public JpaTransactionManager jpaWriterTransactionManager(@Qualifier("qcWriterManagerFactory") LocalContainerEntityManagerFactoryBean builder) {
        return new JpaTransactionManager(builder.getObject());
    }

    /**
     * QC READ
     * 
     * @param builder
     * @return
     */
    @Primary
    @Bean(name = "qcReaderManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaReaderManagerFactory(@Qualifier("quanchengDBReadDataSource") DataSource dataSource,
                                                                          EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean loefb = builder.dataSource(dataSource).packages(Region.class).persistenceUnit("qc_read_db").build();
        loefb.setSharedCacheMode(SharedCacheMode.NONE);
        return loefb;
    }

    @Primary
    @Bean(name = "qcReaderTransactionManager")
    public JpaTransactionManager jpaReaderTransactionManager(@Qualifier("qcReaderManagerFactory") LocalContainerEntityManagerFactoryBean builder) {
        return new JpaTransactionManager(builder.getObject());
    }
    
    /**生产环境数据库实例数据源 quancheng_db*/
    @Bean(name = "quanchengOnlineDBWriteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.online.quancheng_db")
    public DataSource quanchengDBOnlineDataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
    
    @Bean(name = "quanchengOnLineManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaQuanchengOnlineManagerFactory(@Qualifier("quanchengOnlineDBWriteDataSource") DataSource dataSource,
                                                                          EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages(UcbUser.class).persistenceUnit("quancheng_db_write_db").build();
    }

    @Bean(name = "quanchengOnLineTransactionManager")
    public JpaTransactionManager jpaQuanchengOnlineTransactionManager(@Qualifier("quanchengOnLineManagerFactory") LocalContainerEntityManagerFactoryBean builder) {
        return new JpaTransactionManager(builder.getObject());
    }
}
