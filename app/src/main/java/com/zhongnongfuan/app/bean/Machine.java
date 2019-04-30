package com.zhongnongfuan.app.bean;

import java.util.List;

/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class Machine {

    /**
     * code : 1
     * msg : Success
     * count : 5
     * data : [{"ID":10,"SB_BM":"5678      ","MC":"111","BZ":"111","userId":"jy","saled":1,"jgcbm":null},{"ID":37,"SB_BM":"111       ","MC":null,"BZ":null,"userId":"jy","saled":1,"jgcbm":null},{"ID":38,"SB_BM":"333       ","MC":null,"BZ":null,"userId":"jy","saled":1,"jgcbm":null},{"ID":39,"SB_BM":"444       ","MC":null,"BZ":null,"userId":"jy","saled":1,"jgcbm":null},{"ID":40,"SB_BM":"555       ","MC":null,"BZ":null,"userId":"jy","saled":1,"jgcbm":null}]
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

    public static class DataBean {
        /**
         * ID : 10
         * SB_BM : 5678
         * MC : 111
         * BZ : 111
         * userId : jy
         * saled : 1
         * jgcbm : null
         */

        private int ID;
        private String SB_BM;
        private String MC;
        private String BZ;
        private String userId;
        private int saled;
        private Object jgcbm;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSB_BM() {
            return SB_BM;
        }

        public void setSB_BM(String SB_BM) {
            this.SB_BM = SB_BM;
        }

        public String getMC() {
            return MC;
        }

        public void setMC(String MC) {
            this.MC = MC;
        }

        public String getBZ() {
            return BZ;
        }

        public void setBZ(String BZ) {
            this.BZ = BZ;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getSaled() {
            return saled;
        }

        public void setSaled(int saled) {
            this.saled = saled;
        }

        public Object getJgcbm() {
            return jgcbm;
        }

        public void setJgcbm(Object jgcbm) {
            this.jgcbm = jgcbm;
        }
    }
}
