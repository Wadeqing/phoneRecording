package com.sinocall.phonerecordera;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.sinocall.phonerecordera.ui.activity.MainActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 信鸽消息接收和逻辑处理类
 */
public class TencentMessageReceiver extends XGPushBaseReceiver {
    public static final String LOG_TAG = "TPushReceiver";
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");

    private void show(Context context, String text) {
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
//        LauncherBadgeHelper.setBadgeCount(context, 1);
       /* XGNotification notific = new XGNotification();
        notific.setMsg_id(notifiShowedRlt.getMsgId());
        notific.setTitle(notifiShowedRlt.getTitle());
        notific.setContent(notifiShowedRlt.getContent());
        // notificationActionType==1为Activity，2为url，3为intent
        notific.setNotificationActionType(notifiShowedRlt
                .getNotificationActionType());
        //Activity,url,intent都可以通过getActivity()获得
        notific.setActivity(notifiShowedRlt.getActivity());
        notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime()));
        NotificationService.getInstance(context).save(notific);
        context.sendBroadcast(intent);
        show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
        Log.d("LC","+++++++++++++++++++++++++++++展示通知的回调");*/
    }

    //反注册的回调
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        show(context, text);
    }

    //设置tag的回调
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        show(context, text);
    }

    //删除tag的回调
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        show(context, text);
    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(final Context context,
                                           XGPushClickedResult message) {

        if (context == null || message == null) {
            return;
        }

        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            //发送消息   首页消息小红点消失
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
            final String content = message.getCustomContent();
            Log.e("------content", content);
            PushModel pushModel = new Gson().fromJson(content, PushModel.class);
//            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("message", pushModel);
            intent.putExtra("bundle", pushModel);
            intent.setClass(context, MainActivity.class);
            context.startActivity(intent);

        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            /*Intent intent = new Intent(context, MeMessageActivity.class);
            context.startActivity(intent);*/
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LOG_TAG, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
        show(context, text);
    }

    //注册的回调
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        show(context, text);
    }

    // 消息透传的回调
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // TODO Auto-generated method stub
        String text = "收到消息:" + message.toString();

        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        show(context, text);
    }

    public class PushModel implements Serializable {
        public long id;
        public int msgType;
        public int type;
        public String title;
        public String url;
        public int sysScreenPopId;
        public String picUrl;
        public int popType;
    }
}