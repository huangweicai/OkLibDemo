package com.oklib.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.CommonManager;
import com.oklib.R;
import com.oklib.bean.MainBean;
import com.oklib.view.base.BaseRcvAdapter;

/**
 * 时间：2017/9/5
 * 作者：蓝天
 * 描述：首页数据适配器
 */

public class MainRvAdapter extends BaseRcvAdapter<MainBean> {
    private boolean isCollect = false;

    public MainRvAdapter(Context context) {
        super(context);
    }

    public MainRvAdapter(Context context, boolean isCollect) {
        super(context);
        this.isCollect = isCollect;
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

        private TextView tv_title;
        private View itemView;

        private ViewHolderItem(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

        private void processData(final int position) {
            final MainBean bean = dataList.get(position);
            tv_title.setText(bean.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.setClassName(context, bean.getCls());
                    intent.setClass(context, bean.getCls());
                    if (context.getPackageManager().resolveActivity(intent, 0) == null) {
                        // 说明系统中不存在这个activity
                        Toast.makeText(context, "Activity没注册，请检查", Toast.LENGTH_SHORT).show();
                    }else{
                        intent.putExtra(CommonManager.TITLE, bean.getTitle());
                        intent.putExtra(CommonManager.URL, bean.getUrl());
                        context.startActivity(intent);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (isCollect) {
                        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                                .setTitle("确定删除？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dataList.remove(position);
                                        notifyDataSetChanged();
                                        bean.delete();
                                    }
                                })
                                .create();
                        dialog.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("收藏");
                        builder.setMessage("是否收藏？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //处理逻辑
                                bean.save();
                                Toast.makeText(context, "收藏成功", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    return false;
                }
            });
        }

    }
}
