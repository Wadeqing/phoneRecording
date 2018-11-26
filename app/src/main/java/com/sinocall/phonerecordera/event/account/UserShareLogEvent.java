package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UserShareLogResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/2.
 */

public class UserShareLogEvent extends BaseApiEvent {
    public UserShareLogResponse getUserShareLogResponse() {
        return userShareLogResponse;
    }

    public void setUserShareLogResponse(UserShareLogResponse userShareLogResponse) {
        this.userShareLogResponse = userShareLogResponse;
    }

    private UserShareLogResponse userShareLogResponse;

    public UserShareLogEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UserShareLogEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
