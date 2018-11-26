package com.sinocall.phonerecordera.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.TencentMessageReceiver;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.PopsBeanList;
import com.sinocall.phonerecordera.ui.activity.WebViewActivity;

/**
 * Created by qingchao on 2017/11/24.
 */

public class DialogUtils {
    public static AlertDialog alertDialogLogout;

    public static void showDialog(Activity context, View.OnClickListener onClickListener, String picUrl) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_call_fragment_explain);
        ImageView imageView = (ImageView) alertDialogLogout.getWindow().findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        Glide.with(context).load(picUrl).asBitmap().into(imageView);
    }

    public static void dismissDialog() {
        if (alertDialogLogout == null) {
            return;
        } else {
            try {
                if (alertDialogLogout.isShowing()) {
                    alertDialogLogout.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                alertDialogLogout = null;
            }
        }
    }

    public static void showPayDialog(Activity context, View.OnClickListener onClickListener, String money, String recordNum) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        try{
            if (!context.isFinishing()) {
                alertDialogLogout.show();
            }
        }catch (Exception e){

        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 140;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_pay);
        LinearLayout WeiXinPay = alertDialogLogout.getWindow().findViewById(R.id.linearlayout_weixin_pay);
        LinearLayout ALiPay = alertDialogLogout.getWindow().findViewById(R.id.linearlayout_alipay);
        LinearLayout UnionPay = alertDialogLogout.getWindow().findViewById(R.id.linearlayout_unionpay);
        TextView DetailPay = alertDialogLogout.getWindow().findViewById(R.id.textview_detail_pay);
        DetailPay.setText("您即将充值¥" + money + "元，充值成功后可获得" + recordNum + "录音币，请点击支付：");
        String s = DetailPay.getText().toString();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s);
        int i1End = s.indexOf("，充值成功后可获得");
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 5, i1End, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int i2start = s.indexOf("获得");
        int i2End = s.indexOf("，请点击支付：");
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), i2start + 2, i2End, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        WeiXinPay.setOnClickListener(onClickListener);
        ALiPay.setOnClickListener(onClickListener);
        UnionPay.setOnClickListener(onClickListener);
        DetailPay.setText(spannableStringBuilder);
    }

    public static void showCouponRuleDialog(Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 140;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_counpon_rule);
        LinearLayout linearLayout = (LinearLayout) alertDialogLogout.getWindow().findViewById(R.id.linearlayout_dialog_coupo_rule);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public static void showDeleteMessageDialog(Activity context, View.OnClickListener onClickListener, final String userID, final String sysmsgId) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_delete_message);
        TextView messageDelete = (TextView) alertDialogLogout.getWindow().findViewById(R.id.message_delete);
        messageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.getInstance().deleteMessage(userID, sysmsgId);
                DialogUtils.dismissDialog();
            }
        });
    }

    public static void showContactDialog(Activity context, View.OnClickListener onClickListener, String mobile, String name) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_contact_phone_num);
        TextView contactName = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_name);
        TextView contactNum1 = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_num1);
        TextView contactNum2 = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_num2);
        TextView contactNum3 = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_num3);
        TextView contactNum4 = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_num4);
        TextView contactNum5 = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_contact_num5);
        View devide1 = (View) alertDialogLogout.getWindow().findViewById(R.id.devide_1);
        View devide2 = (View) alertDialogLogout.getWindow().findViewById(R.id.devide_2);
        View devide3 = (View) alertDialogLogout.getWindow().findViewById(R.id.devide_3);
        View devide4 = (View) alertDialogLogout.getWindow().findViewById(R.id.devide_4);
        contactName.setText(name);
        String[] split = mobile.split(",");
        LogUtil.e("split", split.toString());
        int length = split.length;
        contactNum1.setText(split[0]);
        contactNum2.setText(split[1]);
        if (length > 2) {
            contactNum3.setText(split[2]);
            contactNum3.setVisibility(View.VISIBLE);
            devide2.setVisibility(View.VISIBLE);
        }
        if (length > 3) {
            contactNum4.setText(split[3]);
            contactNum4.setVisibility(View.VISIBLE);
            devide3.setVisibility(View.VISIBLE);
        }
        if (length > 4) {
            contactNum5.setText(split[4]);
            contactNum5.setVisibility(View.VISIBLE);
            devide4.setVisibility(View.VISIBLE);
        }
        contactNum1.setOnClickListener(onClickListener);
        contactNum2.setOnClickListener(onClickListener);
        contactNum3.setOnClickListener(onClickListener);
        contactNum4.setOnClickListener(onClickListener);
        contactNum5.setOnClickListener(onClickListener);
    }

    public static void showCallPhoneHintDialog(Activity context, View.OnClickListener onClickListener, int i) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_call_phone_hint);
        TextView textTitle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_title);
        TextView textDetail = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_detail_text);
        TextView button = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_button_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        if (i == 0) {
            textTitle.setText("使用本机号码拨号");
            textDetail.setText("使用该功能，对方手机来电显示的是您的“本机号码”。为了录音效果更加清晰该功能通过录音专线（400电话）转接，请您放心使用。");
        } else if (i == 1) {
            textTitle.setText("隐藏本机号码拨号");
            textDetail.setText("使用该功能，对方手机来电显示022开头的电话号码（特殊电信授权专线）。当您无法拨通被叫号码时，可尝试使用该功能进行录音。");
        }
    }

    public static void showAddzoneDescription(final Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
            alertDialogLogout.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            }
        });
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }

        alertDialogLogout.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialogLogout.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_add_zone);
        final EditText editText = (EditText) alertDialogLogout.getWindow().findViewById(R.id.edittext_dialog_zone);
        TextView textCancle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_cancle);
        TextView textSure = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_sure);
        editText.setFocusable(true);

        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        String zone = editText.getText().toString();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogLogout.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        });
    }

    public static void showConsutingDialog(Activity context) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_consulting_hint);
        TextView textTitle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_title);
        TextView textDetail = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_detail_text);
        TextView button = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_button_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public static void showNotSufficientFunds(Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_not_sufficient_funds);
        Button buttonSure = (Button) alertDialogLogout.getWindow().findViewById(R.id.textview_button_sure);
        Button buttonCancle = (Button) alertDialogLogout.getWindow().findViewById(R.id.textview_button_cancle);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        buttonSure.setOnClickListener(onClickListener);
    }

    public static void showConsutingHintDialog(Activity context, String text) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_consulting_hint);
        TextView textTitle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_title);
        TextView textDetail = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_detail_text);
        TextView button = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_button_sure);
        textDetail.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public static void showLawyerAppraiseDialog(Activity context) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance());
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_lawyer_appraise);
        RatingBar ratingBar = (RatingBar) alertDialogLogout.getWindow().findViewById(R.id.raringbar);
        EditText editTextAppraise = (EditText) alertDialogLogout.getWindow().findViewById(R.id.edittext_appraise);
        Button buttonSure = (Button) alertDialogLogout.getWindow().findViewById(R.id.button_sure);
        Button buttonCancle = (Button) alertDialogLogout.getWindow().findViewById(R.id.button_cancle);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public static void showConsultPayDialog(Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance());
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_consulting_pay);
        Button wexinPay = (Button) alertDialogLogout.getWindow().findViewById(R.id.button_dialog_wxpay);
        Button aliPay = (Button) alertDialogLogout.getWindow().findViewById(R.id.button_dialog_alipay);
        Button cancle = (Button) alertDialogLogout.getWindow().findViewById(R.id.button_cancle_pay);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        wexinPay.setOnClickListener(onClickListener);
        aliPay.setOnClickListener(onClickListener);
    }

    public static void showDialogSign(Activity context) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(false);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_sign);
        TextView buttonsure = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_sign_sure);
        buttonsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    public static void showDeleteFileDialog(Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_delete_file);
        TextView textSure = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_delete_file_sure);
        TextView textCancle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_delete_file_cancle);
        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        textSure.setOnClickListener(onClickListener);
    }

    public static void showLoginDialog(Activity context, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_delete_file);
        TextView textviewContent = alertDialogLogout.getWindow().findViewById(R.id.textview_content);
        textviewContent.setText("您尚未登录，请前往登录/注册");
        TextView textSure = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_delete_file_sure);
        TextView textCancle = (TextView) alertDialogLogout.getWindow().findViewById(R.id.textview_dialog_delete_file_cancle);
        textCancle.setText("稍后登录");
        textSure.setText("立即登录");
        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }

        });
        textSure.setOnClickListener(onClickListener);
    }

    public static void showPayResultDialog(Activity context, View.OnClickListener onClickListener, boolean isSuccess) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(true);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        ;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_pay_result);
        ImageView imageView = alertDialogLogout.getWindow().findViewById(R.id.imageview_pay_result);
        TextView textViewTitle = alertDialogLogout.getWindow().findViewById(R.id.textview_pay_result_title);
        TextView textViewDetail = alertDialogLogout.getWindow().findViewById(R.id.textview_pay_result_detail);
        Button buttonSure = alertDialogLogout.getWindow().findViewById(R.id.button_pay_result_sure);
        if (isSuccess) {
            textViewTitle.setText("提交成功");
            textViewDetail.setText("您的咨询已受理\n请等待律师来电");
            imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.icon_successful));
        } else {
            textViewTitle.setText("提交失败");
            textViewDetail.setText("您本次的支付未成功\n请点击【确定】按钮再次提交");
            imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.icon_fail));
        }
        buttonSure.setOnClickListener(onClickListener);
    }

    /**
     * @param context
     * @param bundle
     * @param onClickListener
     */
    public static void showPushDialog(Activity context, TencentMessageReceiver.PushModel bundle, View.OnClickListener onClickListener) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(false);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_push);
        alertDialogLogout.getWindow().setBackgroundDrawable(new ColorDrawable());
        Button buttonSure = alertDialogLogout.getWindow().findViewById(R.id.button_sure_push);
        ImageView imageview = alertDialogLogout.getWindow().findViewById(R.id.imageview_close_push);
        ImageView imageViewBG = alertDialogLogout.getWindow().findViewById(R.id.imageview_push_backgroud);
        Glide.with(context).load(bundle.picUrl).asBitmap().into(imageViewBG);
        buttonSure.setOnClickListener(onClickListener);
        imageview.setOnClickListener(onClickListener);
    }

    public static void showCallPhoneRechage(Activity context, View.OnClickListener onClickListener, String detail) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(false);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_call_phone_rechage);
        TextView textViewContent = alertDialogLogout.getWindow().findViewById(R.id.textview_detail_content);
        TextView textViewSure = alertDialogLogout.getWindow().findViewById(R.id.textview_dialog__sure);
        TextView textViewCancle = alertDialogLogout.getWindow().findViewById(R.id.textview_dialog__cancle);
        textViewContent.setText(detail);
        textViewSure.setOnClickListener(onClickListener);
        textViewCancle.setOnClickListener(onClickListener);
    }

    public static void showShareSuccessDialog(Activity context, View.OnClickListener onClickListener, String message) {
        if (alertDialogLogout == null) {
            alertDialogLogout = new AlertDialog.Builder(context).create();
        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(false);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_share_success_dialog);
        TextView textViewMessage = alertDialogLogout.getWindow().findViewById(R.id.textview_share_message);
        Button buttonSure = alertDialogLogout.getWindow().findViewById(R.id.button_share_success_sure);
        textViewMessage.setText(message);
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    /**
     * @param context
     * @param bundle
     * @param onClickListener
     */
    public static void showBeginDialog(final Activity context, final PopsBeanList.PopsBean bundle, View.OnClickListener onClickListener, final String userID) {
//        if (alertDialogLogout == null) {
        final AlertDialog alertDialogLogout = new AlertDialog.Builder(context).create();
//        }
        WindowManager.LayoutParams lp = alertDialogLogout.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.setCanceledOnTouchOutside(false);
        if (!context.isFinishing()) {
            alertDialogLogout.show();
        }
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        alertDialogLogout.getWindow().setAttributes(lp);
        alertDialogLogout.getWindow().setContentView(R.layout.dialog_begin);
        alertDialogLogout.getWindow().setBackgroundDrawable(new ColorDrawable());
        Button buttonSure = alertDialogLogout.getWindow().findViewById(R.id.button_sure_begin);
        ImageView imageview = alertDialogLogout.getWindow().findViewById(R.id.imageview_close_begin);
        ImageView imageViewBG = alertDialogLogout.getWindow().findViewById(R.id.imageview_begin_backgroud);
        Glide.with(context).load(bundle.picUrl).asBitmap().into(imageViewBG);
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialogLogout == null) {
                    return;
                } else {
                    try {
                        if (alertDialogLogout.isShowing()) {
                            alertDialogLogout.dismiss();
                        }
                    } catch (final IllegalArgumentException e) {
                        // Handle or log or ignore
                    } catch (final Exception e) {
                        // Handle or log or ignore
                    }
                }
                AccountManager.getInstance().recordScreenPop(bundle.sysScreenPopId, 1, userID);
                Intent intent = new Intent();
                intent.putExtra("url", bundle.url);
                intent.putExtra("title", bundle.title);
                context.startActivity(intent.setClass(context, WebViewActivity.class));
            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialogLogout == null) {
                    return;
                } else {
                    try {
                        if (alertDialogLogout.isShowing()) {
                            alertDialogLogout.dismiss();
                        }
                    } catch (final IllegalArgumentException e) {
                        // Handle or log or ignore
                    } catch (final Exception e) {
                        // Handle or log or ignore
                    } finally {
                    }
                }
                alertDialogLogout.dismiss();
                AccountManager.getInstance().recordScreenPop(bundle.sysScreenPopId, 0, userID);
            }
        });
    }
}
