package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.PayResultResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/4.
 */

public class PayResultEvent extends BaseApiEvent {
    public PayResultResponse payResultResponse;
    public PayResultEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PayResultEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
