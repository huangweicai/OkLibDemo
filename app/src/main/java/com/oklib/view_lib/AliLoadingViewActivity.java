package com.oklib.view_lib;

import android.view.View;
import android.widget.Button;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.AliLoadingStatusView;


/**
 * 时间：2017/8/29
 * 作者：蓝天
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
