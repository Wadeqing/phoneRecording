package com.sinocall.phonerecordera.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.EvidenceListResponse;
import com.sinocall.phonerecordera.ui.activity.BaseActivity;
import com.sinocall.phonerecordera.util.FileSizeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class RecordingRecyclerViewAdapter extends RecyclerView.Adapter<RecordingRecyclerViewAdapter.ViewHolder> {
    private BaseActivity mContext;
    private List<EvidenceListResponse.DataBean> list = new ArrayList<>();

    public RecordingRecyclerViewAdapter(List<EvidenceListResponse.DataBean> data, BaseActivity mineRecodingActivity) {
        this.list = data;
        this.mContext = mineRecodingActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.recording_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (list != null && list.size() > 0) {
            EvidenceListResponse.DataBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                holder.textviewRecordName.setText(dataBean.fName);
                holder.textviewRecordTime.setText(dataBean.createTime);
                holder.textviewRecordSize.setText(FileSizeUtil.getPrintSize(Integer.parseInt(dataBean.size)));
            }
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.imageviewRecordDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemDownLoadClick(holder.itemView, pos);
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

        void onItemDownLoadClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
