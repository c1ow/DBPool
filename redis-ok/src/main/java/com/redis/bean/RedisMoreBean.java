package com.redis.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * Created by Xuzz on 2017/12/25.
 */
@Configuration
public class RedisMoreBean {

    @Value("${redis-ok.host}")
    private String host;

    @Value("${redis-ok.password}")
    private String password;

    private int port = 6379;

    @Value("${redis-ok.timeout}")
    private int timeout;


    @Bean(name = "redisok01")
    public StringRedisTemplate stringRedisTemplateR1(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory(0,host,password,timeout,port));
        return stringRedisTemplate;
    }
    @Bean(name = "redisok02")
    public StringRedisTemplate stringRedisTemplateR2(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory(1,host,password,timeout,port));
        return stringRedisTemplate;
    }

    public RedisConnectionFactory connectionFactory(int database,String host,String password,int timeout,int port ){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setDatabase(database);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(timeout);
        RedisConnectionFactory redisConnectionFactory = jedisConnectionFactory;
        return redisConnectionFactory;
    }
}
