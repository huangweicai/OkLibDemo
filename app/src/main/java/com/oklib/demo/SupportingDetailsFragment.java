package com.oklib.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oklib.base.BaseFragment;
import com.oklib.demo.adapter.MainListAdapter;

/**
 * 时间：2017/8/5
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：首页碎片，用于内容列表展示
 */

public class SupportingDetailsFragment extends BaseFragment {

    private ListView lv_main_list;
    private MainListAdapter mainListAdapter;
    private Context context;
    private int type;

    public static SupportingDetailsFragment getInstance(Bundle bundle) {
        SupportingDetailsFragment mainFragment = new SupportingDetailsFragment();
        if (null != bundle) {
            mainFragment.setArguments(bundle);
        }
        return mainFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initVariable() {
        type = getArguments().getInt("type");
    }

    @Override
    protected void initView(View view) {
        context = getContext();
        lv_main_list = findView(view, R.id.lv_main_list);
        lv_main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lv_main_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
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
        });
    }

    @Override
    protected void initNet() {

    }

    private class ViewHolder {
        private TextView tv_item;

        private void processData(int position) {
            tv_item.setText(Common.getDatas(type)[position]);
        }
    }
}
