package com.zhongnongfuan.app.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.MachineList;
import com.zhongnongfuan.app.bean.MonitorBean;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 设置时要将配置文件的application名称设置为android:name=".utils.App"
 */
public class App extends Application {

    private static final String TAG = "App";
    List<DetailState> mDetailStateList;

    private MachineList mMachineList;
    private static Context context;

    public MachineList getMachineList() {
        Log.i(TAG, "getMachineList:  APP-获取机器集合：：：" + mMachineList);
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
        context = getApplicationContext();
    }

    public ArrayList<LatLngBean.DataBean> getDataBeanList() {
        Log.i(TAG, "getDataBeanList: 获取DataBeanList的值为：：：：" + mDataBeanList);
        return mDataBeanList;
    }

    public void setDataBeanList(ArrayList<LatLngBean.DataBean> dataBeanList) {
        Log.i(TAG, "setDataBeanList: 设置DataBeanList的值为：：：：" + dataBeanList);
        mDataBeanList = dataBeanList;
    }

    private ArrayList<LatLngBean.DataBean> mDataBeanList;

    public static Context getAppContext(){
        return context;
    }



    private MonitorBean mMonitorBean;

    public MonitorBean getMonitorBean() {
        Log.i(TAG, "getMonitorBean: 获取监控列表：：：：：：：" + mMonitorBean);
        return mMonitorBean;
    }

    public void setMonitorBean(MonitorBean monitorBean) {
        Log.i(TAG, "setMonitorBean: 设置监控列表：：：：：" + monitorBean);
        mMonitorBean = monitorBean;
    }

}