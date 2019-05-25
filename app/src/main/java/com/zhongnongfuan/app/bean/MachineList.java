package com.zhongnongfuan.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author qichaoqun
 * @date 2019/1/19
 */
public class MachineList implements Serializable {


    /**
     * code : 1
     * msg : Success
     * count : 8
     * data : [{"ID":10,"SB_BM":"5678      ","MC":"30烘干机","BZ":"郑州设备1号","userId":"jy","saled":1,"jgcbm":"1234","jgc":"江西长宏农科","gzzt":"报警","warn":false},{"ID":37,"SB_BM":"111       ","MC":"郑州设备2号","BZ":"郑州设备2号","userId":"jy","saled":1,"jgcbm":"410101","jgc":"湖北中航","gzzt":"进粮","warn":false},{"ID":38,"SB_BM":"333       ","MC":"郑州设备3号","BZ":"郑州设备3号","userId":"jy","saled":1,"jgcbm":"410101","jgc":"湖北中航","gzzt":"烘干","warn":false},{"ID":39,"SB_BM":"444       ","MC":"郑州设备4号","BZ":"郑州设备4号","userId":"jy","saled":1,"jgcbm":"1238","jgc":"高升农业","gzzt":null,"warn":false},{"ID":40,"SB_BM":"555       ","MC":"郑州设备5号","BZ":"郑州设备5号","userId":"jy","saled":1,"jgcbm":null,"jgc":null,"gzzt":null,"warn":false},{"ID":68,"SB_BM":"421       ","MC":"郑州设备8号","BZ":"郑州设备8号","userId":"jy","saled":1,"jgcbm":"1234","jgc":null,"gzzt":"烘干","warn":false},{"ID":69,"SB_BM":"121       ","MC":"郑州设备9号","BZ":"郑州设备9号","userId":"jy","saled":1,"jgcbm":"1234","jgc":null,"gzzt":"烘干","warn":false},{"ID":73,"SB_BM":"1111      ","MC":"郑州设备2号","BZ":"11","userId":"jy","saled":1,"jgcbm":"410101","jgc":"湖北中航","gzzt":null,"warn":false}]
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

    @Override
    public String toString() {
        return "MachineList{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        /**
         * ID : 10
         * SB_BM : 5678
         * MC : 30烘干机
         * BZ : 郑州设备1号
         * userId : jy
         * saled : 1
         * jgcbm : 1234
         * jgc : 江西长宏农科
         * gzzt : 报警
         */

        private int ID;
        private String SB_BM;
        private String MC;
        private String BZ;
        private String userId;
        private int saled;
        private String jgcbm;
        private String jgc;
        private String gzzt;

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

        public String getJgcbm() {
            return jgcbm;
        }

        public void setJgcbm(String jgcbm) {
            this.jgcbm = jgcbm;
        }

        public String getJgc() {
            return jgc;
        }

        public void setJgc(String jgc) {
            this.jgc = jgc;
        }

        public String getGzzt() {
            return gzzt;
        }

        public void setGzzt(String gzzt) {
            this.gzzt = gzzt;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "ID=" + ID +
                    ", SB_BM='" + SB_BM + '\'' +
                    ", MC='" + MC + '\'' +
                    ", BZ='" + BZ + '\'' +
                    ", userId='" + userId + '\'' +
                    ", saled=" + saled +
                    ", jgcbm='" + jgcbm + '\'' +
                    ", jgc='" + jgc + '\'' +
                    ", gzzt='" + gzzt + '\'' +
                    '}';
        }
    }
}
