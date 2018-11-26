package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.CallPhoneResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/6.
 */

public class CallPhoneEvent extends BaseApiEvent {
    public CallPhoneResponse response;
    public int typeLog;//  0.键盘直接拨号   1.首页通讯录  2.首页通话记录 3.通讯录页面
    public String callMobileNo;

    public CallPhoneEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public CallPhoneEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
