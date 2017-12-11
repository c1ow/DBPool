package com.concurrence.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xuzz on 2017/11/18.
 */
public class Priority implements Runnable {
    private int countDown = 5;
    private volatile double d;
    private int priority;

    public Priority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return Thread.currentThread() +":"+countDown;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true){
            for (int i =0 ; i < 100000; i++){
                d+=(Math.PI+Math.E)/i;
                if (i%1000==0){
                    Thread.yield();
                }
            }
            System.err.println(this);
            if (--countDown == 0) return;
        }
    }

    /**
     * 线程优先级
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Priority(Thread.MAX_PRIORITY));
            executorService.execute(new Priority(Thread.MIN_PRIORITY));
        }
        executorService.shutdown();
    }
}
