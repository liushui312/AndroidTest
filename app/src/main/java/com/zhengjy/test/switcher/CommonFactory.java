package com.zhengjy.test.switcher;

import android.view.View;
import android.view.ViewGroup;


/**
 * 配合{@link ExViewSwitcher}使用的视图工厂类
 * @author chenhn 2016年5月3日 14:53:36
 *
 * @param <T> 视图生成者类
 */
public class CommonFactory<T extends IViewCreator> implements ExViewSwitcher.ViewFactory {

    IViewCreator[] mCreators;

    public CommonFactory(IViewCreator... creators) {
        mCreators = creators;
    }

     /**
      * 根据视图生产者创建视图
      */
    @Override
    public View makeView(int tag, ViewGroup parent) {
        if (mCreators == null)
            return null;
        for (int i = 0; i < mCreators.length; i++) {
            if (mCreators[i] == null)
                continue;
            if (mCreators[i].getTag() == tag) {
                //创建View
                View view = mCreators[i].createConvertView(parent);
                //初始化View
                mCreators[i].initView();
                //绑定监听器
                mCreators[i].bindListener();
                return view;
            }
        }
        return null;
    }

    /**
     * 通过视图的tag获取对应的生产者对象
     * @param tag 视图Id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <Create extends T> Create getCreatorByTag(int tag) {
        Create create = null;
        try {
            for (IViewCreator item : mCreators) {
                if (item != null && item.getTag() == tag) {
                    return (Create) item;
                }
            }
        } catch (Exception e) {
            // donothing
        }
        return create;
    }

}
