package com.quancheng.achilles.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
@Component
public class RedisComponent {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }
}
