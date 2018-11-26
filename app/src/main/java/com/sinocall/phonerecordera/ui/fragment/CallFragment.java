package com.sinocall.phonerecordera.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.dao.CallLogBean;
import com.sinocall.phonerecordera.dao.ConstactBean;
import com.sinocall.phonerecordera.event.account.CallPhoneEvent;
import com.sinocall.phonerecordera.event.account.PushEvent;
import com.sinocall.phonerecordera.event.account.UserBeforeRegInfoEvent;
import com.sinocall.phonerecordera.greendao.gen.CallLogBeanDao;
import com.sinocall.phonerecordera.greendao.gen.ConstactBeanDao;
import com.sinocall.phonerecordera.greendao.gen.DaoSession;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.activity.LoginActivity;
import com.sinocall.phonerecordera.ui.activity.MainActivity;
import com.sinocall.phonerecordera.ui.activity.MessageCenterActivity;
import com.sinocall.phonerecordera.ui.activity.RechargeActivity;
import com.sinocall.phonerecordera.ui.activity.WebViewActivity;
import com.sinocall.phonerecordera.ui.adapter.CallLogRecyclerViewAdapter;
import com.sinocall.phonerecordera.ui.adapter.StickyAdapter;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tencent.android.tpush.XGPushConfig;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 拨号页面
 */
@RuntimePermissions
public class CallFragment extends FragmentBase implements View.OnClickListener {
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
    Unbinder unbinder;
    @BindView(R.id.call_log_recyclerview)
    RecyclerView callLogRecyclerview;
    @BindView(R.id.stickylist_contact)
    ListView stickylistContact;
    @BindView(R.id.linearlayout_push)
    LinearLayout linearLayoutPush;


