package com.sinocall.phonerecordera.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.HistoryConsultResponse;
import com.sinocall.phonerecordera.widget.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class HistoryConsultRecyclerViewAdapter extends RecyclerView.Adapter<HistoryConsultRecyclerViewAdapter.ViewHolder> implements SwipeLayout.OnSwipeListener {
    private Context mContext;
    private List<HistoryConsultResponse.DataBean> list = new ArrayList<>();
    SwipeLayout currentLayout = null;//用来记录当前打开的SwipeLayout

    public HistoryConsultRecyclerViewAdapter(List<HistoryConsultResponse.DataBean> data, Context mContext) {
        this.list = data;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.history_condult_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            HistoryConsultResponse.DataBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                holder.textviewHistoryDetail.setText(dataBean.question);
                holder.textviewHistoryTime.setText(dataBean.createTime);
                if (dataBean.commentStatus == 1) {
                    holder.imageviewAppraise.setBackgroundResource(R.drawable.consulting_btn_appraised_default);
                    holder.imageviewAppraise.setEnabled(false);
                } else {
                    holder.imageviewAppraise.setBackgroundResource(R.drawable.consulting_btn_appraise_default);
                    holder.imageviewAppraise.setEnabled(true);
                }
                if (dataBean.proofFileId > 0) {
                    holder.textviewLockConsult.setVisibility(View.VISIBLE);
                } else {
                    holder.textviewLockConsult.setVisibility(View.GONE);
                }
                holder.swipeLayout.setOnSwipeListener(this);
            }
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null && holder != null) {
            holder.textviewLockConsult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClickLockConsutl(holder.itemView, pos);
                }
            });

            holder.imageviewAppraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClickAppraise(holder.itemView, pos);
                }
            });
            holder.textviewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClickDelete(holder.itemView, pos);
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

    public void setRemove(int deletePosition) {
        list.remove(deletePosition);
        notifyDataSetChanged();
    }

    public void setAppraised(int appraisePosition) {
        HistoryConsultResponse.DataBean dataBean = list.get(appraisePosition);
        dataBean.commentStatus = 1;
        list.set(appraisePosition, dataBean);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_history_detail)
        TextView textviewHistoryDetail;
        @BindView(R.id.textview_history_time)
        TextView textviewHistoryTime;
        @BindView(R.id.textview_lock_consult)
        TextView textviewLockConsult;
        @BindView(R.id.imageview_appraise)
        ImageView imageviewAppraise;
        @BindView(R.id.textview_delete)
        TextView textviewDelete;
        @BindView(R.id.swipelayout)
        SwipeLayout swipeLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemClickLockConsutl(View view, int position);

        void onItemClickDelete(View view, int position);

        void onItemClickAppraise(View view, int position);

        void onItemLongClick(View view, int position);
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
    public void updateList(List<HistoryConsultResponse.DataBean> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            list.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onOpen(SwipeLayout layout) {
        //先关闭之间已经打开的
        if (currentLayout != null && currentLayout != layout) {
            currentLayout.close();
            Log.e("-------close", "close");
        }
        //记录一下
        currentLayout = layout;
    }

    @Override
    public void onClose(SwipeLayout layout) {
        //关闭的时候清除一下
        if (currentLayout == layout) {
            Log.e("---onClose", "close");
            currentLayout = null;
        }
    }

    @Override
    public void onTouchDown(SwipeLayout swipeLayout) {
        if (currentLayout != null && currentLayout != swipeLayout) {
            Log.e("---onTouchDown", "close");
            currentLayout.close();
        }
    }


}
