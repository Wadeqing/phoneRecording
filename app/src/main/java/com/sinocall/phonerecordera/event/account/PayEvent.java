package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.PayResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/29.
 */

public class PayEvent extends BaseApiEvent {
    public PayResponse payResponse;
    public int payType;
    public PayEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PayEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
