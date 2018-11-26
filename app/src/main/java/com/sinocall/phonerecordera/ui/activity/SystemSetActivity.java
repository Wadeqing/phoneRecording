package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.event.account.LogoutEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tencent.android.tpush.XGPushManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/25.
 */

public class SystemSetActivity extends BaseActivity {
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
    @BindView(R.id.linearlayout_download_record)
    LinearLayout linearlayoutDownloadRecord;
    @BindView(R.id.linearlayout_talk_line)
    LinearLayout linearlayoutTalkLine;
    @BindView(R.id.linearlayout_revise_password)
    LinearLayout linearlayoutRevisePassword;
    @BindView(R.id.linearlayout_about_us)
    LinearLayout linearlayoutAboutUs;
    @BindView(R.id.button_logout)
    Button buttonLogout;
    private String aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_system_set);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        textviewTitle.setText(getResources().getString(R.string.set));
        imageviewTitleLeft.setVisibility(View.GONE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        aboutUs = getIntent().getStringExtra("aboutUs");
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.linearlayout_download_record, R.id.linearlayout_talk_line, R.id.linearlayout_revise_password, R.id.linearlayout_about_us, R.id.button_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.linearlayout_download_record:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(this, DownLoadRecoedActivity.class));
                }
                break;
            case R.id.linearlayout_talk_line:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(this, TalkLineActivity.class));
                }
                break;
            case R.id.linearlayout_revise_password:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(this, RevisePasswordActivity.class));
                }
                break;
            case R.id.linearlayout_about_us:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("aboutUs", aboutUs);
                    startActivity(intent.setClass(this, AboutUsActivity.class));
                }
                break;
            case R.id.button_logout:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                } else {
                    UserInfo userInfo = AccountManager.getUserInfo();
                    AccountManager.getInstance().logout(userInfo.userID);
                }
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(LogoutEvent event) {
        if (event.code == 0) {
            XGPushManager.unregisterPush(this.getApplicationContext());
            startActivity(new Intent().setClass(this, LoginActivity.class));
        } else {
            ToastManager.show(this, event.message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
