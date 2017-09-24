package com.oklib.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oklib.base.BaseRcvAdapter;
import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.bean.MainBean;
import com.oklib.util.toast.ToastUtil;

/**
 * 时间：2017/9/5
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：首页数据适配器
 */

public class MainRvAdapter extends BaseRcvAdapter<MainBean> {
    public MainRvAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOTER_TYPE == viewType) {
            return new BaseRcvAdapter.FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_foot_loading, parent, false));
        } else if (EXTRA_NODATA_TYPES == viewType) {
            return new EmptyDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_nodata, parent, false));
        } else {
            return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mian_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //尾部不填充数据
        if (canFillingData) {
            ViewHolderItem mHolder = (ViewHolderItem) holder;
            mHolder.processData(position);
        }
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {

        private TextView tv_item;
        private View itemView;

        private ViewHolderItem(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }

        private void processData(final int position) {
            final MainBean bean = dataList.get(position);
            tv_item.setText(bean.getTitle());
            itemView.setTag(bean.getClassName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClassName(context, bean.getClassName());
                    if (context.getPackageManager().resolveActivity(intent, 0) == null) {
                        // 说明系统中不存在这个activity
                        ToastUtil.show("请检测更新，功能在新版本体验");
                    }else{
                        intent.putExtra(Common.TITLE, bean.getTitle());
                        context.startActivity(intent);
                    }
                }
            });
        }

    }
}
