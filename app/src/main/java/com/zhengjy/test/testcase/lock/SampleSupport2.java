package com.zhengjy.test.testcase.lock;

public class SampleSupport2 extends SampleSupport{
    public synchronized void doSomething() {
        System.out.println(Thread.currentThread().getName() + " will execute counter++.");
        startTheCountdown();
        counter++;
    }
}
