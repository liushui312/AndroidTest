package com.zhengjy.test.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的Adapter
 * @param <Data> 数据类型
 */
abstract public class CommonAdapter<Data> extends BaseAdapter {
    protected List<Data> mDataList;
    protected Context mContext;

    public CommonAdapter(Context context) {
        super();
        mDataList = new ArrayList<>();
        mContext = context;
    }

    public void setData(List<Data> dataList){
        if (dataList != null && !dataList.isEmpty()) {
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if(mDataList != null){
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Data getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Data> getDataList(){
        return mDataList;
    }
}
