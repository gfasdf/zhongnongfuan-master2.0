package com.zhongnongfuan.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zhongnongfuan.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.machine_toolbar_contact)
    Toolbar machineToolbarContact;
    @BindView(R.id.tv_sale_tel_param)
    TextView tvSaleTelParam;
    @BindView(R.id.tv_fax_param)
    TextView tvFaxParam;
    @BindView(R.id.tv_address_param)
    TextView tvAddressParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        machineToolbarContact.setTitle("联系我们");
        setSupportActionBar(machineToolbarContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        machineToolbarContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
