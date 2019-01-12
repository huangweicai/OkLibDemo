package com.oklib.view_lib;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.FllowerAnimView;


/**
 * 时间：2017/8/18
 * 作者：蓝天
 * 描述：微信撒花动画使用演示
 */

public class FllowerViewActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_fllower_view;
    }

    @Override
    protected void initView() {
        final FllowerAnimView fllower = (FllowerAnimView) findViewById(R.id.fllower);

        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "撒花", Toast.LENGTH_LONG).show();
                fllower.startAnimation();
            }
        });
    }

}
