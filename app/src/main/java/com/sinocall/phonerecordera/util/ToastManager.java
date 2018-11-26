package com.sinocall.phonerecordera.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class ToastManager {

    private static Toast sToast;

    public static void show(final Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void show(final Context context, final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
                } else {
                    sToast.setText(text);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(Toast.LENGTH_SHORT);
                }
                sToast.show();
            }
        });
    }

    public static void show(final Context context, final String text, final int time) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(context.getApplicationContext(), text, time);
                } else {
                    sToast.setText(text);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(Toast.LENGTH_SHORT);
                }
                sToast.show();
            }
        });
    }

}
