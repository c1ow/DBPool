package com.concurrence.test;

import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xuzz on 2017/11/21.
 */

/**
 * 计数实体
 */
class Count {
    private int count = 0;
    private Random random = new Random(47);
    @Async
    public synchronized int increment(){
        int temp = count;
        if (!random.nextBoolean())
            Thread.yield();
        return (count = ++ temp);
    }
    public synchronized int getValue(){
        return count;
    }
}

/**
 * 线程
 */
class Entrance implements Runnable{
    //作为本地存储域
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    private int number = 0;
    private final int id;
    private static volatile boolean cancer = false;
    Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    public static void cancer(){
        cancer = true;
    }


    @Override
    public void run() {
        while (!cancer){
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("sleep");
            }
            System.out.println("stopping "+this);
        }
    }
    public synchronized int getValue(){
        return number;
    }

    public String toString(){
        return "Entrance" + id + ":" + getValue();
    }

    public static int getTolatCount(){
        return count.getValue();
    }

    public static int sumEntrance(){
        int sum = 0;
        for (Entrance entrance:entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

class OrnamenttalGarden{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i< 5;i++){
            executorService.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancer();
        executorService.shutdown();
        if (executorService.awaitTermination(250,TimeUnit.MILLISECONDS)){
            System.out.println("0----------");
        }
        System.out.print("total: "+Entrance.getTolatCount());
        System.out.print("sumEntranmen: "+ Entrance.sumEntrance());
    }
}