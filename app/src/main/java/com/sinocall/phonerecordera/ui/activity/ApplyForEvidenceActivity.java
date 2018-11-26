package com.sinocall.phonerecordera.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.util.StatusColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qingchao on 2017/11/25.
 */

public class ApplyForEvidenceActivity extends BaseActivity {
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
    @BindView(R.id.linearlayout_evidence_manager)
    LinearLayout linearlayoutEvidenceManager;
    @BindView(R.id.linearlayout_apply_for_evidence)
    LinearLayout linearlayoutApplyForEvidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_apply_for_evidence);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        textviewTitle.setText("存证服务");
        textviewTitle.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.linearlayout_evidence_manager, R.id.linearlayout_apply_for_evidence})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.linearlayout_evidence_manager:
                startActivity(new Intent().setClass(this, EvidenceManagerActivity.class));
                break;
            case R.id.linearlayout_apply_for_evidence:
                Intent intent = new Intent();
                intent.putExtra("type", 1);
                startActivity(intent.setClass(this, MineRecodingActivity.class));
                break;
            default:
                break;
        }
    }
}
