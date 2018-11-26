package com.sinocall.phonerecordera.model.bean;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/1/27.
 */

public class LawyerPayResult implements Serializable {
    /**
     * payType : 100
     * aliPayData : {"partner":"2088911439658303","seller":"sinocall_caiwu@163.com","out_trade_no":"dhly201707041354481509","subject":"购买录音币","body":"购买录音币","total_fee":"168.0","notify_url":"http://52046f75.nat123.net/payNotify.action"}
     * sendStr : partner="2088911439658303"&seller="sinocall_caiwu@163.com"&out_trade_no="dhly201707041354481509"&subject="购买录音币"&body="购买录音币"&total_fee="168.0"&notify_url="http://52046f75.nat123.net/payNotify.action"&sign_type="RSA"&sign="coGHNCarx9hVxfSq8psLf7RTNlIhSG79MFocC5FRvRZsds558fbN1hKDYFTmcIN8%2BGiAJPryqPs3O5ooEauDLZNJj8WIxWYXjcWG%2BTz5%2BzBy1KswSlUmfeAQ1oZGh1WVhpv2nWeBbngkUSJv6Nqz%2FBBXjjr9tXWaHIwb3sYEeZA%3D"
     */
    public String aliPayData;
    public String sendStr;
    public int payType;
    public String wxData;
}
