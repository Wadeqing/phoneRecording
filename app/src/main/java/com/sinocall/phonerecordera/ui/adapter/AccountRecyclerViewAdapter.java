package com.sinocall.phonerecordera.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.AccounDetailResponse;
import com.sinocall.phonerecordera.ui.activity.AccountDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder> {
    private AccountDetailActivity mContext;
    private List<AccounDetailResponse.DataBean> list = new ArrayList<>();

    public AccountRecyclerViewAdapter(List<AccounDetailResponse.DataBean> data, AccountDetailActivity accountDetailActivity) {
        this.list = data;
        this.mContext = accountDetailActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.account_detail_item, parent, false);
        return new AccountRecyclerViewAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final AccountRecyclerViewAdapter.ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            AccounDetailResponse.DataBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                holder.textviewAccountBalance.setText(dataBean.remainCoin + "");
                holder.textviewAccountDate.setText(dataBean.createTime.month + "-" + dataBean.createTime.day);
                holder.textviewAccountTime.setText(dataBean.createTime.hours + ":" + ((dataBean.createTime.minutes < 10) ? ("0" + dataBean.createTime.minutes) : dataBean.createTime.minutes));
                holder.textviewAccountUse.setText(dataBean.reason);

                if ("".equals(dataBean.couponAmount)) {
                    if (dataBean.type >= 10) {
                        holder.textviewAccountNum.setText("+" + dataBean.num + "录音币");
                    } else {
                        holder.textviewAccountNum.setText("-" + dataBean.num + "录音币");
                    }
                } else {
                    if (dataBean.num == 0) {
                        holder.textviewAccountNum.setText(dataBean.couponAmount);
                    } else {
                        if (dataBean.type >= 10) {
                            holder.textviewAccountNum.setText("+" + dataBean.num + "录音币," + dataBean.couponAmount);
                        } else {
                            holder.textviewAccountNum.setText("-" + dataBean.num + "录音币," + dataBean.couponAmount);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_account_date)
        TextView textviewAccountDate;
        @BindView(R.id.textview_account_time)
        TextView textviewAccountTime;
        @BindView(R.id.textview_account_num)
        TextView textviewAccountNum;
        @BindView(R.id.textview_account_use)
        TextView textviewAccountUse;
        @BindView(R.id.textview_account_balance)
        TextView textviewAccountBalance;
        @BindView(R.id.textview_account_balancetext)
        TextView textviewAccountBalancetext;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
