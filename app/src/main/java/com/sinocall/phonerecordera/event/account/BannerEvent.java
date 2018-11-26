package com.sinocall.phonerecordera.event.account;

import android.app.Activity;

import com.sinocall.phonerecordera.api.account.BannerResponse;
import com.sinocall.phonerecordera.event.BaseApiEvent;

/**
 * Created by qingchao on 2017/12/19.
 */

public class BannerEvent extends BaseApiEvent {
    public BannerResponse response;
    public BannerEvent(int code, String message, String data, Activity activity) {
        super(code, message, data, activity);
    }

    public BannerEvent(int code, String message, String data) {
        super(code, message, data);
    }
}
