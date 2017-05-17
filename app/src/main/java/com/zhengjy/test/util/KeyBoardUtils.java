package com.zhengjy.test.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭软键盘
 * 
 * @author zhy
 * 
 */
public class KeyBoardUtils {
    public static final String ACTION_HIDE_IME_DIALOGS = "com.yealink.intent.hide_ime_dialogs";
	private static final String TAG = KeyBoardUtils.class.getSimpleName();
    
	/**
	 * 打开软键盘
	 * 
	 * @param editText
	 *            输入框
	 * @param context
	 *            上下文
	 */
	public static void openKeybord(EditText editText, Context context) {
		//YLog.d(TAG, "openKeybord->" + context);
		if (editText != null && context != null) {
			InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(editText, 0);
		}
	}

	/**
     * 关闭软键盘
     * 
     * @param view
     *            输入框
     * @param context
     *            上下文
     */
	public static void closeKeybord(View view, Context context) {
		if (view != null && context != null) {
			InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
/*	*//**
	 * 强制关闭软键盘，yealink定制的framework接口，APP内部推荐使用这个，简单安全保险。
	 * 适用于APP内部，无法关闭其他APP残留的输入法
	 * 
	 * @author liurs
	 * @param context
	 *//*
	public static void closeKeyboard(Context context){
		//YLog.d(TAG, "closeKeyboard->" + context);
	    if(context != null){
	        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_NEVER, 0);
	    }
	}*/
	
/*	*//**
     * 强制关闭全局软键盘，可以关闭其他应用打开的软键盘，yealink定制的framework接口
     * 
     * @author liurs
     * @param context
     *//*
	public static void closeGlobalKeyboard(Context context){
		//YLog.d(TAG, "closeGlobalKeyboard->" + context);
	    if(context != null){
	        context.getApplicationContext().sendBroadcast(new Intent(InputMethodManager.ACTION_FORCE_IME_HIDE));
	    }
	}*/
	
	/**
	 * 弹出或者隐藏输入法
	 * 
	 * @param v
	 * @param context
	 */
	public static void toggleKeybord(View v, Context context) {
		//YLog.d(TAG, "toggleKeybord->" + context);
		if (v != null && context != null) {
			InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInputFromWindow(v.getWindowToken(), 0, 0);
		}
	}
	
	/**
	 * 关闭输入法弹框，比如拼音输入法设置，LatinIME设置弹框
	 * 
	 * @param context
	 * @author liurs
	 */
	public static void closeImeDialogs(Context context){
		//YLog.d(TAG, "closeImeDialogs->" + context);
	    context.getApplicationContext().sendBroadcast(new Intent(ACTION_HIDE_IME_DIALOGS));
	}
}
