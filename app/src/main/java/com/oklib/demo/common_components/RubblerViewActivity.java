package com.oklib.demo.common_components;

import android.view.View;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.view.CommonToolBar;
import com.oklib.view.RubblerView;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/9/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：刮刮乐使用演示
 */

public class RubblerViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_rubbler_view;
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
                        mBeans.add(new FunctionDetailBean("activity_rubbler_view.xml", BASE_RES + "/layout/activity_rubbler_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        RubblerView mRubblerView = (RubblerView) findViewById(R.id.rubbler);
        mRubblerView.beginRubbler(40,
                1f, "一等奖");
    }

    @Override
    protected void initNet() {

    }
}
