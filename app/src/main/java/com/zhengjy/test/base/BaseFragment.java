package com.zhengjy.test.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

/**
 * Created by zhengjy on 2016/12/6.
 */

public class BaseFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "BaseFragment";

    protected Context mContext;
    protected FragmentManager mFragmentManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mFragmentManager = getFragmentManager();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBar();
    }

    public void initToolBar() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolBar();
        }
    }

    public void switchFragment(int resId, BaseFragment toFragment) {
        ((BaseFragmentActivity)mContext).switchFragment(resId, toFragment);
    }

    public BaseFragment getCurrentFragment() {
        return ((BaseFragmentActivity)mContext).getCurrentFragment();
    }

    public void setCurrentFragment(BaseFragment fragment) {
        ((BaseFragmentActivity)mContext).setCurrentFragment(fragment);
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

    @Override
    public void onClick(View v) {

    }
}
