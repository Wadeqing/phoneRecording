package com.sinocall.phonerecordera.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sinocall.phonerecordera.BuildConfig;
import com.sinocall.phonerecordera.ContacterSyncService;
import com.sinocall.phonerecordera.GrayService;
import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.TencentMessageReceiver;
import com.sinocall.phonerecordera.api.account.UserChargeCoinCouponResponse;
import com.sinocall.phonerecordera.event.account.BeginAppEvent;
import com.sinocall.phonerecordera.event.account.UpLoadAppEvent;
import com.sinocall.phonerecordera.event.account.UserChargeCoinCouponEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.PopsBeanList;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.fragment.CallFragment;
import com.sinocall.phonerecordera.ui.fragment.ConsultingFragment;
import com.sinocall.phonerecordera.ui.fragment.ContactFragment;
import com.sinocall.phonerecordera.ui.fragment.MineFragment;
import com.sinocall.phonerecordera.util.AppUtil;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.SPUtils;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.main_fragment_container)
    RelativeLayout mainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mainBottomeSwitcherContainer;
    @BindView(R.id.textview_phone_num)
    TextView textviewPhoneNum;
    @BindView(R.id.imageview_close_popupwindow)
    ImageView imageviewClosePopupwindow;
    @BindView(R.id.button_one)
    Button buttonOne;
    @BindView(R.id.button_two)
    Button buttonTwo;
    @BindView(R.id.button_three)
    Button buttonThree;
    @BindView(R.id.button_four)
    Button buttonFour;
    @BindView(R.id.button_five)
    Button buttonFive;
    @BindView(R.id.button_six)
    Button buttonSix;
    @BindView(R.id.button_seven)
    Button buttonSeven;
    @BindView(R.id.button_eight)
    Button buttonEight;
    @BindView(R.id.button_nine)
    Button buttonNine;
    @BindView(R.id.button_stars)
    Button buttonStars;
    @BindView(R.id.button_zero)
    Button buttonZero;
    @BindView(R.id.button_pound)
    Button buttonPound;
    @BindView(R.id.linearlayout_popupwindow)
    LinearLayout linearlayoutPopupwindow;
    @BindView(R.id.linearlayout_hide_phone_num)
    LinearLayout linearlayoutHidePhoneNum;
    @BindView(R.id.linearlayout_show_phone_num)
    LinearLayout linearlayoutShowPhoneNum;
    @BindView(R.id.textview_main_remain_coin)
    TextView textViewMainRemainCion;
    private FragmentTransaction fragmentTransaction;
    private CallFragment callFragment;
    private ContactFragment contactFragment;
    private ConsultingFragment consultingFragment;
    private MineFragment mineFragment;
    private int childCount;
    private StringBuilder phoneNum;
    private boolean isFirst;
    private int type = 0;  //0.使用本机号码 1.隐藏本机号码
    private boolean login;
    public TencentMessageReceiver.PushModel bundle;
    public UserChargeCoinCouponResponse.DataBean data;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initClick();
        EventBus.getDefault().register(this);
        login = getIntent().getBooleanExtra("login", false);
        startService(new Intent(this, ContacterSyncService.class));
        View view = mainBottomeSwitcherContainer.getChildAt(0);
        onClick(view);
        linearlayoutPopupwindow.setVisibility(View.VISIBLE);
        textViewMainRemainCion.setVisibility(View.VISIBLE);
        textviewPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callFragment.setContact(textviewPhoneNum.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        注册信鸽
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            registerXGPush(userInfo.userID);
        }
        if (login && AccountManager.isLogined()) {
            UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().beginApp(userInfo.userID);
        }
        //进程保活
        wake();
    }

    public static String getSignature(Context context) {
        try {
            /* 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /* ***** 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /* ***** 循环遍历签名数组拼接应用签名 *******/
            return signatures[0].toCharsString();
            /* ************ 得到应用签名 **************/
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initClick() {
        childCount = mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout frameLayout = (LinearLayout) mainBottomeSwitcherContainer.getChildAt(i);
            frameLayout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        //提供点中控所在线性布局容器中的索引位置
        int indexOfChild = mainBottomeSwitcherContainer.indexOfChild(view);
        //1.切换选中FrameLayout内部孩子节点的颜色
        changeUI(indexOfChild);
        //2.切换Fragment(管理中心，首页，个人中心)
        changeFragment(indexOfChild);
        if (indexOfChild == 0) {
            if (linearlayoutPopupwindow.getVisibility() == View.VISIBLE) {
                linearlayoutPopupwindow.setVisibility(View.GONE);
                textViewMainRemainCion.setVisibility(View.GONE);
//                textviewPhoneNum.setText("");
            } else {
                linearlayoutPopupwindow.setVisibility(View.VISIBLE);
                textViewMainRemainCion.setVisibility(View.VISIBLE);
            }
        } else {
            linearlayoutPopupwindow.setVisibility(View.GONE);
            textViewMainRemainCion.setVisibility(View.GONE);
        }
        // 推送小喇叭
        int id = view.getId();
        if (id == R.id.button_sure_push) {
            if (bundle != null) {
                DialogUtils.dismissDialog();
                Intent intent = new Intent();
                intent.putExtra("url", bundle.url);
                intent.putExtra("title", bundle.title);
                startActivity(intent.setClass(this, WebViewActivity.class));
                AccountManager.getInstance().recordScreenPop(bundle.sysScreenPopId, 1, userInfo.userID);
            }
            changeFragment(0);
            changeUI(0);
        } else if (id == R.id.imageview_close_push) {
            AccountManager.getInstance().recordScreenPop(bundle.sysScreenPopId, 0, userInfo.userID);
            DialogUtils.dismissDialog();
            changeFragment(0);
            changeUI(0);
        } else if (id == R.id.textview_dialog__sure) {
            DialogUtils.dismissDialog();
            startActivity(new Intent().setClass(this, RechargeActivity.class));
            changeFragment(0);
            changeUI(0);
        } else if (id == R.id.textview_dialog__cancle) {
            DialogUtils.dismissDialog();
            if (data != null && data.remainCoin > 0) {
                UserInfo userInfo = AccountManager.getUserInfo();
                AccountManager.getInstance().callPhone(userInfo.userID, phoneNum.toString(), type, 0);
            }
            textviewPhoneNum.setText("");
            changeFragment(0);
            changeUI(0);
        }
    }

    private void changeFragment(int indexOfChild) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (callFragment != null) {
            fragmentTransaction.hide(callFragment);
        }
        if (contactFragment != null) {
            fragmentTransaction.hide(contactFragment);
        }
        if (consultingFragment != null) {
            fragmentTransaction.hide(consultingFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
        switch (indexOfChild) {
            case 0:
                if (callFragment == null) {
                    callFragment = new CallFragment();
                    fragmentTransaction.add(R.id.main_fragment_container, callFragment);
                } else {
                    fragmentTransaction.show(callFragment);
                }
                break;

            case 1:
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                    fragmentTransaction.add(R.id.main_fragment_container, contactFragment);
                } else {
                    fragmentTransaction.show(contactFragment);
                }
                break;
            case 2:
                if (consultingFragment == null) {
                    consultingFragment = new ConsultingFragment();
                    fragmentTransaction.add(R.id.main_fragment_container, consultingFragment);
                } else {
                    fragmentTransaction.show(consultingFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.main_fragment_container, mineFragment);
                } else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void changeUI(int indexOfChild) {
        for (int i = 0; i < mainBottomeSwitcherContainer.getChildCount(); i++) {
            View view = mainBottomeSwitcherContainer.getChildAt(i);
            if (i == indexOfChild) {
                //找到了选中的view对象,view孩子节点都需要变色,将view中的孩子节点都设置为不可用
//                setEnable(view, false);
                setSelecte(view, true);
            } else {
                //没找到选中的view对象,view孩子节点都不需要变色,将view中的孩子节点都设置为可用
//                setEnable(view, true);
                setSelecte(view, false);
            }
        }
    }

    private void setEnable(View view, boolean b) {
        view.setEnabled(b);
        //view是否能够转换成viewGroup判断
        if (view instanceof ViewGroup) {
            //view转换成viewGroup后孩子节点的总数
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                setEnable(viewChild, b);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        bundle = (TencentMessageReceiver.PushModel) intent.getSerializableExtra("bundle");
        if (bundle != null) {
            // 小喇叭
            if (bundle.msgType == 0) {
                changeFragment(0);
            } else if (bundle.msgType == 1) { //弹框
                DialogUtils.showPushDialog(this, bundle, this);
            }
        }

        super.onNewIntent(intent);
    }

    @OnClick({R.id.imageview_close_popupwindow, R.id.button_one, R.id.button_two,
            R.id.button_three, R.id.button_four, R.id.button_five, R.id.button_six,
            R.id.button_seven, R.id.button_eight, R.id.button_nine, R.id.button_stars,
            R.id.linearlayout_show_phone_num, R.id.linearlayout_hide_phone_num,
            R.id.button_zero, R.id.button_pound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_close_popupwindow:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                if (phoneNum.length() >= 1) {
                    String delete = phoneNum.delete(phoneNum.length() - 1, phoneNum.length()).toString();
                    textviewPhoneNum.setText(delete);
                } else if (phoneNum.length() == 0) {
                    textviewPhoneNum.setHint(this.getString(R.string.popupwindow_hint));
                    linearlayoutPopupwindow.setVisibility(View.GONE);
                    textViewMainRemainCion.setVisibility(View.GONE);
                }
                break;
            case R.id.button_one:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("1");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_two:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("2");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_three:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("3");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_four:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("4");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_five:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("5");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_six:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("6");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_seven:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("7");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_eight:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("8");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_nine:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("9");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_stars:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("*");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_zero:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("0");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.button_pound:
                phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                phoneNum.append("#");
                textviewPhoneNum.setText(phoneNum);
                break;
            case R.id.linearlayout_show_phone_num:
                if (AccountManager.isLogined()) {
                    isFirst = (boolean) SPUtils.get(this, Constants.FIRST_SHOW_DIALOG_MAIN, true);
                    if (isFirst) {
                        DialogUtils.showCallPhoneHintDialog(this, this, 0);
                        SPUtils.put(this, Constants.FIRST_SHOW_DIALOG_MAIN, false);
                    } else {
                        if (data != null && (data.remainCoin >= 50 || data.couponNum > 0)) {
                            type = 0;
                            phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                            if (phoneNum.length() == 0) {
                                ToastManager.show(this, "请输入号码");
                            } else if (phoneNum.length() < 3 && phoneNum.length() > 0) {
                                ToastManager.show(this, "该号码无法拨打");
                            } else if (phoneNum.length() < 10 && phoneNum.length() > 2) {
                                showDialog();
                            } else if (data != null && data.remainCoin < 50 && data.couponNum < 0) {
                                checkRemainCion();
                            } else {
                                UserInfo userInfo = AccountManager.getUserInfo();
                                AccountManager.getInstance().callPhone(userInfo.userID, phoneNum.toString(), type, 0);
                                textviewPhoneNum.setText("");
                            }
                        } else if (data != null) {
                            checkRemainCion();
                        } else {
                            ToastManager.show(this, "请检查你的网络连接");
                            if (AccountManager.isLogined() && data == null) {
                                UserInfo userInfo = AccountManager.getUserInfo();
                                AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
                            }
                        }
                    }
                } else {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                }
                break;
            case R.id.linearlayout_hide_phone_num:
                if (AccountManager.isLogined()) {
                    isFirst = (boolean) SPUtils.get(this, Constants.FIRST_HIDE_DIALOG_MAIN, true);
                    if (isFirst) {
                        DialogUtils.showCallPhoneHintDialog(this, this, 1);
                        SPUtils.put(this, Constants.FIRST_HIDE_DIALOG_MAIN, false);
                    } else if (data != null && (data.remainCoin >= 50 || data.couponNum > 0)) {
                        type = 1;
                        phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                        if (phoneNum.length() == 0) {
                            ToastManager.show(this, "请输入号码");
                        } else if (phoneNum.length() < 3 && phoneNum.length() > 0) {
                            ToastManager.show(this, "该号码无法拨打");
                        } else if (phoneNum.length() < 10 && phoneNum.length() > 2) {
                            showDialog();
                        } else {
                            UserInfo userInfo = AccountManager.getUserInfo();
                            AccountManager.getInstance().callPhone(userInfo.userID, phoneNum.toString(), type, 0);
                            textviewPhoneNum.setText("");
                        }
                    } else if (data != null) {
                        checkRemainCion();
                    } else {
                        ToastManager.show(this, "请检查你的网络连接");
                        if (AccountManager.isLogined() && data == null) {
                            UserInfo userInfo = AccountManager.getUserInfo();
                            AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
                        }
                    }
                } else {
                    startActivity(new Intent().setClass(this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void checkRemainCion() {
        if (data != null && data.remainCoin > 0 && data.remainCoin < 50) {
            DialogUtils.showCallPhoneRechage(this, this, "您的账户录音币不足50，为不影响正常使用，请及时充值！");
        } else if (data != null && data.remainCoin <= 0 && data.chargeFlag != 1) {
            DialogUtils.showCallPhoneRechage(this, this, "您的账户录音币不足，请前往充值！首充最高可额外获赠90元录音币。");
        } else {
            DialogUtils.showCallPhoneRechage(this, this, "您的账户录音币不足，请前往充值！");
        }
    }

    private void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_zone, null);
        dialog.setView(view);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        dialog.getWindow().setAttributes(lp);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        final EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_zone);
        TextView textCancle = (TextView) view.findViewById(R.id.textview_dialog_cancle);
        TextView textSure = (TextView) view.findViewById(R.id.textview_dialog_sure);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置可获得焦点
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                //请求获得焦点
                editText.requestFocus();
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) editText
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        });
        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s.length() == 0) {
                    ToastManager.show(MainActivity.this, "区号不能为空");
                } else if (s.length() < 3 || s.length() > 5) {
                    ToastManager.show(MainActivity.this, "输入区号不正确");
                } else if (s.length() > 2 && s.length() < 6) {
                    phoneNum = new StringBuilder(textviewPhoneNum.getText().toString());
                    UserInfo userInfo = AccountManager.getUserInfo();
                    AccountManager.getInstance().callPhone(userInfo.userID, s + phoneNum.toString(), type, 0);
                    clearNum();
                }
                dialog.dismiss();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void registerXGPush(final String userID) {
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this.getApplicationContext(), BuildConfig.DEBUG);
        //注册
        XGPushManager.registerPush(this.getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        //打印log来确定是否注册成功，在代码中可通过 XGPushConfig.getToken(Context) 来获取设备token
                        String XGtoken = (String) data;
                        AccountManager.getInstance().bindPushToken(XGPushConfig.getToken(PhonerecorderaApplication.getInstance()), userID);
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        String XGtoken = (String) data;
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        /*if (AccountManager.isLogined() && login) {
            UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
        }*/
        if (AccountManager.isLogined()) {
            UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
        }
    }

    public void onEventMainThread(UserChargeCoinCouponEvent event) {
        if (event.code == 0) {
            data = event.response.data;
            textViewMainRemainCion.setText("当前余额：" + data.remainCoin);
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void clearNum() {
        textviewPhoneNum.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onEventMainThread(BeginAppEvent event) {
        if (event.code == 0 && event.response.data != null && event.response.data.pops.length() > 5) {
            String pops = event.response.data.pops;
            pops = "{\"pops\":" + pops + "}";
            PopsBeanList popsBean = new Gson().fromJson(pops, PopsBeanList.class);
            for (int i = 0; i < popsBean.pops.size(); i++) {
                DialogUtils.showBeginDialog(this, popsBean.pops.get(i), this, userInfo.userID);
            }
        }
    }

    /*————————————————保活————————————————*/
    private final static String TAG = MainActivity.class.getSimpleName();
    /**
     * 黑色唤醒广播的action
     */
    private final static String BLACK_WAKE_ACTION = "com.sinocall.phonerecordera";

    private void wake() {

        Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
        startService(grayIntent);

        Intent blackIntent = new Intent();
        blackIntent.setAction(BLACK_WAKE_ACTION);
        sendBroadcast(blackIntent);
    }

}
