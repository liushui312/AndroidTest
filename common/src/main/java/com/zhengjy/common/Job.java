package com.zhengjy.common;

/**
 * 任务
 * Created by chenhn on 2016/12/7.
 */

public abstract class Job<Result>{

    private String mTag;
    
    public Job(String tag) {
        this.mTag = tag;
    }

    @Override
    public String toString() {
        return mTag;
    }
    /**
     * 执行任务
     * @return
     */
    abstract public  Result run();

    /**
     * 任务执行结束
     * @param result
     */
    abstract public void finish(Result result);
}

