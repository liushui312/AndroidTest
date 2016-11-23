package com.zhengjy.test.util;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 *   Avoid double click almost at the same time
 */
public abstract class OnNoDoubleClickListener implements OnClickListener {
    private static final String TAG = OnNoDoubleClickListener.class.getSimpleName();
    public static final int MIN_CLICK_INTERVAL_TIME = 300;
    private long mLastClickTime = 0;

    @Override
    public void onClick(View v) {
        Log.v(TAG, "onClick");
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > MIN_CLICK_INTERVAL_TIME) {
            mLastClickTime = currentTime;
            OnNoDoubleClick(v);
            return;
        }
        Log.d(TAG, "ignore too frequent clicks");
    }

    public abstract void OnNoDoubleClick(View v);
}
