package com.zhongnongfuan.app.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zhongnongfuan.app.bean.MachineList;
import com.zhongnongfuan.app.eventbus.MessageEvent;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoadInfoUtil {
    private static final int SUCCESS_CODE = 1;
    private static final int Error_CODE = 0;
    private static final String TAG = "LoadInfoUtil";
    private static Map<String, String> paramMap;
    private static int count = 1;
    private static MachineList machines;
    private static boolean isFirstLoad = true;
    private static Context mContext;
    private boolean isInstanced = false;
    private static LoadInfoUtil mInstance;

    public LoadInfoUtil() {
    }

    public static LoadInfoUtil getInstance(){
        if(mInstance == null){
            synchronized (LoadInfoUtil.class){
                if(mInstance == null){
                    mInstance = new LoadInfoUtil();
                }
            }
        }
        return mInstance;
    }

    //获取机器列表
    public void loadDeviceListData(final Context context, String userId, String deviceListPath) {
        mContext = context;
        MyNetWork myNetWork1 = MyNetWork.getInstance(context);
        paramMap = new HashMap<>();
        paramMap.put("userName", userId);
        myNetWork1.postAsynHttp(deviceListPath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.i(TAG, "onError: 机器列表访问错误信息为：：：：" + e.getMessage());
                EventBus.getDefault().post(new MessageEvent("deviceList is null!"));
            }

            @Override
            public void onResponse(final String str) throws IOException {
                Log.i(TAG, "onResponse: 机器列表：：：：：" + str);
                EventBus.getDefault().post(new MessageEvent(str));
            }
        });
    }


    //访问网络，获取经纬度
    public void loadLatLng(final Context context, String userId, String LatLonPath){
        MyNetWork myNetWork1 = MyNetWork.getInstance(context);
        paramMap = new HashMap<>();
        paramMap.put("userName", userId);
        myNetWork1.postAsynHttp(LatLonPath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                EventBus.getDefault().post(new MessageEvent("get latlng failed!"));
                Log.i(TAG, "onError: 经纬度访问错误信息为：：：：" + e.getMessage());
            }
            @Override
            public void onResponse(String str) throws IOException {
                Log.i(TAG, "onResponse: 坐标列表：：：：：" + str);
                EventBus.getDefault().post(new MessageEvent(str));
            }
        });
    }

    //监控列表
    public void loadMonitorList(final Context context, String userName, String monitorListPath) {
        paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        MyNetWork myNetWork = MyNetWork.getInstance(context);
        myNetWork.postAsynHttp(monitorListPath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(context, "获取摄像头列表失败", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MessageEvent("monitorList is null!"));
            }
            @Override
            public void onResponse(String str) throws IOException {
                Log.i(TAG, "onResponse: 获取的摄像头列表数据为：：：：：： " + str);
                EventBus.getDefault().post(new MessageEvent(str));
             /*   Gson gson = new Gson();
                MonitorBean mMonitorBean = gson.fromJson(str, MonitorBean.class);
                if (mMonitorBean.getCode() == 1){
                    if (mMonitorBean.getData().size() == 0){
                        Toast.makeText(context, "监控设备列表为空!", Toast.LENGTH_SHORT).show();
                    }else{
                        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(context, mMonitorBean.getData());
                        monitor_recycler_view.setAdapter(mVideoRecyclerViewAdapter);
                        mVideoRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                if (CommonUtils.isFastDoubleClick()) {
                                    Toast.makeText(context, "操作过于频繁", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("", "onItemClickListener: 点击监控列表中某一项：：：：：：：： " + position);
                                    imitatePlayButton("ezopen://"+mMonitorBean.getData().get(position).getYZM()+"@open.ys7.com/" + mMonitorBean.getData().get(position).getXLH() + "/1.hd.live", AppKey, mMonitorBean.getAccesstoken());
                                }
                            }
                        });
                    }

                }else if(mMonitorBean.getCode() == 0){
                    Toast.makeText(context, "获取监控设备列表失败！", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    //作用：传递参数
    public App getApp() {
        return (App) mContext.getApplicationContext();
    }

}
