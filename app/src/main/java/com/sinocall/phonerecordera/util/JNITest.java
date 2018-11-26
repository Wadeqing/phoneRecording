package com.sinocall.phonerecordera.util;


/**
 * Created by qingchao on 2017/7/26.
 */

public class JNITest {
    static {
        System.loadLibrary("secretKeyLib");
    }

    public static native String getsecretKeyFromC(Object contextObject);

}
