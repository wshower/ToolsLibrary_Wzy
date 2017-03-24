package com.wzylibrary.tools.retrofit;

import android.text.TextUtils;

import com.wzylibrary.base.BaseApplication_wzylibrary;
import com.wzylibrary.view.SuperCustomToast;


/**
 * 实现 MyRetrofitCallback 中定义的接口方法，
 * 可以让实际使用中灵活实现接口方法
 *
 * Created by www on 2016/12/8.
 */
public class OnMyRetrofitCallBackImp implements MyRetrofitCallback.onMyRetrofitCallback {

    SuperCustomToast toast = SuperCustomToast.getInstance(BaseApplication_wzylibrary.getMyApplicationContext());

    @Override
    public void onState_1(ResponseBean responseBean) {

    }

    @Override
    public void onStatus_0(ResponseBean responseBean) {

        String message = responseBean.getMessage();
        if (!TextUtils.isEmpty(message)) {
//            Toast.makeText(BaseApplication_wzylibrary.getMyApplicationContext(), message, Toast.LENGTH_SHORT).show();
            toast.showSameMsg(message);
        }
    }

    @Override
    public void onFail(String msg) {
//        Toast.makeText(BaseApplication_wzylibrary.getMyApplicationContext(), "[请求失败] "+msg, Toast.LENGTH_SHORT).show();
        toast.showSameMsg("[请求失败] "+msg);
    }

    @Override
    public void onAlways() {

    }

}
