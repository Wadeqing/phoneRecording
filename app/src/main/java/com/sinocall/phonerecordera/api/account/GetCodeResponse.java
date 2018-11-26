package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/11/28.
 */

public class GetCodeResponse implements Serializable{

    /**
     * code : 0
     * codeInfo :
     * data : {"status":"true"}
     */

    public int code;
    public String codeInfo;
    public Object data;

    public static class DataBean {
        /**
         * status : true
         */

        public String status;
    }
}
