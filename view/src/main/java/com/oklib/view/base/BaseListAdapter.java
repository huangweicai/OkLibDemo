package com.oklib.view.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oklib.view.R;

import java.util.List;


/**
 * 创建时间：2017/4/1
 * 编写者：蓝天
 * 功能描述：RV列表适配器
 */

public abstract class BaseListAdapter<T> extends BaseRcvAdapter<T> {

    public BaseListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOTER_TYPE == viewType) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_foot_loading, parent, false));
        }else if(EXTRA_NODATA_TYPES == viewType){
            return new EmptyDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_nodata, parent, false));
        }else{
            return newViewHolder(context, parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //尾部不填充数据
        if (canFillingData) {
            processData(holder, dataList, position);
        }
    }

    protected abstract int itemLayoutRes();
    protected abstract RecyclerView.ViewHolder newViewHolder(Context context, ViewGroup parent);
    protected abstract void processData(RecyclerView.ViewHolder holder, List<T> dataList, int position);
}
