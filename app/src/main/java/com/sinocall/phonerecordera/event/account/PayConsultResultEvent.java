package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.PayConsultResultResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/12.
 */

public class PayConsultResultEvent extends BaseApiEvent {
    public PayConsultResultResponse response;
    public PayConsultResultEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PayConsultResultEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
