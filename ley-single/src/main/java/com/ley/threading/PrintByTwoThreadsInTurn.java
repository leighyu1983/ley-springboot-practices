package com.ley.threading;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Leigh Yu
 * @date
 */
@Slf4j
public class PrintByTwoThreadsInTurn {

    private final static int LOOP_TIMES = 100;
    private final AtomicInteger atomicLoopTimes = new AtomicInteger(0);
    private volatile boolean VOLATILE_FLAG = false;
    // 多线程按照顺序依次调用
    private final Lock lock = new ReentrantLock();
    private final Condition c1 = lock.newCondition();
    private final Condition c2 = lock.newCondition();
    private final Condition c3 = lock.newCondition();
    /* 状态位 */
    private int action = 1;


    /**
     * 通知 -> 等待 -> 打印  sychrnozied wait notify
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

    private synchronized void printTwoWaitNotify() {
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
     * Volatile change variable status in while
     *
     * 特别好的例子 https://dzone.com/articles/java-volatile-keyword-0
     * 一般情况使用static 而不用volatile没有问题，但是在特殊的场景下，比如下例或者上面链接中的例子，模拟高并发场景下的循环，
     * 如果不用volatile,一个线程对数据的更新，不能马上被另一个线程看到，因为都在使用从主内存拷贝出来的副本数据，
     * 平时的时候，只用static是不会碰到不用volatile这个问题，因为业务处理完后，系统有充足的时间完成一个线程的数据同步回主线程，
     * 另一个线程从主线程读取新的数据。
     *
     * 另一个说明，上面连接中的例子没有使用== 而是 《=，这是推荐的做法，在高并发场景下避免扣成负数。
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

    /**
     * A -> B -> C = (1,2,3)
     *
     * AA prints 5 times, BB prints 10 times, CC prints 15 times
     * repeat for 10 times
     *
     */
    public void runReentrantCondition() throws Exception {
        PrintByTwoThreadsInTurn obj = new PrintByTwoThreadsInTurn();
        Thread t1 = new Thread(obj::print5, "A");
        Thread t2 = new Thread(obj::print10, "B");
        Thread t3 = new Thread(obj::print15, "C");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    private void print5() {
        lock.lock();
        try {
            // 直到是1才运行
            while (action != 1) {
                c1.wait();
            }

            for (int i = 0; i < 5; i++) {
                log.info(Thread.currentThread().getName() + "-->" + i);
            }

            action = 2;
            c2.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print10() {
        lock.lock();
        try {
            // 直到是2才运行
            while (action != 2) {
                c2.wait();
            }

            for (int i = 0; i < 10; i++) {
                log.info(Thread.currentThread().getName() + "-->" + i);
            }

            action = 3;
            c3.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print15() {
        lock.lock();
        try {
            // 直到是3才运行
            while (action != 3) {
                c3.wait();
            }

            for (int i = 0; i < 15; i++) {
                log.info(Thread.currentThread().getName() + "-->" + i);
            }

            // 通知1工作
            action = 1;
            c1.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Callable & FutureTask & Thread
     */
    public void runFutureTask() {
        FutureTask futureTask = new FutureTask(new MyCallable());
        new Thread(futureTask, "A").start();

        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            throw new Exception("waahah");
        }
    }

}
