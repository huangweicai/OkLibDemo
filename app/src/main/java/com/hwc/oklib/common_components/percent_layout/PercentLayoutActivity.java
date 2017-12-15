package com.hwc.oklib.common_components.percent_layout;

import android.content.Intent;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/26
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：适配百分比布局
 */

public class PercentLayoutActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_adaptivity_layout;
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
                        mBeans.add(new FunctionDetailBean("activity_fllower_view.xml", BASE_RES +"/layout/activity_fllower_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }

    public void PercentRelativeLayout(View view) {
        Intent intent = new Intent(context, PercentRelativeLayoutActivity.class);
        intent.putExtra(Common.TITLE, "PercentRelativeLayout");
        startActivity(intent);
    }
    public void PercentFrameLayout(View view) {
        Intent intent = new Intent(context, PercentFrameLayoutActivity.class);
        intent.putExtra(Common.TITLE, "PercentFrameLayout");
        startActivity(intent);
    }
    public void PercentLinearLayout(View view) {
        Intent intent = new Intent(context, PercentLinearLayoutActivity.class);
        intent.putExtra(Common.TITLE, "PercentLinearLayout");
        startActivity(intent);
    }
}
