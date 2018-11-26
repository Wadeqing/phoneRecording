package com.sinocall.phonerecordera.event;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.ApplyVerifyResponse;

/**
 * Created by qingchao on 2017/12/13.
 */

public class ApplyVerifyEvent extends BaseApiEvent {
    public ApplyVerifyResponse response;
    public ApplyVerifyEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public ApplyVerifyEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
