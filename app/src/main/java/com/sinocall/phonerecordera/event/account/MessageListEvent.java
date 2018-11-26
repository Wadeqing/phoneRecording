package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.MessageListResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/1.
 */

public class MessageListEvent extends BaseApiEvent {
    public MessageListResponse messageListResponse;
    public MessageListEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public MessageListEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
