package com.sinocall.phonerecordera.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.dao.CallLogBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 *
 */

public class CallLogRecyclerViewAdapter extends RecyclerView.Adapter<CallLogRecyclerViewAdapter.ViewHolder> {
    private FragmentActivity mContext;
    private List<CallLogBean> list = new ArrayList<>();

    public CallLogRecyclerViewAdapter(List<CallLogBean> list, FragmentActivity activity) {
        this.list = list;
        this.mContext = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.call_log_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            CallLogBean callLogBean = list.get(position);
            if (callLogBean != null && holder != null) {
                holder.textviewCallLogName.setText(callLogBean.getName());
                holder.textviewCallLogPhoneNum.setText(callLogBean.getPhoneNum());
                holder.textviewCallLogTime.setText(com.sinocall.phonerecordera.util.TimeUtils.getFormatDate(callLogBean.getId(), "yyyy-MM-dd HH:mm"));
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_call_log_name)
        TextView textviewCallLogName;
        @BindView(R.id.textview_call_log_time)
        TextView textviewCallLogTime;
        @BindView(R.id.textview_call_log_phone_num)
        TextView textviewCallLogPhoneNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
