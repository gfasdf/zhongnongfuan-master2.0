package com.zhongnongfuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 机器具体信息
 */
public class MachineActivity extends AppCompatActivity {


    @BindView(R.id.machine_toolbar)
    Toolbar machineToolbar;
    @BindView(R.id.name)
    TextView name;//机器名称
    @BindView(R.id.tv_machine_State)
    TextView tvMachineState;//当前机器的状态
    @BindView(R.id.tv_fan1_state)
    TextView tvFan1State;//风机1
    @BindView(R.id.tv_fan2_state)
    TextView tvFan2State;//风机2
    @BindView(R.id.tv_lift_motor_state)
    TextView tvLiftMotorState;//提升机
    @BindView(R.id.tv_discharge_grain_motor_state)
    TextView tvDischargeGrainMotorState;//排粮电机
    @BindView(R.id.tv_belt_motor_state)
    TextView tvBeltMotorState;//皮带电机
    @BindView(R.id.tv_wind_temperature)
    TextView tvWindTemperature;//风温
    @BindView(R.id.tv_foodstuff_temperature)
    TextView tvFoodstuffTemperature;//粮温
    @BindView(R.id.tv_dew)
    TextView tvDew;//水分
    @BindView(R.id.tv_weight)
    TextView tvWeight;//重量

    Intent intent;
    private static final int ERROR = 0;
    private static final int CHANG_UI = 1;
    DetailState detailState;
    String machineDeviceId;
    String deviceName;
    Message message;
    DetailState.DataBean mDataBean;
    Map<String, String> paramMap;
    Timer mTimer;
    String devicePath = Prefix.PREFIX + "Android/SBZT";
    @BindView(R.id.tv_somke_motor_state)
    TextView tvSomkeMotorState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_layout);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent!=null){
            if (intent.getExtras()!= null){
                if ("MainActivity".equals(intent.getStringExtra("activity"))){
                    machineDeviceId = intent.getStringExtra("deviceId");
                    deviceName = intent.getStringExtra("deviceName");
                    Log.i("", "onCreate: intent来自MainActivity：：：：deviceId为: "+ machineDeviceId + " deviceName为： "+ deviceName);
                }else{
                    Log.i("", "onCreate: 数据来自receiver");
                    Bundle bundle = intent.getExtras();  //取出来的是个数组
                    //获取bundle中所有key的值
                    Set<String> getKey = bundle.keySet();
                    for (String key : getKey) {
                        if ("cn.jpush.android.ALERT".equals(key)) {
                            Log.i("", "onCreate: 获取的推送消息::::" + key);
                            String str = bundle.getString("cn.jpush.android.ALERT");
                            String[] arr = str.split(",");
                            String[] arr1;
                            for (int i = 0; i < arr.length; i++) {
                                arr1 = arr[i].split(":");
                                if ("设备号".equals(arr1[0])){
                                    machineDeviceId = arr1[1];
                                    Log.i("", "onCreate: 获取的推送设备号为：：：：：" + arr1[1]);
                                }else if ("名称".equals(arr1[0])){
                                    deviceName = arr1[1];
                                    Log.i("", "onCreate: 获取的推送机器名称为：：：：：" + arr1[1]);
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.i("用户选择的机器的设备编号为：：：",   machineDeviceId + "   名称为：：：" + deviceName);

        initToolBar();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.i("MainActivity:", "run: 定时器：：：：：刷新数据：：：：");
                loadData(machineDeviceId);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(task, 0, 50000);
    }

    public void loadData(String deviceId) {
        Log.i("", "loadData: 读取机器具体信息：：：：：：" + deviceId);
        paramMap = new HashMap<>();
        paramMap.put("deviceId", deviceId);
        MyNetWork myNetWork = MyNetWork.getInstance(this);

        myNetWork.postAsynHttp(devicePath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MachineActivity.this, "读取机器详细信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) throws IOException {
                Gson gson = new Gson();
                detailState = gson.fromJson(str, DetailState.class);
                if (detailState.getCode() == 1) {
                    mDataBean = detailState.getData();
                    Log.i("MachineActivity:", "onResponse: 获取机器具体参数为：： " + str);
                    name.setText(deviceName);
                    tvMachineState.setText(mDataBean.getGzzt());
                    if ("报警".equals(mDataBean.getGzzt())){
                        tvMachineState.setBackgroundResource(R.drawable.off);
                    }
                    if ("warning".equals(mDataBean.getGzzt())) ;
                    setBackground(tvFan1State, mDataBean.getFj1());
                    setBackground(tvFan2State, mDataBean.getFj2());
                    setBackground(tvLiftMotorState, mDataBean.getTsj());
                    setBackground(tvDischargeGrainMotorState, (String) mDataBean.getPldj());
                    setBackground(tvBeltMotorState, (String) mDataBean.getPddj());
                    setBackground(tvSomkeMotorState, (String) mDataBean.getSmoke());
                    //风温过高
                    if ("".equals(mDataBean.getFw())) {
                        tvWindTemperature.setBackgroundResource(R.drawable.other_alarm);
                    }
                    setWinFoodBackground(tvWindTemperature, mDataBean.getFw()+"");
                    tvWindTemperature.setText(mDataBean.getFw() + "");
                    tvFoodstuffTemperature.setText(mDataBean.getLw() + "");
                    tvDew.setText(mDataBean.getSf() + "");
                    tvWeight.setText(mDataBean.getZl() + "");
                } else {
                    Toast.makeText(MachineActivity.this, "获取机器详细信息失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initToolBar() {
        machineToolbar.setTitle("详细状态信息");
        setSupportActionBar(machineToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        machineToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //风温、粮温背景设置
    public void setWinFoodBackground(TextView textView, String str){
        if ("warn".equals(str)) {
            Log.i("MachineActivity:", "setBackground: 状态为on，设置str为开");
            textView.setText("警告");
            textView.setBackgroundResource(R.drawable.off);
        } else {
            textView.setText(str);
            textView.setBackgroundResource(R.drawable.open);
        }
    }
    //电机工作状态背景设置
    public void setBackground(TextView textView, String str) {
        Log.i("MachineActivity:", "setBackground: setBackground开始运行:::, 获取的str为：：" + str);
        if ("warn".equals(str)) {
            Log.i("MachineActivity:", "setBackground: 状态为on，设置str为开");
            textView.setText("警告");
            textView.setBackgroundResource(R.drawable.off);
        } else {
            Log.i("MachineActivity:", "setBackground: 状态为off，设置str为开");
            if ("on".equals(str)) {
                textView.setText("开");
            } else if ("off".equals(str)) {
                textView.setText("关");
            }
            textView.setBackgroundResource(R.drawable.open);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();//销毁时关闭定时器
        super.onDestroy();
    }
}
