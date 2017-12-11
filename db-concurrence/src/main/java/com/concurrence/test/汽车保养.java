package com.concurrence.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xuzz on 2017/11/21.
 */
class 汽车保养 {
    /**
     * 关于汽车打蜡抛光
     * 各线程之间等待 协作
     */
}

class Car{
    private boolean waxOn = false;
    /**
     * 打蜡结束
     */
    public synchronized void waxed(){
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed(){
        waxOn = false;
        notifyAll();
    }

    /**
     * 等待打蜡完成
     * @throws InterruptedException
     */
    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn == false)
            wait();
    }

    /**
     * 打蜡完成下次打蜡等待
     * @throws InterruptedException
     */
    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true)
            wait();

    }
}

class WaxOn implements Runnable{
    private Car car = new Car();
    WaxOn(Car c) {car = c; }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println(" Wax On ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println(" exiting via interrupt");
        }
        System.out.println(" Ending wax on task! ");
    }
}

class WaxOff implements Runnable{
    private Car car = new Car();
    WaxOff(Car c){car = c;}
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                car.waitForWaxing();
                System.out.println(" Wax off ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        }catch (InterruptedException e){
            System.out.println(" exiting via interrupt");
        }
        System.out.println(" Ending wax on task! ");
    }
}

class WaxOMatic{
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOff(car));
        executorService.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
