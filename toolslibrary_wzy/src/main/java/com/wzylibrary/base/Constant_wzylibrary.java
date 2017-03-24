package com.wzylibrary.base;

/**
 * Created by www on 2016/12/9.
 */
public class Constant_wzylibrary {

    /**
     * 在com.wzylibrary.utils.retrofit.RetrofitHelp中使用的BaseUrl
     *
     * 实际使用时：
     * 在app项目的BaseApplication中设置Constant_wzylibrary.BaseUrl = "http://192.168.1.244:888/";
     * 否则会报参数异常。
     *
     * 因为需要重新设定此值，所以此类不能为interface（接口中对于其成员变量，却只能是public static final 类型 ）
     */
    public static String BaseUrl = "请在项目的Application中修改：Constant_wzylibrary.BaseUrl = \"http://192.168.1.1:888/\"";
}
