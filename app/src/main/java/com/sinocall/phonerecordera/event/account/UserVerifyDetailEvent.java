package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UserVerifyDetailResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyDetailEvent extends BaseApiEvent {
    public UserVerifyDetailResponse response;
    public UserVerifyDetailEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UserVerifyDetailEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
