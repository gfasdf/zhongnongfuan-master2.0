package com.zhongnongfuan.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.google.gson.JsonObject;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.network.MyNetWork;
import com.zhongnongfuan.app.network.ResultCallback;
import com.zhongnongfuan.app.utils.ParseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class MachineMapActivity extends Activity {
    private static final String TAG = "MachineMapActivity:";
    MapView mMapView;
    BaiduMap mBaiduMap;
    ArrayList<LatLngBean.DataBean> dataBeanList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏，并在配置文件中将该activity主题设置为android:theme="@android:style/Theme.NoTitleBar"
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SDKInitializer.initialize(getApplicationContext());//必须在setContentView之前
        setContentView(R.layout.activity_machine_map);

        Intent intent = getIntent();
        if (intent != null) {
            dataBeanList = (ArrayList<LatLngBean.DataBean>) intent.getSerializableExtra("dataBeanList");
            Log.i(TAG, "MachineMapActivity通过intent获取的dataBeanList个数为：:  " + dataBeanList.size() + "  分别为： " + dataBeanList);
        }
        //获取地图控件引用
        mMapView = findViewById(R.id.bmapview);
        mBaiduMap = mMapView.getMap();//获取地图实例
        initLocationOption();
    }


    //初始化定位参数配置
    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener(dataBeanList);
        locationClient.registerLocationListener(myLocationListener);
        Log.i("MachineMapActivity:", "initLocationOption: ==注册监听函数");
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();
        Log.i("", "initLocationOption: 定位服务的客户端开始运行====");
    }

    //实现定位回调
    public class MyLocationListener extends BDAbstractLocationListener {
        private static final String TAG = "MyLocationListener";
        private ArrayList<LatLngBean.DataBean> dataBeanList;
        private List<Marker> mMarkerList;
        private Marker marker;
        private Bundle mBundle;
        private Double lat;
        private Double lon;

        public MyLocationListener(ArrayList<LatLngBean.DataBean> dataBeanList) {
            Log.i(TAG, "MyLocationListener: 监听方法执行：：：：：");
            this.dataBeanList = dataBeanList;
        }

        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        @Override
        public void onReceiveLocation(BDLocation location) {
       /*     LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());//设定中心点坐标
            MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
            .target(cenpt).zoom(18).build(); //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态*/
            Log.i(TAG, "onReceiveLocation: onReceiveLocation方法执行：：：：");
            mMarkerList = new ArrayList<>();
            for (int i = 0; i < dataBeanList.size(); i++) {
                //根据view设置坐标定位图标
                View location_option = LayoutInflater.from(MachineMapActivity.this).inflate(R.layout.location_option_layout, null);
                TextView textView = location_option.findViewById(R.id.baidumap_custom_text);
                textView.setText("机器ID：" + dataBeanList.get(i).getDeviceid());
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(location_option);

                if (dataBeanList.get(i).getLat() != null && ("".equals(dataBeanList.get(i).getLat()))){
                    lat = Double.parseDouble(dataBeanList.get(i).getLat());
                }else {
                    lat = 0.0;
                }
                if (dataBeanList.get(i).getLon() != null && ("".equals(dataBeanList.get(i).getLon()))){
                    lon = Double.parseDouble(dataBeanList.get(i).getLon());
                }else {
                    lon = 0.0;
                }

                LatLng position = new LatLng(lat, lon);
                OverlayOptions options = new MarkerOptions().position(position).icon(bitmapDescriptor).title("机器");
                mBundle = new Bundle();
                mBundle.putString("deviceId", dataBeanList.get(i).getDeviceid());
                marker = (Marker) mBaiduMap.addOverlay(options);
                marker.setExtraInfo(mBundle);
                mMarkerList.add(marker);
            }
            //设置多个marker在同一个页面内
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int x = 0; x < dataBeanList.size(); x++) {
                Log.i(TAG, "onReceiveLocation: 设置多个marker在同一个页面内：：：：：");
                builder.include(new LatLng(Double.parseDouble(dataBeanList.get(x).getLat()), Double.parseDouble(dataBeanList.get(x).getLon())));
            }
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(builder.build());
            mBaiduMap.setMapStatus(u);

            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            Log.i(TAG, "onReceiveLocation: 获取定位精度==" + radius);
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            Log.i(TAG, "onReceiveLocation:获取经纬度坐标类型== " + coorType);
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            Log.i(TAG, "onReceiveLocation: 获取定位错误返回码==" + errorCode);
            //设置marker点击事件
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    Log.i(TAG, "onMarkerClick: 点击marker事件：：：");
                    final Bundle bundle = marker.getExtraInfo();
                    String deviceId = bundle.getString("deviceId");
                    String devicePath = Prefix.PREFIX+"Android/SBZT";
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("deviceId", "5678");
//                    paramMap.put("deviceId", deviceId);
                    MyNetWork myNetWork = MyNetWork.getInstance(MachineMapActivity.this);
                    myNetWork.postAsynHttp(devicePath, paramMap, new ResultCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Toast.makeText(MachineMapActivity.this, "加载具体信息失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String str) throws IOException {
                            Log.i(TAG, "onResponse: 点击marker响应的具体信息为：：：" + str);
                            DetailState detailState = ParseUtil.parseDeviceJson(str);
                            if (detailState.getCode() == 1) {
                                LatLng latLng = marker.getPosition();
                                InfoWindow currentInfoWindow = new InfoWindow(getInfoWindowView(detailState), latLng, -85);
                                mBaiduMap.showInfoWindow(currentInfoWindow);
                            } else {
                                Toast.makeText(MachineMapActivity.this, "加载具体信息有误", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    return true;
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private View getInfoWindowView(final DetailState detailState) {
        Log.i(TAG, "getInfoWindowView: 获取弹窗：：：：：");
        ViewGroup infoView = null;
        if (null == infoView) {
            infoView = (ViewGroup) LayoutInflater.from(MachineMapActivity.this).inflate(R.layout.baidumap_infowindow, null);
        }

        TextView tvRefinery = infoView.findViewById(R.id.tv_refinery);
        TextView tvWorkState = infoView.findViewById(R.id.tv_work_state);
        TextView tvDeviceId = infoView.findViewById(R.id.tv_deviceId);

        tvRefinery.setText("加工厂：湖北一航");
        tvWorkState.setText("工作状态：排粮");
        tvDeviceId.setText("设备编号：" + detailState.getData().getDeviceId());

        return infoView;
    }


}

