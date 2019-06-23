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
import com.zhongnongfuan.app.adapter.DeviceListAdapter;
import com.zhongnongfuan.app.bean.MachineList;
import com.zhongnongfuan.app.utils.App;
import com.zhongnongfuan.app.utils.LoadInfoUtil;

public class DeviceListFragment extends Fragment {

    private Context context;
    public DeviceListAdapter mDeviceListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MachineList mMachineList;
    private String userId;
    private String deviceListPath;

    public DeviceListFragment() {

    }

    public static DeviceListFragment newInstance(String userId, String deviceListPath) {
        Log.i("", "newInstance: fragment执行newInstance方法：：：" );
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("deviceListPath", deviceListPath);
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("", "newInstance: fragment执行onCreate方法：：：" );
        context = getContext();
        userId = getArguments().getString("userId");
        deviceListPath = getArguments().getString("deviceListPath");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getApp().getMachineList() == null){
            LoadInfoUtil.getInstance().loadDeviceListData(context, userId, deviceListPath);
        }
        Log.i("", "onCreate: 在DeviceListFragment中获取的args为：：：：：userId = " + userId + " deviceListPath为：" + deviceListPath);
        Log.i("", "newInstance: fragment执行onCreateView方法：：：" );
         View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_fragment_one);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        // 下拉刷新颜色控制
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green,
                R.color.yellow);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadInfoUtil.getInstance().loadDeviceListData(context, userId,deviceListPath);
                mDeviceListAdapter.flushList(getApp().getMachineList());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mDeviceListAdapter = new DeviceListAdapter(context, getApp().getMachineList());
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mDeviceListAdapter);

        return view;
    }

    public void refreshView(){
        if(mDeviceListAdapter != null){
            LoadInfoUtil.getInstance().loadDeviceListData(context, userId, deviceListPath);
            System.out.println("LoaclFragment中的数据大小" + getApp().getMachineList().getData().size());
            mDeviceListAdapter.notifyDataSetChanged();
        }
    }

    //作用：传递参数
    public App getApp() {
        return (App) App.getAppContext();
    }

    public DeviceListAdapter getDeviceListAdapter(){
        return mDeviceListAdapter;
    }
}
