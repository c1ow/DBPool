package com.concurrence.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xuzz on 2017/11/18.
 */
public class OtherThreadPool {
    public static void main(String[] args) {
        //Cached... 会为每一个项目创建线程
        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0 ; i < 3;i++)
//            executorService.execute(new LiftOff());
//        executorService.shutdown();
        //fixed... 设置线程数
        executorService = Executors.newFixedThreadPool(1);
//        for (int i=0;i<3;i++)
////            executorService.execute(new LiftOff());
//        executorService.shutdown();
        //single.. 线程为1的Fixed
        executorService = Executors.newSingleThreadExecutor();
        for (int i =0; i<3;i++)
                executorService.execute(new LiftOff());
        executorService.shutdown();
    }
}
