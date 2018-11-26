package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
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
import com.sinocall.phonerecordera.api.account.MessageListResponse;
import com.sinocall.phonerecordera.event.account.DeleteMessageEvent;
import com.sinocall.phonerecordera.event.account.MessageListEvent;
import com.sinocall.phonerecordera.event.account.UpdataMessageEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.MessageRecyclerViewAdapter;
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
 * Created by qingchao on 2017/11/25.
 *
 */

public class MessageCenterActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.message_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.message_swiperefresh)
    SwipeRefreshLayout mSwiperefresh;
    @BindView(R.id.linearlayout_message_default)
    LinearLayout linearlayoutMessageDefault;
    private List<MessageListResponse.DataBean> data = new ArrayList<>();
    private UserInfo userInfo;
    private MessageRecyclerViewAdapter messageRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initUI();
        initData();
    }

    private void initData() {
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getMessageList(userInfo.userID);
        }
    }

    private void initUI() {
        textviewTitle.setText("消息中心");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(data, this);
        mRecyclerview.setAdapter(messageRecyclerViewAdapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, 0));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        messageRecyclerViewAdapter.setOnItemClickLitener(new MessageRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                MessageListResponse.DataBean dataBean = data.get(position);
                if ("1".equals(dataBean.type)) {
                    Intent intent = new Intent(MessageCenterActivity.this, WebViewActivity.class);
                    intent.putExtra("url", dataBean.url);
                    intent.putExtra("title", dataBean.title);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MessageCenterActivity.this, MessageDetailActivity.class);
                    intent.putExtra("time",dataBean.createTime);
                    intent.putExtra("content",dataBean.msgInfo);
                    startActivity(intent);
                }
                AccountManager.getInstance().getMessageRead(userInfo.userID, dataBean.sysmsgId);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                MessageListResponse.DataBean dataBean = data.get(position);
                showDeleteDialog(userInfo.userID, dataBean.sysmsgId);
                if (data.size() > position) {
                    data.remove(position);
                }
            }
        });
    }

    private void showDeleteDialog(String userID, String sysmsgId) {
        DialogUtils.showDeleteMessageDialog(this, this, userID, sysmsgId);
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

    public void onEventMainThread(MessageListEvent event) {
        mSwiperefresh.setRefreshing(false);
        if (event.code == 0) {
            data = event.messageListResponse.data;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
        if (data.size() > 0) {
            linearlayoutMessageDefault.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(DeleteMessageEvent event) {
        if (event.code == 0) {
            messageRecyclerViewAdapter.notifyDataSetChanged();
            if (data.size() == 0) {
                linearlayoutMessageDefault.setVisibility(View.VISIBLE);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(UpdataMessageEvent event) {
        if (event.code == 0) {

        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
