package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.MineCouponResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/1.
 */

public class MineCouponEvent extends BaseApiEvent {
    public MineCouponResponse mineCouponResponse;
    public MineCouponEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public MineCouponEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
