package com.sinocall.phonerecordera.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sinocall.phonerecordera.widget.LoadingDialog;

/**
 * Created by qingchao on 2017/11/23.
 */

public class FragmentBase extends Fragment implements View.OnClickListener {
    // 视图化的对象
    public View contentView;
    private LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return contentView;
    }

    public void setContentView(int res) {
        if (contentView == null) {
            contentView = LinearLayout.inflate(getActivity(), res, null);
        }
    }

    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    public void onRefresh() {

    }


    public void startActivity(Class<?> clazz) {
        getActivity().startActivity(new Intent(getActivity(), clazz));
    }

    public void startActivityForResult(Class<?> clazz, int code) {
        getActivity().startActivityForResult(new Intent(getActivity(), clazz),
                code);
    }

    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public void startActivityForResult(Class<?> clazz, Bundle bundle, int code) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected void showLoading(String msg) {
        dismissLoading();
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.setTips(msg);
        mLoadingDialog.show();
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
