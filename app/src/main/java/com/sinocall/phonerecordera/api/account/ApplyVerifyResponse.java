package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/13.
 */

public class ApplyVerifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * code : 0
     * codeInfo :
     * data : {"verifyPic":"http://182.50.120.90:8881//res/verifyPic/20171213/SINOV201712131611297256.jpg","remainCoin":-1408}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {

        public String verifyPic;
        public int remainCoin;
    }
}
