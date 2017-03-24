package com.wzylibrary.tools.retrofit;

import android.util.Log;

import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  Callback<T>的实现类，T即ApiService中的方法返回类型
 *
 *  定义了接口onMyRetrofitCallback，用来回调此实现类中的一些方法（状态为0、1，失败）
 *
 *  此实现类实现了官方的Callback，封装了一些情况下（如返回码200、错误，服务器返回状态为0、1）处理的事情，
 *  同时又定义了接口，在接口中统一实现具体的处理方法，，
 *  实际使用过程中，可以在此类的构造方法中传入接口的实现类OnMyRetrofitCallBackImp，也可以不传或不用super方法，灵活使用
 *
 *  参考了http://blog.csdn.net/free_co/article/details/51726311
 *
 * Created by www on 2016/12/8.
 */
public class MyRetrofitCallback  implements Callback<ResponseBean>{


    public MyRetrofitCallback(onMyRetrofitCallback myRetrofitCallback){
        this.myRetrofitCallback = myRetrofitCallback;
    }

    @Override
    public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {

        myRetrofitCallback.onAlways();

        if (response.code() == 200) {
            if ("1".equals(response.body().getState())) {
//                Logger.i(response.body().getData());
                Logger.json(response.body().getData());

                myRetrofitCallback.onState_1(response.body());
            } else if ("0".equals(response.body().getState())) {

                myRetrofitCallback.onStatus_0(response.body());
            }
        }else {
            myRetrofitCallback.onFail("ErrCode:"+response.code()+":"+response.errorBody());
            Log.i("== onFail", "ErrCode:"+response.code()+":"+response.errorBody());
        }

    }

    @Override
    public void onFailure(Call<ResponseBean> call, Throwable t) {

        Log.i("== onFailure", t.toString()+"");
        myRetrofitCallback.onFail("请检查网络");
        myRetrofitCallback.onAlways();
    }


    onMyRetrofitCallback myRetrofitCallback;
    interface onMyRetrofitCallback{
        void onState_1(ResponseBean responseBean);
        void onStatus_0(ResponseBean responseBean);
        void onFail(String msg);
        /**
         * onResponse以后总是干的事，用来取消一些dialog、刷新之类的
         */
        void onAlways();
    }
}
