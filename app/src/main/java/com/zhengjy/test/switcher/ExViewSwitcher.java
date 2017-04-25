package com.zhengjy.test.switcher;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.widget.ViewAnimator;


/**
 * 内部多个子view可以切换
 * 
 * @author chenhn 2016年4月13日 14:25:50
 * 
 */
public class ExViewSwitcher extends ViewAnimator {
    private int curIndex = 0;
    private SparseArray<View> mViewList = new SparseArray<View>();
    private ViewFactory mFactory;
    private OnViewChangeListener onViewChangeListener;
    
    public ExViewSwitcher(Context context) {
        super(context);
        setMeasureAllChildren(false);
    }

    public ExViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMeasureAllChildren(false);
    }

    public void setFactory(ViewFactory factory) {
        mFactory = factory;
        addChild(0);
    }
    
    public void setOnViewChangeListener(OnViewChangeListener onViewChangeListener){
        this.onViewChangeListener=onViewChangeListener;
    }
    
    /**
     * 显示下一个View
     */
    public void showNextView() {
        int nextIndex = curIndex + 1;
        if (mFactory != null) {
            View child = mViewList.get(nextIndex);
            if (child == null) {
                child = addChild(nextIndex);
                if (child == null)
                    return;
            }
            if (child != null) {
                Animation inAnimation = getInAnimation();
                if (inAnimation != null) {
                    child.startAnimation(inAnimation);
                }
                changeView(curIndex, nextIndex);
                curIndex++;
            }
        }
    }
    
    public void preLoadView(int index){
        if (mFactory != null) {
            View child = mViewList.get(index);
            if (child == null) {
                addChild(index);
            }
        }
    }
    
    @Override
    @Deprecated
    public void showNext() {
        super.showNext();
    }
    
    @Override
    @Deprecated
    public void showPrevious() {
        super.showPrevious();
    }
    
    /**
     * 显示上一个View
     */
    public void showPreView() {
        if (curIndex == 0)
            return;
        int preIndex = curIndex - 1;
        if (mFactory != null) {
            View child = mViewList.get(preIndex);
            if (child == null) {
                child = addChild(preIndex);
                if (child == null)
                    return;
            }
            if (child != null) {
                Animation outAnimation = getOutAnimation();
                if (outAnimation != null) {
                    View curView = mViewList.get(curIndex);
                    curView.startAnimation(outAnimation);
                }
                changeView(curIndex, preIndex);
                curIndex--;
            }
        }
    }

    public void showViewByIndex(int index) {
        if (index == curIndex || index < 0)
            return;
        if (mFactory != null) {
            View child = mViewList.get(index);
            if (child == null) {
                child = addChild(index);
                if (child == null)
                    return;
            }
            if (index > curIndex) {
                Animation inAnimation = getInAnimation();
                if (inAnimation != null) {
                    child.startAnimation(inAnimation);
                }
            } else {
                Animation outAnimation = getOutAnimation();
                if (outAnimation != null) {
                    View curView = mViewList.get(curIndex);
                    curView.startAnimation(outAnimation);
                }
            }
            changeView(curIndex, index);
            curIndex = index;
        }
    }
    
    public int getCurrentIndex(){
        return curIndex;
    }
    
    private View addChild(int index) {
        if (mFactory == null)
            return null;
        View child = mFactory.makeView(index,this);
        if (child == null) {
            return null;
        }
        mViewList.put(index, child);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        addView(child, lp);
        return child;
    }

    private void changeView(int hideIndex, int showIndex) {
        View v = mViewList.get(hideIndex);
        if (v != null) {
            v.setVisibility(View.GONE);
        }
        v = mViewList.get(showIndex);
        if (v != null) {
            v.setVisibility(View.VISIBLE);
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            if(lp.height==LayoutParams.WRAP_CONTENT||lp.height>0){
                ViewGroup.LayoutParams containerLp=this.getLayoutParams();
                containerLp.height=LayoutParams.WRAP_CONTENT;
                this.setLayoutParams(containerLp);
            }
        }
        if(onViewChangeListener!=null){
            onViewChangeListener.onChange(hideIndex, showIndex);
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ExViewSwitcher.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ExViewSwitcher.class.getName());
    }

    /**
     * Creates views in a ExViewSwitcher.
     */
    public interface ViewFactory {
        /**
         * Creates a new {@link View} to be added in a
         * {@link com.zhengjy.test.switcher.ExViewSwitcher}.
         *
         * @return a {@link View}
         */
        View makeView(int index, ViewGroup parent);
    }
    
    public interface OnViewChangeListener{
        void onChange(int hideTag, int showTag);
    }
}
