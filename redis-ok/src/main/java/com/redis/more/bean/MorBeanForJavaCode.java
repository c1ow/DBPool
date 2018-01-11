package com.redis.more.bean;

import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 纯代码生成javaBean
 */
@Component
public class MorBeanForJavaCode implements ApplicationContextAware,ApplicationListener{
    private final static Map<String,RedisTemplate<String,Object>> redisTemplateMap = new HashMap<String,RedisTemplate<String,Object>>();

    private MoreBean.Pool pool;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    public  Map<String,RedisTemplate<String,Object>> getRedisTemplateMap(){
        return redisTemplateMap;
    }
    /*******************************************************/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent){
            try{
                forCirculationCreateJavaBean();
            }catch (Exception e){
                System.err.println(":"+e.getLocalizedMessage());
                System.err.println("==:"+e.getMessage());
                System.err.println("创建失败");
            }
        }
    }
    /*******************************************************/
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


    public void createJavaCode(Integer index){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getAutowireCapableBeanFactory();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        JedisShardInfo jedisShardInfo = new JedisShardInfo("http://" + "001" + ":" + "01" + "/" + index);
        jedisShardInfo.setPassword("pas111");
        jedisConnectionFactory.setShardInfo(jedisShardInfo);
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setTimeout(1000);
        pool = new MoreBean.Pool(10,20, 30, 4000L);
        jedisConnectionFactory.setPoolConfig(pool);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RedisTemplate.class);
        beanDefinitionBuilder.getBeanDefinition().setAttribute("id","xuzz"+index);
        beanDefinitionBuilder.addPropertyValue("connectionFactory",jedisConnectionFactory);
        defaultListableBeanFactory.registerBeanDefinition("xuzz"+index,beanDefinitionBuilder.getBeanDefinition());
        redisTemplateMap.put("xuzz"+index, (RedisTemplate<String, Object>) configurableApplicationContext.getBean("xuzz"+index));
    }

    public void forCirculationCreateJavaBean(){
        for (int i =0;i<3 ;i++){
            createJavaCode(i);
        }
    }
}
