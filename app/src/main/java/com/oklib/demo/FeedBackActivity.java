package com.oklib.demo;

import android.view.View;

import com.oklib.demo.base.BaseAppActivity;
import com.oklib.view.CommonToolBar;
import com.oklib.view.EditTextWithDelete;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：反馈界面
 */

public class FeedBackActivity extends BaseAppActivity {
    private EditTextWithDelete et_input_content;
    private EditTextWithDelete et_contact_way;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feedback;
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
                .setRightTitle("提交", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    @Override
    protected void initView() {
        et_input_content = findView(R.id.et_input_content);
        et_contact_way = findView(R.id.et_contact_way);
    }

    @Override
    protected void initNet() {

    }
}
