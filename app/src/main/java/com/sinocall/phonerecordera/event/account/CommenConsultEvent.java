package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.CommenConsultResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/11.
 */

public class CommenConsultEvent extends BaseApiEvent {
    public CommenConsultResponse response;
    public CommenConsultEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public CommenConsultEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
