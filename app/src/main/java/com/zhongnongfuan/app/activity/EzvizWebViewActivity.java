package com.zhongnongfuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ezvizuikit.open.EZUIPlayer;
import com.google.gson.Gson;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.adapter.OnRecyclerviewItemClickListener;
import com.zhongnongfuan.app.adapter.VideoRecyclerViewAdapter;
import com.zhongnongfuan.app.bean.MonitorBean;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.CommonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class EzvizWebViewActivity extends AppCompatActivity {

    private static final String TAG = "VideoActivity";
    public static final String APPKEY = "AppKey";
    public static final String AccessToekn = "AccessToekn";
    public static final String PLAY_URL = "play_url";
    public String monitorListPath = Prefix.PREFIXQ + "Android/Monitor";
    public static String AppKey = "945bbd856cd84ea599fec4241f0ed632";
    @BindView(R.id.monitor_recycler_view)
    RecyclerView monitor_recycler_view;
    @BindView(R.id.machine_toolbar_supervision)
    Toolbar machineToolbarSupervision;
    @BindView(R.id.monitor_swipeLayout)
    SwipeRefreshLayout monitor_swipeLayout;
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    private boolean isGlobal = false;
    Map<String, String> map = new HashMap<>();
    private MonitorBean mMonitorBean;
    private String mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userId");
        loadMonitorList(mUserName);
        initToolBar();

    }

    private void loadMonitorList(String userName) {
        map.put("userName", userName);
        MyNetWork myNetWork = MyNetWork.getInstance(EzvizWebViewActivity.this);
        myNetWork.postAsynHttp(monitorListPath, map, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(EzvizWebViewActivity.this, "获取摄像头列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) throws IOException {
                Log.i(TAG, "onResponse: 获取的摄像头列表数据为：：：：：： " + str);
                Gson gson = new Gson();
                mMonitorBean = gson.fromJson(str, MonitorBean.class);
                if (mMonitorBean.getCode() == 1){
                    if (mMonitorBean.getData().size() == 0){
                        Toast.makeText(EzvizWebViewActivity.this, "监控设备列表为空!", Toast.LENGTH_SHORT).show();
                    }else{
                        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(EzvizWebViewActivity.this, mMonitorBean.getData());
                        monitor_recycler_view.setAdapter(mVideoRecyclerViewAdapter);
                        mVideoRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                if (CommonUtils.isFastDoubleClick()) {
                                    Toast.makeText(EzvizWebViewActivity.this, "操作过于频繁", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("", "onItemClickListener: 点击监控列表中某一项：：：：：：：： " + position);
                                    imitatePlayButton("ezopen://"+mMonitorBean.getData().get(position).getYZM()+"@open.ys7.com/" + mMonitorBean.getData().get(position).getXLH() + "/1.hd.live", AppKey, mMonitorBean.getAccesstoken());
                                }
                            }
                        });
                    }

                }else if(mMonitorBean.getCode() == 0){
                    Toast.makeText(EzvizWebViewActivity.this, "获取监控设备列表失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initToolBar() {
        machineToolbarSupervision.setTitle("监控列表");
        setSupportActionBar(machineToolbarSupervision);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        machineToolbarSupervision.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {
        // 下拉刷新颜色控制
        monitor_swipeLayout.setColorSchemeResources(R.color.green,
                R.color.yellow);
        monitor_swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("程序执行了：：：", "onRefresh: " + "程序刷新了");
                //将用户的所有的机器数设置到RecyclerView上
                if (mMonitorBean != null) {
                    mMonitorBean.getData().clear();
                }
                Log.i("", "loadData: 列出监控刷新后的数据：：：：：：mMonitorList = " + mMonitorBean.getData());
                loadMonitorList(mUserName);
                mVideoRecyclerViewAdapter.flushList(mMonitorBean.getData());
                monitor_swipeLayout.setRefreshing(false);
            }
        });

        //初始化RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        monitor_recycler_view.setLayoutManager(linearLayoutManager);
    }




    public void imitatePlayButton(String mUrl, String mAppKey, String mAccessToken) {
        String[] urls = mUrl.split(",");
        Log.e("", "onClick: 切割后产生的urls为：：：：：" + urls);
        if (urls != null && urls.length == 2) {
            //直播预览
            if (isGlobal) {
                //启动普通回放页面
//                DoublePlayActivity.startPlayActivity(this, mAppKey, mAccessToken, urls[0],urls[1],mGlobalAreaDomain);
                return;
            }
            //启动普通回放页面
            DoublePlayActivity.startPlayActivity(this, mAppKey, mAccessToken, urls[0], urls[1]);
            return;
        }

        EZUIPlayer.EZUIKitPlayMode mode = null;
        mode = EZUIPlayer.getUrlPlayType(mUrl);
        if (mode == EZUIPlayer.EZUIKitPlayMode.EZUIKIT_PLAYMODE_LIVE) {
            //直播预览
            if (isGlobal) {
                //启动播放页面
//                PlayActivity.startPlayActivityGlobal(this, mAppKey, mAccessToken, mUrl,mGlobalAreaDomain);
                //应用内只能初始化一次，当首次选择了国内或者海外版本，并点击进入预览回放，此时不能再进行国内海外切换
                return;
            }
            //启动播放页面
            PlayActivity.startPlayActivity(this, mAppKey, mAccessToken, mUrl);
        } else if (mode == EZUIPlayer.EZUIKitPlayMode.EZUIKIT_PLAYMODE_REC) {
            //默认启动动回放带时间轴页面
            PlayBackActivity.startPlayBackActivity(this, mAppKey, mAccessToken, mUrl);
        } else {
            Toast.makeText(this, "播放模式未知，默认进入直播预览模式", Toast.LENGTH_LONG).show();
            //直播预览
      /*      if (isGlobal){
                //启动播放页面
                PlayActivity.startPlayActivityGlobal(this, mAppKey, mAccessToken, mUrl,mGlobalAreaDomain);
                //应用内只能初始化一次，当首次选择了国内或者海外版本，并点击进入预览回放，此时不能再进行国内海外切换
                return;
            }*/
            //启动播放页面
            PlayActivity.startPlayActivity(this, mAppKey, mAccessToken, mUrl);
        }
    }

}
