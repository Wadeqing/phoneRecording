package com.sinocall.phonerecordera.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/1/27.
 */

public class WeiXinPay implements Serializable {

    public String appid;
    public String noncestr;
    public String orderId;
    @SerializedName("package")
    public String packageX;
    public String partnerid;
    public String prepayid;
    public String sign;
    public String timestamp;
}
