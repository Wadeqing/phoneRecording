package com.sinocall.phonerecordera.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.UserVerifyFilesResponse;
import com.sinocall.phonerecordera.ui.activity.EvidenceManagerActivity;
import com.sinocall.phonerecordera.ui.activity.UserVerifyDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyFilesRecyclerViewAdapter extends RecyclerView.Adapter<UserVerifyFilesRecyclerViewAdapter.ViewHolder> {
    private EvidenceManagerActivity context;
    private List<UserVerifyFilesResponse.DataBean> data;

    public UserVerifyFilesRecyclerViewAdapter(EvidenceManagerActivity context, List<UserVerifyFilesResponse.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.user_verify_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final UserVerifyFilesResponse.DataBean dataBean = data.get(position);
            holder.textviewFileApplyTime.setText("申请时间：" + dataBean.createTime);
            holder.textviewFileName.setText("录音文件：" + dataBean.fileName);
            holder.textviewFileType.setText("证书类型：" + dataBean.verifyType);
            holder.buttonFileLockdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("id",dataBean.id);
                    context.startActivity(intent.setClass(context,UserVerifyDetailActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_file_type)
        TextView textviewFileType;
        @BindView(R.id.textview_file_apply_time)
        TextView textviewFileApplyTime;
        @BindView(R.id.textview_file_name)
        TextView textviewFileName;
        @BindView(R.id.button_file_lockdetail)
        Button buttonFileLockdetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
