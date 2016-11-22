package com.zhengjy.test.testcase.lock;

abstract class SampleSupport {

    protected int counter;

    public void startTheCountdown() {
        long currentTime = System.currentTimeMillis();
        for (;;) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long diff = System.currentTimeMillis() - currentTime;
            if (diff > 5000) {
                break;
            }
        }
    }
}