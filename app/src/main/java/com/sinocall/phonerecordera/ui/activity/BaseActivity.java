package com.sinocall.phonerecordera.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lzy.okgo.OkGo;
import com.sinocall.phonerecordera.event.account.UpLoadAppEvent;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.NetWorkUtil;
import com.sinocall.phonerecordera.widget.LoadingDialog;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private LoadingDialog mLoadingDialog;
    //是否屏蔽返回键
    private boolean isSkip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //app强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //将activity添加到栈中
    }

    @Override
    protected void onDestroy() {
        OkGo.getInstance().cancelAll();
        DialogUtils.dismissDialog();
        super.onDestroy();
    }

    private void initActivityData() {

    }

    private class ConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetWorkUtil.isOpenNet(BaseActivity.this) && NetWorkUtil.isNetworkAvailable(BaseActivity.this)) {
                initActivityData();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoading(int resId) {
        isSkip = true;
        showLoading(getString(resId));
    }

    public void showLoading(String msg) {
        dismissLoading();
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setTips(msg);
        isSkip = true;
        mLoadingDialog.show();
    }

    protected void dismissLoading() {
        isSkip = false;
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void keyBack() {
        finish();

    }

    @Override
    public void finish() {
        super.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isSkip) {
            return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
        } else {
            boolean onKey = true;
            if (keyCode == KeyEvent.KEYCODE_HOME) {
                //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                keyBack();
            } else {
                onKey = super.onKeyDown(keyCode, event);
            }
            return onKey;
        }
    }


    // 拦截/屏蔽系统Home键
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showKeyboard(View view) {
        InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 监听Home键
     */
    @Override
    public void onClick(View view) {

    }

    public void onEventMainThread(UpLoadAppEvent event) {
        if (event.code == -100) {
            Intent intent = new Intent();
            intent.putExtra("url", event.message);
            intent.putExtra("title", "应用下载");
            startActivity(intent.setClass(this, WebViewActivity.class));
        } else {

        }
    }
}


