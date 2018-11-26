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
import com.sinocall.phonerecordera.api.account.MineCouponResponse;
import com.sinocall.phonerecordera.event.account.MineCouponEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.CouponBean;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.CouponRecyclerViewMoreAdapter;
import com.sinocall.phonerecordera.util.DialogUtils;
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

public class MineCouponActivity extends BaseActivity {
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
    @BindView(R.id.mine_coupon_recyclerview)
    RecyclerView mineCouponRecyclerview;
    @BindView(R.id.mine_coupon_swiperefresh)
    SwipeRefreshLayout mineCouponSwiperefresh;
    @BindView(R.id.linearlayout_coupons_default)
    LinearLayout linearlayoutCouponsDefault;
    private List<CouponBean> data = new ArrayList<>();
    private CouponRecyclerViewMoreAdapter couponRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_mine_coupon);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initUI();
        initData();
    }

    private void initData() {
        UserInfo userInfo = AccountManager.getUserInfo();
        AccountManager.getInstance().getMineCouPon(userInfo.userID);
    }

    private void initUI() {
        textviewTitle.setText("优惠券");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        linearlayoutViewTitleSetting.setVisibility(View.VISIBLE);
        textviewTitleRight.setText("规则");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mineCouponRecyclerview.setLayoutManager(linearLayoutManager);
        couponRecyclerViewAdapter = new CouponRecyclerViewMoreAdapter(data, this);
        mineCouponRecyclerview.setAdapter(couponRecyclerViewAdapter);
        mineCouponRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mineCouponRecyclerview.addItemDecoration(new DividerItemDecoration(this, 0));
        mineCouponSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        if (data.size() > 0) {
            linearlayoutCouponsDefault.setVisibility(View.GONE);
            mineCouponRecyclerview.setVisibility(View.VISIBLE);
        } else {
            linearlayoutCouponsDefault.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back,
             R.id.textview_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.textview_title_right:
                DialogUtils.showCouponRuleDialog(this, this);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MineCouponEvent event) {
        mineCouponSwiperefresh.setRefreshing(false);
        if (event.code == 0) {
            MineCouponResponse mineCouponResponse = event.mineCouponResponse;
            if (mineCouponResponse != null) {
                data = mineCouponResponse.data;
                initUI();
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }
}
