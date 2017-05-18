package com.zhengjy.test.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.zhengjy.test.R;
import com.zhengjy.test.base.CommonAdapter;

import java.util.ArrayList;


public class DragListAdapter extends CommonAdapter<String> {
    private static final String TAG = "DragListAdapter";

    private int mInvisiblePosition = -1;
    private boolean mIsChanged = true;
    private boolean mShowItem = false;

    private RemoveListener mRemoveListener;

    private ArrayList<String> mCopyList = new ArrayList<>();

    private int mLastFlag = -1;
    private int mHeight;

    public DragListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //在这里尽可能每次都进行实例化新的，这样在拖拽ListView的时候不会出现错乱.
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_codec_enable_list, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.content);
        textView.setText(getDataList().get(position));
        convertView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoveListener != null) {
                    mRemoveListener.onRemoveListen(getDataList().get(position));
                }
            }
        });

        if (mIsChanged) {
            Log.i(TAG, "position:" + position + ", holdPosition:" + mInvisiblePosition);
            if (position == mInvisiblePosition) {
                if (!mShowItem) {
                    convertView.setVisibility(View.INVISIBLE);
                }
            }
            if (mLastFlag == 1) {
                if (position > mInvisiblePosition) {
                    Animation animation;
                    animation = getFromSelfAnimation(0, -mHeight);
                    convertView.startAnimation(animation);
                }
            } else if (mLastFlag == 0) {
                if (position < mInvisiblePosition) {
                    Animation animation;
                    animation = getFromSelfAnimation(0, mHeight);
                    convertView.startAnimation(animation);
                }
            }
        }
        return convertView;
    }

    public void showDropItem(boolean showItem) {
        this.mShowItem = showItem;
    }

    public void setInvisiblePosition(int position) {
        mInvisiblePosition = position;
    }

    public void setRemoveListener(RemoveListener removeListener) {
        mRemoveListener = removeListener;
    }

    public void exchangeCopy(int startPosition, int endPosition) {
        Object startObject = getCopyItem(startPosition);
        Log.i(TAG, "startPosition:" + startPosition + ", endPosition:" + endPosition);
        if (startPosition < endPosition) {
            mCopyList.add(endPosition + 1, (String) startObject);
            mCopyList.remove(startPosition);
        } else {
            mCopyList.add(endPosition, (String) startObject);
            mCopyList.remove(startPosition + 1);
        }
        mIsChanged = true;
    }

    public Object getCopyItem(int position) {
        return mCopyList.get(position);
    }

    public void copyList() {
        mCopyList.clear();
        for (String str : getDataList()) {
            mCopyList.add(str);
        }
    }

    public void pasteList() {
        getDataList().clear();
        for (String str : mCopyList) {
            getDataList().add(str);
        }
    }

    public void setLastFlag(int flag) {
        mLastFlag = flag;
    }

    public void setHeight(int value) {
        mHeight = value;
    }

    public Animation getFromSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);
        go.setFillAfter(true);
        go.setDuration(100);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }

    public interface RemoveListener {
        void onRemoveListen(String content);
    }
}