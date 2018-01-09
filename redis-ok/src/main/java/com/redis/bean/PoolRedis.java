package com.redis.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 11273 on 2018/1/8.
 */
@Configuration
public class PoolRedis extends JedisPoolConfig{
    @Bean("poolRedis")
    @ConditionalOnMissingBean
    public PoolRedis PoolRedis() {
        return new PoolRedis();
    }
}
