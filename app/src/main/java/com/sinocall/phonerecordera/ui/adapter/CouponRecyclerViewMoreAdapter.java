package com.sinocall.phonerecordera.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.model.bean.CouponBean;
import com.sinocall.phonerecordera.ui.activity.BaseActivity;
import com.sinocall.phonerecordera.ui.activity.HistoricalRecordCouponActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class CouponRecyclerViewMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private BaseActivity mContext;
    private List<CouponBean> list = new ArrayList<>();
    private View inflate;

    public CouponRecyclerViewMoreAdapter(List<CouponBean> data, BaseActivity mineCouponActivity) {
        this.mContext = mineCouponActivity;
        this.list = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            inflate = LayoutInflater.from(mContext).inflate(R.layout.coupon_list_item, parent, false);
            return new ItemViewHolder(inflate);
        } else {
            inflate = LayoutInflater.from(mContext).inflate(R.layout.coupon_list_item_textview, parent, false);
            View viewById = inflate.findViewById(R.id.textview_lock_past_records);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent().setClass(mContext, HistoricalRecordCouponActivity.class));
                }
            });
            return new FootViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {
        if (list != null && list.size() > 0 && list.size() > position && holder1 instanceof ItemViewHolder) {
            CouponBean dataBean = list.get(position);
            ItemViewHolder holder = (ItemViewHolder) holder1;
            holder.textviewCounponTime.setText(dataBean.amount + "");
            holder.textciewCouponPeriodOfValidity.setText(dataBean.createTime);
            holder.textviewCouponDesc.setText(dataBean.couponDesc);
            if (dataBean.couponType == 1) {
                holder.textviewCouponType.setText(dataBean.unit);
            } else {
                holder.textviewCouponType.setText(dataBean.unit);
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
        } else if (list == null || list.size() == position) {

        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null && holder1 != null) {
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder1.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder1.itemView, pos);
                }
            });

            holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder1.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder1.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textviewCounponTime;
        TextView textviewCouponDesc;
        TextView textciewCouponPeriodOfValidity;
        TextView textviewCouponStatus;
        LinearLayout lineaylayoutCoupon;
        TextView textviewCouponType, textviewLockPastRecords;

        ItemViewHolder(View view) {
            super(view);
            textviewCounponTime = view.findViewById(R.id.textview_counpon_time);
            textviewCouponDesc = view.findViewById(R.id.textview_coupon_desc);
            textciewCouponPeriodOfValidity = view.findViewById(R.id.textciew_coupon_period_of_validity);
            textviewCouponStatus = view.findViewById(R.id.textview_coupon_status);
            textviewCouponType = view.findViewById(R.id.textview_coupon_type);
            lineaylayoutCoupon = view.findViewById(R.id.lineaylayout_coupon);

            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        TextView textviewLockPastRecords;

        FootViewHolder(View view) {
            super(view);
            textviewLockPastRecords = view.findViewById(R.id.textview_lock_past_records);
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

    @Override
    public int getItemViewType(int position) {
        if (list == null || position == list.size()) {
            return 1;
        } else {
            return 0;
        }
    }
}
