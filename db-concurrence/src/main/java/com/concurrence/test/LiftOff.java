package com.concurrence.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Xuzz on 2017/11/18.
 * 线程任务
 */
public class LiftOff implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static int taskCount = 0;
    protected int countDown = 10;
    private final int id = taskCount++;

    public LiftOff(){}
    public String status(){
        return "#"+id+"("+(countDown>0?countDown:"liftDown")+").";
    }
    @Override
    public void run() {
        while (countDown --> 0){
            logger.info("线程状态：{}",new Object[]{status()});
            //线程调度器
            Thread.yield();
//            try {
//                Thread.sleep(1000l);               ，
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        logger.info("此线程结束。");
    }

    public static void main(String[] args) {
        LiftOff liftOff = new LiftOff();
        Logger logger = LoggerFactory.getLogger(liftOff.getClass().getName());
//        liftOff.run();
////        Thread thread = new Thread(new LiftOff());
//        logger.info("开启下一个线程----->");
////        thread.start();
//        logger.info("此线程结束<-------->");
//        logger.info("开启循环线程------->");
        for (int i = 0; i< 3 ;i++){
            new Thread(new LiftOff()).start();
            logger.info("----启动----"+i);
        }
    }
}
