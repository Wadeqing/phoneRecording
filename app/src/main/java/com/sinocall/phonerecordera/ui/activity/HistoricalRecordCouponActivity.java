package com.sinocall.phonerecordera.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.CouponHistoricalResponse;
import com.sinocall.phonerecordera.event.account.CouponHistoricalEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.CouponBean;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.CouponRecyclerViewAdapter;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/12/1.
 */

public class HistoricalRecordCouponActivity extends BaseActivity {
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
    @BindView(R.id.historical_coupon_recyclerview)
    RecyclerView historicalCouponRecyclerview;
    @BindView(R.id.historical_coupon_swiperefresh)
    SwipeRefreshLayout historicalCouponSwiperefresh;
    private List<CouponBean> data = new ArrayList<>();
    private CouponRecyclerViewAdapter couponRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_historical_record_coupon);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initUI();
    }

    private void initData() {
        if (AccountManager.isLogined()) {
            UserInfo userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getHistorialPreferential(userInfo.userID);
        }
    }

    private void initUI() {
        textviewTitle.setText("历史记录");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        historicalCouponRecyclerview.setLayoutManager(linearLayoutManager);
        couponRecyclerViewAdapter = new CouponRecyclerViewAdapter(data, this);
        historicalCouponRecyclerview.setAdapter(couponRecyclerViewAdapter);
        historicalCouponRecyclerview.setItemAnimator(new DefaultItemAnimator());
        historicalCouponRecyclerview.addItemDecoration(new DividerItemDecoration(this, 0));
        historicalCouponSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
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

    public void onEventMainThread(CouponHistoricalEvent event) {
        historicalCouponSwiperefresh.setRefreshing(false);
        if (event.code == 0) {
            CouponHistoricalResponse response = event.response;
            data = response.data;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }
}
