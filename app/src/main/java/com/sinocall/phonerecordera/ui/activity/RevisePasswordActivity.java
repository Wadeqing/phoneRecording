package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.ResetPswResponse;
import com.sinocall.phonerecordera.event.account.ResetPswEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/25.
 */

public class RevisePasswordActivity extends BaseActivity {
    @BindView(R.id.imageview_title_left)
    ImageView imageviewTitleLeft;
    @BindView(R.id.textview_title_left)
    TextView textviewTitleLeft;
    @BindView(R.id.linearlayout_view_title_back)
    LinearLayout linearlayoutViewTitleBack;
    @BindView(R.id.textview_title)
    TextView textviewTitle;
    @BindView(R.id.imageview_title_right)
    ImageView imageviewTitleRight;
    @BindView(R.id.textview_title_right)
    TextView textviewTitleRight;
    @BindView(R.id.linearlayout_view_title_setting)
    LinearLayout linearlayoutViewTitleSetting;
    @BindView(R.id.imageview_small_red)
    ImageView imageviewSmallRed;
    @BindView(R.id.framelayout_view_title)
    FrameLayout framelayoutViewTitle;
    @BindView(R.id.edittext_old_psw)
    EditText edittextOldPsw;
    @BindView(R.id.edittext_new_psw)
    EditText edittextNewPsw;
    @BindView(R.id.edittext_new_psw_sure)
    EditText edittextNewPswSure;
    @BindView(R.id.button_revise_psw)
    Button buttonRevisePsw;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_revise_password);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initUI();
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
        }
    }

    private void initUI() {
        textviewTitle.setText("修改密码");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        edittextNewPsw.addTextChangedListener(textWatcher);
        edittextNewPswSure.addTextChangedListener(textWatcher);
        edittextOldPsw.addTextChangedListener(textWatcher);

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
            buttonRevisePsw.setEnabled(btnCanClick());
            String s1 = edittextNewPsw.getText().toString();
            String s2 = edittextNewPswSure.getText().toString();
            if (s1.length() > 5 && s2.length() > 0 && !s1.contains(s2)) {
                ToastManager.show(RevisePasswordActivity.this, "两次密码输入不一致");
            }
        }
    };

    //用户是否全部输入了
    private boolean btnCanClick() {
        return !TextUtils.isEmpty(edittextOldPsw.getText().toString()) &&
                !TextUtils.isEmpty(edittextNewPswSure.getText().toString()) &&
                !TextUtils.isEmpty(edittextNewPsw.getText().toString());
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.button_revise_psw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.button_revise_psw:
                String oldPsw = edittextOldPsw.getText().toString();
                String newPsw = edittextNewPsw.getText().toString();
                String newPswSure = edittextNewPswSure.getText().toString();
                if (oldPsw.length() > 0 && newPsw.length() > 0 && newPswSure.length() > 0) {
                    if (newPsw.length() < 6) {
                        ToastManager.show(this, "密码长度6~12位");
                        break;
                    }
                    if (newPsw.equals(newPswSure)) {
                        if (userInfo != null) {
                            AccountManager.getInstance().resetLoginPassword(userInfo.userID, oldPsw, newPswSure);
                        } else {
                            startActivity(new Intent().setClass(this, LoginActivity.class));
                        }
                    } else {
                        ToastManager.show(this, "两次输入密码不一致");
                    }
                } else {
                    ToastManager.show(this, "请输入密码");
                }
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(ResetPswEvent event) {
        if (event.code == 0) {
            ResetPswResponse resetPswResponse = event.resetPswResponse;
            if (resetPswResponse != null) {
                String flag = resetPswResponse.data.status;
                if ("true".equals(flag)) {
                    ToastManager.show(this, "修改密码成功");
                    finish();
//                    AccountManager.setUserInfo(null);
                } else if ("false".equals(flag)) {
                    ToastManager.show(this, "修改密码失败");
                }
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
