package com.zhengjy.test.testcase.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zhengjy.test.R;

/**
 * Created by zhengjy on 2016/11/29.
 */

public class FragmentDemoActivity extends AppCompatActivity {

    private static final String TAG = "FragmentDemoActivity";

    private BaseFragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_fragment_demo);

        mFragmentManager = getSupportFragmentManager();
    }

    public void switchFragment(BaseFragment toFragment) {
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
                    .add(R.id.content_fragment, toFragment)
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
        boolean ret = mCurrentFragment.onBackPressed();
        Log.d(TAG, "onBackPressed. ret:"+ret);
        if (!ret) {
            super.onBackPressed();
        }
    }
}
