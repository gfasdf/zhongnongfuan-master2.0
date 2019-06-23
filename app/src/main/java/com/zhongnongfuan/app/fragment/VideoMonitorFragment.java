package com.zhongnongfuan.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.activity.Prefix;
import com.zhongnongfuan.app.adapter.VideoRecyclerViewAdapter;
import com.zhongnongfuan.app.utils.App;
import com.zhongnongfuan.app.utils.LoadInfoUtil;
import com.zhongnongfuan.app.utils.Utility;

public class VideoMonitorFragment extends Fragment {

    private Context context;
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String userId;
    private String monitorListPath = Prefix.PREFIX + "Android/Monitor";

    public VideoMonitorFragment() {

    }

    public static VideoMonitorFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString("userId", userId);
        VideoMonitorFragment fragment = new VideoMonitorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        userId = getArguments().getString("userId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Utility.isNullOrEmpty(getApp().getMonitorBean())){
            LoadInfoUtil.getInstance().loadMonitorList(context, userId, monitorListPath);
        }
        Log.i("", "onCreate: 在VideoMonitorFragment中获取的args为：：：：：userId = " + userId);
        final View view = inflater.inflate(R.layout.video_list_fragment, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.monitor_recycler_view_fragment);
        mSwipeRefreshLayout = view.findViewById(R.id.monitor_swipeLayout_fragment);
        // 下拉刷新颜色控制
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green,
                R.color.yellow);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadInfoUtil.getInstance().loadDeviceListData(context, userId,monitorListPath);
                mVideoRecyclerViewAdapter.flushList(getApp().getMonitorBean());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mVideoRecyclerViewAdapter = new VideoRecyclerViewAdapter(context, getApp().getMonitorBean());
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mVideoRecyclerViewAdapter);
        return view;
    }

    public void refreshView(){
        if(mVideoRecyclerViewAdapter != null){
            LoadInfoUtil.getInstance().loadMonitorList(context, userId, monitorListPath);
            mVideoRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public VideoRecyclerViewAdapter getVideoRecyclerViewAdapter(){
        return mVideoRecyclerViewAdapter;
    }

    //作用：传递参数
    public App getApp() {
        return (App) App.getAppContext();
    }

}
