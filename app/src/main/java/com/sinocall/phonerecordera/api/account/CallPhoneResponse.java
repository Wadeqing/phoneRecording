package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/6.
 */

public class CallPhoneResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * code : 0
     * codeInfo :
     * data : 02259831708
     */

    public int code;
    public String codeInfo;
    public String data;
}
