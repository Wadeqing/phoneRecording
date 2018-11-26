package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.RemainCoinResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by mac on 2018/1/4.
 */

public class RemainCoinEvent extends BaseApiEvent {
    public RemainCoinResponse remainCoinResponse;
    public RemainCoinEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public RemainCoinEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
