package com.hwc.oklib.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.CarName;
import com.bumptech.glide.Glide;
import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppListActivity;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.util.CarTypeToCN;
import com.hwc.oklib.view.base.BaseListAdapter;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/11/19
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：汽车logo大全
 */

public class CarLogoActivity extends BaseAppListActivity {

    public class ViewHolderItem<T> extends RecyclerView.ViewHolder {
        GlideImageView glideImageView;
        TextView tv_carName;
        TextView tv_carType;
        public ViewHolderItem(View itemView) {
            super(itemView);
            glideImageView = itemView.findViewById(R.id.glideImageView);
            tv_carName = itemView.findViewById(R.id.tv_carName);
            tv_carType = itemView.findViewById(R.id.tv_carType);
        }
    }

    @Override
    protected BaseListAdapter setBaseListAdapter() {
        return new BaseListAdapter<CarName>(context) {//泛型入口，内部流通

            @Override
            protected int itemLayoutRes() {
                return R.layout.item_baselist;
            }

            @Override
            protected RecyclerView.ViewHolder newViewHolder(Context context, ViewGroup parent) {
                //泛型出口，外部使用
                return new ViewHolderItem<CarName>(LayoutInflater.from(context).inflate(itemLayoutRes(), parent, false));
            }

            @Override
            protected void processData(RecyclerView.ViewHolder holder, List<CarName> dataList, int position) {
                ViewHolderItem mholder = (ViewHolderItem) holder;
                CarName bean = dataList.get(position);
                mholder.glideImageView.loadImage(bean.getCarUrl(), R.drawable.image_placeholder_icon);
                mholder.tv_carName.setText(bean.getCarName());
                mholder.tv_carType.setText(CarTypeToCN.getCarTypeCN(bean.getCarType()));
            }
        };
    }

    @Override
    protected void requestNet() {
        BmobQueryHelp.queryCar(page, new BmobQueryHelp.OnCarListener() {
            @Override
            public void result(List<CarName> cars, BmobException e) {
                processDataList(cars);
            }
        });
        //滚动监听
        rv_layout.getRecycleView().setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_SETTLING){//冲刺的状态
                    Glide.with(context).pauseRequests();
                }else{
                    Glide.with(context).resumeRequests();//停留和拖拽状加载
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected String centerTitle() {
        return getIntent().getStringExtra(Common.TITLE);
    }

}
