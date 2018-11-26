package com.sinocall.phonerecordera.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.api.BaseResponse;
import com.sinocall.phonerecordera.api.DefaultRespHandler;
import com.sinocall.phonerecordera.api.HttpRestClient;
import com.sinocall.phonerecordera.api.account.AccounDetailResponse;
import com.sinocall.phonerecordera.api.account.ApplyVerifyResponse;
import com.sinocall.phonerecordera.api.account.BannerResponse;
import com.sinocall.phonerecordera.api.account.BeginAppResponse;
import com.sinocall.phonerecordera.api.account.BindPushResponse;
import com.sinocall.phonerecordera.api.account.CallPhoneResponse;
import com.sinocall.phonerecordera.api.account.CommenConsultResponse;
import com.sinocall.phonerecordera.api.account.CouponHistoricalResponse;
import com.sinocall.phonerecordera.api.account.DelFolderAndFileResponse;
import com.sinocall.phonerecordera.api.account.DeleteConsultResponse;
import com.sinocall.phonerecordera.api.account.DeleteMessageResponse;
import com.sinocall.phonerecordera.api.account.DirectCallLawyerHistoryResponse;
import com.sinocall.phonerecordera.api.account.DirectCallLawyerResponse;
import com.sinocall.phonerecordera.api.account.EvidenceListResponse;
import com.sinocall.phonerecordera.api.account.EvidenceRootListResponse;
import com.sinocall.phonerecordera.api.account.FileDetailResponse;
import com.sinocall.phonerecordera.api.account.GetCodeResponse;
import com.sinocall.phonerecordera.api.account.H5AskQuestionResponse;
import com.sinocall.phonerecordera.api.account.HistoryConsultResponse;
import com.sinocall.phonerecordera.api.account.LawConsultResponse;
import com.sinocall.phonerecordera.api.account.LoginResponse;
import com.sinocall.phonerecordera.api.account.LogoutResponse;
import com.sinocall.phonerecordera.api.account.MessageListResponse;
import com.sinocall.phonerecordera.api.account.MineCouponResponse;
import com.sinocall.phonerecordera.api.account.PayConsultResultResponse;
import com.sinocall.phonerecordera.api.account.PayResponse;
import com.sinocall.phonerecordera.api.account.PayResultResponse;
import com.sinocall.phonerecordera.api.account.PersonalInfoResponse;
import com.sinocall.phonerecordera.api.account.PreferentialResponse;
import com.sinocall.phonerecordera.api.account.ProofFileRenameResponse;
import com.sinocall.phonerecordera.api.account.RechageCenterResposse;
import com.sinocall.phonerecordera.api.account.RegisterResponse;
import com.sinocall.phonerecordera.api.account.RemainCoinResponse;
import com.sinocall.phonerecordera.api.account.RequireGiftResponse;
import com.sinocall.phonerecordera.api.account.ResetPswResponse;
import com.sinocall.phonerecordera.api.account.UpDataMessageResponse;
import com.sinocall.phonerecordera.api.account.UpLoadSignPicResponse;
import com.sinocall.phonerecordera.api.account.UserBeforeRegInfoResponse;
import com.sinocall.phonerecordera.api.account.UserChargeCoinCouponResponse;
import com.sinocall.phonerecordera.api.account.UserShareLogResponse;
import com.sinocall.phonerecordera.api.account.UserVerifyDetailResponse;
import com.sinocall.phonerecordera.api.account.UserVerifyFilesResponse;
import com.sinocall.phonerecordera.event.ApplyVerifyEvent;
import com.sinocall.phonerecordera.event.account.AccountDetailEvent;
import com.sinocall.phonerecordera.event.account.BannerEvent;
import com.sinocall.phonerecordera.event.account.BeginAppEvent;
import com.sinocall.phonerecordera.event.account.BindPushEvent;
import com.sinocall.phonerecordera.event.account.CallPhoneEvent;
import com.sinocall.phonerecordera.event.account.CommenConsultEvent;
import com.sinocall.phonerecordera.event.account.CouponHistoricalEvent;
import com.sinocall.phonerecordera.event.account.DelFolderAndFileEvent;
import com.sinocall.phonerecordera.event.account.DeleteConsultEvent;
import com.sinocall.phonerecordera.event.account.DeleteMessageEvent;
import com.sinocall.phonerecordera.event.account.DirectCallLawyerEvent;
import com.sinocall.phonerecordera.event.account.DirectCallLawyerHistoryEvent;
import com.sinocall.phonerecordera.event.account.EvidenceListEvent;
import com.sinocall.phonerecordera.event.account.EvidenceRootListEvent;
import com.sinocall.phonerecordera.event.account.FileDetailEvent;
import com.sinocall.phonerecordera.event.account.GetCodeEvent;
import com.sinocall.phonerecordera.event.account.H5AskQuestionEvent;
import com.sinocall.phonerecordera.event.account.HistoryConsultEvent;
import com.sinocall.phonerecordera.event.account.LawConsutingEvent;
import com.sinocall.phonerecordera.event.account.LoginEvent;
import com.sinocall.phonerecordera.event.account.LogoutEvent;
import com.sinocall.phonerecordera.event.account.MessageListEvent;
import com.sinocall.phonerecordera.event.account.MineCouponEvent;
import com.sinocall.phonerecordera.event.account.PayConsultResultEvent;
import com.sinocall.phonerecordera.event.account.PayEvent;
import com.sinocall.phonerecordera.event.account.PayResultEvent;
import com.sinocall.phonerecordera.event.account.PersonalInfoEvent;
import com.sinocall.phonerecordera.event.account.PreferentialEvent;
import com.sinocall.phonerecordera.event.account.ProofFileRenameEvent;
import com.sinocall.phonerecordera.event.account.RechageCenterEvent;
import com.sinocall.phonerecordera.event.account.RegisterEvent;
import com.sinocall.phonerecordera.event.account.RemainCoinEvent;
import com.sinocall.phonerecordera.event.account.RequireGiftEvent;
import com.sinocall.phonerecordera.event.account.ResetPasswordEvent;
import com.sinocall.phonerecordera.event.account.ResetPswEvent;
import com.sinocall.phonerecordera.event.account.UpLoadSignPicEvent;
import com.sinocall.phonerecordera.event.account.UpdataMessageEvent;
import com.sinocall.phonerecordera.event.account.UserBeforeRegInfoEvent;
import com.sinocall.phonerecordera.event.account.UserChargeCoinCouponEvent;
import com.sinocall.phonerecordera.event.account.UserShareLogEvent;
import com.sinocall.phonerecordera.event.account.UserVerifyDetailEvent;
import com.sinocall.phonerecordera.event.account.UserVerifyFilesEvent;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.MD5Utils;

