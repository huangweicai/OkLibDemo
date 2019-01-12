package com.oklib.view_lib;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.RubblerView;


/**
 * 时间：2017/9/8
 * 作者：蓝天
 * 描述：刮刮乐使用演示
 */

public class RubblerViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_rubbler_view;
    }

    @Override
    protected void initView() {
        RubblerView mRubblerView = (RubblerView) findViewById(R.id.rubbler);
        mRubblerView.beginRubbler(40,
                1f, "一等奖");
    }

}
