package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/4.
 */

public class PayResultResponse implements Serializable {


    /**
     * code : 0
     * codeInfo :
     * data : {"recordCoin":644}
     */

    public int code;
    public String codeInfo;
    public Object data;

    public static class DataBean {
        /**
         * recordCoin : 644
         */

        public int recordCoin;
    }
}
