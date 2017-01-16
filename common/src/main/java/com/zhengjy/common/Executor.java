package com.zhengjy.common;

import android.os.AsyncTask;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务执行者
 * Created by chenhn on 2016/12/7.
 */

public class Executor {
    private static final String TAG = "Executor";
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 15;
    private static final int KEEP_ALIVE_TIME = 10; // 10 seconds
    private static java.util.concurrent.Executor mExecutor;
    private static LinkedBlockingQueue mjobQueue;
    static {
        mjobQueue = new LinkedBlockingQueue<Runnable>();
        mExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, mjobQueue,
                new PriorityThreadFactory("Logic-Job",
                        android.os.Process.THREAD_PRIORITY_BACKGROUND));
    }

    private Executor() {

    }

    public static void postJob(final Job job){
        AsyncTask<Void,Integer,Object> task = new AsyncTask<Void,Integer,Object>() {

            @Override
            protected Object doInBackground(Void... params) {
                return job.run();
            }

            @Override
            protected void onPostExecute(Object o) {
                job.finish(o);
            }
        };
        task.executeOnExecutor(mExecutor);
    }
}
