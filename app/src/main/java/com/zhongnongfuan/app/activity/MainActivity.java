package com.zhongnongfuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.adapter.OnRecyclerviewItemClickListener;
import com.zhongnongfuan.app.adapter.RecyclerViewAdapter;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.Machine;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.App;
import com.zhongnongfuan.app.utils.CommonUtils;
import com.zhongnongfuan.app.utils.ParseUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * @author qichaoqun
 * @create 2019/1/19
 * @Describe
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int QUIT_TIME = 2000;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.no_content)
    TextView noContent;


    private RecyclerViewAdapter mRecyclerViewAdapter;
    private static Machine mMachines;//网络获取的机器列表
    private static List<DetailState> mDetailStateList;//通过机器列表访问网络获取机器详细状态信息列表
    List<String> nameList;//机器列表的名称集合（用于在intent中向MachineMapActivity传递数据）
    Map<String, String> paramMap;//获取机器列表需要传递的参数
    private long mStartTime;
    String deviceListPath = Prefix.PREFIX+"Android/SBLB";
    String LatLonPath = Prefix.PREFIX+"Android/SBWZ";
    Intent intent;
    String userId;
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intent = getIntent();
        userId = intent.getStringExtra("userId");


        initView();
        loadData();

       /* TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.i("MainActivity:", "run: 定时器：：：：：刷新数据：：：：");
                loadData();
            }
        };
        new Timer().schedule(task, 5000, 5000);*/
    }
    /**
     * 从网络获取数据
     */
    private void loadData() {
        Log.i("", "loadData: 列出机器数据：：：：：：");
        MyNetWork myNetWork1 = MyNetWork.getInstance(this);
        paramMap = new HashMap<>();
        paramMap.put("userName", "jy");
        myNetWork1.postAsynHttp(deviceListPath, paramMap, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MainActivity.this, "获取机器列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) throws IOException {
                Log.i("MainActivity:", "onResponse: 获取机器列表返回值：：：： " + str);
                mMachines = ParseUtil.parseDeviceListJson(str);
                nameList = new ArrayList<>();
                for (int i = 0; i < mMachines.getData().size(); i++) {
                    nameList.add(mMachines.getData().get(i).getMC());
                }
                getApp().setMachineList(mMachines);
                getApp().setNameList(nameList);
                mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mMachines);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClickListener(View v, int position) {
                        if (CommonUtils.isFastDoubleClick()) {
                            Toast.makeText(MainActivity.this, "操作过于频繁", Toast.LENGTH_SHORT).show();
                        }else{
                            //弹出Toast或者Dialog
                            //假设根据机器的编号来获取相关的信息
                            Intent intent = new Intent(MainActivity.this, MachineActivity.class);
                            intent.putExtra("machine_name", mMachines.getData().get(position).getMC());
                            startActivity(intent);
                        }
                    }
                });

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

        mRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Log.i("程序执行了：：：", "onRefresh: "+"程序刷新了");
                //将用户的所有的机器数设置到RecyclerView上
                if (mMachines != null) {
                    mMachines.getData().clear();
                }
                Log.i("", "loadData: 新列出机刷器数据：：：：：：");
                loadData();

                mRecyclerViewAdapter.flushList(mMachines);

                mRefreshLayout.finishRefresh(1000);
            }
        });

        //初始化RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_map_location){
            final MyNetWork myNetWork1 = MyNetWork.getInstance(this);
            paramMap = new HashMap<>();
            paramMap.put("userName", "jy");
            myNetWork1.postAsynHttp(LatLonPath, paramMap, new ResultCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(MainActivity.this, "获取经纬度失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String str) throws IOException {
                    Log.i("MainActivity:", "onResponse: 返回机器列表经纬度：：：" + str);

                    LatLngBean latLngBean = ParseUtil.parseLatLngJson(str);
                    if(latLngBean.getCode() == 1){
                        List<LatLngBean.DataBean> dataBeanList = latLngBean.getData();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataBeanList", (Serializable) dataBeanList);

                        Intent intent1 = new Intent(MainActivity.this, MachineMapActivity.class);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                    }else {
                        Toast.makeText(MainActivity.this, "加载定位信息失败", Toast.LENGTH_SHORT).show();
                    }
                   
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //作用：传递参数
    public App getApp(){
        return (App)getApplicationContext();
    }

}
