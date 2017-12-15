package com.hwc.oklib.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.bean.CarName;
import com.hwc.oklib.BackgroundActivity;
import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.bean.MainBean;
import com.hwc.oklib.database.sugar.SugarRecord;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.base.BaseListAdapter;
import com.hwc.oklib.view.base.BaseListFragment;
import com.hwc.oklib.window.ConfirmDialog;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 时间：2017/11/21
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：功能收藏
 */

public class CollectFunctionFragment extends BaseListFragment {
    private View mItemView;

    public class ViewHolderItem<T> extends RecyclerView.ViewHolder {
        private TextView tv_item;

        public ViewHolderItem(View itemView) {
            super(itemView);
            mItemView = itemView;
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }

    @Override
    protected BaseListAdapter setBaseListAdapter() {
        return new BaseListAdapter<MainBean>(getContext()) {
            @Override
            protected int itemLayoutRes() {
                return R.layout.item_mian_list;
            }

            @Override
            protected RecyclerView.ViewHolder newViewHolder(Context context, ViewGroup parent) {
                //泛型出口，外部使用
                return new ViewHolderItem<CarName>(LayoutInflater.from(context).inflate(itemLayoutRes(), parent, false));
            }

            @Override
            protected void processData(RecyclerView.ViewHolder holder, final List<MainBean> dataList, final int position) {
                ViewHolderItem mholder = (ViewHolderItem) holder;
                final MainBean bean = dataList.get(position);
                mholder.tv_item.setText(bean.getTitle());
                mItemView.setTag(bean.getClassName());
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(context, bean.getClassName());
                        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
                            // 说明系统中不存在这个activity
                            ToastUtil.show("请检测更新，功能在新版本体验");
                        } else {
                            intent.putExtra(Common.TITLE, bean.getTitle());
                            context.startActivity(intent);
                        }
                    }
                });
                mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ConfirmDialog dialog = ConfirmDialog.create(((BackgroundActivity) context).getSupportFragmentManager());
                        dialog.setTitle("删除");
                        dialog.setContent("是否删除？");
                        dialog.setContentGravity(Gravity.CENTER);
                        dialog.show();
                        dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                            @Override
                            public void confirm(View v) {
                                //bean.delete();，这里删除错位，问题待定
                                adapter.removeDataItem(position);
                                ToastUtil.show("删除成功");
                            }
                        });
                        return false;
                    }
                });
            }
        };
    }

    @Override
    protected void requestNet() {
        rv_layout.setAnimEnabled(false);
        try {
            List<MainBean> mainBeanList = MainBean.listAll(MainBean.class);
            Collections.sort(mainBeanList, new Comparator<MainBean>() {
                @Override
                public int compare(MainBean bean1, MainBean bean2) {
                    //比较规则，升序排序(左比右)
                    return bean1.getNum().compareTo(bean2.getNum());
                }
            });
            processDataList(mainBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空所有数据
        MainBean.deleteAll(MainBean.class);
        //数据源数据批量插入更新
        SugarRecord.saveInTx(adapter.getDataList());
    }
}
