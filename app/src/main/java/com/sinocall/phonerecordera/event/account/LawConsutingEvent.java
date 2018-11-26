package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.LawConsultResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/8.
 */

public class LawConsutingEvent extends BaseApiEvent {
    public LawConsultResponse response;
    public LawConsutingEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public LawConsutingEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
