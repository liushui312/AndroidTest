package com.zhengjy.test.testcase.CustomView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhengjy.test.R;
import com.zhengjy.test.base.BaseFragment;
import com.zhengjy.test.constant.Constant;


/**
 * Created by zhengjy on 2016/12/8.
 */

public class CustomFragment extends BaseFragment {
    private static final String TAG = "CustomFragment";

    private static final String VIEW_TYPE_KEY = "viewType";

    private int mViewType = Constant.CUSTOM_VIEW;
    private BrickView mBrickView;
    private CircleImageView mCircleImageView;
    private WaveView mWaveView;
    private ReflectView mReflectView;
    private MultiCricleView mMultiCricleView;
    private PolylineView mPolyLineView;

    public static CustomFragment newInstanst(int viewType) {
        CustomFragment customFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(VIEW_TYPE_KEY, viewType);
        customFragment.setArguments(bundle);
        return customFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mViewType = getArguments().getInt(VIEW_TYPE_KEY);
        }

        Log.d(TAG, "onCreate. mViewType:" + mViewType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.fragment_custom_view, container, false);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        Log.d(TAG, "onCreateView. mViewType:" + mViewType);
        switch (mViewType) {
            case Constant.CUSTOM_VIEW_BRICK:
                mBrickView = new BrickView(mContext, null);
                mBrickView.setLayoutParams(layoutParams);
                view.addView(mBrickView);
                break;
            case Constant.CUSTOM_VIEW_CIRCLE_HEAD:
                mCircleImageView= new CircleImageView(mContext);
                mCircleImageView.setLayoutParams(layoutParams);
                mCircleImageView.setImageResource(R.drawable.headshow1);
                view.addView(mCircleImageView);
                break;
            case Constant.CUSTOM_VIEW_WAVE:
                mWaveView= new WaveView(mContext, null);
                mWaveView.setLayoutParams(layoutParams);
                view.addView(mWaveView);
                break;
            case Constant.CUSTOM_VIEW_REFLECT:
                mReflectView= new ReflectView(mContext, null);
                mReflectView.setLayoutParams(layoutParams);
                view.addView(mReflectView);
                break;
            case Constant.CUSTOM_VIEW_MULTI_CRICLE:
                mMultiCricleView= new MultiCricleView(mContext, null);
                mMultiCricleView.setLayoutParams(layoutParams);
                view.addView(mMultiCricleView);
                break;
            case Constant.CUSTOM_VIEW_DREAM_EFFECT:
                break;
            case Constant.CUSTOM_VIEW_POLYLINE:
                mPolyLineView= new PolylineView(mContext, null);
                mPolyLineView.setLayoutParams(layoutParams);
                view.addView(mPolyLineView);
                break;
            default:
                break;
        }

        return view;
    }

    public void resetWave() {
        if (mWaveView != null) {
            mWaveView.reset();
        }
    }
}
