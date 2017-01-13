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

public class HistoryshowFragment extends BaseFragment {
    private static final String TAG = "HistoryshowFragment";
    private SearchHistoryView mSearchHistoryView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.fragment_history_show, container, false);
        mSearchHistoryView = (SearchHistoryView) view.findViewById(R.id.history_show);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<String> searchHistory = new ArrayList<>();
        searchHistory.add("米兰达");
        searchHistory.add("Brtney");
        searchHistory.add("布拉德Brad");
        searchHistory.add("布朗恩");
        searchHistory.add("厦门亿联网络技术股份有限公司");
        searchHistory.add("安格斯");
        searchHistory.add("切图");
        searchHistory.add("产品设计一部");
        searchHistory.add("罗伯特");
        searchHistory.add("罗伯特");

        mSearchHistoryView.setData(searchHistory);
        mSearchHistoryView.setOnItemClickListener(new SearchHistoryView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick. position:" + position);
            }
        });
    }

}
