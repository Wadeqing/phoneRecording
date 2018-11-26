package com.sinocall.phonerecordera.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.AccounDetailResponse;
import com.sinocall.phonerecordera.event.account.AccountDetailEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.AccountRecyclerViewAdapter;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.sinocall.phonerecordera.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/25.
 *
 */

public class AccountDetailActivity extends BaseActivity {
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
    @BindView(R.id.recyclerview_activity_account_detail)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_activity_account_detail)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.linearlayout_record_default)
    LinearLayout linearlayoutRecordDefault;
    private List<AccounDetailResponse.DataBean> data = new ArrayList<>();
    private int pn = 0;
    private int ps = 20;
    private boolean isRefresh = true;
    private UserInfo userInfo;
    private AccountRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        inttUI();
    }

    private void initData() {
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getAccountDetail(userInfo.userID, pn, ps);
        }
    }

    private void inttUI() {
        textviewTitle.setText("消费明细");
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new AccountRecyclerViewAdapter(data, this);
        mRecyclerview.setAdapter(recyclerViewAdapter);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (data.size() >= ps) {
                    simulateLoadMoreData();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pn = 0;
                AccountManager.getInstance().getAccountDetail(userInfo.userID, pn, ps);
            }
        });
    }

    private void simulateLoadMoreData() {
        isRefresh = false;
        pn += 1;
        AccountManager.getInstance().getAccountDetail(userInfo.userID, pn, ps);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(AccountDetailEvent event) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (event.code == 0) {
            if (isRefresh) {
                data.clear();
                data = event.accounDetailResponse.data;
                inttUI();
            } else {
                data.addAll(event.accounDetailResponse.data);
                recyclerViewAdapter.notifyDataSetChanged();
            }
            if (data.size() > 0) {
                linearlayoutRecordDefault.setVisibility(View.GONE);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
