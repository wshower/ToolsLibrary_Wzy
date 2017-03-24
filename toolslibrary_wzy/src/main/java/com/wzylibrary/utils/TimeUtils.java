package com.wzylibrary.utils;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by www on 2017/2/14.
 */
public class TimeUtils {


    //******************************************** 格式化时间 类似"三分钟前"  Start ******************************************

    private static final int seconds_of_1minute = 60;
    private static final int seconds_of_30minutes = 30 * 60;
    private static final int seconds_of_1hour = 60 * 60;
    private static final int seconds_of_1day = 24 * 60 * 60;
    private static final int seconds_of_15days = seconds_of_1day * 15;
    private static final int seconds_of_30days = seconds_of_1day * 30;
    private static final int seconds_of_6months = seconds_of_30days * 6;
    private static final int seconds_of_1year = seconds_of_30days * 12;
    /**
     * 距离现在经过的时间，分为:
     * 刚刚，1-29分钟前，半小时前，1-23小时前，1-14天前，半个月前，1-5个月前，半年前，1-xxx年前
     */
    public static String getTimeElapse(String datestr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
//            Date date = format.parse("2013-11-11 18:35:35");
            Date date = format.parse(datestr);
            long nowTime = new Date().getTime() / 1000;
            //createTime是发表文章的时间
            long oldTime = date.getTime() /1000;
            //elapsedTime是发表和现在的间隔时间
            long elapsedTime = nowTime - oldTime;
            Logger.i(nowTime+"\n"+oldTime);
            if (elapsedTime < seconds_of_1minute) {
                return "刚刚";
            }
            if (elapsedTime < seconds_of_30minutes) {
                return elapsedTime / seconds_of_1minute + "分钟前";
            }
            if (elapsedTime < seconds_of_1hour) {
                return "半小时前";
            }
            if (elapsedTime < seconds_of_1day) {
                return elapsedTime / seconds_of_1hour + "小时前";
            }
            if (elapsedTime < seconds_of_15days) {
                return elapsedTime / seconds_of_1day + "天前";
            }
            if (elapsedTime < seconds_of_30days) {
                return "半个月前";
            }
            if (elapsedTime < seconds_of_6months) {
                return elapsedTime / seconds_of_30days + "月前";
            }
            if (elapsedTime < seconds_of_1year) {
                return "半年前";
            }
            if (elapsedTime >= seconds_of_1year) {
                return elapsedTime / seconds_of_1year + "年前";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    //******************************************** 格式化时间 类似"三分钟前"  End ********************************************

}
