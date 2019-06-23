package com.zhongnongfuan.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.adapter.DeviceListAdapter;
import com.zhongnongfuan.app.adapter.FragmentPageAdapter;
import com.zhongnongfuan.app.base.BaseActivity;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.LoginResponseBean;
import com.zhongnongfuan.app.bean.MachineList;
import com.zhongnongfuan.app.bean.MonitorBean;
import com.zhongnongfuan.app.eventbus.MessageEvent;
import com.zhongnongfuan.app.fragment.BaiduMapFragment;
import com.zhongnongfuan.app.fragment.DeviceListFragment;
import com.zhongnongfuan.app.fragment.VideoMonitorFragment;
import com.zhongnongfuan.app.utils.App;
import com.zhongnongfuan.app.utils.Utility;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 机器列表
 */
public class Main2Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int QUIT_TIME = 2000;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private static final String TAG = "Main2Activity";
    @BindView(R.id.myTableLayout)
    TabLayout myTableLayout;
    @BindView(R.id.myViewPager)
    ViewPager myViewPager;
    @BindView(R.id.drawer2_layout)
    DrawerLayout drawerLayout;

    Map<String, String> paramMap;//获取机器列表需要传递的参数
    private long mStartTime;
    String deviceListPath = Prefix.PREFIX + "Android/SBLB";//机器列表
    String LatLonPath = Prefix.PREFIX + "Android/SBWZ";//经纬度路径
    Intent intent;
    String userId;
    LoginResponseBean mLoginResponseBean;
    boolean isFirstLoad = true;
    private MachineList mMachineList;//网络获取的机器列表
    private DeviceListAdapter mDeviceListAdapter;
    private Handler mHandler;
    private MonitorBean mMonitorBean;
    private List<Fragment> mFragments;
    private FragmentPageAdapter mFragmentPageAdapter;
    public static boolean isForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        mLoginResponseBean = (LoginResponseBean) intent.getSerializableExtra("userInfo");
        Log.i("", "onCreate方法执行: 从登陆页面跳转到主界面：：：：：：：：：：：：");
        initView();
        initFragments();

    }
    /**
     * 初始化控件和各种数据
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main2);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //返回键
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long endTime = System.currentTimeMillis();
            if (endTime - mStartTime > QUIT_TIME) {
                Toast.makeText(Main2Activity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                mStartTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.machine_list_menu, menu);
        return true;
    }

    //toolbar上的功能键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow2) {
            Log.i("", "onNavigationItemSelected: 用户信息选项被点击，跳转.......................");
            //用户信息
            Intent intent = new Intent(Main2Activity.this, UserInfoActivity.class);
            intent.putExtra("userInfo", mLoginResponseBean);
            startActivity(intent);

        } else if (id == R.id.nav_manage2) {
            //设置
            Intent intent = new Intent(Main2Activity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share2) {
            //分享
            Intent intent = new Intent(Main2Activity.this, ShareActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send2) {
            //联系我们
            Intent intent = new Intent(Main2Activity.this, ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delete2) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("确认提示").setIcon(R.drawable.alert_icon).
                    setMessage("是否确定注销？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("取消", null).create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer2_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //作用：传递参数
    public App getApp() {
        return (App) App.getAppContext();
    }

    /**
     * 初始化Fragment组件
     */
    public void initFragments() {
        Log.i("", "initFragments: initFragment方法执行：：：：：：：：：：：：");
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("机器列表");
        tabTitles.add("显示位置");
        tabTitles.add("监控列表");
        mFragments = new ArrayList<>();
        mFragments.add(DeviceListFragment.newInstance(userId, deviceListPath));
        mFragments.add(BaiduMapFragment.newInstance(userId));
        mFragments.add(VideoMonitorFragment.newInstance(userId));
        mFragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), mFragments, tabTitles);
        myViewPager.setAdapter(mFragmentPageAdapter);
        mFragmentPageAdapter.notifyDataSetChanged();

        myTableLayout.setupWithViewPager(myViewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        Log.i("", "onMessageEvent: 收到消息：：：" + event.getMessage());
        if ("deviceList is null!".equals(event.getMessage())){
            Toast.makeText(this, "获取机器列表失败", Toast.LENGTH_SHORT).show();
        }else if(event.getMessage().contains("jgcbm")){
            Gson gson = new Gson();
            mMachineList = gson.fromJson(event.getMessage(), MachineList.class);
            if (mMachineList.getCode()==0){
                Toast.makeText(this, "获取机器列表失败", Toast.LENGTH_SHORT).show();
            }else if (mMachineList.getData().size()==0){
                Toast.makeText(this, "无设备", Toast.LENGTH_SHORT).show();
            }
            Log.i("", "onMessageEvent: ：：：：：：：：：：：从gson转化为MachineList：：：" + mMachineList);
            getApp().setMachineList(mMachineList);
            DeviceListFragment deviceListFragment = (DeviceListFragment) mFragmentPageAdapter.getItem(0);
            deviceListFragment.getDeviceListAdapter().flushList(mMachineList);
        }else if ("get latlng failed!".equals(event.getMessage())){
            Toast.makeText(this, "获取经纬度失败", Toast.LENGTH_SHORT).show();
        }else if (event.getMessage().contains("lat")){
            Gson gson = new Gson();
            LatLngBean latLngBean = gson.fromJson(event.getMessage(), LatLngBean.class);
            if (latLngBean != null && (latLngBean.getCode() == 1)) {
                ArrayList<LatLngBean.DataBean> mDataBeanList = (ArrayList<LatLngBean.DataBean>) latLngBean.getData();
                getApp().setDataBeanList(mDataBeanList);
                Log.i(TAG, "onResponse: mDataBeanList值为：：：：" + mDataBeanList);
                BaiduMapFragment baiduMapFragment = (BaiduMapFragment) mFragmentPageAdapter.getItem(1);
                baiduMapFragment.initLocationOption();
            } else {
                Toast.makeText(this, "加载定位信息失败", Toast.LENGTH_SHORT).show();
            }
        }else if ("monitorList is null!".equals(event.getMessage())){
            Toast.makeText(this, "获取摄像头列表失败", Toast.LENGTH_SHORT).show();
        }else if (event.getMessage().contains("WZ")){
            Gson gson = new Gson();
            mMonitorBean = gson.fromJson(event.getMessage(), MonitorBean.class);
            boolean isNull = Utility.isNullOrEmpty(mMonitorBean);
            if (isNull){
                Toast.makeText(this, "获取监控设备列表失败！", Toast.LENGTH_SHORT).show();
            }else{
                if (mMonitorBean.getCode() == 0){
                    Toast.makeText(this, "获取监控设备列表失败！", Toast.LENGTH_SHORT).show();
                }else if (mMonitorBean.getData().size()==0){
                    Toast.makeText(this, "监控设备列表为空!", Toast.LENGTH_SHORT).show();
                }
                Log.i("", "onMessageEvent: ：：：：：：：：：：：从gson转化为mMonitorBean：：：" + mMonitorBean);
                getApp().setMonitorBean(mMonitorBean);
                VideoMonitorFragment videoMonitorFragment = (VideoMonitorFragment) mFragmentPageAdapter.getItem(2);
                videoMonitorFragment.getVideoRecyclerViewAdapter().flushList(mMonitorBean);
            }
        }

    }
}
