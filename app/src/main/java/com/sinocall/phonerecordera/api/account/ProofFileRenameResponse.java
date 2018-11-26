package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/14.
 */

public class ProofFileRenameResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"status":"0"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * status : 0
         */

        public String status;
    }
}
