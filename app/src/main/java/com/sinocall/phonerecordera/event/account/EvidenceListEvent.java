package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.EvidenceListResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/30.
 */

public class EvidenceListEvent extends BaseApiEvent {
    public EvidenceListResponse evidenceListResponse;
    public EvidenceListEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public EvidenceListEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