import java.io.File;

import de.greenrobot.event.EventBus;

public class AccountManager {

    private static AccountManager INSTANCE = new AccountManager();

    private static final String PREF_NAME = "account";
    private static final String KEY_USER_INFO = "user_info";
    private static final String KEY_USER_PASSPORT = "user_passport";
    private static final String KEY_USER_DETAIL = "user_detail";
    private static final String KEY_IOU_PHOTO = "iou_photo";
    private static final String KEY_SYS_IS_GESTURE = "sys_is_gesture";
    private static final String KEY_FIRST_GESTURE = "first_gesture";
    private static final String KEY_GESTURE_PASSWORD = "sys_gestrue_password";
    private static final String KEY_PHONE_NUM = "user_cellphone";

    public static AccountManager getInstance() {
        return INSTANCE;
    }

    private AccountManager() {

    }

    public static boolean isLogined() {
        return getUserInfo() != null;
    }


    public static String getLastCellPhone() {
        String cellphone = getSharedPreferences().getString(KEY_PHONE_NUM, null);
        if (!TextUtils.isEmpty(cellphone)) {
            return cellphone;
        } else {
            return null;
        }
    }

    public static void setUserInfo(UserInfo userInfo) {
        String info = userInfo != null ? new Gson().toJson(userInfo) : null;
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_USER_INFO, info);
        editor.apply();
    }

    public static UserInfo getUserInfo() {
        String userInfo = getSharedPreferences().getString(KEY_USER_INFO, null);
        if (!TextUtils.isEmpty(userInfo)) {
            return new Gson().fromJson(userInfo, UserInfo.class);
        } else {
            return null;
        }
    }

    private static SharedPreferences getSharedPreferences() {
        Context context = PhonerecorderaApplication.getInstance();
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取验证码
     *
     * @param cellphone
     * @param type
     */
    public void getValidationCode(String cellphone, int type) {
        HttpParams bundle = new HttpParams();
        bundle.put("mobileNo", cellphone);
        bundle.put("validType", type);
        HttpRestClient.post("valNumQry.action", bundle,
                new DefaultRespHandler<GetCodeResponse, GetCodeEvent>() {
                    @Override
                    public void onSuccess(GetCodeResponse baseResponse, String rawJsonResponse) {
                        EventBus.getDefault().post(
                                new GetCodeEvent(baseResponse.code, baseResponse.codeInfo, rawJsonResponse));
                    }
                });
    }

    /**
     * 注册
     *
     * @param cellphone
     * @param password
     * @param validNo
     */
    public void register(String cellphone, String password, String validNo) {
        HttpParams bundle = new HttpParams();
        bundle.put("mobileNo", cellphone);
        bundle.put("validNo", validNo);
        try {
            bundle.put("password", MD5Utils.hexdigest(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRestClient.post("regInfo.action", bundle, new DefaultRespHandler<RegisterResponse, RegisterEvent>() {
            @Override
            public void onSuccess(RegisterResponse baseResponse, String rawJsonResponse) {
                RegisterEvent registerEvent = new RegisterEvent(baseResponse.code, baseResponse.codeInfo, rawJsonResponse);
                EventBus.getDefault().post(registerEvent);
            }
        });
    }

    /**
     * 忘记密码
     *
     * @param cellphone
     * @param password
     * @param validNo
     */
    public void updatePassword(String cellphone, String password, String validNo) {
        HttpParams bundle = new HttpParams();
        bundle.put("mobileNo", cellphone);
        bundle.put("validNo", validNo);
        try {
            bundle.put("password", MD5Utils.hexdigest(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRestClient.post("resetPwdV2.action", bundle, new DefaultRespHandler<BaseResponse, ResetPasswordEvent>() {
            @Override
            public void onSuccess(BaseResponse baseResponse, String rawJsonResponse) {
                ResetPasswordEvent resetPasswordEvent = new ResetPasswordEvent(baseResponse.getCode(), baseResponse.getCodeInfo(), rawJsonResponse);
                EventBus.getDefault().post(resetPasswordEvent);
            }
        });
    }


    /**
     * 修改密码
     *
     * @param passwordOld
     * @param passwordNew
     */
    public void resetLoginPassword(String userID, String passwordOld, String passwordNew) {
        HttpParams bundle = new HttpParams();
        bundle.put("userID", userID);
        bundle.put("oldpwd", MD5Utils.hexdigest(passwordOld));
        try {
            bundle.put("password", MD5Utils.hexdigest(passwordNew));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRestClient.post("pwdmodify.action", bundle, new DefaultRespHandler<ResetPswResponse, ResetPswEvent>() {
            @Override
            public void onSuccess(ResetPswResponse resetPswResponse, String rawJsonResponse) {
                ResetPswEvent resetPswEvent = new ResetPswEvent(resetPswResponse.code, resetPswResponse.codeInfo, rawJsonResponse);
                resetPswEvent.resetPswResponse = resetPswResponse;
                EventBus.getDefault().post(resetPswEvent);
            }
        });
    }


    /**
     * 登录
     *
     * @param cellphone
     * @param password
     */
    public void login(String cellphone, String password) {
        HttpParams bundle = new HttpParams();
        bundle.put("mobileNo", cellphone);
        try {
            bundle.put("password", MD5Utils.hexdigest(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRestClient.post("login.action", bundle, new DefaultRespHandler<LoginResponse, LoginEvent>() {
            @Override
            public void onSuccess(LoginResponse loginResponse, String rawJsonResponse) {
                LoginEvent loginEvent = new LoginEvent(loginResponse.code, loginResponse.codeInfo, rawJsonResponse);
                if (loginResponse.code == 0) {
                    AccountManager.setUserInfo(loginResponse.data);
                }
                EventBus.getDefault().post(loginEvent);
            }
        });
    }

    /**
     * 登出
     */
    public void logout(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("logout.action", httpParams, new DefaultRespHandler<LogoutResponse, LogoutEvent>() {
            @Override
            public void onSuccess(LogoutResponse logoutResponse, String rawJsonResponse) {
                LogoutEvent logoutEvent = new LogoutEvent(logoutResponse.code, logoutResponse.codeInfo, rawJsonResponse);
                if (logoutResponse.data.status == 0) {
                    AccountManager.setUserInfo(null);
                } else {

                }
                EventBus.getDefault().post(logoutEvent);
            }
        });
    }

    /**
     * 个人中心
     *
     * @param userID
     */
    public void getPersonInfo(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("feeQuery.action", httpParams, new DefaultRespHandler<PersonalInfoResponse, PersonalInfoEvent>() {
            @Override
            public void onSuccess(PersonalInfoResponse personalInfoResponse, String rawJsonResponse) {
                PersonalInfoEvent personalInfoEvent = new PersonalInfoEvent(personalInfoResponse.code, personalInfoResponse.codeInfo, rawJsonResponse);
                personalInfoEvent.personalInfoResponse = personalInfoResponse;
                EventBus.getDefault().post(personalInfoEvent);
            }
        });
    }

    /**
     * 充值中心
     *
     * @param userID
     */
    public void getRechageCenter(String userID, int operationActivityId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        if (operationActivityId != 0) {
            httpParams.put("operationActivityId", operationActivityId);
        }
        HttpRestClient.post("sysPayConfig.action", httpParams, new DefaultRespHandler<RechageCenterResposse, RechageCenterEvent>() {
            @Override
            public void onSuccess(RechageCenterResposse rechageCenterResposse, String rawJsonResponse) {
                RechageCenterEvent rechageCenterEvent = new RechageCenterEvent(rechageCenterResposse.code, rechageCenterResposse.codeInfo, rawJsonResponse);
                rechageCenterEvent.rechageCenterResposse = rechageCenterResposse;
                EventBus.getDefault().post(rechageCenterEvent);
            }
        });
    }

    public void getPayInit(String userID, final int payType, Double price, Double payPrice, int giftCoinNum,int operationActivityId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("payType", payType);
        httpParams.put("price", price);
        httpParams.put("payPrice", payPrice);
        httpParams.put("giftCoinNum", giftCoinNum);
        HttpRestClient.post("payInit.action", httpParams, new DefaultRespHandler<PayResponse, PayEvent>() {
            @Override
            public void onSuccess(PayResponse aliPayResponse, String rawJsonResponse) {
                PayEvent payEvent = new PayEvent(aliPayResponse.code, aliPayResponse.codeInfo, rawJsonResponse);
                payEvent.payType = payType;
                payEvent.payResponse = aliPayResponse;
                EventBus.getDefault().post(payEvent);
            }
        });
    }

    public void checkPayResult(String userID, String orderId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("orderId", orderId);
        HttpRestClient.post("userPayResult.action", httpParams, new DefaultRespHandler<PayResultResponse, PayResultEvent>() {
            @Override
            public void onSuccess(PayResultResponse response, String rawJsonResponse) {
                PayResultEvent payResultResponse = new PayResultEvent(response.code, response.codeInfo, rawJsonResponse);
                payResultResponse.payResultResponse = response;
                EventBus.getDefault().post(payResultResponse);
            }
        });
    }

    /**
     * 获取账户详情
     *
     * @param userID
     * @param pn
     * @param ps
     */
    public void getAccountDetail(String userID, int pn, int ps) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("pn", pn);
        httpParams.put("ps", ps);
        HttpRestClient.post("recordCoinCC.action", httpParams, new DefaultRespHandler<AccounDetailResponse, AccountDetailEvent>() {
            @Override
            public void onSuccess(AccounDetailResponse accounDetailResponse, String rawJsonResponse) {
                AccountDetailEvent accountDetailEvent = new AccountDetailEvent(accounDetailResponse.code, accounDetailResponse.codeInfo, rawJsonResponse);
                accountDetailEvent.accounDetailResponse = accounDetailResponse;
                EventBus.getDefault().post(accountDetailEvent);
            }
        });
    }

    /**
     * 获取录音列表
     *
     * @param userID
     * @param folderID
     * @param type
     * @param pn
     * @param ps
     */
    public void getMineRecording(String userID, String folderID, int type, int pn, int ps) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("folderID", folderID);
        if (type != 0) {
            httpParams.put("type", type);
        }
        httpParams.put("pn", pn);
        httpParams.put("ps", ps);
        HttpRestClient.post("evidenceList.action", httpParams, new DefaultRespHandler<EvidenceListResponse, EvidenceListEvent>() {
            @Override
            public void onSuccess(EvidenceListResponse evidenceListResponse, String rawJsonResponse) {
                EvidenceListEvent evidenceRootListEvent = new EvidenceListEvent(evidenceListResponse.code, evidenceListResponse.codeInfo, rawJsonResponse);
                evidenceRootListEvent.evidenceListResponse = evidenceListResponse;
                EventBus.getDefault().post(evidenceRootListEvent);
            }
        });
    }

    /**
     * 获取文件夹
     *
     * @param userID
     */
    public void getMineRecordingRoot(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("evidenceRootList.action", httpParams, new DefaultRespHandler<EvidenceRootListResponse, EvidenceRootListEvent>() {
            @Override
            public void onSuccess(EvidenceRootListResponse evidenceRootListResponse, String rawJsonResponse) {
                EvidenceRootListEvent evidenceRootListEvent = new EvidenceRootListEvent(evidenceRootListResponse.code, evidenceRootListResponse.codeInfo, rawJsonResponse);
                evidenceRootListEvent.evidenceRootListResponse = evidenceRootListResponse;
                EventBus.getDefault().post(evidenceRootListEvent);
            }
        });
    }

    /**
     * 获取文件详情
     *
     * @param fileID
     * @param userID
     */
    public void getFileDetail(String fileID, String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("fileID", fileID);
        httpParams.put("userID", userID);
        HttpRestClient.post("fileInfo.action", httpParams, new DefaultRespHandler<FileDetailResponse, FileDetailEvent>() {
            @Override
            public void onSuccess(FileDetailResponse fileDetailResponse, String rawJsonResponse) {
                FileDetailEvent fileDetailEvent = new FileDetailEvent(fileDetailResponse.code, fileDetailResponse.codeInfo, rawJsonResponse);
                fileDetailEvent.fileDetailResponse = fileDetailResponse;
                EventBus.getDefault().post(fileDetailEvent);
            }
        });
    }

    /**
     * 获取消息列表
     *
     * @param userID
     */
    public void getMessageList(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("sysMsgQry.action", httpParams, new DefaultRespHandler<MessageListResponse, MessageListEvent>() {
            @Override
            public void onSuccess(MessageListResponse messageListResponse, String rawJsonResponse) {
                MessageListEvent messageListEvent = new MessageListEvent(messageListResponse.code, messageListResponse.codeInfo, rawJsonResponse);
                messageListEvent.messageListResponse = messageListResponse;
                EventBus.getDefault().post(messageListEvent);
            }
        });
    }

    /**
     * 标记已读
     *
     * @param userID
     * @param sysmsgId
     */
    public void getMessageRead(String userID, String sysmsgId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("sysmsgId", sysmsgId);
        HttpRestClient.post("sysMsgUpdate.action", httpParams, new DefaultRespHandler<UpDataMessageResponse, UpdataMessageEvent>() {
            @Override
            public void onSuccess(UpDataMessageResponse upDataMessageResponse, String rawJsonResponse) {
                UpdataMessageEvent updataMessageEvent = new UpdataMessageEvent(upDataMessageResponse.code, upDataMessageResponse.codeInfo, rawJsonResponse);
                updataMessageEvent.upDataMessageResponse = upDataMessageResponse;
                EventBus.getDefault().post(updataMessageEvent);
            }
        });
    }

    /**
     * 删除消息
     *
     * @param userID
     * @param sysmsgId
     */
    public void deleteMessage(String userID, String sysmsgId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("sysmsgId", sysmsgId);
        HttpRestClient.post("sysMsgDelete.action", httpParams, new DefaultRespHandler<DeleteMessageResponse, DeleteMessageEvent>() {
            @Override
            public void onSuccess(DeleteMessageResponse deleteMessageResponse, String rawJsonResponse) {
                DeleteMessageEvent deleteMessageEvent = new DeleteMessageEvent(deleteMessageResponse.code, deleteMessageResponse.codeInfo, rawJsonResponse);
                deleteMessageEvent.deleteMessageResponse = deleteMessageResponse;
                EventBus.getDefault().post(deleteMessageEvent);
            }
        });
    }

    /**
     * 我的优惠券页面
     *
     * @param userID
     */
    public void getMineCouPon(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("userCouponListValid.action", httpParams, new DefaultRespHandler<MineCouponResponse, MineCouponEvent>() {
            @Override
            public void onSuccess(MineCouponResponse mineCouponResponse, String rawJsonResponse) {
                MineCouponEvent mineCouponEvent = new MineCouponEvent(mineCouponResponse.code, mineCouponResponse.codeInfo, rawJsonResponse);
                mineCouponEvent.mineCouponResponse = mineCouponResponse;
                EventBus.getDefault().post(mineCouponEvent);
            }

        });
    }

    /**
     * 优惠活动
     *
     * @param userID
     */
    public void getPreferential(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("userActivity.action", httpParams, new DefaultRespHandler<PreferentialResponse, PreferentialEvent>() {
            @Override
            public void onSuccess(PreferentialResponse mResponse, String rawJsonResponse) {
                PreferentialEvent personalInfoEvent = new PreferentialEvent(mResponse.code, mResponse.codeInfo, rawJsonResponse);
                personalInfoEvent.preferentialResponse = mResponse;
                EventBus.getDefault().post(personalInfoEvent);
            }

        });
    }

    /**
     * 优惠券历史记录
     *
     * @param userID
     */
    public void getHistorialPreferential(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("userCouponListHistory.action", httpParams, new DefaultRespHandler<CouponHistoricalResponse, CouponHistoricalEvent>() {
            @Override
            public void onSuccess(CouponHistoricalResponse mResponse, String rawJsonResponse) {
                CouponHistoricalEvent couponHistoricalEvent = new CouponHistoricalEvent(mResponse.code, mResponse.codeInfo, rawJsonResponse);
                couponHistoricalEvent.response = mResponse;
                EventBus.getDefault().post(couponHistoricalEvent);
            }

        });
    }

    /**
     * 拨打电话号码
     *
     * @param userID
     * @param callMobileNo
     */
    public void callPhone(String userID, final String callMobileNo, int type, final int typeLog) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("callMobileNo", callMobileNo);
        httpParams.put("type", type);
        HttpRestClient.post("saveCallMobileNo.action", httpParams, new DefaultRespHandler<CallPhoneResponse, CallPhoneEvent>() {
            @Override
            public void onSuccess(CallPhoneResponse callPhoneResponse, String rawJsonResponse) {
                CallPhoneEvent callPhoneEvent = new CallPhoneEvent(callPhoneResponse.code, callPhoneResponse.codeInfo, rawJsonResponse);
                callPhoneEvent.response = callPhoneResponse;
                callPhoneEvent.typeLog = typeLog;
                callPhoneEvent.callMobileNo = callMobileNo;
                EventBus.getDefault().post(callPhoneEvent);
            }
        });
    }

    /**
     * 律师页面
     *
     */
    public void getLawConsult() {
        HttpRestClient.post("lawConsult.action", null, new DefaultRespHandler<LawConsultResponse, LawConsutingEvent>() {
            @Override
            public void onSuccess(LawConsultResponse lawConsultResponse, String rawJsonResponse) {
                LawConsutingEvent lawConsutingEvent = new LawConsutingEvent(lawConsultResponse.code, lawConsultResponse.codeInfo, rawJsonResponse);
                lawConsutingEvent.response = lawConsultResponse;
                EventBus.getDefault().post(lawConsutingEvent);
            }
        });
    }

    /**
     * 一键呼叫咨询律师
     *
     * @param userID
     * @param mobileNo
     */
    public void getDirectCallLawyer(String userID, String mobileNo) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("mobileNo", mobileNo);
        HttpRestClient.post("directCallLawyer.action", httpParams, new DefaultRespHandler<DirectCallLawyerResponse, DirectCallLawyerEvent>() {
            @Override
            public void onSuccess(DirectCallLawyerResponse directCallLawyerResponse, String rawJsonResponse) {
                DirectCallLawyerEvent directCallLawyerEvent = new DirectCallLawyerEvent(directCallLawyerResponse.code, directCallLawyerResponse.codeInfo, rawJsonResponse);
                directCallLawyerEvent.response = directCallLawyerResponse;
                EventBus.getDefault().post(directCallLawyerEvent);
            }
        });
    }

    /**
     * 呼叫律师
     *
     * @param userID
     * @param pn
     * @param ps
     */
    public void getDirectCallLawyerHistory(String userID, int pn, int ps) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("pn", pn);
        httpParams.put("ps", ps);
        HttpRestClient.post("directCallLawyerHistory.action", httpParams, new DefaultRespHandler<DirectCallLawyerHistoryResponse, DirectCallLawyerHistoryEvent>() {
            @Override
            public void onSuccess(DirectCallLawyerHistoryResponse directCallLawyerHistoryResponse, String rawJsonResponse) {
                DirectCallLawyerHistoryEvent directCallLawyerHistoryEvent = new DirectCallLawyerHistoryEvent(directCallLawyerHistoryResponse.code, directCallLawyerHistoryResponse.codeInfo, rawJsonResponse);
                directCallLawyerHistoryEvent.response = directCallLawyerHistoryResponse;
                EventBus.getDefault().post(directCallLawyerHistoryEvent);
            }
        });
    }

    /**
     * 律师回电
     *
     * @param userID
     * @param pn
     * @param ps
     */
    public void getHistoryConsult(String userID, int pn, int ps) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("pn", pn);
        httpParams.put("ps", ps);
        HttpRestClient.post("historyConsult.action", httpParams, new DefaultRespHandler<HistoryConsultResponse, HistoryConsultEvent>() {
            @Override
            public void onSuccess(HistoryConsultResponse response, String rawJsonResponse) {
                HistoryConsultEvent historyConsultEvent = new HistoryConsultEvent(response.code, response.codeInfo, rawJsonResponse);
                historyConsultEvent.response = response;
                EventBus.getDefault().post(historyConsultEvent);
            }
        });
    }

    public void deleteConsult(int id) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("cid", id);
        HttpRestClient.post("deleteConsult.action", httpParams, new DefaultRespHandler<DeleteConsultResponse, DeleteConsultEvent>() {
            @Override
            public void onSuccess(DeleteConsultResponse deleteConsultResponse, String rawJsonResponse) {
                DeleteConsultEvent deleteConsultEvent = new DeleteConsultEvent(deleteConsultResponse.code, deleteConsultResponse.codeInfo, rawJsonResponse);
                deleteConsultEvent.response = deleteConsultResponse;
                EventBus.getDefault().post(deleteConsultEvent);
            }
        });
    }

    /**
     * 评价
     *
     * @param id
     * @param numStars
     * @param s
     */
    public void commentConsult(int id, int numStars, String s) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("cid", id);
        httpParams.put("star", numStars);
        httpParams.put("comment", s);
        HttpRestClient.post("commentConsult.action", httpParams, new DefaultRespHandler<CommenConsultResponse, CommenConsultEvent>() {
            @Override
            public void onSuccess(CommenConsultResponse commenConsultResponse, String rawJsonResponse) {
                CommenConsultEvent commenConsultEvent = new CommenConsultEvent(commenConsultResponse.code, commenConsultResponse.codeInfo, rawJsonResponse);
                commenConsultEvent.response = commenConsultResponse;
                EventBus.getDefault().post(commenConsultEvent);
            }
        });
    }

    /**
     * 咨询律和律师
     *
     * @param userID   userid
     * @param payType  支付宝100  微信105
     * @param payPrice 实际支付价格
     * @param phone    接听手机号
     * @param code     价格码
     * @param question 咨询问题
     */
    public void askQuestion(String userID, int payType, double payPrice, String phone, String code, String question) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("payType", payType);
        httpParams.put("payPrice", payPrice);
        httpParams.put("phone", phone);
        httpParams.put("priceCode", code);
        httpParams.put("question", question);
        HttpRestClient.post("askQuestion.action", httpParams, new DefaultRespHandler<H5AskQuestionResponse, H5AskQuestionEvent>() {
            @Override
            public void onSuccess(H5AskQuestionResponse h5AskQuestionResponse, String rawJsonResponse) {
                H5AskQuestionEvent h5AskQuestionEvent = new H5AskQuestionEvent(h5AskQuestionResponse.code, h5AskQuestionResponse.codeInfo, rawJsonResponse);
                h5AskQuestionEvent.response = h5AskQuestionResponse;
                EventBus.getDefault().post(h5AskQuestionEvent);
            }
        });
    }

    /**
     * 律师咨询  校验支付结果
     *
     * @param userID
     * @param orderId
     */
    public void payConsultResult(String userID, String orderId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("orderId", orderId);
        HttpRestClient.post("payConsultResult.action", httpParams, new DefaultRespHandler<PayConsultResultResponse, PayConsultResultEvent>() {
            @Override
            public void onSuccess(PayConsultResultResponse payConsultResultResponse, String rawJsonResponse) {
                PayConsultResultEvent payConsultResultEvent = new PayConsultResultEvent(payConsultResultResponse.code, payConsultResultResponse.codeInfo, rawJsonResponse);
                payConsultResultEvent.response = payConsultResultResponse;
                EventBus.getDefault().post(payConsultResultEvent);
            }
        });
    }

    /**
     * @param userID
     * @param signPath
     */
    public void upLoadSignPic(String userID, String signPath) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("file", new File(signPath));
        HttpRestClient.post("uploadSignPic.action", httpParams, new DefaultRespHandler<UpLoadSignPicResponse, UpLoadSignPicEvent>() {
            @Override
            public void onSuccess(UpLoadSignPicResponse upLoadSignPicResponse, String rawJsonResponse) {
                UpLoadSignPicEvent upLoadSignPicEvent = new UpLoadSignPicEvent(upLoadSignPicResponse.code, upLoadSignPicResponse.codeInfo, rawJsonResponse);
                upLoadSignPicEvent.response = upLoadSignPicResponse;
                EventBus.getDefault().post(upLoadSignPicEvent);
            }
        });
    }

    /**
     * 申请存证
     *
     * @param fileId
     * @param name
     * @param idCard
     * @param signPic
     * @param userID
     */
    public void applyVerify(long fileId, String name, String idCard, String signPic, String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("fileId", fileId);
        httpParams.put("realName", name);
        httpParams.put("idCard", idCard);
        httpParams.put("signPic", signPic);
        httpParams.put("userID", userID);
        HttpRestClient.post("applyVerify.action", httpParams, new DefaultRespHandler<ApplyVerifyResponse, ApplyVerifyEvent>() {
            @Override
            public void onSuccess(ApplyVerifyResponse applyVerifyResponse, String rawJsonResponse) {
                ApplyVerifyEvent applyVerifyEvent = new ApplyVerifyEvent(applyVerifyResponse.code, applyVerifyResponse.codeInfo, rawJsonResponse);
                applyVerifyEvent.response = applyVerifyResponse;
                EventBus.getDefault().post(applyVerifyEvent);
            }
        });
    }

    /**
     * 文件重命名
     *
     * @param userID
     * @param fileID
     * @param fileName
     */
    public void proofFileRename(String userID, String fileID, String fileName) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("fileID", fileID);
        httpParams.put("name", fileName);
        HttpRestClient.post("proofFileRename.action", httpParams, new DefaultRespHandler<ProofFileRenameResponse, ProofFileRenameEvent>() {
            @Override
            public void onSuccess(ProofFileRenameResponse renameResponse, String rawJsonResponse) {
                ProofFileRenameEvent proofFileRenameEvent = new ProofFileRenameEvent(renameResponse.code, renameResponse.codeInfo, rawJsonResponse);
                proofFileRenameEvent.response = renameResponse;
                EventBus.getDefault().post(proofFileRenameEvent);
            }
        });
    }

    public void delFolderAndFile(String userID, String fileIDs) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("fileIDs", fileIDs);
        HttpRestClient.post("delFolderAndFile.action", httpParams, new DefaultRespHandler<DelFolderAndFileResponse, DelFolderAndFileEvent>() {
            @Override
            public void onSuccess(DelFolderAndFileResponse delFolderAndFileResponse, String rawJsonResponse) {
                DelFolderAndFileEvent delFolderAndFileEvent = new DelFolderAndFileEvent(delFolderAndFileResponse.code, delFolderAndFileResponse.codeInfo, rawJsonResponse);
                delFolderAndFileEvent.response = delFolderAndFileResponse;
                EventBus.getDefault().post(delFolderAndFileEvent);
            }
        });
    }

    /**
     * 存证列表
     *
     * @param userID
     * @param pn
     * @param ps
     */
    public void getUserVerifyFiles(String userID, int pn, int ps) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("pn", pn);
        httpParams.put("ps", ps);
        HttpRestClient.post("userVerifyFiles", httpParams, new DefaultRespHandler<UserVerifyFilesResponse, UserVerifyFilesEvent>() {
            @Override
            public void onSuccess(UserVerifyFilesResponse userVerifyFilesResponse, String rawJsonResponse) {
                UserVerifyFilesEvent userVerifyFilesEvent = new UserVerifyFilesEvent(userVerifyFilesResponse.code, userVerifyFilesResponse.codeInfo, rawJsonResponse);
                userVerifyFilesEvent.response = userVerifyFilesResponse;
                EventBus.getDefault().post(userVerifyFilesEvent);
            }
        });
    }

    /**
     * 存证详情
     *
     * @param fileId
     */
    public void getUserVerifyFileDetail(long fileId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("fileId", fileId);
        HttpRestClient.post("userVerifyFileDetail", httpParams, new DefaultRespHandler<UserVerifyDetailResponse, UserVerifyDetailEvent>() {
            @Override
            public void onSuccess(UserVerifyDetailResponse userVerifyDetailResponse, String rawJsonResponse) {
                UserVerifyDetailEvent userVerifyDetailEvent = new UserVerifyDetailEvent(userVerifyDetailResponse.code, userVerifyDetailResponse.codeInfo, rawJsonResponse);
                userVerifyDetailEvent.response = userVerifyDetailResponse;
                EventBus.getDefault().post(userVerifyDetailEvent);
            }
        });

    }

    /**
     * 优惠活动 banner
     */
    public void getBanner() {
        HttpRestClient.post("banner.action", null, new DefaultRespHandler<BannerResponse, BannerEvent>() {
            @Override
            public void onSuccess(BannerResponse bannerResponse, String rawJsonResponse) {
                BannerEvent bannerEvent = new BannerEvent(bannerResponse.code, bannerResponse.codeInfo, rawJsonResponse);
                bannerEvent.response = bannerResponse;
                EventBus.getDefault().post(bannerResponse);
            }
        });
    }

    /**
     * 分享成功调用
     *
     * @param shareType （0：新浪微博，1：微信好友，2：微信朋友圈，3：短信 4:QQ）
     * @param userID    用户id
     */
    public void userShareLog(int shareType, String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("shareType", shareType);
        httpParams.put("userID", userID);
        HttpRestClient.post("userShareLog.action", httpParams, new DefaultRespHandler<UserShareLogResponse, UserShareLogEvent>() {
            @Override
            public void onSuccess(UserShareLogResponse userShareLogResponse, String rawJsonResponse) {
                UserShareLogEvent userShareLogEvent = new UserShareLogEvent(userShareLogResponse.code, userShareLogResponse.codeInfo, rawJsonResponse);
                userShareLogEvent.setUserShareLogResponse(userShareLogResponse);
                EventBus.getDefault().post(userShareLogEvent);
            }
        });
    }

    /**
     * 五星好评后调用
     *
     * @param userID
     * @param i
     */
    public void requireGift(String userID, int i) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("type", i);
        HttpRestClient.post("requireGift.action", httpParams, new DefaultRespHandler<RequireGiftResponse, RequireGiftEvent>() {
            @Override
            public void onSuccess(RequireGiftResponse requireGiftResponse, String rawJsonResponse) {
                RequireGiftEvent requireGiftEvent = new RequireGiftEvent(requireGiftResponse.code, requireGiftResponse.codeInfo, rawJsonResponse);
                requireGiftEvent.response = requireGiftResponse;
                EventBus.getDefault().post(requireGiftEvent);
            }
        });
    }

    /**
     * 推送
     *
     * @param xingeToken
     */
    public void bindPushToken(String xingeToken, String userID) {
        HttpParams params = new HttpParams();
        params.put("xingeToken", xingeToken);
        params.put("userID", userID);
        HttpRestClient.post("bindDeviceToken.action", params, new DefaultRespHandler<BindPushResponse, BindPushEvent>() {
            @Override
            public void onSuccess(BindPushResponse bindPushResponse, String rawJsonResponse) {
                BindPushEvent bindPushEvent = new BindPushEvent(bindPushResponse.code, bindPushResponse.codeInfo, "");
                bindPushEvent.response = bindPushResponse;
                EventBus.getDefault().post(bindPushEvent);
            }
        });
    }

    /**
     * 推送弹屏
     */
    public void recordScreenPop(int sysScreenPopId, int operateStatus, String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("sysScreenPopId", sysScreenPopId);
        httpParams.put("operateStatus", operateStatus);
        httpParams.put("userID", userID);
        HttpRestClient.post("recordScreenPop.action", httpParams, new DefaultRespHandler());

    }

    public void userChargeCoinCoupon(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("userChargeCoinCoupon.action", httpParams, new DefaultRespHandler<UserChargeCoinCouponResponse, UserChargeCoinCouponEvent>() {
            @Override
            public void onSuccess(UserChargeCoinCouponResponse userChargeCoinCouponResponse, String rawJsonResponse) {
                UserChargeCoinCouponEvent userChargeCoinCouponEvent = new UserChargeCoinCouponEvent(userChargeCoinCouponResponse.code, userChargeCoinCouponResponse.codeInfo, "");
                userChargeCoinCouponEvent.response = userChargeCoinCouponResponse;
                EventBus.getDefault().post(userChargeCoinCouponEvent);
            }
        });
    }

    public void checkAllowDownloadFile(String userID, String fileIDs, String validType) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        httpParams.put("fileID", fileIDs);
        httpParams.put("validType", validType);
        HttpRestClient.post("remainCoin", httpParams, new DefaultRespHandler<RemainCoinResponse, RemainCoinEvent>() {
            @Override
            public void onSuccess(RemainCoinResponse remainCoinResponse, String rawJsonResponse) {
                RemainCoinEvent remainCoinEvent = new RemainCoinEvent(remainCoinResponse.code, remainCoinResponse.codeInfo, rawJsonResponse);
                remainCoinEvent.remainCoinResponse = remainCoinResponse;
                EventBus.getDefault().post(remainCoinEvent);
            }
        });
    }

    public void downloadFile(String userID, String fileIDs, String fileName, String fileExt) {

        HttpRestClient.downloadFile("download.action", userID, fileIDs, fileName, fileExt, null);

    }

    /**
     * @param token
     */

    public void userBeforeRegInfo(String token) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("xingeToken", token);
        HttpRestClient.post("userBeforeRegInfo.action", httpParams, new DefaultRespHandler<UserBeforeRegInfoResponse, UserBeforeRegInfoEvent>() {
            @Override
            public void onSuccess(UserBeforeRegInfoResponse userBeforeRegInfoResponse, String rawJsonResponse) {
                UserBeforeRegInfoEvent userBeforeRegInfoEvent = new UserBeforeRegInfoEvent(userBeforeRegInfoResponse.code, userBeforeRegInfoResponse.codeInfo, rawJsonResponse);
                userBeforeRegInfoEvent.response = userBeforeRegInfoResponse;
                EventBus.getDefault().post(userBeforeRegInfoEvent);
            }
        });
    }

    /**
     * 开启APP调用，判断是否有弹屏
     *
     * @param userID
     */
    public void beginApp(String userID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userID", userID);
        HttpRestClient.post("beginApp.action", httpParams, new DefaultRespHandler<BeginAppResponse, BeginAppEvent>() {
            @Override
            public void onSuccess(BeginAppResponse beginAppResponse, String rawJsonResponse) {
                BeginAppEvent beginAppEvent = new BeginAppEvent(beginAppResponse.code, beginAppResponse.codeInfo, rawJsonResponse);
                beginAppEvent.response = beginAppResponse;
                EventBus.getDefault().post(beginAppEvent);
            }
        });
    }


}
