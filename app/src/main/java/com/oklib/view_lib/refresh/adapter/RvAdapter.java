package com.oklib.view_lib.refresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oklib.R;
import com.oklib.view.base.BaseRcvAdapter;
import com.oklib.view_lib.refresh.bean.ViewHolderBean;


/**
 * 创建时间：2017/4/1
 * 编写者：蓝天
 * 功能描述：适配器
 */

public class RvAdapter<T> extends BaseRcvAdapter<T> {

    public RvAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOTER_TYPE == viewType) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_foot_loading, parent, false));
        }else if(EXTRA_NODATA_TYPES == viewType){
            return new EmptyDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_nodata, parent, false));
        }else{
            return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component, parent, false));
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

        private ViewHolderItem(View itemView) {
            super(itemView);
        }

        private void processData(final int position) {
            ViewHolderBean bean = (ViewHolderBean) dataList.get(position);
            Log.d("TAG", "ViewHolderBeam position"+bean.getStepNum());
        }

    }

}
