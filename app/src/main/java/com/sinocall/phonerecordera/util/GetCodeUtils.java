package com.sinocall.phonerecordera.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.manager.AccountManager;

import java.lang.ref.WeakReference;


public class GetCodeUtils {
    //发送验证码读秒计时
    public static int mCountDownSecond;
    //Message what
    private static final int WHAT_CODE_COUNT_DOWN = 0;
    //发来进行验证码读秒
    private static SendCodeHandler handler;
    private static TextView textView;
    private static String tips;

    public static void getCode(String cellphone, Activity activity, int type) {
        if (!TextUtils.isEmpty(cellphone) && PhoneNuUtils.isMobile(cellphone)) {
            //初始化handler
            if (handler == null) {handler = new SendCodeHandler(activity);}
            //发送验证码
            AccountManager.getInstance().getValidationCode(cellphone,type);
        } else {
            ToastManager.show(activity, "请输入正确的手机号码!");
        }
    }

    public static void getCodeCountDown(Activity activity, TextView codeBtn, String tipsText) {
        if (handler == null) {handler = new SendCodeHandler(activity);}
        mCountDownSecond = 60;
        codeCountDown(codeBtn, tipsText);
    }

    /**
     * 不断改变View的文本
     *
     * @param codeBtn
     */
    public static void codeCountDown(TextView codeBtn, String tipsText) {
        if (mCountDownSecond > 0) {
            if (textView == null && TextUtils.isEmpty(tips)) {
                textView = codeBtn;
                tips = tipsText;
            }
            textView.setEnabled(false);
            textView.setText(mCountDownSecond + tipsText);
            mCountDownSecond--;
            handler.sendEmptyMessageDelayed(WHAT_CODE_COUNT_DOWN, 1000);
        } else {
            textView.setEnabled(true);
            textView.setText(R.string.get_validate_code);
            handler.removeMessages(WHAT_CODE_COUNT_DOWN);
            textView = null;
            tips = null;
        }
    }


    static class SendCodeHandler extends Handler {

        WeakReference<Activity> weakReference;

        public SendCodeHandler(Activity activity) {
            weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity Activity = weakReference.get();
            if (Activity == null) {
                return;
            }
            codeCountDown(textView, tips);
        }
    }

    public static void removeMessage() {
        //取消消息发送
        if (handler != null) {handler.removeMessages(WHAT_CODE_COUNT_DOWN);}
        textView = null;
        tips = null;
    }
}
