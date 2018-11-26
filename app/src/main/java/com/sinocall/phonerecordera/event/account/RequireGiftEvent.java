package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.RequireGiftResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/20.
 */

public class RequireGiftEvent extends BaseApiEvent {
    public RequireGiftResponse response;
    public RequireGiftEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public RequireGiftEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
