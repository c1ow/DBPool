package com.work.test;

import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.util.List;

/**
 * Created by Xuzz on 2017/11/25.
 * 字符串替换
 */
public class StrSplit {
    public static void main(String[] args) {
        splitS("");
    }

    public static void splitS(String valueName){
        StringBuffer sb = new StringBuffer();
        StringBuffer value = new StringBuffer(valueName);
        for (int i =0;i<value.length();i++){
            char c = value.charAt(i);
            String s = String.valueOf(c);
            if ("_".equals(s)){
                char c1 = value.charAt(i+1);
                sb.append(String.valueOf(c1).toUpperCase());
                i++;
                continue;
            }
            sb.append(value.charAt(i));
        }
    }
}
