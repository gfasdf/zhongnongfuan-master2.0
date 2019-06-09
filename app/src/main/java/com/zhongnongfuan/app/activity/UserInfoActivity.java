package com.zhongnongfuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.LoginResponseBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户信息
 */
public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.machine_toolbar_user)
    Toolbar machineToolbarUser;
    @BindView(R.id.tv_user_accuounts)
    TextView tvUserAccuounts;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_sex)
    TextView tvUserSex;
    @BindView(R.id.tv_user_department)
    TextView tvUserDepartment;
    @BindView(R.id.tv_user_tel)
    TextView tvUserTel;
    private Intent mIntent;
    private LoginResponseBean mLoginResponseBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        Log.i("", "onNavigationItemSelected: 跳转至用户信息界面.......................");
        mIntent = getIntent();
        mLoginResponseBean = (LoginResponseBean) mIntent.getSerializableExtra("userInfo");
        initToolBar();
        initView();
    }

    private void initView() {
        setText(tvUserAccuounts, mLoginResponseBean.getData().getUserName());
        setText(tvUserName, mLoginResponseBean.getData().getName());
        setText(tvUserSex, mLoginResponseBean.getData().getGender());
        setText(tvUserDepartment, mLoginResponseBean.getData().getDepartName());
        setText(tvUserTel, mLoginResponseBean.getData().getPhone());
    }
    //设置各个组件显示的字
    private void setText(TextView textView, Object userName) {
        boolean isNull = isEmpty(userName);
        if (isNull){
            textView.setText("暂无");
        }else {
            textView.setText(userName.toString());
        }
    }

    private void initToolBar() {
        machineToolbarUser.setTitle("用户信息");
        setSupportActionBar(machineToolbarUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        machineToolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //是否为空
    public static boolean isEmpty(Object str){
        if(str==null||"".equals(str)){
            return true;
        }
        return false;
    }
}
