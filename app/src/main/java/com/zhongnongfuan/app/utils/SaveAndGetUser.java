package com.zhongnongfuan.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class SaveAndGetUser {

    private Context mContext;

    public SaveAndGetUser(Context context){
        mContext = context;
    }

    /**
     * 检查本地保存的是否有用户名和密码
     * 如果有则代表登陆过，可以直接进入到主页面
     * 没有就说明用户没有进行登陆
     * @return
     */
    public boolean getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_infor",Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name",null);
        String passWord = sharedPreferences.getString("user_password",null);
        if(userName == null || passWord == null){
            return false;
        }
        return true;
    }


    /**
     * 将用户的信息存储到本地
     * @param userName 用户名
     * @param passWord 密码
     */
    public void setUser(String userName,String passWord){
        //暂时通过普通方法进行保存
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_infor",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name",userName);
        editor.putString("user_password",passWord);
        editor.commit();
    }
}
