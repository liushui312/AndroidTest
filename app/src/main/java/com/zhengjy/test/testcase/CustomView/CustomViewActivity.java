package com.zhengjy.test.testcase.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zhengjy.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengjy on 2016/11/23.
 */

public class CustomViewActivity extends Activity {

    @BindView(R.id.circleImageView) CircleImageView mCircleImageView;
    @BindView(R.id.brickView) BrickView mBrickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_circle)
    void circleBtnOnClick() {
        mCircleImageView.setVisibility(View.VISIBLE);
        mBrickView.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_brick)
    void brckBtnOnClick() {
        mCircleImageView.setVisibility(View.GONE);
        mBrickView.setVisibility(View.VISIBLE);
    }
}
