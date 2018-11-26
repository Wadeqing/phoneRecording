package com.sinocall.phonerecordera.util;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sinocall.phonerecordera.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.sinocall.phonerecordera.util.UIUtils.getResources;

/**
 * Created by Administrator on 2017/8/7.
 */

public class StatusColorUtils {

    public static void setStatusColor(Window window) {
        //判断是否支持魅族和小米状态栏深度颜色
        if (!FlymeSetStatusBarLightMode(window, true) &&
                !MIUISetStatusBarLightMode(window, true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上系统，设置深色字体
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//6.0以下，5.0以上
                View decorView = window.getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.setStatusBarColor(getResources().getColor(R.color.text_color_ccgray));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
