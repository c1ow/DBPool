package com.work.test;

/**
 * Created by Xuzz on 2017/12/2.
 */
public class MathTest {
    public static void main(String[] args) {
        math01("100");
        indexOF();
    }
    public static void math01(String sourceEnum){
        Float tmp1 = Float.valueOf(sourceEnum);
        Float tmp2 = ((float)Math.round(tmp1*100)/100);
        System.err.println("-----------------"+tmp2.toString());
    }
    public static void indexOF(){
        String age = "10岁";
        if ("岁".indexOf(age)==-1){
           age = age.replace("岁","");
            System.err.println("data++"+age);
        }
    }
}
