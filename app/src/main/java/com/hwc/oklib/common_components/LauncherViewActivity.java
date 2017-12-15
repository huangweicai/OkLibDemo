package com.hwc.oklib.common_components;

import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.launch.LauncherView;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/30
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：应用启动动画使用演示
 */

public class LauncherViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_launcher_view;
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
                        mBeans.add(new FunctionDetailBean("activity_launcher_view.xml", BASE_RES +"/layout/activity_launcher_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        final LauncherView launcherView = (LauncherView) findViewById(R.id.load_view);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherView.setLogo(R.mipmap.app_icon, true);
                launcherView.setSLogo(R.mipmap.app_icon, false);
                launcherView.start();
            }
        });
    }

    @Override
    protected void initNet() {

    }
}
