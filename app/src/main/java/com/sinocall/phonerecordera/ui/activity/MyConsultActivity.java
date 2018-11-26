package com.sinocall.phonerecordera.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.ui.fragment.CallLawyerFragment;
import com.sinocall.phonerecordera.ui.fragment.LawyerCallBackFragment;
import com.sinocall.phonerecordera.util.StatusColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qingchao on 2017/12/8.
 */

public class MyConsultActivity extends BaseActivity {
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
    @BindView(R.id.linearlayout_call_law)
    LinearLayout linearlayoutCallLaw;
    @BindView(R.id.linearlayout_law_call_back)
    LinearLayout linearlayoutLawCallBack;
    @BindView(R.id.framelayout_my_consult)
    FrameLayout framelayoutMyConsult;
    @BindView(R.id.lineaylayout_my_consult)
    LinearLayout lineaylayoutMyConsult;
    private FragmentTransaction fragmentTransaction;
    private CallLawyerFragment callLawyerFragment;
    private LawyerCallBackFragment lawyerCallBackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_my_consult);
        ButterKnife.bind(this);
        View childAt = lineaylayoutMyConsult.getChildAt(0);
        onClick(childAt);
        textviewTitle.setText("我的咨询");
        linearlayoutLawCallBack.setVisibility(View.VISIBLE);
        imageviewTitleLeft.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.linearlayout_view_title_back, R.id.linearlayout_call_law, R.id.linearlayout_law_call_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.linearlayout_call_law:
                onClick(linearlayoutCallLaw);
                break;
            case R.id.linearlayout_law_call_back:
                onClick(linearlayoutLawCallBack);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        //提供点中控所在线性布局容器中的索引位置
        int indexOfChild = lineaylayoutMyConsult.indexOfChild(view);
        //1.切换选中FrameLayout内部孩子节点的颜色
        changeUI(indexOfChild);
        //2.切换Fragment(管理中心，首页，个人中心)
        changeFragment(indexOfChild);

    }

    private void changeUI(int indexOfChild) {
        for (int i = 0; i < lineaylayoutMyConsult.getChildCount(); i++) {
            View view = lineaylayoutMyConsult.getChildAt(i);
            if (i == indexOfChild) {
                setSelecte(view, true);
            } else {
                setSelecte(view, false);
            }
        }
    }

    private void setSelecte(View view, boolean b) {
        view.setSelected(b);
        //view是否能够转换成viewGroup判断
        if (view instanceof ViewGroup) {
            //view转换成viewGroup后孩子节点的总数
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                setSelecte(viewChild, b);
            }
        }
    }

    private void changeFragment(int indexOfChild) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (callLawyerFragment != null) {
            fragmentTransaction.hide(callLawyerFragment);
        }
        if (lawyerCallBackFragment != null) {
            fragmentTransaction.hide(lawyerCallBackFragment);
        }

        switch (indexOfChild) {
            case 0:
                if (callLawyerFragment == null) {
                    callLawyerFragment = new CallLawyerFragment();
                    fragmentTransaction.add(R.id.framelayout_my_consult, callLawyerFragment);
                } else {
                    fragmentTransaction.show(callLawyerFragment);
                }
                break;

            case 1:
                if (lawyerCallBackFragment == null) {
                    lawyerCallBackFragment = new LawyerCallBackFragment();
                    fragmentTransaction.add(R.id.framelayout_my_consult, lawyerCallBackFragment);
                } else {
                    fragmentTransaction.show(lawyerCallBackFragment);
                }
                break;

            default:
                break;
        }
        fragmentTransaction.commit();
    }
}
