package com.hwc.oklib.common_components;

import android.view.View;
import android.widget.Button;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.progress.CircleProgressView;
import com.hwc.oklib.view.progress.HorizontalProgressBar;
import com.hwc.oklib.view.progress.PercentCircleProgressView;

import static com.hwc.oklib.Common.BASE_RES;


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

    @Override
    protected void initView() {
        final PercentCircleProgressView pcv_circle = findView(R.id.pcv_circle);
        Button btn_percent_anim = findView(R.id.btn_percent_anim);
        btn_percent_anim.setOnClickListener(new View.OnClickListener() {
            int count = 5;
            @Override
            public void onClick(View v) {
                count++;
                if (count <= 15) {
                    pcv_circle.setProgressValue(15, count).startAnim();
                }
            }
        });


        final CircleProgressView mCircleBar = (CircleProgressView) findViewById(R.id.circleProgressbar);
        mCircleBar.setProgress(50);//0-100
        Button btn_c_start = findView(R.id.btn_c_start);
        btn_c_start.setOnClickListener(new View.OnClickListener() {
            int count = 50;
            @Override
            public void onClick(View v) {
                count++;
                if (count <= 100) {
                    mCircleBar.setProgress(count);//0-100
                }
            }
        });


        final HorizontalProgressBar horizontalProgressBar = (HorizontalProgressBar) findViewById(R.id.horizontalProgressBar);
        horizontalProgressBar.setMax(100);//必须，设置最大值
        horizontalProgressBar.setProgress(50);//进度值
        Button btn_h_start = findView(R.id.btn_h_start);
        btn_h_start.setOnClickListener(new View.OnClickListener() {
            int count = 50;
            @Override
            public void onClick(View v) {
                count++;
                if (count <= 100) {
                    horizontalProgressBar.setProgress(count);
                }
            }
        });

    }

    @Override
    protected void initNet() {

    }


    public void start(View view) {

    }
}
