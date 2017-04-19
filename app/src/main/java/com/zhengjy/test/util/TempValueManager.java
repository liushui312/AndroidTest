package com.zhengjy.test.util;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chenhn on 2017/3/10.
 */

public class TempValueManager {
    private static TempValueManager mInstance;

    private ReentrantLock mLock;
    private HashMap<String,Object> mValueMap;

    private TempValueManager() {
        mLock = new ReentrantLock();
        mValueMap = new HashMap<>();
    }

    synchronized public static TempValueManager getInstance(){
        if(mInstance == null){
            mInstance = new TempValueManager();
        }
        return mInstance;
    }

    /**
     * 获取临时变量(取一次就删除)
     * @param key
     * @return
     */
    public Object getValue(String key){
        mLock.tryLock();
        try{
            Object value = mValueMap.get(key);
            mValueMap.remove(key);
            return value;
        } finally {
            mLock.unlock();
        }
    }

    /**
     * 添加临时变量
     * @param model
     * @return 变量对应的唯一key
     */
    public String putValue(Object model){
        mLock.tryLock();
        try{
            String key = Utils.generateRandomId();
            mValueMap.put(key,model);
            return key;
        }finally {
            mLock.unlock();
        }
    }

}
