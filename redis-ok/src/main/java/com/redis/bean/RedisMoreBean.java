package com.redis.bean;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by 11273 on 2018/1/8.
 */
public interface RedisMoreBean {
    public RedisTemplate createRedisTemplate(Integer dbIndex);
}
