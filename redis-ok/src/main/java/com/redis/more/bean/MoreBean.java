package com.redis.more.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11273 on 2018/1/9.
 */
@Configuration
public class MoreBean {
//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;
    private MoreBean.Pool pool;

    private final static Map<String,RedisTemplate<String,Object>> redisTemplateMap = new HashMap<String,RedisTemplate<String,Object>>();

    @Bean("redis--0")
    public RedisTemplate<String,Object> redisTemplate01(){
        JedisShardInfo jedisShardInfo = new JedisShardInfo("http://" + "000" + ":" + "00" + "/" + 0);
        jedisShardInfo.setPassword("pas");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setShardInfo(jedisShardInfo);
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setTimeout(1000);
        pool = new Pool(1,2, 3, 400L);
        jedisConnectionFactory.setPoolConfig(pool);
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplateMap.put("redis--0",redisTemplate);
        return redisTemplate;
    }
    @Bean("redis--1")
    public RedisTemplate<String,Object> redisTemplate02(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        JedisShardInfo jedisShardInfo = new JedisShardInfo("http://" + "001" + ":" + "01" + "/" + 1);
        jedisShardInfo.setPassword("pas111");
        jedisConnectionFactory.setShardInfo(jedisShardInfo);
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setTimeout(1000);
        pool = new Pool(10,20, 30, 4000L);
        jedisConnectionFactory.setPoolConfig(pool);
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplateMap.put("redis--1",redisTemplate);
        return redisTemplate;
    }

    public  Map<String,RedisTemplate<String,Object>> getRedisTemplateMap(){
        return redisTemplateMap;
    }

    public static class Pool extends JedisPoolConfig {
        private int maxIdle = 8;
        private int minIdle = 0;
        private int maxActive = 8;
        private Long maxWait = -1L;

        public Pool() {
        }

        public Pool(int maxIdle, int minIdle, int maxActive, Long maxWait) {
            this.maxIdle = maxIdle;
            this.minIdle = minIdle;
            this.maxActive = maxActive;
            this.maxWait = maxWait;
        }

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        @Override
        public int getMaxTotal() {
            return this.maxActive;
        }

        @Override
        public long getMaxWaitMillis() {
            return this.maxWait;
        }

        public void setMaxWait(Long maxWait) {
            this.maxWait = maxWait;
        }
    }
}
