package com.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Xuzz on 2017/12/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisJunit {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void save(){
        stringRedisTemplate.opsForValue().set("name","test");
        System.err.println("----------------");
        System.err.println("获取结果："+stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void del(){
        stringRedisTemplate.delete("name");
        System.err.println("----------------");
        System.err.println("获取结果："+stringRedisTemplate.opsForValue().get("name"));
    }
}
