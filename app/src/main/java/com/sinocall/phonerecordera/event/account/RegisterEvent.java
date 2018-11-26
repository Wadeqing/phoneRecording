package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/28.
 */

public class RegisterEvent extends BaseApiEvent {
    public RegisterEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public RegisterEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
