package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.RechageCenterResposse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/11/29.
 */

public class RechageCenterEvent  extends BaseApiEvent{
    public RechageCenterResposse rechageCenterResposse;
    public RechageCenterEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public RechageCenterEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
