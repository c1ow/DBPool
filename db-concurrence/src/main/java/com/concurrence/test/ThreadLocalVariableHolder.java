package com.concurrence.test;

import java.util.Random;

/**
 * Created by Xuzz on 2017/11/20.
 */
public class ThreadLocalVariableHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        Random random = new Random(47); //初始值
        protected synchronized Integer initiaValue(){
            return random.nextInt(1000);
        }
    };
}
