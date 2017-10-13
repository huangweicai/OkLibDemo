package com.oklib.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.CommoTools;
import com.bmoblib.bean.CommonComponents;
import com.bmoblib.bean.IntegrationFramework;
import com.bmoblib.bean.WindowRelated;
import com.oklib.base.BaseFragment;
import com.oklib.base.BaseRcvAdapter;
import com.oklib.demo.adapter.MainRvAdapter;
import com.oklib.demo.bean.MainBean;
import com.oklib.util.FastJsonUtil;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonRefreshLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/8/5
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：首页碎片，用于内容列表展示
 */

public class MainRvFragment extends BaseFragment implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener, View.OnClickListener {

    private CommonRefreshLayout rv_layout;
    private MainRvAdapter mainRvAdapter;
    private Context context;
    private List<MainBean> mainList = new ArrayList<>();
    private int type;

    public static MainRvFragment getInstance(Bundle bundle) {
        MainRvFragment mainRvFragment = new MainRvFragment();
        if (null != bundle) {
            mainRvFragment.setArguments(bundle);
        }
        return mainRvFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_rv_main;
    }

    @Override
    protected void initVariable() {
        type = getArguments().getInt("type");
    }

    @Override
    protected void initView(View view) {
        context = getContext();
        rv_layout = findView(view, R.id.rv_layout);
        rv_layout.setAdapter(mainRvAdapter = new MainRvAdapter(context));
        rv_layout.setOnRefreshListener(this);
        mainRvAdapter.setOnFootItemClickListener(this);
    }

    @Override
    protected void initNet() {
        onRefresh();
    }

    /**
     * 作者：黄伟才
     * 描述：请求网络
     */
    private void requestNet() {
        switch (type) {
            case 0:
                BmobQueryHelp.queryIntegrationFramework(page, new BmobQueryHelp.OnIntegrationFrameworkListener() {
                    @Override
                    public void result(List<IntegrationFramework> object, BmobException e) {
                        if (e == null) {
                            String fastJsonStr = FastJsonUtil.list2Json(object);
                            mainList.clear();
                            mainList = FastJsonUtil.json2List(fastJsonStr, MainBean.class);
                            //金额降序排序处理
                            Collections.sort(mainList, new Comparator<MainBean>() {
                                @Override
                                public int compare(MainBean bean1, MainBean bean2) {
                                    //比较规则，金额比较，升序排序(左比右)
                                    return bean1.getPage().compareTo(bean2.getPage());
                                }
                            });
                        } else {
                            ToastUtil.show("请求数据异常");
                        }
                        processList();
                    }
                });
                break;
            case 1:
                BmobQueryHelp.queryCommonComponents(page, new BmobQueryHelp.OnCommonComponentsListener() {
                    @Override
                    public void result(List<CommonComponents> object, BmobException e) {
                        if (e == null) {
                            String fastJsonStr = FastJsonUtil.list2Json(object);
                            mainList.clear();
                            mainList = FastJsonUtil.json2List(fastJsonStr, MainBean.class);
                            //金额降序排序处理
                            Collections.sort(mainList, new Comparator<MainBean>() {
                                @Override
                                public int compare(MainBean bean1, MainBean bean2) {
                                    //比较规则，金额比较，升序排序(左比右)
                                    return bean1.getPage().compareTo(bean2.getPage());
                                }
                            });
                        } else {
                            ToastUtil.show("请求数据异常");
                        }
                        processList();
                    }
                });
                break;
            case 2:
                BmobQueryHelp.queryCommoTools(page, new BmobQueryHelp.OnCommoToolsListener() {
                    @Override
                    public void result(List<CommoTools> object, BmobException e) {
                        if (e == null) {
                            String fastJsonStr = FastJsonUtil.list2Json(object);
                            mainList.clear();
                            mainList = FastJsonUtil.json2List(fastJsonStr, MainBean.class);
                            //金额降序排序处理
                            Collections.sort(mainList, new Comparator<MainBean>() {
                                @Override
                                public int compare(MainBean bean1, MainBean bean2) {
                                    //比较规则，金额比较，升序排序(左比右)
                                    return bean1.getPage().compareTo(bean2.getPage());
                                }
                            });
                        } else {
                            ToastUtil.show("请求数据异常");
                        }
                        processList();
                    }
                });
                break;
            case 3:
                BmobQueryHelp.queryWindowRelated(page, new BmobQueryHelp.OnWindowRelatedListener() {
                    @Override
                    public void result(List<WindowRelated> object, BmobException e) {
                        if (e == null) {
                            String fastJsonStr = FastJsonUtil.list2Json(object);
                            mainList.clear();
                            mainList = FastJsonUtil.json2List(fastJsonStr, MainBean.class);
                            //金额降序排序处理
                            Collections.sort(mainList, new Comparator<MainBean>() {
                                @Override
                                public int compare(MainBean bean1, MainBean bean2) {
                                    //比较规则，金额比较，升序排序(左比右)
                                    return bean1.getPage().compareTo(bean2.getPage());
                                }
                            });
                        } else {
                            ToastUtil.show("请求数据异常");
                        }
                        processList();
                    }
                });
                break;
        }
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
        }else{
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
        page++;
        isRefreshState = false;

        //正在加载状态，更新文本
        mainRvAdapter.setLoadState(true, "加载中···");
        requestNet();
    }

    @Override
    public void onFootItemClick(View v, int position) {
        onLoadMore();
    }

    @Override
    public void onClick(View v) {
        String className = (String) v.getTag();
        Logger.d(className);
    }
}
