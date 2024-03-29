package com.zhongnongfuan.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class LauncherActivity extends Activity {

    /**
     * 所有需要的权限类型
     */
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private Intent mIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = new Intent();
        //动态获取权限
        getPermission();
    }

    /**
     * 申请完权限跳转到主页面
     */
    private void toMainActivity() {
        //start:事件序列的起始点，这里从0开始
        //count: 总的事件的数量，这里只需要发送一次，因此总的事件的数量是 1
        //initialDelay: 距离第一次返送间隔的时间
        //period: 间隔的时间（第一次发送除外）
        //time_unit:事件单位，这里使用的是秒
        Observable.intervalRange(0,1,2,3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mIntent.setClass(LauncherActivity.this,Main2Activity.class);
                        startActivity(mIntent);
                        finish();
                    }
                });
    }

    private void toLoginActivity() {
        Observable.intervalRange(0,1,2,3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mIntent.setClass(LauncherActivity.this,LoginActivity.class);
                        startActivity(mIntent);
                        finish();
                    }
                });
    }

    /**
     * 动态获取权限
     */
    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(LauncherActivity.this);
        rxPermissions.request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if(granted){
                    //判断是否登陆，如果没有登陆跳转到登陆页面
                    //如果登陆了跳转到主页面
                   /* SaveAndGetUser saveAndGetUser = new SaveAndGetUser(LauncherActivity.this);
                    if(saveAndGetUser.getUser()){
                        //暂时先跳转到登陆页面
                       toMainActivity();
                    }else{
                        mIntent.setClass(LauncherActivity.this,LoginActivity.class);
                        startActivity(mIntent);
                    }*/
                    toLoginActivity();
                }else{
                    //权限申请不成功，弹窗提示退出
                    showDialog();
                }
            }
        });
    }


    /**
     * 弹出对话框提示，往设置中给与权限否则应用程序退出
     */
    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("请开启相关权限，否则程序无法运行")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LauncherActivity.this.finish();
                    }
                })
                .show();
        //设置点击dialog外的屏幕，该dialog不会消失
        alertDialog.setCanceledOnTouchOutside(false);
    }

}
