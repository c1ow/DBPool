package com.concurrence.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Xuzz on 2017/11/20.
 * 共享有限资源
 */
 abstract class IntGenerator {
    private volatile boolean canceled = false;
    public abstract int next();
    public void canceled(){
        canceled = true;
    }
    public boolean isCanceled(){
        return canceled;
    }
}

class EvenChecker implements Runnable{
    private IntGenerator intGenerator;
    private final int id;

    EvenChecker(IntGenerator intGenerator, int id) {
        this.intGenerator = intGenerator;
        this.id = id;
    }


    @Override
    public void run() {
        while (!intGenerator.isCanceled()){
            if (intGenerator.next()%2 != 0){
                System.out.println(intGenerator.next() + " is not even.");
                intGenerator.canceled();
            }
        }
    }
    public static void test(IntGenerator intGenerator,int count) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i< count;i++){
            executorService.execute(new EvenChecker(intGenerator,i));
        }
        executorService.shutdown();
    }

    public static void test(IntGenerator intGenerator) {
        test(intGenerator,10);
    }
}
class EavnGenerator extends IntGenerator {
     private int currentEvenValue = 0;
//    public static void test(IntGenerator intGenerator) {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i< 10;i++){
//            executorService.execute(new EvenChecker(intGenerator,i));
//        }
//        executorService.shutdown();
//    }

    @Override
    //加锁防止两个任务同时访问该方法
    public synchronized int next() {
        ++currentEvenValue;
//        System.err.println("---->"+currentEvenValue);
        Thread.yield();
        ++currentEvenValue;
//        System.out.println("---->"+currentEvenValue);
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EavnGenerator());
    }
}


/**
 * 显视加锁
 */
class LookEven extends IntGenerator{
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;
            return currentEvenValue;
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        EvenChecker.test(new LookEven());
    }
}


