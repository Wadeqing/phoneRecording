package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.event.account.LoginEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/27.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.edittext_num)
    EditText edittextNum;
    @BindView(R.id.edittext_textpwd)
    EditText edittextTextpwd;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.textview_forgot_password)
    TextView textviewForgotPassword;
    @BindView(R.id.textview_now_register)
    TextView textviewNowRegister;
    @BindView(R.id.imageview_back_login)
    ImageView imageviewBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.button_login, R.id.textview_forgot_password,
            R.id.textview_now_register, R.id.imageview_back_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                login();
                break;
            case R.id.textview_forgot_password:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.textview_now_register:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.imageview_back_login:
                finish();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //具体调用代码
                showLoading("登录中...");
                String phoneNum = edittextNum.getText().toString();
                String password = edittextTextpwd.getText().toString();
                AccountManager.getInstance().login(phoneNum, password);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);

            }
        } else {
            showLoading("登录中...");
            String phoneNum = edittextNum.getText().toString();
            String password = edittextTextpwd.getText().toString();
            AccountManager.getInstance().login(phoneNum, password);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1002) {
            if (grantResults.length >= 1) {
                int cameraResult = grantResults[0];
                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;
                if (cameraGranted) {
                    showLoading("登录中...");
                    String phoneNum = edittextNum.getText().toString();
                    String password = edittextTextpwd.getText().toString();
                    AccountManager.getInstance().login(phoneNum, password);
                } else {
                    finish();
                }
            }
        }
    }

    public void onEventMainThread(LoginEvent event) {
        dismissLoading();
        if (event.code == 0) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("login", true);
            startActivity(intent.setClass(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            if (!TextUtils.isEmpty(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
