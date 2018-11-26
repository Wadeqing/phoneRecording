package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/11.
 */

public class HistoryConsultResponse implements Serializable {


    /**
     * code : 0
     * codeInfo :
     * data : [{"id":85,"question":"19","createTime":"2017-12-01 15:10:55","payPrice":98,"commentStatus":1,"payStatus":1,"proofFileId":0},{"id":71,"question":"这是第五条数据！这是第五条数据！这是第五条数据！这是第五条数据！这是第五条数据！这是第五条数据！这是第五条数据！这是第五条数据！","createTime":"2017-11-29 11:17:08","payPrice":98,"commentStatus":1,"payStatus":1,"proofFileId":541977},{"id":80,"question":"14","createTime":"2017-05-04 14:26:21","payPrice":38,"commentStatus":1,"payStatus":1,"proofFileId":0},{"id":87,"question":"21","createTime":"2017-05-04 13:40:31","payPrice":68,"commentStatus":1,"payStatus":1,"proofFileId":0},{"id":83,"question":"17","createTime":"2017-05-04 10:40:12","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":82,"question":"16","createTime":"2017-05-04 10:39:54","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":77,"question":"十一","createTime":"2017-05-03 18:15:25","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":76,"question":"十十十十十","createTime":"2017-05-03 18:07:14","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":74,"question":"888888888888888","createTime":"2017-05-03 18:05:56","payPrice":68,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":69,"question":"这是第三条数据！这是第三条数据！这是第三条数据！这是第三条数据！这是第三条数据！这是第三条数据！","createTime":"2017-05-03 17:56:19","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0},{"id":67,"question":"这是第一条数据！第一条第一条第一条第一条第一条第一条第一条","createTime":"2017-05-03 17:38:58","payPrice":38,"commentStatus":0,"payStatus":1,"proofFileId":0}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 85
         * question : 19
         * createTime : 2017-12-01 15:10:55
         * payPrice : 98
         * commentStatus : 1
         * payStatus : 1
         * proofFileId : 0
         */

        public int id;
        public String question;
        public String createTime;
        public int payPrice;
        public int commentStatus;
        public int payStatus;
        public int proofFileId;
    }
}
