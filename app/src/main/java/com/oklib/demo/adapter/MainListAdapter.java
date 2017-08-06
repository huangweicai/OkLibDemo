package com.oklib.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oklib.demo.R;
import com.oklib.demo.Common;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：入口类演示列表适配器
 */

public class MainListAdapter extends BaseAdapter {
    private int type;

    public MainListAdapter(int type) {
        this.type = type;
    }

    public String getTitle(int position) {
        return Common.getDatas(type)[position];
    }

    @Override
    public int getCount() {
        return Common.getDatas(type).length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mian_list, parent, false);
            holder = new ViewHolder();
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //处理逻辑
        holder.processData(position);
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_item;

        private void processData(int position) {
            tv_item.setText(Common.getDatas(type)[position]);
        }
    }
}


