package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.ProofFileRenameResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/14.
 */

public class ProofFileRenameEvent extends BaseApiEvent {
    public ProofFileRenameResponse response;

    public ProofFileRenameEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public ProofFileRenameEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
