package com.zhengjy.test.testcase.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhengjy.test.R;
import com.zhengjy.test.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhengjy on 2016/12/8.
 */

public class HistoryEidtFragment extends BaseFragment {
    private static final String TAG = "HistoryEidtFragment";
    private HistoryEdit mHistoryEdit;
    private ArrayList<String> mHistoryStrings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.fragment_history, container, false);
        mHistoryEdit = (HistoryEdit) view.findViewById(R.id.history_edit);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated. init mHistoryStrings");
        mHistoryStrings = new ArrayList<>();
        mHistoryStrings.add("AAAAA");
        mHistoryStrings.add("bbbbb");
        mHistoryStrings.add("ccccc");
        mHistoryStrings.add("test01");
        mHistoryStrings.add("test02");
        mHistoryStrings.add("test03");

        mHistoryEdit.setIHistoryEditListener(new HistoryEdit.IHistoryEditListener() {
            @Override
            public void OnHistorySelected(String str) {
                Log.d(TAG, "OnHistorySelected. str:" + str);
            }

            @Override
            public List<String> searchHistory(CharSequence key) {
                return searchFromHistory(key);
            }

            @Override
            public void clearHistory() {
                Log.d(TAG, "clearHistory");
            }
        });
    }

    private List<String> searchFromHistory(CharSequence key) {
        List<String> searchResult = new ArrayList<>();
        for (String string : mHistoryStrings) {
            if (string.contains(key)) {
                searchResult.add(string);
            }
        }
        return searchResult;
    }

    @Override
    public void onPause() {
        super.onPause();
        mHistoryEdit.updateHistoryData(null);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mHistoryEdit != null) {
            mHistoryEdit.updateHistoryData(mHistoryStrings);
        }
    }
}
