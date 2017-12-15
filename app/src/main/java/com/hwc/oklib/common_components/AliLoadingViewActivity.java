package com.hwc.oklib.common_components;

import android.view.View;
import android.widget.Button;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.AliLoadingStatusView;
import com.hwc.oklib.view.CommonToolBar;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/29
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：支付宝加载动画使用演示
 */

public class AliLoadingViewActivity extends BaseAppActivity implements View.OnClickListener {
    private AliLoadingStatusView customStatusView;

    private Button btn_init;
    private Button btnSuccess;
    private Button btnFailure;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ali_loading_view;
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
                        mBeans.add(new FunctionDetailBean("activity_ali_loading_view.xml", BASE_RES +"/layout/activity_ali_loading_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        customStatusView = (AliLoadingStatusView) findViewById(R.id.as_status);
        btn_init = (Button) findViewById(R.id.btn_init);
        btnSuccess = (Button) findViewById(R.id.btn_success);
        btnFailure = (Button) findViewById(R.id.btn_failure);

        customStatusView.loadLoading();
        btnSuccess.setOnClickListener(this);
        btnFailure.setOnClickListener(this);
        btn_init.setOnClickListener(this);
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_success:
                customStatusView.loadSuccess();
                break;
            case R.id.btn_failure:
                customStatusView.loadFailure();
                break;
            case R.id.btn_init:
                customStatusView.loadLoading();
                break;
        }
    }
}
