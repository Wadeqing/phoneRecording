package com.sinocall.phonerecordera.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.BannerResponse;
import com.sinocall.phonerecordera.widget.GlideRoundTransform;

import java.util.List;


public class BannerUltraPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private Activity activity;
    public List<BannerResponse.DataBean> list;

    public BannerUltraPagerAdapter(Activity preferentialActivity, List<BannerResponse.DataBean> bannerList) {
        this.activity = preferentialActivity;
        this.list = bannerList;
    }


    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_banner, null);
        ImageView imageview = (ImageView) linearLayout.findViewById(R.id.imageview_banner);
        if (list != null && list.size() > 0) {
            BannerResponse.DataBean item = list.get(position);
            Glide.with(container.getContext())
                    .load(item.imageUrl)
                    .transform(new GlideRoundTransform(container.getContext(), 6))
                    .into(imageview);
        }
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }

    @Override
    public void onClick(View v) {

    }


}
