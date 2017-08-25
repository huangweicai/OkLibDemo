package com.oklib.demo.common_components;

import android.view.View;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.view.CommonToolBar;
import com.oklib.view.ProgressCircleView;

import static com.oklib.demo.Common.BASE_RES;


/**
 * 时间：2017/8/25
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：自定义进度条view使用演示
 */

public class ProgressViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_progress_view;
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
                        mBeans.add(new FunctionDetailBean("activity_progress_view.xml", BASE_RES +"/layout/activity_progress_view.xml"));
                        showDetail();
                    }
                });
    }

    private ProgressCircleView pcv_circle;
    @Override
    protected void initView() {
        pcv_circle = findView(R.id.pcv_circle);
    }

    @Override
    protected void initNet() {

    }

    int count = 5;
    public void start(View view) {
        count++;
        if (count <= 15) {
            pcv_circle.setProgressValue(15, count).startAnim();
        }
    }
}
