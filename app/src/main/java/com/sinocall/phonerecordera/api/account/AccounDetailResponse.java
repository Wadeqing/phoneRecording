package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/11/30.
 * @author l
 */

public class AccounDetailResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * code : 0
     * codeInfo :
     * data : [{"reason":"充值:68.0(70.0元套餐)","num":700,"createTime":{"day":18,"date":18,"hours":11,"minutes":14,"month":4,"seconds":56,"time":1492485296000,"timezoneOffset":28800000,"year":2017},"remainCoin":1400,"type":10,"couponAmount":""},{"reason":"充值:68.0(70.0元套餐)","num":700,"createTime":{"day":18,"date":18,"hours":9,"minutes":54,"month":4,"seconds":22,"time":1492480462000,"timezoneOffset":28800000,"year":2017},"remainCoin":700,"type":10,"couponAmount":""}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * reason : 充值:68.0(70.0元套餐)
         * num : 700
         * createTime : {"day":18,"date":18,"hours":11,"minutes":14,"month":4,"seconds":56,"time":1492485296000,"timezoneOffset":28800000,"year":2017}
         * remainCoin : 1400
         * type : 10
         * couponAmount :
         */

        public String reason;
        public int num;
        public CreateTimeBean createTime;
        public int remainCoin;
        public int type;
        public String couponAmount;

        public static class CreateTimeBean {
            /**
             * day : 18
             * date : 18
             * hours : 11
             * minutes : 14
             * month : 4
             * seconds : 56
             * time : 1492485296000
             * timezoneOffset : 28800000
             * year : 2017
             */

            public int day;
            public int date;
            public int hours;
            public int minutes;
            public int month;
            public int seconds;
            public long time;
            public int timezoneOffset;
            public int year;
        }
    }
}
