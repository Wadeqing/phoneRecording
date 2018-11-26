package com.sinocall.phonerecordera.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.dao.ConstactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingchao on 2017/7/4.
 * 通讯录粘性listview
 */

public class StickyAdapter extends BaseAdapter {
    private FragmentActivity activity;
    private ArrayList<ConstactBean> friends;
    private ConstactBean contacts;
    private static final String EXTRA_CREATE_BY = "create_by";
    private String keyWord;

    public StickyAdapter(ArrayList<ConstactBean> friends, FragmentActivity activity) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public ConstactBean getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String letter;
        if (getCount() > position) {
            contacts = friends.get(position);
            final long id = contacts.getId();
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.adapter_friend, null);
                holder = new ViewHolder();
                holder.tvNum = (TextView) convertView.findViewById(R.id.textview_address_list_user_phone_num);
                holder.tvLetter = (TextView) convertView.findViewById(R.id.tv_letter);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(contacts.getName());
            final String mobile = contacts.getMobile();
            if (mobile.contains(",")) {
                if (keyWord != null && keyWord.length() > 0) {
                    String[] split = mobile.split(",");
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].contains(keyWord)) {
                            holder.tvNum.setText(split[i]);
                            break;
                        }
                    }
                } else {
                    holder.tvNum.setText(contacts.getMobile().substring(0, mobile.indexOf(",")));
                }
            } else {
                holder.tvNum.setText(contacts.getMobile());
            }
            if (this.contacts.getSortKey() == null || this.contacts.getSortKey().isEmpty()) {
                letter = " ";
            } else {
                letter = this.contacts.getSortKey().charAt(0) + "";
            }
            if (position > 0 && friends.get(position - 1).getSortKey() != null &&
                    friends.get(position - 1).getSortKey().length() > 0) {
                //获取上一个条目的首字母
                String lastLetter = friends.get(position - 1).getSortKey().charAt(0) + "";
                //如果当前letter和上一个的一样，那么则隐藏当前的字母View
                lastLetter = lastLetter.toUpperCase();
                if (letter.toUpperCase().equals(lastLetter)) {
                    holder.tvLetter.setVisibility(View.GONE);
                } else {
                    holder.tvLetter.setVisibility(View.VISIBLE);
                    holder.tvLetter.setText(letter);
                }
            } else {
                //说明是第0个，直接显示
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(letter);
            }

        }
        return convertView;
    }


    public void updateListView(List<ConstactBean> filterDateList, String keyWord) {
        this.keyWord = keyWord;
        friends = (ArrayList<ConstactBean>) filterDateList;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView tvName, tvLetter, tvNum;
    }

}

