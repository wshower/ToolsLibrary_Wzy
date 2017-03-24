package com.wzylibrary.utils;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by www on 2017/2/14.
 */
public class ScreenUtils {

    /**
     * 获取屏幕分辨率
     */
    public static int[] getScreenResolution(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();//手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        Log.i("width  higth", width + "   " + height);
        return result;
    }
    /**
     * 获取屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    /**
     * 获取屏幕的高
     */
    public static int getScreenHight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
