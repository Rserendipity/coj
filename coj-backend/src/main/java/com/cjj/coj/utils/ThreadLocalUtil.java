package com.cjj.coj.utils;

public class ThreadLocalUtil {
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static Object get() {
        return threadLocal.get();
    }

    public static void set(Object obj) {
        threadLocal.set(obj);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
