package com.hwc.oklib;

import android.view.View;

import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.view.CommonToolBar;


/**
 * 时间：2017/8/24
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：技术交流
 */

public class ExchangeAreaActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_exchange_area;
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
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }
}
