package com.zhongnongfuan.app.bean;

import java.io.Serializable;

public class DetailState implements Serializable {


    /**
     * code : 1
     * msg : Success
     * count : 1
     * data : {"id":"49eec7b5-c99b-4fd7-b2fa-6a13444877ad","jgcbm":"1234","gzzt":"报警","sj":"/Date(1558269536007)/","fw":23.1,"lw":25.5,"sf":13.2,"zl":3802,"deviceId":"5678","fj1":"on","fj2":"off","tsj":"on","pddj":"warn","pldj":"on","lon":null,"lat":null,"fastComm":0,"ccid":"898607B1101700823754","warnTemp":null,"userLock":0,"smoke":null}
     */

    private int code;
    private String msg;
    private int count;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 49eec7b5-c99b-4fd7-b2fa-6a13444877ad
         * jgcbm : 1234
         * gzzt : 报警
         * sj : /Date(1558269536007)/
         * fw : 23.1
         * lw : 25.5
         * sf : 13.2
         * zl : 3802.0
         * deviceId : 5678
         * fj1 : on
         * fj2 : off
         * tsj : on
         * pddj : warn
         * pldj : on
         * lon : null
         * lat : null
         * fastComm : 0
         * ccid : 898607B1101700823754
         * warnTemp : null
         * userLock : 0
         * smoke : null
         */

        private String id;
        private String jgcbm;
        private String gzzt;
        private String sj;
        private double fw;
        private double lw;
        private double sf;
        private double zl;
        private String deviceId;
        private String fj1;
        private String fj2;
        private String tsj;
        private String pddj;
        private String pldj;
        private Object lon;
        private Object lat;
        private int fastComm;
        private String ccid;
        private Object warnTemp;
        private int userLock;
        private Object smoke;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJgcbm() {
            return jgcbm;
        }

        public void setJgcbm(String jgcbm) {
            this.jgcbm = jgcbm;
        }

        public String getGzzt() {
            return gzzt;
        }

        public void setGzzt(String gzzt) {
            this.gzzt = gzzt;
        }

        public String getSj() {
            return sj;
        }

        public void setSj(String sj) {
            this.sj = sj;
        }

        public double getFw() {
            return fw;
        }

        public void setFw(double fw) {
            this.fw = fw;
        }

        public double getLw() {
            return lw;
        }

        public void setLw(double lw) {
            this.lw = lw;
        }

        public double getSf() {
            return sf;
        }

        public void setSf(double sf) {
            this.sf = sf;
        }

        public double getZl() {
            return zl;
        }

        public void setZl(double zl) {
            this.zl = zl;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getFj1() {
            return fj1;
        }

        public void setFj1(String fj1) {
            this.fj1 = fj1;
        }

        public String getFj2() {
            return fj2;
        }

        public void setFj2(String fj2) {
            this.fj2 = fj2;
        }

        public String getTsj() {
            return tsj;
        }

        public void setTsj(String tsj) {
            this.tsj = tsj;
        }

        public String getPddj() {
            return pddj;
        }

        public void setPddj(String pddj) {
            this.pddj = pddj;
        }

        public String getPldj() {
            return pldj;
        }

        public void setPldj(String pldj) {
            this.pldj = pldj;
        }

        public Object getLon() {
            return lon;
        }

        public void setLon(Object lon) {
            this.lon = lon;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public int getFastComm() {
            return fastComm;
        }

        public void setFastComm(int fastComm) {
            this.fastComm = fastComm;
        }

        public String getCcid() {
            return ccid;
        }

        public void setCcid(String ccid) {
            this.ccid = ccid;
        }

        public Object getWarnTemp() {
            return warnTemp;
        }

        public void setWarnTemp(Object warnTemp) {
            this.warnTemp = warnTemp;
        }

        public int getUserLock() {
            return userLock;
        }

        public void setUserLock(int userLock) {
            this.userLock = userLock;
        }

        public Object getSmoke() {
            return smoke;
        }

        public void setSmoke(Object smoke) {
            this.smoke = smoke;
        }
    }
}
