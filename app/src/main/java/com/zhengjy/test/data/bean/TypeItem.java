package com.zhengjy.test.data.bean;

/**
 *   用于BaseAdapter中Item有不同的数据
 *
 * Created by zhengjy on 2017/1/13.
 */
public class TypeItem {
    private int mType;
    private Object mData;

    public TypeItem(int type, Object data) {
        this.mType = type;
        this.mData = data;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }
}
