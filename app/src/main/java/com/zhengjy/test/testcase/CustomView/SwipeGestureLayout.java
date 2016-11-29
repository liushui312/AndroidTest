package com.zhengjy.test.testcase.CustomView;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * 用于判断左右滑动手势的layout
 * 
 * @author liurs
 */
public class SwipeGestureLayout extends RelativeLayout {
    protected static final String TAG = SwipeGestureLayout.class.getSimpleName();

    private OnSwipeGesture mLsnr;
    
    private int mTouchSlop;
    private GestureDetector mGestureDetector;

    private int mDownX;
    private int mDownY;

    private boolean mScroll = false;
    //左右滑动时，x轴移动的最小距离
    private float horizontalMinDistance = 20;
    //左右滑动时，x轴移动的最小速度    
    private float minVelocity = 0;
    
    private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener(){
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling vx " + velocityX);
            if(mScroll)
                return true;

            //上下角度不能超过45度
            if(e2.getX() - e1.getX() > Math.abs(e2.getY() -e1.getY()) && e2.getX() - e1.getX() 
            		> horizontalMinDistance && Math.abs(velocityX) > minVelocity){
                if(mLsnr != null){
                    mLsnr.onSwipeRight();
                }
            } else if (e1.getX() - e2.getX() > Math.abs(e1.getY() -e2.getY()) && e1.getX() - e2.getX()
            		> horizontalMinDistance && Math.abs(velocityX) > minVelocity) {
                if(mLsnr != null){
                    mLsnr.onSwipeLeft();
                }
            }
            return true;
        };
    };
    
    public SwipeGestureLayout(Context context){
        this(context, null);
    }
    
    public SwipeGestureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, mGestureListener);
    }
    
    /**
     * 手势监听接口
     */
    public static interface OnSwipeGesture {
        void onSwipeLeft();
        
        void onSwipeRight();
    }
    
    public void setOnSwipeGestureListener(OnSwipeGesture lsnr){
        mLsnr = lsnr;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);

        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mDownX = (int) ev.getRawX();
            mDownY = (int) ev.getRawY();
            break;
        case MotionEvent.ACTION_MOVE:
            int moveX = (int) ev.getRawX();
            int moveY = (int) ev.getRawY();
            //当x轴的增量小于y轴的增量时即角度大于45度时不处理左右滑动
            if(Math.abs(moveX - mDownX) < Math.abs(moveY - mDownY)) {
                return false;
            }
            mScroll = canScroll(this, true, mTouchSlop, (int)ev.getX(), (int)ev.getY());

            if (Math.abs(moveX - mDownX) > mTouchSlop) {
                if (mScroll) {
                    return false;
                } else {
                    return true;
                }
            }
            
            break;
        }

        return super.onInterceptTouchEvent(ev);
    }
    
    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v View to test for horizontal scrollability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx Delta scrolled in pixels
     * @param x X coordinate of the active touch point
     * @param y Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                // Add versioned support here for transformed views.
                // This will not work for transformed views in Honeycomb+
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() &&
                        y + scrollY >= child.getTop() && y + scrollY < child.getBottom() &&
                        canScroll(child, true, dx, x + scrollX - child.getLeft(),
                                y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && ViewCompat.canScrollHorizontally(v, -dx);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }    
}
