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
import com.hwc.oklib.WebViewActivity;
import com.hwc.oklib.bean.MyTechnicalData;
import com.hwc.oklib.database.sugar.SugarRecord;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
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
 * 描述：文章收藏
 */

public class CollectPublishFragment extends BaseListFragment {
    private View mItemView;
    public class ViewHolderItem<T> extends RecyclerView.ViewHolder {
        private GlideImageView iv_headPortrait;
        private TextView tv_name;
        private TextView tv_introduce;
        public ViewHolderItem(View itemView) {
            super(itemView);
            mItemView = itemView;
            iv_headPortrait = itemView.findViewById(R.id.iv_headPortrait);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_introduce = itemView.findViewById(R.id.tv_introduce);
        }
    }

    @Override
    protected BaseListAdapter setBaseListAdapter() {
        return new BaseListAdapter<MyTechnicalData>(getContext()) {
            @Override
            protected int itemLayoutRes() {
                return R.layout.item_support_list;
            }

            @Override
            protected RecyclerView.ViewHolder newViewHolder(Context context, ViewGroup parent) {
                //泛型出口，外部使用
                return new ViewHolderItem<CarName>(LayoutInflater.from(context).inflate(itemLayoutRes(), parent, false));
            }

            @Override
            protected void processData(RecyclerView.ViewHolder holder, List<MyTechnicalData> dataList, final int position) {
                final ViewHolderItem mholder = (ViewHolderItem) holder;
                final MyTechnicalData bean = dataList.get(position);
                mholder.tv_name.setText("" + bean.getName());
                mholder.tv_introduce.setText("" + bean.getIntroduce());
                mholder.tv_introduce.setTag("" + bean.getReferralLinks());
                mholder.iv_headPortrait.loadImage("" + bean.getHeadPortrait(), R.color.placeholder_color);

                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(Common.TITLE, bean.getName());
                        intent.putExtra(Common.URL, mholder.tv_introduce.getTag().toString());
                        intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                        startActivity(intent);
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
        //技术资料
        try {
            List<MyTechnicalData> mTechnicalDataList = MyTechnicalData.listAll(MyTechnicalData.class);
            Collections.sort(mTechnicalDataList, new Comparator<MyTechnicalData>() {
                @Override
                public int compare(MyTechnicalData bean1, MyTechnicalData bean2) {
                    //比较规则，插入记录比较，降序排序(右比左)
                    return bean2.getRecordNum().compareTo(bean1.getRecordNum());
                }
            });
            processDataList(mTechnicalDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空所有数据
        MyTechnicalData.deleteAll(MyTechnicalData.class);
        //数据源数据批量插入更新
        SugarRecord.saveInTx(adapter.getDataList());
    }
}
