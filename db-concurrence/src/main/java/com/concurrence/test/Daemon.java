package com.concurrence.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xuzz on 2017/11/20.
 *  后台程序
 *  当非后台程序结束后，所有的后台程序都会被杀死。main方法是一个非后台程序。
 * TimeUnit 时间颗粒度 毫秒 秒 时 天
 *
 *
 */
public class Daemon implements Runnable{
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public void run() {
        try {
        while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                logger.info("线程状态：{} {}",new Object[]{Thread.currentThread(),this});
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("sleep interrupted...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10 ; i++){
            Thread daemon = new Thread(new Daemon());
            //设置为后台程序 不影响 非后台数据运行流程
            daemon.setDaemon(true);
            System.err.println("---> isDaemon: "+daemon.isDaemon());
            daemon.start();
        }
        System.err.println("All daemon started");
        //让非后台程序休眠之后开始
        TimeUnit.MILLISECONDS.sleep(12);
    }
}
