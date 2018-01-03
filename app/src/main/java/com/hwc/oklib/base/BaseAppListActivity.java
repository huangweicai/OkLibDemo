package com.hwc.oklib.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hwc.oklib.R;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonRefreshLayout;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.base.BaseListActivity;
import com.hwc.oklib.view.base.BaseRcvAdapter;
import com.hwc.oklib.widget.CenterWinListDialog;

import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_JAVA;
import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/11/19
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：刷新Activity
 */

public abstract class BaseAppListActivity<T> extends BaseListActivity<T> implements CommonRefreshLayout.OnRefreshListener, BaseRcvAdapter.OnFootItemClickListener {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
    }

    protected List<FunctionDetailBean> mBeans = new ArrayList<>();
    protected void showDetail() {
        mBeans.add(0,new FunctionDetailBean(context.getClass().getSimpleName()+".java", BASE_JAVA + context.getClass().getName().replace(".", "/")+".java"));
        final CenterWinListDialog centerWinListDialog = CenterWinListDialog.create(getSupportFragmentManager());
        centerWinListDialog.show();
        centerWinListDialog.addDataList(mBeans);
    }


    private CommonToolBar tb_toolbar;
    @Override
    protected void initTitle() {
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setVisibility(View.VISIBLE);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(TextUtils.isEmpty(centerTitle()) ? "标题" : centerTitle(), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_refresh.xml", BASE_RES + "/layout/activity_refresh.xml"));
                        showDetail();
                    }
                });
    }

}
