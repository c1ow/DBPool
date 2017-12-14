package com.redis.tools;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * Created by Xuzz on 2017/12/14.
 * redis 配置 缓存
 */
public class RedisConfig extends CachingConfigurerSupport{
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(o.getClass().getName());
                stringBuilder.append(method.getName());
                for (Object object : objects){
                    stringBuilder.append(object.toString());
                }
                return stringBuilder.toString();
            }
        };
    }
}
