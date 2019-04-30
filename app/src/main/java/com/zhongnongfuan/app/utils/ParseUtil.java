package com.zhongnongfuan.app.utils;

import android.net.NetworkInfo;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.zhongnongfuan.app.bean.DetailState;
import com.zhongnongfuan.app.bean.LatLngBean;
import com.zhongnongfuan.app.bean.LoginResponseBean;
import com.zhongnongfuan.app.bean.Machine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ParseUtil {
    //转换机器集合JSON字符串
    public static Machine parseDeviceListJson(String json) {
        Machine machine;
        List<Machine.DataBean> dataBeanList;
        Machine.DataBean dataBean;
        try {
            machine = new Machine();
            JSONObject object = new JSONObject(json);
            machine.setCode( object.getInt("code"));
            machine.setMsg( object.getString("msg"));
            machine.setCount( object.getInt("count"));

            JSONArray jsonArray = object.getJSONArray("data");
            dataBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                dataBean = new Machine.DataBean();
                dataBean.setSB_BM(jsonObject.getString("SB_BM"));
                dataBean.setMC(jsonObject.getString("MC"));
                dataBean.setBZ(jsonObject.getString("BZ"));
                dataBean.setSaled(jsonObject.getInt("saled"));
                dataBeanList.add(dataBean);
            }
            machine.setData(dataBeanList);
            return machine;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //parse返回经纬度
    public static LatLngBean parseLatLngJson(String json) {
        LatLngBean latLngBean;
        List<LatLngBean.DataBean> dataBeanList;
        LatLngBean.DataBean dataBean;
        try {
            latLngBean = new LatLngBean();
            dataBeanList = new ArrayList<>();
            JSONObject object = new JSONObject(json);
            latLngBean.setCode(object.getInt("code"));
            latLngBean.setMsg(object.getString("msg"));
            latLngBean.setCount(object.getInt("count"));
            JSONArray jsonArray = object.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                dataBean = new LatLngBean.DataBean();
                dataBean.setDeviceid(jsonObject.getString("Deviceid"));
                dataBean.setLat(jsonObject.getString("lat"));
                dataBean.setLon(jsonObject.getString("lon"));
                dataBeanList.add(dataBean);
            }
            latLngBean.setData(dataBeanList);
            return latLngBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //转换具体机器参数JSON字符串
    public static DetailState parseDeviceJson(String json) {
        DetailState detailState = null;
        DetailState.DataBean dataBean = null;
        try {
            detailState = new DetailState();
            dataBean = new DetailState.DataBean();
            JSONObject object = new JSONObject(json);
            detailState.setCode(object.getInt("code"));
            detailState.setMsg(object.getString("msg"));
            detailState.setCount(object.getInt("count"));
            JSONObject jsonObject = object.getJSONObject("data");
            dataBean.setId(jsonObject.getString("id"));
            dataBean.setJgcbm(jsonObject.getString("jgcbm"));
            dataBean.setGzzt(jsonObject.getString("gzzt"));
            String sjStr = jsonObject.getString("sj");
            String dateStr = getDate(sjStr);
            dataBean.setSj(dateStr);
            dataBean.setFw(jsonObject.getDouble("fw"));
            dataBean.setLw(jsonObject.getDouble("lw"));
            dataBean.setSf(jsonObject.getDouble("sf"));
            dataBean.setZl(jsonObject.getDouble("zl"));
            dataBean.setDeviceId(jsonObject.getString("deviceId"));
            dataBean.setFj1(jsonObject.getString("fj1"));
            dataBean.setFj2(jsonObject.getString("fj2"));
            dataBean.setTsj(jsonObject.getString("tsj"));
            dataBean.setPddj(jsonObject.getString("pddj"));
            dataBean.setPldj(jsonObject.getString("pldj"));
            String lon = jsonObject.getString("lon");
            Log.i("Parse", "parseDeviceJson: 时间：：：：：：：" + dataBean.getSj());
            Log.i("Parse", "parseDeviceJson: 当前机器状态：：：：：：：" + dataBean.getGzzt());
            if (lon != null && !lon.equals("") && !lon.equals("null")){
                dataBean.setLon(Double.parseDouble(jsonObject.getString("lon")));
            }else {
                dataBean.setLon(0.0);
            }
            String lat = jsonObject.getString("lat");
            if (lat != null && !lat.equals("") && !lat.equals("null")){
                dataBean.setLat(Double.parseDouble(jsonObject.getString("lat")));
            }else {
                dataBean.setLat(0.0);
            }
            detailState.setData(dataBean);
            return detailState;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将从数据库获取的时间字符串/Date(1556182879613)/转换为时间格式
    private static String getDate(String sjStr) {
        String subStr = sjStr.substring(sjStr.indexOf("(")+1, sjStr.indexOf(")"));
        long dateLong=Long.parseLong(subStr);
        Date date = new Date(dateLong);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=formatter.format(date);
        return dateStr;
    }

    //输入流转字符串
    public static String getStreamString(InputStream tInputStream) {
        if (tInputStream != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                StringBuffer tStringBuffer = new StringBuffer();

                String sTempOneLine = null;
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    //登录返回值转换
    public static LoginResponseBean loginResponseBean(String json) {
        LoginResponseBean loginResponseBean = null;
        try {
            loginResponseBean = new LoginResponseBean();
            JSONObject object = new JSONObject(json);
            loginResponseBean.setCode(object.getInt("code"));
            loginResponseBean.setMsg(object.getString("msg"));
            loginResponseBean.setCount(object.getInt("count"));
            loginResponseBean.setData(object.getString("data"));

            return loginResponseBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}
