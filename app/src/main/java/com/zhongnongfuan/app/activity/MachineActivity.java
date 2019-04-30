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

import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.ParseUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * @author qichaoqun
 * @date 2019/1/19
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
    String machineName;
    Message message;
    DetailState.DataBean mDataBean;
    Map<String, String> paramMap;
/*    @BindView(R.id.tv_time)
    TextView tvTime;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_layout);
        ButterKnife.bind(this);

        intent = getIntent();
        machineName = intent.getStringExtra("machine_name");
        Log.i("用户选择的机器的名字为：：：", "onCreate: " + machineName);

        String devicePath = Prefix.PREFIX + "Android/SBZT";
        paramMap = new HashMap<>();
        paramMap.put("deviceId", "5678");
        MyNetWork myNetWork = MyNetWork.getInstance(this);

        myNetWork.postAsynHttp(devicePath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MachineActivity.this, "读取机器详细信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) throws IOException {
                detailState = ParseUtil.parseDeviceJson(str);
                if (detailState.getCode() == 1) {
                    mDataBean = detailState.getData();
                    Log.i("MachineActivity:", "onResponse: 获取机器具体参数为：： " + str);
                    name.setText(machineName);
                    tvMachineState.setText(mDataBean.getGzzt());
                    setBackground(tvFan1State, mDataBean.getFj1());
                    setBackground(tvFan2State, mDataBean.getFj2());
                    setBackground(tvLiftMotorState, mDataBean.getTsj());
                    setBackground(tvDischargeGrainMotorState, mDataBean.getPldj());
                    setBackground(tvBeltMotorState, mDataBean.getPddj());
//                    tvTime.setText(mDataBean.getSj());
                    //风温过高
                    if(mDataBean.getFw() > 20){
                        tvWindTemperature.setBackgroundResource(R.drawable.other_alarm);
                    }
                    tvWindTemperature.setText(mDataBean.getFw() + "");
                    tvFoodstuffTemperature.setText(mDataBean.getLw() + "");
                    tvDew.setText(mDataBean.getSf() + "");
                    tvWeight.setText(mDataBean.getZl() + "");
                } else {
                    Toast.makeText(MachineActivity.this, "获取机器详细信息失败", Toast.LENGTH_SHORT).show();
                }

            }
        });

        initToolBar();
    }

    private void initToolBar() {
        machineToolbar.setTitle("详细状态信息");
        setSupportActionBar(machineToolbar);
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

    public void setBackground(TextView textView, String str) {
        Log.i("MachineActivity:", "setBackground: setBackground开始运行:::, 获取的str为：：" + str);
        if ("on".equals(str)) {
            Log.i("MachineActivity:", "setBackground: 状态为on，设置str为开");
            textView.setText("开");
            textView.setBackgroundResource(R.drawable.open);
        } else if ("off".equals(str)) {
            Log.i("MachineActivity:", "setBackground: 状态为off，设置str为开");
            textView.setText("关");
            textView.setBackgroundResource(R.drawable.off);
        }
    }

}
