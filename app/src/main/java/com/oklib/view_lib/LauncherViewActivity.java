package com.oklib.view_lib;

import android.view.View;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.launch.LauncherView;


/**
 * 时间：2017/8/30
 * 作者：蓝天
 * 描述：应用启动动画使用演示
 */

public class LauncherViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_launcher_view;
    }

    @Override
    protected void initView() {
        final LauncherView launcherView = (LauncherView) findViewById(R.id.load_view);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherView.setLogo(R.mipmap.app_oklib_icon, true);
                launcherView.setSLogo(R.mipmap.app_oklib_icon, false);
                launcherView.start();
            }
        });
    }

    @Override
    protected void initNet() {

    }
}
