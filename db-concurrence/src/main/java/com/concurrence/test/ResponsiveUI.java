package com.concurrence.test;

import java.io.IOException;

/**
 * Created by Xuzz on 2017/11/20.
 */
public class ResponsiveUI extends Thread {

    private static volatile double d = 1;

    public ResponsiveUI() {
        //设置为后台程序，在main方法结束后停止该进程
        setDaemon(true);
        start();
    }

    public void run(){
        while (true){
            d = d + (Math.E + Math.PI)/d;
        }
    }

    public static void main(String[] args) throws IOException {
        new ResponsiveUI();
        System.in.read();
        System.out.println(d);
    }
}
