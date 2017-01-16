package com.zhengjy.test.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.zhengjy.test.R;


/**
 * Created by zhengjy on 2017/1/9.
 */

public class YDialog {
    private Context mContext;
    private String mDialogTitle;
    private String mDialogMessage;
    private String mPositiveText;
    private String mNegativeText;

    private View mDialogView;
    private OnPositiveClickListener mPositiveClickListener;
    private OnNegativeClickListener mNegativeClickListener;

    //带有自定义view的构造器, dialogTitle 为 null 则不显示标题
    public YDialog(Context context, int customLayoutId, String dialogTitle) {
        this.mContext = context;
        this.mDialogTitle = dialogTitle;
        this.mDialogView = View.inflate(context, customLayoutId, null);
        //默认按钮取消和确定
        this.mPositiveText = mContext.getString(R.string.btn_confirm);
        this.mNegativeText = mContext.getString(R.string.btn_cancel);
    }

    //不带自定义view的构造器
    public YDialog(Context context, String dialogMessage, String dialogTitle) {
        this.mContext = context;
        this.mDialogTitle = dialogTitle;
        this.mDialogMessage= dialogMessage;
        //默认按钮取消和确定
        this.mPositiveText = mContext.getString(R.string.btn_confirm);
        this.mNegativeText = mContext.getString(R.string.btn_cancel);
    }

    /**
     * @param negativeText  null 则不显示此button
     * @return
     */
    public YDialog setNegativeText(String negativeText) {
        mNegativeText = negativeText;
        return this;
    }

    /**
     * @param positiveText null 则不显示此button
     * @return
     */
    public YDialog setPositiveText(String positiveText) {
        mPositiveText = positiveText;
        return this;
    }

    public View getDialogView() {
        return mDialogView;
    }

    public void setDialogView(View dialogView) {
        this.mDialogView = dialogView;
    }

    public void showDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(mContext);
        dialog.setTitle(mDialogTitle);
        //注意:dialogMessage和dialogView是互斥关系也就是dialogMessage存在dialogView就不存在,dialogView不存在dialogMessage就存在
        if (mDialogMessage != null) {
            dialog.setMessage(mDialogMessage);
        } else {
            dialog.setContentView(mDialogView);
        }
        dialog.setPositiveButton(mPositiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                if (mPositiveClickListener != null) {
                    mPositiveClickListener.positiveClickListener(mDialogView, dialogInterface, which);
                }
            }
        }).setNegativeButton(mNegativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                if (mNegativeClickListener != null) {
                    mNegativeClickListener.negativeClickListener(mDialogView, dialogInterface, which);
                }
            }
        }).create().show();
    }

    public YDialog setPositiveClickListener(OnPositiveClickListener positiveClickListener) {
        mPositiveClickListener = positiveClickListener;
        return this;
    }

    public YDialog setNegativeClickListener(OnNegativeClickListener negativeClickListener) {
        mNegativeClickListener = negativeClickListener;
        return this;
    }

    public interface OnPositiveClickListener {
        //customView　这个参数需要注意就是如果没有自定义view,那么它则为null
        void positiveClickListener(View customView, DialogInterface dialogInterface, int which);
    }

    public interface OnNegativeClickListener {
        void negativeClickListener(View customView, DialogInterface dialogInterface, int which);
    }
}
