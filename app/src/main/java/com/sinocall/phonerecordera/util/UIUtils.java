package com.sinocall.phonerecordera.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;

import com.sinocall.phonerecordera.PhonerecorderaApplication;

/**
 * Created by qingchao on 2017/4/1.
 */

public class UIUtils {
    public static Context getContext() {
        return PhonerecorderaApplication.getInstance();
    }

    //获取资源对象
    public static Resources getResources() {
        return getContext().getResources();
    }


    /******************************************/

    // xml->View
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    // 获取字符串
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }
    //获取图片
    public static Drawable getDrawable(int drawableId){
        return ContextCompat.getDrawable(getContext(),drawableId);
    }

    public static int dip2px(int dip){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (density*dip+0.5);
    }

    public static int px2dip(int px){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (px/density+0.5);
    }

    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        ColorStateList colorStateList = getResources().getColorStateList(mTabTextColorResId);
        return colorStateList;
    }

    public static String[] getStringArray(int stringArrayId){
        return getResources().getStringArray(stringArrayId);
    }
}

