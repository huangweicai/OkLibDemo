package com.hwc.oklib.common_components;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.bean.CarName;
import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppListActivity;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.util.CarTypeToCN;
import com.hwc.oklib.view.base.BaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/11/19
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：刷新列表模板
 */

public class RefreshListTemplateActivity extends BaseAppListActivity {

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<CarName> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    CarName bean = new CarName();
                    bean.setCarName("标题" + i);
                    bean.setCarUrl("http://img0.pcauto.com.cn/pcauto/zt/chebiao/guochan/1108/1446234_chery.jpg");
                    bean.setCarType("China");
                    list.add(bean);
                }
                processDataList(list);
            }
        }, 1500);
    }

    @Override
    protected String centerTitle() {
        return getIntent().getStringExtra(Common.TITLE);
    }

}
