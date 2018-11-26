package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/11/29.
 */

public class RechageCenterResposse implements Serializable{

    /**
     * code : 0
     * codeInfo :
     * data : {"remainCoin":1400,"pay":[{"id":29,"iosVersion":"3.0.0","isUsePay":1,"payDec":"支付宝","payType":100},{"id":30,"iosVersion":"3.0.0","isUsePay":1,"payDec":"微信","payType":101},{"id":31,"iosVersion":"3.0.0","isUsePay":0,"payDec":"银联","payType":102},{"id":32,"iosVersion":"3.0.0","isUsePay":1,"payDec":"appStore","payType":103}],"strategy":[{"giftCoinNum":"0","price":"68.0","payPrice":"68.0","remark":"","name":"68元","id":"1"},{"giftCoinNum":"100","price":"168.0","payPrice":"168.0","remark":"","name":"168元","id":"2"},{"giftCoinNum":"300","price":"388.0","payPrice":"388.0","remark":"","name":"388元","id":"3"},{"giftCoinNum":"900","price":"998.0","payPrice":"998.0","remark":"","name":"998元","id":"4"}]}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * remainCoin : 1400
         * pay : [{"id":29,"iosVersion":"3.0.0","isUsePay":1,"payDec":"支付宝","payType":100},{"id":30,"iosVersion":"3.0.0","isUsePay":1,"payDec":"微信","payType":101},{"id":31,"iosVersion":"3.0.0","isUsePay":0,"payDec":"银联","payType":102},{"id":32,"iosVersion":"3.0.0","isUsePay":1,"payDec":"appStore","payType":103}]
         * strategy : [{"giftCoinNum":"0","price":"68.0","payPrice":"68.0","remark":"","name":"68元","id":"1"},{"giftCoinNum":"100","price":"168.0","payPrice":"168.0","remark":"","name":"168元","id":"2"},{"giftCoinNum":"300","price":"388.0","payPrice":"388.0","remark":"","name":"388元","id":"3"},{"giftCoinNum":"900","price":"998.0","payPrice":"998.0","remark":"","name":"998元","id":"4"}]
         */

        public int remainCoin;
        public List<PayBean> pay;
        public List<StrategyBean> strategy;

        public static class PayBean {
            /**
             * id : 29
             * iosVersion : 3.0.0
             * isUsePay : 1
             * payDec : 支付宝
             * payType : 100
             */

            public int id;
            public String iosVersion;
            public int isUsePay;
            public String payDec;
            public int payType;
        }

        public static class StrategyBean {
            /**
             * giftCoinNum : 0
             * price : 68.0
             * payPrice : 68.0
             * remark :
             * name : 68元
             * id : 1
             */

            public String giftCoinNum;
            public String price;
            public String payPrice;
            public String remark;
            public String name;
            public String id;
        }
    }
}
