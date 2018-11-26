package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/28.
 */

public class ResetPasswordEvent extends BaseApiEvent {
    public ResetPasswordEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public ResetPasswordEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
