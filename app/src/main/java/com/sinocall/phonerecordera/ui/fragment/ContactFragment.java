package com.sinocall.phonerecordera.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.sinocall.phonerecordera.event.account.UpdataContactEvent;
import com.sinocall.phonerecordera.greendao.gen.CallLogBeanDao;
import com.sinocall.phonerecordera.greendao.gen.ConstactBeanDao;
import com.sinocall.phonerecordera.greendao.gen.DaoSession;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.activity.LoginActivity;
import com.sinocall.phonerecordera.ui.activity.MainActivity;
import com.sinocall.phonerecordera.ui.activity.RechargeActivity;
import com.sinocall.phonerecordera.ui.adapter.StickyAdapter;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.SPUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.sinocall.phonerecordera.widget.QuickIndexBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 */
@RuntimePermissions
public class ContactFragment extends FragmentBase implements View.OnClickListener {
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
    @BindView(R.id.edittext_contact)
    EditText edittextContact;
    @BindView(R.id.textview_hide_phone)
    TextView textviewHidePhone;
    @BindView(R.id.textview_use_phone)
    TextView textviewUsePhone;
    @BindView(R.id.stickylist_contact)
    ListView stickylistContact;
    @BindView(R.id.quickIndexBar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.textview_word_center)
    TextView textviewWordCenter;
    @BindView(R.id.button_open_contact)
    Button buttonOpenContact;
    @BindView(R.id.linearlayout_contact_default)
    LinearLayout linearlayoutContactDefault;
    @BindView(R.id.imageview_clear_edittext)
    ImageView imageviewClearEdittext;
    Unbinder unbinder;
    private String mobile;
    private List<ConstactBean> list = new ArrayList<>();
    private Cursor contactsCursor;
    private ContentResolver resolver;
    private StickyAdapter stickyAdapter;
    private ConstactBeanDao constactBeanDao;
    Pattern pattern = Pattern.compile("[0-9]*");
    private String[] split;
    private UserInfo userInfo;
    private DaoSession daoSession;
    private String callName;
    private String callPhone;
    private List<ConstactBean> filterDateList;
    private int type = 0;//0 普通拨号   1：隐私拨号
    private boolean isFirst;
    private MainActivity activity;
    private ConstactBean constactBean;
    private String[] permissions = {android.Manifest.permission.READ_PHONE_STATE};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_contact, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        constactBeanDao = daoSession.getConstactBeanDao();
        list = constactBeanDao.queryBuilder().build().list();
        textviewTitle.setText("联系人");
        if (list.size() > 0) {
            initUI();
        }
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
        }
        // 获取保存的类型  type= 1 隐藏手机号码  type = 0 使用手机号码
        type = (int) SPUtils.get(getActivity(), Constants.CALL_PHONE_HIDE_OR_SHOW, 0);
        if (type == 0) {
            textviewUsePhone.setSelected(true);
            textviewHidePhone.setSelected(false);
        } else {
            textviewUsePhone.setSelected(false);
            textviewHidePhone.setSelected(true);
        }
        checkContactSync();
        return view;
    }

    private void checkContactSync() {
        boolean isSync = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
            } else {
                isSync = (boolean) SPUtils.get(getActivity(), Constants.CONTACT_SYNC, false);
            }
        } else {
            isSync = (boolean) SPUtils.get(getActivity(), Constants.CONTACT_SYNC, false);
        }
        if (isSync) {
            constactBeanDao.deleteAll();
            list.clear();
            ContactFragmentPermissionsDispatcher.getContactInfoWithCheck(this);
        }
    }

    private void initUI() {
        //填充listview
        stickylistContact.setVisibility(View.VISIBLE);
        linearlayoutContactDefault.setVisibility(View.GONE);
        stickyAdapter = new StickyAdapter((ArrayList<ConstactBean>) list, getActivity());
        stickylistContact.setAdapter(stickyAdapter);
        stickylistContact.setDividerHeight(1);
        stickylistContact.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list != null && list.size() > 0) {
                    ConstactBean constactBean = list.get(firstVisibleItem);
                    if (constactBean != null && constactBean.getPinyin() != null && constactBean.getPinyin().length() > 0) {
                        String c = constactBean.getPinyin().charAt(0) + "";
                    }
                }
            }
        });
        //   监听搜索框的输入
        edittextContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 过滤出想要的数据
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String word) {
                //listview需要从集合中查找首字母和word一样的条目，然后置顶
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String pinyin = list.get(i).getSortKey();
                        if (pinyin != null && !pinyin.isEmpty()) {
                            String letter = list.get(i).getSortKey().charAt(0) + "";
                            if (letter.equals(word + "")) {
                                //说明找到了，那么就置顶
                                stickylistContact.setSelection(i);
                                break;//找到后立即中断
                            }
                        }
                    }
                }
                //显示当前的字母
                showCurrentWord(word);
            }
        });

        stickylistContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (filterDateList != null && filterDateList.size() > 0) {
                    constactBean = filterDateList.get(position);
                } else {
                    constactBean = list.get(position);
                }
                String mobile = constactBean.getMobile();
                if (AccountManager.isLogined()) {
                    activity = (MainActivity) getActivity();
                    if (activity.data != null && (activity.data.remainCoin >= 50 || activity.data.couponNum > 0)) {
                        if (mobile.contains(",")) {
                            callName = constactBean.getName();
                            split = mobile.split(",");
                            DialogUtils.showContactDialog(ContactFragment.this.getActivity(), ContactFragment.this, mobile, constactBean.getName());
                        } else {
                            callName = constactBean.getName();
                            callPhone = mobile;
                            callPhone(mobile);
                        }
                    } else {
                        lackRemainCion();
                    }
                } else {
                    startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
                }
            }
        });
    }

    private void lackRemainCion() {
        if (activity.data != null && activity.data.remainCoin > 0 && activity.data.remainCoin < 50) {
            DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足50，为不影响正常使用，请及时充值！");
        } else if (activity.data != null && activity.data.remainCoin <= 0 && activity.data.chargeFlag != 1) {
            DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足，请前往充值！首充最高可额外获赠90元录音币。");
        } else {
            DialogUtils.showCallPhoneRechage(getActivity(), this, "您的账户录音币不足，请前往充值！");
        }
    }

    private void callPhone(final String mobile) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("" + mobile)
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AccountManager.getInstance().callPhone(userInfo.userID, mobile, type, 3);
//                        ContactFragmentPermissionsDispatcher.tellPhoneWithCheck(ContactFragment.this, mobile);
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

    private Handler handler = new Handler();

    private void showCurrentWord(String word) {
        //移除之前的任务
        handler.removeCallbacksAndMessages(null);

        textviewWordCenter.setText(word);
        textviewWordCenter.setVisibility(View.VISIBLE);
        //延时消失
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textviewWordCenter.setVisibility(View.GONE);
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.textview_hide_phone, R.id.textview_use_phone, R.id.imageview_clear_edittext,
            R.id.quickIndexBar, R.id.button_open_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textview_hide_phone:
                type = 1;
                textviewHidePhone.setSelected(true);
                textviewUsePhone.setSelected(false);
                SPUtils.put(getActivity(), Constants.CALL_PHONE_HIDE_OR_SHOW, type);
                isFirst = (boolean) SPUtils.get(getActivity(), Constants.FIRST_USING_SHOW_DIALOG_HIDE, true);
                if (isFirst) {
                    DialogUtils.showCallPhoneHintDialog(getActivity(), this, 1);
                    SPUtils.put(getActivity(), Constants.FIRST_USING_SHOW_DIALOG_HIDE, false);
                }
                break;
            case R.id.textview_use_phone:
                type = 0;
                textviewHidePhone.setSelected(false);
                textviewUsePhone.setSelected(true);
                SPUtils.put(getActivity(), Constants.CALL_PHONE_HIDE_OR_SHOW, type);
                isFirst = (boolean) SPUtils.get(getActivity(), Constants.FIRST_USING_SHOW_DIALOG_SHOW, true);
                if (isFirst) {
                    DialogUtils.showCallPhoneHintDialog(getActivity(), this, 0);
                    SPUtils.put(getActivity(), Constants.FIRST_USING_SHOW_DIALOG_SHOW, false);
                }
                break;
            case R.id.quickIndexBar:

                break;
            case R.id.button_open_contact:
                linearlayoutContactDefault.setVisibility(View.GONE);
                ContactFragmentPermissionsDispatcher.getContactInfoWithCheck(this);
                break;
            case R.id.imageview_clear_edittext:
                edittextContact.setText("");
                break;
            default:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_CONTACTS})
    public void getContactInfo() {

        new Thread() {
            private ConstactBean contactsData;

            @Override
            public void run() {
                super.run();
                resolver = getActivity().getContentResolver();
                int num = 0;
                try {
                    //搜索字段
                    String[] projection = new String[]{
                            Phone.CONTACT_ID,
                            Phone.NUMBER,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.SORT_KEY_PRIMARY,
                    };
                    String sortKey = "sort_key";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        projection[3] = "phonebook_label";
                        sortKey = "phonebook_label";
                    }
                    contactsCursor = resolver.query(Phone.CONTENT_URI,
                            projection, null, null, sortKey);
                    if (contactsCursor != null) {
                        //key: contactId,value: 该contactId在联系人集合data的index
                        Map<Integer, Integer> contactIdMap = new HashMap<>();
                        while (contactsCursor.moveToNext()) {
                            //获取联系人的ID
                            int contactId = contactsCursor.getInt(0);
                            //获取联系人的姓名
                            String name = contactsCursor.getString(2);
                            //获取联系人的号码
                            String phoneNumber = contactsCursor.getString(1);
                            String sortKeyPrimary = contactsCursor.getString(3);
                            String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+86", "").replace("+", "").replace("\\", "");

                            //如果联系人Map已经包含该contactId
                            if (contactIdMap.containsKey(contactId)) {
                                //得到该contactId在data的index
                                Integer index = contactIdMap.get(contactId);
                                //重新设置号码数组
                                contactsData = list.get(index);
                                mobile = contactsData.getMobile();
                                contactsData.setMobile(mobile + "," + replace);
                            } else {
                                //如果联系人Map不包含该contactId
                                ConstactBean constactBean = new ConstactBean();
                                constactBean.setId(num);
                                constactBean.setName(name);
                                constactBean.setSortKey(sortKeyPrimary);
                                constactBean.setMobile(replace);
                                list.add(constactBean);
                                contactIdMap.put(contactId, list.size() - 1);
                                num++;
                            }
                        }
                    }

                } catch (SQLiteException ex) {
                    contactsCursor.close();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                    }
                });
                for (int i = 0; i < list.size(); i++) {
                    constactBeanDao.insert(list.get(i));
                }
            }
        }.start();
    }

    @OnShowRationale({Manifest.permission.READ_CONTACTS})
        //提示用户为什么需要此权限
    void showWhy(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setMessage("读取手机联系人，方便查看以及做更多操作")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CONTACTS})
    void denied() {
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
                .setMessage("您已经禁止了读取联系人权限,是否现在去开启")
                .show();
        linearlayoutContactDefault.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ContactFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void filterData(String filterStr) {
        filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = list;
        } else {
            filterDateList.clear();
            if (isNumeric(filterStr)) {
                for (ConstactBean friend : list) {
                    String phoneNum = friend.getMobile();
                    if (phoneNum.contains(filterStr)) {
                        filterDateList.add(friend);
                    }
                }
            } else {
                for (ConstactBean friend : list) {
                    String name = friend.getName();
                    String jianpin = friend.getJianpin();
                    String pinyin = friend.getPinyin();
                    if ((pinyin != null && pinyin.startsWith(filterStr.toUpperCase())) || (name != null && name.contains(filterStr)) || (jianpin != null && jianpin.contains(filterStr.toUpperCase()))) {
                        filterDateList.add(friend);
                    }
                }
            }
        }
//         根据a-z进行排序
        stickyAdapter.updateListView(filterDateList, edittextContact.getText().toString());
    }

    public boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_dialog_contact_num1:
                DialogUtils.dismissDialog();
                if (split.length > 0) {
                    callPhone = split[0];
                    AccountManager.getInstance().callPhone(userInfo.userID, split[0], type, 3);
                }
                break;
            case R.id.textview_dialog_contact_num2:
                DialogUtils.dismissDialog();
                if (split.length > 1) {
                    callPhone = split[1];
                    AccountManager.getInstance().callPhone(userInfo.userID, split[1], type, 3);
                }
                break;
            case R.id.textview_dialog_contact_num3:
                DialogUtils.dismissDialog();
                if (split.length > 2) {
                    callPhone = split[2];
                    AccountManager.getInstance().callPhone(userInfo.userID, split[2], type, 3);
                }
                break;
            case R.id.textview_dialog_contact_num4:
                DialogUtils.dismissDialog();
                if (split.length > 3) {
                    callPhone = split[3];
                    AccountManager.getInstance().callPhone(userInfo.userID, split[3], type, 3);
                }
                break;
            case R.id.textview_dialog_contact_num5:
                DialogUtils.dismissDialog();
                if (split.length > 4) {
                    callPhone = split[4];
                    AccountManager.getInstance().callPhone(userInfo.userID, split[4], type, 3);
                }
                break;
            case R.id.textview_dialog__cancle:
                DialogUtils.dismissDialog();
                if (activity.data.remainCoin > 0 && userInfo != null) {
                    if (mobile.contains(",")) {
                        callName = constactBean.getName();
                        split = mobile.split(",");
                        DialogUtils.showContactDialog(ContactFragment.this.getActivity(), ContactFragment.this, mobile, constactBean.getName());
                    } else {
                        callName = constactBean.getName();
                        callPhone = mobile;
                        callPhone(mobile);
                    }
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

    public void onEventMainThread(CallPhoneEvent event) {
        if (event.code == 0) {
            CallLogBeanDao callLogBeanDao = daoSession.getCallLogBeanDao();
            if (callName != null && callPhone != null && event.typeLog == 3) {
                CallLogBean callLogBean = new CallLogBean();
                callLogBean.setId(System.currentTimeMillis());
                callLogBean.setName(callName);
                callLogBean.setPhoneNum(callPhone);
                callLogBeanDao.insert(callLogBean);
            }
            ContactFragmentPermissionsDispatcher.tellPhoneWithCheck(ContactFragment.this, event.response.data);
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(UpdataContactEvent event) {
        if (event.code == 0) {
            if (constactBeanDao != null) {
                constactBeanDao.queryBuilder().build().list().clear();
                ContactFragmentPermissionsDispatcher.getContactInfoWithCheck(this);
            }
        }
    }

}
