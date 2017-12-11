package com.concurrence.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Xuzz on 2017/11/20.
 * 捕捉run内异常
 */
class ExceptionThread implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("thread is by " + t);
        System.err.println("eh " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " create new thread");
        Thread t = new Thread(r);
        System.err.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh " + t.getUncaughtExceptionHandler());
        return t;
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("caught " + e);
    }
}

class MainTest {
    public static void main(String[] args) {
//        01 详细
//        ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
//        02 方便
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ExceptionThread());
        executorService.shutdown();

    }
}
