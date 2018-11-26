package com.sinocall.phonerecordera.model.bean;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/1.
 */

public class CouponBean implements Serializable{
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
