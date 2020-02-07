package com.ley.threading;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Leigh Yu
 * @date
 */
@Slf4j
public class PrintByTwoThreadsInTurn {

    private final static int LOOP_TIMES = 100;
    private final AtomicInteger atomicLoopTimes = new AtomicInteger(0);
    private volatile boolean VOLATILE_FLAG = false;
    /**
     * 通知 -> 等待 -> 打印
     * @throws Exception
     */
    public void runWaitNotify() throws Exception{
        PrintByTwoThreadsInTurn obj = new PrintByTwoThreadsInTurn();
        Thread t1 = new Thread(obj::printOneWaitNotify);
        Thread t2 = new Thread(obj::printTwoWaitNotify);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private synchronized void printOneWaitNotify() {
        for (int i = 0; i < PrintByTwoThreadsInTurn.LOOP_TIMES; i += 2) {
            //通知另一个线程打印
            this.notify();
            try {
                //当前线程等待
                this.wait();
                // 在junit输出日志
                log.info("printOne->" + String.valueOf(i));
                log.debug("printOne->" + String.valueOf(i));
                // 防止打印速度过快导致混乱
                //Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //通知循环中最后一个等待线程
        this.notify();
    }

    private synchronized  void printTwoWaitNotify() {
        for (int i = 1; i < PrintByTwoThreadsInTurn.LOOP_TIMES; i += 2) {
            //通知另一个线程打印
            this.notify();
            try {
                //当前线程等待
                this.wait();
                // 在junit输出日志
                log.info("printOne->" + String.valueOf(i));
                log.debug("prinTwo->" + String.valueOf(i));
                // 防止打印速度过快导致混乱
                //Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //通知循环中最后一个等待线程
        this.notify();
    }

    /**
     * Volatile
     *
     * 多线程判断状态需要用while判断wait notify (wait 的 java doc 也提及需要用while避免虚假唤醒)
     * 如果两个及以上线程消费同一个对象，被消费的对象应该的判断必须放在循环中，否则可能会出现多个线程等待(wait)对象到某个状态，
     * 一旦到达某个状态，全部的消费线程将全部进行业务处理，这是不对的。应该只有一个线程进行消费，其他的线程依然等待。或者使用notify
     * 不使用notifyall 进行一个唤醒。
     *
     * @throws Exception
     */
    public void runVolatile() throws Exception{
        PrintByTwoThreadsInTurn obj = new PrintByTwoThreadsInTurn();
        Thread t1 = new Thread(obj::printOneVolatile);
        Thread t2 = new Thread(obj::printTwoVolatile);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private void printOneVolatile() {
        while (atomicLoopTimes.get() < LOOP_TIMES) {
            if (!VOLATILE_FLAG) {
                // 在junit输出日志
                log.info("printOneCAS->" + atomicLoopTimes.incrementAndGet());
                VOLATILE_FLAG = true;
            }
        }
    }

    private void printTwoVolatile() {
        while (atomicLoopTimes.get() < LOOP_TIMES) {
            if (VOLATILE_FLAG) {
                // 在junit输出日志
                log.info("printTwoCAS->" + atomicLoopTimes.incrementAndGet());
                VOLATILE_FLAG = false;
            }
        }
    }
}
