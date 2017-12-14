package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Xuzz on 2017/12/14.
 * redis 服务启动
 */
@SpringBootApplication
public class RedisAPP {
    public static void main(String[] args) {
        SpringApplication.run(RedisAPP.class,args);
    }
}
