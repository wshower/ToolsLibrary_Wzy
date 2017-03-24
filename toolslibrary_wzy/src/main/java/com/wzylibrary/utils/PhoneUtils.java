package com.wzylibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by www on 2017/2/14.
 */
public class PhoneUtils {

    /**
     * 显示内存上限
     */
    public static void showMemory(Context context) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.i("== maxMemory", "Max memory is " + maxMemory + "KB");

        //每个程序都会有可使用的内存上限，这被称为堆大小（Heap Size）我们在开发应用程序时所使用的内存不能超出这个限制
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Log.i("== heapSize", "heapSize is " + manager.getMemoryClass() + "MB");
    }


    /**
     * 获取是否存在NavigationBar
     * http://blog.csdn.net/zjt593688/article/details/52606980
     */
    public static boolean isHaveNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }


    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

}
