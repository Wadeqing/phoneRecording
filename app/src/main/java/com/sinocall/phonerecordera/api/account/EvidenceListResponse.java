package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/11/30.
 */

public class EvidenceListResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : [{"fName":"我是一个文件名成我是名称.wav","recordTime":"41''","expireTime":"2017-05-13 11:53:14","size":"123564","verifyStatus":"0","createTime":"2017-04-13 11:53:14","isAlreadySubmit":"false","type":"1","folderID":"541690"},{"fName":"拨打01010086的通话录音.wav","recordTime":"11''","expireTime":"2017-04-08 09:36:41","size":"140204","verifyStatus":"1","createTime":"2017-03-09 09:36:41","isAlreadySubmit":"false","type":"1","folderID":"529184"}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        /**
         * fName : 我是一个文件名成我是名称.wav
         * recordTime : 41''
         * expireTime : 2017-05-13 11:53:14
         * size : 123564
         * verifyStatus : 0
         * createTime : 2017-04-13 11:53:14
         * isAlreadySubmit : false
         * type : 1
         * folderID : 541690
         */

        public String fName;
        public String recordTime;
        public String expireTime;
        public String size;
        public String verifyStatus;
        public String createTime;
        public String isAlreadySubmit;
        public String type;
        public String folderID;
    }
}
