package com.zhongnongfuan.app.utils;

import android.app.Application;
import android.util.Log;

import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.MachineList;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 设置时要将配置文件的application名称设置为android:name=".utils.App"
 */
public class App extends Application {

    private static final String TAG = "App";
    List<DetailState> mDetailStateList;

    private MachineList mMachineList;

    public MachineList getMachineList() {
        return mMachineList;
    }

    public void setMachineList(MachineList machineList) {
        Log.i(TAG, "setMachineList: APP-设置机器集合：：：" + machineList.toString());
        mMachineList = machineList;
    }

    public List<DetailState> getDetailStateList() {
        Log.i(TAG, "APP-getDetailStateList: 获取的详细状态集合为：" + mDetailStateList);
        return mDetailStateList;
    }

    public void setDetailStateList(List<DetailState> detailStateList) {
        mDetailStateList = detailStateList;
        Log.i(TAG, "APP-setDetailStateList: 设置详细状态集合为：：" + mDetailStateList);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}