package com.zhongnongfuan.app.bean;

import java.io.Serializable;

public class VideoParam implements Serializable {
    private String appKey;
    private String accessToken;
    private String url;

    public VideoParam(String appKey, String accessToken, String url) {
        this.appKey = appKey;
        this.accessToken = accessToken;
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VideoParam{" +
                "appKey='" + appKey + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
