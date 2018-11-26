package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.FileDetailResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/30.
 */

public class FileDetailEvent extends BaseApiEvent {
    public FileDetailResponse fileDetailResponse;
    public FileDetailEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public FileDetailEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
