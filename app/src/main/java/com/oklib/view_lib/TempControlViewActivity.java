package com.oklib.view_lib;

import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.TempControlView;


/**
 * 时间：2018/1/3
 * 作者：蓝天
 * 描述：温度控制view
 */

public class TempControlViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_temp_control_view;
    }

    private TempControlView tempControl;
    @Override
    protected void initView() {
        tempControl = (TempControlView) findViewById(R.id.temp_control);
        // 设置三格代表温度1度
        tempControl.setAngleRate(3);
        tempControl.setTemp(16, 37, 16);

        tempControl.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
            @Override
            public void change(int temp) {
                Toast.makeText(TempControlViewActivity.this, temp + "°", Toast.LENGTH_SHORT).show();
            }
        });

        tempControl.setOnClickListener(new TempControlView.OnClickListener() {
            @Override
            public void onClick(int temp) {
                Toast.makeText(TempControlViewActivity.this, temp + "°", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
