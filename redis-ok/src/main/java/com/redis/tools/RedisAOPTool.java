package com.redis.tools;

import com.redis.bean.RedisMoreBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Xuzz on 2017/12/25.
 * redis aop 自动切换数据源（db）
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RedisAOPTool {

    @Resource(name = "redisMordBean")
    private RedisMoreBean redisMoreBean;

    //方法执行前搞定
    @Before("execution(* com.redis.controller.RedisController.*(..)) && args(code)")
    public void redisAOP(Long code){
        if (code==100L){
            System.out.println("切换到100");

        }else if (code==200L){
            System.out.println("切换到200");

        }
    }

//    @Around(value = "redisAOP()" )
    public void changeDB(Long code){
        if (code==100L){
            System.out.println("切换到100");
        }else if (code==200L){
            System.out.println("切换到200");
        }
    }
}
