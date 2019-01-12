package com.oklib.view.base;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.CommonToolBar;
import com.oklib.view.R;

import java.util.List;


/**
 * 时间：2017/11/19
 * 作者：蓝天
 * 描述：刷新Activity
 */

public abstract class BaseListActivity<T> extends BaseCommonUseActivity implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {
    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void afterLayout() {

    }

    @Override
    protected int initLayoutId() {
        return R.layout.oklib_activity_refresh;
    }

    @Override
    protected void initVariable() {

    }

    private CommonToolBar tb_toolbar;
    @Override
    protected void initTitle() {
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setVisibility(View.VISIBLE);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.oklib_btn_back)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(TextUtils.isEmpty(centerTitle()) ? "标题" : centerTitle(), 17, R.color.oklib_white)//中间标题
                .setRightTitle("更多", 14, R.color.oklib_white);
    }

    protected void setCenterTitle(String title) {
        tb_toolbar.setCenterTitle("" + title);
    }
    protected void setRightTitle(String title) {
        tb_toolbar.setRightTitle("" + title);
    }

    protected abstract BaseListAdapter<T> setBaseListAdapter();

    @Override
    protected void initView() {
        rv_layout = (CommonRefreshLayout) findViewById(R.id.rv_layout);
        rv_layout.setAdapter(adapter = setBaseListAdapter());
        rv_layout.setOnRefreshListener(this);
        adapter.setOnFootItemClickListener(this);
        onRefresh();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rv_layout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                }
            });
        }
    }

    @Override
    protected void initNet() {

    }

    protected abstract void requestNet();

    protected abstract String centerTitle();

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
