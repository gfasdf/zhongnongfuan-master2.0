package com.zhongnongfuan.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.adapter.OnRecyclerviewItemClickListener;
import com.zhongnongfuan.app.adapter.RecyclerViewAdapter;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.LoginResponseBean;
import com.zhongnongfuan.app.bean.MachineList;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.App;
import com.zhongnongfuan.app.utils.CommonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 机器列表
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int QUIT_TIME = 2000;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.no_content)
    TextView noContent;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private static MachineList mMachines;//网络获取的机器列表
    private static List<DetailState> mDetailStateList;//通过机器列表访问网络获取机器详细状态信息列表
    Map<String, String> paramMap;//获取机器列表需要传递的参数
    private long mStartTime;
    String deviceListPath = Prefix.PREFIX + "Android/SBLB";//机器列表
    String LatLonPath = Prefix.PREFIX + "Android/SBWZ";//经纬度路径
    Intent intent;
    String userId;
    LoginResponseBean mLoginResponseBean;
    public static boolean isForeground = false;
    boolean isFirstLoad = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        mLoginResponseBean = (LoginResponseBean) intent.getSerializableExtra("userInfo");
        initView();
        loadData();

    }

    /**
     * 从网络获取数据
     */
    private void loadData() {
        Log.i("", "loadData: 列出机器数据：：：：：：");
        MyNetWork myNetWork1 = MyNetWork.getInstance(this);
        paramMap = new HashMap<>();
        paramMap.put("userName", userId);
        myNetWork1.postAsynHttp(deviceListPath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MainActivity.this, "获取机器列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) throws IOException {
                Log.i("MainActivity:", "onResponse: 获取机器列表返回值：：：： " + str);
                Gson gson = new Gson();
                mMachines = gson.fromJson(str, MachineList.class);
                if (mMachines.getCode() == 1) {
                    Log.i("", "onResponse: 返回码为1:::::");
                    getApp().setMachineList(mMachines);
                    if (mMachines.getData().size() == 0) {
                        Toast.makeText(MainActivity.this, "无设备", Toast.LENGTH_SHORT).show();
                    }else {
                        mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mMachines);
                        mRecyclerView.setAdapter(mRecyclerViewAdapter);
                        Log.i("", "onResponse: 给机器列表注册监听器");
                        mRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                if (CommonUtils.isFastDoubleClick()) {
                                    Toast.makeText(MainActivity.this, "操作过于频繁", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("", "onItemClickListener: 点击某一项：：：：：：：：");
                                    //弹出Toast或者Dialog
                                    //假设根据机器的编号来获取相关的信息
                                    Intent intent = new Intent(MainActivity.this, MachineActivity.class);
                                    Log.i("", "onItemClickListener: 即将传递的数据为：：：：deviceId：：" + mMachines.getData().get(position).getSB_BM()
                                            + "  deviceName:::" + mMachines.getData().get(position).getMC());
                                    intent.putExtra("deviceId", mMachines.getData().get(position).getSB_BM());
                                    intent.putExtra("deviceName", mMachines.getData().get(position).getMC());
                                    intent.putExtra("activity", "MainActivity");
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                } else if (mMachines.getCode() == 0) {
                    if (isFirstLoad){
                        Toast.makeText(MainActivity.this, "获取机器列表失败", Toast.LENGTH_SHORT).show();
                        isFirstLoad = false;
                    }
                }
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * 初始化控件和各种数据
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 下拉刷新颜色控制
        swipeLayout.setColorSchemeResources(R.color.green,
                R.color.yellow);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("程序执行了：：：", "onRefresh: " + "程序刷新了");
                //将用户的所有的机器数设置到RecyclerView上
                if (mMachines != null) {
                    mMachines.getData().clear();
                }
                Log.i("", "loadData: 新列出机刷器数据：：：：：：");
                loadData();

                mRecyclerViewAdapter.flushList(mMachines);
                swipeLayout.setRefreshing(false);
            }
        });

        //初始化RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    //返回键
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long endTime = System.currentTimeMillis();
            if (endTime - mStartTime > QUIT_TIME) {
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                mStartTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.machine_list_menu, menu);
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
        if (id == R.id.action_map_location) {
            final MyNetWork myNetWork1 = MyNetWork.getInstance(this);
            paramMap = new HashMap<>();
            paramMap.put("userName", userId);
            myNetWork1.postAsynHttp(LatLonPath, paramMap, new ResultCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(MainActivity.this, "获取经纬度失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String str) throws IOException {
                    Gson gson = new Gson();
                    LatLngBean latLngBean = gson.fromJson(str, LatLngBean.class);
                    if (latLngBean != null && (latLngBean.getCode() == 1)) {
                        List<LatLngBean.DataBean> dataBeanList = latLngBean.getData();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataBeanList", (Serializable) dataBeanList);
                        bundle.putSerializable("MachineList", (Serializable) mMachines);
                        Intent intent1 = new Intent(MainActivity.this, MachineMapActivity.class);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(MainActivity.this, "加载定位信息失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (id == R.id.action_video_supervision) {
            Intent intent = new Intent(this, EzvizWebViewActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            Log.i("", "onNavigationItemSelected: 用户信息选项被点击，跳转.......................");
            //用户信息
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("userInfo", mLoginResponseBean);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            //设置
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            //分享
            Intent intent = new Intent(MainActivity.this, ShareActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            //联系我们
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delete) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("确认提示").setIcon(R.drawable.alert_icon).
                    setMessage("是否确定注销？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("取消", null).create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //作用：传递参数
    public App getApp() {
        return (App) getApplicationContext();
    }

}
