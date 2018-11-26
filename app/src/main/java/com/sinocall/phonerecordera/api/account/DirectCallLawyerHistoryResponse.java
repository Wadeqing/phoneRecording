package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/11.
 */

public class DirectCallLawyerHistoryResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : [{"id":42,"comment":"","callInterval":"","updateTime":"2017-11-22 18:05:56","commentStatus":1,"proofFileId":542023,"size":8120,"fileName":"哈哈哈哈哈.wav"},{"id":44,"comment":"sssss","callInterval":"","updateTime":"2017-11-23 10:11:32","commentStatus":1,"proofFileId":542024,"size":60,"fileName":"拨打法律服务的通话录音.wav"},{"id":58,"comment":"是的","callInterval":"","updateTime":"2017-11-23 15:06:00","commentStatus":1,"proofFileId":542025,"size":9160,"fileName":"这段h\u2006h\u2006g\u2006g\u2006个.wav"},{"id":59,"comment":"嘚嘚","callInterval":"","updateTime":"2017-11-23 15:41:44","commentStatus":1,"proofFileId":542026,"size":11370,"fileName":".wav"},{"id":60,"comment":"sdddd","callInterval":"","updateTime":"2017-11-23 16:28:30","commentStatus":1,"proofFileId":542027,"size":273710,"fileName":"拨打法律服务的通话录音.wav"}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 42
         * comment :
         * callInterval :
         * updateTime : 2017-11-22 18:05:56
         * commentStatus : 1
         * proofFileId : 542023
         * size : 8120
         * fileName : 哈哈哈哈哈.wav
         */

        public int id;
        public String comment;
        public String callInterval;
        public String updateTime;
        public int commentStatus;
        public int proofFileId;
        public int size;
        public String fileName;
    }
}
