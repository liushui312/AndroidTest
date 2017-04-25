package com.zhengjy.test.switcher;

import android.view.View;
import android.view.ViewGroup;

/**
 * 视图生产者接口
 * @author chenhn 2016年5月3日 14:59:32
 * 
 */
public interface IViewCreator {
    // 创建View
    View createConvertView(ViewGroup parent);

    // View的唯一标识
    int getTag();

    // 绑定监听器
    void bindListener();

    void initView();

    void hide();
}
