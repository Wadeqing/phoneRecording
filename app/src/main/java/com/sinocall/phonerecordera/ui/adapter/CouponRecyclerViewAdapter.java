package com.sinocall.phonerecordera.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.model.bean.CouponBean;
import com.sinocall.phonerecordera.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class CouponRecyclerViewAdapter extends RecyclerView.Adapter<CouponRecyclerViewAdapter.ViewHolder> {
    private BaseActivity mContext;
    private List<CouponBean> list = new ArrayList<>();
    private View inflate;

    public CouponRecyclerViewAdapter(List<CouponBean> data, BaseActivity mineCouponActivity) {
        this.mContext = mineCouponActivity;
        this.list = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflate = LayoutInflater.from(mContext).inflate(R.layout.coupon_list_item, parent, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            CouponBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                holder.textviewCounponTime.setText(dataBean.amount + "");
                holder.textciewCouponPeriodOfValidity.setText(dataBean.createTime);
                holder.textviewCouponDesc.setText(dataBean.couponDesc);
                if (dataBean.couponType == 1) {
                    holder.textviewCouponType.setText("分钟");
                } else {
                    holder.textviewCouponType.setText("个录音币");
                }
                if (dataBean.status == 0) {
                    holder.textviewCouponStatus.setText("未使用");
                    setSelected(holder.lineaylayoutCoupon, false);
                } else if (dataBean.status == 1) {
                    holder.textviewCouponStatus.setText("已使用");
                    setSelected(holder.lineaylayoutCoupon, true);
                } else if (dataBean.status == 3) {
                    holder.textviewCouponStatus.setText("已过期");
                    setSelected(holder.lineaylayoutCoupon, true);
                } else if (dataBean.status == 4) {
                    holder.textviewCouponStatus.setText("已失效");
                    setSelected(holder.lineaylayoutCoupon, true);
                }
            }
        }

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null && holder != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_counpon_time)
        TextView textviewCounponTime;
        @BindView(R.id.textview_coupon_desc)
        TextView textviewCouponDesc;
        @BindView(R.id.textciew_coupon_period_of_validity)
        TextView textciewCouponPeriodOfValidity;
        @BindView(R.id.textview_coupon_status)
        TextView textviewCouponStatus;
        @BindView(R.id.lineaylayout_coupon)
        LinearLayout lineaylayoutCoupon;
        @BindView(R.id.textview_coupon_type)
        TextView textviewCouponType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setSelected(View view, boolean b) {
        view.setSelected(b);
        if (view instanceof ViewGroup) {
            //view转换成viewGroup后孩子节点的总数
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                setSelected(viewChild, b);
            }
        }
    }

}
