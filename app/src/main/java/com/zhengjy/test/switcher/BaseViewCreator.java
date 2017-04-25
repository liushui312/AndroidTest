package com.zhengjy.test.switcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 基础视图生成类
 * @author chenhn 2016年5月3日 15:39:12
 *
 */
public abstract class BaseViewCreator implements IViewCreator {
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;

    public BaseViewCreator() {
        mViews = new SparseArray<View>();
    }

    @Override
    public void bindListener() {

    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub

    }

    @Override
    final public View createConvertView(ViewGroup parent) {
        mConvertView = createView(parent);
        mContext = parent.getContext();
        return mConvertView;
    }

    abstract public View createView(ViewGroup parent);

    @Override
    public void hide() {
        if (mConvertView != null
                && mConvertView.getVisibility() != View.VISIBLE) {
            mConvertView.setVisibility(View.GONE);
        }
    }

    /**
     * 通过viewId获取控件
     * 
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     * 
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewCreator setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    
    /**
     * 设置图片
     * @param viewId
     * @param resId
     * @return
     */
    public BaseViewCreator setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    
    /**
     * 设置图片
     * @param viewId
     * @param bitmap
     * @return
     */
    public BaseViewCreator setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    
    /**
     * 设置图片
     * @param viewId
     * @param drawable
     * @return
     */
    public BaseViewCreator setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
    
    /**
     * 设置背景色
     * @param viewId
     * @param color
     * @return
     */
    public BaseViewCreator setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }
    
    /**
     * 设置背景色
     * @param viewId
     * @param backgroundRes
     * @return
     */
    public BaseViewCreator setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
    
    /**
     * 设置字体颜色
     * @param viewId
     * @param textColor
     * @return
     */
    public BaseViewCreator setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    
    /**
     * 设置字体颜色
     * @param viewId
     * @param textColorRes
     * @return
     */
    public BaseViewCreator setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }
    
    /**
     * 设置透明度（SDK>11）
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewCreator setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }
    
    /**
     * 设置可见/隐藏
     * @param viewId
     * @param visible
     * @return
     */
    public BaseViewCreator setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    
    /**
     * 设置文本
     * @param viewId
     * @return
     */
    public BaseViewCreator linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }
    
    /**
     * 
     * @param typeface
     * @param viewIds
     * @return
     */
    public BaseViewCreator setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public BaseViewCreator setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseViewCreator setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BaseViewCreator setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public BaseViewCreator setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BaseViewCreator setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BaseViewCreator setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewCreator setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseViewCreator setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public BaseViewCreator setOnClickListener(int viewId,
            View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewCreator setOnTouchListener(int viewId,
            View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewCreator setOnLongClickListener(int viewId,
            View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
