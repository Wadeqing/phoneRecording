package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UpDataMessageResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/1.
 */

public class UpdataMessageEvent extends BaseApiEvent {
    public UpDataMessageResponse upDataMessageResponse;
    public UpdataMessageEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UpdataMessageEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
