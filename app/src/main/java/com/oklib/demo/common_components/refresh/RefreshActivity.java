package com.oklib.demo.common_components.refresh;

import android.os.Handler;
import android.view.View;

import com.oklib.base.BaseRcvAdapter;
import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.demo.common_components.refresh.adapter.RvAdapter;
import com.oklib.demo.common_components.refresh.bean.ViewHolderBeam;
import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/8/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：刷新组件使用演示
 */

public class RefreshActivity extends BaseAppActivity implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_refresh.xml", BASE_RES +"/layout/activity_refresh.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        rv_layout = (CommonRefreshLayout) findViewById(R.id.rv_layout);
        rv_layout.setAdapter(adapter = new RvAdapter(this));
        rv_layout.setOnRefreshListener(this);
        adapter.setOnFootItemClickListener(this);

        //开始默认显示动画
        rv_layout.isShowRefreshAnim(true);
        onRefresh();
    }

    @Override
    protected void initNet() {

    }

    private CommonRefreshLayout rv_layout;
    private RvAdapter adapter;

    //模拟网络请求
    private void netSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //数据源
                List<ViewHolderBeam> list = new ArrayList<>();
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());
                list.add(new ViewHolderBeam());

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

    @Override
    public void onRefresh() {
        isRefreshState = true;
        //停止刷新动画
        rv_layout.isShowRefreshAnim(false);
        netSuccess();
    }

    @Override
    public void onLoadMore() {
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
