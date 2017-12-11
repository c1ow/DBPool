package com.concurrence.test;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Xuzz on 2017/11/20.
 * 默认的后程序工厂
 */
public class DaemonFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        //设置优先集
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    }
}
