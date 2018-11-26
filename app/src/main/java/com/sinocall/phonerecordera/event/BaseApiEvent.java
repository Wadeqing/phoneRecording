package com.sinocall.phonerecordera.event;

import android.app.Activity;


public abstract class BaseApiEvent {
    public int code;
    public String message;
    public String data;
    private Activity activity;

    public BaseApiEvent(int code, String message,String data, Activity activity) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.activity = activity;

    }

    public BaseApiEvent(int code, String message,String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
