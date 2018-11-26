package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/1/12.
 */

public class UserChargeCoinCouponResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"chargeFlag":1,"couponNum":0,"remainCoin":65280}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * chargeFlag : 1
         * couponNum : 0
         * remainCoin : 65280
         */

        public int chargeFlag;
        public int couponNum;
        public double remainCoin;
        public String infoImage;
    }
}
