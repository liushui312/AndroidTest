package com.zhengjy.test.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * 对话框基类
 *
 * Created by liurs on 2017/11/2.
 */

public abstract class AbsBaseDialog {
	protected final Context mContext;
	protected Dialog mDialog;
	private DialogInterface.OnClickListener mOnClickLsnr;
	/** 对话框关闭监听器 **/
	private DialogInterface.OnDismissListener mOutterDismissLsnr;
	/** 按键监听器 **/
	private DialogInterface.OnKeyListener mOutterKeyLsnr;


	private DialogInterface.OnDismissListener mDismissLsnr = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			if(mOutterDismissLsnr != null){
				mOutterDismissLsnr.onDismiss(dialog);
			}
			AbsBaseDialog.this.onDismiss();
		}
	};

	private DialogInterface.OnKeyListener mKeyLsnr = new DialogInterface.OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if(mOutterKeyLsnr != null && mOutterKeyLsnr.onKey(dialog, keyCode, event)){
				return true;
			}

			return onKeyEvent(event);
		}
	};

	public AbsBaseDialog(Context context) {
		mContext = context;
	}

	/**
	 * 在调用show时，才会创建对话框
	 *
	 * @return
	 */
	protected abstract Dialog createDialog();

	/**
	 * 显示对话框
	 */
	public void show(){
		if(mDialog == null){
			mDialog = createDialog();
		}
		mDialog.setOnDismissListener(mDismissLsnr);
		mDialog.setOnKeyListener(mKeyLsnr);
		mDialog.show();
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss(){
		if(mDialog != null){
			mDialog.dismiss();
		}
	}

	/**
	 * 对话框消失时回调该接口
	 */
	protected void onDismiss() {
		// child implement
	}

	/**
	 * 设置对话框消失监听
	 *
	 * @param lsnr
	 */
	public AbsBaseDialog setOnDismissListener(DialogInterface.OnDismissListener lsnr) {
		mOutterDismissLsnr = lsnr;
		return this;
	}

	/**
	 * 设置点击监听
	 *
	 * @param lsnr
	 */
	public void setOnClickListener(DialogInterface.OnClickListener lsnr){
		mOnClickLsnr = lsnr;
	}

	/**
	 * 设置按键事件监听器
	 *
	 * @param lsnr
	 */
	public void setOnKeyListener(DialogInterface.OnKeyListener lsnr){
		mOutterKeyLsnr = lsnr;
	}

	protected void performClick(int which) {
		if(mOnClickLsnr != null){
			mOnClickLsnr.onClick(mDialog, which);
		}
		dismiss();
	}

	/**
	 * 按键事件处理
	 *
	 * @param event
	 * @return
	 */
	public boolean onKeyEvent(KeyEvent event) {
		return false;
	}

/*	public String translate(String translateId) {
		return LanguageManager.getInstance().translate(translateId);
	}*/

}
