package com.sinocall.phonerecordera.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.MessageListResponse;
import com.sinocall.phonerecordera.ui.activity.MessageCenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/11/30.
 */

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {
    private MessageCenterActivity mContext;
    private List<MessageListResponse.DataBean> list = new ArrayList<>();

    public MessageRecyclerViewAdapter(List<MessageListResponse.DataBean> data, MessageCenterActivity messageCenterActivity) {
        this.mContext = messageCenterActivity;
        this.list = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            MessageListResponse.DataBean dataBean = list.get(position);
            if (dataBean != null && holder != null) {
                if ("Y".equals(dataBean.IsReadAlready)) {
                    holder.imageviewMessageRead.setVisibility(View.VISIBLE);
                    holder.imageviewMessageUnread.setVisibility(View.GONE);
                    setSelected(holder.linearlayoutText, true);
                } else {
                    holder.imageviewMessageRead.setVisibility(View.GONE);
                    holder.imageviewMessageUnread.setVisibility(View.VISIBLE);
                    setSelected(holder.linearlayoutText, false);
                }
                holder.textviewMessageContent.setText(dataBean.subTitle);
                holder.textviewMessageTitle.setText(dataBean.title);
                holder.textViewMessageTime.setText(dataBean.createTime.substring(0, 10));
            }
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null && holder != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.imageviewMessageRead.setVisibility(View.VISIBLE);
                    holder.imageviewMessageUnread.setVisibility(View.GONE);
                    setSelected(holder.linearlayoutText, true);
                    int pos = holder.getLayoutPosition();
                    MessageListResponse.DataBean dataBean = list.get(pos);
                    dataBean.IsReadAlready = "Y";
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
        @BindView(R.id.imageview_message_read)
        ImageView imageviewMessageRead;
        @BindView(R.id.imageview_message_unread)
        ImageView imageviewMessageUnread;
        @BindView(R.id.textview_message_title)
        TextView textviewMessageTitle;
        @BindView(R.id.textview_message_content)
        TextView textviewMessageContent;
        @BindView(R.id.linearlayout_text)
        LinearLayout linearlayoutText;
        @BindView(R.id.textview_message_time)
        TextView textViewMessageTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
