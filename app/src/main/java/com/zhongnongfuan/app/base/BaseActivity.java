package com.zhongnongfuan.app.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.eventbus.MessageEvent;
import com.zhongnongfuan.app.utils.App;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());//必须在setContentView之前
        setContentView(R.layout.activity_base);
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(this, "收到消息：：" + event.getMessage(), Toast.LENGTH_SHORT).show();
        Log.i("", "message is " + event.getMessage());
        // 更新界面
    }

    //作用：传递参数
    public App getApp() {
        return (App) getApplicationContext();
    }
}
