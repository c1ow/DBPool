package com.concurrence.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Xuzz on 2017/11/20.
 */
public class AttemptLock  {
    public ReentrantLock lock = new ReentrantLock();
    //娌℃湁鏃堕棿绛夊緟
    public void unTime(){
        Boolean captured = lock.tryLock();
        try {
            System.out.println("unTime tryLock: "+captured);
        }finally {
            if (captured)
                lock.unlock();
        }
    }
    //涓ょ鏃堕棿绛夊緟
    public void timed(){
        Boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("timed tryLock: "+captured);
        }finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AttemptLock attemptLock = new AttemptLock();
        attemptLock.unTime();
        attemptLock.timed();
//        LockTread lockTread = new LockTread();
//        attemptLock = lockTread.putA(attemptLock);
//        Thread thread = new Thread(lockTread);
//        thread.setDaemon(true);
//        thread.start();
        new Thread(){
            {setDaemon(true);}

            @Override
            public void run() {
//                attemptLock.lock.lo();
                System.out.println("-------->");
            }
        }.start();
        Thread.yield();
//        浼戠湢璁╃嚎绋嬪惎鍔�
        Thread.sleep(3000);
        attemptLock.unTime();
        attemptLock.timed();
    }
}
class LockTread implements Runnable{
    private AttemptLock attemptLock = new AttemptLock();

    public AttemptLock getAttemptLock() {
        return attemptLock;
    }

    public void setAttemptLock(AttemptLock attemptLock) {
        this.attemptLock = attemptLock;
    }

    public AttemptLock putA(AttemptLock attemptLock){
       return getAttemptLock();
   }

    @Override
    public void run() {
        //鑾峰彇閿佷絾鏄垜涓嶉噴鏀�
        attemptLock.lock.lock();
        System.out.println("---->tryLock");
        setAttemptLock(attemptLock);
    }
}
