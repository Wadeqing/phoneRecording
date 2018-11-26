package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/11/30.
 */

public class EvidenceRootListResponse implements Serializable{


    /**
     * code : 0
     * codeInfo :
     * data : [{"haschild":"false","dataNum":"0","folderName":"回收站","type":"0","folderID":"4998979"},{"haschild":"false","dataNum":"50","folderName":"去电录音","type":"1","folderID":"4998977"},{"haschild":"false","dataNum":"0","folderName":"现场录音","type":"2","folderID":"4998978"},{"haschild":"false","dataNum":"6","folderName":"照片图片","type":"3","folderID":"4998982"},{"haschild":"false","dataNum":"0","folderName":"音频视频","type":"4","folderID":"4998980"},{"haschild":"false","dataNum":"0","folderName":"其它资料","type":"5","folderID":"4998983"},{"haschild":"false","dataNum":"0","folderName":"本地文件","type":"6","folderID":"4998981"}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * haschild : false
         * dataNum : 0
         * folderName : 回收站
         * type : 0
         * folderID : 4998979
         */

        public String haschild;
        public String dataNum;
        public String folderName;
        public String type;
        public String folderID;
    }
}
