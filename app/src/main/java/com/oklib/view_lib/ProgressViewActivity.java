package com.oklib.view_lib;

import android.view.View;
import android.widget.Button;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.progress.CircleProgressView;
import com.oklib.view.progress.HorizontalProgressBar;
import com.oklib.view.progress.PercentCircleProgressView;


/**
 * 时间：2017/8/25
 * 作者：蓝天
 * 描述：自定义进度条样例
 */

public class ProgressViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_progress_view;
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

    public void start(View view) {

    }
}
