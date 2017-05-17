package com.zhengjy.test;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjy on 2017/5/4.
 */

public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {
    private int mSize;
    private int mIconIndicatorSelected = R.mipmap.icon_indicator_selected;
    private int mIconIndicatorNormal = R.mipmap.icon_indicator_normal;
    private List<ImageView> mDotViewLists = new ArrayList<>();

    public ViewPagerIndicator(Context context, LinearLayout dotLayout, int size) {
        this.mSize = size;

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //为小圆点左右添加间距
            params.leftMargin = context.getResources().getDimensionPixelOffset(R.dimen.welcome_indicator_left_margin);
            params.rightMargin = context.getResources().getDimensionPixelOffset(R.dimen.welcome_indicator_right_margin);
            //手动给小圆点一个大小
            params.height = context.getResources().getDimensionPixelOffset(R.dimen.welcome_indicator_width);
            params.width = context.getResources().getDimensionPixelOffset(R.dimen.welcome_indicator_width);
            if (i == 0) {
                imageView.setBackgroundResource(mIconIndicatorSelected);
            } else {
                imageView.setBackgroundResource(mIconIndicatorNormal);
            }
            //为LinearLayout添加ImageView
            dotLayout.addView(imageView, params);
            mDotViewLists.add(imageView);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mSize; i++) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if ((position % mSize) == i) {
                mDotViewLists.get(i).setBackgroundResource(mIconIndicatorSelected);
            } else {
                mDotViewLists.get(i).setBackgroundResource(mIconIndicatorNormal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
