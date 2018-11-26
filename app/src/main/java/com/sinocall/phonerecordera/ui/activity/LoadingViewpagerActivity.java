package com.sinocall.phonerecordera.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.ui.adapter.AdapterViewPager;
import com.sinocall.phonerecordera.ui.fragment.FragmentBase;
import com.sinocall.phonerecordera.ui.fragment.LoadingViewPagerFragment;
import com.sinocall.phonerecordera.ui.fragment.LoadingViewPagerLastFragment;
import com.sinocall.phonerecordera.util.StatusColorUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 15-6-19.
 */
public class LoadingViewpagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout linearLayout;
    private ArrayList<FragmentBase> fragmentBases = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_loading_viewpager);
        initUI();
    }

    private void initUI() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_loading);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout_loading_point_show);
        viewPager.addOnPageChangeListener(this);
        LoadingViewPagerFragment loadingViewPagerFragment1 = new LoadingViewPagerFragment();
        loadingViewPagerFragment1.setActivity(this);
        loadingViewPagerFragment1.setImage(R.drawable.guidepage01);
        LoadingViewPagerFragment loadingViewPagerFragment2 = new LoadingViewPagerFragment();
        loadingViewPagerFragment2.setActivity(this);
        loadingViewPagerFragment2.setImage(R.drawable.guidepage02);
        LoadingViewPagerFragment loadingViewPagerFragment3 = new LoadingViewPagerFragment();
        loadingViewPagerFragment3.setActivity(this);
        loadingViewPagerFragment3.setImage(R.drawable.guidepage03);
        LoadingViewPagerLastFragment loadingViewPagerFragment4 = new LoadingViewPagerLastFragment();
        loadingViewPagerFragment4.setActivity(this);

        fragmentBases.add(loadingViewPagerFragment1);
        fragmentBases.add(loadingViewPagerFragment2);
        fragmentBases.add(loadingViewPagerFragment3);
        fragmentBases.add(loadingViewPagerFragment4);


        AdapterViewPager adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.setFragmentBases(fragmentBases);
        viewPager.setAdapter(adapterViewPager);

        setPoints();
        selectPoints(0);
    }

    private void setPoints() {
        for (int i = 0; i < fragmentBases.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i != fragmentBases.size() - 1) {
                imageView.setPadding(0, 0, 15, 0);
            }
            linearLayout.addView(imageView);
            imageViews.add(imageView);
        }


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPoints(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void selectPoints(int id) {
        for (int i = 0; i < fragmentBases.size(); i++) {
            /*if (id == 0) {
                if (i == id) {
                    imageViews.get(i)
                            .setImageResource(R.drawable.cqd_guidepage_blue);
                } else {
                    imageViews.get(i).setImageResource(R.drawable.cqd_guidepage_gray);
                }
                linearLayout.setVisibility(View.VISIBLE);
            } else if (id == 1) {
                if (i == id) {
                    imageViews.get(i)
                            .setImageResource(R.drawable.cqd_guidepage_yellow);
                } else {
                    imageViews.get(i).setImageResource(R.drawable.cqd_guidepage_gray);
                }
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }*/

        }
    }

}
