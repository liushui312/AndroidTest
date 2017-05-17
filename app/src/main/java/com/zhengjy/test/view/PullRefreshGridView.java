package com.zhengjy.test.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by zhengjy on 2017/5/17.
 */

public class PullRefreshGridView extends SwipeRefreshLayout {

    private GridView mGridView;
    private float mScaledTouchSlop;
    private boolean isLoading;
    private OnLoadListener mOnLoadListener;

    public PullRefreshGridView(Context context) {
        super(context);
        initView();
    }

    public PullRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mGridView == null) {
            // 判断容器有多少个孩子
            if (getChildCount() > 0) {
                // 判断第一个孩子是不是ListView
                if (getChildAt(0) instanceof GridView) {
                    mGridView = (GridView) getChildAt(0);
                    // 设置ListView的滑动监听
                    setGridViewOnScroll();
                }
            }
        }
    }

    /**
     * 在分发事件的时候处理子控件的触摸事件
     */
    private float mDownY, mUpY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动过程中判断时候能下拉加载更多
                if (canLoadMore()) {
                    // 加载数据
                    loadData();
                }

                break;
            case MotionEvent.ACTION_UP:
                // 移动的终点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setGridViewOnScroll() {
        if (mGridView != null) {
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // 移动过程中判断时候能下拉加载更多
                    if (canLoadMore()) {
                        loadData();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
    }

    private void loadData() {
        System.out.println("加载数据...");
        if (mOnLoadListener != null) {
            // 设置加载状态，让布局显示出来
            setLoading(true);
            mOnLoadListener.onLoad();
        }

    }

    /**
     * 设置加载状态，是否加载传入boolean值进行判断
     */
    public void setLoading(boolean loading) {
        // 修改当前的状态
        isLoading = loading;
        if (!isLoading) {
            // 重置滑动的坐标
            mDownY = 0;
            mUpY = 0;
        }
    }

    private boolean canLoadMore() {
        // 1. 是上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        if (condition1) {
            System.out.println("是上拉状态");
        }

        // 2. 当前页面可见的item是最后一个条目
        boolean condition2 = false;
        if (mGridView != null && mGridView.getAdapter() != null) {
            condition2 = mGridView.getLastVisiblePosition() == (mGridView.getAdapter().getCount() - 1);
        }

        if (condition2) {
            System.out.println("是最后一个条目");
        }
        // 3. 正在加载状态
        boolean condition3 = !isLoading;
        if (condition3) {
            System.out.println("不是正在加载状态");
        }
        return condition1 && condition2 && condition3;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
