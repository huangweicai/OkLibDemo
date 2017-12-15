package com.hwc.oklib.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.TechnicalData;
import com.bumptech.glide.Glide;
import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.SupportingDetailsActivity;
import com.hwc.oklib.TechnicalDataAddActivity;
import com.hwc.oklib.WebViewActivity;
import com.hwc.oklib.bean.MyTechnicalData;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.util.FastJsonUtil;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonRefreshLayout;
import com.hwc.oklib.view.base.BaseFragment;
import com.hwc.oklib.view.base.BaseRcvAdapter;
import com.hwc.oklib.window.ConfirmDialog;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/9/29
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：技术资料
 */

public class TechnicalDataFragment extends BaseFragment implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {
    private CommonRefreshLayout rv_layout;
    private SupportRvAdapter adapter;
    private FloatingActionButton fab_button;

    private Context context;
    private List<MyTechnicalData> dataList = new ArrayList<>();

    public static TechnicalDataFragment getInstance(Bundle bundle) {
        TechnicalDataFragment technicalDataFragment = new TechnicalDataFragment();
        if (null != bundle) {
            technicalDataFragment.setArguments(bundle);
        }
        return technicalDataFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_technical_data;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(View view) {
        context = getContext();
        fab_button = findView(view, R.id.fab_button);
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TechnicalDataAddActivity.class);
                intent.putExtra(Common.TITLE, "技术资料添加");
                startActivity(intent);
            }
        });

        rv_layout = findView(view, R.id.rv_layout);
        rv_layout.setAdapter(adapter = new SupportRvAdapter(context));
        rv_layout.setOnRefreshListener(this);
        adapter.setOnFootItemClickListener(this);

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
    protected void initNet() {

    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    /**
     * 作者：黄伟才
     * 描述：请求网络
     */
    private void requestNet() {
        //技术资料
        BmobQueryHelp.queryTechnicalData(page, new BmobQueryHelp.OnTechnicalDataQueryListener() {
            @Override
            public void result(List<TechnicalData> dataList, BmobException e) {
                //一个集合中元素属性值迁移到另一个集合元素中
                List<MyTechnicalData> mdataList = FastJsonUtil.json2List(FastJsonUtil.list2Json(dataList), MyTechnicalData.class);

                TechnicalDataFragment.this.dataList.clear();
                TechnicalDataFragment.this.dataList.addAll(null == mdataList ? new ArrayList<MyTechnicalData>() : mdataList);
                if (e == null) {
                    //金额降序排序处理
                    Collections.sort(TechnicalDataFragment.this.dataList, new Comparator<MyTechnicalData>() {
                        @Override
                        public int compare(MyTechnicalData bean1, MyTechnicalData bean2) {
                            //比较规则，插入记录比较，降序排序(右比左)
                            return bean1.getRecordNum().compareTo(bean2.getRecordNum());
                        }
                    });
                } else {
                    ToastUtil.show("请求数据异常");
                }
                processList();
            }
        });
    }

    /**
     * 作者：黄伟才
     * 描述：刷新显示列表
     */
    private void processList() {
        if (isRefreshState) {
            //清空数据源
            adapter.getDataList().clear();
        }
        //停止刷新动画
        rv_layout.isShowRefreshAnim(false);
        //追加数据源
        adapter.addDataList(dataList);
        //少于默认加载数则没有认为是没有更多数据了
        if (dataList.size() < adapter.LOAD_NUM) {//默认加载条数15，这个条数应该同请求一次的条数一致
            //完成加载更多状态，更新文本
            adapter.setLoadState(false, "没有更多数据了");
        } else {
            //必须，能再次触发加载更多，更新可以加载状态
            rv_layout.loadFinish();
        }
        //必须，无数据显示状态，请求失败或者网络异常，更新显示效果
        //备选：R.mipmap.no_net_icon, "暂无网络"
        adapter.setEmptyDataState(R.mipmap.oklib_rv_refresh_component_no_data_icon, "暂无数据");
    }

    private boolean isRefreshState = true;
    private int page = 0;//页码（从0开始，第一页不跳过记录）

    @Override
    public void onRefresh() {
        page = 0;
        isRefreshState = true;
        rv_layout.isShowRefreshAnim(true);//显示刷新动画
        requestNet();
    }

    @Override
    public void onLoadMore() {
        page++;
        isRefreshState = false;

        //正在加载状态，更新文本
        adapter.setLoadState(true, "加载中···");
        requestNet();
    }

    @Override
    public void onFootItemClick(View v, int position) {
        onLoadMore();
    }


    public class SupportRvAdapter extends BaseRcvAdapter<MyTechnicalData> {

        public SupportRvAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (FOOTER_TYPE == viewType) {
                return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_foot_loading, parent, false));
            } else if (EXTRA_NODATA_TYPES == viewType) {
                return new EmptyDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_item_rv_refresh_component_nodata, parent, false));
            } else {
                return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_list, parent, false));
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

            private GlideImageView iv_headPortrait;
            private TextView tv_name;
            private TextView tv_introduce;
            private View itemView;

            private ViewHolderItem(View itemView) {
                super(itemView);
                this.itemView = itemView;
                iv_headPortrait = (GlideImageView) itemView.findViewById(R.id.iv_headPortrait);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_introduce = (TextView) itemView.findViewById(R.id.tv_introduce);
            }

            private void processData(final int position) {
                final MyTechnicalData bean = dataList.get(position);
                tv_name.setText("" + bean.getName());
                tv_introduce.setText("" + bean.getIntroduce());
                tv_introduce.setTag("" + bean.getReferralLinks());
                iv_headPortrait.loadImage("" + bean.getHeadPortrait(), R.mipmap.head_icon);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(Common.TITLE, bean.getName());
                        intent.putExtra(Common.URL, tv_introduce.getTag().toString());
                        intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                        startActivity(intent);
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ConfirmDialog dialog = ConfirmDialog.create(((SupportingDetailsActivity)context).getSupportFragmentManager());
                        dialog.setTitle("收藏");
                        dialog.setContent("是否收藏？");
                        dialog.setContentGravity(Gravity.CENTER);
                        dialog.show();
                        dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                            @Override
                            public void confirm(View v) {
                                ToastUtil.show("收藏成功");
                                bean.save();//本地数据库存储
                            }
                        });
                        return false;
                    }
                });
            }

        }
    }
}
