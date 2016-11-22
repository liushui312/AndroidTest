package com.zhengjy.test.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhengjy.test.R;
import com.zhengjy.test.ui.fragment.ContactLogFragment;

/**
 * Created by yl1222 on 2016/11/15.
 */

public class GroupApplicationActivity extends Activity {
    private static final String TAG = GroupApplicationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_application);
        initView();
    }

    private void initView() {
        Button button = (Button) findViewById(R.id.btn_send);
        final EditText editText = (EditText) findViewById(R.id.et_verify_message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "send: " + editText.getText());
            }
        });

        findViewById(R.id.llyt_tmp).setVisibility(View.GONE);

        ContactLogFragment fragment = ContactLogFragment.getInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.contact_log_fragment, fragment);
        transaction.commit();
    }

}
