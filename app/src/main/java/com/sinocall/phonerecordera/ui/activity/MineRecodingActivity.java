package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
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
import com.sinocall.phonerecordera.api.account.EvidenceListResponse;
import com.sinocall.phonerecordera.api.account.EvidenceRootListResponse;
import com.sinocall.phonerecordera.event.account.EvidenceListEvent;
import com.sinocall.phonerecordera.event.account.EvidenceRootListEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.RecordingRecyclerViewAdapter;
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
 */

public class MineRecodingActivity extends BaseActivity {
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
    @BindView(R.id.recyclerview_activity_mine_recording)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_mine_record)
    SwipeRefreshLayout swipeMineRecord;
    @BindView(R.id.linearlayout_recording_default)
    LinearLayout linearlayoutRecordingDefault;
    private int pn = 0, ps = 20;
    private boolean isRefresh = true;
    private RecordingRecyclerViewAdapter recyclerViewAdapter;
    private UserInfo userInfo;
    private String folderID;
    private List<EvidenceListResponse.DataBean> data = new ArrayList<>();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_mine_recording);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initUI();
        initData();
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getMineRecordingRoot(userInfo.userID);
        }
    }

    private void initUI() {
        textviewTitle.setText("我的录音");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new RecordingRecyclerViewAdapter(data, this);
        mRecyclerview.setAdapter(recyclerViewAdapter);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        /*mRecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                isRefresh = false;
                pn += 1;
                if (data.size() >= pn * ps) {
                    AccountManager.getInstance().getMineRecording(userInfo.userID, folderID, type, pn, ps);
                }
            }
        });*/
        swipeMineRecord.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pn = 0;
                AccountManager.getInstance().getMineRecording(userInfo.userID, folderID, type, pn, ps);
            }
        });
        recyclerViewAdapter.setOnItemClickLitener(new RecordingRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                EvidenceListResponse.DataBean dataBean = data.get(position);
                Intent intent = new Intent();
                intent.putExtra("folderID", dataBean.folderID);
                intent.putExtra("userID", userInfo.userID);
                intent.putExtra("verifyStatus", Integer.parseInt(dataBean.verifyStatus));
                startActivity(intent.setClass(MineRecodingActivity.this, FileDetailActivity.class));
            }

            @Override
            public void onItemDownLoadClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

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

    public void onEventMainThread(EvidenceRootListEvent event) {
        if (event.code == 0) {
            EvidenceRootListResponse evidenceRootListResponse = event.evidenceRootListResponse;
            List<EvidenceRootListResponse.DataBean> data = evidenceRootListResponse.data;
            for (int i = 0; i < data.size(); i++) {
                if ("1".equals(data.get(i).type)) {
                    folderID = data.get(i).folderID;
                    AccountManager.getInstance().getMineRecording(userInfo.userID, folderID, type, pn, ps);
                }
            }
        } else if (!"".equals(event.message)) {
            ToastManager.show(this, event.message);
        }

    }

    public void onEventMainThread(EvidenceListEvent event) {
        swipeMineRecord.setRefreshing(false);
        if (event.code == 0) {
            EvidenceListResponse evidenceListResponse = event.evidenceListResponse;
            if (isRefresh) {
                data = evidenceListResponse.data;
                initUI();
            } else {
                data.addAll(evidenceListResponse.data);
                recyclerViewAdapter.notifyDataSetChanged();
            }
            if (data.size() > 0) {
                linearlayoutRecordingDefault.setVisibility(View.GONE);
            }
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
