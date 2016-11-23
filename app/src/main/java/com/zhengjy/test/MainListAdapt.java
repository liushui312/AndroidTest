package com.zhengjy.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhengjy.test.data.bean.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjy on 2016/9/8.
 */
public class MainListAdapt extends BaseAdapter {

    private Context mContext;
    private List<Item> mList;

    public MainListAdapt(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    public synchronized void updateList(List<Item> list) {
        if (list != null) {
            this.mList.clear();
            this.mList.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(mList.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).launch(mContext);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
