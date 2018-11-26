package com.sinocall.phonerecordera.api.account;

import com.sinocall.phonerecordera.model.bean.CouponBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/1.
 */

public class CouponHistoricalResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * code : 0
     * codeInfo :
     * data : [{"amount":6,"unit":"分钟","expireTime":"2016-12-02","createTime":"2016-11-25","couponType":1,"couponDesc":"五星好评赠通话代金券（2)","status":3},{"amount":2,"unit":"分钟","expireTime":"2016-12-25","createTime":"2016-11-25","couponType":1,"couponDesc":"五星好评赠通话代金券（3)","status":3},{"amount":10,"unit":"分钟","expireTime":"2016-12-02","createTime":"2016-11-25","couponType":1,"couponDesc":"五星好评赠通话代金券（1)","status":3},{"amount":2,"unit":"分钟","expireTime":"2016-12-25","createTime":"2016-11-25","couponType":1,"couponDesc":"五星好评赠通话代金券（4)","status":3},{"amount":1,"unit":"分钟","expireTime":"2017-03-18","createTime":"2017-02-16","couponType":1,"couponDesc":"新春祝福体验券","status":1},{"amount":1,"unit":"分钟","expireTime":"2017-02-23","createTime":"2017-02-16","couponType":1,"couponDesc":"分享好友体验券","status":3}]
     */

    public int code;
    public String codeInfo;
    public List<CouponBean> data;

    public static class DataBean {
        /**
         * amount : 6
         * unit : 分钟
         * expireTime : 2016-12-02
         * createTime : 2016-11-25
         * couponType : 1
         * couponDesc : 五星好评赠通话代金券（2)
         * status : 3
         */

        public int amount;
        public String unit;
        public String expireTime;
        public String createTime;
        public int couponType;
        public String couponDesc;
        public int status;
    }
}
