package com.zhengjy.test.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by zhengjy on 2016/12/6.
 */

public class BaseFragmentActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "BaseFragmentActivity";

    protected FragmentManager mFragmentManager;
    protected BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     *   hide current fragment and show target fragment
     *
     * @param toFragment the fragment to switch
     */
    public void switchFragment(int resId, BaseFragment toFragment) {
        if (mCurrentFragment == null) {
            Log.e(TAG, "switchFragment. mCurrentFragment is null");
            return;
        }

        if (toFragment == null) {
            Log.e(TAG, "switchFragment. toFragment is null");
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (!toFragment.isAdded()) {
            transaction.hide(mCurrentFragment)
                    .add(resId, toFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            transaction.hide(mCurrentFragment)
                    .show(toFragment)
                    .addToBackStack(null)
                    .commit();
        }

        mCurrentFragment = toFragment;
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        mCurrentFragment = currentFragment;
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            boolean ret = mCurrentFragment.onBackPressed();
            Log.d(TAG, "onBackPressed. ret:"+ret);
            if (!ret) {
                super.onBackPressed();
            }
        } else {
            Log.d(TAG, "onBackPressed. mCurrentFragment is null");
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
