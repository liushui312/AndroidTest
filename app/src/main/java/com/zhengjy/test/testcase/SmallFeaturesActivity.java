package com.zhengjy.test.testcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zhengjy.test.R;
import com.zhengjy.test.util.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmallFeaturesActivity extends Activity {
    @BindView(R.id.tmp_textview) TextView mTextView;
    @OnClick(R.id.add_shortcut_btn) void addShortCut() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    Log.d("liushui", "add shortcut: " + i);
                    addShortcut("shortcut" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @OnClick(R.id.remove_shortcut_btn) void removeShortCut() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    Log.d("liushui", "remove shortcut: " + i);
                    removeShortcut("shortcut" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
    }

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static final String ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";
    private void addShortcut(String name) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        addShortcutIntent.putExtra("duplicate", false);//不允许重复创建
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this,
                        R.mipmap.ic_launcher));
        // 设置关联程序
        Intent launcherIntent = new Intent(IntentUtils.LOCK_ACTION);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        sendBroadcast(addShortcutIntent);
    }

    private void removeShortcut(String name) {
        Intent intent = new Intent(ACTION_REMOVE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 设置关联程序
        Intent launcherIntent = new Intent(IntentUtils.LOCK_ACTION);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        sendBroadcast(intent);
    }
}
