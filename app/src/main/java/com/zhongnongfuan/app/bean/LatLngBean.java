package com.zhongnongfuan.app.bean;

import java.io.Serializable;
import java.util.List;

public class LatLngBean implements Serializable {

    /**
     * code : 1
     * msg : Success
     * count : 2
     * data : [{"Deviceid":"5678      ","lon":"99.2789075418","lat":"37.8981372270"},{"Deviceid":"111       ","lon":"114.733514","lat":"33.858099"}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * Deviceid : 5678
         * lon : 99.2789075418
         * lat : 37.8981372270
         */

        private String Deviceid;
        private String lon;
        private String lat;

        public String getDeviceid() {
            return Deviceid;
        }

        public void setDeviceid(String Deviceid) {
            this.Deviceid = Deviceid;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Deviceid='" + Deviceid + '\'' +
                    ", lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LatLngBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
