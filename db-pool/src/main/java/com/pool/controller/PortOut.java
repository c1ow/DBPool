package com.pool.controller;

import com.pool.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xuzz on 2017/11/14.
 */
@RestController
@RequestMapping("/test")
public class PortOut {
    @Autowired
    private User user;
    @PostMapping("/ok")
    public String getTest(){
        return "---"+user.getUserName();
    }
}
