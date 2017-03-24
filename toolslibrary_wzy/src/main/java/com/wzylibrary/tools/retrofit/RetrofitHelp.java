package com.wzylibrary.tools.retrofit;

import com.wzylibrary.base.Constant_wzylibrary;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 设置 Retrofit
 *
 * 参考了
 * https://github.com/jaydenxiao2016/AndroidFire项目中的Api（私有构造方法初始化，传参）
 * https://github.com/hugeterry/coderfun项目中的retrofit包（获取ApiService的单例，createAPI方法）、
 * https://github.com/ahmclishihao/gankiodemo项目中的NetHelper（静态代码块初始化（不能修改参数，没有使用））
 *
 *
 * Created by www on 2016/12/7.
 */
public class RetrofitHelp {

    private static Retrofit retrofit = null;
    private static RetrofitHelp mRetrofitHelp = null;

    //无参的构造方法使用默认的BaseUrl，
    private RetrofitHelp(){
        new RetrofitHelp(Constant_wzylibrary.BaseUrl);
    }

    //构造方法私有
    private RetrofitHelp(String base_Url){
        HttpLoggingInterceptor logging  = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(base_Url)
                .client(okHttpClient)
                //不能同时添加多个 addConverterFactory，ScalarsConverterFactory不能和@Body一起使用
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create()) //字符串
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    /**
     * 由retrofit得到一个网络请求接口的实现类
     *
     * @param serviceClass 定义请求方法的接口类，ApiService.class
     * @param base_Url 不传参则使用默认的BaseUrl，传参则修改BaseUrl
     * @param <T>
     * @return  由retrofit得到一个网络请求接口的实现类
     */
    public static <T>T createAPI(Class<T> serviceClass,String... base_Url) {

        return getInstance(base_Url).retrofit2Create(serviceClass);
    }

    /**
     * 获得一个RetrofitHelp的单例
     * @param base_Url 不传参则使用默认的BaseUrl，传参则修改BaseUrl
     * @return
     */
    private static RetrofitHelp getInstance(String... base_Url){
        if (mRetrofitHelp == null) {
            //不传参则使用无参构造函数，默认的BaseUrl
            if (base_Url.length == 0) {
                mRetrofitHelp = new RetrofitHelp();
            } else {
                mRetrofitHelp = new RetrofitHelp(base_Url[0]) ;
            }
        }
        return mRetrofitHelp;
    }


    /**
     * 获取请求的接口类型的Service
     * 即Retrofit中的方法：retrofit.create(final Class<T> service)
     * noted by wzy (2016/12/7  17:41)
     */
    public <T> T retrofit2Create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }


}
