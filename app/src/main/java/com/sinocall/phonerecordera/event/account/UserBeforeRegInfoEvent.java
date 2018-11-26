package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UserBeforeRegInfoResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/25.
 */

public class UserBeforeRegInfoEvent extends BaseApiEvent {
    public UserBeforeRegInfoResponse response;
    public UserBeforeRegInfoEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UserBeforeRegInfoEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
