package com.wzylibrary.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 文件 工具类
 * Created by www on 2017/2/14.
 */
public class FilesUtils {
    /**
     * 从Raw文件中读取：
     * res  R.raw.test
     */
    public static String getFromRaw(Context context, int res){
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().openRawResource(res));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    /**
     * 直接从assets读取
     */
    public static String getFromAssets(Context context,String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
