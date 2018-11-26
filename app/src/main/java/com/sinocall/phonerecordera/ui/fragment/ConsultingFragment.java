package com.sinocall.phonerecordera.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.DirectCallLawyerResponse;
import com.sinocall.phonerecordera.api.account.H5AskQuestionResponse;
import com.sinocall.phonerecordera.api.account.LawConsultResponse;
import com.sinocall.phonerecordera.event.account.DirectCallLawyerEvent;
import com.sinocall.phonerecordera.event.account.H5AskQuestionEvent;
import com.sinocall.phonerecordera.event.account.LawConsutingEvent;
import com.sinocall.phonerecordera.event.account.PayConsultResultEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.PayResult;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.model.bean.WeiXinPay;
import com.sinocall.phonerecordera.ui.activity.LoginActivity;
import com.sinocall.phonerecordera.ui.activity.MainActivity;
import com.sinocall.phonerecordera.ui.activity.MyConsultActivity;
import com.sinocall.phonerecordera.ui.activity.RechargeActivity;
import com.sinocall.phonerecordera.ui.adapter.LawyerUltraPagerAdapter;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.DouClickUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by qingchao on 2017/11/23.
 */
@RuntimePermissions
public class ConsultingFragment extends FragmentBase implements View.OnClickListener {
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
    @BindView(R.id.lineaylayout_call_phone)
    LinearLayout lineaylayoutCallPhone;
    @BindView(R.id.edittext_crime_reports)
    EditText edittextCrimeReports;
    @BindView(R.id.edittext_phone_num_consulting)
    EditText edittextPhoneNumConsulting;
    @BindView(R.id.imageview_consulting_icon_reminding)
    ImageView imageviewConsultingIconReminding;
    @BindView(R.id.textview_consulting_38)
    TextView textviewConsulting38;
    @BindView(R.id.textview_consulting_68)
    TextView textviewConsulting68;
    @BindView(R.id.textview_consulting_98)
    TextView textviewConsulting98;
    @BindView(R.id.textview_start_consult)
    TextView textviewStartConsult;
    Unbinder unbinder;
    @BindView(R.id.framlayout_lawyer)
    FrameLayout framlayoutLawyer;
    private LawConsultResponse response;
    private UserInfo userInfo;
    private List<LawConsultResponse.DataBean.RecommendLawsBean> lawyerList = new ArrayList<>();
    private LawConsultResponse.DataBean.PricesBean pricesBean;
    private List<LawConsultResponse.DataBean.PricesBean> prices;
    private String mobile;
    private String question;
    int paytype = 0;
    private static final int SDK_PAY_FLAG = 1010;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        AccountManager.getInstance().payConsultResult(userInfo.userID, orderId);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        AccountManager.getInstance().payConsultResult(userInfo.userID, orderId);
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private String orderId;
    private boolean payResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_consulting, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initUI();
        initData();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (paytype == 105) {
            AccountManager.getInstance().payConsultResult(userInfo.userID, orderId);
        }
        if (response == null) {
            initData();
        }
    }

    private void initViewPager() {
        UltraViewPager ultraViewPager = new UltraViewPager(getActivity());
        ViewPager viewPager = ultraViewPager.getViewPager();
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        PagerAdapter ultraPagerAdapter = new LawyerUltraPagerAdapter(getActivity(), false, lawyerList);
        viewPager.setAdapter(ultraPagerAdapter);
        //设定页面循环播放
        ultraViewPager.setInfiniteLoop(true);
        //设定页面自动切换  间隔2秒
        ultraViewPager.setAutoScroll(3000);
        framlayoutLawyer.addView(ultraViewPager);
    }

    private void initData() {
        AccountManager.getInstance().getLawConsult();
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            edittextPhoneNumConsulting.setText(userInfo.mobileNo);
        }
    }

    private void initUI() {
        textviewTitle.setText("法律咨询");
        textviewTitleRight.setText("我的咨询");
        textviewTitle.setVisibility(View.VISIBLE);
        textviewTitleRight.setVisibility(View.VISIBLE);
        linearlayoutViewTitleSetting.setVisibility(View.VISIBLE);
        if (response != null) {
            prices = response.data.prices;
            textviewConsulting38.setText("最全匹配\n¥" + (int) prices.get(0).Value);
            textviewConsulting68.setText("最多人选\n¥" + new String(prices.get(1).Value + "").replace(".0", ""));
            textviewConsulting98.setText("最快应答\n¥" + new String(prices.get(2).Value + "").replace(".0", ""));
            textviewConsulting68.setSelected(true);
            pricesBean = prices.get(1);
            lawyerList = response.data.recommendLaws;
            initViewPager();
        }
        lineaylayoutCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DouClickUtils.isFastClick()) {
                    if (AccountManager.isLogined()) {
                        MainActivity activity = (MainActivity) getActivity();
                        if (activity.data != null && activity.data.remainCoin >= 500) {
                            AccountManager.getInstance().getDirectCallLawyer(userInfo.userID, userInfo.mobileNo);
                        } else {
                            DialogUtils.showCallPhoneRechage(getActivity(), ConsultingFragment.this, "录音币需500以上才可以呼叫律师");
                        }
                    } else {
                        startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imageview_consulting_icon_reminding,
            R.id.textview_consulting_38, R.id.textview_consulting_68, R.id.textview_consulting_98,
            R.id.textview_start_consult, R.id.linearlayout_view_title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_consulting_icon_reminding:
                DialogUtils.showConsutingDialog(getActivity());
                break;
            case R.id.textview_consulting_38:
                if (response != null && response.data != null) {
                    prices = response.data.prices;
                    pricesBean = prices.get(0);
                }
                textviewConsulting68.setSelected(false);
                textviewConsulting38.setSelected(true);
                textviewConsulting98.setSelected(false);
                break;
            case R.id.textview_consulting_68:
                if (response != null && response.data != null) {
                    prices = response.data.prices;
                    pricesBean = prices.get(1);
                }
                textviewConsulting68.setSelected(true);
                textviewConsulting38.setSelected(false);
                textviewConsulting98.setSelected(false);
                break;
            case R.id.textview_consulting_98:
                if (response != null && response.data != null && prices.size() > 1) {
                    prices = response.data.prices;
                    pricesBean = prices.get(2);
                }
                textviewConsulting68.setSelected(false);
                textviewConsulting38.setSelected(false);
                textviewConsulting98.setSelected(true);
                break;
            case R.id.textview_start_consult:
                if (AccountManager.isLogined()) {
                    String s = edittextPhoneNumConsulting.getText().toString();
                    if (s.length() < 3) {
                        DialogUtils.showConsutingHintDialog(getActivity(), "联系电话填写错误\n" +
                                "请重新填写");
                    } else if (edittextCrimeReports.getText().toString().length() < 10) {
                        DialogUtils.showConsutingHintDialog(getActivity(), "请输入至少10字以上的案情描述。");
                    } else {//调用支付
                        DialogUtils.showConsultPayDialog(getActivity(), this);
                    }
                } else {
                    DialogUtils.showLoginDialog(getActivity(), this);
                }
                break;
            case R.id.linearlayout_view_title_setting:
                if (AccountManager.isLogined()) {
                    startActivity(new Intent().setClass(getActivity(), MyConsultActivity.class));
                } else {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_button_sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(getActivity(), RechargeActivity.class));
                break;
            case R.id.button_dialog_wxpay:
                DialogUtils.dismissDialog();
                mobile = edittextPhoneNumConsulting.getText().toString();
                question = edittextCrimeReports.getText().toString();
                if (pricesBean != null) {
                    paytype = 105;
                    AccountManager.getInstance().askQuestion(userInfo.userID, paytype, pricesBean.Value, mobile, pricesBean.Code, question);
                }
                break;

            case R.id.button_dialog_alipay:
                DialogUtils.dismissDialog();
                mobile = edittextPhoneNumConsulting.getText().toString();
                question = edittextCrimeReports.getText().toString();
                if (pricesBean != null) {
                    paytype = 100;
                    AccountManager.getInstance().askQuestion(userInfo.userID, 100, pricesBean.Value, mobile, pricesBean.Code, question);
                }
                DialogUtils.dismissDialog();
                break;

            case R.id.button_cancle_pay:
                DialogUtils.dismissDialog();
                break;
            case R.id.button_pay_result_sure:
                DialogUtils.dismissDialog();
                if (payResult) {
                    startActivity(new Intent().setClass(getActivity(), MyConsultActivity.class));
                } else {
                    AccountManager.getInstance().askQuestion(userInfo.userID, paytype, pricesBean.Value, mobile, pricesBean.Code, question);
                }
                break;
            case R.id.textview_dialog__cancle:
                DialogUtils.dismissDialog();
                break;
            case R.id.textview_dialog__sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(getActivity(), RechargeActivity.class));
                break;
            case R.id.textview_dialog_delete_file_sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(LawConsutingEvent event) {
        if (event.code == 0) {
            response = event.response;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    /**
     * 一键呼叫、咨询律师
     *
     * @param event
     */
    public void onEventMainThread(DirectCallLawyerEvent event) {
        if (event.code == 0) {
            DirectCallLawyerResponse response = event.response;
            String mobile = response.data;
            callPhone(mobile);
        } else if (event.code == 1097) {
            DialogUtils.showNotSufficientFunds(getActivity(), this);
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }


    public void onEventMainThread(H5AskQuestionEvent event) {
        if (event.code == 0) {
            H5AskQuestionResponse response = event.response;
            int payType = response.data.payType;
            //支付宝
            if (payType == 100) {
                aLiPay(event.response.data.sendStr);
                AliPayData aliPayData = new Gson().fromJson(event.response.data.aliPayData, AliPayData.class);
                orderId = aliPayData.out_trade_no;
            } else if (payType == 105) {//微信
                String wxData = event.response.data.wxData;
                WeiXinPay weiXinPay = new Gson().fromJson(wxData, WeiXinPay.class);
                orderId = weiXinPay.orderId;
                wXPay(weiXinPay);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    private void callPhone(final String mobile) {
        new AlertDialog.Builder(getActivity())
                .setMessage("拨打电话：" + mobile)
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ConsultingFragmentPermissionsDispatcher.tellPhoneWithCheck(ConsultingFragment.this, mobile);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    public void tellPhone(String mobile) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
        startActivity(intent);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    public void showWhyCall(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage("拨打电话需要权限")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    public void deniedCallPhone() {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("您已经禁止了拨打电话权限,是否现在去开启")
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ConsultingFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void aLiPay(String aliPayData) {
        final String orderInfo = aliPayData;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void wXPay(WeiXinPay wxData) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(getActivity(), Constants.WX_APP_ID, false);
        PayReq request = new PayReq();
        request.appId = wxData.appid;
        request.partnerId = wxData.partnerid;
        request.prepayId = wxData.prepayid;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = wxData.noncestr;
        request.timeStamp = wxData.timestamp;
        request.sign = wxData.sign;
        wxapi.sendReq(request);
    }


    public void onEventMainThread(PayConsultResultEvent event) {
        if (event.code == 0) {
            payResult = true;
            DialogUtils.showPayResultDialog(getActivity(), this, true);
        } else {
            payResult = false;
            DialogUtils.showPayResultDialog(getActivity(), this, false);
        }
    }

    private class AliPayData {
        private String body;
        private String notify_url;
        private String out_trade_no;
        private String partner;
        private String seller;
        private String subject;
        private String total_fee;
    }


}
