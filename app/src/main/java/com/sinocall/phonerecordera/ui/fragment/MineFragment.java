package com.sinocall.phonerecordera.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.PersonalInfoResponse;
import com.sinocall.phonerecordera.event.account.PersonalInfoEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.ui.activity.AccountDetailActivity;
import com.sinocall.phonerecordera.ui.activity.ApplyForEvidenceActivity;
import com.sinocall.phonerecordera.ui.activity.LoginActivity;
import com.sinocall.phonerecordera.ui.activity.MessageCenterActivity;
import com.sinocall.phonerecordera.ui.activity.MineRecodingActivity;
import com.sinocall.phonerecordera.ui.activity.PreferentialActivity;
import com.sinocall.phonerecordera.ui.activity.RechargeActivity;
import com.sinocall.phonerecordera.ui.activity.SystemSetActivity;
import com.sinocall.phonerecordera.ui.activity.WebViewActivity;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.serviceforce.cssdk.customer.OnSetUserInfoCallBack;
import cn.serviceforce.cssdk.customer.OnShowPageCallBack;
import cn.serviceforce.cssdk.customer.PageType;
import cn.serviceforce.cssdk.customer.ServiceForce;
import cn.serviceforce.cssdk.customer.ServiceForcePage;
import cn.serviceforce.cssdk.model.UserInfo;
import de.greenrobot.event.EventBus;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by qingchao on 2017/11/23.
 *
 */

