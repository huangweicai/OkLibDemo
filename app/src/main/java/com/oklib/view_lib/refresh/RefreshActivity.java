package com.oklib.view_lib.refresh;

import android.os.Handler;
import android.view.View;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.base.BaseRcvAdapter;
import com.oklib.view_lib.refresh.adapter.RvAdapter;
import com.oklib.view_lib.refresh.bean.ViewHolderBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间：2017/8/2
 * 作者：蓝天
 * 描述：刷新组件使用演示
 */

public class RefreshActivity extends BaseAppActivity implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {
        rv_layout = (CommonRefreshLayout) findViewById(R.id.rv_layout);
        rv_layout.setAdapter(adapter = new RvAdapter(this));
        rv_layout.setOnRefreshListener(this);
        adapter.setOnFootItemClickListener(this);

        onRefresh();
    }


    private CommonRefreshLayout rv_layout;
    private RvAdapter adapter;

    //模拟网络请求
    private void netSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //数据源
                List<ViewHolderBean> list = new ArrayList<>();
                list.add(new ViewHolderBean(111));
                list.add(new ViewHolderBean(222));
                list.add(new ViewHolderBean(333));
                list.add(new ViewHolderBean(444));
                list.add(new ViewHolderBean(555));
                list.add(new ViewHolderBean(666));
                list.add(new ViewHolderBean(777));
                list.add(new ViewHolderBean(888));
                list.add(new ViewHolderBean(999));

                if (isRefreshState) {
                    //清空数据源
                    adapter.getDataList().clear();
                }
                //停止刷新动画
                rv_layout.isShowRefreshAnim(false);
                //追加数据源
                adapter.addDataList(list);
                if (list.size() < adapter.LOAD_NUM) {//默认加载条数15，这个条数应该同请求一次的条数一致
                    //完成加载更多状态，更新文本
                    adapter.setLoadState(false, "没有更多数据了");
                }else{
                    //必须，能再次触发加载更多，更新可以加载状态
                    rv_layout.loadFinish();
                }
                //必须，无数据显示状态，请求失败或者网络异常，更新显示效果
                //备选：R.mipmap.no_net_icon, "暂无网络"
                adapter.setEmptyDataState(R.mipmap.oklib_rv_refresh_component_no_data_icon, "暂无数据");
            }
        }, 2000);

    }

    private boolean isRefreshState = true;
    private int page = 0;
    @Override
    public void onRefresh() {
        page = 0;
        isRefreshState = true;
        //开始默认显示动画
        rv_layout.isShowRefreshAnim(true);
        netSuccess();
    }

    @Override
    public void onLoadMore() {
        page++;
        isRefreshState = false;

        //正在加载状态，更新文本
        adapter.setLoadState(true, "加载中···");
        netSuccess();
    }

    @Override
    public void onFootItemClick(View v, int position) {
        onLoadMore();
    }

}
