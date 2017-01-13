package com.zhengjy.common.utils;

import android.content.res.Resources;

import com.zhengjy.common.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 获取规范的时间
     * < 24h  15:02:02
     * 24h~48h 昨天 15:02:02
     * 48h~7天内 周一 15:02:02
     * 今年内7天外 02/02 15:02:02
     * 去年 2015/02/02 15:02:02
     * @param time
     * @param res
     * @return
     */
    public static String getFormatTimeStr(long time, Resources res){
        long currentTime = new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        Calendar curCalendar = Calendar.getInstance();
        curCalendar.setTimeInMillis(currentTime);
//        YLog.i(TAG,"DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR) +" - " + curCalendar.get(Calendar.DAY_OF_YEAR));
        if(calendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == curCalendar.get(Calendar.DAY_OF_YEAR)){
            String tempelte = "%02d:%02d:%02d";
            return  String.format(tempelte,hour,minute,second);
        }else if(calendar.get(Calendar.DAY_OF_YEAR) == curCalendar.get(Calendar.DAY_OF_YEAR)-1){
            String tempelte = "%s %02d:%02d:%02d";
            return String.format(tempelte,res.getString(R.string.yesterday),hour,minute,second);
        }else if(calendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) > curCalendar.get(Calendar.DAY_OF_YEAR) - 7){
            String tempelte = "%s %02d:%02d:%02d";
            String dayOfWeek = "";
            switch (calendar.get(Calendar.DAY_OF_WEEK) ){
                case Calendar.SUNDAY:
                    dayOfWeek = res.getString(R.string.sunday);
                    break;
                case Calendar.MONDAY:
                    dayOfWeek = res.getString(R.string.monday);
                    break;
                case Calendar.TUESDAY:
                    dayOfWeek = res.getString(R.string.tuesday);
                    break;
                case Calendar.WEDNESDAY:
                    dayOfWeek = res.getString(R.string.wednesday);
                    break;
                case Calendar.THURSDAY:
                    dayOfWeek = res.getString(R.string.thursday);
                    break;
                case Calendar.FRIDAY:
                    dayOfWeek = res.getString(R.string.friday);
                    break;
                case Calendar.SATURDAY:
                    dayOfWeek = res.getString(R.string.saturday);
                    break;
            }
            return String.format(tempelte,dayOfWeek,hour,minute,second);
        }else if(calendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR) ){
            String tempelte = "%02d/%02d %02d:%02d:%02d";
            return  String.format(tempelte,month,date,hour,minute,second);
        } else {
            String tempelte = "%4d/%02d/%02d %02d:%02d:%02d";
            return  String.format(tempelte,year,month,date,hour,minute,second);
        }
    }
}
