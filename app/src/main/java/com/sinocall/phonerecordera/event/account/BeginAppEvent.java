package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.BeginAppResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/2/2.
 */

public class BeginAppEvent extends BaseApiEvent {
    public BeginAppResponse response;
    public BeginAppEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public BeginAppEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
