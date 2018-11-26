package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/12.
 */

public class H5AskQuestionResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"_input_charset":"UTF-8","subject":"律师咨询费","sign":"s2w6crYXyGz8MboY4OKmofXQcV/SBRfaIy3ZB0PtOzOIfKQPsTMeQi985jJrpU1vuGzJFioBWKDRlO7uXgV2jBK3cfauU0DL68idD+JlACx0zRSv6VhX0wYBzbHLqVhLUFMjec2hnbe7rdxnbLm+jQf1dLydA6DPPbvg7UgVFOI=","notify_url":"http://182.50.120.90:8881/askQuestionNotify.action","body":"律师咨询费","payment_type":"1","out_trade_no":"lh201711161500340352","partner":"2088911439658303","service":"alipay.wap.create.direct.pay.by.user","total_fee":"48.0","return_url":"http://182.50.120.90:8881/views/askLawyer.html?payReturn=1","sign_type":"RSA","seller_id":"sinocall_caiwu@163.com","show_url":"http://182.50.120.90:8881/views/askLawyer.html?payReturn=1"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        public String aliPayData;
        public String sendStr;
        public int payType;
        public String wxData;
    }
}
