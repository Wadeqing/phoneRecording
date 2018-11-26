package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.TencentMessageReceiver;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/10.
 */

public class PushEvent extends BaseApiEvent {
    public TencentMessageReceiver.PushModel model;
    public PushEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PushEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
