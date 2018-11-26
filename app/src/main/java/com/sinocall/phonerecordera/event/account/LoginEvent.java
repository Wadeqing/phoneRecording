package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/28.
 */

public class LoginEvent extends BaseApiEvent {
    public LoginEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public LoginEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
