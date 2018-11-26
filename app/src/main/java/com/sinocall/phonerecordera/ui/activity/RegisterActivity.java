package com.sinocall.phonerecordera.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.event.account.GetCodeEvent;
import com.sinocall.phonerecordera.event.account.RegisterEvent;
import com.sinocall.phonerecordera.event.account.ResetPasswordEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.RegistDate;
import com.sinocall.phonerecordera.util.GetCodeUtils;
import com.sinocall.phonerecordera.util.PhoneNuUtils;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 注册页面
 * Created by Administrator on 2017/8/8.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.password_old)
    TextView passwordOld;
    @BindView(R.id.password_old_text)
    EditText passwordOldText;
    @BindView(R.id.password_new_text)
    EditText passwordNewText;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.register_linear)
    LinearLayout registerLinear;

    private int type;//用来区分是忘记密码还是注册

    private String cellphone;
    private String code;
    private String password_old;
    private String password_new;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 1);
        //设置标题
        if (type == 0) {
            centerTitle.setText("注册");
            passwordOld.setText("登录密码");
            loginBtn.setText("立即注册");
        } else {
            centerTitle.setText("忘记密码");
            passwordOld.setText("新密码");
            loginBtn.setText("提交");
        }
        //添加监听
        phoneEt.addTextChangedListener(textWatcher);
        passwordOldText.addTextChangedListener(textWatcher);
        passwordNewText.addTextChangedListener(textWatcher);
        codeEt.addTextChangedListener(textWatcher);
        //EventBus注册
        registerLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(registerLinear);
                return true;
            }
        });
        passwordNewText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password_old = passwordOldText.getText().toString();
                password_new = passwordNewText.getText().toString();
                if (password_new.length() == 6 && !password_new.equals(password_old)) {
                    ToastManager.show(RegisterActivity.this, "两次密码输入不一致");
                }
            }
        });
    }

    /**
     * 文本输入框监听
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            loginBtn.setEnabled(btnCanClick());
            getCode.setEnabled(phoneEt.getText().toString().length() > 10);
        }
    };

    //用户是否全部输入了
    private boolean btnCanClick() {
        return !TextUtils.isEmpty(phoneEt.getText().toString()) &&
                !TextUtils.isEmpty(passwordOldText.getText().toString()) &&
                !TextUtils.isEmpty(passwordNewText.getText().toString()) &&
                !TextUtils.isEmpty(codeEt.getText().toString());
    }

    @OnClick({R.id.back_image, R.id.get_code, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.get_code:
                /*password_old = passwordOldText.getText().toString();
                password_new = passwordNewText.getText().toString();
                if (password_new.equals(password_old)) {
                    if (password_new.length() == 6) {*/
                GetCodeUtils.getCode(phoneEt.getText().toString(), this, type);
                codeEt.requestFocus();
                 /*   } else {
                        ToastManager.show(this, "请输入六位密码");
                    }
                } else {
                    ToastManager.show(this, "两次密码输入不一致");
                }*/
                break;
            case R.id.login_btn:
                cellphone = phoneEt.getText().toString();
                code = codeEt.getText().toString();
                password_old = passwordOldText.getText().toString();
                password_new = passwordNewText.getText().toString();

                if (checkBeforeWork(cellphone, code, password_old, password_new)) {
                    if (type == 0) {
                        //注册
                        if (password_new.equals(password_old)) {
                            showLoading("正在注册...");
                            AccountManager.getInstance().register(cellphone, password_new, code);
                        } else {
                            ToastManager.show(this, "两次密码输入不一致");
                        }
                    } else {
                        //忘记密码
                        if (password_new.equals(password_old)) {
                            showLoading("正在重置密码...");
                            AccountManager.getInstance().updatePassword(cellphone, password_new, code);
                        } else {
                            ToastManager.show(this, "两次密码输入不一致");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean checkBeforeWork(String cellphone, String code, String password_old, String password_new) {
        if (!PhoneNuUtils.isMobile(cellphone)) {
            ToastManager.show(this, "手机格式不正确");
            return false;
        }

        if (TextUtils.isEmpty(password_old) || TextUtils.isEmpty(password_new)) {
            ToastManager.show(this, "密码输入不能为空");
            return false;
        }
        if (password_old.length() < 6) {
            ToastManager.show(this, "密码长度最短6位");
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            ToastManager.show(this, "验证码不能为空");
            return false;
        }

        if (!TextUtils.equals(password_old, password_new)) {
            ToastManager.show(this, "两次密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //取消注册
        EventBus.getDefault().unregister(this);
        GetCodeUtils.removeMessage();
        super.onDestroy();
    }

    public void onEventMainThread(GetCodeEvent event) {
        if (event.code == 0) {
            GetCodeUtils.mCountDownSecond = 60;
            GetCodeUtils.codeCountDown(getCode, "秒");
        } else {
            if (!TextUtils.isEmpty(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(ResetPasswordEvent event) {
        dismissLoading();
        if (event.code == 0) {
            ToastManager.show(this, "修改成功，请重新登录");
            finish();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(RegisterEvent event) {
        dismissLoading();
        if (event.code == 0) {
            String data = event.data;
            RegistDate registDate = new Gson().fromJson(data, RegistDate.class);
            if ("true".equals(registDate.data.flag)) {
                ToastManager.show(this, "注册成功");
                finish();
            } else {
                ToastManager.show(this, event.message);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }


}
