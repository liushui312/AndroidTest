package com.zhengjy.test.testcase.CustomView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhengjy.test.R;
import com.zhengjy.test.base.BaseFragmentActivity;
import com.zhengjy.test.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengjy on 2016/11/23.
 */

public class CustomViewActivity extends BaseFragmentActivity {
    @BindView(R.id.llyt_btns)
    LinearLayout mLlytBtns;

    private CustomFragment mCircleFragment;
    private CustomFragment mBrickFragment;
    private CustomFragment mWaveFragment;
    private CustomFragment mReflectFragment;
    private CustomFragment mPolyLineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        mCurrentFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_CIRCLE_HEAD);
    }

    @OnClick(R.id.btn_circle)
    void circleBtnOnClick() {
        if (mCircleFragment == null) {
            mCircleFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_CIRCLE_HEAD);
        }
        switchFragment(R.id.custom_view_fragment, mCircleFragment);
        mLlytBtns.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_brick)
    void brckBtnOnClick() {
        if (mBrickFragment == null) {
            mBrickFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_BRICK);
        }
        switchFragment(R.id.custom_view_fragment, mBrickFragment);
        mLlytBtns.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_wave)
    void waveBtnOnClick() {
        if (mWaveFragment == null) {
            mWaveFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_WAVE);
        }
        mWaveFragment.resetWave();
        switchFragment(R.id.custom_view_fragment, mWaveFragment);
        mLlytBtns.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_polyline)
    void polyLineViewOnClick() {
        if (mPolyLineFragment == null) {
            mPolyLineFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_POLYLINE);
        }
        switchFragment(R.id.custom_view_fragment, mPolyLineFragment);
        mLlytBtns.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_reflect)
    void reflectViewOnClick() {
        if (mReflectFragment == null) {
            mReflectFragment = CustomFragment.newInstanst(Constant.CUSTOM_VIEW_REFLECT);
        }
        switchFragment(R.id.custom_view_fragment, mReflectFragment);
        mLlytBtns.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mLlytBtns.setVisibility(View.VISIBLE);
    }
}
