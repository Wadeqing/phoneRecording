package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.UserChargeCoinCouponResponse;
import com.sinocall.phonerecordera.event.ApplyVerifyEvent;
import com.sinocall.phonerecordera.event.account.UserChargeCoinCouponEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.IDCardUtil;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/25.
 */

public class MineRecordingEvidenceActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.edittext_name)
    EditText edittextName;
    @BindView(R.id.edittext_id_card_num)
    EditText edittextIdCardNum;
    @BindView(R.id.textview_click_sign)
    TextView textviewClickSign;
    @BindView(R.id.button_evidence)
    Button buttonEvidence;
    @BindView(R.id.imageview_signature)
    ImageView imageviewSignature;
    private boolean isHaveSignature;
    private long fileId;
    private String signPic;
    private UserInfo userInfo;
    private UserChargeCoinCouponResponse.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_mine_recording_evidence);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
        } else {
            startActivity(new Intent().setClass(this, LoginActivity.class));
        }
        initUI();
        Intent intent = getIntent();
        fileId = intent.getLongExtra("fileId", 0);
        AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
    }

    private void initUI() {
        textviewTitle.setText("申请存证");
        textviewTitle.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.textview_click_sign, R.id.button_evidence})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.textview_click_sign:
                Intent intent = new Intent(this, SignatureActivity.class);
                startActivityForResult(intent, 999);
                break;
            case R.id.button_evidence:
                if (data != null && data.remainCoin >= 900) {
                    String iDCard = edittextIdCardNum.getText().toString();
                    String name = edittextName.getText().toString();
                    if (iDCard.length() > 0 && name.length() > 0) {
                        if (!"".equals(IDCardUtil.IDCardValidate(iDCard))) {
                            ToastManager.show(this, "身份证号格式不对");
                        } else {
                            if (isHaveSignature) {
                                AccountManager.getInstance().applyVerify(fileId, name, iDCard, signPic, userInfo.userID);
                            } else {
                                ToastManager.show(this, "请签名后再试");
                            }
                        }
                    } else {
                        ToastManager.show(this, "姓名和身份证号不能为空");
                    }
                } else {
                    DialogUtils.showCallPhoneRechage(this, this, "您的账户录音币不足，请前往充值！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        signPic = data.getStringExtra("signPic");
        isHaveSignature = data.getBooleanExtra("SignatureSure", false);
        if (signPic != null && isHaveSignature) {
            imageviewSignature.setScaleType(ImageView.ScaleType.FIT_XY);
            imageviewSignature.setImageBitmap(SignatureActivity.mSignBitmap);
            imageviewSignature.setVisibility(View.VISIBLE);
            textviewClickSign.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(ApplyVerifyEvent event) {
        if (event.code == 0) {
            String verifyPic = event.response.data.verifyPic;
            Intent intent = new Intent(this, CertificateDetailActivity.class);
            intent.putExtra("verifyPic", verifyPic);
            startActivity(intent);
            finish();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            } else {
                ToastManager.show(this, "存证失败");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(UserChargeCoinCouponEvent event) {
        if (event.code == 0) {
            data = event.response.data;
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_dialog__sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(this, RechargeActivity.class));
                break;
            case R.id.textview_dialog__cancle:
                DialogUtils.dismissDialog();
                break;
            default:
                break;
        }
    }
}
