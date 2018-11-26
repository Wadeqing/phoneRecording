package com.sinocall.phonerecordera.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.LawConsultResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mikeafc on 15/11/26.
 */

public class LawyerUltraPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private Activity activity;
    public List<LawConsultResponse.DataBean.RecommendLawsBean> list;
    private boolean isMultiScr;

    public LawyerUltraPagerAdapter(Activity activity, boolean isMultiScr, List<LawConsultResponse.DataBean.RecommendLawsBean> list) {
        this.isMultiScr = isMultiScr;
        this.list = list;
        this.activity = activity;
    }

    public LawyerUltraPagerAdapter(boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
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
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child_laywer, null);
        ImageView imageview = (ImageView) linearLayout.findViewById(R.id.imageview_laywe_icon);
        TextView layName = (TextView) linearLayout.findViewById(R.id.textview_laywe_name);
        TextView LayFirm = (TextView) linearLayout.findViewById(R.id.textview_laywe_uifirm);
        TextView laySign = (TextView) linearLayout.findViewById(R.id.textview_laywer_uisignature);
        if (list != null && list.size() > 0) {
            LawConsultResponse.DataBean.RecommendLawsBean item = list.get(position);
            Glide.with(container.getContext())
                    .load(item.UIPic)
//                    .transform(new GlideRoundTransform(container.getContext()))
                    .into(imageview);
            layName.setText(item.UIName);
            LayFirm.setText(item.UIFirm);
            laySign.setText(item.UISignature);
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


    public void setList(ArrayList<LawConsultResponse.DataBean.RecommendLawsBean> list) {
        this.list = list;
    }
}
