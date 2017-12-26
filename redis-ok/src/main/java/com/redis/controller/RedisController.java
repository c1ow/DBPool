package com.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xuzz on 2017/12/14.
 * redis 测试
 */
@RestController
@RequestMapping("/redis/ok/")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("code")
    public void getCOde(@RequestParam("code") Long code){
        System.err.println("================");
        stringRedisTemplate.opsForValue().set("name1","test1");
        stringRedisTemplate.opsForValue().set("name2","test1");
        System.err.println("----------------");
        System.err.println("获取结果："+stringRedisTemplate.opsForValue().get("name1"));
    }
}
