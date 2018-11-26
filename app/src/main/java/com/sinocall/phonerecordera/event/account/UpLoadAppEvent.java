package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/5.
 */

public class UpLoadAppEvent extends BaseApiEvent {

    public UpLoadAppEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UpLoadAppEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
