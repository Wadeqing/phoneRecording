package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.BindPushResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/9.
 */

public class BindPushEvent extends BaseApiEvent {
    public BindPushResponse response;
    public BindPushEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public BindPushEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
