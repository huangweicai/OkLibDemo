package com.hwc.oklib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.bean.CarName;
import com.hwc.oklib.R;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.util.CarTypeToCN;
import com.hwc.oklib.view.base.BaseRcvAdapter;


/**
 * 创建时间：2017/4/1
 * 编写者：黄伟才
 * 功能描述：适配器
 */

public class TestRvAdapter extends BaseRcvAdapter<CarName> {

    public TestRvAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOTER_TYPE == viewType) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_foot_loading, parent, false));
        } else if (EXTRA_NODATA_TYPES == viewType) {
            return new EmptyDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_nodata, parent, false));
        } else {
            return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baselist, parent, false));
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

        GlideImageView glideImageView = null;
        TextView tv_carName = null;
        TextView tv_carType = null;

        private ViewHolderItem(View itemView) {
            super(itemView);
            glideImageView = itemView.findViewById(R.id.glideImageView);
            tv_carType = itemView.findViewById(R.id.tv_carType);
            tv_carName = itemView.findViewById(R.id.tv_carName);
        }

        private void processData(final int position) {
            CarName bean = dataList.get(position);
            glideImageView.loadImage(bean.getCarUrl(), R.drawable.image_placeholder_icon);
            tv_carName.setText(bean.getCarName());
            tv_carType.setText(CarTypeToCN.getCarTypeCN(bean.getCarType()));
        }

    }

}
