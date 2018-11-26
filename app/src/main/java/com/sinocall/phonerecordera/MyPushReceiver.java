package com.sinocall.phonerecordera;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on
 *
 */
public class MyPushReceiver extends BroadcastReceiver {
    private NotificationManager NotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (null == NotificationManager) {
            NotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {// 用户注册成功
            String registrationID = JPushInterface.getRegistrationID(context);
            System.out.print(registrationID);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {// 接受到推送下来的自定义消息
            String json2 = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            if (null != json2 && "cn.serviceforce.csplus".equals(json2)) {
                Intent intent2 = new Intent("android.intent.action.ServiceForceChat");
                intent2.putExtra("msg", "hello receiver.");
                context.sendBroadcast(intent2);//-->在ServiceForceChatActivity中接收，并进行刷新
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {// 接受到推送下来的通知
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                int type = jsonObject.getInt("type");
                Context application = PhonerecorderaApplication.getInstance();
                SharedPreferences sharedPreferences = application.getSharedPreferences("type", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("type", type);
                if (type == 2) {
                    String payload = jsonObject.getString("payload");
                    JSONObject jsonObjectPayload = new JSONObject(payload);
                    String title = jsonObjectPayload.getString("title");
                    String url = jsonObjectPayload.getString("url");
                    editor.putString("title", title);
                    editor.putString("url", url);
                    Intent intent3 = new Intent("android.intent.action.MessageAlert");
                    intent3.putExtra("title", title);
                    intent3.putExtra("url", url);
                    context.sendBroadcast(intent3);//-->在ServiceForceChatActivity中接收，并进行刷新
                }
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {// 用户点击打开了通知
//            System.out.print(intent);
         /*   Context application = YouJieApplication.getInstance();
            SharedPreferences sharedPreferences = application.getSharedPreferences("type", Context.MODE_PRIVATE);
            int type = sharedPreferences.getInt("type", -1);
            Intent intent2 = new Intent();
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (type == 1) {//跳转到消息列表
                intent2.setClass(context.getApplicationContext(), MeMessageActivity.class);
                context.startActivity(intent2);
            } else if (type == 2) {//跳转到相应的url
                String title = sharedPreferences.getString("title", "");
                String url = sharedPreferences.getString("url", "");
                intent2.putExtra("title", title);
                intent2.putExtra("url", url);
                intent2.setClass(context.getApplicationContext(), WebViewActivity.class);
                context.startActivity(intent2);
            } else {
                intent2.setClass(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent2);
            }
*/
        }
    }
}
