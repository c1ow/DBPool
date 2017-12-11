package com.pool;

import com.pool.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by Xuzz on 2017/11/14.
 */
@SpringBootApplication
@EnableConfigurationProperties({User.class})
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class,args);
        User user = new User();
        System.err.println("----"+user.getUserName());
    }
}
