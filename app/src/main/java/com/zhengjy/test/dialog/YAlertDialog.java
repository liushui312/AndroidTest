package com.zhengjy.test.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhengjy.test.R;


/**
 * Created by zhengjy on 2017/4/27.
 */

public class YAlertDialog {
    private final View mView;
    private TextView mMessage;
    private TextView mBtnCancel;
    private TextView mBtnOk;
    private Context mContext;

    private AlertDialog mDialog;

    public YAlertDialog(Context context) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.common_dialog, null);
        mMessage = (TextView) mView.findViewById(R.id.message);
        mBtnCancel = (TextView) mView.findViewById(R.id.button_cancel);
        mBtnOk = (TextView) mView.findViewById(R.id.button_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setCancelable(true);
        b.setView(mView);
        mDialog = b.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setMessage(String message) {
        if (mMessage != null) {
            mMessage.setText(message);
        }
    }

    public void setMessage(int resId) {
        if (mMessage != null) {
            mMessage.setText(resId);
        }
    }

    public void setPositiveButton(final DialogInterface.OnClickListener onClickListener) {
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
                }
                mDialog.dismiss();
            }
        });
    }

    public void setPositiveButton(String text, final DialogInterface.OnClickListener onClickListener) {
        mBtnOk.setText(text);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
                }
                mDialog.dismiss();
            }
        });
    }

    public void setNegativeButton(String text, final DialogInterface.OnClickListener onClickListener) {
        mBtnCancel.setText(text);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
                }
                mDialog.dismiss();
            }
        });
    }

    public void setNegativeButton(final DialogInterface.OnClickListener onClickListener) {
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
                }
                mDialog.dismiss();
            }
        });
    }

    public void show() {
        mDialog.show();
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();
        p.width = mContext.getResources().getDimensionPixelOffset(R.dimen.common_dialog_width);
        p.height = mContext.getResources().getDimensionPixelOffset(R.dimen.common_dialog_height);
        mDialog.getWindow().setAttributes(p);
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void setCancelable(boolean flag) {
        mDialog.setCancelable(flag);
    }
}
