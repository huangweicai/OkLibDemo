package com.hwc.oklib.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.CarName;
import com.bmoblib.bean.UserBean;
import com.hwc.oklib.R;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.view.base.BaseListAdapter;
import com.hwc.oklib.view.base.BaseListFragment;

import java.util.List;

/**
 * 时间：2017/11/21
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：短信发送
 */

public class BackgroundUserFragment extends BaseListFragment {
    private View mItemView;

    public class ViewHolderItem<T> extends RecyclerView.ViewHolder {
        private GlideImageView iv_headPortrait;
        private TextView tv_name;
        private TextView tv_introduce;
        private TextView tv_num;

        public ViewHolderItem(View itemView) {
            super(itemView);
            mItemView = itemView;
            iv_headPortrait = itemView.findViewById(R.id.iv_headPortrait);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_introduce = itemView.findViewById(R.id.tv_introduce);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }

    @Override
    protected BaseListAdapter setBaseListAdapter() {
        return new BaseListAdapter<UserBean>(getContext()) {
            @Override
            protected int itemLayoutRes() {
                return R.layout.item_user_list;
            }

            @Override
            protected RecyclerView.ViewHolder newViewHolder(Context context, ViewGroup parent) {
                //泛型出口，外部使用
                return new ViewHolderItem<CarName>(LayoutInflater.from(context).inflate(itemLayoutRes(), parent, false));
            }

            @Override
            protected void processData(RecyclerView.ViewHolder holder, List<UserBean> dataList, final int position) {
                final ViewHolderItem mholder = (ViewHolderItem) holder;
                final UserBean bean = dataList.get(position);
                mholder.tv_name.setText(TextUtils.isEmpty(bean.getNickName()) ? "昵称" : bean.getNickName() + "");
                mholder.tv_introduce.setText(TextUtils.isEmpty(bean.getIntro()) ? "介绍" : bean.getIntro() + "");
                mholder.tv_num.setText("" + bean.getPhoneNum());
                mholder.iv_headPortrait.loadImage("" + bean.getHeadPicture(), R.mipmap.head_icon);
            }
        };
    }

    @Override
    protected void requestNet() {
        BmobQueryHelp.queryUserBeanList(page, new BmobQueryHelp.OnUserBeanQueryListener() {
            @Override
            public void before() {
            }

            @Override
            public void success(List<UserBean> userBeans) {
                processDataList(userBeans);
            }

            @Override
            public void onError(String errorMessage, int code) {

            }

        });
    }

}
