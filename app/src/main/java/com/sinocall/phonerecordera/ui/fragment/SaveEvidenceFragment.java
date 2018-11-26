package com.sinocall.phonerecordera.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.UserVerifyFilesResponse;
import com.sinocall.phonerecordera.event.account.UserVerifyFilesEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.activity.EvidenceManagerActivity;
import com.sinocall.phonerecordera.ui.adapter.UserVerifyFilesRecyclerViewAdapter;
import com.sinocall.phonerecordera.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/12/18.
 *
 */

public class SaveEvidenceFragment extends FragmentBase {
    @BindView(R.id.recyclerview_fragment_save)
    RecyclerView recyclerviewFragmentSave;
    @BindView(R.id.swiperefresh_fragment_save)
    SwipeRefreshLayout swiperefreshFragmentSave;
    @BindView(R.id.linearlayout_save_evidence)
    LinearLayout linearlayoutSaveEvidence;
    Unbinder unbinder;
    private UserInfo userInfo;
    private int pn = 0, ps = 20;
    private List<UserVerifyFilesResponse.DataBean> data = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private UserVerifyFilesRecyclerViewAdapter userVerifyFilesRecyclerViewAdapter;
    private boolean isRefresh = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_save_evidence, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initSwipeRefresh();
        initData();
        showLoading(null);
        return view;
    }

    private void initData() {
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getUserVerifyFiles(userInfo.userID, pn, ps);
        }
    }

    private void initSwipeRefresh() {
        swiperefreshFragmentSave.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                AccountManager.getInstance().getUserVerifyFiles(userInfo.userID, 0, ps);
            }
        });
        recyclerviewFragmentSave.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == userVerifyFilesRecyclerViewAdapter.getItemCount() && userVerifyFilesRecyclerViewAdapter.getItemCount() >= ps) {
                        isRefresh = false;
                        pn += 1;
                        AccountManager.getInstance().getUserVerifyFiles(userInfo.userID, pn, ps);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    public void onEventMainThread(UserVerifyFilesEvent event) {
        dismissLoading();
        swiperefreshFragmentSave.setRefreshing(false);
        if (event.code == 0) {
            if (isRefresh) {
                data.clear();
                data = event.response.data;
            } else {
                data.addAll(event.response.data);
            }
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(getActivity(), event.message);
            }
        }
    }

    private void initUI() {
        if (data != null && data.size() > 0) {
            linearlayoutSaveEvidence.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerviewFragmentSave.setLayoutManager(linearLayoutManager);
            userVerifyFilesRecyclerViewAdapter = new UserVerifyFilesRecyclerViewAdapter((EvidenceManagerActivity) getActivity(), data);
            recyclerviewFragmentSave.setAdapter(userVerifyFilesRecyclerViewAdapter);
            recyclerviewFragmentSave.setItemAnimator(new DefaultItemAnimator());
        } else {
            linearlayoutSaveEvidence.setVisibility(View.VISIBLE);
        }
    }
}
