package com.zhengjy.test.testcase.combination;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircularImageView extends View {

    private static final String TAG = "CircularImageView";
    protected int mViewWidth;
    protected int mViewHeight;

    private List<Paint> mPaints;
    private ArrayList<Bitmap> mBitmaps;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int dimen = Math.min(width, height);
        setMeasuredDimension(dimen, dimen);
    }

    /**
     *   Sets a List of Bitmap as the content of this CircularImageView
     *
     * @param bitmaps The bitmaps to set
     */
    public void setImageBitmaps(ArrayList<Bitmap> bitmaps) {
        if (bitmaps == null) {
            throw new IllegalArgumentException("bitmaps can not be null");
        }
        if (bitmaps.size() > JoinLayout.HEAD_COUNT_MAX) {
            throw new IllegalArgumentException("bitmaps size can not be greater than " + JoinLayout.HEAD_COUNT_MAX);
        }
        mBitmaps = bitmaps;
        mPaints = JoinBitmaps.getPaints(mViewWidth, bitmaps);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mViewWidth = mViewHeight = Math.min(w, h);
        mPaints = JoinBitmaps.getPaints(mViewWidth, mBitmaps);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mViewWidth > 0 && mViewHeight > 0) {
            JoinBitmaps.join(canvas, mPaints, mViewWidth);
        }
    }
}
