package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.DelFolderAndFileResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/14.
 */

public class DelFolderAndFileEvent extends BaseApiEvent {
    public DelFolderAndFileResponse response;
    public DelFolderAndFileEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public DelFolderAndFileEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
