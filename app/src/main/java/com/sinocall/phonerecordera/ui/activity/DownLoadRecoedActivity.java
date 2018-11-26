package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.dao.FileDownloadBean;
import com.sinocall.phonerecordera.greendao.gen.DaoSession;
import com.sinocall.phonerecordera.greendao.gen.FileDownloadBeanDao;
import com.sinocall.phonerecordera.ui.adapter.DownLoadRecordingRecyclerViewAdapter;
import com.sinocall.phonerecordera.util.StatusColorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author l
 */

public class DownLoadRecoedActivity extends BaseActivity {
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
    @BindView(R.id.download_recyclerview)
    RecyclerView downloadRecyclerview;

    @BindView(R.id.swipe_activity_download)
    SwipeRefreshLayout swipeActivityDownload;
    @BindView(R.id.linearlayout_download_default)
    LinearLayout linearlayoutDownloadDefault;
    private DaoSession daoSession;
    private List<FileDownloadBean> fileList = new ArrayList<>();
    private DownLoadRecordingRecyclerViewAdapter recordingRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_download_record);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        FileDownloadBeanDao fileDownloadBeanDao = daoSession.getFileDownloadBeanDao();
        fileList = fileDownloadBeanDao.queryBuilder().build().list();
        initUI();
    }

    private void initUI() {
        textviewTitle.setText("下载记录");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        if (fileList != null && fileList.size() > 0) {
            linearlayoutDownloadDefault.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            downloadRecyclerview.setLayoutManager(linearLayoutManager);
            recordingRecyclerViewAdapter = new DownLoadRecordingRecyclerViewAdapter(fileList, this);
            downloadRecyclerview.setAdapter(recordingRecyclerViewAdapter);

            recordingRecyclerViewAdapter.setOnItemClickLitener(new DownLoadRecordingRecyclerViewAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    FileDownloadBean dataBean = fileList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("folderID", dataBean.getFid() + "");
                    intent.putExtra("isDownLoad", 1);
                    startActivityForResult(intent.setClass(DownLoadRecoedActivity.this, FileDetailActivity.class), 9999);
                }

                @Override
                public void onItemDownLoadClick(View view, int position) {

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        } else {
            linearlayoutDownloadDefault.setVisibility(View.VISIBLE);
        }
        swipeActivityDownload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fileList.clear();
                swipeActivityDownload.setRefreshing(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20180116) {
            fileList.clear();
            initData();
        }
    }
}
