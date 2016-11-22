package com.zhengjy.test.testcase.lock;


import java.util.concurrent.locks.ReentrantLock;

public class SampleSupport1 extends SampleSupport{
    private final ReentrantLock lock = new ReentrantLock();

    public void doSomething() throws InterruptedException {
        lock.lockInterruptibly();
//        try {
//            lock.lockInterruptibly();
//        } catch (InterruptedException e) {
//            //做一些其它的事，不结束线程
//        }
        System.out.println(Thread.currentThread().getName() + " will execute counter++.");
        startTheCountdown();
        try {
            counter++;
        }
        finally {
            lock.unlock();
        }
    }
}
