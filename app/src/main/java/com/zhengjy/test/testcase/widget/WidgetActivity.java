package com.zhengjy.test.testcase.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zhengjy.test.R;
import com.zhengjy.test.base.BaseFragmentActivity;

/**
 * Created by zhengjy on 2016/12/8.
 */

public class WidgetActivity extends BaseFragmentActivity {
    private static final String TAG = "WidgetActivity";

    LinearLayout mLlytBtns;

    private HistoryEidtFragment mHistoryEidtFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_widget);

        mLlytBtns = (LinearLayout) findViewById(R.id.llyt_btns);
        mCurrentFragment = new HistoryEidtFragment();
        //mFragmentManager.beginTransaction().add(R.id.widget_fragment, mCurrentFragment).commit();

        findViewById(R.id.btn_history_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "historyEditOnClick");
                if (mHistoryEidtFragment == null) {
                    mHistoryEidtFragment = new HistoryEidtFragment();
                }
                switchFragment(R.id.widget_fragment, mHistoryEidtFragment);
                mLlytBtns.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mLlytBtns.setVisibility(View.VISIBLE);
    }


}
