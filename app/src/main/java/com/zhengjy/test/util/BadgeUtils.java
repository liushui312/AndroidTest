package com.zhengjy.test.util;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.zhengjy.test.MainApplication;

import java.lang.reflect.Field;

/**
 * 角标设置工具类
 * Created by chenhn on 2017/5/10.
 */

public class BadgeUtils {

    public static final String launcherClassName = "com.yealink.videophone.WaitingActivity";//启动的Activity完整名称
    
    public static final String TYPE_SONY = "sony";
    public static final String TYPE_VIVO = "vivo";
    public static final String TYPE_OPPO = "oppo";
    public static final String TYPE_SUMXING = "sumxing";
    public static final String TYPE_XIAOMI = "mi";
    public static final String TYPE_HUAWEI = "hauwei";
    public static final String TYPE_MEIZU = "meizu";
    public static final String TYPE_DEFAULT = "unknow";



    public static String getType(){
        if(Build.MODEL.toLowerCase().contains("mi")){
            return TYPE_XIAOMI;
        } else if(Build.MODEL.toLowerCase().contains("huawei")){
            return TYPE_HUAWEI;
        } else if(Build.MODEL.toLowerCase().contains("sony")){
            return TYPE_SONY;
        } else if(Build.MODEL.toLowerCase().contains("sumxing")){
            return TYPE_SUMXING;
        } else if(Build.MODEL.toLowerCase().contains("oppo")){
            return TYPE_OPPO;
        } else if(Build.MODEL.toLowerCase().contains("vivo")){
            return TYPE_VIVO;
        }else if(Build.MODEL.toLowerCase().contains("meizu")){
            return TYPE_MEIZU;
        }
        return TYPE_DEFAULT;
    }

    public static void setBadge(int number,Notification notification){
        Context context = MainApplication.getInstance();
        try {
            switch (getType()){
                case TYPE_HUAWEI:
                    setHuaWeiBadge(context,number);
                    break;
                case TYPE_XIAOMI:
                    setXiaoMiBadge(context,number,notification);
                    break;
                case TYPE_SUMXING:
                    setSumxingBadge(context,number);
                    break;
                case TYPE_OPPO:
                    setOppoBadge(context,number);
                    break;
                case TYPE_SONY:
                    setSonyBadge(context,number);
                    break;
                case TYPE_VIVO:
                    setVivoBadge(context,number);
                    break;
                case TYPE_DEFAULT:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 华为角标
     * @param context
     */
    public static void setHuaWeiBadge(Context context, int number){
        if (launcherClassName != null) {
            Bundle extra = new Bundle();
            extra.putString("package", context.getPackageName());
            extra.putString("class", launcherClassName);
            extra.putInt("badgenumber", number);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);
        }
    }

    /**
     * 设置小米角标
     */
    public static void setXiaoMiBadge(Context context, int number, Notification notification){
        try{
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification,number);
            notification.getClass().getField("extraNotification").set(notification, miuiNotification);
        }catch (Exception e){
            e.printStackTrace();
            // miui6之前
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name",launcherClassName);
            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(number==0?"":number));
            context.sendBroadcast(localIntent);
        }
    }

    /**
     * 设置三星角标
     * @param context
     * @param number
     */
    public static void setSumxingBadge(Context context, int number){
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count",99);
        intent.putExtra("badge_count_package_name",context.getPackageName());
        intent.putExtra("badge_count_class_name",launcherClassName);
        context.sendBroadcast(intent);
    }

    /**
     * 设置索尼手机角标
     * @param context
     * @param number
     */
    private static void setSonyBadge(Context context, int number) {
        boolean isShow = true;
        if ("0".equals(number)) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",launcherClassName );//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", number);//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }

    /**
     * Vivo手机角标
     * @param context
     * @param number
     */
    private static void setVivoBadge(Context context, int number){
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", launcherClassName);
        intent.putExtra("notificationNum", number);
        context.sendBroadcast(intent);
    }
    /**
     * 设置Oppo手机角标
     * @param context
     * @param number
     */
    private static void setOppoBadge(Context context, int number){
        try {
            Bundle extras = new Bundle();
            extras.putInt("app_badge_count", number);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", String.valueOf(number), extras);
        } catch (Throwable th) {

        }
    }
}
