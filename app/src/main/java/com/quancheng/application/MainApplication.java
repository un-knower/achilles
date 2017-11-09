package com.quancheng.application;

import java.net.InetAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * 主入口
 *
 * @author joe
 */
@ComponentScan({"com.quancheng.application", "com.quancheng.application.api", "com.quancheng.*.service",
        "com.quancheng.*.service.view", "com.quancheng.*.service.api", "com.quancheng.service.*",
        // dao
        "com.quancheng.achilles.dao.*.repository", "com.quancheng.achilles.dao.write",
        "com.quancheng.shared.*", "com.quancheng.saluki.monitor.web"})
@ImportResource({"classpath*:quancheng-app-*.xml"})
@SpringBootApplication
public class MainApplication {
    static{
        System.setProperty("APP_NAME", "achilles-service");
    }
    public static void main(String[] args) {
        if (System.getProperty("SALUKI_GRPC_HOST") == null) {
            System.setProperty("SALUKI_GRPC_HOST", getLocal());
        }
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * 获取本机的name
     *
     * @return name
     */
    public static String getLocal() {
        String name;
        try {
            /** 返回本地主机。 */
            InetAddress addr = InetAddress.getLocalHost();
            /** 返回 IP 地址字符串（以文本表现形式） */
            name = addr.getHostAddress();
        } catch (Exception ex) {
            name = "127.0.0.1";
        }
        System.out.println("start server in " + name);
        return name;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
