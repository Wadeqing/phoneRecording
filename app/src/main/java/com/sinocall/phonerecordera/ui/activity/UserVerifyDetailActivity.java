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
import com.sinocall.phonerecordera.api.account.UserVerifyDetailResponse;
import com.sinocall.phonerecordera.event.account.UserVerifyDetailEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyDetailActivity extends BaseActivity {

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
    @BindView(R.id.textview_type)
    TextView textviewType;
    @BindView(R.id.textview_time)
    TextView textviewTime;
    @BindView(R.id.textview_name)
    TextView textviewName;
    @BindView(R.id.textview_status)
    TextView textviewStatus;
    @BindView(R.id.textview_num_copy)
    TextView textviewNumCopy;
    @BindView(R.id.textview_applycost)
    TextView textviewApplycost;
    @BindView(R.id.textview_copycost)
    TextView textviewCopycost;
    @BindView(R.id.textview_serverCost)
    TextView textviewServerCost;
    @BindView(R.id.textview_totalcost)
    TextView textviewTotalcost;
    @BindView(R.id.button_bottom_left)
    Button buttonBottomLeft;
    @BindView(R.id.button_bottom_right)
    Button buttonBottomRight;
    private UserVerifyDetailResponse.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_user_verify_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        long fileId = intent.getLongExtra("id", 0);
        if (AccountManager.isLogined()) {
            AccountManager.getInstance().getUserVerifyFileDetail(fileId);
        }
    }

    public void onEventMainThread(UserVerifyDetailEvent event) {
        if (event.code == 0) {
            data = event.response.data;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    private void initUI() {
        textviewTitle.setText("查看详情");
        textviewTitle.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        if (data != null) {
            textviewName.setText("录音文件：" + data.fileName);
            textviewApplycost.setText(data.applyCost + "录音币");
            textviewCopycost.setText(data.copyCost + "录音币");
            textviewNumCopy.setText(data.num + "份");
            textviewServerCost.setText(data.serverCost + "录音币");
            textviewStatus.setText("当前状态：" + data.status);
            textviewTime.setText("申请时间：" + data.createTime);
            textviewTotalcost.setText(data.totalCost + "录音币");
            textviewType.setText("证书类型：" + data.verifyType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.button_bottom_left, R.id.button_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.button_bottom_left:
                if (data != null && data.verifyType != null) {
                    Intent intent = new Intent();
                    intent.putExtra("verifyPic", data.verifyPic);
                    intent.putExtra("finish", 1);
                    startActivity(intent.setClass(this, CertificateDetailActivity.class));
                }
                break;
            case R.id.button_bottom_right:
                Intent intent1 = new Intent();
                intent1.putExtra("type", 1);
                startActivity(intent1.setClass(this, MineRecodingActivity.class));
                break;
            default:
                break;
        }
    }
}
