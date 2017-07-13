package com.zhengjy.test;

import android.app.Application;

/**
 * Created by zhengjy on 2017/7/13.
 */

public class MainApplication extends Application {
    private static MainApplication mContext;

    public static MainApplication getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
