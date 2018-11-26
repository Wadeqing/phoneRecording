package com.sinocall.phonerecordera.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.HistoryConsultResponse;
import com.sinocall.phonerecordera.event.account.CommenConsultEvent;
import com.sinocall.phonerecordera.event.account.DeleteConsultEvent;
import com.sinocall.phonerecordera.event.account.HistoryConsultEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.activity.FileDetailActivity;
import com.sinocall.phonerecordera.ui.adapter.HistoryConsultRecyclerViewAdapter;
import com.sinocall.phonerecordera.util.AppUtil;
import com.sinocall.phonerecordera.util.ToastManager;
import com.sinocall.phonerecordera.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/12/9.
 */

public class LawyerCallBackFragment extends FragmentBase {
    @BindView(R.id.recyclerview_call_lawyer)
    RecyclerView recyclerviewCallLawyer;
    @BindView(R.id.swiperefresh_call_lawyer)
    SwipeRefreshLayout swiperefreshCallLawyer;
    @BindView(R.id.linearlayout_null_data_default)
    LinearLayout linearlayoutNullDataDefault;
    Unbinder unbinder;
    private int pn = 0, ps = 20;
    private List<HistoryConsultResponse.DataBean> data = new ArrayList<>();
    private boolean isRefresh = true;
    private HistoryConsultRecyclerViewAdapter historyConsultRecyclerViewAdapter;
    private UserInfo userInfo;
    private LinearLayoutManager linearLayoutManager;
    private HistoryConsultResponse.DataBean dataBean;
    private int deletePosition;
    private int AppraisePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_lawyer_call_back, null);
        initData();
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initData() {
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getHistoryConsult(userInfo.userID, pn, ps);
        }
    }

    public void onEventMainThread(HistoryConsultEvent event) {
        swiperefreshCallLawyer.setRefreshing(false);
        if (event.code == 0) {
            HistoryConsultResponse response = event.response;
            if (isRefresh) {
                data.clear();
                data = response.data;
                initUI();
            } else {
                data.addAll(response.data);
            }
            if (data.size() > 0) {
                linearlayoutNullDataDefault.setVisibility(View.GONE);
                recyclerviewCallLawyer.setVisibility(View.VISIBLE);
                swiperefreshCallLawyer.setVisibility(View.VISIBLE);
            } else {
                linearlayoutNullDataDefault.setVisibility(View.VISIBLE);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    private void initUI() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerviewCallLawyer.setLayoutManager(linearLayoutManager);
        historyConsultRecyclerViewAdapter = new HistoryConsultRecyclerViewAdapter(data, getActivity());
        recyclerviewCallLawyer.setAdapter(historyConsultRecyclerViewAdapter);
        recyclerviewCallLawyer.addItemDecoration(new MyDividerItemDecoration(getActivity(), 1));
        swiperefreshCallLawyer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pn = 0;
                AccountManager.getInstance().getHistoryConsult(userInfo.userID, pn, ps);
            }
        });
        recyclerviewCallLawyer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == historyConsultRecyclerViewAdapter.getItemCount() && historyConsultRecyclerViewAdapter.getItemCount() > ps) {
                        isRefresh = false;
                        pn += 1;
                        AccountManager.getInstance().getDirectCallLawyerHistory(userInfo.userID, pn, ps);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

            }
        });
        historyConsultRecyclerViewAdapter.setOnItemClickLitener(new HistoryConsultRecyclerViewAdapter.OnItemClickLitener() {


            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemClickLockConsutl(View view, int position) {
                dataBean = data.get(position);
                int proofFileId = dataBean.proofFileId;
                Intent intent = new Intent();
                intent.putExtra("folderID", proofFileId + "");
                intent.putExtra("userID", userInfo.userID);
                startActivity(intent.setClass(getActivity(), FileDetailActivity.class));
            }

            @Override
            public void onItemClickDelete(View view, int position) {
                deletePosition = position;
                HistoryConsultResponse.DataBean dataBean = data.get(position);
                AccountManager.getInstance().deleteConsult(dataBean.id);
            }

            @Override
            public void onItemClickAppraise(View view, int position) {
                AppraisePosition = position;
                HistoryConsultResponse.DataBean dataBean = data.get(position);
                showLawyerAppraiseDialog(dataBean.id);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void showLawyerAppraiseDialog(final int id) {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_lawyer_appraise, null);
        dialog.setView(view);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance());
        dialog.getWindow().setAttributes(lp);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        RatingBar ratingBar = (RatingBar) dialog.getWindow().findViewById(R.id.raringbar);
        final EditText editTextAppraise = (EditText) dialog.getWindow().findViewById(R.id.edittext_appraise);
        Button buttonSure = (Button) dialog.getWindow().findViewById(R.id.button_sure);
        Button buttonCancle = (Button) dialog.getWindow().findViewById(R.id.button_cancle);
        editTextAppraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置可获得焦点
                editTextAppraise.setFocusable(true);
                editTextAppraise.setFocusableInTouchMode(true);
                //请求获得焦点
                editTextAppraise.requestFocus();
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) editTextAppraise
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editTextAppraise, 0);
            }
        });
        final int numStars = ratingBar.getNumStars();
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editTextAppraise.getText().toString();
                AccountManager.getInstance().commentConsult(id, numStars, s);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DeleteConsultEvent event) {
        if (event.code == 0) {
            initData();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    public void onEventMainThread(CommenConsultEvent event) {
        if (event.code == 0) {
            HistoryConsultResponse.DataBean dataBean = data.get(AppraisePosition);
            dataBean.commentStatus = 1;
            data.set(AppraisePosition, dataBean);
            historyConsultRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }
}
