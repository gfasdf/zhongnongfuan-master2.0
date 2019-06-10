package com.zhongnongfuan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.LoginResponseBean;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.SaveAndGetUser;
import com.zhongnongfuan.app.utils.TagAliasOperatorHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Request;

import static com.zhongnongfuan.app.utils.TagAliasOperatorHelper.ACTION_SET;
import static com.zhongnongfuan.app.utils.TagAliasOperatorHelper.sequence;
/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class LoginActivity extends Activity {

    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.input_user_name)
    EditText mNameEdit;
    @BindView(R.id.input_user_password)
    EditText mPassEdit;
    private MyNetWork mMyNetWork;

    String loginPath = Prefix.PREFIX+"Android/Login";
    Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        initData();
        loginButtonClick();

    }

    private void initData() {
        SaveAndGetUser saveAndGetUser = new SaveAndGetUser(this);
        List<String> list = saveAndGetUser.getUser();//第一个数据为用户名，第二个数据为密码
        if (list != null){
            mNameEdit.setText(list.get(0));
            mPassEdit.setText(list.get(1));
        }
    }

    /**
     * 防止登陆按钮重复点击，由此进入主页面
     */
    private void loginButtonClick() {
        RxView.clicks(loginButton).throttleFirst(1, TimeUnit.SECONDS);
        RxView.clicks(loginButton).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object value) {
                /* ProgressShowDialog showDialog = new ProgressShowDialog(LoginActivity.this);
                        showDialog.show();*/
                //获取用户输入的值
                final String userName = mNameEdit.getText().toString().trim();
                final String passWord = mPassEdit.getText().toString().trim();
                if(userName.isEmpty()){
                    Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                }else if(passWord.isEmpty()){
                    Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if((!userName.isEmpty()) && (!passWord.isEmpty())){
                    Log.i("用户名是多少：：", "loginClick: " + userName);
                    Log.i("用户密码是多少：：；", "loginClick: " + passWord);
                    map.put("userName", userName);
                    map.put("passWord", passWord);
                    MyNetWork myNetWork = MyNetWork.getInstance(LoginActivity.this);
                    myNetWork.postAsynHttp(loginPath, map, new ResultCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.i("LoginActivity:", "onError: 登录失败连接：：：：：：");
                            Toast.makeText(LoginActivity.this, "登录失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String str) throws IOException {
                            Log.i("LoginActivity:", "onResponse: 登录成功：：：：：：获取的str为::::" + str);
                            Gson gson = new Gson();
                            LoginResponseBean loginResponseBean =  gson.fromJson(str, LoginResponseBean.class);
                            if (loginResponseBean.getCode() == 1){
                                SaveAndGetUser saveAndGetUser = new SaveAndGetUser(LoginActivity.this);
                                saveAndGetUser.setUser(userName,passWord);
                                setAlias(userName);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", userName);
                                intent.putExtra("userInfo", (Serializable) loginResponseBean);
                                startActivity(intent);
                            }else if(loginResponseBean.getCode() == 0){
                                Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    boolean isAliasAction = false;
    int action = -1;
    public static final int ACTION_GET = 5;//获取
    public void setAlias(String username){
        isAliasAction = true;
        action = ACTION_SET;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        if(isAliasAction){
            Log.i("", "onCreate: 设置别名：：：：：：： " + username);
            tagAliasBean.alias = username;
        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
    }

}
