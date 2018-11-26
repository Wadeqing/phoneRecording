package com.sinocall.phonerecordera.api.account;

import com.sinocall.phonerecordera.model.bean.CouponBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/1.
 */

public class MineCouponResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : [{"amount":3,"unit":"分钟","expireTime":"2017-08-02","createTime":"2017-07-03","couponType":1,"couponDesc":"新用户注册体验券","status":0}]
     */

    public int code;
    public String codeInfo;
    public List<CouponBean> data;

    public static class DataBean {
        /**
         * amount : 3
         * unit : 分钟
         * expireTime : 2017-08-02
         * createTime : 2017-07-03
         * couponType : 1
         * couponDesc : 新用户注册体验券
         * status : 0
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
