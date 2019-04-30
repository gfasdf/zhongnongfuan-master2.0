package com.zhongnongfuan.app.bean;

import java.io.Serializable;

public class DetailState implements Serializable {


    /**
     * code : 1
     * msg : Success
     * count : 1
     * data : {"id":"207b46d2-68bc-45c3-8223-c8619439b602","gzzt":"杩涚伯","sj":"/Date(1555394571810)/","fw":23.1,"lw":25.5,"sf":13.2,"zl":3802,"deviceId":"5678","fj1":"on","fj2":"off","tsj":"on","pddj":"off","pldj":"on","lon":null,"lat":null,"fastComm":0,"ccid":"898607B1101700823754","warnTemp":50,"userLock":0,"jgcbm":null}
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

    public static class DataBean implements Serializable{
        /**
         * id : 207b46d2-68bc-45c3-8223-c8619439b602
         * gzzt : 杩涚伯
         * sj : /Date(1555394571810)/
         * fw : 23.1
         * lw : 25.5
         * sf : 13.2
         * zl : 3802.0
         * deviceId : 5678
         * fj1 : on
         * fj2 : off
         * tsj : on
         * pddj : off
         * pldj : on
         * lon : null
         * lat : null
         * fastComm : 0
         * ccid : 898607B1101700823754
         * warnTemp : 50.0
         * userLock : 0
         * jgcbm : null
         */

        private String id;
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
        private double warnTemp;
        private int userLock;
        private Object jgcbm;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public double getWarnTemp() {
            return warnTemp;
        }

        public void setWarnTemp(double warnTemp) {
            this.warnTemp = warnTemp;
        }

        public int getUserLock() {
            return userLock;
        }

        public void setUserLock(int userLock) {
            this.userLock = userLock;
        }

        public Object getJgcbm() {
            return jgcbm;
        }

        public void setJgcbm(Object jgcbm) {
            this.jgcbm = jgcbm;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", gzzt='" + gzzt + '\'' +
                    ", sj='" + sj + '\'' +
                    ", fw=" + fw +
                    ", lw=" + lw +
                    ", sf=" + sf +
                    ", zl=" + zl +
                    ", deviceId='" + deviceId + '\'' +
                    ", fj1='" + fj1 + '\'' +
                    ", fj2='" + fj2 + '\'' +
                    ", tsj='" + tsj + '\'' +
                    ", pddj='" + pddj + '\'' +
                    ", pldj='" + pldj + '\'' +
                    ", lon=" + lon +
                    ", lat=" + lat +
                    ", fastComm=" + fastComm +
                    ", ccid='" + ccid + '\'' +
                    ", warnTemp=" + warnTemp +
                    ", userLock=" + userLock +
                    ", jgcbm=" + jgcbm +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DetailState{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
