package com.zhengjy.test.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zhengjy.test.R;
import com.zhengjy.test.util.OnNoDoubleClickListener;

/**
 * Created by yl1222 on 2016/11/11.
 */

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText etUser;
    private EditText etPsw;
    private EditText etServer;
    private CheckBox ckbRememberPsw;
    private TextView tvForgetPsw;
    private CheckBox ckbAutoLogin;
    private Button btnLogin;
    private TextView tvChangeLogin;
    private boolean isUserMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean isFirstIn = getIntent().getBooleanExtra(WelcomeActivity.IS_FIRST_IN_KEY, false);
        Log.d(TAG, "onCreate. is first in:"+ isFirstIn);
        initView(isFirstIn);

    }

    private void initView(boolean isFirstIn) {
        etUser = (EditText) findViewById(R.id.et_user);
        etPsw = (EditText) findViewById(R.id.et_psw);
        etServer = (EditText) findViewById(R.id.et_server);
        ckbRememberPsw = (CheckBox) findViewById(R.id.ckb_remember_psw);
        tvForgetPsw = (TextView) findViewById(R.id.tv_forget_psw);
        ckbAutoLogin = (CheckBox) findViewById(R.id.ckb_auto_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvChangeLogin = (TextView) findViewById(R.id.tv_change_login);
        if (isFirstIn) {
            isUserMode = false;
            changeLoginMode(false);
        } else {
            isUserMode = true;
            changeLoginMode(true);
        }
        btnLogin.setOnClickListener(mOnNoDoubleClickListener);
        tvChangeLogin.setOnClickListener(mOnNoDoubleClickListener);
    }

    private void changeLoginMode(boolean isUserMode) {
        if (isUserMode) {
            etPsw.setVisibility(View.VISIBLE);
            etUser.setHint("用户名");
            ckbRememberPsw.setVisibility(View.VISIBLE);
            tvForgetPsw.setVisibility(View.VISIBLE);
            tvChangeLogin.setText("使用pin码登录");
        } else {
            etPsw.setVisibility(View.GONE);
            etUser.setHint("pin码");
            ckbRememberPsw.setVisibility(View.GONE);
            tvForgetPsw.setVisibility(View.GONE);
            tvChangeLogin.setText("使用用户登录");
        }
    }


    private OnNoDoubleClickListener mOnNoDoubleClickListener = new OnNoDoubleClickListener() {
        @Override
        public void OnNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.tv_change_login:
                    isUserMode = !isUserMode;
                    changeLoginMode(isUserMode);
                    break;

                case R.id.btn_login:
                    startActivity(new Intent(LoginActivity.this, GroupApplicationActivity.class));
                    break;

                case R.id.tv_forget_psw:
                    Log.d(TAG, "click tv forget psw");
                    break;
            }
        }
    };

    private void updateUIForLoginBegin() {
        btnLogin.setText("登录中...");
    }

    private void updateUIForLoginCompeletion() {
        btnLogin.setText("登录");
    }


}
