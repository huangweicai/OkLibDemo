package com.oklib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oklib.adapter.MainRvAdapter;
import com.oklib.bean.MainBean;
import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.base.BaseRcvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2018/5/21
 * 作者：蓝天
 * 描述：
 */

public class MainFragment extends Fragment implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener, View.OnClickListener {
    private CommonRefreshLayout rv_layout;
    private MainRvAdapter mainRvAdapter;
    private List<MainBean> mainList = new ArrayList<>();

    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_main, null);
        rv_layout = view.findViewById(R.id.rv_layout);
        rv_layout.setAdapter(mainRvAdapter = new MainRvAdapter(getContext()));
        rv_layout.setOnRefreshListener(this);
        rv_layout.setAnimEnabled(false);
        mainRvAdapter.setOnFootItemClickListener(this);
        position = getArguments().getInt("position");
        onRefresh();
        return view;
    }

    /**
     * 作者：蓝天
     * 描述：请求网络
     */
    private void requestNet() {
        mainList = CommonManager.getDatas(position);
        processList();
    }

    /**
     * 作者：黄伟才
     * 描述：刷新显示列表
     */
    private void processList() {
        if (isRefreshState) {
            //清空数据源
            mainRvAdapter.getDataList().clear();
        }
        //停止刷新动画
        rv_layout.isShowRefreshAnim(false);
        //追加数据源
        mainRvAdapter.addDataList(mainList);
        //少于默认加载数则没有认为是没有更多数据了
        if (mainList.size() < mainRvAdapter.LOAD_NUM) {//默认加载条数15，这个条数应该同请求一次的条数一致
            //完成加载更多状态，更新文本
            mainRvAdapter.setLoadState(false, "没有更多数据了");
        } else {
            //必须，能再次触发加载更多，更新可以加载状态
            rv_layout.loadFinish();
        }
        //必须，无数据显示状态，请求失败或者网络异常，更新显示效果
        //备选：R.mipmap.no_net_icon, "暂无网络"
        mainRvAdapter.setEmptyDataState(R.mipmap.oklib_rv_refresh_component_no_data_icon, "暂无数据");
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
//        page++;
//        isRefreshState = false;

        //正在加载状态，更新文本
        //mainRvAdapter.setLoadState(true, "加载中···");
        //requestNet();


        mainRvAdapter.setLoadState(false, "没有更多数据了");
    }

    @Override
    public void onFootItemClick(View v, int position) {
        //onLoadMore();
    }

    @Override
    public void onClick(View v) {
//        String className = (String) v.getTag();
//        Logger.d(className);
    }
}
