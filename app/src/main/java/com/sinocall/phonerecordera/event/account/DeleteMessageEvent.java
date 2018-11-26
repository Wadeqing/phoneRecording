package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.DeleteMessageResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/1.
 */

public class DeleteMessageEvent extends BaseApiEvent {
    public DeleteMessageResponse deleteMessageResponse;
    public DeleteMessageEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public DeleteMessageEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
