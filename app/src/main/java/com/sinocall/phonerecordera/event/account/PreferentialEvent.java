package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.PreferentialResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/1.
 */

public class PreferentialEvent extends BaseApiEvent {
    public PreferentialResponse preferentialResponse;
    public PreferentialEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PreferentialEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
