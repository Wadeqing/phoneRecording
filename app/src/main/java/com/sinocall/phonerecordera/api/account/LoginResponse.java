package com.sinocall.phonerecordera.api.account;

import com.sinocall.phonerecordera.model.bean.UserInfo;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/11/28.
 */

public class LoginResponse implements Serializable{

    /**
     * code : 0
     * codeInfo :
     * data : {"unReadMsgNum":"2","secretKey":"bypapx967389","transfer_tel":"022-59831700","remain":"1400.0","mobileNo":"18826679482","phone_setting":"1","userID":"1215485","succRecordNum":"1000000","isExist":"false","userCode":"18826679482KPK","just_setting":"0"}
     */

    public int code;
    public String codeInfo;
    public UserInfo data;

}
