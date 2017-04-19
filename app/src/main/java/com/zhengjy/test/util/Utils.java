package com.zhengjy.test.util;

import java.util.Date;

/**
 * Created by zhengjy on 2017/3/16.
 */

public class Utils {
    private static int mId = 1;
    public synchronized static String generateRandomId(){
        return String.valueOf(new Date().getTime() + mId++);
    }
}
