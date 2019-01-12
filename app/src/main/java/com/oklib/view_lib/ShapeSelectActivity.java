package com.oklib.view_lib;

import android.view.View;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.StateButton;
import com.oklib.view.StateImageView;


/**
 * 时间：2017/8/3
 * 作者：蓝天
 * 描述：shape，select使用样例
 */

public class ShapeSelectActivity extends BaseAppActivity {

    private StateImageView iv_text_test;

    private StateButton text;

    private StateButton background;

    private StateButton radius;

    private StateButton stroke;

    private StateButton dash;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_shape_select;
    }

    @Override
    protected void initView() {

        //设置不同状态下文字变色
        iv_text_test = (StateImageView) findViewById(R.id.iv_text_test);
        iv_text_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setEnabled(false);
            }
        });

        //设置不同状态下文字变色
        text = (StateButton) findViewById(R.id.text_test);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setEnabled(false);
            }
        });

        //最常用的设置不同背景
        background = (StateButton) findViewById(R.id.background_test);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setEnabled(false);
            }
        });

        //设置四个角不同的圆角
        radius = (StateButton) findViewById(R.id.different_radius_test);
        radius.setRadius(new float[]{0, 0, 20, 20, 40, 40, 60, 60});


        //设置不同状态下边框颜色，宽度
        stroke = (StateButton) findViewById(R.id.stroke_test);
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stroke.setEnabled(false);
            }
        });

        //设置间断
        dash = (StateButton) findViewById(R.id.dash_test);
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dash.setEnabled(false);
            }
        });
    }

}
