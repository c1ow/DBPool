package com.concurrence.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Xuzz on 2017/11/18.
 *
 * 带有返回结果的线程
 */
public class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result taskWithResult id is "+id;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i< 10 ; i ++)
            results.add(executorService.submit(new TaskWithResult(i)));
        for (Future<String> result: results)
            try {
                System.err.println("--->"+result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                executorService.shutdown();
            }
    }
}
