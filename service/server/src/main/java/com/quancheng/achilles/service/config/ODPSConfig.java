package com.quancheng.achilles.service.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aliyun.odps.Odps;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
@Configuration
@EnableConfigurationProperties({ODPSProperties.class})
public class ODPSConfig {
    
    @Autowired
    @Bean(name="odps_source")
    public Odps config(ODPSProperties properties){
        Account account = new AliyunAccount(properties.getAccessId(), properties.getAccessKey());
        Odps odps = new Odps(account);
        odps.setEndpoint(properties.getEndPoint());
        odps.setDefaultProject(properties.getProjectName());
        return odps;
    }
}
