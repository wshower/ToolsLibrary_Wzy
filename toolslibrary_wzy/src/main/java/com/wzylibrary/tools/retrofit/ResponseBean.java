package com.wzylibrary.tools.retrofit;

/**
 * 获取到的最外层的json键，传入ApiService中的Call<T>
 * {"state":"0","message":"没有数据！","data":""}
 * Created by www on 2016/12/5.
 */
public class ResponseBean {
//    {"state":"1","message":"","data":"
    private String state;
    private String message;
    private String data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "state='" + state + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
