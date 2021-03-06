package com.redis.test;
import com.redis.bean.RedisMoreBean;
import com.redis.bean.RedisMoreBeanImpl;
import com.redis.more.bean.MorBeanForJavaCode;
import com.redis.more.bean.MoreBean;
import org.apache.commons.pool2.PooledObjectFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xuzz on 2017/12/14.
 * 测试redis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisJunit {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MoreBean moreBean;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void save(){

        stringRedisTemplate.opsForValue().set("name1","test1");
        stringRedisTemplate.opsForValue().set("name2","test1");
        System.err.println("----------------");
        System.err.println("获取结果："+stringRedisTemplate.opsForValue().get("name1"));
    }
    @Test
    public void del(){
        stringRedisTemplate.delete("name");
        System.err.println("----------------");
        System.err.println("获取结果："+stringRedisTemplate.opsForValue().get("name"));
    }


    //设置缓存时间
    @Test
    public void cachaTime(){
        //保存20秒
        stringRedisTemplate.opsForValue().set("test","20秒",20, TimeUnit.SECONDS);
    }

    /**
     * 删除当先数据库所有数据
     */
    @Test
    public void flushDb(){
       redisTemplate.execute(new RedisCallback() {
           @Override
           public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
               redisConnection.flushDb();
               return 200;
           }
       });
    }


    @Resource
    private RedisMoreBean redisMoreBean;

    @Test
    public void testMOre(){
        redisTemplate =  redisMoreBean.createRedisTemplate(2);
        redisTemplate.opsForValue().set("xxx","yyyy");
        redisTemplate.opsForValue().set("zz","yyyy");
        redisTemplate.opsForValue().set("dd","yyyy");
        redisTemplate.opsForValue().set("ff","yyyy");

    }

    @Test
    public void getC(){
        Map<String,RedisTemplate<String,Object>> redisTemplateMap = moreBean.getRedisTemplateMap();
    }
    @Autowired
    private MorBeanForJavaCode morBeanForJavaCode;
    @Test
    public void getCJavaCode(){
        Map<String,RedisTemplate<String,Object>> redisTemplateMap = morBeanForJavaCode.getRedisTemplateMap();
    }
}
