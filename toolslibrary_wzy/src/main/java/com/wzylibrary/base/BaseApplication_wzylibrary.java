package com.wzylibrary.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by www on 2016/12/9.
 */
public class BaseApplication_wzylibrary extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static Context getMyApplicationContext(){
        return context;
    }
}
