package com.zhengjy.test.testcase.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by zhengjy on 2016/11/29.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    protected Context mContext;
    protected FragmentManager mFragmentManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mFragmentManager = getFragmentManager();
    }


    public void switchFragment(BaseFragment toFragment) {
        ((FragmentDemoActivity)mContext).switchFragment(toFragment);
    }

    public BaseFragment getCurrentFragment() {
        return ((FragmentDemoActivity)mContext).getCurrentFragment();
    }

    public void setCurrentFragment(BaseFragment fragment) {
        ((FragmentDemoActivity)mContext).setCurrentFragment(fragment);
    }

    /**
     * @return true 回退事件已被此fragment处理，false 回退事件未处理
     *
     * */
    public boolean onBackPressed() {
        if(mFragmentManager == null){
            return false;
        }
        int count = mFragmentManager.getBackStackEntryCount();
        Log.d(TAG, "onBackPressed. getBackStackEntryCount:" + count);
        if (count > 0) {
            mFragmentManager.popBackStack();
            setCurrentFragment((BaseFragment) mFragmentManager.getFragments().get(count-1));
            return true;
        } else {
            return false;
        }
    }

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity a,Bundle data){
        Intent intent = new Intent();
        intent.setClass(a, FragmentDemoActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        a.startActivity(intent);
    }
}
