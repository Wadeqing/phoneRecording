package com.sinocall.phonerecordera.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.RechageCenterResposse;
import com.sinocall.phonerecordera.event.account.PayEvent;
import com.sinocall.phonerecordera.event.account.PayResultEvent;
import com.sinocall.phonerecordera.event.account.RechageCenterEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.PayResult;
import com.sinocall.phonerecordera.model.bean.WeiXinPay;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/25.
 *
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 99898;
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
    @BindView(R.id.textview_record_currency_num)
    TextView textviewRecordCurrencyNum;
    @BindView(R.id.textView_68_top)
    TextView textView68Top;
    @BindView(R.id.textView_68_bottom)
    TextView textView68Bottom;
    @BindView(R.id.linearlayout_one_button)
    LinearLayout linearlayoutOneButton;
    @BindView(R.id.textview_168_top)
    TextView textview168Top;
    @BindView(R.id.textview_168_bottom)
    TextView textview168Bottom;
    @BindView(R.id.linearlayout_two_button)
    LinearLayout linearlayoutTwoButton;
    @BindView(R.id.textview_388_top)
    TextView textview388Top;
    @BindView(R.id.textview_388_bottom)
    TextView textview388Bottom;
    @BindView(R.id.linearlayout_three_button)
    LinearLayout linearlayoutThreeButton;
    @BindView(R.id.textview_998_top)
    TextView textview998Top;
    @BindView(R.id.textview_998_bottom)
    TextView textview998Bottom;
    @BindView(R.id.linearlayout_four_button)
    LinearLayout linearlayoutFourButton;

    private RechageCenterResposse rechageCenterResposse;
    private List<RechageCenterResposse.DataBean.PayBean> pay;
    private Double payPrice;
    private Double price;
    private int payType = 0;
    private int giftCoinNum;
    private final String mMode = "00";
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
                        AccountManager.getInstance().checkPayResult(userID, orderID);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        AccountManager.getInstance().checkPayResult(userID, orderID);
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private String userID;
    private String orderID;
    private int operationActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        userID = AccountManager.getUserInfo().userID;
        EventBus.getDefault().register(this);
        operationActivityId = getIntent().getIntExtra("operationActivityId", 0);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccountManager.getInstance().getRechageCenter(userID, operationActivityId);
        if (payType == 105) {
            AccountManager.getInstance().checkPayResult(userID, orderID);
        }
    }

    private void initUI() {
        textviewTitle.setText(getResources().getString(R.string.mine_recharge));
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        if (rechageCenterResposse != null && rechageCenterResposse.data.strategy != null) {
            textviewRecordCurrencyNum.setText("当前拥有录音币：" + rechageCenterResposse.data.remainCoin + "");
            List<RechageCenterResposse.DataBean.StrategyBean> strategy = rechageCenterResposse.data.strategy;
            Collections.sort(strategy, new Comparator<RechageCenterResposse.DataBean.StrategyBean>() {
                @Override
                public int compare(RechageCenterResposse.DataBean.StrategyBean o1, RechageCenterResposse.DataBean.StrategyBean o2) {
                    return Integer.parseInt(o1.id) > Integer.parseInt(o2.id) ? 1 : 0;
                }
            });
            if (strategy.size() > 0) {
                linearlayoutOneButton.setVisibility(View.VISIBLE);
                textView68Top.setText(("¥" + (int) Double.parseDouble(strategy.get(0).payPrice) + "=" + (int) Double.parseDouble(strategy.get(0).price) + "0录音币"));
                textView68Bottom.setText("额外赠送+" + strategy.get(0).giftCoinNum + "录音币");
                if (Integer.parseInt(strategy.get(0).giftCoinNum) > 0) {
                    textView68Bottom.setVisibility(View.VISIBLE);
                } else {
                    textView68Bottom.setVisibility(View.GONE);
                }
            } else {
                linearlayoutOneButton.setVisibility(View.GONE);
            }
            if (strategy.size() > 1) {
                linearlayoutTwoButton.setVisibility(View.VISIBLE);
                textview168Top.setText(("¥" + (int) Double.parseDouble(strategy.get(1).payPrice) + "=" + (int) Double.parseDouble(strategy.get(1).price) + "0录音币"));
                textview168Bottom.setText("额外赠送+" + strategy.get(1).giftCoinNum + "录音币");
                if (Integer.parseInt(strategy.get(1).giftCoinNum) > 0) {
                    textview168Bottom.setVisibility(View.VISIBLE);
                } else {
                    textview168Bottom.setVisibility(View.GONE);
                }
            } else {
                linearlayoutTwoButton.setVisibility(View.GONE);
            }
            if (strategy.size() > 2) {
                linearlayoutThreeButton.setVisibility(View.VISIBLE);
                textview388Top.setText(("¥" + (int) Double.parseDouble(strategy.get(2).payPrice) + "=" + (int) Double.parseDouble(strategy.get(2).price) + "0录音币"));
                textview388Bottom.setText("额外赠送+" + strategy.get(2).giftCoinNum + "录音币");
                if (Integer.parseInt(strategy.get(2).giftCoinNum) > 0) {
                    textview388Bottom.setVisibility(View.VISIBLE);
                } else {
                    textview388Bottom.setVisibility(View.GONE);
                }
            } else {
                linearlayoutThreeButton.setVisibility(View.GONE);
            }
            if (strategy.size() > 3) {
                linearlayoutFourButton.setVisibility(View.VISIBLE);
                textview998Top.setText(("¥" + (int) Double.parseDouble(strategy.get(3).payPrice) + "=" + (int) Double.parseDouble(strategy.get(3).price) + "0录音币"));
                textview998Bottom.setText("额外赠送+" + strategy.get(3).giftCoinNum + "录音币");
                if (Integer.parseInt(strategy.get(3).giftCoinNum) > 0) {
                    textview998Bottom.setVisibility(View.VISIBLE);
                } else {
                    textview998Bottom.setVisibility(View.GONE);
                }
            } else {
                linearlayoutFourButton.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.linearlayout_one_button,
            R.id.linearlayout_two_button, R.id.linearlayout_three_button, R.id.linearlayout_four_button
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.linearlayout_one_button:
                setSelecte(linearlayoutOneButton, true);
                setSelecte(linearlayoutTwoButton, false);
                setSelecte(linearlayoutThreeButton, false);
                setSelecte(linearlayoutFourButton, false);
                if (rechageCenterResposse != null && rechageCenterResposse.data != null) {
                    List<RechageCenterResposse.DataBean.StrategyBean> strategy = rechageCenterResposse.data.strategy;
                    if (strategy.size() > 0) {
                        payPrice = Double.parseDouble(strategy.get(0).payPrice);
                        price = Double.parseDouble(strategy.get(0).price);
                        giftCoinNum = Integer.parseInt(strategy.get(0).giftCoinNum);
                        DialogUtils.showPayDialog(this, this, strategy.get(0).payPrice, (Double.parseDouble(strategy.get(0).price) * 10 + Double.parseDouble(strategy.get(0).giftCoinNum)) + "");
                    }
                }
                break;
            case R.id.linearlayout_two_button:
                setSelecte(linearlayoutOneButton, false);
                setSelecte(linearlayoutTwoButton, true);
                setSelecte(linearlayoutThreeButton, false);
                setSelecte(linearlayoutFourButton, false);
                if (rechageCenterResposse != null && rechageCenterResposse.data != null) {
                    List<RechageCenterResposse.DataBean.StrategyBean> strategy = rechageCenterResposse.data.strategy;
                    if (strategy.size() > 1) {
                        payPrice = Double.parseDouble(strategy.get(1).payPrice);
                        price = Double.parseDouble(strategy.get(1).price);
                        giftCoinNum = Integer.parseInt(strategy.get(1).giftCoinNum);
                        DialogUtils.showPayDialog(RechargeActivity.this, this, strategy.get(1).payPrice, (Double.parseDouble(strategy.get(1).price) * 10 + Double.parseDouble(strategy.get(1).giftCoinNum)) + "");
                    }
                }
                break;
            case R.id.linearlayout_three_button:
                setSelecte(linearlayoutOneButton, false);
                setSelecte(linearlayoutTwoButton, false);
                setSelecte(linearlayoutThreeButton, true);
                setSelecte(linearlayoutFourButton, false);
                if (rechageCenterResposse != null && rechageCenterResposse.data != null) {
                    List<RechageCenterResposse.DataBean.StrategyBean> strategy = rechageCenterResposse.data.strategy;
                    if (strategy.size() > 2) {
                        payPrice = Double.parseDouble(strategy.get(2).payPrice);
                        price = Double.parseDouble(strategy.get(2).price);
                        giftCoinNum = Integer.parseInt(strategy.get(2).giftCoinNum);
                        DialogUtils.showPayDialog(this, this, strategy.get(2).payPrice, (Double.parseDouble(strategy.get(2).price) * 10 + Double.parseDouble(strategy.get(2).giftCoinNum)) + "");
                    }
                }
                break;
            case R.id.linearlayout_four_button:
                setSelecte(linearlayoutOneButton, false);
                setSelecte(linearlayoutTwoButton, false);
                setSelecte(linearlayoutThreeButton, false);
                setSelecte(linearlayoutFourButton, true);
                if (rechageCenterResposse != null && rechageCenterResposse.data != null) {
                    List<RechageCenterResposse.DataBean.StrategyBean> strategy = rechageCenterResposse.data.strategy;
                    if (strategy.size() > 3) {
                        payPrice = Double.parseDouble(strategy.get(3).payPrice);
                        price = Double.parseDouble(strategy.get(3).price);
                        giftCoinNum = Integer.parseInt(strategy.get(3).giftCoinNum);
                        DialogUtils.showPayDialog(RechargeActivity.this, this, strategy.get(3).payPrice, (Double.parseDouble(strategy.get(3).price) * 10 + Double.parseDouble(strategy.get(3).giftCoinNum)) + "");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearlayout_alipay:
                payType = 100;
                AccountManager.getInstance().getPayInit(AccountManager.getUserInfo().userID, 100, price, payPrice, giftCoinNum, operationActivityId);
                break;
            case R.id.linearlayout_weixin_pay:
                payType = 105;
                AccountManager.getInstance().getPayInit(AccountManager.getUserInfo().userID, 105, price, payPrice, giftCoinNum, operationActivityId);
                break;
            case R.id.linearlayout_unionpay:
                payType = 102;
                AccountManager.getInstance().getPayInit(AccountManager.getUserInfo().userID, 102, price, payPrice, giftCoinNum, operationActivityId);
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(RechageCenterEvent event) {
        if (event.code == 0) {
            rechageCenterResposse = event.rechageCenterResposse;
            pay = rechageCenterResposse.data.pay;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    private void setSelecte(View view, boolean b) {
        view.setSelected(b);
        //view是否能够转换成viewGroup判断
        if (view instanceof ViewGroup) {
            //view转换成viewGroup后孩子节点的总数
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                setSelecte(viewChild, b);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(PayEvent event) {
        DialogUtils.dismissDialog();
        if (event.code == 0) {
            int payType = event.payType;
            //支付宝
            if (payType == 100) {
                AliPay aliPay = new Gson().fromJson(event.payResponse.data.aliPayData, AliPay.class);
                orderID = aliPay.out_trade_no;
                aLiPay(event.payResponse.data.sendStr);
            } else if (payType == 102) {//银联
                String tn = event.payResponse.data.tn;
                orderID = event.payResponse.data.orderId;
                doStartUnionPayPlugin(this, tn, mMode);
            } else if (payType == 105) {//微信
                String wxData = event.payResponse.data.wxData;
                WeiXinPay weiXinPay = new Gson().fromJson(wxData, WeiXinPay.class);
                wXPay(weiXinPay);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    private void aLiPay(String aliPayData) {
        final String orderInfo = aliPayData;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

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
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
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

    public void doStartUnionPayPlugin(Activity activity, String tn,
                                      String mode) {
        UPPayAssistEx.startPay(activity, null, null, tn, mode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if ("success".equalsIgnoreCase(str)) {
            AccountManager.getInstance().checkPayResult(userID, orderID);
        } else if ("fail".equalsIgnoreCase(str)) {
            msg = "支付失败！";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if ("cancel".equalsIgnoreCase(str)) {
            msg = "用户取消了支付";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }


    private class AliPay {

        /**
         * body : 购买录音币
         * notify_url : http://52046f75.nat123.net/payNotify.action
         * out_trade_no : dhly201707041354481509
         * partner : 2088911439658303
         * seller : sinocall_caiwu@163.com
         * subject : 购买录音币
         * total_fee : 168.0
         */

        public String body;
        public String notify_url;
        public String out_trade_no;
        public String partner;
        public String seller;
        public String subject;
        public String total_fee;
    }

    public void onEventMainThread(PayResultEvent event) {
        if (event.code == 0) {
            ToastManager.show(this, "支付成功");
            AccountManager.getInstance().getRechageCenter(userID, 0);
        } else if (!"".equals(event.message)) {
            ToastManager.show(this, event.message);
        }
    }
}
