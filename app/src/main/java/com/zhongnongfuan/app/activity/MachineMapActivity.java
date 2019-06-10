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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.zhongnongfuan.app.R;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.MachineList;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示地图
 */
public class MachineMapActivity extends Activity {
    private static final String TAG = "MachineMapActivity:";
    MapView mMapView;
    BaiduMap mBaiduMap;
    ArrayList<LatLngBean.DataBean> dataBeanList;
    MachineList mMachineList;
    private TextView tvWorkState;
    private TextView tvRefinery;
    private TextView tvDeviceId;
    boolean isFirstLoadMap = true;


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
            mMachineList = (MachineList) intent.getSerializableExtra("MachineList");

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
        //        private List<Marker> mMarkerList;

        private Double lat;
        private Double lon;

        public MyLocationListener(ArrayList<LatLngBean.DataBean> dataBeanList) {
            this.dataBeanList = dataBeanList;
        }

        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i(TAG, "onReceiveLocation: onReceiveLocation方法执行：：：：");

        if (dataBeanList!=null){
            /**
             * 对多个点标记添加监听
             */
            //先创建一个Bundle用来进行marker的值的传递.
            Bundle mBundle = null;

            /**
             * 批量添加多个点标记,将
             */
            //创建OverlayOptions的集合
            List<Marker> markerList = new ArrayList<>();
            Marker marker;
            LatLng point;//坐标点
            MarkerOptions option;//添加图层
            View location_option = null;
            TextView textView;//图标对应的文本
            BitmapDescriptor bitmapDescriptor;
            //设置坐标点
            for (int i = 0; i < dataBeanList.size(); i++) {
                Log.i(TAG, "onReceiveLocation: 坐标信息为：：：" + mMachineList);
                mBundle = new Bundle();
                mBundle.putString("deviceId", dataBeanList.get(i).getDeviceid());
                mBundle.putString("lat", dataBeanList.get(i).getLat());
                mBundle.putString("lon", dataBeanList.get(i).getLon());
                for (int j = 0; j < mMachineList.getData().size(); j++) {
                    Log.i(TAG, "onReceiveLocation: mBundle值设置为：：：：" + dataBeanList.get(i).getDeviceid());
                    if (dataBeanList.get(i).getDeviceid().equals(mMachineList.getData().get(j).getSB_BM())){
                        Log.i(TAG, "onReceiveLocation: 设备marker背景：：：：工作状态为：：：" + mMachineList.getData().get(i).getGzzt());
                        if ("报警".equals(mMachineList.getData().get(j).getGzzt())){
                            location_option = LayoutInflater.from(MachineMapActivity.this).inflate(R.layout.location_option_red, null);
                        }else {
                            location_option = LayoutInflater.from(MachineMapActivity.this).inflate(R.layout.location_option_green, null);
                        }
                    }
                }

                point = new LatLng(Double.parseDouble(dataBeanList.get(i).getLat()), Double.parseDouble(dataBeanList.get(i).getLon()));
                //根据view设置坐标定位图标， 即构建Marker图标
                textView = location_option.findViewById(R.id.baidumap_custom_text);
                textView.setText("机器ID：" + dataBeanList.get(i).getDeviceid());
                bitmapDescriptor = BitmapDescriptorFactory.fromView(location_option);
                //构建MarkerOption，用于在地图上添加Marker
                option = new MarkerOptions().position(point).icon(bitmapDescriptor).title("机器");
                marker = (Marker) mBaiduMap.addOverlay(option);
                marker.setExtraInfo(mBundle);
                markerList.add(marker);
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
                    Bundle bundle = marker.getExtraInfo();
                    //获取机器id
                    final String deviceId = bundle.getString("deviceId");
                    String deviceId2;
                    List<String> deviceIdList = new ArrayList<>();
                    String lat = bundle.getString("lat");
                    String lon = bundle.getString("lon");
                    Log.i(TAG, "onMarkerClick: ：：：" + deviceId + "  获取的坐标为：：：(" + lat + " , " + lon + ")");
                    for (int i = 0; i < dataBeanList.size(); i++) {
                        if (lat.equals(dataBeanList.get(i).getLat()) && lon.equals(dataBeanList.get(i).getLon())){
                            deviceId2 = dataBeanList.get(i).getDeviceid();
                            deviceIdList.add(deviceId2);
                        }
                    }
                    LatLng latLng;
                    InfoWindow currentInfoWindow;
                    for (int j = 0; j < deviceIdList.size(); j++) {
                        for (int i = 0; i < mMachineList.getData().size(); i++) {
                            Log.i(TAG, "onResponse: 在点击marker时循环遍历：：：：：：：设备编码为：：" +
                                    mMachineList.getData().get(i).getSB_BM() + "该marker的设备编码为：：：" + deviceIdList.toString());
                            //一个位置有1或多个坐标
                            if ((deviceIdList.get(j).trim()).equals(mMachineList.getData().get(i).getSB_BM().trim())){
                                Log.i(TAG, "onMarkerClick: 编码相等");
                                latLng = marker.getPosition();
                                Log.i(TAG, "onResponse: 获取该marker的坐标：：：：：：：");
                                currentInfoWindow = new InfoWindow( getInfoWindowView(mMachineList.getData().get(i)), latLng, -85);
                                Log.i(TAG, "onResponse: new悬浮窗对象：：：：：：");
                                mBaiduMap.showInfoWindow(currentInfoWindow);//显示悬浮窗
                                break;
                            }
                        }
                    }
                    return true;
                }
            });
        }else {
            if (isFirstLoadMap){
                Toast.makeText(MachineMapActivity.this, "无机器可显示", Toast.LENGTH_SHORT).show();
                isFirstLoadMap = false;
            }
        }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private View getInfoWindowView(final MachineList.DataBean dataBean) {
        Log.i(TAG, "getInfoWindowView: 获取弹窗：：：：：要填充的数据为：：：" + dataBean.toString());
        ViewGroup infoView = null;
        if (null == infoView) {
            infoView = (ViewGroup) LayoutInflater.from(MachineMapActivity.this).inflate(R.layout.baidumap_infowindow, null);
        }

        tvRefinery = infoView.findViewById(R.id.tv_refinery);
        tvWorkState = infoView.findViewById(R.id.tv_work_state);
        tvDeviceId = infoView.findViewById(R.id.tv_deviceId);

        tvRefinery.setText("加工厂：" + dataBean.getJgc());
        tvWorkState.setText("工作状态：" + dataBean.getGzzt());
        tvDeviceId.setText("设备编号：" + dataBean.getSB_BM());

        return infoView;
    }


}

