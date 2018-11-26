package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyDetailResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"id":154,"verifyType":"电话录音存证文件","createTime":"2017/12/15","fileName":"拨打01084186438的通话录音.wav","status":"已存证","num":1,"applyCost":900,"copyCost":0,"serverCost":0,"totalCost":900,"verifyPic":"http://182.50.120.90:8091/sinomini//res/verifyPic/20171215/SINOV201712151134078249.jpg"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * id : 154
         * verifyType : 电话录音存证文件
         * createTime : 2017/12/15
         * fileName : 拨打01084186438的通话录音.wav
         * status : 已存证
         * num : 1
         * applyCost : 900
         * copyCost : 0
         * serverCost : 0
         * totalCost : 900
         * verifyPic : http://182.50.120.90:8091/sinomini//res/verifyPic/20171215/SINOV201712151134078249.jpg
         */

        public int id;
        public String verifyType;
        public String createTime;
        public String fileName;
        public String status;
        public int num;
        public int applyCost;
        public int copyCost;
        public int serverCost;
        public int totalCost;
        public String verifyPic;
    }
}
