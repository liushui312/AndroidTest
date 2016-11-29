package com.zhengjy.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhengjy.common.helper.SharePreferencesHelper;
import com.zhengjy.test.util.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhengjy on 2016/11/10.
 */

public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";

    public static final String IS_FIRST_IN_KEY = "mIsFirstIn";
    boolean mIsFirstIn = false;

    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    //引导图片资源
    private static final int[] mPics = {R.drawable.whatsnew_00,
            R.drawable.whatsnew_01, R.drawable.whatsnew_02,
            R.drawable.whatsnew_03};
    private Button mBtnBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFirstIn = SharePreferencesHelper.getInstance(this).getBoolean(IS_FIRST_IN_KEY, true);
        if (mIsFirstIn) {
            Log.d(TAG, "is first in");
            setContentView(R.layout.activity_welcome);
            initView();
        } else {
            Log.d(TAG, "not first in");
            MainActivity.start(this, null);
            finish();
        }

    }

    private void initView() {
        List<View> views = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int mPic : mPics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setImageResource(mPic);
            views.add(iv);
        }
        mAdapter = new ViewPagerAdapter(views);

        mViewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mAdapter.getCount() - 1) {
                    mBtnBegin.setVisibility(View.VISIBLE);
                } else {
                    mBtnBegin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtnBegin = (Button) findViewById(R.id.btn_begin);
        mBtnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesHelper.getInstance(WelcomeActivity.this).putBoolean(IS_FIRST_IN_KEY, false);
                MainActivity.start(WelcomeActivity.this, null);
                finish();
            }
        });

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.pagerIndicator);
        circlePageIndicator.setStrokeWidth(0);
        circlePageIndicator.setRadius(11);
        circlePageIndicator.setFillColor(Color.WHITE);
        circlePageIndicator.setPageColor(getResources().getColor(R.color.fill_color));
        circlePageIndicator.setViewPager(mViewPager);
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }
    }
}
