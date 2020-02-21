package com.ley.util;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Leigh Yu
 * @date 2020/2/16 20:27
 */
public class MyCounter {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
    private static AtomicLong atomic = new AtomicLong();

    public static void add() {
        threadLocal.set(atomic.incrementAndGet());
    }

    public static Long get() {
        return threadLocal.get();
    }
}
