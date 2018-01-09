package com.redis.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;


/**
 * Created by Xuzz on 2017/12/25.
 */
//@Configuration
@Service
public class RedisMoreBeanImpl implements RedisMoreBean{
    @Resource
    private  JedisConnectionFactory jedisConnectionFactory ;

    @Resource
    private  RedisTemplate redisTemplate;

    @Resource(name = "poolRedis")
    private PoolRedis poolRedis;

    @Override
    public  RedisTemplate createRedisTemplate(Integer dbIndex){
        if (dbIndex==null){
            dbIndex=0;
        }
        jedisConnectionFactory.setDatabase(dbIndex);
        JedisShardInfo jedisShardInfo = new JedisShardInfo("192.168.0.120");
        jedisShardInfo.setPassword("redis");
        jedisConnectionFactory.setShardInfo(jedisShardInfo);
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setTimeout(10000);
        poolRedis.setMaxWaitMillis(60000);
        poolRedis.setMaxIdle(10);
        poolRedis.setMinIdle(8);
        poolRedis.setMaxTotal(200);
        jedisConnectionFactory.setPoolConfig(poolRedis);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

}
