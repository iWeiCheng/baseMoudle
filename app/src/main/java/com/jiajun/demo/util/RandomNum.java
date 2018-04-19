package com.jiajun.demo.util;

/**
 * Created by Administrator on 2016/5/19.
 */
public class RandomNum {
    public static String getrandom() {
        int numcode = (int) ((Math.random() * 9 + 1) * 100000000);
        return String.valueOf(numcode);
    }
}
