package com.wzylibrary.utils;

import java.text.DecimalFormat;

/**
 * Created by www on 2017/2/14.
 */
public class NumberUtils {

    /**
     * 将字符串转为保留两位的小数,出错返回0.00
     */
    public static Double string2decimal_2wei(String s){

        try {
            DecimalFormat df = new DecimalFormat("0.00");
            Double d = Double.parseDouble(df.format(Double.parseDouble(s)));
            return d;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.00;
        }
    }


    /**
     * 将一个Double类型的数变为保留2位小数的String
     */
    public static String double2string_2wei(Double d){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 将一个Double类型的数变为保留2位小数的Double
     */
    public static Double double2double_2wei(Double d){
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(d));
    }
}
