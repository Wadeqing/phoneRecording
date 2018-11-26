package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.DirectCallLawyerResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/8.
 */

public class DirectCallLawyerEvent extends BaseApiEvent {
    public DirectCallLawyerResponse response;
    public DirectCallLawyerEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public DirectCallLawyerEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
