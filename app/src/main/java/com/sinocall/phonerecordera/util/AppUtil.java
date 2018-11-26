/**
 *
 */
package com.sinocall.phonerecordera.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AppUtil {


    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 手机屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        // 手机屏幕的高度
        windowManager.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        int[] result = {width, height};
        return result;
    }

    public static int getWindowWidth(Context context) {
        int mScreenWidth = 0;
        if (context != null) {
            WindowManager wm = (WindowManager) (context
                    .getSystemService(Context.WINDOW_SERVICE));
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
        }
        return mScreenWidth;
    }

    public static int getWindowHeigh(Context context) {
        int mScreenHeigh = 0;
        if (context != null) {
            WindowManager wm = (WindowManager) (context
                    .getSystemService(Context.WINDOW_SERVICE));
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenHeigh = dm.heightPixels;
        }
        return mScreenHeigh;
    }


}
