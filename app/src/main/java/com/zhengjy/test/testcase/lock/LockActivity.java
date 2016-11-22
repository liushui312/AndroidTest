package com.zhengjy.test.testcase.lock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zhengjy.test.R;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockActivity extends Activity {
    private static final String TAG = LockActivity.class.getSimpleName();

    @OnClick(R.id.test_reentrantlock1) void doTest() {
        doReentrantLockTest();
    }

    @OnClick(R.id.test_reentrantlock2) void testReentranLock2 () {
        testReentrantLock();
    }

    @OnClick(R.id.test_synchronized) void testSynchronized() {
        final SampleSupport2 support2 = new SampleSupport2();

        Runnable runnable = new Runnable() {
            public void run() {
                support2.doSomething();
            }
        };

        Thread third = new Thread(runnable,"third");
        Thread fourth = new Thread(runnable,"fourth");

        executeTest(third, fourth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
    }


    /** ReentrantLock Condition test begin **/
    private void doReentrantLockTest() {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "t1 run");
                for (int i=0;i<1000;i++) {
                    try {
                        Log.d(TAG, "putting..");
                        boundedBuffer.put(Integer.valueOf(i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }) ;

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<1000;i++) {
                    try {
                        Object val = boundedBuffer.take();
                        Log.d(TAG, "val=" + val.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }) ;

        t1.start();
        t2.start();
    }

    /**
     * BoundedBuffer 是一个定长100的集合，当集合中没有元素时，take方法需要等待，直到有元素时才返回元素
     * 当其中的元素数达到最大值时，要等待直到元素被take之后才执行put的操作
     * @author yukaizhao
     *
     */
    static class BoundedBuffer {
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[100];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            Log.d(TAG, "put wait lock");
            lock.lock();
            Log.d(TAG, "put get lock");
            try {
                while (count == items.length) {
                    Log.d(TAG,"buffer full, please wait");
                    notFull.await();
                }

                items[putptr] = x;
                if (++putptr == items.length)
                    putptr = 0;
                ++count;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            Log.d(TAG,"take wait lock");
            lock.lock();
            Log.d(TAG, "take get lock");
            try {
                while (count == 0) {
                    Log.d(TAG, "no elements, please wait");
                    notEmpty.await();
                }
                Object x = items[takeptr];
                if (++takeptr == items.length)
                    takeptr = 0;
                --count;
                notFull.signal();
                return x;
            } finally {
                lock.unlock();
            }
        }
    }
    /** ReentrantLock Condition test end **/


    public void testReentrantLock() {
        final SampleSupport1 support = new SampleSupport1();
        Thread first = new Thread(new Runnable() {
            public void run() {
                try {
                    support.doSomething();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"first");

        Thread second = new Thread(new Runnable() {
            public void run() {
                try {
                    support.doSomething();
                }
                catch (InterruptedException e) {
                    System.out.println("Second Thread Interrupted without executing counter++,beacuse it waits a long time.");
                }
            }
        },"second");

        executeTest(first, second);
    }

    public void executeTest(Thread a, Thread b) {
        a.start();
        try {
            Thread.sleep(100);
            b.start();
            Thread.sleep(1000);
            b.interrupt();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