@RuntimePermissions
public class MineFragment extends FragmentBase implements View.OnClickListener {
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
    @BindView(R.id.button_mine_account_details)
    Button buttonMineAccountDetails;
    @BindView(R.id.button_recharge)
    Button buttonRecharge;
    @BindView(R.id.button_mine_recording)
    Button buttonMineRecording;
    @BindView(R.id.linearlayout_mine_law_service)
    LinearLayout linearlayoutMineLawService;
    @BindView(R.id.linearlayout_mine_apply_for_dhyg)
    LinearLayout linearlayoutMineApplyForDhyg;
    @BindView(R.id.linearlayout_mine_preferential_activity)
    LinearLayout linearlayoutMinePreferentialActivity;
    @BindView(R.id.linearlayout_mine_message_center)
    LinearLayout linearlayoutMineMessageCenter;
    @BindView(R.id.linearlayout_mine_frequently_asked_questions)
    LinearLayout linearlayoutMineFrequentlyAskedQuestions;
    @BindView(R.id.linearlayout_mine_customer_service)
    LinearLayout linearlayoutMineCustomerService;
    @BindView(R.id.linearlayout_mine_system_set)
    LinearLayout linearlayoutMineSystemSet;
    Unbinder unbinder;
    @BindView(R.id.textview_mine_phone_num)
    TextView textviewMinePhoneNum;
    @BindView(R.id.textview_record_num)
    TextView textviewRecordNum;
    @BindView(R.id.textview_message_num)
    TextView textviewMessageNum;
    @BindView(R.id.textview_tellphone)
    TextView textviewTellphone;
    @BindView(R.id.imageview_icon)
    ImageView imageviewIcon;
    private PersonalInfoResponse personalInfoResponse;
    private ServiceForce serviceForce;
    private static final String[] PEEMISSION1 = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (AccountManager.isLogined()) {
            com.sinocall.phonerecordera.model.bean.UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getPersonInfo(userInfo.userID);
        }

    }

    private void initUI() {
        textviewTitle.setText(getResources().getString(R.string.mine));
        textviewTitle.setVisibility(View.VISIBLE);
        if (personalInfoResponse != null && personalInfoResponse.data != null) {
            textviewMinePhoneNum.setText(personalInfoResponse.data.mobileNo.substring(0, 3) + "****" + personalInfoResponse.data.mobileNo.substring(7));
            textviewMessageNum.setText(personalInfoResponse.data.unreadMsgNum);
            textviewRecordNum.setText(personalInfoResponse.data.leftMoney);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void initServiceForce() {
        serviceForce = ServiceForce.getInstance(PhonerecorderaApplication.getInstance());
        UserInfo userInfo = new UserInfo();
        if (AccountManager.isLogined()) {
            com.sinocall.phonerecordera.model.bean.UserInfo userInfo1 = AccountManager.getUserInfo();
            if (userInfo1 != null && serviceForce != null) {
                userInfo.setId(userInfo1.userID);
                userInfo.setCellphone(userInfo1.mobileNo);
                serviceForce.bindUser(userInfo, new OnSetUserInfoCallBack() {
                    @Override
                    public void CallBackOnResult(int code) {

                    }
                });
                ServiceForcePage serviceForceContactPage = new
                        ServiceForcePage(PageType.CHAT);
                serviceForce.showPage(serviceForceContactPage, new OnShowPageCallBack() {
                    @Override
                    public void CallBackOnResult(int code) {

                    }
                });
            }
        }

    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void initServiceForceAskedQuestions() {
        serviceForce = ServiceForce.getInstance(PhonerecorderaApplication.getInstance());
        ServiceForcePage serviceForceFAQPage = new ServiceForcePage(PageType.FAQ);
        serviceForce.showPage(serviceForceFAQPage, new OnShowPageCallBack() {
            @Override
            public void CallBackOnResult(int code) {

            }
        });
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showServiceForceReadPhone(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage("使用常见问题功能获取权限")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void showDeniedForCamera() {
        ToastManager.show(getActivity(), "您已拒绝读取状态的权限");
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void showNeverAskForCamera() {
        ToastManager.show(getActivity(), "您已拒绝读取状态的权限");
    }

    @OnClick({R.id.button_mine_account_details, R.id.button_recharge, R.id.textview_tellphone,
            R.id.button_mine_recording, R.id.linearlayout_mine_law_service, R.id.imageview_icon,
            R.id.linearlayout_mine_apply_for_dhyg, R.id.linearlayout_mine_preferential_activity,
            R.id.linearlayout_mine_message_center, R.id.linearlayout_mine_frequently_asked_questions,
            R.id.linearlayout_mine_customer_service, R.id.linearlayout_mine_system_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_mine_account_details:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(getActivity(), AccountDetailActivity.class));
                }
                break;
            case R.id.imageview_icon:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.button_recharge:
                if (!AccountManager.isLogined()) {
//                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                    DialogUtils.showLoginDialog(getActivity(), this);
                } else {
                    startActivity(new Intent().setClass(getActivity(), RechargeActivity.class));
                }
                break;
            case R.id.button_mine_recording:
                if (!AccountManager.isLogined()) {
//                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                    DialogUtils.showLoginDialog(getActivity(), this);
                } else {
                    startActivity(new Intent().setClass(getActivity(), MineRecodingActivity.class));
                }
                break;
            case R.id.linearlayout_mine_law_service:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    if (personalInfoResponse != null && personalInfoResponse.data != null && personalInfoResponse.data.lawTools != null) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", personalInfoResponse.data.lawTools);
                        intent.putExtra("title", "法律工具");
                        startActivity(intent);
                    } else {
                        ToastManager.show(getActivity(), "检查网络设置");
                    }
                }
                break;
            case R.id.linearlayout_mine_apply_for_dhyg:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(getActivity(), ApplyForEvidenceActivity.class));
                }
                break;
            case R.id.linearlayout_mine_preferential_activity:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(getActivity(), PreferentialActivity.class));
                }
                break;
            case R.id.linearlayout_mine_message_center:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent().setClass(getActivity(), MessageCenterActivity.class));
                }
                break;
            case R.id.linearlayout_mine_frequently_asked_questions:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    MineFragmentPermissionsDispatcher.initServiceForceAskedQuestionsWithCheck(this);
                }
                break;
            case R.id.linearlayout_mine_customer_service:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    MineFragmentPermissionsDispatcher.initServiceForceWithCheck(this);
                }
                break;
            case R.id.linearlayout_mine_system_set:
                if (!AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                } else {
                    Intent intent = new Intent();
                    if (personalInfoResponse != null) {
                        intent.putExtra("aboutUs", personalInfoResponse.data.aboutUs);
                    }
                    startActivity(intent.setClass(getActivity(), SystemSetActivity.class));
                }
                break;
            case R.id.textview_tellphone:
                new AlertDialog.Builder(getActivity())
                        .setMessage("拨打客服电话：4008705000")
                        .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tellPhone();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    private void tellPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PEEMISSION1, 1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data;
                data = Uri.parse("tel:4008705000");
                intent.setData(data);
                startActivity(intent);
            }
        }
    }

    public void onEventMainThread(PersonalInfoEvent event) {
        if (event.code == 0) {
            personalInfoResponse = event.personalInfoResponse;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MineFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_dialog_delete_file_sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                break;
            default:
                break;
        }
    }

}
