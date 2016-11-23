package com.zhengjy.test.data.bean;

import android.content.Context;
import android.content.Intent;

/**
 *  每个Item 对应一个Activity，代表一个testcase
 */
public class Item {
    private String name;
    private String action;

    public Item(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public void launch(Context context) {
        if (action != null) {
            Intent intent = new Intent(action);
            context.startActivity(intent);
        }
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
