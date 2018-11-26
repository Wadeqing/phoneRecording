package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/14.
 */

public class DelFolderAndFileResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        public String status;
    }
}
