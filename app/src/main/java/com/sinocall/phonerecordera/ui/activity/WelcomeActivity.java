package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.SPUtils;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/27.
 *
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.imageview_welcome)
    ImageView imageviewWelcome;
    private Handler mHandler = new Handler();
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XGPushClickedResult message = XGPushManager.onActivityStarted(this);
        if (message != null) {
            //拿到数据自行处理
            if (isTaskRoot()) {
                return;
            }
            //如果有面板存在则关闭当前的面板
            finish();
        } else {
            boolean isFirst = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                } else {
                    isFirst = (boolean) SPUtils.get(this, Constants.FIRST_USING, true);
                }
            } else {
                isFirst = (boolean) SPUtils.get(this, Constants.FIRST_USING, true);
            }
            if (isFirst) {
                Intent intent = new Intent();
                intent.setClass(this, LoadingViewpagerActivity.class);
                startActivity(intent);
                finish();
            } else {
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.startpage).into(imageviewWelcome);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!AccountManager.isLogined()) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            } else {
                Intent intent = new Intent();
                intent.putExtra("login", true);
                startActivity(intent.setClass(WelcomeActivity.this, MainActivity.class));
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    };


}
