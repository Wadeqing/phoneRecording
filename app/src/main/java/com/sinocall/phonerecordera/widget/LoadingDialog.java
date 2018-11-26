package com.sinocall.phonerecordera.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;

/**
 * Created by qingchao on 2017/11/23.
 */

public class LoadingDialog extends Dialog {

    private TextView mTipsTv;
    private ProgressBar mProgressBar;

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialogStyle);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        setCanceledOnTouchOutside(false);
        setCancelable(true);

        setContentView(R.layout.dialog_loading);
        mTipsTv = (TextView) findViewById(R.id.tips);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTipsTv.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void setTips(int resId) {
        mTipsTv.setVisibility(View.VISIBLE);
        mTipsTv.setText(resId);
    }

    public void setTips(String tips) {
        if (null != tips) {
            mTipsTv.setVisibility(View.VISIBLE);
            mTipsTv.setText(tips);
        }

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
