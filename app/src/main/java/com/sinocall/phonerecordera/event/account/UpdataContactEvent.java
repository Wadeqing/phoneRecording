package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/27.
 */

public class UpdataContactEvent extends BaseApiEvent {
    public UpdataContactEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UpdataContactEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
