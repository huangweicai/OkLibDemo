package com.oklib.view.base;

import android.view.View;

import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.R;

import java.util.List;


/**
 * 时间：2017/11/19
 * 作者：蓝天
 * 描述：刷新Activity
 */

public abstract class BaseListFragment<T> extends BaseFragment implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {
    @Override
    protected int initLayoutId() {
        return R.layout.oklib_activity_refresh;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(View view) {
        rv_layout = view.findViewById(R.id.rv_layout);
        rv_layout.setAdapter(adapter = setBaseListAdapter());
        rv_layout.setOnRefreshListener(this);
        adapter.setOnFootItemClickListener(this);
        onRefresh();
    }

    @Override
    protected void initNet() {

    }

    protected abstract BaseListAdapter<T> setBaseListAdapter();

    protected abstract void requestNet();

    protected CommonRefreshLayout rv_layout;
    protected BaseListAdapter adapter;

    //处理数据源
    protected void processDataList(List<T> dataList) {
        if (isRefreshState) {
            //清空数据源
            adapter.getDataList().clear();
        }
        //停止刷新动画
        rv_layout.isShowRefreshAnim(false);
        //追加数据源
        adapter.addDataList(dataList);
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
    protected int page = 0;

    @Override
    public void onRefresh() {
        page = 0;
        isRefreshState = true;
        //开始默认显示动画
        rv_layout.isShowRefreshAnim(true);
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
}
