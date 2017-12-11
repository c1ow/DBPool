package com.concurrence.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xuzz on 2017/11/20.
 */
public class DaemonThreadFactory implements Runnable{
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public void run() {
        while (true){
            logger.info("线程状态---->:{},{}",new Object[]{Thread.currentThread(),this});
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newCachedThreadPool();
        //自定义后台线程属性
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonFactory());

        for (int i =0; i < 10 ; i++){
            Thread thread = new Thread(new DaemonThreadFactory());
            executorService.execute(new DaemonThreadFactory());
//            executorService.shutdown();
//            thread.setDaemon(true);
//            thread.start();
        }
        System.err.println("__start_thread__");
        Thread.sleep(100);
    }
}
