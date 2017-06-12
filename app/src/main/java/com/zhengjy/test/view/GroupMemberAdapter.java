package com.zhengjy.test.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhengjy.test.data.bean.TypeItem;
import com.zhengjy.test.testcase.CustomView.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *  ListView 实现GridView 的效果
 * Created by zhengjy on 2017/1/10.
 */

public class GroupMemberAdapter extends BaseAdapter {
    private static final String TAG = "GroupMemberAdapter";

    public static final int TYPE_MEMBER = 0;
    public static final int TYPE_ADD_MEMBER = 1;
    public static final int TYPE_REMOVE_MEMBER = 2;

    private int mNumColumn = 1;
    private Context mContext;
    List<TypeItem> mList;
    private OnItemClickListenerCustom mOnItemClickListenerCustom;

    public GroupMemberAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public synchronized void updateData(List<TypeItem> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setNumColumn(int numColumn) {
        if (numColumn > 0) {
            mNumColumn = numColumn;
        }
    }

    public void setOnItemClickListenerCustom(OnItemClickListenerCustom onItemClickListenerCustom) {
        mOnItemClickListenerCustom = onItemClickListenerCustom;
    }

    @Override
    public int getCount() {
        if (mList.size() % mNumColumn > 0) {
            return mList.size() / mNumColumn + 1;
        } else {
            return mList.size() / mNumColumn;
        }
    }

    @Override
    public Object getItem(final int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        List<ViewHolder> viewHolders;
        int count;
        if (mNumColumn * (position+1) < mList.size()) {
            count = mNumColumn;
        } else {
            count = (mList.size() - mNumColumn * position);
        }
        if (convertView == null || ((List<ViewHolder>)convertView.getTag()).size() != count) {
            viewHolders = new ArrayList<>();
            if (mNumColumn > 1) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i < count; i++) {
/*                    View item = LayoutInflater.from(mContext).inflate(R.layout.item_create_group_member, linearLayout, false);
                    linearLayout.addView(item);
                    viewHolders.add(new ViewHolder(item));*/
                }

                convertView = linearLayout;
                convertView.setTag(viewHolders);
            } else {
                //convertView = LayoutInflater.from(mContext).inflate(R.layout.item_create_group_member, parent, false);
                viewHolders.add(new ViewHolder(convertView));
                convertView.setTag(viewHolders);
            }
        } else {
            viewHolders = (List<ViewHolder>) convertView.getTag();
        }

        if (viewHolders.size() > 1) {
            for (int i = 0; i < viewHolders.size(); i++) {
                initConvertView(viewHolders.get(i), mNumColumn*position + i);
            }
        } else {
            if (mNumColumn == 1) {
                initConvertView(viewHolders.get(0), position);
            } else {
                initConvertView(viewHolders.get(0), mNumColumn*position);
            }
        }

        return convertView;
    }

    private void initConvertView(ViewHolder viewHolder, final int position) {
        if (position >= mList.size()) {
            return;
        }
    }

    static class ViewHolder {
        View root;
        CircleImageView icon;
        TextView name;

        public ViewHolder(View rootView) {
            //icon = (CircleImageView) rootView.findViewById(R.id.item_member_icon);
            //name = (TextView) rootView.findViewById(R.id.item_member_name);
            root = rootView;
        }
    }

    public interface OnItemClickListenerCustom {
        void onItemClickCustom(int position);
    }
}
