package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.H5AskQuestionResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/12.
 */

public class H5AskQuestionEvent extends BaseApiEvent {
    public H5AskQuestionResponse response;
    public H5AskQuestionEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public H5AskQuestionEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
