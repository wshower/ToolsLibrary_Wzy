package com.wzylibrary.tools.retrofit;

/**
 * Created by www on 2016/12/8.
 */
public class RequestBean {
    private String userCode;
    private String device;
    private String token;
    private String data;

    public RequestBean(String userCode, String token, String data) {
        this.userCode = userCode;
        this.device = "android";
        this.token = token;
        this.data = data;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
