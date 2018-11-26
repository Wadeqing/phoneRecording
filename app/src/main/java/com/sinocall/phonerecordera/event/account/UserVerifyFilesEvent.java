package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UserVerifyFilesResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyFilesEvent extends BaseApiEvent {
    public UserVerifyFilesResponse response;
    public UserVerifyFilesEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UserVerifyFilesEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
