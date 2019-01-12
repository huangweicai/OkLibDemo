package com.oklib.base;

import android.content.Context;
import android.view.View;

import com.oklib.CommonManager;
import com.oklib.R;
import com.oklib.view.CommonToolBar;
import com.oklib.view.base.BaseAbstractActivity;

import butterknife.ButterKnife;

/**
 * 时间：2017/8/2
 * 作者：蓝天
 * 描述：app相关基类，与具体app业务相关
 */

public abstract class BaseAppActivity extends BaseAbstractActivity {
    public Context context;

    @Override
    protected void beforeLayout() {
        context = this;
    }

    @Override
    protected void afterLayout() {
        ButterKnife.bind(this);
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
                }).setCenterTitle(getIntent().getStringExtra(CommonManager.TITLE), 17, R.color.app_white_color);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initVariable() {

    }

}
