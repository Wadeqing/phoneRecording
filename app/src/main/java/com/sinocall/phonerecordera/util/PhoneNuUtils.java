package com.sinocall.phonerecordera.util;

import android.text.TextUtils;

/**
 * Created by Administrator on 15-6-18.
 */
public class PhoneNuUtils {
    public static boolean isMobile(String str) {
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(str) && str.matches(telRegex);
    }
}
