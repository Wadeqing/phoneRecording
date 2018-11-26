package com.sinocall.phonerecordera.util;


import com.sinocall.phonerecordera.BuildConfig;
import com.tencent.mm.opensdk.utils.Log;


public class LogUtil {

    public static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "com.codeest.geeknews";

    public static void e(String tag, String o) {
        if (isDebug) {
            Log.e(tag, o);
        }
    }

    public static void e(String o) {
        LogUtil.e(TAG, o);
    }

    public static void w(String tag, String o) {
        if (isDebug) {
            Log.w(tag, o);
        }
    }

    public static void w(String o) {
        LogUtil.w(TAG, o);
    }

    public static void d(String msg) {
        if (isDebug) {
            LogUtil.d(msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            LogUtil.i(msg);
        }
    }
}
