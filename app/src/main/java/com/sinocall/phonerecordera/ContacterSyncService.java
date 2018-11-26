package com.sinocall.phonerecordera;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;

import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.SPUtils;

/**
 * Created by qingchao on 2017/10/31.
 *
 *
 */

public class ContacterSyncService extends Service {
    private String[] permissions = {android.Manifest.permission.READ_PHONE_STATE};
    private final static String TAG = "Any.ONE.ContacterSyncService";
    //延时处理同步联系人，若在延时期间，通话记录数据库未改变，即判断为联系人被改变了
    private final static int ELAPSE_TIME = 5000;

    private Handler mHandler = null;

    public ContentObserver mObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange) {
            // 当系统联系人数据库发生更改时触发此操作
            //去掉多余的或者重复的同步
            mHandler.removeMessages(0);

            //延时ELAPSE_TIME(10秒）发送同步信号“0”
            mHandler.sendEmptyMessageDelayed(0, ELAPSE_TIME);
        }

    };

    // 当通话记录数据库发生更改时触发此操作
    private ContentObserver mCallLogObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            // 当通话记录数据库发生更改时触发此操作
            //如果延时期间发现通话记录数据库也改变了，即判断为联系人未被改变，取消前面的同步
            mHandler.removeMessages(0);
        }

    };


    //在此处处理联系人被修改后应该执行的操作
    public void updataContact() {
        //DO SOMETHING HERE...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
            } else {
                SPUtils.put(this, Constants.CONTACT_SYNC, true);
            }
        } else {
            SPUtils.put(this, Constants.CONTACT_SYNC, true);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册监听通话记录数据库
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, mCallLogObserver);
        //注册监听联系人数据库
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, mObserver);

        //为了避免同步联系人时阻塞主线程，此处获取一个子线程的handler

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        updataContact();
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return flags;
    }


}