    private List<CallLogBean> list = new ArrayList<>();
    private CallLogRecyclerViewAdapter callLogRecyclerViewAdapter;
    private CallLogBean callLogBean;
    private DaoSession daoSession;
    private List<ConstactBean> contactList = new ArrayList<>();
    Pattern pattern = Pattern.compile("[0-9]*");
    private StickyAdapter stickyAdapter;
    private List<ConstactBean> filterDateList = new ArrayList();
    private UserInfo userInfo;
    private String keyWord;
    private String callPhone;
    private String callName;
    private int type = 0;
    private int pushType;
    private String url;
    private String title;
    private MainActivity activity;
    private String mobile;
    private int typeLog; // 1.callLog  2.
    private String infoImage = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_call, null);
        unbinder = ButterKnife.bind(this, view);
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        EventBus.getDefault().register(this);
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
        }
        if (!AccountManager.isLogined()) {
            AccountManager.getInstance().userBeforeRegInfo(XGPushConfig.getToken(PhonerecorderaApplication.getInstance()));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onRefresh() {
        super.onRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (MainActivity) this.getActivity();
        if (activity.bundle != null && activity.bundle.msgType == 0) {
            linearLayoutPush.setVisibility(View.VISIBLE);
            if (activity.bundle.type == 1) {
                this.pushType = activity.bundle.type;
                this.url = activity.bundle.url;
                this.title = activity.bundle.title;
            }
        }
        if (AccountManager.isLogined() && activity.data == null) {
            UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
        }
        CallLogBeanDao callLogBeanDao = daoSession.getCallLogBeanDao();
        list = callLogBeanDao.queryBuilder().list();
        Collections.reverse(list);
        initUI();
    }

    private void initUI() {
        textviewTitle.setText("拨号");
        textviewTitle.setVisibility(View.VISIBLE);
        textviewTitleRight.setText("说明");
        textviewTitleRight.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        callLogRecyclerview.setLayoutManager(linearLayoutManager);
        callLogRecyclerview.setItemAnimator(new DefaultItemAnimator());
        callLogRecyclerViewAdapter = new CallLogRecyclerViewAdapter(list, getActivity());
        callLogRecyclerview.setAdapter(callLogRecyclerViewAdapter);
        callLogRecyclerViewAdapter.setOnItemClickLitener(new CallLogRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AccountManager.isLogined()) {
                    if (activity == null || activity.data == null) {
                        ToastManager.show(getActivity(), "请检查你的网络连接");
                    } else {
                        typeLog = 1;
                        if (activity.data.remainCoin >= 50 || activity.data.couponNum > 0) {
                            UserInfo userInfo = AccountManager.getUserInfo();
                            callLogBean = list.get(position);
                            AccountManager.getInstance().callPhone(userInfo.userID, callLogBean.getPhoneNum(), type, typeLog);

                        } else {
                            if (activity.data.remainCoin > 0 && activity.data.remainCoin < 50) {
                                DialogUtils.showCallPhoneRechage(getActivity(), CallFragment.this, "您的账户录音币不足50，为不影响正常使用，请及时充值！");
                            } else if (activity.data.remainCoin <= 0 && activity.data.chargeFlag != 1) {
                                DialogUtils.showCallPhoneRechage(getActivity(), CallFragment.this, "您的账户录音币不足，请前往充值！首充最高可额外获赠90元录音币。");
                            } else {
                                DialogUtils.showCallPhoneRechage(getActivity(), CallFragment.this, "您的账户录音币不足，请前往充值！");
                            }
                        }
                    }
                } else {
                    startActivity(new Intent().setClass(CallFragment.this.getActivity(), LoginActivity.class));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        stickyAdapter = new StickyAdapter((ArrayList<ConstactBean>) contactList, getActivity());
        stickylistContact.setAdapter(stickyAdapter);
        stickylistContact.setDividerHeight(1);
        stickylistContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConstactBean constactBean;
                if (filterDateList != null && filterDateList.size() > 0) {
                    constactBean = filterDateList.get(position);
                } else {
                    constactBean = contactList.get(position);
                }
                String mobile = constactBean.getMobile();
                if (AccountManager.isLogined()) {
                    callName = constactBean.getName();
                    if (mobile.contains(",")) {
                        String[] split = mobile.split(",");
                        for (int i = 0; i < split.length; i++) {
                            if (split[i].contains(keyWord)) {
                                callPhone = split[i];
                                callPhone(split[i]);
                                break;
                            }
                        }
                    } else {
                        callPhone = mobile;
                        callPhone(mobile);
                    }
                } else {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.textview_title_right, R.id.imageview_small_red, R.id.linearlayout_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textview_title_right:
                if (!AccountManager.isLogined()) {
                    if (!"".equals(infoImage)) {
                        DialogUtils.showDialog(getActivity(), this, infoImage);
                    } else {
                        ToastManager.show(getActivity(), "请检查你的网络连接");
                        AccountManager.getInstance().userBeforeRegInfo(XGPushConfig.getToken(PhonerecorderaApplication.getInstance()));
                    }
                } else {
                    if (activity.data != null) {
                        DialogUtils.showDialog(getActivity(), this, activity.data.infoImage);
                    } else {
                        ToastManager.show(getActivity(), "请检查你的网络连接");
                        AccountManager.getInstance().userChargeCoinCoupon(userInfo.userID);
                    }
                }
                break;
            case R.id.imageview_small_red:
                break;
            case R.id.linearlayout_push:
                if (pushType == 0) {
                    startActivity(new Intent().setClass(getActivity(), MessageCenterActivity.class));
                } else if (pushType == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    startActivity(intent.setClass(getActivity(), WebViewActivity.class));
                }
                linearLayoutPush.setVisibility(View.GONE);
                activity.bundle = null;
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(CallPhoneEvent event) {
        if (event.code == 0) {
            if (activity != null) {
                activity.clearNum();
            }
            if (callLogBean != null && typeLog == 1 && event.typeLog == 1) {
                CallLogBeanDao callLogBeanDao = daoSession.getCallLogBeanDao();
                CallLogBean callBean = new CallLogBean();
                callBean.setId(System.currentTimeMillis());
                callBean.setName(callLogBean.getName());
                callBean.setPhoneNum(callLogBean.getPhoneNum());
                if (callBean.getName() != null && callBean.getPhoneNum() != null) {
                    callLogBeanDao.insert(callBean);
                }
            }
            if (callName != null && callPhone != null && event.typeLog == 2) {
                CallLogBeanDao callLogBeanDao = daoSession.getCallLogBeanDao();
                CallLogBean callLogBean = new CallLogBean();
                callLogBean.setId(System.currentTimeMillis());
                callLogBean.setName(callName);
                callLogBean.setPhoneNum(callPhone);
                callLogBeanDao.insert(callLogBean);
            }
            if (event.typeLog == 0) {
                CallLogBeanDao callLogBeanDao = daoSession.getCallLogBeanDao();
                CallLogBean callLogBean = new CallLogBean();
                callLogBean.setId(System.currentTimeMillis());
                getName(event.callMobileNo, callLogBean, callLogBeanDao);

            }
            CallFragmentPermissionsDispatcher.tellPhoneWithCheck(CallFragment.this, event.response.data);
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }

    }

    private void getName(final String callMobileNo, final CallLogBean callLogBean, final CallLogBeanDao callLogBeanDao) {
        ConstactBeanDao constactBeanDao = daoSession.getConstactBeanDao();
        final Query<ConstactBean> query = constactBeanDao.queryBuilder().where(ConstactBeanDao.Properties.Mobile.like(callMobileNo)).build();
        new Thread() {
            @Override
            public void run() {
                List<ConstactBean> list = query.forCurrentThread().list();
                if (list.size() > 0) {
                    ConstactBean constactBean = list.get(0);
                    String name = constactBean.getName();
                    callLogBean.setName(name);
                } else {
                    callLogBean.setName(callMobileNo);
                }
                callLogBean.setPhoneNum(callMobileNo);
                callLogBeanDao.insert(callLogBean);
            }
        }.start();

    }

    private void callPhone(final String mobile) {
        this.mobile = mobile;
        typeLog = 2;
        MainActivity activity = (MainActivity) getActivity();
        if (activity.data != null && activity.data.remainCoin > 50 || activity.data.couponNum > 0) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setMessage("拨打电话：" + mobile)
                    .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AccountManager.getInstance().callPhone(userInfo.userID, mobile, type, typeLog);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            if (activity.data != null && activity.data.remainCoin > 0 && activity.data.remainCoin < 50) {
                DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足50，为不影响正常使用，请及时充值！");
            } else if (activity.data != null && activity.data.remainCoin <= 0 && activity.data.chargeFlag != 1) {
                DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足，请前往充值！首充最高可额外获赠90元录音币。");
            } else {
                DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足，请前往充值！");
            }
        }

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
        CallFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void setContact(String string) {
        keyWord = string;
        ConstactBeanDao constactBeanDao = daoSession.getConstactBeanDao();
        contactList = constactBeanDao.queryBuilder().list();
        if (string.length() > 0) {
            callLogRecyclerview.setVisibility(View.GONE);
            stickylistContact.setVisibility(View.VISIBLE);
            filterData(string);
        } else {
            callLogRecyclerview.setVisibility(View.VISIBLE);
            stickylistContact.setVisibility(View.GONE);
        }
    }

    private void filterData(String filterStr) {
        filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = contactList;
        } else {
            filterDateList.clear();
            if (isNumeric(filterStr)) {
                for (ConstactBean friend : contactList) {
                    String phoneNum = friend.getMobile();
                    if (phoneNum.contains(filterStr)) {
                        filterDateList.add(friend);
                    }
                }
            }
        }

        //         根据a-z进行排序
        stickyAdapter.updateListView(filterDateList, filterStr);
    }

    public boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public void onEventMainThread(PushEvent event) {
        if (event.code == 0) {
            if (linearLayoutPush != null) {
                linearLayoutPush.setVisibility(View.VISIBLE);
            }
        } else if (event.code == 1) {
            if (linearLayoutPush != null) {
                linearLayoutPush.setVisibility(View.VISIBLE);
            }
            this.pushType = event.model.type;
            this.url = event.model.url;
            this.title = event.model.title;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_dialog__cancle:
                DialogUtils.dismissDialog();
                if (activity.data.remainCoin > 0 && userInfo != null) {
                    AccountManager.getInstance().callPhone(userInfo.userID, mobile, type, typeLog);
                }
                break;
            case R.id.textview_dialog__sure:
                DialogUtils.dismissDialog();
                startActivity(new Intent().setClass(getActivity(), RechargeActivity.class));
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(UserBeforeRegInfoEvent event) {
        if (event.code == 0) {
            infoImage = event.response.data.infoImage;
        }
    }
}
