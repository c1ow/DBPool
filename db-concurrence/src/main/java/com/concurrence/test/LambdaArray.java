package com.concurrence.test;

import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by Xuzz on 2017/12/29.
 */
public class LambdaArray implements Runnable {
    private static  List<String> list = new Vector<String>();

    public LambdaArray(List<String> list) {
        this.list = list;
    }

    public static void getOne(){
        if (list.size()<1){
            return;
        }
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(list.size(),0);
        int ime = atomicStampedReference.getStamp();
        if (atomicStampedReference.compareAndSet(list.size(),list.size()-1,ime,ime+1)){
//            list.remove(0);
            System.err.println("----->获取数值："+list.remove(0));
        }
    }

    public static List<String> createList(){
        for (int i =0 ; i<20 ; i++){
            list.add(String.valueOf(i));
        }
        return list;
    }

    @Override
    @Async
    public void run() {
        while (true){
            getOne();
        }
    }

    public static void main(String[] args) {
        List<String> list = createList();
        Thread thread = new Thread(new LambdaArray(list));
        Thread thread1 = new Thread(new LambdaArray(list));
        Thread thread2 = new Thread(new LambdaArray(list));
        Thread thread3 = new Thread(new LambdaArray(list));
        Thread thread4 = new Thread(new LambdaArray(list));
        Thread thread5 = new Thread(new LambdaArray(list));
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(thread);

        service.shutdown();
    }

}
