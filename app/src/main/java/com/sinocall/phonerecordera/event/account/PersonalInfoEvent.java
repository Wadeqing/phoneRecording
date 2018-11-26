package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.PersonalInfoResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/28.
 *
 */

public class PersonalInfoEvent extends BaseApiEvent {
    public PersonalInfoResponse personalInfoResponse;
    public PersonalInfoEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public PersonalInfoEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
