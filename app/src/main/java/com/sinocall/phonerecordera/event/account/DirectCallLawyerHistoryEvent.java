package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.DirectCallLawyerHistoryResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/11.
 */

public class DirectCallLawyerHistoryEvent extends BaseApiEvent {
    public DirectCallLawyerHistoryResponse response;
    public DirectCallLawyerHistoryEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public DirectCallLawyerHistoryEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
