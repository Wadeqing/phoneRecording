package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by mac on 2018/1/9.
 */


public class DownloadFinishEvent extends BaseApiEvent {
    public DownloadFinishEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public DownloadFinishEvent(int code, String message, String data) {
        super(code, message, data);
    }
}

