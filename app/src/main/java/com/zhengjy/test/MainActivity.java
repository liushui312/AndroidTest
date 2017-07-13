package com.zhengjy.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.zhengjy.test.data.bean.Item;
import com.zhengjy.test.util.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_list) ListView mListView;
    private MainListAdapt mAdapt;

    public static void start(Activity a, Bundle data){
        Intent intent = new Intent();
        intent.setClass(a, MainActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        a.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("SmallFeaturesActivity", IntentUtils.SMALL_ACTION));
        list.add(new Item("LockActivity", IntentUtils.LOCK_ACTION));
        list.add(new Item("Crumb", IntentUtils.CRUMB_ACTION));
        list.add(new Item("ComImage", IntentUtils.COM_IMAGE_ACTION));
        list.add(new Item("CustomView", IntentUtils.CUSTOM_VIEW_ACTION));
        list.add(new Item("WidgetAcivity", IntentUtils.WIDGET_ACTION));
        list.add(new Item("Bar", IntentUtils.BAR_ACTION));
        mAdapt = new MainListAdapt(this);
        mAdapt.updateList(list);
        mListView.setAdapter(mAdapt);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
