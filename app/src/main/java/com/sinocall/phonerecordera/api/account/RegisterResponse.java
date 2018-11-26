package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/11/28.
 */

public class RegisterResponse implements Serializable {

    /**
     * code : 1001
     * codeInfo : 校验码验证失败
     * data :
     */

    public int code;
    public String codeInfo;
    public Object data;
}
