package com.zhongnongfuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.zhongnongfuan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.machine_toolbar_setting)
    Toolbar machineToolbarSetting;
    @BindView(R.id.tv_is_alarm)
    TextView tvIsAlarm;
    @BindView(R.id.s_v)
    Switch aSwitch;
    @BindView(R.id.input_tel)
    EditText inputTel;
    @BindView(R.id.rl_tel)
    RelativeLayout rlTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initToolBar();
        initView();
    }

    private void initView() {
        final Switch aSwitch = (Switch) findViewById(R.id.s_v);
        aSwitch.setChecked(false);
        Log.i("", "onCheckedChanged: 默认为不点击，字体颜色为灰色");
        aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //控制开关字体颜色
                if (isChecked) {
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_true);
                    inputTel.setEnabled(true);
                } else {
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_false);
                    inputTel.setEnabled(false);
                }
            }
        });
    }

    private void initToolBar() {
        machineToolbarSetting.setTitle("设置");
        setSupportActionBar(machineToolbarSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        machineToolbarSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
