package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UpLoadSignPicResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/13.
 */

public class UpLoadSignPicEvent extends BaseApiEvent {
    public UpLoadSignPicResponse response;
    public UpLoadSignPicEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UpLoadSignPicEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
