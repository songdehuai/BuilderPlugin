package com.builder.utils;

public class Log {

    private static final String TAG = " ------------------- ";

    public static void log(String log) {
        System.out.println(TAG + log + TAG);
    }

    public static void i(String log) {
        System.out.println(log);
    }

    public static void e(String log) {
        System.err.println(log);
    }

    public static void ln(){
        System.out.println("");
    }
}
