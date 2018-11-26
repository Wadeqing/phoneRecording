package com.sinocall.phonerecordera.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.DirectCallLawyerHistoryResponse;
import com.sinocall.phonerecordera.util.FileSizeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 *
 */

public class CallLawyerRecyclerViewAdapter extends RecyclerView.Adapter<CallLawyerRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<DirectCallLawyerHistoryResponse.DataBean> list = new ArrayList<>();

    public CallLawyerRecyclerViewAdapter(List<DirectCallLawyerHistoryResponse.DataBean> data, Context mContext) {
        this.list = data;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.call_lawyer_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            DirectCallLawyerHistoryResponse.DataBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                holder.textviewRecordName.setText(dataBean.fileName);
                holder.textviewRecordTime.setText(dataBean.updateTime);
                holder.textviewRecordSize.setText(FileSizeUtil.getPrintSize(dataBean.size));
                if (dataBean.commentStatus == 1) {
                    holder.imageviewAppraiseCallLawyer.setBackgroundResource(R.drawable.consulting_btn_appraised_default);
                    holder.imageviewAppraiseCallLawyer.setEnabled(false);
                } else {
                    holder.imageviewAppraiseCallLawyer.setBackgroundResource(R.drawable.consulting_btn_appraise_default);
                    holder.imageviewAppraiseCallLawyer.setEnabled(true);
                }
            }
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null && holder != null) {
            holder.imageviewAppraiseCallLawyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemAppraiseClick(holder.imageviewAppraiseCallLawyer, pos);
                }
            });
            holder.imageviewRecordDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemDownLoadClick(holder.imageviewRecordDownload, pos);
                }
            });
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
        @BindView(R.id.imageview_record_download)
        ImageView imageviewRecordDownload;
        @BindView(R.id.imageview_appraise_call_lawyer)
        ImageView imageviewAppraiseCallLawyer;
        @BindView(R.id.textview_record_name)
        TextView textviewRecordName;
        @BindView(R.id.textview_record_size)
        TextView textviewRecordSize;
        @BindView(R.id.textview_record_time)
        TextView textviewRecordTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onItemDownLoadClick(View view, int position);

        void onItemAppraiseClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return true;
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<DirectCallLawyerHistoryResponse.DataBean> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            list.addAll(newDatas);
        }
        notifyDataSetChanged();
    }
}
