package com.zhengjy.test.testcase.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhengjy.test.R;

import java.util.List;

/**
 * Created by zhengjy on 2016/12/08.
 */
public class HistoryEdit extends EditText {
    private static final String TAG = "HistoryEdit";
    private Resources mRes;
    private Context mContext;
    private PopupWindow popupWindow;
    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private IHistoryEditListener mIHistoryEditListener;
    private int MAX = 3;
    private int mPopupWindowHeight;

    public HistoryEdit(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HistoryEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mRes = context.getResources();
        TypedArray typedArray = mRes.obtainAttributes(attrs, R.styleable.HistoryEditAttrs);
        try {
            mPopupWindowHeight = typedArray.getDimensionPixelOffset(R.styleable.HistoryEditAttrs_popupwindow_height, 90);
            Log.d(TAG, "mPopupWindowHeight:" + mPopupWindowHeight);
        }finally {
            typedArray.recycle();
        }
        initView();
    }

    public void setIHistoryEditListener(IHistoryEditListener IHistoryEditListener) {
        mIHistoryEditListener = IHistoryEditListener;
    }


    /**
     * 初始化一个LinearLayout布局用于显示在popupwindow上
     */
    private void initView() {
        mScrollView = new ScrollView(mContext);
        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mScrollView.addView(mLinearLayout);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIHistoryEditListener != null) {
                    List<String> searchResult = mIHistoryEditListener.searchHistory(s);
                    for (int i = 0; i < searchResult.size(); i++) {
                        Log.d(TAG, "i:"+i+", searchResult: "+ searchResult.get(i));
                    }
                    updateHistoryData(searchResult);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置历史数据的方法
     *
     * @param strings
     */
    public void updateHistoryData(List<String> strings) {
        mLinearLayout.removeAllViews();
        if (strings == null || strings.size() == 0) {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            return;
        }

        int count = Math.min(strings.size(), MAX);
        Log.d(TAG, "updateHistoryData. count:" + count);

        for (int i = 0; i < count; i++) {
            final TextView textView = new TextView(mContext);
            textView.setText(strings.get(i));
            textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setText(textView.getText().toString());
                    popupWindow.dismiss();
                    if (mIHistoryEditListener != null) {
                        mIHistoryEditListener.OnHistorySelected(textView.getText().toString());
                    }
                }
            });
            mLinearLayout.addView(textView);
        }

        //添加消除历史记录的textview
        TextView textView = new TextView(mContext);
        textView.setText("消除历史记录");
        textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIHistoryEditListener != null) {
                    mIHistoryEditListener.clearHistory();
                }
            }
        });
        mLinearLayout.addView(textView);

        showWindow();
    }

    public void showWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        } else if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAsDropDown(this);
            return;
        }

        popupWindow = new PopupWindow(mScrollView, getWidth(), mPopupWindowHeight);
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.quanaplha));
        popupWindow.update();
        popupWindow.setFocusable(false);
        popupWindow.showAsDropDown(this);
    }

    public interface IHistoryEditListener {
        void OnHistorySelected(String str);
        List<String> searchHistory(CharSequence key);
        void clearHistory();
    }
}
