//package com.wzylibrary.utils.retrofit;
//
//import com.wzylibrary.base.Constant_wzylibrary;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * 设置 Retrofit
// *
// * 参考了
// * https://github.com/jaydenxiao2016/AndroidFire项目中的Api（私有构造方法初始化，传参）
// * https://github.com/hugeterry/coderfun项目中的retrofit包（获取ApiService的单例，createAPI方法）、
// * https://github.com/ahmclishihao/gankiodemo项目中的NetHelper（静态代码块初始化（不能修改参数，没有使用））
// *
// *
// * (这个类是修改之前的RetrofitHelp，这个类中的ApiService还没有通过传参获得，，
// *  修改后的RetrofitHelp的请求方法通过传参获得，不知道会不会有问题，所以先留着这个类)
// *
// * Created by www on 2016/12/7.
// */
//public class RetrofitHelp_beforeChange {
//
//    private static Retrofit retrofit = null;
//
//    private static ApiService apiService = null;
//
//    //无参的构造方法使用默认的BaseUrl，
//    private RetrofitHelp_beforeChange(){
//        new RetrofitHelp_beforeChange(Constant_wzylibrary.BaseUrl);
//    }
//
//    //构造方法私有
//    private RetrofitHelp_beforeChange(String base_Url){
//        HttpLoggingInterceptor logging  = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build();
//
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(base_Url)
//                .client(okHttpClient)
//                //不能同时添加多个 addConverterFactory，ScalarsConverterFactory不能和@Body一起使用
//                .addConverterFactory(GsonConverterFactory.create())
////                .addConverterFactory(ScalarsConverterFactory.create()) //字符串
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//    }
//
//
//    /**
//     * 由retrofit得到一个网络请求接口的实现类
//     * @param base_Url 不传参则使用默认的BaseUrl，传参则修改BaseUrl
//     * @return
//     */
//    public static ApiService getInstance(String... base_Url){
//        if (apiService == null) {
//            synchronized (ApiService.class) {
//                if (apiService == null) {
//                    //不传参则使用无参构造函数，默认的BaseUrl
//                    if (base_Url.length == 0) {
//                        apiService = new RetrofitHelp_beforeChange().createAPI(ApiService.class);
//                    } else {
//                        apiService = new RetrofitHelp_beforeChange(base_Url[0]).createAPI(ApiService.class);
//                    }
//                }
//            }
//        }
//        return apiService;
//    }
//
//
//    /**
//     * 获取请求的接口类型的Service
//     * 即Retrofit中的方法：retrofit.create(final Class<T> service)
//     * noted by wzy (2016/12/7  17:41)
//     */
//    public <T> T createAPI(Class<T> serviceClass) {
//        return retrofit.create(serviceClass);
//    }
//
//
//}
