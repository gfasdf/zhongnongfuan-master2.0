package com.zhongnongfuan.app.bean;

import java.util.List;

public class MonitorBean {

    /**
     * code : 1
     * msg : Success
     * accesstoken : at.cxtwj61ob5kxaqxhae8zd9shbudcaqh8-5tov3iotem-1komnxb-geqnfjfih
     * count : 1
     * data : [{"ID":2,"XLH":"231878716","YZM":"MCNZKW","JGCBM":"4101010000","MC":"2号摄像头","WZ":"3区东北角","BZ":null,"JGCMC":"中农福安"}]
     */

    private int code;
    private String msg;
    private String accesstoken;
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

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
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

    public static class DataBean {
        /**
         * ID : 2
         * XLH : 231878716
         * YZM : MCNZKW
         * JGCBM : 4101010000
         * MC : 2号摄像头
         * WZ : 3区东北角
         * BZ : null
         * JGCMC : 中农福安
         */

        private int ID;
        private String XLH;
        private String YZM;
        private String JGCBM;
        private String MC;
        private String WZ;
        private Object BZ;
        private String JGCMC;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getXLH() {
            return XLH;
        }

        public void setXLH(String XLH) {
            this.XLH = XLH;
        }

        public String getYZM() {
            return YZM;
        }

        public void setYZM(String YZM) {
            this.YZM = YZM;
        }

        public String getJGCBM() {
            return JGCBM;
        }

        public void setJGCBM(String JGCBM) {
            this.JGCBM = JGCBM;
        }

        public String getMC() {
            return MC;
        }

        public void setMC(String MC) {
            this.MC = MC;
        }

        public String getWZ() {
            return WZ;
        }

        public void setWZ(String WZ) {
            this.WZ = WZ;
        }

        public Object getBZ() {
            return BZ;
        }

        public void setBZ(Object BZ) {
            this.BZ = BZ;
        }

        public String getJGCMC() {
            return JGCMC;
        }

        public void setJGCMC(String JGCMC) {
            this.JGCMC = JGCMC;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "ID=" + ID +
                    ", XLH='" + XLH + '\'' +
                    ", YZM='" + YZM + '\'' +
                    ", JGCBM='" + JGCBM + '\'' +
                    ", MC='" + MC + '\'' +
                    ", WZ='" + WZ + '\'' +
                    ", BZ=" + BZ +
                    ", JGCMC='" + JGCMC + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MonitorBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
