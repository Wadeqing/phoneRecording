package com.sinocall.phonerecordera.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.ui.fragment.NotarizationFragment;
import com.sinocall.phonerecordera.ui.fragment.SaveEvidenceFragment;
import com.sinocall.phonerecordera.util.StatusColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qingchao on 2017/11/25.
 */

public class EvidenceManagerActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.framelayout_evidence_manager)
    FrameLayout framelayoutEvidenceManager;
    @BindView(R.id.textview_notarization_file)
    TextView textviewNotarizationFile;
    @BindView(R.id.textview_save_file)
    TextView textviewSaveFile;
    @BindView(R.id.linearlayout_tab)
    LinearLayout linearlayoutTab;
    private FragmentTransaction fragmentTransaction;
    private NotarizationFragment notarizationFragment;
    private SaveEvidenceFragment saveEvidenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_evidence_manager);
        ButterKnife.bind(this);
        int fragment = getIntent().getIntExtra("fragment", 1);
        initUI(fragment);

    }

    private void initUI(int fragment) {
        textviewTitle.setText("证据管理");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        textviewNotarizationFile.setSelected(true);
        textviewSaveFile.setSelected(false);
        if (fragment == 0) {
            changeFragment(textviewNotarizationFile);
        } else {
            changeFragment(textviewSaveFile);
        }

    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.textview_notarization_file, R.id.textview_save_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.textview_notarization_file:
                changeFragment(textviewNotarizationFile);
                break;
            case R.id.textview_save_file:
                changeFragment(textviewSaveFile);
                break;
            default:
                break;
        }
    }

    private void changeFragment(View view) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (notarizationFragment != null) {
            fragmentTransaction.hide(notarizationFragment);
        }
        if (saveEvidenceFragment != null) {
            fragmentTransaction.hide(saveEvidenceFragment);
        }
        switch (view.getId()) {
            case R.id.textview_notarization_file:
                if (notarizationFragment == null) {
                    notarizationFragment = new NotarizationFragment();
                    fragmentTransaction.add(R.id.framelayout_evidence_manager, notarizationFragment);
                } else {
                    fragmentTransaction.show(notarizationFragment);
                }
                textviewNotarizationFile.setSelected(true);
                textviewSaveFile.setSelected(false);
                linearlayoutTab.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.mine_tab_left_selected));
                break;
            case R.id.textview_save_file:
                if (saveEvidenceFragment == null) {
                    saveEvidenceFragment = new SaveEvidenceFragment();
                    fragmentTransaction.add(R.id.framelayout_evidence_manager, saveEvidenceFragment);
                } else {
                    fragmentTransaction.show(saveEvidenceFragment);
                }
                textviewNotarizationFile.setSelected(false);
                textviewSaveFile.setSelected(true);
                linearlayoutTab.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.mine_tab_right_selected));
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

}
