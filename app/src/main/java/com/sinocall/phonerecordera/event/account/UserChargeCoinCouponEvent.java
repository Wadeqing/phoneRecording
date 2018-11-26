package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.UserChargeCoinCouponResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2018/1/12.
 */

public class UserChargeCoinCouponEvent extends BaseApiEvent {
    public UserChargeCoinCouponResponse response;
    public UserChargeCoinCouponEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public UserChargeCoinCouponEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
