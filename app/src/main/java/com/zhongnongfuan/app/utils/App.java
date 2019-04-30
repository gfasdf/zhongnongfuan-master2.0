package com.zhongnongfuan.app.utils;

import android.app.Application;
import android.util.Log;

import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.Machine;

import java.util.List;

/**
 * 设置时要将配置文件的application名称设置为android:name=".utils.App"
 */
public class App extends Application {

    private static final String TAG = "App";
    List<DetailState> mDetailStateList;
    List<String> nameList;

    private Machine mMachineList;

    public Machine getMachineList() {
        return mMachineList;
    }

    public void setMachineList(Machine machineList) {
        Log.i(TAG, "setMachineList: APP-设置机器集合：：：" + machineList);
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

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        Log.i(TAG, "setNameList: 设置的namelist值为：：： " + nameList);
        this.nameList = nameList;
    }

}