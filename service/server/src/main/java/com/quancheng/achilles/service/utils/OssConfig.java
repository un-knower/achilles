package com.quancheng.achilles.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

@Service
public class OssConfig {
    @Value("${oss.connectionTimeout}")
     int connectionTimeout;
    @Value("${oss.socketTimeout}")
     int socketTimeout;
    @Value("${oss.smaxErrorRetry}")
     int maxErrorRetry;
    @Value("${oss.maxConnections}")
     int maxConnections;
    @Value("${oss.endpoint}")
     String endpoint;
    @Value("${oss.accessKeyId}")
     String accessKeyId;
    @Value("${oss.accessKeySecret}")
     String accessKeySecret;
    @Value("${oss.bucketName}")
    public  String bucketName;
    @Value("${oss.fileExpireTimeLong}")
    public  String fileExpireTimeLong;
    @Value("${oss.fileExpireTimeUnit}")
    public  String fileExpireTimeUnit;
    public  OSSClient createClient(){
        return new OSSClient(endpoint,accessKeyId,accessKeySecret,createConfig());
    }
    private final  ClientConfiguration createConfig(){
        ClientConfiguration cfg = new ClientConfiguration();
        cfg.setConnectionTimeout(connectionTimeout);
        cfg.setSocketTimeout(socketTimeout);
        cfg.setMaxErrorRetry(maxErrorRetry);
        cfg.setMaxConnections(maxConnections);
        return cfg;
    }
}
