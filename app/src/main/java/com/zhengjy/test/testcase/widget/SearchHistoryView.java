package com.zhengjy.test.testcase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhengjy.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjy on 2016/12/28.
 */

public class SearchHistoryView extends ViewGroup {
    static final String TAG = "HistoryShowView";

    private Context mContext;
    /**
     *  记录每一行View 的个数
     */
    private List<Integer> mLineCount = new ArrayList<>();

    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    /**
     *  行间距
     */
    private int mLineSpace = 20;

    private OnItemClickListener mOnItemClickListener;

    public SearchHistoryView(Context context) {
        super(context);
        mContext = context;
    }

    public SearchHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLineCount.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int lineCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            lineCount++;
            if (lineWidth + lp.leftMargin + lp.rightMargin + childWidth > width) {
                Log.d(TAG, "new line i:" + i + ", lineCount:" + (lineCount - 1));

                if (i == 0) {
                    mLineHeight.add(childHeight + lp.topMargin + lp.bottomMargin);
                    mLineCount.add(lineCount);
                    lineCount = 0;
                    continue;
                }
                mLineHeight.add(lineHeight);
                mLineCount.add(lineCount - 1);
                lineWidth = 0;
                lineHeight = childHeight;
                lineCount = 1;

                //补上最后一个
                if (i == getChildCount()-1) {
                    mLineHeight.add(lineHeight);
                    mLineCount.add(1);
                    Log.d(TAG, "new last line 1. mlineCount size:" + mLineCount.size());
                    break;
                }
            }

            if (i == getChildCount()-1) {
                mLineHeight.add(lineHeight);
                mLineCount.add(lineCount);
                Log.d(TAG, "new last line 2. lineCount size:" + lineCount);
            }

            //第一行的第一个元素进行保存
            lineWidth += lp.leftMargin + lp.rightMargin + childWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
        }

        //以下开始画此viewgroup第一行的时候左上都为0.
        int left = 0;
        int top = 0;
        int viewPosition = 0;
        for (int i = 0; i < mLineCount.size(); i++) {
            Log.d(TAG, "line " + i + ":" + mLineCount.get(i) + ", mLineHeight.get(i):"+mLineHeight.get(i));
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < mLineCount.get(i); j++) {
                Log.d(TAG, "viewPosition:" +viewPosition + ", total:" + getChildCount());
                if (viewPosition >= getChildCount()) {
                    break;
                }
                View view = getChildAt(viewPosition);
                viewPosition++;
                if (view.getVisibility() == View.GONE) {
                    //如果是不可见的那么不需要为它分配位置
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                int vl = left + lp.leftMargin;
                int vr = vl + view.getMeasuredWidth();
                int vt = top + lp.topMargin;
                int vb = vt + view.getMeasuredHeight();
                //为每个view分配具体位置
                Log.d(TAG, "view layout: vt:" + vt + ", vb:" + vb);
                view.layout(vl, vt, vr, vb);
                left += view.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            //下一行的初始化
            left = 0;
            top += lineHeight + mLineSpace;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获得父类的大小和设置方式
        int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "parentWidthSize: "+parentWidthSize+", parentHeightSize:" + parentHeightSize);

        int width = 0;
        int height = 0;

        int lineWidth = 0;

        int childSize = getChildCount();
        boolean isFirstViewOneLine = false;
        for (int i = 0; i < childSize; i++) {
            View view = getChildAt(i);
            if (view == null) {
                return;
            }
            //measure child获取child的大小，所有的view必须先measure一下然后通过
            //getMeasureWidth 和getMeasureHeight获取准确值
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = lp.leftMargin + view.getMeasuredWidth() + lp.rightMargin;
            int childHeight = lp.topMargin + view.getMeasuredHeight() + lp.bottomMargin;
            Log.d(TAG, "lineWidth:"+ lineWidth + ", childWidth: " + childWidth + ", childHeight:" + childHeight);
            if (i == 0) {
                //记录第一行的行高
                height = childHeight;
            } else if (i == 1 && isFirstViewOneLine){
                height += childHeight;
            }
            //判断此行是否超过了parent的宽
            if (lineWidth + childWidth > parentWidthSize) {
                Log.d(TAG, "new line");
                //因为此时已经占用的行宽加上当前的view的宽已经超过一行，需要换行
                if (i == 0) {
                    height += mLineSpace;
                    isFirstViewOneLine = true;
                    width = Math.max(lineWidth, childWidth);//取最大值
                    lineWidth = 0;
                    continue;
                }

                width = Math.max(lineWidth, childWidth);//取最大值
                lineWidth = childWidth; //新一行的宽就是当前view的宽。
                //增加新一行的行高
                height += childHeight + mLineSpace;
            } else {
                //如果当前一行没有满，那么增加行宽
                lineWidth += childWidth;
            }

            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childSize - 1) {
                width = Math.max(lineWidth, width);
            }
        }

        //真正对view的大小起作用的是这里。
        Log.d(TAG, "width: " + width + ", height" + height);
        if (width > parentWidthSize) {
            width = parentWidthMode;
        }
        setMeasuredDimension((parentWidthMode == MeasureSpec.EXACTLY ? parentWidthSize : width)
                , (parentHeightMode == MeasureSpec.EXACTLY ? parentHeightSize : height));
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setData(List<String> strings) {
        for (int position = 0; position < strings.size(); position++) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_history, this, false);
            TextView tvSearchHistory = (TextView) view.findViewById(R.id.tv_search_history);
            tvSearchHistory.setText(strings.get(position));
            final int pos = position;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, pos);
                    }
                }
            });
            addView(view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
